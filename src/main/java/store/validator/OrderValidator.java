package store.validator;

import store.enums.ExceptionMessage;
import store.model.Products;

public class OrderValidator {

    public static void validateProductExists(Products products, String name){
        if(products.findProduct(name).isEmpty()){
            throw new IllegalArgumentException(ExceptionMessage.PRODUCT_NOT_FOUND.getMessage());
        }
    }

    public static  void validateOrderQuantity(Products products, String name,int orderQuantity){
        int totalQuantity = products.getTotalQuantity(name);
        if(totalQuantity < orderQuantity) {
            throw new IllegalArgumentException(ExceptionMessage.EXCEED_STOCK_QUANTITY.getMessage());
        }
    }
}
