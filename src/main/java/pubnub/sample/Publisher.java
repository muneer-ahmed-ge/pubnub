package pubnub.sample;


import com.google.gson.JsonObject;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.models.consumer.PNPublishResult;
import org.apache.log4j.Logger;

public class Publisher implements Constants {

    final static Logger log = Logger.getLogger(Publisher.class);

    private static final String CHANNEL_NAME = "GoApp-00D7A000000DH4GUAW-0057A000001xKZ4QAM.4d0a4520f48d6fd1af6cd8215731e92dc94875cec482a6acf1e8927c6e76c862";

    public static void main(String[] args) throws Exception {

        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setPublishKey(PUBLISH_KEY);
        pnConfiguration.setSubscribeKey(SUBSCRIBE_KEY);
        pnConfiguration.setSecretKey(SECRET_KEY);

        PubNub pubnub = new PubNub(pnConfiguration);

        // create message payload using Gson
        JsonObject messageJsonObject = new JsonObject();
        messageJsonObject.addProperty("msg", "Allah Help Me !");


        PNPublishResult result = pubnub.publish()
                .message(messageJsonObject)
                .channel(CHANNEL_NAME)
                .sync();

        System.out.println(result);

//        pubnub.publish()
//                .message(messageJsonObject)
//                .channel(CHANNEL_NAME)
//                .async(new PNCallback<PNPublishResult>() {
//                    @Override
//                    public void onResponse(PNPublishResult result, PNStatus status) {
//                        log.debug("Published message = message to Channel ["
//                                + CHANNEL_NAME + "] status = " + status.getStatusCode());
//                    }
//                });
    }
}
