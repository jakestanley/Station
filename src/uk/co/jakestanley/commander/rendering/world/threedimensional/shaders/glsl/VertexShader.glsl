#version 400 core

in vec3 position;
in vec2 textureCoordinates;

out vec3 colour;
out vec2 pass_textureCoordinates;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;

void main(void){
    
    gl_Position = transformationMatrix * vec4(position.x, position.y, position.z, 1.0);
    pass_textureCoordinates = textureCoordinates;
    colour = vec3(position.x+0.5,0.0,position.y+0.5);
    
}