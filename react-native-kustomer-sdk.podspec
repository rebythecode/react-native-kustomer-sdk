require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-kustomer-sdk"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  react-native-kustomer-sdk
                   DESC
  s.homepage     = "https://github.com/imariic/react-native-kustomer-sdk"
  s.license      = "MIT"
  s.authors      = { "Aitor Cubeles Torres" => "aitor@reby.com" }
  s.platforms    = { :ios => "12.0" }
  s.source       = { :git => "https://github.com/imariic/react-native-kustomer-sdk.git#ios-setup", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,c,m,swift}"
  s.requires_arc = true

  s.dependency "React"
  s.dependency "KustomerChat"
end

