package cat.itacademy.s04.t02.n01.fruitapih2.services;

import cat.itacademy.s04.t02.n01.fruitapih2.dtos.RequestFruitDTO;
import cat.itacademy.s04.t02.n01.fruitapih2.dtos.ResponseFruitDTO;

import java.util.List;

public interface FruitService {
    ResponseFruitDTO createFruit(RequestFruitDTO createFruitDTO);

    ResponseFruitDTO updateFruit(long id, RequestFruitDTO updateFruitDTO);

    void deleteFruit(long id);

    List<ResponseFruitDTO> getAllFruits();

    ResponseFruitDTO getFruitById(Long id);
}
