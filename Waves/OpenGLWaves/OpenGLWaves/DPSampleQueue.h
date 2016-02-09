//
//  DPSampleQueue.h
//  InfinityClient
//
//  Created by Andrey Morichev on 22/09/14.
//  Copyright (c) 2014 Auriga. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface DPSampleQueue : NSObject

@property (nonatomic, assign) NSUInteger maxSize;
@property (nonatomic, assign) float sampleRate;

- (void)pushSample:(float) sampleUnit;

- (float)popSample;

- (BOOL)isEmpty;

- (NSInteger) size;

@end
