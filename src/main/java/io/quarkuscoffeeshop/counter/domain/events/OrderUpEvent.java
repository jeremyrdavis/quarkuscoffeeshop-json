package io.quarkuscoffeeshop.counter.domain.events;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.debezium.outbox.quarkus.ExportedEvent;
import io.quarkuscoffeeshop.counter.domain.OrderUp;

import java.time.Instant;

public class OrderUpEvent  implements ExportedEvent<String, JsonNode> {

    private static ObjectMapper mapper = new ObjectMapper();

    private static final String TYPE = "Order";

    private static final String EVENT_TYPE = "OrderUpEvent";

    private final JsonNode jsonNode;

    private final Instant timestamp;

    private String orderId;

    private String itemId;

    private OrderUpEvent(JsonNode jsonNode, Instant timestamp, String orderId, String itemId) {
        this.jsonNode = jsonNode;
        this.timestamp = timestamp;
        this.orderId = orderId;
    }

    public static OrderUpEvent from(final OrderUp orderUp) {

        Instant timestamp = Instant.now();

        ObjectNode asJson = mapper.createObjectNode()
                .put("orderId", orderUp.getOrderId())
                .put("itemId", orderUp.getItemId())
                .put("timestamp", timestamp.toString());

        return new OrderUpEvent(asJson, timestamp, orderUp.getOrderId(), orderUp.getItemId());
    }

    @Override
    public String getAggregateId() {
        return orderId;
    }

    @Override
    public String getAggregateType() {
        return TYPE;
    }

    @Override
    public String getType() {
        return EVENT_TYPE;
    }

    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public JsonNode getPayload() {
        return jsonNode;
    }
}
