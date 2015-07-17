//
//  MainViewController.m
//  exentriq-logo-designer
//
//  Created by Mehdi Mahmoudi on 7/8/15.
//  Copyright (c) 2015 EMIKETIC. All rights reserved.
//

#import "MainViewController.h"
#import "DesignViewController.h"
#import "PreviewViewController.h"
#import "ExportViewController.h"

@interface MainViewController ()

@end

@implementation MainViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)navigateToDesign:(id)sender {
    
    // Instantiate a tab bar controller
    UITabBarController *tbc = [[UITabBarController alloc] init];
    
    
    // Instantiate preview, design and export controllers
    DesignViewController *designVC = [[DesignViewController alloc] init];
    PreviewViewController *previewVC = [[PreviewViewController alloc] init];
    ExportViewController *exportVC = [[ExportViewController alloc] init];
    [tbc setViewControllers:[NSArray arrayWithObjects:designVC, previewVC, exportVC, nil]];
    //[tbc setSelectedViewController:previewVC];
    
    [self.navigationController pushViewController:tbc animated:YES];
    
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
