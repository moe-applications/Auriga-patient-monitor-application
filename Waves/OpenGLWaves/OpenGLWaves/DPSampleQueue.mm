//
//  DPSampleQueue.m
//  InfinityClient
//
//  Created by Andrey Morichev on 22/09/14.
//  Copyright (c) 2014 Auriga. All rights reserved.
//

#import "DPSampleQueue.h"
#import <queue>

@implementation DPSampleQueue
{
    std::queue<float> _samplesQueue;
}

- (id) init
{
    self = [super init];
    if (self != nil) {
        _maxSize = 2000;
        self.sampleRate = 1.0;
    }
    return self;
}

- (void)pushSample:(float) sampleUnit
{
    if (self.size >= self.maxSize) {
        _samplesQueue.pop();
    }
    _samplesQueue.push(sampleUnit / 10);
}

- (float)popSample
{
    float returnValue = _samplesQueue.front();
    _samplesQueue.pop();
    return returnValue;
}

- (BOOL)isEmpty
{
    return _samplesQueue.empty();
}

- (NSInteger) size
{
    return _samplesQueue.size();
}

@end
