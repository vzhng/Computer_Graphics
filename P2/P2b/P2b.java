// Scene Example //
//SPIRITED AWAY: HOTTUB DUCKS

float time = 0;  // keep track of the "time"
float timer1 = 0;
boolean up1 = true;
float timer2 = 0;
boolean up2 = false;
float timer3 = 0;
boolean up3 = true;
float timer4 = 0;
boolean up4 = true;
float tick = -200;
float tilt = PI/20;
float jump = 0;
float jump_forward = -50;
float water = 110;
float s = 1;

void setup() {
  size (600, 600, P3D);  // use 3D here!
  background(0);
  noStroke();  
}

// Draw a scene with a box, cylinder and sphere
void draw() {
  resetMatrix();  // set the transformation matrix to the identity (important!)
  background(236, 213, 183, 100);

  // set up for perspective projection
  perspective (PI * 0.333, 1.0, 0.01, 1000.0);

  // place the camera in the scene
  //camera (-60, -70, 100, 20, 0.0, 15, 0.0, 1.0, 0.0); //scene
  camera (0, -30, -140, 0, 0, -200, 0.0, 0, 1.0); //face front
  //camera (0, -50, 100, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0); //top side view

  // create an ambient light source
  // larger # = brighter
  ambientLight (102, 102, 102);

  // create two directional light sources
  //lightSpecular (204, 204, 204);
  directionalLight(200, 200, 200, 0, 1, 0); //up
  directionalLight (100, 100, 100, 1, 0, 0);
  directionalLight(50, 50, 50, 1, 1, 1);
  directionalLight(200, 200, 200, -50, -50, -50);
  directionalLight(50, 50, 50, 0, 0, -50);
  
  if (time < 10) {
    camera (0, -30, -140, 0, 0, -200, 0.0, 0, 1.0); //face front
    //directionalLight(50, 50, 50, 0, 0, -50);
  } else if (time >= 10 && time < 30) {
    camera (0, -30, -140 + 10*time, 0, 0, -200, 0.0, 0, 1.0);
    //camera (-60, -70, 100, 20, 0.0, 15, 0.0, 1.0, 0.0); //scene
  } else {
    camera (-60, -70, 100, 20, 0.0, 15, 0.0, 1.0, 0.0);
  }
  
  
  //outside duck
  pushMatrix();
  if (time < 30) {
    translate(0, 0, -200);
    if (time > 3) {
      hottubduck(1,1);
      
      pushMatrix();
      translate(-10,0,100);
      pushMatrix();
      exclamation();
      popMatrix();
      pushMatrix();
      translate(8, 0, 0);
      exclamation();
      popMatrix();
      popMatrix();
    } else {
      hottubduck(1,0);
    }
  } else if (time >= 30 && tick < -50) {
    if (tilt == PI/20) {
      rotate(tilt = -PI/20);
      translate(0, 0, tick += 1);
    } else {
      rotate(tilt = PI/20);
      translate(0, 0, tick += 1);
    }
  } else if (time > 30 && time < 40) {
    translate(0, 0, -50);
  } else if (time >= 40 && time < 41) {
    translate(0, jump -= 5, jump_forward += 1);
  } else if (time >= 41) {
    translate(0, jump += 3, jump_forward += 2);
  }
  hottubduck(1,1);
  popMatrix();
  
  
  //water splash
  if (time > 42 && time < 43) {
    pushMatrix();
    fill (174,221,173);
    ambient (50, 50, 50);
    shininess (5.0);
    translate (1.5, water -= 7, 30);
    cylinder (10, 17, 32, 1, 1);
    popMatrix();
  } else if (time >= 42 && time < 44) {
    pushMatrix();
    fill (174,221,173);
    translate(0,-40,30);
    scale(s *= 1.2);
    sphereDetail(40);
    sphere (20);
    popMatrix();
  } else if (time > 44) {
    pushMatrix();
    fill (174,221,173);
    shininess(100.0);
    translate(0,0,30);
    sphereDetail(40);
    sphere (100);
    popMatrix();
  }
  
  
  pushMatrix();
  translate(-15,0,0);
  
  pushMatrix();
  translate(0, -25, 20);
  hottub();
  popMatrix();
 
 
  pushMatrix();
  //bob(true, 0, 1, 4, 0.1, 10);
  if (up1) {
    if (timer1 == 20) {
      up1 = false;
    }
    translate(4, timer1 * 0.1, 10);
    timer1 += 1;
  } else if (!up1) {
    if (timer1 == 0) {
      up1 = true;
    }
    translate(4, timer1 * 0.1, 10);
    timer1 -= 1;
  }
  rotateY(-PI/1.5);
  hottubduck(0,0);
  popMatrix();
  
  pushMatrix();
  translate(-5, 0, 40);
  rotateY(-time * 0.1);
  rotateX(PI/10);
  hottubduck(0,1);
  popMatrix();
 
  pushMatrix();
  //bob(true, 0, 0.5, 25, 0.1, 58);
  if (up2) {
    if (timer2 == 40) {
      up2 = false;
    }
    translate(25, timer2 * 0.05, 58);
    timer2 += 1;
  } else if (!up2) {
    if (timer2 == 0) {
      up2 = true;
    }
    translate(25, timer2 * 0.05, 58);
    timer2 -= 1;
  }
  rotateX(PI/11);
  hottubduck(0,0);
  popMatrix();
  
  pushMatrix();
  if (up3) {
    if (timer3 == 10) {
      up3 = false;
    }
    translate(50, timer3 * 0.05, 35);
    timer3 += 0.5;
  } else if (!up3) {
    if (timer3 == 0) {
      up3 = true;
    }
    translate(50, timer3 * 0.05, 35);
    timer3 -= 0.5;
  }
  rotateY(PI/2);
  hottubduck(0,1);
  popMatrix();
  
  pushMatrix();
  translate(40, -2, 10);
  rotateY(PI);
  hottubduck(0,0);
  popMatrix();
  popMatrix();
 
    
  // step forward the time
  time += 0.05;
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

void hottubduck(int towel, int beak) {
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
  if (beak == 0) {
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
    translate (2, -9.5, 15);
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
    fill (255, 165, 0);
    ambient (100, 100, 200);
    specular (0, 0, 0);
    shininess (1.0);
    translate (-1, -9, 16);
    box (1);
    popMatrix();
    
    pushMatrix();
    fill (255, 165, 0);
    ambient (100, 100, 200);
    specular (0, 0, 0);
    shininess (1.0);
    translate (0, -9, 16);
    box (1);
    popMatrix();
    
    pushMatrix();
    fill (255, 165, 0);
    ambient (100, 100, 200);
    specular (0, 0, 0);
    shininess (1.0);
    translate (1, -9, 16);
    box (1);
    popMatrix();
    
    pushMatrix();
    fill (255, 165, 0);
    ambient (100, 100, 200);
    specular (0, 0, 0);
    shininess (1.0);
    translate (-1.5, -9.5, 16);
    box (1);
    popMatrix();
    
    pushMatrix();
    fill (255, 165, 0);
    ambient (100, 100, 200);
    specular (0, 0, 0);
    shininess (1.0);
    translate (1.5, -9.5, 16);
    box (1);
    popMatrix();
    
    pushMatrix();
    fill (255, 165, 0);
    ambient (100, 100, 200);
    specular (0, 0, 0);
    shininess (1.0);
    translate (-2, -9.5, 15);
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
  } else {
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
  }
    
  
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
  if (towel == 1) {
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
  }
     
   
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
  translate(0,0,1.5);
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

void hottub() {
  pushMatrix();
  fill (94,55,31);
  ambient (50, 50, 50);
  shininess (5.0); 
  translate (20, 21, 10);
  cylinder (53, 35, 32, 1, 1);
  popMatrix();
  
  pushMatrix();
  fill (174,221,173);
  ambient (50, 50, 50);
  shininess (100.0); 
  if (up4) {
    if (timer4 == 20) {
      up4 = false;
    }
    translate (20, timer2*0.01+19, 10);
    timer4 += 0.1;
  } else if (!up4) {
    if (timer4 == 0) {
      up4 = true;
    }
    translate (20, timer4*0.01+19, 10);
    timer4 -= 0.1;
  }
  cylinder (48, 15, 32, 1, 1);
  popMatrix();
}

void exclamation() {
  pushMatrix();
  fill (255, 0, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate(50, -9, -180);
  box (5);
  popMatrix();
  
  pushMatrix();
  fill (255, 0, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate(50, -5, -180);
  box (5);
  popMatrix();
  
  pushMatrix();
  fill (255, 0, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate(50, -3, -180);
  box (5);
  popMatrix();
  
  pushMatrix();
  fill (255, 0, 0);
  ambient (100, 100, 200);
  specular (0, 0, 0);
  shininess (1.0);
  translate(50, 5, -180);
  box (5);
  popMatrix();
}