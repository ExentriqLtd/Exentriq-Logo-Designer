//
//  LogoLabel.m
//  exentriq-logo-designer
//
//  Created by Mehdi Mahmoudi on 7/9/15.
//  Copyright (c) 2015 EMIKETIC. All rights reserved.
//

#import "LogoLabel.h"

@implementation LogoLabel

- (void)awakeFromNib {
    
    // Initial location in superview
    _initialLocation = self.center;
    
    // Set gesture recognizers for all logo components
    UIPanGestureRecognizer *panGestureRecognizer = [[UIPanGestureRecognizer alloc] initWithTarget:self
                                                                                           action:@selector(handleForPanGestureRecognizer:)];
    [self addGestureRecognizer:panGestureRecognizer];
}


- (IBAction)handleForPanGestureRecognizer:(UIPanGestureRecognizer *)recognizer {
    
    
    CGPoint translation = [recognizer translationInView:self.superview];
    
    self.center = CGPointMake(_lastLocation.x + translation.x,
                              _lastLocation.y + translation.y);
    
}


- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
    
    UITouch *bestTouch = [[event allTouches] anyObject];
    
    _lastLocation = [bestTouch locationInView:self.superview];
}

@end
