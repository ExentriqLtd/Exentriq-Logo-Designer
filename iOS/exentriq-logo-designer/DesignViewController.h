//
//  DesignViewController.h
//  exentriq-logo-designer
//
//  Created by Mehdi Mahmoudi on 7/8/15.
//  Copyright (c) 2015 EMIKETIC. All rights reserved.
//

#import <UIKit/UIKit.h>

@class LogoImageView;
@class LogoLabel;

@interface DesignViewController : UIViewController
<UIScrollViewDelegate, UITableViewDelegate, UITableViewDataSource> {
    
    IBOutlet LogoImageView *_logoImage;
    IBOutlet LogoLabel *_logoTitle;
    IBOutlet LogoLabel *_logoTagline;
    IBOutlet UIScrollView *_contentView;
    IBOutlet UISegmentedControl *_librarySelector;
    IBOutlet UIView *_libraryView;
    IBOutlet UIView *_libraryImageEditorView;
    IBOutlet UIView *_libraryTextEditorView;
    IBOutlet UITableView *_libraryImageCollection;
    
    BOOL _isLibraryViewVisible;
}

@end
