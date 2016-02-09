//
//  Vertex.h
//  OpenGLTutorial
//
//  Created by Eric Lanz on 3/26/12.
//  Copyright (c) 2012 200Monkeys. All rights reserved.
//

typedef struct {
    float x;
    float y;
    float z;
} Vertex;

enum
{
    UNIFORM_MODELVIEWPROJECTION_MATRIX1,
    UNIFORM_MODELVIEWPROJECTION_MATRIX2,
    UNIFORM_TEX01,
    UNIFORM_TEX02,
    NUM_UNIFORMS
};

static GLint uniforms[NUM_UNIFORMS];

enum
{
    ATTRIB_VERTEX,
    ATTRIB_TEX01,
    ATTRIB_COLOR,
    NUM_ATTRIBUTES
};
