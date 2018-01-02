
#import <UIKit/UIKit.h>
#import <CoreBluetooth/CoreBluetooth.h>
#import <Cordova/CDVPlugin.h>
#import "MTSCRA.h"

@interface CDVMagTek: CDVPlugin <CBCentralManagerDelegate, MTSCRAEventDelegate> {}

- (void)getBLEStatus:(CDVInvokedUrlCommand*)command;
- (void)getSDKVersion:(CDVInvokedUrlCommand*)command;

@end
