//
//  UIWaveFormVC.m
//  OpenGLWaves
//
//  Created by Alexey Bologov on 8/4/15.
//  Copyright (c) 2015 Auriga. All rights reserved.
//

#import "UIWaveFormVC.h"

#import "ShaderController.h"
#import "Vertex.h"

#import "DPSampleQueue.h"

// 50 потому что количество точек в секунду кратно этому числу
const int kFPS=31;



@interface UIWaveFormVC ()
@property (strong, nonatomic) EAGLContext * context;
@property (nonatomic) int pointsByFrame;
@end

@implementation UIWaveFormVC

{
    // OpenGL supporting
    ShaderController * _shaders;
    GLuint _lineBuffer;
    GLKVector4 _lineColor;
    GLKMatrix4 _viewMatrix;
    Vertex _vertexBuffer[2000];
    
    BOOL _notFirstCircle;
    int currentIndex;
    int pointsInMatrix;
    int space; ///< space between old and new data
}

- (void)setDataQueue:(DPSampleQueue *) dataQueue {
    self.inputQueue = dataQueue;
}

- (void)setWaveColor:(UIColor *)waveColor {
    self.waveformColor = waveColor;
}

-(void)setSampleFreq:(float)sampleFreq{
    NSLog(@"got sample freq %f", sampleFreq);
    _sampleFreq = sampleFreq;
}

-(void)setYPadding:(float)yPadding{
    _yPadding = yPadding;
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
    self.preferredFramesPerSecond = kFPS;
    
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
    
    self.pointsByFrame = self.sampleFreq / kFPS;
    
    NSLog(@"points By Frame: %d, blue = %f",self.pointsByFrame, blue);
    
    float koef = self.pointsByFrame/7.0;
    
    pointsInMatrix =  koef * 2000; //FIXME: experimental value;
    
    space = koef * 100;
    
    if (pointsInMatrix>2000) {
        pointsInMatrix = 2000;
    }
    
    float x = -1;
    const float dx = 2 / (float) pointsInMatrix;
    
    for (int i = 0; i < pointsInMatrix; ++i) {
        _vertexBuffer[i] = (Vertex){x, 0, 0};
        x+=dx;
    }
    
    currentIndex = 0;
    
    
}

- (void)glkView:(GLKView *)view drawInRect:(CGRect)rect
{
    
    glClear(GL_COLOR_BUFFER_BIT);
    
    
    
    if (self.inputQueue == nil) {
        return;
    }
    int points = 0;
    while (!self.inputQueue.isEmpty && points <= self.pointsByFrame) {
        // TODO: need based on samples freq plotting, now we plot all new samples;
        
        _vertexBuffer[currentIndex].y = self.inputQueue.popSample + _yPadding;
        
        ++currentIndex;
        ++points;
        
        if (currentIndex >= pointsInMatrix) {
            currentIndex = 0;
            _notFirstCircle = YES;
            
        }
        
    }
    
    
    glBindBuffer(GL_ARRAY_BUFFER, _lineBuffer);
    glBufferData(GL_ARRAY_BUFFER, sizeof(Vertex) * currentIndex, _vertexBuffer,  GL_DYNAMIC_DRAW);
    
    
    glUseProgram(_shaders.lineShader);
    
    glVertexAttribPointer(ATTRIB_VERTEX, 3, GL_FLOAT, GL_FALSE, sizeof(Vertex), 0);
    
    glUniformMatrix4fv(uniforms[UNIFORM_MODELVIEWPROJECTION_MATRIX2], 1, 0, GLKMatrix4Multiply(_viewMatrix, GLKMatrix4Identity).m);
    glVertexAttrib4fv(ATTRIB_COLOR, _lineColor.v);
    
    glDrawArrays(GL_LINE_STRIP, 0, currentIndex);
    
    if (_notFirstCircle) {
        // redraw previous data after space
        
        GLint pointsToRedraw = pointsInMatrix - space- currentIndex;
        
        glBindBuffer(GL_ARRAY_BUFFER, _lineBuffer);
        glBufferData(GL_ARRAY_BUFFER,
                     sizeof(Vertex) *pointsToRedraw,
                     &_vertexBuffer[currentIndex + space],
                     GL_DYNAMIC_DRAW);
        
        glUseProgram(_shaders.lineShader);
        
        glVertexAttribPointer(ATTRIB_VERTEX, 3, GL_FLOAT, GL_FALSE, sizeof(Vertex), 0);
        
        glUniformMatrix4fv(uniforms[UNIFORM_MODELVIEWPROJECTION_MATRIX2], 1, 0, GLKMatrix4Multiply(_viewMatrix, GLKMatrix4Identity).m);
        glVertexAttrib4fv(ATTRIB_COLOR, _lineColor.v);
        
        glDrawArrays(GL_LINE_STRIP, 0, pointsToRedraw);
    }
}

@end
