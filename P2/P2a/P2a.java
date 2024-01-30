// Scene Example //<>// //<>//

float time = 0;  // keep track of the "time"

void setup() {
  size (600, 600, P3D);  // use 3D here!
  background(0);
  noStroke();  
}

// Draw a scene with a box, cylinder and sphere
void draw() {
  resetMatrix();  // set the transformation matrix to the identity (important!)
  background(255, 255, 255);

  // set up for perspective projection
  perspective (PI * 0.333, 1.0, 0.01, 1000.0);

  // place the camera in the scene
  camera (0, 0, 85, 0, 0.0, -1.0, 0.0, 1.0, 0.0);
  
  // create an ambient light source
  // larger # = brighter
  ambientLight (100, 100, 100);

  // create two directional light sources
  lightSpecular (204, 204, 204);
  directionalLight (50, 50, 50, -10, -10, -1);
  directionalLight (150, 150, 150, 0, 0, -1);
  
  pushMatrix();
  rotateY(-PI/8);
  hottubduck();
  popMatrix();
 
    
  // step forward the time
  time += 0.03;
}

// Process key press events
void keyPressed()
{
  if (key == 's' || key =='S') {
    save ("image_file.jpg");
    println ("Screen shot was saved in JPG file.");
  }
}

// Draw a cylinder of a given radius, height and number of sides.
// The base is on the y=0 plane, and it extends vertically in the y direction.
void cylinder (float radius, float height, int sides, float topr, float botr) {
  int i,ii;
  float []c = new float[sides];
  float []s = new float[sides];

  for (i = 0; i < sides; i++) {
    float theta = TWO_PI * i / (float) sides;
    c[i] = cos(theta);
    s[i] = sin(theta);
  }
  
  // bottom end cap
  normal (0.0, -1.0, 0.0);
  float bot_r = radius * botr;
  for (i = 0; i < sides; i++) {
    ii = (i+1) % sides;
    beginShape(TRIANGLES);
    vertex (c[ii] * bot_r, 0.0, s[ii] * bot_r);
    vertex (c[i] * bot_r, 0.0, s[i] * bot_r);
    vertex (0.0, 0.0, 0.0);
    endShape();
  }
  
  // top end cap
  float top_r = radius * topr;
  normal (0.0, 1.0, 0.0);
  for (i = 0; i < sides; i++) {
    ii = (i+1) % sides;
    beginShape(TRIANGLES);
    vertex (c[ii] * top_r, height, s[ii] * top_r);
    vertex (c[i] * top_r, height, s[i] * top_r);
    vertex (0.0, height, 0.0);
    endShape();
  }
  
  // main body of cylinder
  for (i = 0; i < sides; i++) {
    ii = (i+1) % sides;
    beginShape();
    normal (c[i], 0.0, s[i]);
    vertex (c[i] * bot_r, 0.0, s[i] * bot_r);
    vertex (c[i] * top_r, height, s[i] * top_r);
    normal (c[ii], 0.0, s[ii]);
    vertex (c[ii] * top_r, height, s[ii] * top_r);
    vertex (c[ii] * bot_r, 0.0, s[ii] * bot_r);
    endShape(CLOSE);
  }
}

//translate (2 * sin(2 * time), 0, 0);  // translate based on time
//rotate (-time, 1.0, 0.0, 0.0);      // rotate based on time
// diffuse (fill), ambient and specular material properties

void hottubduck() {
  //cylinder
  pushMatrix();
  fill (255, 200, 0);        // "fill" sets both diffuse and ambient color
  ambient (50, 50, 50);      // set ambient color
  specular (150, 155, 155);  // set specular color
  shininess (5.0);           // set specular exponent
  translate (0, 10, 0);
  translate (0.0, -20.0, 0.0);
  cylinder (20, 20.0, 32, 1, 0.7);
  popMatrix();


  // body
  pushMatrix();
  fill (255, 200, 0);
  ambient (50, 50, 50);
  specular (155, 155, 155);
  shininess (5.0);
  sphereDetail (40);
  translate(0, 8, 0);
  sphere (20);
  popMatrix();
  
  
  // head
  pushMatrix();
  fill (255, 200, 0);
  ambient (50, 50, 50);
  specular (155, 155, 155);
  shininess (5.0);
  sphereDetail (40);
  translate(0, -8, 0);
  sphere (15);
  popMatrix();
  
  
  // left eye
  pushMatrix();
  fill (255, 255, 255);
  ambient (50, 50, 50);
  specular (155, 155, 155);
  shininess (5.0);
  translate(-6, -13, 11);
  rotateZ(PI/7);
  rotateY(-PI/8);
  sphere(3);
  popMatrix();
  
  // left eye pupil
  pushMatrix();
  fill (0, 0, 0);
  ambient (50, 50, 50);
  specular (155, 155, 155);
  shininess (5.0);
  sphereDetail (40);
  rotateX(PI/50);
  rotateY(-PI/50);
  translate(-6, -13, 14.5);
  sphere(1);
  popMatrix();
  
  
  // right eye
  pushMatrix();
  fill (255, 255, 255);
  ambient (50, 50, 50);
  specular (155, 155, 155);
  shininess (5.0);
  translate(6, -13, 11);
  rotateZ(-PI/7);
  rotateY(PI/8);
  sphere(3);
  popMatrix();
  
  // right eye pupil
  pushMatrix();
  fill (0, 0, 0);
  ambient (50, 50, 50);
  specular (155, 155, 155);
  shininess (5.0);
  sphereDetail (40);
  rotateX(PI/50);
  rotateY(PI/50);
  translate(6, -13, 14.5);
  sphere (1);
  popMatrix();
  
  
  // mouth
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (0, -11.5, 15);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (0, -11, 15);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (0, -10, 15);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (0, -10, 16);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (0, -10, 16.5);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (0, -9, 15);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (1, -11, 15);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (1, -10, 15);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (1, -10, 16);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (1, -10, 16.5);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (1.5, -10.5, 15);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (0.5, -9, 15);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (1.5, -9, 15);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (2, -9.5, 16);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (-1, -11, 15);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (-1.5, -10, 15);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (-1, -10, 15);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (-1, -10, 16);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (-1, -10, 16.5);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (-1.5, -10.5, 15);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (-0.5, -9, 15);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (-1.5, -9, 15);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (140, 50, 20);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (-1, -9, 15.5);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (140, 50, 20);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (0, -9, 15.5);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (140, 50, 20);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (1, -9, 15.5);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (140, 50, 20);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (-1.5, -9.5, 15.5);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (140, 50, 20);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (1.5, -9.5, 15.5);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (1.5, -8.5, 17);
  box (0.5);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (-1.5, -8.5, 17);
  box (0.5);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (0, -8.5, 17);
  box (0.5);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (0.5, -8.5, 17);
  box (0.5);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (-0.5, -8.5, 17);
  box (0.5);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (-1, -8.5, 17);
  box (0.5);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (1, -8.5, 17);
  box (0.5);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (-2, -9.5, 16);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (-2, -10, 15);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (-2, -10, 16);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (-2, -10, 16.5);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (-1, -10, 17);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (0, -10, 17);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (1, -10, 17);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (2, -10, 15);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (2, -10, 16);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (2, -10, 16.5);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (2, -10, 16.5);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (3, -10, 15);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (3, -10, 16);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (-3, -10, 15);
  box (1);
  popMatrix();
  
  pushMatrix();
  fill (255, 165, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (-3, -10, 16);
  box (1);
  popMatrix();
  
  
  //nostrils
  pushMatrix();
  fill (0, 0, 0);
  ambient (50, 50, 50);
  specular (155, 155, 155);
  shininess (5.0);
  sphereDetail (40);
  translate(-0.5, -11, 15.5);
  sphere (0.3);
  popMatrix();
  
  pushMatrix();
  fill (0, 0, 0);
  ambient (50, 50, 50);
  specular (155, 155, 155);
  shininess (5.0);
  sphereDetail (40);
  translate(0.5, -11, 15.5);
  sphere (0.3);
  popMatrix();
  
  
  // towel
  pushMatrix();
  fill (255, 255, 255);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (0, -20.5, 4);
  rotateX(-PI/10);
  box(5);
  popMatrix();
  
  pushMatrix();
  fill (255, 255, 255);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (-2, -20.5, 4);
  rotateX(-PI/10);
  box(5);
  popMatrix();
  
  pushMatrix();
  fill (255, 255, 255);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (2, -20.5, 4);
  rotateX(-PI/10);
  box(5);
  popMatrix();
   
   
  // right wing
  pushMatrix();
  fill (255, 200, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (10, 5, 6);
  sphere(10);
  popMatrix();
  
  // left wing
  pushMatrix();
  fill (255, 200, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate (-10, 5, 6);
  sphere(10);
  popMatrix();
  
  
  // right foot
  pushMatrix();
  fill (255, 140, 0);
  ambient (50, 50, 50);
  shininess (5.0); 
  translate (10, 24.0, 5);
  rotateZ(PI/2);
  rotateY(PI/8);
  rotateX(PI/2);
  cylinder (1, 3, 32, 1, 1);
  popMatrix();
  
  pushMatrix();
  ambient (50, 50, 50);
  shininess (5.0);
  translate (10, 25.0, 7.5);
  sphere(1);
  popMatrix();
  
  pushMatrix();
  fill (255, 140, 0);
  ambient (50, 50, 50);
  shininess (5.0);
  translate (11, 23.0, 5);
  rotateZ(PI/2);
  rotateY(PI/8);
  rotateX(PI/1.5);
  cylinder (1, 3.5, 32, 1, 1);
  popMatrix();
  
  pushMatrix();
  fill (255, 140, 0); 
  ambient (50, 50, 50);
  shininess (5.0);
  translate (12.6, 24, 7.5);
  sphere(1);
  popMatrix();
  
  pushMatrix();
  fill (255, 140, 0);
  ambient (50, 50, 50);
  shininess (5.0);
  translate (12, 22, 4);
  rotateZ(PI/2);
  rotateY(PI/8);
  rotateX(PI/1.3);
  cylinder (1, 4, 32, 1, 1);
  popMatrix();
  
  pushMatrix();
  fill (255, 140, 0);
  ambient (50, 50, 50);
  shininess (5.0);
  translate (15, 23, 6.5);
  sphere(1);
  popMatrix();
  
  
  // left foot
  pushMatrix();
  fill (255, 140, 0);
  ambient (50, 50, 50);
  translate (-10, 24.0, 10);
  rotateZ(-PI/2);
  rotateY(-PI/8);
  rotateX(-PI/2);
  cylinder (1, 3, 32, 1, 1);
  popMatrix();
  
  pushMatrix();
  fill (255, 140, 0);
  ambient (50, 50, 50);
  shininess (5.0); 
  translate (-10, 24, 10);
  sphere(1);
  popMatrix();
  
  pushMatrix();
  fill (255, 140, 0);
  ambient (50, 50, 50);
  shininess (5.0); 
  translate (-11.5, 22.5, 7);
  rotateZ(PI/2);
  rotateY(PI/8);
  rotateX(PI/2.5);
  cylinder (1, 3, 32, 1, 1);
  popMatrix();
  
  pushMatrix();
  fill (255, 140, 0);
  ambient (50, 50, 50);
  shininess (5.0); 
  translate (-12.4, 23.7, 9.7);
  sphere(1);
  popMatrix();
  
  pushMatrix();
  fill (255, 140, 0);
  ambient (50, 50, 50);
  shininess (5.0); 
  translate (-12.5, 22, 5.5);
  rotateZ(PI/2);
  rotateY(PI/8);
  rotateX(PI/4);
  cylinder (1, 4, 32, 1, 1);
  popMatrix();
  
  pushMatrix();
  fill (255, 140, 0);
  ambient (50, 50, 50);
  shininess (5.0); 
  translate (-15, 23, 8);
  sphere(1);
  popMatrix();
  
  // butt X
  pushMatrix();
  fill (0, 0, 0);
  ambient (50, 50, 50);
  shininess (5.0); 
  translate (-0.4, 21, -15);
  //rotateZ(PI/6);
  rotateY(PI/5);
  rotateX(PI/4);
  cylinder (0.2, 2, 32, 1, 1);
  popMatrix();
  
  pushMatrix();
  fill (0, 0, 0);
  ambient (50, 50, 50);
  shininess (5.0); 
  translate (0.4, 21, -15);
  //rotateZ(PI/6);
  rotateY(-PI/5);
  rotateX(PI/4);
  cylinder (0.2, 2, 32, 1, 1);
  popMatrix();
}