#import "KustomerSDK.h"

#import <Kustomer/Kustomer.h>
#import <Kustomer/KUSNavigationBarView.h>
#import <Kustomer/KUSSessionsTableView.h>
#import <Kustomer/KUSChatSessionTableViewCell.h>
#import <Kustomer/KUSChatPlaceholderTableViewCell.h>
#import <Kustomer/KUSNewSessionButton.h>
#import <Kustomer/KUSChatTableView.h>
#import <Kustomer/KUSChatMessageTableViewCell.h>
#import <Kustomer/KUSTypingIndicatorTableViewCell.h>
#import <Kustomer/KUSInputBar.h>
#import <Kustomer/KUSRatingView.h>
#import <Kustomer/KUSEditFeedbackTableViewCell.h>
#import <Kustomer/KUSSatisfactionFormTableViewCell.h>
#import <Kustomer/KUSEmailInputView.h>
#import <Kustomer/KUSEndChatButtonView.h>

@interface UIColor (Hex)
+ (UIColor *)colorWithHex:(NSString *)hexStr;
@end

@implementation UIColor (Hex)
+ (UIColor *)colorWithHex:(NSString *)hexStr {
    unsigned int hexInt = 0;
    NSScanner *scanner = [NSScanner scannerWithString:hexStr];
    [scanner setCharactersToBeSkipped:[NSCharacterSet characterSetWithCharactersInString:@"#"]];
    [scanner scanHexInt:&hexInt];
    
    return [UIColor colorWithRed:((CGFloat)((hexInt & 0xFF0000) >> 16))/255.0
                           green:((CGFloat)((hexInt & 0xFF00) >> 8))/255.0
                            blue:((CGFloat)(hexInt & 0xFF))/255.0
                           alpha: 1.0];
}
@end

@implementation KustomerSDK

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(presentSupport)
{
    dispatch_async(dispatch_get_main_queue(), ^{
        [Kustomer presentSupport];
    });
}

RCT_EXPORT_METHOD(presentKnowledgeBase)
{
    dispatch_async(dispatch_get_main_queue(), ^{
        [Kustomer presentKnowledgeBase];
    });
}

RCT_REMAP_METHOD(openConversationsCount,
                 resolver: (RCTPromiseResolveBlock)resolve
                 rejecter: (RCTPromiseRejectBlock)reject)
{
    resolve(@([Kustomer openConversationsCount]));
}

RCT_EXPORT_METHOD(resetTracking)
{
    [Kustomer resetTracking];
}

RCT_EXPORT_METHOD(identify: (NSString *)hash)
{
    [Kustomer identify:hash callback:nil];
}

RCT_EXPORT_METHOD(setCurrentPageName: (NSString *)screen)
{
    [Kustomer setCurrentPageName:screen];
}

RCT_EXPORT_METHOD(describeCustomer: (NSDictionary *)data)
{
    KUSCustomerDescription *customerDescription = [[KUSCustomerDescription alloc] init];

    if ([data objectForKey:@"email"]) {
        customerDescription.email = [data objectForKey:@"email"];
    }
    if ([data objectForKey:@"phone"]) {
        customerDescription.phone = [data objectForKey:@"phone"];
    }

    if ([data objectForKey:@"custom"]) {
        customerDescription.custom = [data objectForKey:@"custom"];
    }

    [Kustomer describeCustomer:customerDescription];
}

RCT_EXPORT_METHOD(customLayout: (NSDictionary *)colors)
{
    if ([colors objectForKey:@"NavigationBar"]) {
        NSDictionary * navBar = [colors objectForKey:@"NavigationBar"];
        
        if ([navBar objectForKey:@"background"]){
            [[KUSNavigationBarView appearance] setBackgroundColor: [UIColor colorWithHex:[navBar objectForKey:@"background"]]];
        }

        if ([navBar objectForKey:@"tint"]){
            [[KUSNavigationBarView appearance] setTintColor: [UIColor colorWithHex:[navBar objectForKey:@"tint"]]];
        }

        if ([navBar objectForKey:@"name"]){
            [[KUSNavigationBarView appearance] setNameColor:[UIColor colorWithHex:[navBar objectForKey:@"name"]]];
        }

        if ([navBar objectForKey:@"greeting"]){
            [[KUSNavigationBarView appearance] setGreetingColor: [UIColor colorWithHex:[navBar objectForKey:@"greeting"]]];
        }
    }

    if ([colors objectForKey:@"SessionsTable"]) {
        NSDictionary * sessionsTable = [colors objectForKey:@"SessionsTable"];
        
        if ([sessionsTable objectForKey:@"background"]){
            [[KUSSessionsTableView appearance] setBackgroundColor: [UIColor colorWithHex:[sessionsTable objectForKey:@"background"]]];
        }
    }

    if ([colors objectForKey:@"ChatSessionTableCell"]) {
        NSDictionary * chatSession = [colors objectForKey:@"ChatSessionTableCell"];
        
        if ([chatSession objectForKey:@"background"]){
            [[KUSChatSessionTableViewCell appearance] setBackgroundColor: [UIColor colorWithHex:[chatSession objectForKey:@"background"]]];
        }

        if ([chatSession objectForKey:@"selectedBackground"]){
            [[KUSChatSessionTableViewCell appearance] setSelectedBackgroundColor: [UIColor colorWithHex:[chatSession objectForKey:@"selectedBackground"]]];
        }

        if ([chatSession objectForKey:@"title"]){
            [[KUSChatSessionTableViewCell appearance] setTitleColor: [UIColor colorWithHex:[chatSession objectForKey:@"title"]]];
        }

        if ([chatSession objectForKey:@"date"]){
            [[KUSChatSessionTableViewCell appearance] setDateColor: [UIColor colorWithHex:[chatSession objectForKey:@"date"]]];
        }

        if ([chatSession objectForKey:@"subtitle"]){
            [[KUSChatSessionTableViewCell appearance] setSubtitleColor: [UIColor colorWithHex:[chatSession objectForKey:@"subtitle"]]];
        }
    }

    if ([colors objectForKey:@"ChatPlaceholderTableCell"]) {
        NSDictionary * chatPlaceholder = [colors objectForKey:@"ChatPlaceholderTableCell"];
        
        if ([chatPlaceholder objectForKey:@"background"]){
            [[KUSChatPlaceholderTableViewCell appearance] setBackgroundColor: [UIColor colorWithHex:[chatPlaceholder objectForKey:@"background"]]];
        }

        if ([chatPlaceholder objectForKey:@"line"]){
            [[KUSChatPlaceholderTableViewCell appearance] setLineColor: [UIColor colorWithHex:[chatPlaceholder objectForKey:@"line"]]];
        }
    }

    if ([colors objectForKey:@"NewSessionButton"]) {
        NSDictionary * newSessionButton = [colors objectForKey:@"NewSessionButton"];
        
        if ([newSessionButton objectForKey:@"color"]){
            [[KUSNewSessionButton appearance] setColor: [UIColor colorWithHex:[newSessionButton objectForKey:@"color"]]];
        }
    }

    if ([colors objectForKey:@"ChatTable"]) {
        NSDictionary * chatTable = [colors objectForKey:@"ChatTable"];
        
        if ([chatTable objectForKey:@"background"]){
            [[KUSChatTableView appearance] setBackgroundColor: [UIColor colorWithHex:[chatTable objectForKey:@"background"]]];
        }
    }

    if ([colors objectForKey:@"ChatMessageTableCell"]) {
        NSDictionary * chatMessage = [colors objectForKey:@"ChatMessageTableCell"];
        
        if ([chatMessage objectForKey:@"background"]){
            [[KUSChatMessageTableViewCell appearance] setBackgroundColor: [UIColor colorWithHex:[chatMessage objectForKey:@"background"]]];
        }

        if ([chatMessage objectForKey:@"companyText"]){
            [[KUSChatMessageTableViewCell appearance] setCompanyTextColor: [UIColor colorWithHex:[chatMessage objectForKey:@"companyText"]]];
        }

        if ([chatMessage objectForKey:@"companyBubble"]){
            [[KUSChatMessageTableViewCell appearance] setCompanyBubbleColor: [UIColor colorWithHex:[chatMessage objectForKey:@"companyBubble"]]];
        }

        if ([chatMessage objectForKey:@"userBubble"]){
            [[KUSChatMessageTableViewCell appearance] setUserBubbleColor: [UIColor colorWithHex:[chatMessage objectForKey:@"userBubble"]]];
        }
    }

    if ([colors objectForKey:@"TypingIndicator"]) {
        NSDictionary * typingIndicator = [colors objectForKey:@"TypingIndicator"];
        
        if ([typingIndicator objectForKey:@"color"]){
            [[KUSTypingIndicatorTableViewCell appearance] setTypingIndicatorColor: [UIColor colorWithHex:[typingIndicator objectForKey:@"color"]]];
        }
    }

    if ([colors objectForKey:@"InputBar"]) {
        NSDictionary * inputBar = [colors objectForKey:@"InputBar"];
        
        if ([inputBar objectForKey:@"sendButton"]){
            [[KUSInputBar appearance] setSendButtonColor: [UIColor colorWithHex:[inputBar objectForKey:@"sendButton"]]];
        }

        if ([inputBar objectForKey:@"tint"]){
            [[KUSInputBar appearance] setTintColor: [UIColor colorWithHex:[inputBar objectForKey:@"tint"]]];
        }

        if ([inputBar objectForKey:@"placeholder"]){
            [[KUSInputBar appearance] setPlaceholderColor: [UIColor colorWithHex:[inputBar objectForKey:@"placeholder"]]];
        }

        if ([inputBar objectForKey:@"text"]){
            [[KUSInputBar appearance] setTextColor: [UIColor colorWithHex:[inputBar objectForKey:@"text"]]];
        }

        if ([inputBar objectForKey:@"background"]){
            [[KUSInputBar appearance] setBackgroundColor: [UIColor colorWithHex:[inputBar objectForKey:@"background"]]];
        }

        if ([inputBar objectForKey:@"keyboardAppearance"]){
            [[KUSInputBar appearance] setKeyboardAppearance: [[inputBar objectForKey:@"keyboardAppearance"]  isEqual: @"light"] ? UIKeyboardAppearanceLight : UIKeyboardAppearanceDark];
        }
    }
    
    if ([colors objectForKey:@"Rating"]) {
        NSDictionary * rating = [colors objectForKey:@"Rating"];
        
        if ([rating objectForKey:@"lowScaleLabel"]){
            [[KUSRatingView appearance] setLowScaleLabelColor: [UIColor colorWithHex:[rating objectForKey:@"lowScaleLabel"]]];
        }

        if ([rating objectForKey:@"highScaleLabel"]){
            [[KUSRatingView appearance] setHighScaleLabelColor: [UIColor colorWithHex:[rating objectForKey:@"highScaleLabel"]]];
        }
    }
    
    if ([colors objectForKey:@"FeedbackTableCell"]) {
        NSDictionary * feedback = [colors objectForKey:@"FeedbackTableCell"];
        
        if ([feedback objectForKey:@"feedbackText"]){
            [[KUSEditFeedbackTableViewCell appearance] setFeedbackTextColor: [UIColor colorWithHex:[feedback objectForKey:@"feedbackText"]]];
        }

        if ([feedback objectForKey:@"editText"]){
            [[KUSEditFeedbackTableViewCell appearance] setEditTextColor: [UIColor colorWithHex:[feedback objectForKey:@"editText"]]];
        }
    }
    
    if ([colors objectForKey:@"SatisfactionFormTableCell"]) {
        NSDictionary * satisfactionForm = [colors objectForKey:@"SatisfactionFormTableCell"];
        
        if ([satisfactionForm objectForKey:@"submitButtonBackground"]){
            [[KUSSatisfactionFormTableViewCell appearance] setSubmitButtonBackgroundColor: [UIColor colorWithHex:[satisfactionForm objectForKey:@"submitButtonBackground"]]];
        }

        if ([satisfactionForm objectForKey:@"submitButtonText"]){
            [[KUSSatisfactionFormTableViewCell appearance] setSubmitButtonTextColor: [UIColor colorWithHex:[satisfactionForm objectForKey:@"submitButtonText"]]];
        }
        
        if ([satisfactionForm objectForKey:@"commentQuestion"]){
            [[KUSSatisfactionFormTableViewCell appearance] setCommentQuestionColor: [UIColor colorWithHex:[satisfactionForm objectForKey:@"commentQuestion"]]];
        }
        
        if ([satisfactionForm objectForKey:@"commentBoxBorder"]){
            [[KUSSatisfactionFormTableViewCell appearance] setCommentBoxBorderColor: [UIColor colorWithHex:[satisfactionForm objectForKey:@"commentBoxBorder"]]];
        }
        
        if ([satisfactionForm objectForKey:@"satisfactionQuestion"]){
            [[KUSSatisfactionFormTableViewCell appearance] setSatisfactionQuestionColor: [UIColor colorWithHex:[satisfactionForm objectForKey:@"satisfactionQuestion"]]];
        }
        
        if ([satisfactionForm objectForKey:@"commentBoxText"]){
            [[KUSSatisfactionFormTableViewCell appearance] setCommentBoxTextColor: [UIColor colorWithHex:[satisfactionForm objectForKey:@"commentBoxText"]]];
        }
        
        if ([satisfactionForm objectForKey:@"introductionQuestion"]){
            [[KUSSatisfactionFormTableViewCell appearance] setIntroductionQuestionColor: [UIColor colorWithHex:[satisfactionForm objectForKey:@"introductionQuestion"]]];
        }
    }
    
    if ([colors objectForKey:@"Email"]) {
        NSDictionary * email = [colors objectForKey:@"Email"];
        
        if ([email objectForKey:@"background"]){
            [[KUSEmailInputView appearance] setBackgroundColor: [UIColor colorWithHex:[email objectForKey:@"background"]]];
        }

        if ([email objectForKey:@"border"]){
            [[KUSEmailInputView appearance] setBorderColor: [UIColor colorWithHex:[email objectForKey:@"border"]]];
        }
        
        if ([email objectForKey:@"prompt"]){
            [[KUSEmailInputView appearance] setPromptColor: [UIColor colorWithHex:[email objectForKey:@"prompt"]]];
        }
    }
    
    if ([colors objectForKey:@"EndChat"]) {
        NSDictionary * endChat = [colors objectForKey:@"EndChat"];

        if ([endChat objectForKey:@"background"]){
            [[KUSEndChatButtonView appearance] setBackgroundColor: [UIColor colorWithHex:[endChat objectForKey:@"background"]]];
        }

        if ([endChat objectForKey:@"text"]){
            [[KUSEndChatButtonView appearance] setTextColor: [UIColor colorWithHex:[endChat objectForKey:@"text"]]];
        }
        
        if ([endChat objectForKey:@"border"]){
            [[KUSEndChatButtonView appearance] setBorderColor: [UIColor colorWithHex:[endChat objectForKey:@"border"]]];
        }
    }
    
}

@end
