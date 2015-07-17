//
//  ExportViewController.m
//  exentriq-logo-designer
//
//  Created by Mehdi Mahmoudi on 7/8/15.
//  Copyright (c) 2015 EMIKETIC. All rights reserved.
//

#import "ExportViewController.h"

@interface ExportViewController ()

@end

@implementation ExportViewController

- (id)init {
    // Tab bar item properties
    [self setTitle:@"Export"];
    [self.tabBarItem setImage:[UIImage imageNamed:@"tbi_export"]];
    return [super init];
}

- (void)viewDidLoad {
    [super viewDidLoad];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
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
