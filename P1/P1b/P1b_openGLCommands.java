// Dummy routines for OpenGL commands.

// You should modify the routines below to complete the assignment.
// Feel free to define any classes, global variables, and helper routines that you need.

ArrayList<float[][]> stack = new ArrayList();
ArrayList<float[]> vertices = new ArrayList();

void Init_Matrix()
{
  stack.clear();
  float[][] ctm = {
    {1,0,0,0},
    {0,1,0,0},
    {0,0,1,0},
    {0,0,0,1}
  };
  stack.add(ctm);
}

void multiply(float[][] m1, float[][] m2)
{
  stack.remove(stack.get(stack.size() - 1));
  float[][] m = {{0,0,0,0}, {0,0,0,0}, {0,0,0,0}, {0,0,0,0}};
  for (int i = 0; i < 4; i++) {
    for (int j = 0; j < 4; j++) {
      for (int k = 0; k < 4; k++) {
        m[i][j] += m1[i][k] * m2[k][j];
      }
    }
  }
  stack.add(m);
}

void Push_Matrix()
{
  float[][] top = stack.get(stack.size() - 1);
  stack.add(top);
}

void Pop_Matrix()
{
  if (stack.size() <= 1) {
    System.out.println("Error: cannot pop the matrix stack.");
  } else {
    stack.remove(stack.get(stack.size() - 1));
  }
}

void Translate(float x, float y, float z)
{
  float[][] t = {{1,0,0,x},{0,1,0,y},{0,0,1,z},{0,0,0,1}};
  multiply(stack.get(stack.size() - 1), t);
}

void Scale(float x, float y, float z)
{
  float[][] s = {{x,0,0,0},{0,y,0,0},{0,0,z,0},{0,0,0,1}};
  multiply(stack.get(stack.size() - 1), s);
}

void RotateX(float theta)
{
  float radian = theta * (PI/180);
  float c = (float) Math.cos(radian);
  float s = (float) Math.sin(radian);
  float[][] rx = {{1,0,0,0},{0,c,-s,0},{0,s,c,0},{0,0,0,1}};
  multiply(stack.get(stack.size() - 1), rx);
}

void RotateY(float theta)
{
  float radian = theta * (PI/180);
  float c = (float) Math.cos(radian);
  float s = (float) Math.sin(radian);
  float[][] ry = {{c,0,s,0},{0,1,0,0},{-s,0,c,0},{0,0,0,1}};
  multiply(stack.get(stack.size() - 1), ry);
}

void RotateZ(float theta)
{
  float radian = theta * (PI/180);
  float c = (float) Math.cos(radian);
  float s = (float) Math.sin(radian);
  float[][] rz = {{c,-s,0,0},{s,c,0,0},{0,0,1,0},{0,0,0,1}};
  multiply(stack.get(stack.size() - 1), rz);
}

int projection = 0;
float fov = 0;
void Perspective(float f, float near, float far) { //f = field of view angle/k
  projection = 2;
  fov = f;
}

float left = 0;
float right = 0;
float top = 0;
float bottom = 0;
void Ortho(float l, float r, float b, float t, float n, float f) {
  projection = 1;
  left = l;
  right = r;
  bottom = b;
  top = t;
}

void Begin_Shape() {
  vertices.clear();
}

void Vertex(float x, float y, float z) {
  float[] point = {x,y,z,1};
  transformation(stack.get(stack.size() - 1), point);
}

void End_Shape() {
  while (vertices.size() != 0) {
    float[] p1 = vertices.get(vertices.size() - 1);
    vertices.remove(vertices.get(vertices.size() - 1));    
    float[] p2 = vertices.get(vertices.size() - 1);
    vertices.remove(vertices.get(vertices.size() - 1));
    
    float x1 = p1[0];
    float y1 = p1[1];
    float z1 = p1[2];
    
    float x2 = p2[0];
    float y2 = p2[0];
    float z2 = p2[2];
     
    if (projection == 1) {
      x1 = (p1[0]-left) * (width/(right-left));
      y1 = (p1[1]-left) * (width/(right-left));
      z1 = (p1[2]-left) * (width/(right-left));
      
      x2 = (p2[0]-left) * (width/(right-left));
      y2 = (p2[1]-left) * (width/(right-left));
      z2 = (p2[2]-left) * (width/(right-left));
      
    } else if (projection == 2) {
      float k = (float) Math.tan((fov * (PI/180))/2);
      
      float ax = p1[0]/Math.abs(p1[2]);
      float ay = p1[1]/Math.abs(p1[2]);
      x1 = (ax+k) * (height/(2*k));
      y1 = (ay+k) * (height/(2*k));
      
      float bx = p2[0]/Math.abs(p2[2]);
      float by = p2[1]/Math.abs(p2[2]);
      x2 = (bx+k) * (height/(2*k));
      y2 = (by+k) * (height/(2*k));
    }
    line(x1, height - y1, x2, height - y2);
  }
}

void transformation(float[][] ctm, float[] p) {
  float[] m = {0, 0, 0, 0};
  for (int i = 0; i < 4; i++) {
    for (int k = 0; k < 4; k++) {
      m[i] += ctm[i][k] * p[k];
    }
  }
  vertices.add(m);
}