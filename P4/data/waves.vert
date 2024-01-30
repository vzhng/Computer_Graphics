// Vertex shader
// The vertex shader is run once for every vertex
// It can change the (x,y,z) of the vertex, as well as its normal for lighting.

#define PROCESSING_TEX_SHADER

// Set automatically by Processing
uniform mat4 transform;
uniform mat3 normalMatrix;
uniform vec3 lightNormal;
uniform mat4 texMatrix;
uniform sampler2D texture;

// Come from the geometry/material of the object
attribute vec4 position;
attribute vec4 vertex;
attribute vec4 color;
attribute vec3 normal;
attribute vec2 texCoord;

// These values will be sent to the fragment shader
varying vec4 vertColor;
varying vec3 vertNormal;
varying vec3 vertLightDir;
varying vec4 vertTexCoord;
varying vec4 vertTexCoordR;
varying vec4 vertTexCoordL;

varying float offset;

float damp(float amp, float x, float y) {
    float xd = abs(x - 0.5f);
    float yd = abs(y - 0.5f);
    float edge_dist = 0.5f - max(xd, yd);
    float border = 0.1f;

    if (edge_dist <= border) {
      return amp - ((3 * amp / 4) * ((border - edge_dist) / border));
    } 
    return amp;
}

void main() {
  vertColor = color;
  vertNormal = normalize(normalMatrix * normal);
  vertLightDir = normalize(-lightNormal);
  vertTexCoord = texMatrix * vec4(texCoord, 1.0, 1.0);

  float x = texCoord.x;
  float y = texCoord.y;

  float d = distance(texCoord.xy, vec2(0.5, 0.5));

  float freq = 50.0;
  float amp = damp(50, x, y);
  float os = sin(d * freq) / 2.0;
  offset = (sin(d * freq) - 1) / 2.0;

  if (x > 0.95 && y > 0.95) {
    amp = damp(amp, x, y);
  } else if (x < 0.05 || y < 0.05 || x > 0.95 || y > 0.95) {
    amp = damp(amp, x, y);
  }

  vec4 pos = vertex + vec4(amp * os * normal * pow(d, 0.4), 0.0);
  gl_Position = transform * pos;
}