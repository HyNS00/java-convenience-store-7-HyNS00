package store.validator;

import store.model.Products;

public class OrderValidator {

    public static void validateProductExists(Products products, String name){
        if(products.findProduct(name).isEmpty()){
            throw new IllegalArgumentException("존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }
    }

    public static  void validateOrderQuantity(Products products, String name,int orderQuantity){
        int totalQuantity = products.getTotalQuantity(name);
        if(totalQuantity < orderQuantity) {
            throw new IllegalArgumentException("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }
}
