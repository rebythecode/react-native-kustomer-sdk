//
 //  KustomerSDK.swift
 import KustomerChat

 @objc(KustomerSDK)
 class KustomerSDK: NSObject {

  @objc(identify:)
  func identify(hash: String) -> Void {
    DispatchQueue.main.async {
     Kustomer.logIn(jwt: hash, { result in
       switch result {
       case .success:
         print("Success")
       case .failure(let error):
         print(error.localizedDescription)
       }
     })
    }
  }

  @objc(presentSupport)
  func presentSupport() -> Void {
    DispatchQueue.main.async {
     Kustomer.show()
    }
  }

  @objc(openConversationsCount)
  func openConversationsCount() -> Int {
     return Kustomer.openConversationCount()
  }

  @objc(logout)
  func logout() -> Void {
    DispatchQueue.main.async {
     Kustomer.logOut({ error in
         if error != nil {
           print("there was a problem \(error?.localizedDescription ?? "")")
         }
       })
    }
  }

  @objc(describeCustomer:)
  func describeCustomer(data: [AnyHashable : Any]) -> Void {
   var emails = [String]()
   let email = data["email"] as? String
   if email?.count != 0 {
    emails.append(email!)
   }
   var phones = [String]()
   let phone = data["phone"] as? String
   if phone?.count != 0 {
    phones.append(phone!)
   }
   var customs = [String : Any]()
   let custom = data["custom"] as? [String: Any]
   if custom != nil {
    customs = custom!
   }
   Kustomer.chatProvider.describeCurrentCustomer(phones: phones, emails: emails, custom: customs) { result in
    switch result {
     case .success:
      print("ok")
     case .failure(let error):
      print(error)
    }
   }
  }
     
 }
