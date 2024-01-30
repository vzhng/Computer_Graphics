// Routines for graphics commands (especially for shading and z-buffer). //<>// //<>//
// Most of these are for you to write.

public enum Shading { WIREFRAME, CONSTANT, FLAT, GOURAUD, PHONG }
Shading shade = Shading.CONSTANT;  // the current shading mode

// current transformation matrix and its adjoint
PMatrix3D cmat;
PMatrix3D adj;

float field_of_view = 0.0;  // non-zero value indicates perspective projection
float[][] zbuff = new float[500][500];
ArrayList<float[]> vertices = new ArrayList();
ArrayList<float[]> normals = new ArrayList();
ArrayList<float[]> colors = new ArrayList();
float[] amb = new float[3];
float[] spec = new float[3];
float p = 1;

// you should initialize your z-buffer here, and also various material color parameters
void Init_Scene() {
  
  // create the current transformation matrix, and its adjoint for transforming the normals
  cmat = new PMatrix3D();
  cmat.reset();             // sets the current transformation to the identity
    
  // calculate the adjoint of the transformation matrix
  PMatrix3D imat = cmat.get(); 
  boolean okay = imat.invert();
  if (!okay) {
    println ("matrix singular, cannot invert");
    exit();
  }
  adj = imat.get();
  adj.transpose();
  
  // initialize your z-buffer here
  for (int i = 0; i < 500; i++) {
    for (int j = 0; j < 500; j++) {
      zbuff[i][j] = -Float.MAX_VALUE;
    }
  }
  
  // set default values to material colors here
  amb[0] = 0;
  amb[1] = 0;
  amb[2] = 0;
  
  spec[0] = 0;
  spec[1] = 0;
  spec[2] = 0;
  
  p = 1;
}

void Set_Field_Of_View (float fov)
{
  field_of_view = fov;
}

void Set_Color (float r, float g, float b) {
  float[] c = {r, g, b};
  colors.add(c);
}

void Ambient_Specular (float ar, float ag, float ab, float sr, float sg, float sb, float pow) {
  amb[0] = ar;
  amb[1] = ag;
  amb[2] = ab;
  spec[0] = sr;
  spec[1] = sg;
  spec[2] = sb;
  p = pow;
}

float[] norm = new float[3];
void Normal(float nx, float ny, float nz) {
  norm[0] = nx;
  norm[1] = ny;
  norm[2] = nz;
}

float[] lp = new float[3];
float[] lc = new float[3]; 
void Set_Light (float x, float y, float z, float r, float g, float b) {
  // set light value here
  // don't forget to normaize the direction of the light vector
  float[] l = {x,y,z};
  lp = normalize_func(l);
  
  lc[0] = r;
  lc[1] = g;
  lc[2] = b;
}

void Begin_Shape() {
  vertices.clear();
}

// some of this code is provided, but you should save the resulting projected coordinates
// and surface normals in your own data structures for vertices
void Vertex(float vx, float vy, float vz) {
  
  float x,y,z;

  // transform this vertex by the current transformation matrix
  x = vx * cmat.m00 + vy * cmat.m01 + vz * cmat.m02 + cmat.m03;
  y = vx * cmat.m10 + vy * cmat.m11 + vz * cmat.m12 + cmat.m13;
  z = vx * cmat.m20 + vy * cmat.m21 + vz * cmat.m22 + cmat.m23;

  // calculate the transformed surface normal (using the adjoint)
  // note that you need to provide normal_x, normal_y and normal_z set from the Normal() command
  float nx,ny,nz;
  nx = norm[0] * adj.m00 + norm[1] * adj.m01 + norm[2] * adj.m02 + adj.m03;
  ny = norm[0] * adj.m10 + norm[1] * adj.m11 + norm[2] * adj.m12 + adj.m13;
  nz = norm[0] * adj.m20 + norm[1] * adj.m21 + norm[2] * adj.m22 + adj.m23;
  
  float xx = x;
  float yy = y;
  float zz = z;
  
  // field of view greater than zero means use perspective projection
  if (field_of_view > 0) {
    float theta = field_of_view * PI / 180.0;  // convert to radians
    float k = tan (theta / 2);
    xx = x / abs(z);
    yy = y / abs(z);
    xx = (xx + k) * width  / (2 * k);
    yy = (yy + k) * height / (2 * k);
    zz = z;
  }
  
  // xx,yy,zz are screen space coordinates of the vertex, after transformation and projection
  // !!!! store xx,yy,zz and nx,ny,nz somewhere for you to use for rasterization and shading !!!!
  float[] point = {xx,yy,zz};
  vertices.add(point);
  
  float[] n = {nx,ny,nz};
  normals.add(n);
}

// rasterize a triangle
void End_Shape() {
  
  // make wireframe (line) drawing if that is the current shading mode
  if (shade == Shading.WIREFRAME) {
    stroke (0, 0, 0);
    strokeWeight (2.0);
    // draw lines between your stored vertices (adjust to your data structures)
    line (vertices.get(0)[0], height - vertices.get(0)[1], vertices.get(1)[0], height - vertices.get(1)[1]);
    line (vertices.get(0)[0], height - vertices.get(0)[1], vertices.get(2)[0], height - vertices.get(2)[1]);
    line (vertices.get(1)[0], height - vertices.get(1)[1], vertices.get(2)[0], height - vertices.get(2)[1]);
    return;
  }
  
  // this is where you should add your rasterization code from Project 3A
  float[] bottom = {500.0,500.0,500.0};
  float[] top = {0.0,0.0,0.0};
  float[] mid = new float[3];
  
  //find min, mid, max vertices
  int ti = 0;
  int bi = 0;
  for (int i = 0; i < vertices.size(); i++) {
    if (vertices.get(i)[1] < bottom[1]) {
      bottom = vertices.get(i);
      bi = i;
    } 
    if (vertices.get(i)[1] > top[1]) {
      top = vertices.get(i);
      ti = i;
    }
  }
  int mi = 3 - ti - bi;
  mid = vertices.get(mi);
  
  float ymin = bottom[1];
  float ymax = top[1];
  
  
  //x increments
  float dx1 = (top[0] - bottom[0]) / (top[1] - bottom[1]);
  float dx2 = (mid[0] - bottom[0]) / (mid[1] - bottom[1]);
  float dx3 = (top[0] - mid[0]) / (top[1] - mid[1]); //for switching
  if (int(mid[1]) - int(bottom[1]) == 0) { //
    dx2 = 0;
  }
  if (int(top[1]) - int(mid[1]) == 0) { //
    dx3 = 0;
  }

  //starting x-values
  float x1 = (ceil(bottom[1]) - bottom[1]) * (dx1) + bottom[0];
  float x2 = (ceil(bottom[1]) - bottom[1]) * (dx2) + bottom[0];
  if (dx2 == 0) {
    x2 = (ceil(mid[1]) - mid[1]) * (dx2) + mid[0];
  }

  //starting z-values?
  float z1 = bottom[2];
  float z2 = bottom[2];
  if (Math.abs(mid[1] - bottom[1]) < 0.01) {
    z2 = mid[2];
  }
  
  //z increments
  float dz1 = (top[2] - bottom[2]);
  float dz2 = (mid[2] - bottom[2]); 
  if (Math.abs(mid[1] - bottom[1]) < 0.01) {
    dz2 = (top[2] - mid[2]);
  }
  
  
  //SHADING
  //for flat
  float[] n = new float[3];
  
  //for Gouraud
  float[] cb = new float[3];
  float[] cm = new float[3];
  float[] ct = new float[3];
  float[] cbt = new float[3];
  float[] cbm = new float[3];
  float[] cmt = new float[3];
  float[] c1 = new float[3];
  float[] c2 = new float[3];
  
  //for PHONG
  float[] nbt = new float[3];
  float[] nbm = new float[3];
  float[] nmt = new float[3];
  float[] n1 = new float[3];
  float[] n2 = new float[3];
  
  
  float[] v = {0,0,1};
  float[] h = {(v[0] + lp[0]) / 2, (v[1] + lp[1]) / 2, (v[2] + lp[2]) / 2};
  h = normalize_func(h);
  
  //normalized normals
  float[] nb = normalize_func(normals.get(bi));
  float[] nm = normalize_func(normals.get(mi));
  float[] nt = normalize_func(normals.get(ti));
  
  
  if (shade == Shading.FLAT) {
    float[] edge1 = {vertices.get(1)[0] - vertices.get(0)[0], vertices.get(1)[1] - vertices.get(0)[1], vertices.get(1)[2] - vertices.get(0)[2]};
    float[] edge2 = {vertices.get(2)[0] - vertices.get(0)[0], vertices.get(2)[1] - vertices.get(0)[1], vertices.get(2)[2] - vertices.get(0)[2]};
    
    float[] cross = crossProduct(edge1, edge2);
    n = normalize_func(cross);
    
  } else if (shade == Shading.GOURAUD) {
    //shading eq for each vertex
    cb = setColor(colors.get(colors.size() - 1), amb, lc, nb, lp, spec, h, p);
    cm = setColor(colors.get(colors.size() - 1), amb, lc, nm, lp, spec, h, p);
    ct = setColor(colors.get(colors.size() - 1), amb, lc, nt, lp, spec, h, p);
    
    //color increments (how much to add to initial vertex to get to end vertex's color)
    cbt[0] = (ct[0] - cb[0]) / (top[1] - bottom[1]);
    cbt[1] = (ct[1] - cb[1]) / (top[1] - bottom[1]);
    cbt[2] = (ct[2] - cb[2]) / (top[1] - bottom[1]);
    
    cbm[0] = (cm[0] - cb[0]) / (mid[1] - bottom[1]);
    cbm[1] = (cm[1] - cb[1]) / (mid[1] - bottom[1]);
    cbm[2] = (cm[2] - cb[2]) / (mid[1] - bottom[1]);
    
    cmt[0] = (ct[0] - cm[0]) / (top[1] - mid[1]);
    cmt[1] = (ct[1] - cm[1]) / (top[1] - mid[1]);
    cmt[2] = (ct[2] - cm[2]) / (top[1] - mid[1]);
    
    //starting colors (deep copy)
    c1[0] = cb[0] + cbt[0] * (ceil(bottom[1]) - bottom[1]);
    c1[1] = cb[1] + cbt[0] * (ceil(bottom[1]) - bottom[1]);
    c1[2] = cb[2] + cbt[0] * (ceil(bottom[1]) - bottom[1]);

    c2[0] = cb[0] + cbt[0] * (ceil(bottom[1]) - bottom[1]);
    c2[1] = cb[1] + cbt[0] * (ceil(bottom[1]) - bottom[1]);
    c2[2] = cb[2] + cbt[0] * (ceil(bottom[1]) - bottom[1]);
    
    
    if (Math.abs(mid[1] - bottom[1]) < 0.01) { //if there's horizontal edge
      c2[0] = cm[0] + cmt[0] * (ceil(mid[1]) - mid[1]);
      c2[1] = cm[1] + cmt[0] * (ceil(mid[1]) - mid[1]);
      c2[2] = cm[2] + cmt[0] * (ceil(mid[1]) - mid[1]);
    }
  } else if (shade == Shading.PHONG) {
    //dNormals
    nbt[0] = (nt[0] - nb[0]) / (top[1] - bottom[1]);
    nbt[1] = (nt[1] - nb[1]) / (top[1] - bottom[1]);
    nbt[2] = (nt[2] - nb[2]) / (top[1] - bottom[1]);
    
    nbm[0] = (nm[0] - nb[0]) / (mid[1] - bottom[1]);
    nbm[1] = (nm[1] - nb[1]) / (mid[1] - bottom[1]);
    nbm[2] = (nm[2] - nb[2]) / (mid[1] - bottom[1]);
    
    nmt[0] = (nt[0] - nm[0]) / (top[1] - mid[1]);
    nmt[1] = (nt[1] - nm[1]) / (top[1] - mid[1]);
    nmt[2] = (nt[2] - nm[2]) / (top[1] - mid[1]);
    
    //starting normals (deep copy)
    n1[0] = nb[0] + nbt[0] * (ceil(bottom[1]) - bottom[1]);
    n1[1] = nb[1] + nbt[1] * (ceil(bottom[1]) - bottom[1]);
    n1[2] = nb[2] + nbt[2] * (ceil(bottom[1]) - bottom[1]);

    n2[0] = nb[0] + nbm[0] * (ceil(bottom[1]) - bottom[1]);
    n2[1] = nb[1] + nbm[1] * (ceil(bottom[1]) - bottom[1]);
    n2[2] = nb[2] + nbm[2] * (ceil(bottom[1]) - bottom[1]);
    
    if (Math.abs(mid[1] - bottom[1]) < 0.001) { //if there's horizontal edge
      n2[0] = nm[0] + nmt[0] * (ceil(mid[1]) - mid[1]);
      n2[1] = nm[1] + nmt[1] * (ceil(mid[1]) - mid[1]);
      n2[2] = nm[2] + nmt[2] * (ceil(mid[1]) - mid[1]);
    }
    
  }
    
  
  for (int y = ceil(ymin); y <= ymax; y++) {
    //initial x values
    float xl = ceil(min(x1, x2));
    float xr = ceil(max(x1, x2));
    
    float zl = x1 < x2 ? z1 : z2;
    float zr = x1 > x2 ? z1 : z2;
    
    float[] cl = x1 < x2 ? c1 : c2;
    float[] cr = x1 > x2 ? c1 : c2;
    
    float[] nl = x1 < x2 ? n1 : n2;
    float[] nr = x1 > x2 ? n1 : n2;
   
    for (int x = ceil(min(x1, x2)); x <= max(x1, x2); x++) {
  
      float z = zl + ((x - xl)/xr) * (zr - zl);

      float r = colors.get(colors.size() - 1)[0] * 255;
      float g = colors.get(colors.size() - 1)[1] * 255; 
      float b = colors.get(colors.size() - 1)[2] * 255;
        
      if (z >= zbuff[y][x]) {
        zbuff[y][x] = z;
        if (shade == Shading.FLAT) {
          if (n[0] != 0) {
            float[] c = setColor(colors.get(colors.size() - 1), amb, lc, n, lp, spec, h, p);
            r = (c[0]) * 255;
            g = (c[1]) * 255;
            b = (c[2]) * 255;
          }
          
        } else if (shade == Shading.GOURAUD) {
          r = (cl[0] + (cr[0] - cl[0]) * (x - xl)/(xr-xl)) * 255;
          g = (cl[1] + (cr[1] - cl[1]) * (x - xl)/(xr-xl)) * 255;
          b = (cl[2] + (cr[2] - cl[2]) * (x - xl)/(xr-xl)) * 255;

        } else if (shade == Shading.PHONG) {
          float nx = (nl[0] + (nr[0] - nl[0]) * (x - xl)/(xr-xl));
          float ny = (nl[1] + (nr[1] - nl[1]) * (x - xl)/(xr-xl));
          float nz = (nl[2] + (nr[2] - nl[2]) * (x - xl)/(xr-xl));
          float[] nn = {nx, ny, nz};
          nn = normalize_func(nn);
          float[] c = setColor(colors.get(colors.size() - 1), amb, lc, nn, lp, spec, h, p);
          r = nb[0] == 0 ? r : (c[0]) * 255;
          g = nb[0] == 0 ? g : (c[1]) * 255;
          b = nb[0] == 0 ? b : (c[2]) * 255;
        }
        set(x, height - (y), color(r,g,b));
      }
    }
    
    if (shade == Shading.GOURAUD) {
      if (cl == c1) {
        cl[0] += cbt[0];
        cl[1] += cbt[1];
        cl[2] += cbt[2];
        
        if ((y >= mid[1] && Math.abs(mid[1] - top[1]) > 0.01)) { //switch edges
          cr[0] += cmt[0];
          cr[1] += cmt[1];
          cr[2] += cmt[2];  
        } else {
          cr[0] += Math.abs(mid[1] - bottom[1]) < 0.01 ? cmt[0] : cbm[0];
          cr[1] += Math.abs(mid[1] - bottom[1]) < 0.01 ? cmt[1] : cbm[1];
          cr[2] += Math.abs(mid[1] - bottom[1]) < 0.01 ? cmt[2] : cbm[2]; 
        }
      } else { //cl == c2
        cr[0] += cbt[0];
        cr[1] += cbt[1];
        cr[2] += cbt[2];
        
        if (y >= mid[1]) { //switch edges
          cl[0] += cmt[0];
          cl[1] += cmt[1];
          cl[2] += cmt[2];
        } else {
          cl[0] += Math.abs(mid[1] - bottom[1]) < 0.01 ? cmt[0] : cbm[0];
          cl[1] += Math.abs(mid[1] - bottom[1]) < 0.01 ? cmt[1] : cbm[1];
          cl[2] += Math.abs(mid[1] - bottom[1]) < 0.01 ? cmt[2] : cbm[2];    
        }
      }
      
    } else if (shade == Shading.PHONG) {
      if (nl == n1) {
        nl[0] += nbt[0];
        nl[1] += nbt[1];
        nl[2] += nbt[2];
        
        if ((y >= mid[1] && Math.abs(mid[1] - top[1]) > 0.01)) { //switch edges
          nr[0] += nmt[0];
          nr[1] += nmt[1];
          nr[2] += nmt[2];  
        } else {
          nr[0] += Math.abs(mid[1] - bottom[1]) < 0.01 ? nmt[0] : nbm[0];
          nr[1] += Math.abs(mid[1] - bottom[1]) < 0.01 ? nmt[1] : nbm[1];
          nr[2] += Math.abs(mid[1] - bottom[1]) < 0.01 ? nmt[2] : nbm[2];   
        }
      } else { 
        nr[0] += nbt[0];
        nr[1] += nbt[1];
        nr[2] += nbt[2];
        
        if ((y >= mid[1] && Math.abs(mid[1] - top[1]) > 0.01)) { //switch edges
          nl[0] += nmt[0];
          nl[1] += nmt[1];
          nl[2] += nmt[2];  
        } else {
          nl[0] += Math.abs(mid[1] - bottom[1]) < 0.01 ? nmt[0] : nbm[0];
          nl[1] += Math.abs(mid[1] - bottom[1]) < 0.01 ? nmt[1] : nbm[1];
          nl[2] += Math.abs(mid[1] - bottom[1]) < 0.01 ? nmt[2] : nbm[2];   
        }
      }
    }
    
    zl += dz1 == 0 ? 0 : (1/dz1);
    zr += dz2 == 0 ? 0 : (1/dz2);
    
    x1 += dx1;
    if ((y >= mid[1] && Math.abs(mid[1] - top[1]) > 0.01)) { //switch edges
      x2 += dx3;
    } else {
      x2 += dx2;
    }
  }
  colors.clear();
  normals.clear();
}

// set the current transformation matrix and its adjoint
void Set_Matrix (
float m00, float m01, float m02, float m03,
float m10, float m11, float m12, float m13,
float m20, float m21, float m22, float m23,
float m30, float m31, float m32, float m33)
{
  cmat.set (m00, m01, m02, m03, m10, m11, m12, m13,
            m20, m21, m22, m23, m30, m31, m32, m33);

  // calculate the adjoint of the transformation matrix12
  PMatrix3D imat = cmat.get(); 
  boolean okay = imat.invert();
  if (!okay) {
    println ("matrix singular, cannot invert");
    exit();
  }
  adj = imat.get();
  adj.transpose();
}


float[] crossProduct(float[] v1, float[] v2) {
  float x = v1[1] * v2[2] - v1[2] * v2[1];
  float y = v1[2] * v2[0] - v1[0] * v2[2];
  float z = v1[0] * v2[1] - v1[1] * v2[0];
  float[] c = {x,y,z};
  return c;
}

float dotProduct(float[] n, float[] l) {
  return n[0] * l[0] + n[1] * l[1] + n[2] * l[2];
}

float[] setColor(float[] cr, float[] ca, float[] cl, float[] n, float[] l, float[] cs, float[] h, float pow) {
  float r = cr[0] * (ca[0] + cl[0] * dotProduct(n, l)) + cl[0] * cs[0] * (float) Math.pow(dotProduct(h, n), pow);
  float g = cr[1] * (ca[1] + cl[1] * dotProduct(n, l)) + cl[1] * cs[1] * (float) Math.pow(dotProduct(h, n), pow);
  float b = cr[2] * (ca[2] + cl[2] * dotProduct(n, l)) + cl[2] * cs[2] * (float) Math.pow(dotProduct(h, n), pow);
  float[] c = {r,g,b};
  return c;
}

float[] normalize_func(float[] v) {
  float magnitude = (float) Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
  if (magnitude == 0) {
    float[] n = {0, 0, 0};
    return n;
  }
  float x = v[0] / magnitude;
  float y = v[1] / magnitude;
  float z = v[2] / magnitude;
  float[] n = {x,y,z};
  return n;
}