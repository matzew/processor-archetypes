package ${package};

import io.streamzi.cloudevents.CloudEvent;
import io.streamzi.openshift.dataflow.annotations.CloudEventComponent;
import io.streamzi.openshift.dataflow.annotations.CloudEventConsumer;

import java.util.logging.Logger;

@CloudEventComponent
public class MyLogger {

    private static final Logger logger = Logger.getLogger(MyLogger.class.getName());

    @CloudEventConsumer(name = "INPUT_DATA")
    public void onCloudEvent(final CloudEvent evt) {

        evt.getData().ifPresent(data -> {
            logger.info("Payload: " + data);
        });
    }
}
