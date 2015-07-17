//
//  LogoCell.h
//  exentriq-logo-designer
//
//  Created by Mehdi Mahmoudi on 7/17/15.
//  Copyright (c) 2015 EMIKETIC. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface LogoCell : UITableViewCell
{
    UIImageView *_logoImage;
}

@property (nonatomic, strong)  UIImageView* logoImage;


- (void)setImageForLogoImage:(UIImage *)image;

@end
