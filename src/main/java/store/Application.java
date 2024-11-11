package store;

import store.controller.StoreController;
import store.loader.ProductLoader;
import store.loader.PromotionLoader;
import store.model.Products;
import store.service.OrderService;
import store.service.PaymentService;
import store.service.PromotionService;
import store.validator.InputValidator;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        PromotionLoader promotionLoader = new PromotionLoader();
        ProductLoader productLoader = new ProductLoader(promotionLoader.load());
        Products loaded = productLoader.load();
        InputView inputView = new InputView(new InputValidator());
        OutputView outputView = new OutputView();
        OrderService orderService = new OrderService(loaded);
        PromotionService promotionService = new PromotionService(loaded);
        PaymentService paymentService = new PaymentService(loaded);
        StoreController storeController = new StoreController(inputView,orderService,
                promotionService,outputView,loaded,paymentService);

        storeController.start();
    }
}
