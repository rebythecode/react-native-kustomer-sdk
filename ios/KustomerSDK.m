#import "React/RCTBridgeModule.h"

@interface RCT_EXTERN_MODULE(KustomerSDK, NSObject)

RCT_EXTERN_METHOD(presentSupport)
RCT_EXTERN_METHOD(presentKnowledgeBase)
RCT_EXTERN_METHOD(resetTracking)
RCT_EXTERN_METHOD(identify:(NSString*) hash
                  resolver:(RCTPromiseResolveBlock) resolve
                  rejecter:(RCTPromiseRejectBlock) reject
                  )
RCT_EXTERN_METHOD(describeCustomer:(NSDictionary*) data)

@end
