package cat.itacademy.s04.t02.n01.fruitapih2.services;

import cat.itacademy.s04.t02.n01.fruitapih2.DTOs.CreateFruitDTO;
import cat.itacademy.s04.t02.n01.fruitapih2.DTOs.ResponseFruitDTO;
import cat.itacademy.s04.t02.n01.fruitapih2.DTOs.UpdateFruitDTO;
import cat.itacademy.s04.t02.n01.fruitapih2.model.Fruit;
import cat.itacademy.s04.t02.n01.fruitapih2.repository.FruitRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class FruitServiceImpl implements FruitService {

    private final FruitRepository fruitRepository;
    private final ObjectMapper objectMapper;

    public FruitServiceImpl(FruitRepository fruitRepository, ObjectMapper objectMapper) {
        this.fruitRepository = fruitRepository;
        this.objectMapper = objectMapper;
    }


    @Override
    public ResponseFruitDTO createFruit(CreateFruitDTO createFruitDTO) {

        Fruit newFruit = objectMapper.convertValue(createFruitDTO, Fruit.class);
        Fruit createdFruit = fruitRepository.save(newFruit);

        return objectMapper.convertValue(createdFruit, ResponseFruitDTO.class);
    }

    @Override
    public ResponseFruitDTO update(UpdateFruitDTO updateFruitDTO) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<ResponseFruitDTO> findAll() {
        return List.of();
    }

    @Override
    public ResponseFruitDTO findFruitById(Long id) {
        return null;
    }
}
