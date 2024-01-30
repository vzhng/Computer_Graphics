// Fragment shader
// The fragment shader is run once for every pixel
// It can change the color and transparency of the fragment.

#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

#define PROCESSING_TEXLIGHT_SHADER

// Set in Processing
uniform sampler2D my_texture;
uniform float cx;
uniform float cy;

// These values come from the vertex shader
varying vec4 vertColor;
varying vec3 vertNormal;
varying vec3 vertLightDir;
varying vec4 vertTexCoord;

void main() { 
  float x = vertTexCoord.x;
  float y = vertTexCoord.y;
  float offset = 0.004;
  
  vec4 diffuse_color = texture2D(my_texture, vertTexCoord.xy);
  vec4 left = texture2D(my_texture, vec2(x - offset, y));
  vec4 right = texture2D(my_texture, vec2(x + offset, y));
  vec4 up = texture2D(my_texture, vec2(x, y - offset));
  vec4 down = texture2D(my_texture, vec2(x, y + offset));

  float curr_gray = (diffuse_color.g + diffuse_color.g + diffuse_color.b) / 3;
  float r = (left.r + right.r + up.r + down.r) / 3;
  float g = (left.g + right.g + up.g + down.g) / 3;
  float b = (left.b + right.b + up.b + down.b) / 3;

  float laplacian = 0.25 * (r + g + b) - (curr_gray);
  float lap = laplacian * 10 + 0.4;

  float xc = x * 3 - 1.5 + cx;
  float yc = y * 3 - 1.5 + cy;

  if (xc * xc + yc * yc < 0.5) {
    gl_FragColor = vec4(lap, lap, lap, 1.0);
  } else {
    float diffuse = abs(dot (vertNormal, vertLightDir));
    gl_FragColor = vec4(diffuse * diffuse_color.rgb, 1.0);
  }
}

