#include "ofApp.h"
#include <vector>

//--------------------------------------------------------------
void ofApp::setup() {
	glEnable(GL_DEPTH_TEST);
	coutModelviewMatrix();
	ofBackground(0.05, 0.05, 0.05);
	glLineWidth(3);

	//view
	top = 1;
	lensAngle = 60;
	alpha = 10;
	beta = 1000;

	//floor parameters
	resX = 10; // malha eixo x
	resY = 4;  // malha eixo y
	floorWidth = gw();
	floorHeight = gw() * 0.5;
	floorHeightPos = 0;
	
	x_start = -0.5;
	y_start = -0.5;

	x_step = 1.0 / GLfloat(resX);
	y_step = 1.0 / GLfloat(resY);

	number_builds = resX * resY;

	matrix.resize(number_builds, std::vector<GLfloat>(3));  // comprimentos / larguras / alturas - matriz
	choice.resize(number_builds);
	colour.resize(number_builds, std::vector<GLfloat>(3));  // Cores dos predios

	for (int i = 0; i < number_builds; i++) {
		matrix[i][0] = x_step * (rand() % 61 + 40) / 100.0;
		matrix[i][1] = y_step * (rand() % 61 + 40) / 100.0;
		matrix[i][2] = (rand() % 201 + 100);
	}

	for (int i = 0; i < number_builds; i++) {
		choice[i] = rand() % 3;
	}

	for (int i = 0; i < number_builds; i++) {
		colour[i][0] = ofRandom(0, 1);
		colour[i][1] = ofRandom(0, 1);
		colour[i][2] = ofRandom(0, 1);
	}

	ambientOn = false;
	diffuseOn = false;
	espcOn = false;
	dirOn = true;
	pointOn = true;
	spotOn = false;
	autoMoveTop = true;
	autoMoveSide = false;
	localViewer = false;

	mat_1 = 1;
	mat_2 = 10;
	mat_3 = 3;
	mat_4 = 7;
	customMat = false;
	customMatCoef = 1;

}

//--------------------------------------------------------------
void ofApp::update(){
	
	angle += 2;


	if (autoMoveTop) {

		//calculo da direcao da luz direcional
		float z = gh() * 0.25 * (cos(dirVecTheta * PI / 180.) + 0.45);
		float y = gh() * 0.5;
		float x = 0;
		dirVec3f = ofVec3f(x, y, z) - ofVec3f(0, 0, 0);
		dirVecTheta += 0.5;

	}

	if (autoMoveSide) {

		//calculo da direcao da luz direcional
		float z = gh() * 0.25 * (cos(dirVecTheta * PI / 180.) + 0.45);
		float y = gh() * 0.5;
		float x = 0;
		dirVec3f = ofVec3f(x, z, y) - ofVec3f(0, 0, 0);
		dirVecTheta += 0.5;

	}


}

//--------------------------------------------------------------
void ofApp::draw() {
	// define viewport
	glViewport(0, 0, gw(), gh());

	if (top == 1) {
		// Matriz projeção / Ortogonal
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(-gw() * 0.5, gw() * 0.5, -gw() * 0.5, gw() * 0.5, -1000, 1000);

		// Posiciona a câmera 
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glPushMatrix();
		lookat(0, 0, 800, 0, 0, 0, 0, 1, 0);
		draw_skyline();
		glPopMatrix();

	}

	else if (top == 2) {
		// Matriz projeção / Ortogonal
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(-gw() * 0.5, gw() * 0.5, -gw() * 0.5, gw() * 0.5, -10000, 10000);

		//Posiciona a câmera
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glPushMatrix();
		lookat(0, -gw(), 0, 0, 0, 0, 0, 0, 1);
		draw_skyline();
		glPopMatrix();

	}
	else if (top == 3) {
		// Matriz projeção / Ortogonal
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(-gw() * 0.5, gw() * 0.5, -gw() * 0.5, gw() * 0.5, -10000, 10000);

		//Posiciona a câmera
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glPushMatrix();
		lookat(gw() * 0.5, gw() * 0.5, 0, 0, 0, 0, 0, 0, 1);
		draw_skyline();
		glPopMatrix();

	}

	else if (top == 4) {
		// Matriz projeção / Prespetiva
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		perspective(lensAngle, alpha, beta);


		//posiciona a câmera com lookat
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glPushMatrix();
		lookat(0, 0, 1000, 0, 0, 0, 0, 1, 0);
		draw_skyline();
		glPopMatrix();
	}

	else if (top == 5) {
		// Matriz projeção / Prespetiva
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		perspective(lensAngle, alpha, beta);

		//posiciona a câmera com lookat
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glPushMatrix();
		lookat(0, -gw(), 0, 0, 0, 0, 0, 0, 1);
		draw_skyline();
		glPopMatrix();
	}

	else if (top == 6) {
		// Matriz projeção / Prespetiva
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		perspective(lensAngle, alpha, beta);

		//posiciona a câmera com lookat
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glPushMatrix();
		lookat(gw() * 0.5, gw() * 0.5, 0, 0, 0, 0, 0, 0, 1);
		draw_skyline();
		glPopMatrix();
	}


	//#################################################
	//inicia iluminação
	glEnable(GL_NORMALIZE);//utiliza versores para normais (normais normalizadas)
	glLightModeli(GL_LIGHT_MODEL_LOCAL_VIEWER, localViewer);
	//#################################################


	//#################################################
	//fonte de luz que só tem componente ambiente
	//não conta como uma fonte de luz LIGHT0 - LIGHT8
	if (ambientOn) {
		ambientLight[0] = 1;//R
		ambientLight[1] = 1;//G
		ambientLight[2] = 1;//B
		ambientLight[3] = 1;//useless
	}
	else {
		ambientLight[0] = 0.;
		ambientLight[1] = 0.;
		ambientLight[2] = 0.;
		ambientLight[3] = 1;
	}
	glLightModelfv(GL_LIGHT_MODEL_AMBIENT, ambientLight);
	//#################################################



	//#################################################
	//luz pontual
	pointPos1[0] = -floorWidth / 2;//x
	pointPos1[1] = floorHeight / 2;//y
	pointPos1[2] = 300;//z
	pointPos1[3] = 1;//ponto - posição!

	pointPos2[0] = floorWidth / 2;//x
	pointPos2[1] = floorHeight / 2;//y
	pointPos2[2] = 300;//z
	pointPos2[3] = 1;//ponto - posição!

	pointPos3[0] = -floorWidth / 2;//x
	pointPos3[1] = -floorHeight / 2;//y
	pointPos3[2] = 300;//z
	pointPos3[3] = 1;//ponto - posição!

	pointPos4[0] = floorWidth / 2;//x
	pointPos4[1] = -floorHeight / 2;//y
	pointPos4[2] = 300;//z
	pointPos4[3] = 1;//ponto - posição!

	pointAmb[0] = 1;//R
	pointAmb[1] = 1;//G
	pointAmb[2] = 1;//B
	pointAmb[3] = 1.;//constante

	if (diffuseOn) {
		pointDif[0] = 1;//R
		pointDif[1] = 1;//G
		pointDif[2] = 1;//B
		pointDif[3] = 1.;//constante
	}

	else {
		pointDif[0] = 0;//R
		pointDif[1] = 0;//G
		pointDif[2] = 0;//B
		pointDif[3] = 1.;//constante
	}

	if (espcOn) {
		pointSpec[0] = 1.;//R
		pointSpec[1] = 1.;//G
		pointSpec[2] = 1.;//B
		pointSpec[3] = 1.;//constante
	}
	else {
		pointSpec[0] = 0.;//R
		pointSpec[1] = 0.;//G
		pointSpec[2] = 0.;//B
		pointSpec[3] = 1.;//constante
	}

	if (top == 2 || top == 3 || top == 5 || top == 6) {
		aux1 = pointPos1[1];
		pointPos1[1] = pointPos1[2];
		pointPos1[2] = aux1;

		aux2 = pointPos2[1];
		pointPos2[1] = pointPos2[2];
		pointPos2[2] = aux1;

		aux3 = pointPos3[1];
		pointPos3[1] = pointPos3[2];
		pointPos3[2] = aux1;

		aux4 = pointPos4[1];
		pointPos4[1] = pointPos4[2];
		pointPos4[2] = aux1;
	}

	glLightfv(GL_LIGHT1, GL_POSITION, pointPos1);
	glLightfv(GL_LIGHT1, GL_AMBIENT, pointAmb);
	glLightfv(GL_LIGHT1, GL_DIFFUSE, pointDif);
	glLightfv(GL_LIGHT1, GL_SPECULAR, pointSpec);

	glLightfv(GL_LIGHT2, GL_POSITION, pointPos2);
	glLightfv(GL_LIGHT2, GL_AMBIENT, pointAmb);
	glLightfv(GL_LIGHT2, GL_DIFFUSE, pointDif);
	glLightfv(GL_LIGHT2, GL_SPECULAR, pointSpec);

	glLightfv(GL_LIGHT3, GL_POSITION, pointPos3);
	glLightfv(GL_LIGHT3, GL_AMBIENT, pointAmb);
	glLightfv(GL_LIGHT3, GL_DIFFUSE, pointDif);
	glLightfv(GL_LIGHT3, GL_SPECULAR, pointSpec);

	glLightfv(GL_LIGHT4, GL_POSITION, pointPos4);
	glLightfv(GL_LIGHT4, GL_AMBIENT, pointAmb);
	glLightfv(GL_LIGHT4, GL_DIFFUSE, pointDif);
	glLightfv(GL_LIGHT4, GL_SPECULAR, pointSpec);

	//atenuação
	//reparem que não é fv (float vector) é só f (float)
	pointAtC = 1;
	pointAtL = 0.0001;
	pointAtQ = 0.00001;
	glLightf(GL_LIGHT1, GL_CONSTANT_ATTENUATION, pointAtC);
	glLightf(GL_LIGHT1, GL_LINEAR_ATTENUATION, pointAtL);
	glLightf(GL_LIGHT1, GL_QUADRATIC_ATTENUATION, pointAtQ);

	glLightf(GL_LIGHT2, GL_CONSTANT_ATTENUATION, pointAtC);
	glLightf(GL_LIGHT2, GL_LINEAR_ATTENUATION, pointAtL);
	glLightf(GL_LIGHT2, GL_QUADRATIC_ATTENUATION, pointAtQ);

	glLightf(GL_LIGHT3, GL_CONSTANT_ATTENUATION, pointAtC);
	glLightf(GL_LIGHT3, GL_LINEAR_ATTENUATION, pointAtL);
	glLightf(GL_LIGHT3, GL_QUADRATIC_ATTENUATION, pointAtQ);

	glLightf(GL_LIGHT4, GL_CONSTANT_ATTENUATION, pointAtC);
	glLightf(GL_LIGHT4, GL_LINEAR_ATTENUATION, pointAtL);
	glLightf(GL_LIGHT4, GL_QUADRATIC_ATTENUATION, pointAtQ);
	if (pointOn) {
		glEnable(GL_LIGHT1);
		glEnable(GL_LIGHT2);
		glEnable(GL_LIGHT3);
		glEnable(GL_LIGHT4);
	}
	else {
		glDisable(GL_LIGHT1);
	}

	//#################################################
	//luz direcional
	dirVec[0] = dirVec3f.x;//x
	dirVec[1] = dirVec3f.y;//y
	dirVec[2] = dirVec3f.z;//z
	dirVec[3] = 0;//vetor - direção!

	dirAmb[0] = 0.3;//R
	dirAmb[1] = 0.3;//G
	dirAmb[2] = 0.3;//B
	dirAmb[3] = 1.;//constante

	if (diffuseOn) {
		dirDif[0] = 0.8;//R
		dirDif[1] = 0;//G
		dirDif[2] = 0;//B
		dirDif[3] = 1.;//constante
	}

	else {
		dirDif[0] = 0;//R
		dirDif[1] = 0;//G
		dirDif[2] = 0;//B
		dirDif[3] = 1.;//constante
	}

	if (espcOn) {
		dirSpec[0] = 0.;//R
		dirSpec[1] = 0.;//G
		dirSpec[2] = 0.;//B
		dirSpec[3] = 1.;//constante
	}
	else {
		dirSpec[0] = 0.;//R
		dirSpec[1] = 0.;//G
		dirSpec[2] = 0.;//B
		dirSpec[3] = 1.;//constante
	}

	glLightfv(GL_LIGHT0, GL_POSITION, dirVec);
	glLightfv(GL_LIGHT0, GL_AMBIENT, dirAmb);
	glLightfv(GL_LIGHT0, GL_DIFFUSE, dirDif);
	glLightfv(GL_LIGHT0, GL_SPECULAR, dirSpec);
	if (dirOn) {
		glEnable(GL_LIGHT0);
	}
	else {
		glDisable(GL_LIGHT0);
	}

	if (top == 2 || top == 3 || top == 5 || top == 6) {
		autoMoveTop = false;
		autoMoveSide = true;
	}

	if (top == 1 || top == 4) {
		autoMoveTop = true;
		autoMoveSide = false;
	}


	//#################################################
	//luz foco left

	//posicao
	spotPos1[0] = -gw() * 0.25;
	spotPos1[1] = 0.;
	spotPos1[2] = 300;
	spotPos1[3] = 1.;

	spotPos2[0] = 0;
	spotPos2[1] = 0.;
	spotPos2[2] = 300;
	spotPos2[3] = 1.;

	spotPos3[0] = gw() * 0.25;
	spotPos3[1] = 0.;
	spotPos3[2] = 300;
	spotPos3[3] = 1.;

	//direcao
	spotDir[0] = 0.;
	spotDir[1] = 0.;
	spotDir[2] = -1.;
	//spotDir[3] = 0.;Não tem a 4 coordenada, é sempre vetor

	//ambiente
	spotAmb[0] = 0.;//R
	spotAmb[1] = 0.;//G
	spotAmb[2] = 1.;//B
	spotAmb[3] = 0.;//constante

	//difusa
	if (diffuseOn) {
		spotDif1[0] = 0.;//R
		spotDif1[1] = 1.;//G
		spotDif1[2] = 0.;//B
		spotDif1[3] = 1.;//constante

		spotDif2[0] = 1.;//R
		spotDif2[1] = 1.;//G
		spotDif2[2] = 0.;//B
		spotDif2[3] = 1.;//constante

		spotDif3[0] = 1.;//R
		spotDif3[1] = 0.;//G
		spotDif3[2] = 0.;//B
		spotDif3[3] = 1.;//constante
	}

	else {
		spotDif1[0] = 0.;//R
		spotDif1[1] = 0.;//G
		spotDif1[2] = 0.;//B
		spotDif1[3] = 1.;//constante

		spotDif2[0] = 0.;//R
		spotDif2[1] = 0.;//G
		spotDif2[2] = 0.;//B
		spotDif2[3] = 1.;//constante

		spotDif3[0] = 0.;//R
		spotDif3[1] = 0.;//G
		spotDif3[2] = 0.;//B
		spotDif3[3] = 1.;//constante
	}

	//specular
	if (espcOn) {
		spotSpecular[0] = 1.;//R
		spotSpecular[1] = 1.;//G
		spotSpecular[2] = 1.;//B
		spotSpecular[3] = 1.;//constante
	}

	else {
		spotSpecular[0] = 0.;//R
		spotSpecular[1] = 0.;//G
		spotSpecular[2] = 0.;//B
		spotSpecular[3] = 1.;//constante
	}

	//concentracao
	spotExponent = 80;//0 - 128

	//angulo
	spotCutoff = 60;//0 - 180

	if (top == 2 || top == 3 || top == 5 || top == 6) {
		aux1 = spotPos1[1];
		spotPos1[1] = spotPos1[2];
		spotPos1[2] = aux1;

		aux2 = spotPos2[1];
		spotPos2[1] = spotPos2[2];
		spotPos2[2] = aux1;

		aux3 = spotPos3[1];
		spotPos3[1] = spotPos3[2];
		spotPos3[2] = aux1;

	}

	glLightfv(GL_LIGHT5, GL_POSITION, spotPos1);
	glLightfv(GL_LIGHT5, GL_SPOT_DIRECTION, spotDir);

	glLightfv(GL_LIGHT5, GL_AMBIENT, spotAmb);
	glLightfv(GL_LIGHT5, GL_DIFFUSE, spotDif1);
	glLightfv(GL_LIGHT5, GL_SPECULAR, spotSpecular);

	glLightf(GL_LIGHT5, GL_SPOT_EXPONENT, spotExponent);
	glLightf(GL_LIGHT5, GL_SPOT_CUTOFF, spotCutoff);

	glLightfv(GL_LIGHT6, GL_POSITION, spotPos2);
	glLightfv(GL_LIGHT6, GL_SPOT_DIRECTION, spotDir);

	glLightfv(GL_LIGHT6, GL_AMBIENT, spotAmb);
	glLightfv(GL_LIGHT6, GL_DIFFUSE, spotDif2);
	glLightfv(GL_LIGHT6, GL_SPECULAR, spotSpecular);

	glLightf(GL_LIGHT6, GL_SPOT_EXPONENT, spotExponent);
	glLightf(GL_LIGHT6, GL_SPOT_CUTOFF, spotCutoff);

	glLightfv(GL_LIGHT7, GL_POSITION, spotPos3);
	glLightfv(GL_LIGHT7, GL_SPOT_DIRECTION, spotDir);

	glLightfv(GL_LIGHT7, GL_AMBIENT, spotAmb);
	glLightfv(GL_LIGHT7, GL_DIFFUSE, spotDif3);
	glLightfv(GL_LIGHT7, GL_SPECULAR, spotSpecular);

	glLightf(GL_LIGHT7, GL_SPOT_EXPONENT, spotExponent);
	glLightf(GL_LIGHT7, GL_SPOT_CUTOFF, spotCutoff);

	//atenuação
	spotAtC = 1.;
	spotAtL = 0.;
	spotAtQ = 0.;
	glLightf(GL_LIGHT5, GL_CONSTANT_ATTENUATION, spotAtC);
	glLightf(GL_LIGHT5, GL_LINEAR_ATTENUATION, spotAtL);
	glLightf(GL_LIGHT5, GL_QUADRATIC_ATTENUATION, spotAtQ);

	glLightf(GL_LIGHT6, GL_CONSTANT_ATTENUATION, spotAtC);
	glLightf(GL_LIGHT6, GL_LINEAR_ATTENUATION, spotAtL);
	glLightf(GL_LIGHT6, GL_QUADRATIC_ATTENUATION, spotAtQ);

	glLightf(GL_LIGHT7, GL_CONSTANT_ATTENUATION, spotAtC);
	glLightf(GL_LIGHT7, GL_LINEAR_ATTENUATION, spotAtL);
	glLightf(GL_LIGHT7, GL_QUADRATIC_ATTENUATION, spotAtQ);
	if (spotOn) {
		glEnable(GL_LIGHT5);
		glEnable(GL_LIGHT6);
		glEnable(GL_LIGHT7);
	}
	else {
		glDisable(GL_LIGHT5);
		glDisable(GL_LIGHT6);
		glDisable(GL_LIGHT7);
	}

	
	if (pointOn) {
		glColor3f(1, 1, 1);
		glPushMatrix();
		glTranslatef(pointPos1[0], pointPos1[1], pointPos1[2]);
		glScalef(30, 30, 30);
		cube_unit();
		glPopMatrix();

		glPushMatrix();
		glTranslatef(pointPos2[0], pointPos2[1], pointPos2[2]);
		glScalef(30, 30, 30);
		cube_unit();
		glPopMatrix();

		glPushMatrix();
		glTranslatef(pointPos3[0], pointPos3[1], pointPos3[2]);
		glScalef(30, 30, 30);
		cube_unit();
		glPopMatrix();

		glPushMatrix();
		glTranslatef(pointPos4[0], pointPos4[1], pointPos4[2]);
		glScalef(30, 30, 30);
		cube_unit();
		glPopMatrix();
	}
	

	if (dirOn) {
		glColor3f(1, 1, 1);
		glPushMatrix();
		glTranslatef(dirVec3f.x, dirVec3f.y, dirVec3f.z);
		glScalef(30, 30, 30);
		cube_unit();
		glPopMatrix();

		glPushMatrix();
		glBegin(GL_LINES);
		glVertex3f(0, 0, 0);
		glVertex3f(dirVec3f.x, dirVec3f.y, dirVec3f.z);
		glEnd();
		glPopMatrix();
	}

}

//______________________________________________________________
void ofApp::draw_skyline() {

	//chão
	glPushMatrix();//chão push
	glColor3f(0.5, 0.5, 0.5);
	glScalef(floorWidth, floorHeight, 1);
	loadMaterial(mat_2);
	malha_unit(resX, resY);
	glPopMatrix();//chão pop

	//predios
	glPushMatrix(); // predios push
	glTranslatef(-floorWidth / 2, -floorHeight / 2, 0); // inicio da malha
	glTranslatef((x_step * floorWidth) / 2, (y_step * floorHeight) / 2, 0); // posiciona dentro dos quadrados da malha
	GLint aux = 0;

	for (int i = 0; i < resX; i++) {
		for (int j = 0; j < resY; j++) {

			switch (choice[aux]) {
			
			case 0:
				glPushMatrix(); // predios com malha push
				glColor3f(colour[i + j][0], colour[i + j][1], colour[i + j][2]); // cor do predio
				glTranslatef(i * floorWidth * x_step, j * floorHeight * y_step, 0); // posiciona no quadrado da malha correto
				glTranslatef(0, 0, matrix[i + j][2] / 2); // ajustamento devido ao scale
				glScalef(matrix[i + j][0] * floorWidth, matrix[i + j][1] * floorHeight, matrix[i + j][2]); // scale com os valores aleatorios guardados na matriz
				//cube_malha_unit(4, 4);
				glPopMatrix(); // predios com malha pop
				
				aux = aux + 1;
				break;

			case 1:
				glPushMatrix(); // predios por pontos push
				glColor3f(colour[i + j][0], colour[i + j][1], colour[i + j][2]); // cor do predio
				glTranslatef(i * floorWidth * x_step, j * floorHeight * y_step, 0); // posiciona no quadrado da malha correto
				glScalef(matrix[i * j][0] * floorWidth, matrix[i * j][0] * floorWidth, matrix[i * j][2]); // scale com os valores aleatorios guardados na matriz
				loadMaterial(mat_1);
				buildings_point();
				glPopMatrix();// predios por pontos pop

				glPushMatrix();
				if (i == 0 || j == 0) { 
					break;
				}
				else {
					glTranslatef(i * floorWidth * x_step, j * floorHeight * y_step, matrix[i * j][2]); // posicionar os cata-ventos
				}
				glRotatef(angle, 0, 0, 1);
				glScalef(matrix[i * j][0] * floorWidth / 2, matrix[i * j][0] * floorWidth / 4, matrix[i * j][0] * floorWidth / 9); // scale para regular o tamanho dos cata-ventos
				loadMaterial(mat_3);
				wind_vanes(3);
				glPopMatrix();

				aux = aux + 1;
				break;

			case 2:
				glPushMatrix(); // predios com cubos push
				glColor3f(colour[i + j][0], colour[i + j][1], colour[i + j][2]); // cor do predio
				glTranslatef(i * floorWidth * x_step, j * floorHeight * y_step, 0);  // posiciona no quadrado da malha correto
				glScalef(matrix[i * j][0] * floorWidth, matrix[i * j][0] * floorWidth, matrix[i * j][0] * floorWidth); // scale com os valores aleatorios guardados na matriz
				loadMaterial(mat_4);
				cube_buildings(resX, resY, matrix, 2, 10);
				glPopMatrix();// predios com cubos push

				aux = aux + 1;
				break;

			}
		}
	}
	glPopMatrix();

	glPopMatrix();


}

//--------------------------------------------------------------
void ofApp::keyPressed(int key) {
	switch (key) {
	case 'z':
		glDisable(GL_CULL_FACE);
		break;
	case 'x':
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		break;
	case 'c':
		glEnable(GL_CULL_FACE);
		glCullFace(GL_FRONT);
		break;
	case 'v':
		glEnable(GL_CULL_FACE);
		glCullFace(GL_FRONT_AND_BACK);
		break;
	case 'g':
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		break;
	case 'f':
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		break;
	case 'l':
		glEnable(GL_LIGHTING);
		break;
	case 'p':
		glDisable(GL_LIGHTING);
		break;
	case '1':
		top = 1;
		break;
	case '2':
		top = 2;
		break;
	case '3':
		top = 3;
		break;
	case '4':
		top = 4;
		break;
	case '5':
		top = 5;
		break;
	case '6':
		top = 6;
		break;
	case '7':
		pointOn = !pointOn;
		break;
	case '8':
		dirOn = !dirOn;
		break;
	case '9':
		spotOn = !spotOn;
		break;
	case 'q':
		ambientOn = !ambientOn;
		break;
	case 'w':
		diffuseOn = !diffuseOn;
		break;
	case 'e':
		espcOn = !espcOn;
		break;
	}
}

//--------------------------------------------------------------
void ofApp::keyReleased(int key){

}

//--------------------------------------------------------------
void ofApp::mouseMoved(int x, int y ){

}

//--------------------------------------------------------------
void ofApp::mouseDragged(int x, int y, int button){

}

//--------------------------------------------------------------
void ofApp::mousePressed(int x, int y, int button){

}

//--------------------------------------------------------------
void ofApp::mouseReleased(int x, int y, int button){

}

//--------------------------------------------------------------
void ofApp::mouseEntered(int x, int y){

}

//--------------------------------------------------------------
void ofApp::mouseExited(int x, int y){

}

//--------------------------------------------------------------
void ofApp::windowResized(int w, int h){
	setup();
}

//--------------------------------------------------------------
void ofApp::gotMessage(ofMessage msg){

}

//--------------------------------------------------------------
void ofApp::dragEvent(ofDragInfo dragInfo){ 

}

