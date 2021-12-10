#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(KustomerSDK, NSObject)

RCT_EXTERN_METHOD(identify: (NSString*)hash
                   resolver:(RCTPromiseResolveBlock)resolve
                   rejecter:(RCTPromiseRejectBlock)reject
                   )

RCT_EXTERN_METHOD(presentSupport)

RCT_EXTERN_METHOD(openConversationsCount)

RCT_EXTERN_METHOD(logout)

RCT_EXTERN_METHOD(describeCustomer: (NSDictionary *)data)

@end
