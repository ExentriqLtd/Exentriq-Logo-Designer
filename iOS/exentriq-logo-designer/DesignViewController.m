//
//  DesignViewController.m
//  exentriq-logo-designer
//
//  Created by Mehdi Mahmoudi on 7/8/15.
//  Copyright (c) 2015 EMIKETIC. All rights reserved.
//

#import "DesignViewController.h"
#import "LogoImageView.h"
#import "LogoLabel.h"
#import "LogoCell.h"

@interface DesignViewController ()

@end

@implementation DesignViewController

- (id)init {
    // Tab bar item properties
    [self setTitle:@"Design"];
    [self.tabBarItem setImage:[UIImage imageNamed:@"tbi_design"]];
    
    // Toggled when library becomes visible
    _isLibraryViewVisible = NO;
    
    return [super init];
    
}

- (void)viewDidLoad {
    [super viewDidLoad];

    // Set background pattern
    //[self.view setBackgroundColor:[UIColor
      //                             colorWithPatternImage:[UIImage
        //                                                  imageNamed:@"background_pattern.png"]]];
    
    
    
    // Add navigation bar buttons
    UIBarButtonItem *buttonLibraryImageEditor
    = [[UIBarButtonItem alloc] initWithImage:[UIImage imageNamed:@"button_library"]
                                       style:UIBarButtonItemStylePlain
                                      target:self
                                      action:@selector(showLibraryWithImageEditor)];
    UIBarButtonItem *buttonLibraryTextEditor
    = [[UIBarButtonItem alloc] initWithImage:[UIImage imageNamed:@"button_text_2"]
                                       style:UIBarButtonItemStylePlain
                                      target:self
                                      action:@selector(showLibraryWithImageEditor)];
    
    
    
    [self.tabBarController.navigationItem setRightBarButtonItems:[NSArray arrayWithObjects:
                                                                  buttonLibraryImageEditor,
                                                                  buttonLibraryTextEditor,
                                                                  nil]];
    

    // Configure scroll view that was defined in NIB
    [self.view addSubview:_contentView];
    [self.view sendSubviewToBack:_contentView];

    [_contentView setFrame:CGRectMake(0, 0,
                                      _contentView.frame.size.width,
                                      _contentView.frame.size.height)];
    [_contentView setContentSize:CGSizeMake(_contentView.frame.size.width,
                                            1.4*_contentView.frame.size.height)];
    [_contentView setDelegate:self];
    
    [_contentView setScrollEnabled:NO];
    
    
    // Setup library views
    [_librarySelector addTarget:self
                         action:@selector(switchVisibleLibraryEditor)
               forControlEvents:UIControlEventValueChanged];
    
    // Add logo image, name and tagline view to the library view
    [_libraryView addSubview:_libraryImageEditorView];
    [_libraryImageEditorView setFrame:CGRectMake(0,
                                                 70,
                                                 _libraryImageEditorView.frame.size.width,
                                                 _libraryImageEditorView.frame.size.height)];
    [_libraryImageEditorView setHidden:YES];
    
    [_libraryView addSubview:_libraryNameTextEditorView];
    [_libraryNameTextEditorView setFrame:CGRectMake(0,
                                                70,
                                                _libraryNameTextEditorView.frame.size.width,
                                                _libraryNameTextEditorView.frame.size.height)];
    
    [_libraryNameTextEditorView setHidden:YES];
    
    [_libraryView addSubview:_libraryTaglineTextEditorView];
    [_libraryTaglineTextEditorView setFrame:CGRectMake(0,
                                                    70,
                                                    _libraryTaglineTextEditorView.frame.size.width,
                                                    _libraryTaglineTextEditorView.frame.size.height)];
    
    [_libraryTaglineTextEditorView setHidden:YES];
    

    // Setup image library view
    [_libraryImageCollection setBackgroundColor:[UIColor clearColor]];
    [_libraryImageCollection registerClass:[LogoCell class]
                    forCellReuseIdentifier:@"LogoCell"];
    [_libraryImageCollection setBackgroundColor:[UIColor clearColor]];
    
    // Toggled when library becomes visible
    _isLibraryViewVisible = NO;
    
}

- (void)awakeFromNib {
    
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


#pragma mark - Navigation


- (void)showLibraryWithImageEditor {
    
    [self bringLibraryView];
    [_librarySelector setSelectedSegmentIndex:0];
    
}

- (void)showLibraryWithTextEditor {
    
    [self bringLibraryView];
    [_librarySelector setSelectedSegmentIndex:1];
    
}

- (void)bringLibraryView {
    
    if (_isLibraryViewVisible) {
        [_logoImage setUserInteractionEnabled:YES];
        [_logoTitle setUserInteractionEnabled:YES];
        [_logoTagline setUserInteractionEnabled:YES];
        [_contentView
         setContentOffset:CGPointMake(0, 0) animated:YES];
    }
    else {
        [_logoImage setUserInteractionEnabled:NO];
        [_logoTitle setUserInteractionEnabled:NO];
        [_logoTagline setUserInteractionEnabled:NO];
        [_contentView
         setContentOffset:CGPointMake(0, 1.5*_libraryView.bounds.size.height)
         animated:YES];
    }
    
    [_librarySelector sendActionsForControlEvents:UIControlEventValueChanged];
}

#pragma mark - Library Navigation

- (void)switchVisibleLibraryEditor {
    
    switch (_librarySelector.selectedSegmentIndex) {
        case 0: {
            [_libraryView sendSubviewToBack:_libraryNameTextEditorView];
            [_libraryView sendSubviewToBack:_libraryTaglineTextEditorView];
            [_libraryNameTextEditorView setHidden:YES];
            [_libraryTaglineTextEditorView setHidden:YES];
            [_libraryView bringSubviewToFront:_libraryImageEditorView];
            [_libraryImageEditorView setHidden:NO];
            break;
        }
        case 1: {
            [_libraryView sendSubviewToBack:_libraryImageEditorView];
            [_libraryView sendSubviewToBack:_libraryTaglineTextEditorView];
            [_libraryImageEditorView setHidden:YES];
            [_libraryTaglineTextEditorView setHidden:YES];
            [_libraryView bringSubviewToFront:_libraryNameTextEditorView];
            [_libraryNameTextEditorView setHidden:NO];
            break;
        }
        case 2: {
            [_libraryView sendSubviewToBack:_libraryNameTextEditorView];
            [_libraryView sendSubviewToBack:_libraryImageEditorView];
            [_libraryImageEditorView setHidden:YES];
            [_libraryNameTextEditorView setHidden:YES];
            [_libraryView bringSubviewToFront:_libraryTaglineTextEditorView];
            [_libraryTaglineTextEditorView setHidden:NO];
            break;
        }
            
            
        default:
            break;
    }
}


#pragma mark - Scroll view delegate

- (void)scrollViewDidScroll:(UIScrollView *)scrollView {
    
    if (scrollView == _contentView) {
        if (_isLibraryViewVisible) {
            [UIView animateWithDuration:0.15
                                  delay: 0.0
                                options: UIViewAnimationOptionCurveLinear
                             animations:^ {
                                 
                                 _logoImage.center = CGPointMake(_logoImage.center.x,
                                                                 _logoImage.center.y
                                                                 + 6);
                                 _logoTitle.center = CGPointMake(_logoTitle.center.x,
                                                                 _logoTitle.center.y
                                                                 + 8);
                                 
                                 _logoTagline.center = CGPointMake(_logoTagline.center.x,
                                                                   _logoTagline.center.y
                                                                   + 9);
                                 
                                 _logoImage.transform =
                                 CGAffineTransformMakeScale(1, 1);
                                 _logoTitle.transform =
                                 CGAffineTransformMakeScale(1, 1);
                                 _logoTagline.transform =
                                 CGAffineTransformMakeScale(1, 1);
                                 
                             }
             
                             completion:^(BOOL finished) {
                             }
             ];
        }
        else {
            [UIView animateWithDuration:0.15
                                  delay: 0.0
                                options: UIViewAnimationOptionCurveLinear
                             animations:^ {
                                 
                                 _logoImage.center = CGPointMake(_logoImage.center.x,
                                                                 _logoImage.center.y
                                                                 - 6);
                                 _logoTitle.center = CGPointMake(_logoTitle.center.x,
                                                                 _logoTitle.center.y
                                                                 - 8);
                                 
                                 _logoTagline.center = CGPointMake(_logoTagline.center.x,
                                                                   _logoTagline.center.y
                                                                   - 9);
                                 
                                 _logoImage.transform =
                                 CGAffineTransformMakeScale(0.5, 0.5);
                                 _logoTitle.transform =
                                 CGAffineTransformMakeScale(0.5, 0.5);
                                 _logoTagline.transform =
                                 CGAffineTransformMakeScale(0.5, 0.5);
                                 
                             }
             
                             completion:^(BOOL finished) {
                             }
             ];
        }

    }
    
}


- (void)scrollViewDidEndScrollingAnimation:(UIScrollView *)scrollView {
    
    _isLibraryViewVisible = !_isLibraryViewVisible;
    
}

#pragma mark - Table View Delegate

- (void)tableView:(UITableView *)tableView
didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    if (tableView == _libraryImageCollection) {
        NSString *prefix = @"logo_";
        _logoImage.image = [UIImage imageNamed:[prefix
                                                stringByAppendingString:
                                                [[NSNumber numberWithInteger:indexPath.row+1]
                                                 stringValue]]];
    }
}

#pragma mark - Table View Data Source


- (UITableViewCell *)tableView:(UITableView *)tableView
         cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    LogoCell *cell = [_libraryImageCollection
                             dequeueReusableCellWithIdentifier:@"LogoCell"
                             forIndexPath:indexPath];
    if (cell) {
        cell.textLabel.text = @"Hello";
        cell.textLabel.font = [UIFont fontWithName:@"Aclonica" size:15];
        /*
        NSString *prefix = @"logo_";
        [cell
         setImageViewForLogoImage:[UIImage imageNamed:[prefix
                                                   stringByAppendingString:
                                                   [[NSNumber numberWithInteger:indexPath.row+1]
                                                    stringValue]]]];
        [cell setSelectionStyle:UITableViewCellSelectionStyleNone];
         */
    }
    
    
    return cell;
    
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView
 numberOfRowsInSection:(NSInteger)section {
    return 5;
}

- (CGFloat)tableView:(UITableView *)tableView
heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 180;
}

#pragma mark - Picker View Delegate

- (NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row
            forComponent:(NSInteger)component {
    return @"Test";
}

#pragma mark - Picker View Data Source

- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView {
    return 1;
}

- (NSInteger)pickerView:(UIPickerView *)pickerView
numberOfRowsInComponent:(NSInteger)component {
    return 4;
}

@end
