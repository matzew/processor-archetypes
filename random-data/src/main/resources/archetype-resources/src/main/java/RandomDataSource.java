package ${package};

import io.streamzi.cloudevents.CloudEvent;
import io.streamzi.cloudevents.CloudEventBuilder;
import io.streamzi.openshift.dataflow.annotations.CloudEventComponent;
import io.streamzi.openshift.dataflow.annotations.CloudEventComponentTimer;
import io.streamzi.openshift.dataflow.annotations.CloudEventProducer;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import io.streamzi.openshift.dataflow.annotations.CloudEventProducerTarget;

import java.util.logging.Logger;

@CloudEventComponent
public class RandomDataSource {

    private static final Logger logger = Logger.getLogger(RandomDataSource.class.getName());
    
    @CloudEventProducer(name = "OUTPUT_DATA")
    private CloudEventProducerTarget output;
       
    @CloudEventComponentTimer(interval = 1000)
    public void send(){
        final String eventId = UUID.randomUUID().toString();
        final URI src = URI.create("/trigger");
        final String eventType = "My.Cloud.Event.Type";

        final Map<String, String> contents = new HashMap<>();
        contents.put("value", Double.toString(Math.random()));

        final CloudEvent<Map<String, String>> simpleKeyValueEvent = new CloudEventBuilder()
                .eventType(eventType)
                .eventID(eventId)
                .source(src)
                .data(contents)
                .build();
        logger.info("sending a CloudEvent to the producer target...");
        output.send(simpleKeyValueEvent);
    }
}
