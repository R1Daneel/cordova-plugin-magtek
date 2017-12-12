
#import <UIKit/UIKit.h>
#import <CoreBluetooth/CoreBluetooth.h>
#import <Cordova/CDVPlugin.h>

@interface CDVMagTek: CDVPlugin <CBCentralManagerDelegate> {}

- (void)BLEStatus:(CDVInvokedUrlCommand*)command;

@end
