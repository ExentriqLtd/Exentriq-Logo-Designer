//
//  LogoLabel.h
//  exentriq-logo-designer
//
//  Created by Mehdi Mahmoudi on 7/9/15.
//  Copyright (c) 2015 EMIKETIC. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface LogoLabel : UILabel {

    CGPoint _initialLocation;
    CGPoint _lastLocation;
}

@property CGPoint initialLocation;

@end
