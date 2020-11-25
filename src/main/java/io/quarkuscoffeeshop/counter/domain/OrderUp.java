package io.quarkuscoffeeshop.counter.domain;

import java.util.StringJoiner;

public class OrderUp {

    private String orderId;

    private String itemId;

    private Item item;

    private String name;

    public OrderUp(String orderId, String itemId, Item item, String name) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.item = item;
        this.name = name;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OrderUp.class.getSimpleName() + "[", "]")
                .add("orderId='" + orderId + "'")
                .add("itemId='" + itemId + "'")
                .add("item=" + item)
                .add("name='" + name + "'")
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderUp orderUp = (OrderUp) o;

        if (orderId != null ? !orderId.equals(orderUp.orderId) : orderUp.orderId != null) return false;
        if (itemId != null ? !itemId.equals(orderUp.itemId) : orderUp.itemId != null) return false;
        if (item != orderUp.item) return false;
        return name != null ? name.equals(orderUp.name) : orderUp.name == null;
    }

    @Override
    public int hashCode() {
        int result = orderId != null ? orderId.hashCode() : 0;
        result = 31 * result + (itemId != null ? itemId.hashCode() : 0);
        result = 31 * result + (item != null ? item.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
