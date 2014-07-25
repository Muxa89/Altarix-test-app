package com.altarix.test;

/**
 * Created by mikhail on 22.07.14.
 */
public class FakeWaresProvider implements IWaresProvider {

    @Override
    public WareStorage getWareStorage() {
        WareStorage storage = new WareStorage();
        storage.add(new Ware("Тостер", WareType.APPLIANCES, 100), 10);
        storage.add(new Ware("Стиральная машина", WareType.APPLIANCES, 200), 5);
        storage.add(new Ware("Samsung Galaxy S", WareType.PHONE, 300), 8);
        storage.add(new Ware("Лада Гранта", WareType.CAR, 400), 4);
        storage.add(new Ware("IPhone 5", WareType.PHONE, 500), 10);
        return storage;
    }
}
