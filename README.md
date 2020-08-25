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

`gradle implementation 'com.kustomer.kustomersdk:kustomersdk:0.3.7'`

#### Maven

```xml
<dependency>
  <groupId>com.kustomer.kustomersdk</groupId>
  <artifactId>kustomersdk</artifactId>
  <version>0.3.7</version>
  <type>pom</type>
</dependency>
```

### iOS

#### CocoaPods

The preferred installation method is with [CocoaPods](https://cocoapods.org). Add the following to your `Podfile`:

```ruby
pod 'Kustomer', :git => 'https://github.com/kustomer/customer-ios.git', :tag => '0.3.9'
```

#### Carthage

For [Carthage](https://github.com/Carthage/Carthage), add the following to your `Cartfile`:

```ogdl
github "kustomer/customer-ios" ~> 0.3.9
```

## Configure

### Android

Add the following three activities to your `AndroidManifest.xml`:

```xml
<activity android:name="com.kustomer.kustomersdk.Activities.KUSSessionsActivity" android:configChanges="orientation|screenSize|keyboardHidden" android:theme="@style/CustomKustomerTheme" />
<activity android:name="com.kustomer.kustomersdk.Activities.KUSChatActivity" android:configChanges="orientation|screenSize|keyboardHidden" android:theme="@style/CustomKustomerTheme" />
<activity android:name="com.kustomer.kustomersdk.Activities.KUSKnowledgeBaseActivity" android:configChanges="orientation|screenSize|keyboardHidden" android:theme="@style/CustomKustomerTheme" />
```

Initialize Kustomer in your `MainApplication` `onCreate` function:

```java
import com.kustomer.kustomersdk.Kustomer;

public class MainApplication extends Application implements ReactApplication {
  @Override
  public void onCreate() {
    Kustomer.init(this, API_KEY);
  }
}
```

To customize Kustomer's theme, add a new style in `styles.xml` overwriting the values you want to replace:

```xml
<style name="MySupportTheme" parent="CustomKustomerTheme">
  <item name="colorPrimary">@color/kusToolbarBackgroundColor</item>
  <item name="colorPrimaryDark">@color/kusStatusBarColor</item>
  <item name="colorAccent">@color/kusColorAccent</item>
  <item name="kus_back_image">@drawable/ic_arrow_back_black_24dp</item>
  <item name="kus_dismiss_image">@drawable/ic_close_black_24dp</item>
  <item name="kus_new_session_button_image">@drawable/ic_edit_white_20dp</item>
</style>
```

To edit Kustomer's UI colors, add the desired color to be replaced in `colors.xml`:

```xml
<!-- CHAT HISTORY SCREEN -->
<color name="kusToolbarBackgroundColor">#000000</color>
<color name="kusStatusBarColor">#000000</color>
<color name="kusToolbarTintColor">#DD2C00</color>
<color name="kusSessionListBackground">#909090</color>
<color name="kusSessionItemBackground">#909090</color>
<color name="kusSessionItemSelectedBackground">#55FFFFFF</color>
<color name="kusSessionTitleColor">#FFFFFF</color>
<color name="kusSessionDateColor">#FFFFFF</color>
<color name="kusSessionSubtitleColor">#FFFFFF</color>
<color name="kusSessionUnreadColor">#FFFFFF</color>
<color name="kusSessionUnreadBackground">#3F51B5</color>
<color name="kusSessionPlaceholderBackground">#909090</color>
<color name="kusSessionPlaceholderLineColor">#55FFFFFF</color>
<color name="kusNewSessionButtonColor">#DD2C00</color>
<color name="kusNewSessionTextColor">#FFFFFF</color>

<!-- CHAT SCREEN -->
<color name="kusToolbarNameColor">#FFFFFF</color>
<color name="kusToolbarGreetingColor">#FFFFFF</color>
<color name="kusToolbarSeparatorColor">#BDBDBD</color>
<color name="kusToolbarUnreadTextColor">#FFFFFF</color>
<color name="kusToolbarUnreadBackground">#aacc0000</color>
<color name="kusEmailInputBackground">#FFFFFF</color>
<color name="kusEmailInputBorderColor">#DD2C00</color>
<color name="kusEmailInputPromptColor">#FFFFFF</color>
<color name="kusChatListBackground">#909090</color>
<color name="kusChatItemBackground">#909090</color>
<color name="kusChatTimestampTextColor">#FFFFFF</color>
<color name="kusCompanyBubbleColor">#000000</color>
<color name="kusCompanyTextColor">#FFFFFF</color>
<color name="kusUserBubbleColor">#DD2C00</color>
<color name="kusUserTextColor">#000000</color>
<color name="kusSendButtonColor">#DD2C00</color>
<color name="kusInputBarTintColor">#DD2C00</color>
<color name="kusInputBarHintColor">#EEEEEE</color>
<color name="kusInputBarTextColor">#FFFFFF</color>
<color name="kusInputBarBackground">#000000</color>
<color name="kusInputBarSeparatorColor">#BDBDBD</color>
<color name="kusInputBarAttachmentIconColor">#FFFFFF</color>
<color name="kusOptionPickerSeparatorColor">#BDBDBD</color>
<color name="kusOptionPickerButtonBorderColor">#2962FF</color>
<color name="kusOptionPickerButtonTextColor">#2962FF</color>
<color name="kusOptionPickerButtonBackground">#F5F5F5</color>

<!-- SATISFACTION FORM -->
<color name="kusCSatRatingPromptTextColor">#FFFFFF</color>
<color name="kusCSatQuestionsTextColor">#FFFFFF</color>
<color name="kusCSatRatingLabelsTextColor">#FFFFFF</color>
<color name="kusCSatFeedbackTextColor">#FFFFFF</color>
<color name="kusCSatEditTextColor">#DD2C00</color>
<color name="kusCSatCommentBorderColor">#FFFFFF</color>
<color name="kusCSatCommentInputTextColor">#FFFFFF</color>
<color name="kusCSatSubmitButtonColor">#DD2C00</color>
<color name="kusCSatSubmitTextColor">#000000</color>
```

### iOS

Initialize Kustomer in your `AppDelegate.m` `didFinishLaunchingWithOptions` function:

```objc
#import <Kustomer/Kustomer.h>

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
  [Kustomer initializeWithAPIKey:@"API_KEY"];
  return YES;
}
```

## Usage

Import the library to use its methods:

```javascript
import KustomerSDK from "react-native-kustomer-sdk";
```

### Methods

#### identify(token: String): void

Identify current user with a token, in order to recover its previous data, such as conversations and profile

```javascript
KustomerSDK.identify(token);
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

#### openConversationsCount: Promise<String>

Get how many open conversations the user has

```javascript
await KustomerSDK.openConversationsCount();
```

#### presentKnowledgeBase: void

Show Kustomer's Knowledge Base (FAQ) website in a browser modal

```javascript
KustomerSDK.presentKnowledgeBase();
```

#### setCurrentPageName(screen: String): void

```javascript
KustomerSDK.setCurrentPageName(screen);
```

#### customLayout(layout: layout): void (iOS ONLY)

Customize Kustomer's UI using your own style. For Android, check the Configure section above.

```javascript
KustomerSDK.customLayout(layout);
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

#### layout

All values are `String` that contain a HEX color (`#FFFFFF`), except for `keyboardAppearance`, which should be either set to `light` or `dark`.
Use the fields for which you want to replace the color, the default color will be used if the field is missing.

```javascript
type layout {
    NavigationBar: {
        background: String,
        tint: String,
        name: String,
        greeting: String
    },
    SessionsTable: {
        background: String
    },
    ChatSessionTableCell: {
        background: String,
        selectedBackground: String,
        title: String,
        date: String,
        subtitle: String
    },
    ChatPlaceholderTableCell: {
        background: String,
        line: String
    },
    NewSessionButton: {
        color: String
    },
    ChatTable: {
        background: String
    },
    ChatMessageTableCell: {
        background: String,
        companyText: String,
        companyBubble: String,
        userBubble: String
    },
    TypingIndicator: {
        color: String
    },
    InputBar: {
        sendButton: String,
        tint: String,
        placeholder: String,
        text: String,
        background: String,
        keyboardAppearance: 'light' | 'dark'
    },
    Rating: {
        lowScaleLabel: String,
        highScaleLabel: String
    },
    FeedbackTableCell: {
        feedbackText: String,
        editText: String
    },
    SatisfactionFormTableCell: {
        submitButtonBackground: String,
        submitButtonText: String,
        commentQuestion: String,
        commentBoxBorder: String,
        satisfactionQuestion: String,
        commentBoxText: String,
        introductionQuestion: String
    },
    Email: {
        background: String,
        border: String,
        prompt: String
    },
    EndChat: {
        background: String,
        text: String,
        border: String
    }
}
```
