//
//  LogoImageView.h
//  exentriq-logo-designer
//
//  Created by Mehdi Mahmoudi on 7/8/15.
//  Copyright (c) 2015 EMIKETIC. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface LogoImageView : UIImageView {
    
    CGPoint _initialLocation;
    CGPoint _lastLocation;
}

@property CGPoint initialLocation;

@end
