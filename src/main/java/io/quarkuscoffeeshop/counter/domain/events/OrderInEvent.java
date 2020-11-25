package io.quarkuscoffeeshop.counter.domain.events;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.debezium.outbox.quarkus.ExportedEvent;
import io.quarkuscoffeeshop.counter.domain.LineItem;
import io.quarkuscoffeeshop.counter.domain.Location;
import io.quarkuscoffeeshop.counter.domain.OrderSource;
import io.quarkuscoffeeshop.counter.domain.commands.PlaceOrderCommand;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class OrderInEvent implements ExportedEvent<String, JsonNode> {

    private static ObjectMapper mapper = new ObjectMapper();

    private static final String TYPE = "Order";

    private static final String EVENT_TYPE = "OrderInEvent";

    private final JsonNode jsonNode;

    private final Instant timestamp;

    private String orderId;

    private Location locationId;

    private OrderSource orderSource;

    private String loyaltyMemberId;

    List<LineItem> baristaItems;

    List<LineItem> kitchenItems;

    private BigDecimal total;

    private OrderInEvent(JsonNode jsonNode, Instant timestamp, String orderId, Location locationId, OrderSource orderSource, Optional<String> loyaltyMemberId, Optional<List<LineItem>> baristaItems, Optional<List<LineItem>> kitchenItems, BigDecimal total) {
        this.jsonNode = jsonNode;
        this.timestamp = timestamp;
        this.orderId = orderId;
        this.locationId = locationId;
        this.orderSource = orderSource;
        if (loyaltyMemberId.isPresent()) {
            this.loyaltyMemberId = loyaltyMemberId.get();
        }
        if (baristaItems.isPresent()) {
            this.baristaItems = baristaItems.get();
        }
        if (kitchenItems.isPresent()) {
            this.kitchenItems = kitchenItems.get();
        }
        this.total = total;
    }

    public static OrderInEvent from(final PlaceOrderCommand placeOrderCommand) {

        Instant timestamp = Instant.now();

        ObjectNode asJson = mapper.createObjectNode()
                .put("orderId", placeOrderCommand.getOrderId())
                .put("orderSource", placeOrderCommand.getOrderSource().toString())
                .put("timestamp", timestamp.toString());

        if (placeOrderCommand.getLoyaltyMemberId().isPresent()) {
            asJson.put("loyaltyMemberId", placeOrderCommand.getLoyaltyMemberId().get());
        }

        if (placeOrderCommand.getBaristaItems().isPresent()) {
            ArrayNode baristaLineItems = asJson.putArray("baristaLineItems") ;
            for (LineItem lineItem : placeOrderCommand.getBaristaItems().get()) {
                ObjectNode lineAsJon = mapper.createObjectNode()
                        .put("orderId", lineItem.getOrderId())
                        .put("item", lineItem.getItem().toString())
                        .put("name", lineItem.getName())
                        .put("price", lineItem.getPrice());
                baristaLineItems.add(lineAsJon);
            }
        }

        if (placeOrderCommand.getKitchenItems().isPresent()) {
            ArrayNode kitchenLineItems = asJson.putArray("kitchenLineItems") ;
            for (LineItem lineItem : placeOrderCommand.getKitchenItems().get()) {
                ObjectNode lineAsJon = mapper.createObjectNode()
                        .put("orderId", lineItem.getOrderId())
                        .put("item", lineItem.getItem().toString())
                        .put("name", lineItem.getName())
                        .put("price", lineItem.getPrice());
                kitchenLineItems.add(lineAsJon);
            }
        }

        return new OrderInEvent(
                asJson,
                timestamp,
                placeOrderCommand.getOrderId(),
                placeOrderCommand.getLocationId(),
                placeOrderCommand.getOrderSource(),
                placeOrderCommand.getLoyaltyMemberId(),
                placeOrderCommand.getBaristaItems(),
                placeOrderCommand.getKitchenItems(),
                placeOrderCommand.getTotal());

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
