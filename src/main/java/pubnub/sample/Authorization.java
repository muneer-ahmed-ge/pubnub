package pubnub.sample;


import java.util.Arrays;

import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.access_manager.PNAccessManagerGrantResult;
import org.apache.log4j.Logger;

public class Authorization implements Constants {

    final static Logger log = Logger.getLogger(Authorization.class);

    public static void main(String[] args) throws Exception {

        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setPublishKey(PUBLISH_KEY);
        pnConfiguration.setSubscribeKey(SUBSCRIBE_KEY);
        pnConfiguration.setSecretKey(SECRET_KEY);
        pnConfiguration.setSecure(true);

        PubNub pubnub = new PubNub(pnConfiguration);

        pubnub.grant()
                .channels(Arrays.asList(CHANNEL_NAME))
                .read(true) // allow keys to read the subscribe feed (false by default)
                .write(false) // allow those keys to write (false by default)
                .ttl(0)
                .async(new PNCallback<PNAccessManagerGrantResult>() {
                    @Override
                    public void onResponse(PNAccessManagerGrantResult result, PNStatus status) {
                        // log.debug("PNAccessManagerGrantResult result=" + result + " status=" + status);
                        log.debug("clean global level permissions status=" + status.getStatusCode());
                    }
                });
        Thread.sleep(5 * 1000);

        // clean the global level permission
//        pubnub.grant()
//                .channels(Arrays.asList(""))
//                .read(false) // allow keys to read the subscribe feed (false by default)
//                .write(false) // allow those keys to write (false by default)
//                .ttl(0)
//                .async(new PNCallback<PNAccessManagerGrantResult>() {
//                    @Override
//                    public void onResponse(PNAccessManagerGrantResult result, PNStatus status) {
//                        // log.debug("PNAccessManagerGrantResult result=" + result + " status=" + status);
//                        log.debug("clean global level permissions status=" + status.getStatusCode());
//                    }
//                });
//        Thread.sleep(5 * 1000);

        // Channel Level Grant
//        pubnub.grant()
//                .channels(Arrays.asList(CHANNEL))
//                .read(true) // allow keys to read the subscribe feed (false by default)
//                .write(false) // allow those keys to write (false by default)
//                .async(new PNCallback<PNAccessManagerGrantResult>() {
//                    @Override
//                    public void onResponse(PNAccessManagerGrantResult result, PNStatus status) {
//                        log.debug("Granted Channel [" + CHANNEL + "] [Channel level read] status = " + status.getStatusCode());
////                        log.debug("PNAccessManagerGrantResult result=" + result + " status=" + status);
//                    }
//                });
//        Thread.sleep(5 * 1000);

        // User Level Grant
//        pubnub.grant()
//                .channels(Arrays.asList(CHANNEL)) //channels to allow grant on
//                .authKeys(Arrays.asList(AUTH_KEY)) // the keys we are provisioning
//                .read(true) // allow keys to read the subscribe feed (false by default)
//                .write(true) // allow those keys to write (false by default)
//                .ttl(5) // how long those keys will remain valid (0 for eternity)
//                .async(new PNCallback<PNAccessManagerGrantResult>() {
//                    @Override
//                    public void onResponse(PNAccessManagerGrantResult result, PNStatus status) {
//                        log.debug("Granted Channel [" + CHANNEL + "] [User level read/write] Auth Key = " + AUTH_KEY + " status = " + status.getStatusCode());
//
//                    }
//                });
//        Thread.sleep(10 * 1000);
    }
}
