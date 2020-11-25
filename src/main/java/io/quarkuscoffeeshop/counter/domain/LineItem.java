package io.quarkuscoffeeshop.counter.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="LineItems")
public class LineItem extends PanacheEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="orderId",nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    private Item item;

    private String name;

    private BigDecimal price;

    public LineItem() {
    }

    public LineItem(Order order, Item item, String name, BigDecimal price) {
        this.order = order;
        this.item = item;
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LineItem lineItem = (LineItem) o;

        if (item != lineItem.item) return false;
        if (name != null ? !name.equals(lineItem.name) : lineItem.name != null) return false;
        return price != null ? price.equals(lineItem.price) : lineItem.price == null;
    }

    @Override
    public int hashCode() {
        int result = order.getOrderId() != null ? order.getOrderId().hashCode() : 0;
        result = 31 * result + (item != null ? item.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    public String getOrderId() {
        return order.getOrderId();
    }

    public Item getItem() {
        return item;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
