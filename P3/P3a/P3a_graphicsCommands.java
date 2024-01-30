// Dummy routines for drawing commands.
// These are for you to write.
ArrayList<float[]> vertices = new ArrayList();
ArrayList<float[]> colors = new ArrayList();

void Set_Color (float r, float g, float b) {
   float[] c = {r * 255,g * 255,b * 255};
   colors.add(c);
}

void Vertex(float x, float y, float z) {
  float[] point = {x,y,z};
  vertices.add(point);
}

void Begin_Shape() {
  vertices.clear();
}

void End_Shape() {
  float[] bottom = {500,500,500};
  float[] top = {0,0,0};
  float[] mid = new float[3];
  
  //find min, mid, max, color indices
  int tci = 0;
  int bci = 0;
  for (int i = 0; i < vertices.size(); i++) {
    if (vertices.get(i)[1] < bottom[1]) {
      bottom = vertices.get(i);
      bci = i;
    } 
    if (vertices.get(i)[1] > top[1]) {
      top = vertices.get(i);
      tci = i;
    }
  }
  int mci = 3 - tci - bci;
  mid = vertices.get(mci);
  
  float ymin = bottom[1];
  float ymax = top[1];
  
  //sorted edges (based on y)
  float[][][] edges = {
    {bottom, top},
    {top[1] <= mid[1] ? top : mid, top[1] > mid[1] ? top : mid},
    {mid[1] <= bottom[1] ? mid : bottom, mid[1] > bottom[1] ? mid : bottom}
  };
  
  //get dx
  float dx1 = top[0] - bottom[0];
  float dx2 = mid[0] - bottom[0];
  float dx3 = top[0] - mid[0];
  
  //x increments
  float xinc1 = dx1 / (top[1] - bottom[1]); 
  float xinc2 = (mid[1] - bottom[1]) != 0 ? dx2 / (mid[1] - bottom[1]) :  dx3 / (top[1] - mid[1]);
  float xinc3 = (top[1] - mid[1]) != 0 ? dx3 / (top[1] - mid[1]) :  dx2 / (mid[1] - bottom[1]);
  
  //color diffs
  //float[] cinc1 = {0.0042010285, 0.002414171, 0.00300979};
  //float[] cinc2 = {0.036087364, 0.018903518, 0.02463147};
  float[] cinc1 = new float[3];
  float[] cinc2 = new float[3];
  if (colors.size() > 1) {
    cinc1[0] = ((colors.get(tci)[0] - colors.get(bci)[0])/255);
    cinc1[1] = ((colors.get(tci)[1] - colors.get(bci)[1])/255);
    cinc1[2] = ((colors.get(tci)[2] - colors.get(bci)[2])/255);
    
    cinc2[0] = ((colors.get(mci)[0] - colors.get(bci)[0])/255);
    cinc2[1] = ((colors.get(mci)[1] - colors.get(bci)[1])/255);
    cinc2[2] = ((colors.get(mci)[2] - colors.get(bci)[2])/255);
  }
  
  //starting x-values & colors
  float x1 = ((ceil(bottom[1]) - bottom[1]) * xinc1) + bottom[0];
  float x2 = mid[1] - bottom[1] == 0 ?
  ((ceil(edges[1][0][1]) - edges[1][0][1]) * xinc2) + edges[1][0][0] : 
  ((ceil(edges[2][0][1]) - edges[2][0][1]) * xinc2) + edges[2][0][0];
   
  //deep copy colors
  float[] c1 = {
    colors.get(0)[0],
    colors.get(0)[1],
    colors.get(0)[2]
  };
  float[] c2 = {
    colors.get(0)[0],
    colors.get(0)[1],
    colors.get(0)[2]
  };
  if (colors.size() > 1) {
    float r = colors.get(bci)[0];
    float g = colors.get(bci)[1];
    float b = colors.get(bci)[2];
    c1[0] = r;
    c1[1] = g;
    c1[2] = b;
    c2[0] = r;
    c2[1] = g;
    c2[2] = b;
  }
  
  for (int y = ceil(ymin); y < ymax; y++) {
    if (colors.size() > 1) {
      c1[0] += cinc1[0];
      c1[1] += cinc1[1];
      c1[2] += cinc1[2];
      
      c2[0] += cinc2[0];
      c2[1] += cinc2[1];
      c2[2] += cinc2[2];
    }
     
    float percent = 1.0;
    for (int x = ceil(min(x1, x2)); x <= max(x1, x2); x++) {
      if (colors.size() == 1) { //only 1 color
        float r = colors.get(0)[0];
        float g = colors.get(0)[1];
        float b = colors.get(0)[2];
        set(x, height - y, color(r,g,b));
      } else { 
        setColor(c1, c2, percent, x, y);
        percent -= (1/(x2 - x1));
      }
    }
    
    x1 += xinc1;
    if (y >= mid[1] && mid[1] != top[1]) { //switch edges
      x2 += xinc3;
    } else {
      x2 += xinc2;
    }
  }
  colors.clear();
}

void setColor(float[] c1, float[] c2, float percent, int x, int y) {
  float newRed = c1[0] * percent + c2[0] * (1.0-percent);
  float newGreen = c1[1] * percent + c2[1] * (1.0-percent);
  float newBlue = c1[2] * percent + c2[2] * (1.0-percent);
  set(x, height - y, color(newRed, newGreen, newBlue));
}