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
    public func identify(_ hash: String){
       Kustomer.logIn(jwt: String(hash)){ result in
            switch result {
                case .success:
                print("success")
                case .failure(let error):
                print("there was a problem \(error.localizedDescription)")
            }
        }
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
