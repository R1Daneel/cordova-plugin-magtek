
declare enum BLEState {
  Unknown = 0,
  Resetting,
  Unsupported,
  Unauthorized,
  PoweredOff,
  PoweredOn,
  TurningOn,
  TurningOff
}

declare interface MagTek {
  getBLEStatus: (successCallback: (status: BLEState) => void) => void;
  getSDKVersion: (successCallback: (status: string) => void) => void;
  subscribe: (event: string, callback: (data: any, error: any) => void, scope: any) => void;
  unsubscribe: (event: string, callback: (data: any, error: any) => void | undefined) => void;
}
