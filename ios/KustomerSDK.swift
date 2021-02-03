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
    public func describeCustomer(_ data: Dictionary<String, AnyObject>){

        let email = data["email"] as? String

        let phone = data["phone"] as? String


        /*if data["custom"] != nil {

            if let object = data["custom"] {
                for key in object {
                    let value = (data["custom"] as? [AnyHashable : Any])?[key] as? String
                    if (value?.count ?? 0) != 0 {
                        custom[key] = value
                    }
                }
            }
        }*/

        Kustomer.chatProvider.describeCurrentCustomer(phone: phone, email: email) { result in
                    switch result {
                        case .success:
                        print("ok")
                        case .failure(let error):
                        print(error)
                    }
              }
    }
}
