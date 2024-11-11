package store;

import store.controller.StoreController;
import store.loader.ProductLoader;
import store.loader.PromotionLoader;
import store.model.Products;
import store.model.Promotions;
import store.model.Services;
import store.service.OrderService;
import store.service.PaymentService;
import store.service.PromotionService;
import store.validator.InputValidator;
import store.view.InputView;
import store.view.OutputView;

public class StoreFactory {

    public static StoreController create() {
        Products loaded = loadProducts();
        return createController(loaded);
    }

    private static Products loadProducts() {
        Promotions promotions = loadPromotions();
        return createProductLoader(promotions).load();
    }

    private static Promotions loadPromotions() {
        return new PromotionLoader().load();
    }

    private static ProductLoader createProductLoader(Promotions promotions) {
        return new ProductLoader(promotions);
    }

    private static StoreController createController(Products loaded) {
        return new StoreController(
                createInputView(),
                loaded,
                createServices(loaded),
                new OutputView()
        );
    }

    private static InputView createInputView() {
        return new InputView(new InputValidator());
    }

    private static Services createServices(Products loaded) {
        return new Services(
                new OrderService(loaded),
                new PromotionService(loaded),
                new PaymentService(loaded)
        );
    }
}
