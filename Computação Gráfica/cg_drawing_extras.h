#pragma once

#include "ofMain.h"
#include "cg_extras.h"
#include <cstdlib>
#include <math.h>


inline void malha_unit(GLint m, GLint n) {
	GLfloat x_start = -0.5;
	GLfloat y_start = -0.5;
	GLfloat x_step = 1.0 / GLfloat(m);
	GLfloat y_step = 1.0 / GLfloat(n);

	glBegin(GL_QUADS);
	for (int i = 0; i < m; i++) {
		for (int j = 0; j < n; j++) {
			glVertex2d(i * x_step + x_start, j * y_step + y_start);
			glVertex2d(i * x_step + x_start, (j + 1) * y_step + y_start);
			glVertex2d((i + 1) * x_step + x_start, (j + 1) * y_step + y_start);
			glVertex2d((i + 1) * x_step + x_start, j * y_step + y_start);
		}
	}
	glEnd();
}


inline void cube_unit() {
	GLfloat p = 0.5;
	glBegin(GL_QUADS);

	//frente
	//glColor3f(1, 0, 0);
	glVertex3f(-p, -p, p);
	glVertex3f(-p, p, p);
	glVertex3f(p, p, p);
	glVertex3f(p, -p, p);

	//tras
	//glColor3f(0, 1, 0);
	glVertex3f(-p, -p, -p);
	glVertex3f(p, -p, -p);
	glVertex3f(p, p, -p);
	glVertex3f(-p, p, -p);

	//glColor3f(1, 1, 1);
	//cima
	glVertex3f(-p, -p, -p);
	glVertex3f(-p, -p, p);
	glVertex3f(p, -p, p);
	glVertex3f(p, -p, -p);

	//baixo
	glVertex3f(-p, p, p);
	glVertex3f(-p, p, -p);
	glVertex3f(p, p, -p);
	glVertex3f(p, p, p);

	//esq
	glVertex3f(-p, -p, p);
	glVertex3f(-p, -p, -p);
	glVertex3f(-p, p, -p);
	glVertex3f(-p, p, p);

	//dir
	glVertex3f(p, -p, p);
	glVertex3f(p, p, p);
	glVertex3f(p, p, -p);
	glVertex3f(p, -p, -p);

	glEnd();

}


inline void buildings_point () {

	GLfloat x_start = 0.5;
	GLfloat y_start = 0.5;
	GLfloat height = 1;
	
	ofPushMatrix();

	glBegin(GL_QUADS);

	// cima
	glVertex3f(-x_start, y_start, height);
	glVertex3f(x_start, y_start, height);
	glVertex3f(x_start, -y_start, height);
	glVertex3f(-x_start, -y_start, height);

	// baixo
	glVertex3f(x_start, y_start, 0);
	glVertex3f(-x_start, y_start, 0);
	glVertex3f(-x_start, -y_start, 0);
	glVertex3f(x_start, -y_start, 0);

	// tras
	glVertex3f(-x_start, -y_start, 0);
	glVertex3f(-x_start, -y_start, height);
	glVertex3f(x_start, -y_start, height);
	glVertex3f(x_start, -y_start, 0);

	// frente
	glVertex3f(-x_start, y_start, 0);
	glVertex3f(-x_start, y_start, height);
	glVertex3f(x_start, y_start, height);
	glVertex3f(x_start, y_start, 0);

	// Esquerda
	glVertex3f(-x_start, -y_start, 0);
	glVertex3f(-x_start, -y_start, height);
	glVertex3f(-x_start, y_start, height);
	glVertex3f(-x_start, y_start, 0);

	// Direita
	glVertex3f(x_start, y_start, 0);
	glVertex3f(x_start, y_start, height);
	glVertex3f(x_start, -y_start, height);
	glVertex3f(x_start, -y_start, 0);

	glEnd();

	ofPopMatrix();
}


inline void cube_malha_unit(GLint m, GLint n) {

	//tras
	glPushMatrix();
	glTranslated(0., -0.5, 0.);
	glRotated(90, 1, 0, 0);
	malha_unit(m, n);
	glPopMatrix();

	//frente
	glPushMatrix();
	glTranslated(0., 0.5, 0.);
	glRotated(-90, 1, 0, 0);
	malha_unit(m, n);
	glPopMatrix();

	//cima
	glPushMatrix();
	glTranslated(0., 0, 0.5);
	malha_unit(m, n);
	glPopMatrix();

	//baixo
	glPushMatrix();
	glTranslated(0., 0, -0.5);
	glRotated(180, 1, 0, 0);
	malha_unit(m, n);
	glPopMatrix();

	//esquerdo
	glPushMatrix();
	glTranslated(-0.5, 0, 0);
	glRotated(-90, 0, 1, 0);
	malha_unit(m, n);
	glPopMatrix();

	//esquerdo
	glPushMatrix();
	glTranslated(0.5, 0, 0);
	glRotated(90, 0, 1, 0);
	malha_unit(m, n);
	glPopMatrix();
}

inline void cube_buildings(GLint m, GLint n, const std::vector<std::vector<GLfloat>>& matrix, GLint num, GLint layers) {
	GLfloat x_start = -0.5;
	GLfloat y_start = -0.5;
	GLfloat z_start = -0.5;
	GLfloat x_step = 1.0 / GLfloat(num);
	GLfloat y_step = 1.0 / GLfloat(num);
	GLfloat z_step = 1.0 / GLfloat(num);

	glBegin(GL_QUADS);
	for (int k = 1; k <= layers; k++) {
		for (int i = 0; i < num; i++) {
			for (int j = 0; j < num; j++) {

				GLfloat  p0 = (i * x_step + x_start);
				GLfloat  p1 = (j * y_step + y_start);
				GLfloat  p2 = ((j + 1) * y_step + y_start);
				GLfloat  p3 = ((i + 1) * x_step + x_start);

				//frente
				glVertex3f(p0, p2, (k - 1) * z_step);
				glVertex3f(p0, p2, k * z_step);
				glVertex3f(p3, p2, k * z_step);
				glVertex3f(p3, p2, (k - 1) * z_step);

				//tras
				glVertex3f(p3, p1, (k - 1) * z_step);
				glVertex3f(p3, p1, k * z_step);
				glVertex3f(p0, p1, k * z_step);
				glVertex3f(p0, p1, (k - 1) * z_step);

				//cima
				glVertex3f(p0, p1, k * z_step);
				glVertex3f(p3, p1, k * z_step);
				glVertex3f(p3, p2, k * z_step);
				glVertex3f(p0, p2, k * z_step);

				//baixo
				glVertex3f(p0, p1, (k - 1) * z_step);
				glVertex3f(p0, p2, (k - 1) * z_step);
				glVertex3f(p3, p2, (k - 1) * z_step);
				glVertex3f(p3, p1, (k - 1) * z_step);

				//esquerda
				glVertex3f(p0, p1, (k - 1) * z_step);
				glVertex3f(p0, p1, k * z_step);
				glVertex3f(p0, p2, k * z_step);
				glVertex3f(p0, p2, (k - 1) * z_step);

				//direita
				glVertex3f(p3, p2, (k - 1) * z_step);
				glVertex3f(p3, p2, k * z_step);
				glVertex3f(p3, p1, k * z_step);
				glVertex3f(p3, p1, (k - 1) * z_step);
			}
		}
	}
	glEnd();
}

inline void wind_vanes(GLint height) {

	glPushMatrix();

	 // poste
	glPushMatrix();
	glColor3f(0, 0, 0);
	glBegin(GL_LINES);
	glVertex3f(0.0, 0.0, 0);
	glVertex3f(0, 0, height);
	glEnd();
	glPopMatrix();
	
	// cata_vento
	glPushMatrix();
	glColor3f(0.5, 0.5, 0.5);
	glTranslatef(0, 0, height);
	buildings_point();
	glPopMatrix();


	glPopMatrix();

}

inline void perspective(GLfloat theta, GLfloat alpha, GLfloat beta, bool invertX = false, bool invertY = false) {
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	GLfloat tan = tanf(theta * 0.5 * PI / 180.0);
	GLfloat d = (gh() / 2.0) / tan;
	GLfloat nearClip = d / alpha;
	GLfloat farClip = d * beta;
	GLfloat ymax = nearClip * tan;
	GLfloat xmax = (gw() / gh()) * ymax;
	if (invertX) {
		xmax = -xmax;
	}
	if (invertY) {
		ymax = -ymax;
	}
	glFrustum(-xmax, xmax, -ymax, ymax, nearClip, farClip);
}

//implementa o algoritmo de lookAt
inline void lookat(
	GLfloat camX,
	GLfloat camY,
	GLfloat camZ,
	GLfloat targetX,
	GLfloat targetY,
	GLfloat targetZ,
	GLfloat upX,
	GLfloat upY,
	GLfloat upZ)
{
	ofVec3f cam = ofVec3f(camX, camY, camZ);
	ofVec3f target = ofVec3f(targetX, targetY, targetZ);
	ofVec3f up = ofVec3f(upX, upY, upZ);


	ofVec3f N = cam - target;
	N = N.normalized();
	ofVec3f U = cross(up, N);
	U = U.normalized();
	ofVec3f V = cross(N, U);
	V = V.normalized();

	/*GLfloat camTransformMatrix[4][4] = {
		{1, 0, 0, 0},
		{0, 1, 0, 0},
		{0, 0, 1, 0},
		{0, 0, 0, 1}
	};*/

	GLfloat camTransformMatrix[4][4] = {
		{U.x, V.x, N.x, 0},
		{U.y, V.y, N.y, 0},
		{U.z, V.z, N.z, 0},
		{-U.dot(cam), -V.dot(cam), -N.dot(cam), 1}
	};

	/*camTransformMatrix[0][0] = U.x;
	camTransformMatrix[1][0] = U.y;
	camTransformMatrix[2][0] = U.z;
	camTransformMatrix[0][1] = V.x;
	camTransformMatrix[1][1] = V.y;
	camTransformMatrix[2][1] = V.z;
	camTransformMatrix[0][2] = N.x;
	camTransformMatrix[1][2] = N.y;
	camTransformMatrix[2][2] = N.z;
	camTransformMatrix[3][0] = -U.dot(cam);
	camTransformMatrix[3][1] = -V.dot(cam);
	camTransformMatrix[3][2] = -N.dot(cam);*/


	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glMultMatrixf(&camTransformMatrix[0][0]);
	//glTranslatef(-cam.x, -cam.y, -cam.z);

}
 