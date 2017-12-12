
#import <Cordova/CDV.h>
#import "CDVMagTek.h"

@interface CDVMagTek ()

@property (strong, nonatomic) CBCentralManager *manager;

@end

@implementation CDVMagTek

- (void)pluginInitialize {
    [super pluginInitialize];
    self.manager = [[CBCentralManager alloc] initWithDelegate:self queue:nil];
}

- (void)BLEStatus:(CDVInvokedUrlCommand*)command {
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsInt:[self.manager state]];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

#pragma mark - CBCentralManagerDelegate

- (void)centralManagerDidUpdateState:(CBCentralManager *)central {
    [self fireEvent: @"bleStateChange" withNumber:[NSNumber numberWithInt:central.state]];
}

- (void)fireEvent:(NSString *) event withNumber:(NSNumber *) data {
    NSString *js = [NSString stringWithFormat: @"cordova.plugins.magtek.fireEvent('%@', %@)", event, data];
    [self.commandDelegate evalJs:js];
}

@end
