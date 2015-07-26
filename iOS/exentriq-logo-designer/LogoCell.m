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
    
    // Init storage attribute for the original image
    _logoImage = [[UIImage alloc] init];
    
    // Position image
    _logoImageView = [[UIImageView alloc] initWithFrame:CGRectMake(60, 20, 100, 100)];
    [_logoImageView setBackgroundColor:[UIColor clearColor]];
    
    [self.contentView setBackgroundColor:[UIColor clearColor]];
    [self.contentView addSubview:_logoImageView];
    
    
    return self;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

// Overload image setter to set default blending color whenver image is changed
- (void)setImageViewForLogoImage:(UIImage *)image{
    
    // Store original image for usage in design view
    _logoImage = [UIImage imageWithCGImage:image.CGImage];
    
    CGRect rect = CGRectMake(0, 0, image.size.width, image.size.height);
    UIGraphicsBeginImageContext(rect.size);
    CGContextRef context = UIGraphicsGetCurrentContext();
    CGContextClipToMask(context, rect, image.CGImage);
    CGContextSetFillColorWithColor(context, [[UIColor whiteColor] CGColor]);
    CGContextFillRect(context, rect);
    UIImage *img = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    // Assign modified whose color was modified to the cell image
    [_logoImageView setImage:img];
}

@end
