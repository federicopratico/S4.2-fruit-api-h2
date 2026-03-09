package cat.itacademy.s04.t02.n01.fruitapih2.utils;

import cat.itacademy.s04.t02.n01.fruitapih2.DTOs.ResponseFruitDTO;
import cat.itacademy.s04.t02.n01.fruitapih2.model.Fruit;

public class FruitMapper {

    public static ResponseFruitDTO toFruitDTO(Fruit fruit) {
        return new ResponseFruitDTO(fruit.getId(), fruit.getName(), fruit.getWeightInKilos());
    }
}
