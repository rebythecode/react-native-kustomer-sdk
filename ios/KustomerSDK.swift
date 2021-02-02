import Foundation
import KustomerChat

@objc(KustomerSDK)
class KustomerSDK: NSObject {

    @objc 
    public func presentSupport(){
        Kustomer.show()
    }

    @objc
    public func presentKnowledgeBase(){
        Kustomer.show(preferredView: .knowledgeBase)
    }

    @objc 
    public func resetTracking(){
        Kustomer.logOut({ error in })
    }
}
