package io.quarkuscoffeeshop.counter.domain.commands;

import io.quarkuscoffeeshop.counter.domain.LineItem;
import io.quarkuscoffeeshop.counter.domain.Location;
import io.quarkuscoffeeshop.counter.domain.OrderSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

public class PlaceOrderCommand {

    private final CommandType commandType = CommandType.PLACE_ORDER;

    private String orderId;

    private Location locationId;

    private String loyaltyMemberId;

    private OrderSource orderSource;

    List<LineItem> baristaItems;

    List<LineItem> kitchenItems;

    private BigDecimal total;

    public PlaceOrderCommand() {
    }

    public PlaceOrderCommand(String orderId, Location locationId, String loyaltyMemberId, OrderSource orderSource, List<LineItem> baristaItems, List<LineItem> kitchenItems, BigDecimal total) {
        this.orderId = orderId;
        this.locationId = locationId;
        this.loyaltyMemberId = loyaltyMemberId;
        this.orderSource = orderSource;
        this.baristaItems = baristaItems;
        this.kitchenItems = kitchenItems;
        this.total = total;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PlaceOrderCommand.class.getSimpleName() + "[", "]")
                .add("commandType=" + commandType)
                .add("id='" + orderId + "'")
                .add("storeId='" + locationId + "'")
                .add("rewardsId='" + loyaltyMemberId + "'")
                .add("orderSource=" + orderSource)
                .add("baristaItems=" + baristaItems)
                .add("kitchenItems=" + kitchenItems)
                .add("total=" + total)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlaceOrderCommand that = (PlaceOrderCommand) o;

        if (commandType != that.commandType) return false;
        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (locationId != null ? !locationId.equals(that.locationId) : that.locationId != null) return false;
        if (loyaltyMemberId != null ? !loyaltyMemberId.equals(that.loyaltyMemberId) : that.loyaltyMemberId != null) return false;
        if (orderSource != that.orderSource) return false;
        if (baristaItems != null ? !baristaItems.equals(that.baristaItems) : that.baristaItems != null) return false;
        if (kitchenItems != null ? !kitchenItems.equals(that.kitchenItems) : that.kitchenItems != null) return false;
        return total != null ? total.equals(that.total) : that.total == null;
    }

    @Override
    public int hashCode() {
        int result = commandType != null ? commandType.hashCode() : 0;
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (locationId != null ? locationId.hashCode() : 0);
        result = 31 * result + (loyaltyMemberId != null ? loyaltyMemberId.hashCode() : 0);
        result = 31 * result + (orderSource != null ? orderSource.hashCode() : 0);
        result = 31 * result + (baristaItems != null ? baristaItems.hashCode() : 0);
        result = 31 * result + (kitchenItems != null ? kitchenItems.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        return result;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public String getOrderId() {
        return orderId;
    }

    public Location getLocationId() {
        return locationId;
    }

    public Optional<String> getLoyaltyMemberId() {
        return Optional.ofNullable(loyaltyMemberId);
    }

    public OrderSource getOrderSource() {
        return orderSource;
    }

    public Optional<List<LineItem>> getBaristaItems() {
        return Optional.ofNullable(baristaItems);
    }

    public Optional<List<LineItem>> getKitchenItems() {
        return Optional.ofNullable(kitchenItems);
    }

    public BigDecimal getTotal() {
        return total;
    }
}
