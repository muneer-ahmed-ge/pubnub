package pubnub.sample;


import java.util.Arrays;

import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;
import org.apache.log4j.Logger;

public class Subscriber implements Constants {

    final static Logger log = Logger.getLogger(Publisher.class);

    public static void main(String[] args) {

        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey(SUBSCRIBE_KEY);

        PubNub pubnub = new PubNub(pnConfiguration);

        pubnub.addListener(new SubscribeCallback() {

            public void status(PubNub pubnub, PNStatus status) {
                log.debug("Subscribed to Channel [" + CHANNEL + "] status = " + status.getStatusCode());
                // log.debug("pubnub " + pubnub + " status=" + status);
            }

            public void message(PubNub pubnub, PNMessageResult message) {
                // log.debug("pubnub " + pubnub + " message=" + message);
                log.debug("Received message = [" + message.getMessage() + "] from Channel [" + CHANNEL + "]");
            }

            public void presence(PubNub pubnub, PNPresenceEventResult presence) {
                log.debug("pubnub " + pubnub + " presence=" + presence);
            }
        });

        pubnub.subscribe()
                .channels(Arrays.asList(CHANNEL))
                .execute();

    }
}
