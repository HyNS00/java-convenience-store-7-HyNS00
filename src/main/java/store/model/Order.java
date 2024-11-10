package store.model;

import store.utils.Converter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Order {
    private final String name;
    private final int quantity;

    public Order(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public static List<Order> parseOrder(String input) {
        List<List<String>> orderInfo = Converter.toOrderList(input);

        List<Order> orders = orderInfo.stream().map(Order::createOrder).toList();
        validateDuplicate(orders);
        return orders;
    }

    private static void validateDuplicate(List<Order> orders) {
        Set<String> productNames = new HashSet<>();
        for (Order order : orders) {
            if (!productNames.add(order.getName())) {
                throw new IllegalArgumentException("중복된 상품이 존재합니다.");
            }
        }
    }

    private static Order createOrder(List<String> orderInfo) {
        return new Order(orderInfo.get(0), Converter.toInt(orderInfo.get(1)));
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }


}
