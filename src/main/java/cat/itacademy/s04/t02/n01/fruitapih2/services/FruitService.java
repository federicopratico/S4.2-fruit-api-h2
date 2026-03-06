package cat.itacademy.s04.t02.n01.fruitapih2.services;

import cat.itacademy.s04.t02.n01.fruitapih2.DTOs.CreateFruitDTO;
import cat.itacademy.s04.t02.n01.fruitapih2.DTOs.ResponseFruitDTO;
import cat.itacademy.s04.t02.n01.fruitapih2.DTOs.UpdateFruitDTO;

import java.util.List;

public interface FruitService {
    ResponseFruitDTO createFruit(CreateFruitDTO createFruitDTO);

    ResponseFruitDTO update(UpdateFruitDTO updateFruitDTO);

    void delete(long id);

    List<ResponseFruitDTO> findAll();

    ResponseFruitDTO findFruitById(Long id);
}
