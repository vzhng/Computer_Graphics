// Sample code for starting the subdivision project //<>//

// parameters used for object rotation by mouse
float mouseX_old = 0;
float mouseY_old = 0;
PMatrix3D rot_mat = new PMatrix3D();

ArrayList<PVector> vertices = new ArrayList();
ArrayList<Integer> geometry = new ArrayList();
ArrayList<Integer> opposites = new ArrayList();
int numFaces = 0;

int currIndex = 0;
PVector sc = new PVector();
boolean gouraud = false;
boolean square = true;

// initialize stuff
void setup() {
  size (800, 800, OPENGL);
}

// Draw the scene
void draw() {
  
  background (170, 170, 255);
  
  // set up for perspective projection
  perspective (PI * 0.333, 1.0, 0.01, 1000.0);
  
  // place the camera in the scene
  camera (0.0, 0.0, 5.0, 0.0, 0.0, -1.0, 0.0, 1.0, 0.0);
    
  // create an ambient light source
  ambientLight (52, 52, 52);
  
  // create two directional light sources
  lightSpecular (204, 204, 204);
  directionalLight (102, 102, 102, -0.7, -0.7, -1);
  directionalLight (152, 152, 152, 0, 0, -1);
  
  pushMatrix();
  
  applyMatrix (rot_mat);  // rotate object using the global rotation matrix
  
  ambient (200, 200, 200);
  specular(0, 0, 0);
  shininess(1.0);
  
  stroke (0, 0, 0);
  fill(200, 200, 200);

  // THIS IS WHERE YOU SHOULD DRAW THE MESH
 
  if (square) {
    beginShape();
    normal (0.0, 0.0, 1.0);
    vertex (-1.0, -1.0, 0.0);
    vertex ( 1.0, -1.0, 0.0);
    vertex ( 1.0,  1.0, 0.0);
    vertex (-1.0,  1.0, 0.0);
    stroke(0);
    endShape(CLOSE);
  } else {
  
    for (int i = 0; i < geometry.size(); i++) { 
      int x = geometry.get(i);
      int y = geometry.get(getNext(i));
      int z = geometry.get(getPrevious(i));
      
      PVector v = vertices.get(x); //current corner
      PVector vn = vertices.get(y); //next corner
      PVector vp = vertices.get(z); //prev corner
      
      //HERE
      
      if (gouraud) {
        beginShape();
        PVector n = getVertexNormals(x);
        normal (n.x, n.y, n.z);
        vertex (v.x, v.y, v.z);
        
        n = getVertexNormals(y);
        normal (n.x, n.y, n.z);
        vertex (vn.x, vn.y, vn.z);
        
        n = getVertexNormals(z);
        normal (n.x, n.y, n.z);
        vertex (vp.x, vp.y, vp.z);
        
        noStroke();
        fill(255);
        endShape(CLOSE); 
      } else {
        beginShape();
        PVector n = normal(vertices.get(x), vertices.get(y), vertices.get(z));
        normal (n.x, n.y, n.z);
        vertex (v.x, v.y, v.z);
        vertex (vn.x, vn.y, vn.z);
        vertex (vp.x, vp.y, vp.z);
        stroke(0);
        fill(255);
        endShape(CLOSE);
      }
    }
    
    PVector v = vertices.get(geometry.get(currIndex)); //current corner
    PVector vn = vertices.get(geometry.get(getNext(currIndex))); //next corner
    PVector vp = vertices.get(geometry.get(getPrevious(currIndex))); //prev corner
    
    sc = PVector.mult(v, 0.8).add(PVector.mult(vn, 0.1)).add(PVector.mult(vp, 0.1));
    
    pushMatrix();
    fill(255, 0, 0);
    noStroke();
    translate(sc.x, sc.y, sc.z);
    sphere(0.04);
    popMatrix();
  }
  popMatrix();
}

// handle keyboard input
void keyPressed() {
  
  if (key == '1') {
    square = false;
    vertices.clear();
    geometry.clear();
    opposites.clear();
    read_mesh ("tetra.ply", 1.5);
  }
  else if (key == '2') {
    square = false;
    vertices.clear();
    geometry.clear();
    opposites.clear();
    read_mesh ("octa.ply", 2.5);
  }
  else if (key == '3') {
    square = false;
    vertices.clear();
    geometry.clear();
    opposites.clear();
    read_mesh ("icos.ply", 2.5);
  }
  else if (key == '4') {
    square = false;
    vertices.clear();
    geometry.clear();
    opposites.clear();
    read_mesh ("star.ply", 1.0);
  }
  else if (key == '5') {
    square = false;
    vertices.clear();
    geometry.clear();
    opposites.clear();
    read_mesh ("torus.ply", 1.6);
  }
  else if (key == 'n') { //next corner operation
    if (!square) {
      currIndex = getNext(currIndex);
    }
  }
  else if (key == 'p') { //previous corner operation
    if (!square) {
      currIndex = getPrevious(currIndex);
    }
  }
  else if (key == 'o') { //opposite corner operation
    if (!square) {
      currIndex = opposites.get(currIndex);
    }
  }
  else if (key == 's') { //swing corner operation
    if (!square) {
      currIndex = getNext(opposites.get(getNext(currIndex)));
    }
  }
  else if (key == 'f') { //flat shading, with black edges
      gouraud = false;
  }
  else if (key == 'g') { //gouraud shading
    if (!square) {
      gouraud = true;
    }
  }
  else if (key == 'd') { //subdivide mesh
    if (!square) {
      subdivide();
    }
  }
  else if (key == 'q') { //quit program
    exit();
  }
}

// Read polygon mesh from .ply file
//
// You should modify this routine to store all of the mesh data
// into a mesh data structure instead of printing it to the screen.
void read_mesh (String filename, float scale_value)
{
  String[] words;

  String lines[] = loadStrings(filename);

  words = split (lines[0], " ");
  int num_vertices = int(words[1]);
  //println ("number of vertices = " + num_vertices);

  words = split (lines[1], " ");
  int num_faces = int(words[1]);
  numFaces = num_faces;
  //println ("number of faces = " + num_faces);

  // read in the vertices
  for (int i = 0; i < num_vertices; i++) {
    words = split (lines[i+2], " ");
    float x = float(words[0]) * scale_value;
    float y = float(words[1]) * scale_value;
    float z = float(words[2]) * scale_value;
    
    PVector v = new PVector(x,y,z);
    vertices.add(v);
    //println ("vertex = " + x + " " + y + " " + z);
  }

  // read in the faces
  for (int i = 0; i < num_faces; i++) {
    
    int j = i + num_vertices + 2;
    words = split (lines[j], " ");
    
    int nverts = int(words[0]);
    if (nverts != 3) {
      println ("error: this face is not a triangle.");
      exit();
    }
    
    int index1 = int(words[1]);
    int index2 = int(words[2]);
    int index3 = int(words[3]);
    geometry.add(index1);
    geometry.add(index2);
    geometry.add(index3);
    
    //println("faces " + index1 + " " + index2 + " " + index3);
  }
  opposites = oppositeTable();
}

// remember old mouse position
void mousePressed()
{
  mouseX_old = mouseX;
  mouseY_old = mouseY;
}

// modify rotation matrix when mouse is dragged
void mouseDragged()
{
  if (!mousePressed)
    return;
  
  float dx = mouseX - mouseX_old;
  float dy = mouseY - mouseY_old;
  dy *= -1;

  float len = sqrt (dx*dx + dy*dy);
  if (len == 0)
      len = 1;
  
  dx /= len;
  dy /= len;
  PMatrix3D rmat = new PMatrix3D();
  rmat.rotate (len * 0.005, dy, dx, 0);
  rot_mat.preApply (rmat);

  mouseX_old = mouseX;
  mouseY_old = mouseY;
}

PVector normal(PVector v1, PVector v2, PVector v3) {
  PVector edge1 = PVector.sub(v2, v1);
  PVector edge2 = PVector.sub(v3, v1);
  PVector cross = edge1.cross(edge2);
  return cross.normalize();
}

PVector getVertexNormals(int i) {
  PVector adj = new PVector(0, 0, 0);
  
  for (int j = 0; j < geometry.size(); j += 3) {
    int x = geometry.get(j);
    int y = geometry.get(getNext(j));
    int z = geometry.get(getPrevious(j));
    
    if (x == i || y == i || z == i) {  
      PVector n = normal(vertices.get(x), vertices.get(y), vertices.get(z));
      adj = PVector.add(adj, n);
    }
  } 
  return adj;
}

ArrayList<Integer> oppositeTable() {
  ArrayList<Integer> temp = new ArrayList();
  
  for (int i = 0; i < geometry.size(); i++) {
    temp.add(-1);
  }
  
  for (int i = 0; i < geometry.size(); i++) { //loop through faces
    PVector an = vertices.get(geometry.get(getNext(i)));
    PVector ap = vertices.get(geometry.get(getPrevious(i)));
    
    for (int j = 0; j < geometry.size(); j++) {
      PVector bn = vertices.get(geometry.get(getNext(j)));
      PVector bp = vertices.get(geometry.get(getPrevious(j)));
            
      if (an.equals(bp) && ap.equals(bn)) {
        temp.set(i, j);
      }
    }
  }
  return temp;
}

void subdivide() {
  int ind = 0;
  ArrayList<Integer> GTemp = new ArrayList();
  ArrayList<PVector> VTemp = new ArrayList<PVector>();
  
  for (int i = 0; i < geometry.size(); i++) {
    GTemp.add(-1);
  }

  //create new vertex for each corner in faces table
  for (int i = 0; i < geometry.size(); i++) {   
    int curr = geometry.get(i);
    int next = geometry.get(getNext(i));
    int prev = geometry.get(getPrevious(i));
    int opp = geometry.get(opposites.get(i));
    
    PVector vc = vertices.get(curr); //current corner
    PVector vn = vertices.get(next); //next corner
    PVector vp = vertices.get(prev); //prev corner
    PVector vo = vertices.get(opp); //opposite corner
    
    PVector v = PVector.mult(PVector.add(vn, vp), 3.0/8.0)
      .add(PVector.mult(PVector.add(vc, vo), 1.0/8.0)); //new vertex

    if (!containsDuplicate(VTemp, v)) {
      GTemp.set(i, ind + vertices.size());
      GTemp.set(opposites.get(i), ind + vertices.size());
      VTemp.add(v);
      ind++;
    }
  }

  //update old vertices' positions
  ArrayList<PVector> updated = new ArrayList();
  for (int i = 0; i < vertices.size(); i++) {
    
    PVector currV = vertices.get(i);
    ArrayList<PVector> neighbors = findNeighbors(i);
    PVector sum = new PVector(0,0,0);
    for (PVector n : neighbors) {
      sum.add(n);
    }
    
    int k = neighbors.size();
    float beta = k == 3 ? 3.0f/16.0f : 3.0f / (8.0f * k);
   
    PVector newV = PVector.mult(currV, (1 - beta * k));
    newV.add(PVector.mult(sum, beta));
    updated.add(newV);
  }
  
  ArrayList<Integer> finalFaces = new ArrayList<>();
  //create 4 new faces for each original face
  for (int f = 0; f < numFaces; f++) {
    
    //original vertices' indices (from geo table)
    int o1 = geometry.get(f * 3 + 0);
    int o2 = geometry.get(f * 3 + 1);
    int o3 = geometry.get(f * 3 + 2);
    
    //new vertices' indices (from GTemp)
    int n1 = GTemp.get(f * 3 + 0);
    int n2 = GTemp.get(f * 3 + 1);
    int n3 = GTemp.get(f * 3 + 2);
    
    finalFaces.add(o1);
    finalFaces.add(n2);
    finalFaces.add(n3);
    
    finalFaces.add(o2);
    finalFaces.add(n3);
    finalFaces.add(n1);
    
    finalFaces.add(o3);
    finalFaces.add(n1);
    finalFaces.add(n2);
    
    finalFaces.add(n1);
    finalFaces.add(n3);
    finalFaces.add(n2);
  }
  println("NEW VERTICES");
  println(VTemp);
  println("UPDATED OLD VERTICES");
  println(updated);
  
  geometry = finalFaces;
  numFaces = geometry.size() / 3;
  updated.addAll(VTemp); //updated old vertices + new vertices
  vertices = updated;
  opposites = oppositeTable();
}

boolean containsDuplicate(ArrayList<PVector> list, PVector v) {
  for (PVector vec : list) {
    if (vec.x == v.x && vec.y == v.y && vec.z == v.z) {
      return true;
    }
  }
  return false;
}

int getNext(int c) {
  int ct = c / 3; //triangle number
  return 3 * ct + (c+1) % 3;
}

int getPrevious(int c) {
  int ct = c / 3; //triangle number
  return 3 * ct + (c+2) % 3;
}

ArrayList<PVector> findNeighbors(int start) {
  ArrayList<PVector> neighbors = new ArrayList(); 
  int curr = getFace(start); //index of first face w/ that vertex index
  
  do {
    int next = geometry.get(getNext(curr));
    neighbors.add(vertices.get(next));
    curr = getNext(opposites.get(getNext(curr)));
  } while (curr != getFace(start));
  
  return neighbors;
}

int getFace(int v) { //get first face that contains that vertex index
  for (int i = 0; i < geometry.size(); i++) {
    int g = geometry.get(i);
    if (g == v) {  
      return i;  
    }
  }
  return 0;
}