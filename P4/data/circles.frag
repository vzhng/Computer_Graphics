// Fragment shader

#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

#define PROCESSING_LIGHT_SHADER

// These values come from the vertex shader
varying vec4 vertColor;
varying vec3 vertNormal;
varying vec3 vertLightDir;
varying vec4 vertTexCoord;

void main() {
  vec4 diffuse_color = vec4 (0.0, 1.0, 1.0, 1.0);
  float diffuse = clamp(dot (vertNormal, vertLightDir),0.0,1.0);
  gl_FragColor = vec4(diffuse * diffuse_color.rgb, 0.8); 

  const float PI = 3.1415926535897932384626433832795;
  float x = vertTexCoord.x;
  float y = vertTexCoord.y;

  //center
  float x0 = 0.5;
  float y0 = 0.5;
  float r = 0.15;
  float d0 = sqrt((x0 - x) * (x0 - x) + (y0 - y) * (y0 - y));

  //(1,0)
  float x1 = x0 + 0.15 * cos(0);
  float y1 = y0 + 0.15 * sin(0);
  float d1 = sqrt((x1 - x) * (x1 - x) + (y1 - y) * (y1 - y));

  float x12 = x0 + 0.3 * cos(0);
  float y12 = y0 + 0.3 * sin(0);
  float d12 = sqrt((x12 - x) * (x12 - x) + (y12 - y) * (y12 - y));

  float x13 = x0 + 0.45 * cos(0);
  float y13 = y0 + 0.45 * sin(0);
  float d13 = sqrt((x13 - x) * (x13 - x) + (y13 - y) * (y13 - y));

  //(a,-b)
  float x2 = x0 + r * cos(PI/3);
  float y2 = y0 + r * sin(PI/3);
  float d2 = sqrt((x2 - x) * (x2 - x) + (y2 - y) * (y2 - y));

  float x22 = x0 + 0.3 * cos(PI/3);
  float y22 = y0 + 0.3 * sin(PI/3);
  float d22 = sqrt((x22 - x) * (x22 - x) + (y22 - y) * (y22 - y));

  float x23 = x0 + 0.45 * cos(PI/3);
  float y23 = y0 + 0.45 * sin(PI/3);
  float d23 = sqrt((x23 - x) * (x23 - x) + (y23 - y) * (y23 - y));

  //(-a,-b)
  float x3 = x0 + r * cos(2 * PI/3);
  float y3 = y0 + r * sin(2 * PI/3);
  float d3 = sqrt((x3 - x) * (x3 - x) + (y3 - y) * (y3 - y));

  float x32 = x0 + 0.3 * cos(2 * PI/3);
  float y32 = y0 + 0.3 * sin(2 * PI/3);
  float d32 = sqrt((x32 - x) * (x32 - x) + (y32 - y) * (y32 - y));

  float x33 = x0 + 0.45 * cos(2 * PI/3);
  float y33 = y0 + 0.45 * sin(2 * PI/3);
  float d33 = sqrt((x33 - x) * (x33 - x) + (y33 - y) * (y33 - y));

  //(-1,0)
  float x4 = x0 + r * cos(PI);
  float y4 = y0 + r * sin(PI);
  float d4 = sqrt((x4 - x) * (x4 - x) + (y4 - y) * (y4 - y));

  float x42 = x0 + 0.3 * cos(PI);
  float y42 = y0 + 0.3 * sin(PI);
  float d42 = sqrt((x42 - x) * (x42 - x) + (y42 - y) * (y42 - y));

  float x43 = x0 + 0.45 * cos(PI);
  float y43 = y0 + 0.45 * sin(PI);
  float d43 = sqrt((x43 - x) * (x43 - x) + (y43 - y) * (y43 - y));

  //(-a,b)
  float x5 = x0 + r * cos(4 * PI/3);
  float y5 = y0 + r * sin(4 * PI/3);
  float d5 = sqrt((x5 - x) * (x5 - x) + (y5 - y) * (y5 - y));

  float x52 = x0 + 0.3 * cos(4 * PI/3);
  float y52 = y0 + 0.3 * sin(4 * PI/3);
  float d52 = sqrt((x52 - x) * (x52 - x) + (y52 - y) * (y52 - y));

  float x53 = x0 + 0.45 * cos(4 * PI/3);
  float y53 = y0 + 0.45 * sin(4 * PI/3);
  float d53 = sqrt((x53 - x) * (x53 - x) + (y53 - y) * (y53 - y));
  
  //(a,b)
  float x6 = x0 + r * cos(5 * PI/3);
  float y6 = y0 + r * sin(5 * PI/3);
  float d6 = sqrt((x6 - x) * (x6 - x) + (y6 - y) * (y6 - y));
  
  float x62 = x0 + 0.3 * cos(5 * PI/3);
  float y62 = y0 + 0.3 * sin(5 * PI/3);
  float d62 = sqrt((x62 - x) * (x62 - x) + (y62 - y) * (y62 - y));

  float x63 = x0 + 0.45 * cos(5 * PI/3);
  float y63 = y0 + 0.45 * sin(5 * PI/3);
  float d63 = sqrt((x63 - x) * (x63 - x) + (y63 - y) * (y63 - y));
  
  if (abs(d0) < 0.07 || abs(d1) < 0.05 || abs(d2) < 0.05 || abs(d3) < 0.05 || abs(d4) < 0.05 || abs(d5) < 0.05 || abs(d6) < 0.05
    || abs(d12) < 0.03 || abs(d13) < 0.02
    || abs(d22) < 0.03 || abs(d23) < 0.02
    || abs(d32) < 0.03 || abs(d33) < 0.02
    || abs(d42) < 0.03 || abs(d43) < 0.02
    || abs(d52) < 0.03 || abs(d53) < 0.02
    || abs(d62) < 0.03 || abs(d63) < 0.02) {
    gl_FragColor.a = 0.0;
  } else {
    gl_FragColor.a = 1.0;
  }
}
