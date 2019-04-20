package pubnub;


import java.util.Arrays;

import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

public class SubscriberSample {

    private static final String SUBSCRIBE_KEY = "sub-c-279f9278-442c-11e9-860e-caf3c7524d6c";

    private static final String CHANNEL_NAME = "GoApp-00D7A000000DH4GUAW-0057A000001xKZ4QAM.4d0a4520f48d6fd1af6cd8215731e92dc94875cec482a6acf1e8927c6e76c862";

    public static void main(String[] args) {

        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey(SUBSCRIBE_KEY);

        PubNub pubnub = new PubNub(pnConfiguration);

        pubnub.addListener(new SubscribeCallback() {

            public void status(PubNub pubnub, PNStatus status) {
                System.out.println("Subscribed to Channel [" + CHANNEL_NAME +
                        "] status = " + status.getStatusCode());

            }

            public void message(PubNub pubnub, PNMessageResult message) {
                System.out.println(message.getMessage());
            }

            public void presence(PubNub pubnub, PNPresenceEventResult presence) {
            }
        });

        pubnub.subscribe()
                .channels(Arrays.asList(CHANNEL_NAME))
                .execute();
    }
}
