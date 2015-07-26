//
//  DesignViewController.h
//  exentriq-logo-designer
//
//  Created by Mehdi Mahmoudi on 7/8/15.
//  Copyright (c) 2015 EMIKETIC. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <FCColorPickerViewController.h>

@class LogoImageView;
@class LogoLabel;

@interface DesignViewController : UIViewController
<UIScrollViewDelegate, UITableViewDelegate, UITableViewDataSource,
FCColorPickerViewControllerDelegate, UIPickerViewDataSource, UIPickerViewDelegate> {
    
    IBOutlet LogoImageView *_logoImage;
    IBOutlet LogoLabel *_logoTitle;
    IBOutlet LogoLabel *_logoTagline;
    IBOutlet UIScrollView *_contentView;
    
    // Library view
    IBOutlet UIView *_libraryView;
    IBOutlet UISegmentedControl *_librarySelector;
    NSArray *fonts;
    BOOL _isLibraryViewVisible;
    
    // Logo image
    IBOutlet UITableView *_libraryImageCollection;
    IBOutlet UIView *_libraryImageEditorView;
    
    // Logo name
    IBOutlet UIView *_libraryNameTextEditorView;
    IBOutlet UIPickerView *_libraryNameTextPicker;
    
    // Logo tagline
    IBOutlet UIView *_libraryTaglineTextEditorView;
    IBOutlet UIPickerView *_libraryTaglineFontPicker;
    
    
}

@end
