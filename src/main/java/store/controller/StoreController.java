package store.controller;

import store.loader.ProductLoader;
import store.loader.PromotionLoader;
import store.model.*;
import store.service.OrderService;
import store.service.PaymentService;
import store.service.PromotionService;
import store.view.InputView;
import store.view.OutputView;

import java.util.List;
import java.util.function.Supplier;

public class StoreController {
    private InputView inputView;
    private OrderService orderService;
    private PromotionService promotionService;
    private OutputView outputView;
    private Products products;
    private PaymentService paymentService;

    public StoreController(InputView inputView, OrderService orderService, PromotionService promotionService,
                           OutputView outputView, Products products, PaymentService paymentService) {
        this.products = products;
        this.inputView = inputView;
        this.orderService = orderService;
        this.promotionService = promotionService;
        this.outputView = outputView;
        this.paymentService = paymentService;
    }

    public void start() {
        boolean continueProgram = true;

        while (continueProgram) {
            outputView.welcomeMessage(products);
            if (!processOrderWithRetry()) {
                continue;
            }
            Receipt receipt = processMemberShip();
            outputView.printReceipt(receipt,orderService.getOrderItems(),
                    products);
            updateInventoryForOrders(orderService.getOrderItems());
            continueProgram = askForRestart();
        }

    }
    private boolean processOrderWithRetry(){
        while(true){
            try{
                List<Order> orders = createOrderWithRetry();
                if (processOrders(orders, products)) {
                    return false;
                }
                return true;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e);
                return false;
            }
        }
    }

    private void updateInventoryForOrders(List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            products.updateInventory(orderItem);
        }
    }

    private List<Order> createOrderWithRetry() {
        while (true) {
            try {
                String input = inputView.requestOrder();
                return Order.parseOrder(input);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e);
            }
        }
    }

    private boolean processOrders(List<Order> orders, Products products) {
        orderService.clearOrder();

        for (Order order : orders) {
            if (isProcessOrderFailed(order)) {
                return true;
            }
        }
        return false;
    }

    private boolean isProcessOrderFailed(Order order) {
        try {
            if (!processOrderAndPromotions(order)) {
                return true;
            }
            orderService.createAndAddOrderItem(order.getName());
            return false;
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
            return true;
        }
    }

    private boolean processOrderAndPromotions(Order order) {
        orderService.processOrder(order);

        if (hasPartialPromotion(order) && !handlePartialPromotion(order)) {
            return false;
        }

        if (hasRequiredPromotion(order)) {
            handleRequiredPromotion(order);
        }

        return true;
    }

    private boolean hasPartialPromotion(Order order) {
        return promotionService.canApplyPartialPromotion(
                order.getName(),
                order.getQuantity()
        );
    }

    private boolean hasRequiredPromotion(Order order) {
        return promotionService.requiresAdditionalProduct(
                order.getName(),
                order.getQuantity()
        );
    }

    private boolean handlePartialPromotion(Order order) {
        while (true) {
            try {
                PromotionResult result = PromotionResult.calculate(products, order.getName(), order.getQuantity());
                String input = inputView.requestPartialPromotion(order.getName(), result);
                if (input.equals("Y")) {
                    return true;
                }
                return false;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e);
            }
        }
    }

    private void handleRequiredPromotion(Order order) {
        while (true) {
            try {
                Product promotionProduct = products.findPromotionProduct(order.getName());
                String input = inputView.requestPlusOrder(promotionProduct);
                if (input.equals("Y")) {
                    orderService.updateOrder(order);
                }
                break;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e);
            }
        }
    }

    private Receipt processMemberShip() {
        boolean isMembershipApplied = retryWhileValid(() -> {
            String input = inputView.requestMembershipOption();
            return input.equals("Y");
        });
        return paymentService.createReceipt(orderService.getOrderItems(), isMembershipApplied);
    }

    private boolean askForRestart() {
        return retryWhileValid(() -> {
            String input = inputView.requestAdditionalOrder();
            return input.equals("Y");
        });
    }

    private <T> T retryWhileValid(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException exception) {
                outputView.printErrorMessage(exception);
            }
        }
    }
}
