//
//  LogoCell.m
//  exentriq-logo-designer
//
//  Created by Mehdi Mahmoudi on 7/17/15.
//  Copyright (c) 2015 EMIKETIC. All rights reserved.
//

#import "LogoCell.h"

@implementation LogoCell

- (void)awakeFromNib {
    
   
}

- (id)initWithStyle:(UITableViewCellStyle)style
    reuseIdentifier:(NSString *)reuseIdentifier {
    
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    
    [self setBackgroundColor:[UIColor clearColor]];
    
    // Position image
    _logoImage = [[UIImageView alloc] initWithFrame:CGRectMake(60, 20, 100, 100)];
    [_logoImage setBackgroundColor:[UIColor clearColor]];
    
    [self.contentView setBackgroundColor:[UIColor clearColor]];
    [self.contentView addSubview:_logoImage];
    
    
    return self;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

// Overload image setter to set default blending color whenver image is changed
- (void)setImageForLogoImage:(UIImage *)image{
    
    CGRect rect = CGRectMake(0, 0, image.size.width, image.size.height);
    UIGraphicsBeginImageContext(rect.size);
    CGContextRef context = UIGraphicsGetCurrentContext();
    CGContextClipToMask(context, rect, image.CGImage);
    CGContextSetFillColorWithColor(context, [[UIColor whiteColor] CGColor]);
    CGContextFillRect(context, rect);
    UIImage *img = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    [_logoImage setImage:img];
}

@end
