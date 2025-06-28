#pragma once

#include "ofMain.h"
#include "cg_extras.h"
#include "cg_drawing_extras.h"
#include "materiais.h"

class ofApp : public ofBaseApp{

	public:
		void setup();
		void update();
		void draw();
		void draw_skyline();

		void keyPressed(int key);
		void keyReleased(int key);
		void mouseMoved(int x, int y );
		void mouseDragged(int x, int y, int button);
		void mousePressed(int x, int y, int button);
		void mouseReleased(int x, int y, int button);
		void mouseEntered(int x, int y);
		void mouseExited(int x, int y);
		void windowResized(int w, int h);
		void dragEvent(ofDragInfo dragInfo);
		void gotMessage(ofMessage msg);

		bool wireframe;
		
		//game variables
		//floor
		GLfloat x_start, y_start, z_start, x_step, y_step, z_step;
		GLint resX, resY;
		GLfloat floorWidth, floorHeight, floorHeightPos;

		//buildings
		GLfloat buildingWidth, buildingDepth, buildingHeight;
		GLint number_builds;
		std::vector<std::vector<GLfloat>> matrix;
		std::vector<GLint> choice;
		std::vector<std::vector<GLfloat>> colour;

		//giratorio
		GLfloat angle;

		//view
		GLint top;
		GLfloat lensAngle, alpha, beta;

		//lights
		bool ambientOn;
		bool diffuseOn;
		bool espcOn;
		bool dirOn;
		bool pointOn;
		bool autoMoveTop;
		bool autoMoveSide;
		bool localViewer;

		GLfloat ambientLight[4];

		GLfloat dirVec[4];
		GLfloat dirAmb[4];
		GLfloat dirDif[4];
		GLfloat dirSpec[4];
		ofVec3f dirVec3f;
		GLfloat dirVecTheta;

		GLfloat pointPos1[4];
		GLfloat aux1;
		GLfloat pointPos2[4];
		GLfloat aux2;
		GLfloat pointPos3[4];
		GLfloat aux3;
		GLfloat pointPos4[4];
		GLfloat aux4;
		GLfloat pointAmb[4];
		GLfloat pointDif[4];
		GLfloat pointSpec[4];
		GLfloat pointZtheta;
		GLfloat pointAtC, pointAtL, pointAtQ;

		GLfloat spotPos1[4];
		GLfloat spotPos2[4];
		GLfloat spotPos3[4];
		GLfloat spotDir[3];
		GLfloat spotAmb[4];
		GLfloat spotDif1[4];
		GLfloat spotDif2[4];
		GLfloat spotDif3[4];
		GLfloat spotSpecular[4];
		GLfloat spotExponent;
		GLfloat spotCutoff;
		GLfloat spotTheta;
		GLfloat spotAtC, spotAtL, spotAtQ;
		bool spotOn;

		//materiais
		int mat_1;
		int mat_2;
		int mat_3;
		int mat_4;
		bool customMat;
		GLint customMatCoef;
		GLfloat customMatAmb[4];
		GLfloat customMatDif[4];
		GLfloat customMatSpec[4];

		
};
