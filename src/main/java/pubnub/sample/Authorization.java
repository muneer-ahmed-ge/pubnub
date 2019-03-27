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

    private static final String SECRET_KEY = "sec-c-MmQ2YjU4ZmEtOTVhNC00MzZjLTkzZjgtY2FmYmY4ZWM2Y2Yy";

    public static void main(String[] args) {

        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setPublishKey(PUBLISH_KEY);
        pnConfiguration.setSubscribeKey(SUBSCRIBE_KEY);
        pnConfiguration.setSecretKey(SECRET_KEY);
        pnConfiguration.setSecure(true);

        PubNub pubnub = new PubNub(pnConfiguration);

        pubnub.grant()
                .channels(Arrays.asList(CHANNEL)) //channels to allow grant on
                .write(false)
                .read(true) // allow keys to read the subscribe feed (false by default)
                .async(new PNCallback<PNAccessManagerGrantResult>() {
                    @Override
                    public void onResponse(PNAccessManagerGrantResult result, PNStatus status) {
                        log.debug("PNAccessManagerGrantResult result=" + result + " status=" + status);
                    }
                });

        pubnub.grant()
                .channels(Arrays.asList(CHANNEL)) //channels to allow grant on
                .authKeys(Arrays.asList(AUTH_KEY)) // the keys we are provisioning
                .read(true) // allow keys to read the subscribe feed (false by default)
                .write(true) // allow those keys to write (false by default)
                .ttl(15) // how long those keys will remain valid (0 for eternity)
                .async(new PNCallback<PNAccessManagerGrantResult>() {
                    @Override
                    public void onResponse(PNAccessManagerGrantResult result, PNStatus status) {
                        log.debug("PNAccessManagerGrantResult result=" + result + " status=" + status);
                    }
                });
    }
}
