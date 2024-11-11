package store;

import store.controller.StoreController;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        runStore();
    }
    private static void runStore(){
        try{
            StoreController storeController = StoreFactory.create();
            storeController.start();
        }catch (IllegalArgumentException  e) {
            System.err.println(e.getMessage());
        }catch (RuntimeException e){
            System.err.println(e.getMessage());
        }
    }
}
