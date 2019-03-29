https://www.pubnub.com/docs/java-se-java/api-reference-access-manager

Authorization
1. Application level privileges are based on subscribeKey applying to all associated channels
2. Channel level privileges are based on a combination of subscribeKey and channel name
3. User level privileges are based on the combination of subscribeKey, channel and auth_key


Secret Key
https://www.pubnub.com/docs/java-se-java/data-streams-publish-and-subscribe
Although a secretKey is also provided to you along with your publish and subscribe keys in the admin portal, it is not required for plain-old publish and subscribe.
You'll only need the secretKey if you are using PAM functionality, which we discuss more in the PAM Feature Tutorial.


Push Notifications
==================
A Push Notification is a type of notification that forces the display of a new message to the user’s current interface.
1. Register device to receive push notifications for a channel say 'muneer-<iphone-device-id>'
2. Publish a message that contains APNS/GCM tags to channel 'muneer-<iphone-device-id>'
3. PubNub will hand the contents to APNS or GCM along with all of the device_ids registered
4. Registered device will receive push notifications on that channel

Send Message
============

What is PubNub ?
- PubNub delivers data to each device simultaneously (less than 1/4 second on average).
- Device has to be active

What is APNS ?
- Apple Push Notification System (APNS) is designed to send data to one device at a time, with no guarantees on latency
- APNS is ideal for waking up the app when the app is not being currently used (i.e. inactive, background, not running)

How to use APNS and PubNub together ?
When APNS and PubNub are used together, APNS is used for alerts to a user, and when the app is launched,
it connects to a PubNub channel for very fast connectivity and data synchronization across the audience of people using the app.

When does PubNub send a message vs push notification ?
When you publish a message with mobile push payload attributes (pn_apns and pn_gcm), the specified mobile push messages
will always be sent to all devices registered for mobile push notifications on the channel that the message was published to.
https://support.pubnub.com/support/solutions/articles/14000043585-when-does-pubnub-send-a-message-vs-push-notification-

How to register for push notifications ?
iOS Mobile Push Gateway Tutorial for Realtime Apps
https://www.pubnub.com/docs/ios-objective-c/mobile-gateway#retrieving-mobile-device-ids-ios-10


What should a user do on a mobile phone to receive the push notifications?
User must accept the receipt of push notifications for the app.

References:
https://www.pubnub.com/developers/tech/push-notifications/
https://www.pubnub.com/learn/glossary/what-is-a-push-notification/

Q&A
https://support.pubnub.com/support/solutions/folders/14000109557