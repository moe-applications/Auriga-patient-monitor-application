//
//  UIWaveFormVC.h
//  OpenGLWaves
//
//  Created by Alexey Bologov on 8/4/15.
//  Copyright (c) 2015 Auriga. All rights reserved.
//

#import <GLKit/GLKit.h>

@class DPSampleQueue;

@interface UIWaveFormVC : GLKViewController

@property (nonatomic, strong) DPSampleQueue * inputQueue;

@property (nonatomic, strong) UIColor * waveformColor;

@property (nonatomic) NSString *queueID;
@property (nonatomic) float sampleFreq;
@property (nonatomic) float yPadding;

- (void)setDataQueue:(DPSampleQueue *) dataQueue;
- (void)setWaveColor:(UIColor *)waveColor;
- (void)setSampleFreq:(float)sampleFreq;
- (void)setYPadding:(float)yPadding;

@end
