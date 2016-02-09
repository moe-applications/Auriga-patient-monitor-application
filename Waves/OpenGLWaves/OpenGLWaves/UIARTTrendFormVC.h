//
//  UIARTTrendFormVC.h
//  OpenGLWaves
//
//  Created by Alexey Bologov on 8/15/15.
//  Copyright (c) 2015 Auriga. All rights reserved.
//

#import <GLKit/GLKit.h>

@class DPSampleQueue;

@interface UIARTTrendFormVC : GLKViewController

@property (nonatomic, strong) DPSampleQueue * inputQueue;
@property (nonatomic, strong) DPSampleQueue * inputQueue2;
@property (nonatomic, strong) DPSampleQueue * inputQueue3;

@property (nonatomic, strong) UIColor * waveformColor;

@property (nonatomic) NSString *queueID;

- (void)setDataQueue:(DPSampleQueue *) dataQueue;
- (void)setDataQueue2:(DPSampleQueue *) dataQueue;
- (void)setDataQueue3:(DPSampleQueue *) dataQueue;
- (void)setWaveColor:(UIColor *)waveColor;

@end
