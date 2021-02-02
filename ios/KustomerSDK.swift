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
    public func identify(hash: String){
       Kustomer.identifyCurrentCustomer(jwt: hash, { result in
                switch result {
                case .success:
                    print("Success")
                case .failure(let error):
                    print(error.localizedDescription)
                }
            }
       )
    }
    

    @objc
    public func describeCustomer(data: Dictionary){

        Kustomer.chatProvider.describeCurrentCustomer(phone:data.phone, email: data.email, custom: data.custom) { result in
                    switch result {
                        case .success:
                        print("ok")
                        case .failure(let error):
                        print(error)
                    }
              }
    }
}
