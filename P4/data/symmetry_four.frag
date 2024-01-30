// Fragment shader

#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

#define PROCESSING_LIGHT_SHADER

uniform float cx;
uniform float cy;

// These values come from the vertex shader
varying vec4 vertColor;
varying vec3 vertNormal;
varying vec3 vertLightDir;
varying vec4 vertTexCoord;

void main() { 
  vec4 diffuse_color = vec4 (1.0, 0.0, 0.0, 1.0);  // red
  float diffuse = abs(dot (vertNormal, vertLightDir));
  gl_FragColor.a = 1.0;

  float zr = vertTexCoord.x * 2.5 - 1.25;
  float zi = vertTexCoord.y * 2.5 - 1.25;

  float a = zr;
  float b = zi;

  int n = 0;
  int max_i = 25;

  while (n < max_i) {
    float r = ((a*a*a*a) - 6*(a*a)*(b*b) + (b*b*b*b));
    float i = 4*a*a*a*b - 4*a*b*b*b;
    
    a = r + cx;
    b = i + cy;

    if (sqrt(a*a + b*b) > 20) {
      break;
    }
    n++;
  }

  if (n == max_i) {
    gl_FragColor = vec4(1.0, 1.0, 1.0, 1.0);
  } else {
    gl_FragColor = vec4(diffuse * diffuse_color.rgb, 1.0);
  }
}
