package io.quarkuscoffeeshop.json.infrastructure;

import io.quarkuscoffeeshop.counter.domain.*;
import io.quarkuscoffeeshop.counter.domain.commands.PlaceOrderCommand;
import io.quarkuscoffeeshop.counter.domain.events.OrderInEvent;
import io.quarkuscoffeeshop.counter.domain.events.OrderUpEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

@Path("/json")
@Produces(MediaType.APPLICATION_JSON)
public class JsonResource {

    Logger logger = LoggerFactory.getLogger(JsonResource.class);

    @GET
    @Path("/PlaceOrderCommand")
    public Response placeOrderCommand() {

        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());

        return Response.ok(new PlaceOrderCommand(
                order.getOrderId(),
                Location.ATLANTA,
                null,
                OrderSource.WEB,
                new ArrayList<LineItem>(){{
                    add(new LineItem(
                            order,
                            Item.CAPPUCCINO,
                            "Goofy",
                            BigDecimal.valueOf(3.50)
                    ));
                }},
                new ArrayList< LineItem >(){{
                    add(new LineItem(
                            order,
                            Item.CAKEPOP,
                            "Goofy",
                            BigDecimal.valueOf(2.75)
                    ));
                }},
                BigDecimal.valueOf(6.25)
        )).build();
    }

    @GET
    @Path("/OrderInEvent")
    public Response orderInEvent() {

        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());

        PlaceOrderCommand placeOrderCommand = new PlaceOrderCommand(
                order.getOrderId(),
                Location.ATLANTA,
                null,
                OrderSource.WEB,
                new ArrayList< LineItem >(){{
                    add(new LineItem(
                            order,
                            Item.CAPPUCCINO,
                            "Goofy",
                            BigDecimal.valueOf(3.50)
                    ));
                }},
                new ArrayList< LineItem >(){{
                    add(new LineItem(
                            order,
                            Item.CAKEPOP,
                            "Goofy",
                            BigDecimal.valueOf(2.75)
                    ));
                }},
                BigDecimal.valueOf(6.25)
        );

        OrderInEvent orderInEvent = OrderInEvent.from(placeOrderCommand);
        logger.debug("returning {}", orderInEvent.getPayload());
        return Response.ok(orderInEvent.getPayload()).build();
    }

    @GET
    @Path("/OrderUpEvent")
    public Response orderUpEvent() {

        OrderUp orderUp = new OrderUp(UUID.randomUUID().toString(), UUID.randomUUID().toString(), Item.CAPPUCCINO, "Minnie");

        OrderUpEvent orderUpEvent = OrderUpEvent.from(orderUp);
        return Response.ok(orderUpEvent.getPayload()).build();
    }
}
