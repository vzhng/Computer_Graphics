//Veronica Zhang

// Matrix Stack Library - You will write this!

// You should modify the routines below to complete the assignment.
// Feel free to define any classes, global variables, and helper routines that you need.

//float[][] ctm;
ArrayList<float[][]> stack = new ArrayList<>();

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

void Print_CTM()
{
  //only loop the top matrix on stack 
  float[][] top = stack.get(stack.size() - 1);
  for (int i = 0; i < 4; i++) {
    for (int j = 0; j < 4; j++) {
      System.out.print(top[i][j] + " ");
    }
    System.out.print("\n");
  }
  System.out.print("\n");
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

void multiply(float[][] m1, float[][] m2)
{
  //matrix multiplication
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