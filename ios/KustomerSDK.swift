import Foundation
import KustomerChat

@objc(KustomerSDK)
class KustomerSDK: NSObject {

    @objc 
    public func presentSupport(){
        Kustomer.show()
    }
}
