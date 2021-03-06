package pubnub.usecase;

import java.util.Arrays;

import com.google.gson.JsonObject;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.access_manager.PNAccessManagerGrantResult;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;
import org.apache.log4j.Logger;

public class UseCaseOne {

    final static Logger log = Logger.getLogger(UseCaseOne.class);

    public static void main(String[] args) throws Exception {
        String userId = "u1";
        String appName = "go";
        String deviceId = "d1";
        enableNotification(userId, appName, deviceId);
        Thread.sleep(15 * 1000);
        subscribe(userId, appName, deviceId);
        Thread.sleep(15 * 1000);
        publish(userId, appName, deviceId);
    }

    private static void enableNotification(String userId, String appName, String deviceId) {

        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setPublishKey(Constants.PUBLISH_KEY);
        pnConfiguration.setSubscribeKey(Constants.SUBSCRIBE_KEY);
        pnConfiguration.setSecretKey(Constants.SECRET_KEY);
        pnConfiguration.setSecure(true);

        final String channelName = userId + "-" + appName + "-" + deviceId;

        PubNub pubnub = new PubNub(pnConfiguration);

        // Channel level read
        pubnub.grant()
                .channels(Arrays.asList(channelName)) //channels to allow grant on
                .write(false)
                .read(true) // allow keys to read the subscribe feed (false by default)
                .async(new PNCallback<PNAccessManagerGrantResult>() {
                    @Override
                    public void onResponse(PNAccessManagerGrantResult result, PNStatus status) {
                        log.debug("Channel [" + channelName + "] created and granted [Channel level read] status = " + status.getStatusCode());
                        // log.debug("PNAccessManagerGrantResult result=" + result + " status=" + status);
                    }
                });

    }

    private static void subscribe(String userId, String appName, String deviceId) {

        final String channelName = userId + "-" + appName + "-" + deviceId;

        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey(Constants.SUBSCRIBE_KEY);

        PubNub pubnub = new PubNub(pnConfiguration);

        pubnub.addListener(new SubscribeCallback() {

            public void status(PubNub pubnub, PNStatus status) {
                // log.debug("pubnub " + pubnub + " status=" + status);
                log.debug("Subscribed to Channel [" + channelName + "] status = " + status.getStatusCode());
            }

            public void message(PubNub pubnub, PNMessageResult message) {
                // log.debug("pubnub " + pubnub + " message=" + message);
                log.debug("Received message = " + message.getMessage());
            }

            public void presence(PubNub pubnub, PNPresenceEventResult presence) {
                log.debug("presence = " + presence);
            }
        });

        pubnub.subscribe()
                .channels(Arrays.asList(channelName))
                .execute();
    }

    private static void publish(String userId, String appName, String deviceId) throws Exception {

        String authKey = "randomkey";
        final String channelName = userId + "-" + appName + "-" + deviceId;

        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setPublishKey(Constants.PUBLISH_KEY);
        pnConfiguration.setSubscribeKey(Constants.SUBSCRIBE_KEY);
        pnConfiguration.setSecretKey(Constants.SECRET_KEY);
        pnConfiguration.setSecure(true);
        PubNub pubnub = new PubNub(pnConfiguration);

        for (int i = 0; i < 10; i++) {

            // user level grant
            pubnub.grant()
                    .channels(Arrays.asList(channelName)) //channels to allow grant on
                    .authKeys(Arrays.asList(authKey)) // the keys we are provisioning
                    .read(true) // allow keys to read the subscribe feed (false by default)
                    .write(true) // allow those keys to write (false by default)
                    .ttl(15) // how long those keys will remain valid (0 for eternity)
                    .async(new PNCallback<PNAccessManagerGrantResult>() {
                        @Override
                        public void onResponse(PNAccessManagerGrantResult result, PNStatus status) {
                            log.debug("Channel [" + channelName + "] created and granted [User level read/write] status = " + status.getStatusCode());
                            // log.debug("PNAccessManagerGrantResult result=" + result + " status=" + status);
                        }
                    });
            Thread.sleep(10 * 1000);

            // create message payload using Gson
            JsonObject messageJsonObject = new JsonObject();
            messageJsonObject.addProperty("msg", "Counter = " + i);
            pubnub.publish()
                    .message(messageJsonObject)
                    .channel(channelName)
                    .async(new PNCallback<PNPublishResult>() {
                        @Override
                        public void onResponse(PNPublishResult result, PNStatus status) {
                            log.debug("Published message status = " + status.getStatusCode());
                            // log.debug("Published result=" + result + " status=" + status);
                            // handle publish result, status always present, result if successful
                            // status.isError to see if error happened
                        }
                    });
            Thread.sleep(10 * 1000);

            // user level grant
            pubnub.grant()
                    .channels(Arrays.asList(channelName)) //channels to allow grant on
                    .authKeys(Arrays.asList(authKey)) // the keys we are provisioning
                    .read(false) // allow keys to read the subscribe feed (false by default)
                    .write(false) // allow those keys to write (false by default)
                    .ttl(15) // how long those keys will remain valid (0 for eternity)
                    .async(new PNCallback<PNAccessManagerGrantResult>() {
                        @Override
                        public void onResponse(PNAccessManagerGrantResult result, PNStatus status) {
                            log.debug("Channel [" + channelName + "] created and revoked [User level read/write] status = " + status.getStatusCode());
                            // log.debug("PNAccessManagerGrantResult result=" + result + " status=" + status);
                        }
                    });

            Thread.sleep(10 * 1000);
        }

    }
}
