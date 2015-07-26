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
    UIImageView *_logoImageView;
    UIImage *_logoImage;

}

@property (nonatomic, strong)  UIImageView *logoImageView;
@property (nonatomic, strong) UIImage *logoImage;


- (void)setImageViewForLogoImage:(UIImage *)image;

@end
