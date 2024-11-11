package store.model;

import store.service.OrderService;
import store.service.PaymentService;
import store.service.PromotionService;

public class Services {
    private final OrderService orderService;
    private final PromotionService promotionService;
    private final PaymentService paymentService;

    public Services(OrderService orderService, PromotionService promotionService, PaymentService paymentService) {
        this.orderService = orderService;
        this.promotionService = promotionService;
        this.paymentService = paymentService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public PromotionService getPromotionService() {
        return promotionService;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }
}
