//
//  UISpO2TrendFormVC.h
//  OpenGLWaves
//
//  Created by Alexey Bologov on 8/15/15.
//  Copyright (c) 2015 Auriga. All rights reserved.
//

#import <GLKit/GLKit.h>

@class DPSampleQueue;

@interface UISpO2TrendFormVC : GLKViewController

@property (nonatomic, strong) DPSampleQueue * inputQueue;

@property (nonatomic, strong) UIColor * waveformColor;

@property (nonatomic) NSString *queueID;

- (void)setDataQueue:(DPSampleQueue *) dataQueue;
- (void)setWaveColor:(UIColor *)waveColor;

@end
