package pubnub.sample;


import static pubnub.usecase.Constants.SECRET_KEY;

import com.google.gson.JsonObject;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;
import org.apache.log4j.Logger;

public class Publisher implements Constants {

    final static Logger log = Logger.getLogger(Publisher.class);


    public static void main(String[] args) throws Exception {

        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setPublishKey(PUBLISH_KEY);
        pnConfiguration.setSubscribeKey(SUBSCRIBE_KEY);
        pnConfiguration.setSecretKey(SECRET_KEY);
        //pnConfiguration.setAuthKey(AUTH_KEY);

        PubNub pubnub = new PubNub(pnConfiguration);

        for (int i = 0; i < 1000; i++) {
            publish(pubnub, "Counter " + i);
            Thread.sleep(10 * 1000);
        }
    }

    private static void publish(PubNub pubnub, final String message) {
        // create message payload using Gson
        JsonObject messageJsonObject = new JsonObject();
        messageJsonObject.addProperty("msg", message);
        pubnub.publish()
                .message(messageJsonObject)
                .channel(CHANNEL)
                .async(new PNCallback<PNPublishResult>() {
                    @Override
                    public void onResponse(PNPublishResult result, PNStatus status) {
                        log.debug("Published message = [" + message + "] to Channel ["
                                + CHANNEL + "] status = " + status.getStatusCode());
//                        log.debug("Published result=" + result + " status=" + status);
                        // handle publish result, status always present, result if successful
                        // status.isError to see if error happened
                    }
                });
    }
}
