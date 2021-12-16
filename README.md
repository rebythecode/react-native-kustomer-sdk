# Kustomer SDK for React Native

## Getting started

`$ npm install react-native-kustomer-sdk --save`

### Using React Native >= 0.60

Linking the package manually is not required anymore with [Autolinking](https://github.com/react-native-community/cli/blob/master/docs/autolinking.md).

### Using React Native < 0.60

`$ react-native link react-native-kustomer-sdk`

## Install native Kustomer libraries

### Android

#### Gradle

Include the library in your `android/app/build.gradle`:

`implementation 'com.kustomer.chat:ui:2.9.1'`

### iOS

#### CocoaPods

The preferred installation method is with [CocoaPods](https://cocoapods.org). Add the following to your `Podfile`:

```ruby
pod 'KustomerChat'
```

## Configure

### Android

Initialize Kustomer in your `MainApplication` `onCreate` function:

```java
import com.kustomer.core.models.KusResult;
import com.kustomer.ui.Kustomer;
import android.util.Log;

public class MainApplication extends Application implements ReactApplication {
  @Override
  public void onCreate() {
    Kustomer.Companion.init(this, API_KEY, null, booleanKusResult -> {
      Log.i("Kustomer", "Init result: " + booleanKusResult);
      return null;
    });
  }
}
```

Add the following permissions to your `AndroidManifest.xml`:

```xml
<!--Required to make network requests-->
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.INTERNET"/>

<!--Required to take a picture and send as an attachment over chat-->

<uses-permission android:name="android.permission.CAMERA"/>

<queries>
    <intent>
        <action android:name="android.media.action.IMAGE_CAPTURE" />
    </intent>
    <intent>
        <action android:name="android.intent.action.GET_CONTENT" />

        <data android:mimeType="*/*" />
    </intent>
    <intent>
        <action android:name="android.intent.action.SEND" />

        <data android:mimeType="*/*" />
    </intent>
</queries>
```

To customize Kustomer's theme, add a new style in `colors.xml` overwriting the values you want to replace, for example:

```xml
<style name="KusAppTheme.Overlay" parent="KusAppTheme">
    <item name="kusColorHeader">#FFFFFF</item>
    <item name="kusColorOnHeader">#FFFFFF</item>
    <item name="kusColorOnHeaderVariant">#FFFFFF</item>

    <item name="kusColorAgentBubble">#FFFFFF</item>
    <item name="kusColorOnAgentBubble">#FFFFFF</item>
    <item name="kusColorUrlLinkOnAgentBubble">#FFFFFF</item>
    ...
</>
```

There are more possible items. For more information about Android customization: https://developer.kustomer.com/chat-sdk/v2-Android/docs/customize-colors-updated

### iOS

Create a file named `KustomerHelper.swift` with this content:

```swift
import Foundation
import KustomerChat
import UIKit

@objc
class KustomerHelper: NSObject {
    @objc
    func initKustomer(_ launchOptions: [UIApplication.LaunchOptionsKey: Any]?) {
        Kustomer.configure(apiKey: API_KEY, options: nil, launchOptions: launchOptions)
    }
}
```

Add to you `AppDelegate.m` file this import:

```objc
#import "your_project_name-Swift.h"
```

Initialize Kustomer in your `AppDelegate.m` `didFinishLaunchingWithOptions` function:

```objc
@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
  KustomerHelper *obj = [[KustomerHelper alloc] init];
  [obj initKustomer:launchOptions];
  return YES;
}
```

To customize Kustomer's theme follow the steps here: https://developer.kustomer.com/chat-sdk/v2-iOS/docs/customize-colors

## Usage

Import the library to use its methods:

```javascript
import KustomerSDK from "react-native-kustomer-sdk";
```

### Methods

#### identify(token: String): Promise<boolean>

Identify current user with a token, in order to recover its previous data, such as conversations and profile. This method is a promise so it will need some handling.

```javascript
KustomerSDK.identify(token).then(...);
```

#### describeCustomer(customerData: customerData): void

Add data to the customer profile. You can use some pre-defined fields, or create custom ones from the dashboard.

```javascript
KustomerSDK.describeCustomer(data);
```

#### resetTracking: void

Unlink the current identified user

```javascript
KustomerSDK.resetTracking();
```

#### presentSupport: void

Show Kustomer's chat support UI

```javascript
KustomerSDK.presentSupport();
```

### Types

#### customerData

The fields `email` and `phone` are mandatory. `custom` can contain any number of custom fields, which can be created from the Kustomer dashboard.

```javascript
type customerData {
  email: String,
  phone: String,
  custom: {String}
}
```
