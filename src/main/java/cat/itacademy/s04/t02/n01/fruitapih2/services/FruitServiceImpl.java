package cat.itacademy.s04.t02.n01.fruitapih2.services;

import cat.itacademy.s04.t02.n01.fruitapih2.DTOs.CreateFruitDTO;
import cat.itacademy.s04.t02.n01.fruitapih2.DTOs.ResponseFruitDTO;
import cat.itacademy.s04.t02.n01.fruitapih2.DTOs.UpdateFruitDTO;
import cat.itacademy.s04.t02.n01.fruitapih2.model.Fruit;
import cat.itacademy.s04.t02.n01.fruitapih2.repository.FruitRepository;
import cat.itacademy.s04.t02.n01.fruitapih2.utils.FruitMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FruitServiceImpl implements FruitService {

    private final FruitRepository fruitRepository;

    public FruitServiceImpl(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }


    @Override
    public ResponseFruitDTO createFruit(CreateFruitDTO createFruitDTO) {

        Fruit newFruit = new Fruit();
        newFruit.setName(createFruitDTO.name());
        newFruit.setWeightInKilos(createFruitDTO.weightInKilos());

        Fruit createdFruit = fruitRepository.save(newFruit);

        return FruitMapper.toFruitDTO(createdFruit);
    }

    @Override
    public ResponseFruitDTO updateFruit(UpdateFruitDTO updateFruitDTO) {
        return null;
    }

    @Override
    public void deleteFruit(long id) {

    }

    @Override
    public List<ResponseFruitDTO> findAllFruits() {
        return fruitRepository.findAll()
                .stream()
                .map(FruitMapper::toFruitDTO)
                .toList();
    }

    @Override
    public ResponseFruitDTO findFruitById(Long id) {
        return null;
    }
}
