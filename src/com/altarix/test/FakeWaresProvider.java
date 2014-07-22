package com.altarix.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikhail on 22.07.14.
 */
public class FakeWaresProvider implements IWaresProvider {

    @Override
    public List<Ware> getWares() {
        ArrayList<Ware> wares = new ArrayList<Ware>();
        wares.add(new Ware("Тостер", WareType.APPLIANCES, 10));
        wares.add(new Ware("Стиральная машина", WareType.APPLIANCES, 5));
        wares.add(new Ware("Samsung Galaxy S", WareType.PHONE, 8));
        wares.add(new Ware("Лада Гранта", WareType.CAR, 4));
        wares.add(new Ware("IPhone 5", WareType.PHONE, 10));
        return wares;
    }
}
