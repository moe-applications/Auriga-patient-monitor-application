//
//  UIARTTrendFormVC.m
//  OpenGLWaves
//
//  Created by Alexey Bologov on 8/15/15.
//  Copyright (c) 2015 Auriga. All rights reserved.
//

#import "UIARTTrendFormVC.h"

#import "ShaderController.h"
#import "Vertex.h"

#import "DPSampleQueue.h"

// 50 потому что количество точек в секунду кратно этому числу
const int kFPS4=31;

@interface UIARTTrendFormVC ()
@property (strong, nonatomic) EAGLContext * context;
@end

@implementation UIARTTrendFormVC


{
    // OpenGL supporting
    ShaderController * _shaders;
    GLuint _lineBuffer;
    GLKVector4 _lineColor;
    GLKVector4 _lineColor2;
    GLKMatrix4 _viewMatrix;
    Vertex _vertexBuffer[2400];
    Vertex _vertexBuffer2[1200];

    BOOL _notFirstCircle;
    int currentIndex;
    int pointsInMatrix;
}

- (void)setDataQueue:(DPSampleQueue *) dataQueue {
    self.inputQueue = dataQueue;
}

- (void)setDataQueue2:(DPSampleQueue *) dataQueue {
    self.inputQueue2 = dataQueue;
}

- (void)setDataQueue3:(DPSampleQueue *) dataQueue {
    self.inputQueue3 = dataQueue;
}

- (void)setWaveColor:(UIColor *)waveColor {
    self.waveformColor = waveColor;
}

- (void)viewDidLoad {
    [super viewDidLoad];

    if (!self.waveformColor) {
        self.waveformColor = [UIColor blackColor];
    }
    self.context = [[EAGLContext alloc] initWithAPI:kEAGLRenderingAPIOpenGLES2];

    if (!self.context)
        NSLog(@"Failed to create ES context");

    [EAGLContext setCurrentContext:self.context];
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

    glClearColor(1.0f, 1.0f, 1.0f, 1.0f);


    GLKView *view = (GLKView *)self.view;
    view.context = self.context;

    view.drawableMultisample = GLKViewDrawableMultisampleNone;
    self.preferredFramesPerSecond = kFPS4;

    _shaders = [[ShaderController alloc] init];
    [_shaders loadShaders];

    glGenBuffers(1, &_lineBuffer);


    glEnableVertexAttribArray(ATTRIB_VERTEX);

    _notFirstCircle = NO;

    glLineWidth(1.0 * self.view.contentScaleFactor);

    //CGRect viewFrame = self.view.frame;
    //_aspect = fabsf(viewFrame.size.width / viewFrame.size.height) * 10.0f;
    _viewMatrix = GLKMatrix4MakeScale(1., 4., 1.);//GLKMatrix4MakePerspective(GLKMathDegreesToRadians(90.0f), _aspect, 1.0, 100.0);

    CGFloat red, green, blue, alpha;
    [self.waveformColor getRed:&red green: &green blue: &blue alpha: &alpha];
    _lineColor = GLKVector4Make(red , green, blue, alpha);
    //NSLog(@"BOLOGOV %f %f", red, green);
    _lineColor2 = GLKVector4Make(1.0 , 1.0, 1.0, 1.0);

    pointsInMatrix = 1200;
    float x = -1;
    const float dx = 2 / (float) pointsInMatrix;

    for (int i = 0; i < pointsInMatrix * 2; i += 2) {
        _vertexBuffer[i] = (Vertex){x, 0, 0};
        _vertexBuffer[i + 1] = (Vertex){x, 0.0, 0};
        x+=dx;
    }

    x=-1;
    for (int i = 0; i < pointsInMatrix; i++) {
        _vertexBuffer2[i] = (Vertex){x, 0, 0};
        x+=dx;
    }

    currentIndex = 0;

}

- (void)glkView:(GLKView *)view drawInRect:(CGRect)rect
{

    if (self.inputQueue == nil) {
        return;
    }

    glClear(GL_COLOR_BUFFER_BIT);
    int points = 0;
    while (!self.inputQueue.isEmpty && points < self.inputQueue.size) {
        // TODO: need based on samples freq plotting, now we plot all new samples;

        float sample = self.inputQueue.popSample;
        _vertexBuffer[currentIndex].y = sample/50.0f - 0.2f;
        sample = self.inputQueue2.popSample;
        _vertexBuffer[currentIndex + 1].y = sample/50.0f - 0.2f;

        currentIndex += 2;
        ++points;
    }

    points = 0;
    while (!self.inputQueue3.isEmpty && points < self.inputQueue3.size) {
        // TODO: need based on samples freq plotting, now we plot all new samples;

        float sample = self.inputQueue3.popSample;
        _vertexBuffer2[points].y = sample/50.0f - 0.2f;

        ++points;
    }

    glBindBuffer(GL_ARRAY_BUFFER, _lineBuffer);
    glBufferData(GL_ARRAY_BUFFER, sizeof(Vertex) * currentIndex, _vertexBuffer,  GL_DYNAMIC_DRAW);


    glUseProgram(_shaders.lineShader);

    glVertexAttribPointer(ATTRIB_VERTEX, 3, GL_FLOAT, GL_FALSE, sizeof(Vertex), 0);

    glUniformMatrix4fv(uniforms[UNIFORM_MODELVIEWPROJECTION_MATRIX2], 1, 0, GLKMatrix4Multiply(_viewMatrix, GLKMatrix4Identity).m);
    glVertexAttrib4fv(ATTRIB_COLOR, _lineColor.v);
    
    glDrawArrays(GL_LINE_STRIP, 0, currentIndex);



    /*glBufferData(GL_ARRAY_BUFFER, sizeof(Vertex) * points, _vertexBuffer2,  GL_DYNAMIC_DRAW);

    glVertexAttrib4fv(ATTRIB_COLOR, _lineColor2.v);

    glDrawArrays(GL_LINE_STRIP, 0, points);*/
}



@end
