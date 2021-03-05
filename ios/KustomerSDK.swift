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

    @objc 
    public func identify(_ hash: NSString, resolver resolve: @escaping RCTPromiseResolveBlock,
                         rejecter reject: @escaping RCTPromiseRejectBlock) -> Void{
        
        if(Kustomer.chatProvider.currentCustomer() == nil){
            Kustomer.logIn(jwt: hash as String){ result in
                switch result {
                    case .success:
                    resolve("success")
                    case .failure(let error):
                        print(error.localizedDescription)
                        reject("error",error.localizedDescription,error)
                }
            }
        }
        
    }
    

    @objc
    public func describeCustomer(_ data: Dictionary<String, AnyObject>){

        let email = data["email"] as? String

        let phone = data["phone"] as? String

        let custom = data["custom"] as? [String: Any]

        Kustomer.chatProvider.describeCurrentCustomer(phone: phone, email: email, custom: custom) { result in
                    switch result {
                        case .success:
                        print("ok")
                        case .failure(let error):
                        print(error)
                    }
              }
    }
}
