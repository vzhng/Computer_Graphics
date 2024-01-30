void setup() {
  size(800,800);
}

boolean first = true;
void draw() {
  background (255, 255, 255);
  float theta = PI/5;
  pentagon(width / 2, height / 2, width / 4, 6, theta);
}

void pentagon(float x, float y, float r, int pentas, float theta) {
  if (pentas == 0) {
    return;
  }
  float d = (1 + mouseX/800.0) * r; //translate left & right
  float rc = r * (((800.0 - mouseY)/800.0) * 0.5); //scale up & down
  
  //make a pentagon: find its 5 vertices
  beginShape();
  for (float i = 0; i <= 5; i++) {
    if (pentas % 2 == 0) {
      float theta1 = 2 * PI * (i/(float)6);
      float xp = x + r * cos(theta1);
      float yp = y + r * sin(theta1);
      vertex(xp, yp);
      fill(255,192,200);
    } else {
      float theta1 = 2 * PI * (i/(float)5);
      float xp = x + r * sin(theta1);
      float yp = y + r * cos(theta1);
      vertex(xp, yp);
      fill(219,112,147);
    }
  }
  endShape(CLOSE);
  
  //make its 5 satellite pentagons: find the satellite pentas' centers
  for (float j = 0; j <= 5; j++) {
    float theta2 = (2 * j * PI/5) + theta;
    float xc = x + d * sin(theta2);
    float yc = y + d * -cos(theta2);
    pentagon(xc, yc, rc, pentas - 1, theta + PI/5);
  }
}