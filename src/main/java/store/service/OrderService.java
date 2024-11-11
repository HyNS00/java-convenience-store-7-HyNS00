package store.service;

import store.model.Order;
import store.model.OrderItem;
import store.model.Products;
import store.model.PromotionResult;
import store.validator.OrderValidator;

import java.util.*;

public class OrderService {
    private final Products products;
    private Map<String, Order> orderMap = new HashMap<>();
    private List<OrderItem> orderItems = new ArrayList<>();

    public OrderService(Products products) {
        this.products = products;
    }

    public void processOrder(Order order) {
        validateOrder(order);
        orderMap.put(order.getName(), order);
    }

    private void validateOrder(Order order) {
        OrderValidator.validateProductExists(products, order.getName());
        OrderValidator.validateOrderQuantity(products, order.getName(), order.getQuantity());
    }

    public void createAndAddOrderItem(String productName) {
        Order order = orderMap.get(productName);
        PromotionResult result = PromotionResult.calculate(products, order.getName(), order.getQuantity());
        orderItems.add(new OrderItem(order.getName(), result));
    }

    public void updateOrder(Order order) {
        orderMap.put(order.getName(), new Order(order.getName(), order.getQuantity() + 1));
    }

    public List<OrderItem> getOrderItems() {
        return Collections.unmodifiableList(orderItems);
    }

    public void clearOrder() {
        orderMap.clear();
        orderItems.clear();
    }
}
