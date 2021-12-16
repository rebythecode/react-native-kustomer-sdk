declare module "react-native-kustomer-sdk" {
  class KustomerSDK {
    static identify(hash: string): Promise<boolean>;

    static presentSupport(): void;

    static openConversationsCount(): void;

    static resetTracking(): void;

    static describeCustomer(custom: {} | undefined): void;
  }
  export default KustomerSDK;
}
