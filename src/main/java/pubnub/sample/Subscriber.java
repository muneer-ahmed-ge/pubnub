package pubnub.sample;


import java.util.Arrays;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
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
                log.debug("Subscribed to Channel [" + Arrays.asList(CHANNEL_NAME) +
                        "] status = " + status.getStatusCode());
                // log.debug("pubnub " + pubnub + " status=" + status);
            }

            public void message(PubNub pubnub, PNMessageResult message) {
                log.debug(message.getMessage());
                // parseSyncStatusMessage(message.getMessage().toString());
            }

            public void presence(PubNub pubnub, PNPresenceEventResult presence) {
                log.debug("pubnub " + pubnub + " presence=" + presence);
            }
        });

        pubnub.subscribe()
                .channels(Arrays.asList(CHANNEL_NAME))
                .execute();

    }

    private static void parseSyncStatusMessage(String message) {
        Map<String, Object> input = null;
        try {
            input = new ObjectMapper().readValue(message, Map.class);
        } catch (Exception excp) {
            log.error(excp);
            return;
        }
        Map<String, Object> meta = (Map<String, Object>) input.get("meta");
        StringBuilder builder = new StringBuilder();
        builder.append("status=").append(meta.get("status"));
        builder.append(" progressPercent=").append(meta.get("progressPercent"));
        builder.append(" attachmentProgressPercent=").append(meta.get("attachmentProgressPercent"));
        builder.append(" hasAttachments=").append(meta.get("hasAttachments"));
        builder.append(" attachmentStatus=").append(meta.get("attachmentStatus"));
        log.debug(builder.toString());
    }
}
