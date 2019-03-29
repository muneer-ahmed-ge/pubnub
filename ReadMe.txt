https://www.pubnub.com/docs/java-se-java/api-reference-access-manager

Authorization
1. Application level privileges are based on subscribeKey applying to all associated channels
2. Channel level privileges are based on a combination of subscribeKey and channel name
3. User level privileges are based on the combination of subscribeKey, channel and auth_key


Secret Key
https://www.pubnub.com/docs/java-se-java/data-streams-publish-and-subscribe
Although a secretKey is also provided to you along with your publish and subscribe keys in the admin portal, it is not required for plain-old publish and subscribe.
You'll only need the secretKey if you are using PAM functionality, which we discuss more in the PAM Feature Tutorial.


