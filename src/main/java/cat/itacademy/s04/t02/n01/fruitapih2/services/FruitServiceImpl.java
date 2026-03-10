package cat.itacademy.s04.t02.n01.fruitapih2.services;

import cat.itacademy.s04.t02.n01.fruitapih2.DTOs.RequestFruitDTO;
import cat.itacademy.s04.t02.n01.fruitapih2.DTOs.ResponseFruitDTO;
import cat.itacademy.s04.t02.n01.fruitapih2.exception.ResourceNotFoundException;
import cat.itacademy.s04.t02.n01.fruitapih2.model.Fruit;
import cat.itacademy.s04.t02.n01.fruitapih2.repository.FruitRepository;
import cat.itacademy.s04.t02.n01.fruitapih2.utils.FruitMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FruitServiceImpl implements FruitService {

    private final FruitRepository fruitRepository;

    public FruitServiceImpl(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }


    @Override
    public ResponseFruitDTO createFruit(RequestFruitDTO createFruitDTO) {

        Fruit newFruit = new Fruit();
        newFruit.setName(createFruitDTO.name());
        newFruit.setWeightInKilos(createFruitDTO.weightInKilos());

        Fruit createdFruit = fruitRepository.save(newFruit);

        return FruitMapper.toFruitDTO(createdFruit);
    }

    @Override
    public ResponseFruitDTO updateFruit(long id, RequestFruitDTO updateFruitDTO) {

        Fruit existentFruit = fruitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fruit to update not found with id: " +id));

        String newName = updateFruitDTO.name();
        int newWeight = updateFruitDTO.weightInKilos();

        Fruit updatedFruit = new Fruit(
                existentFruit.getId(),
                newName,
                newWeight);

        return FruitMapper.toFruitDTO(fruitRepository.save(updatedFruit));
    }

    @Override
    public void deleteFruit(long id) {

    }

    @Override
    public List<ResponseFruitDTO> getAllFruits() {
        return fruitRepository.findAll()
                .stream()
                .map(FruitMapper::toFruitDTO)
                .toList();
    }

    @Override
    public ResponseFruitDTO getFruitById(Long id) {
        return fruitRepository.findById(id)
                .map(FruitMapper::toFruitDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Fruit not found with id: " +id));
    }
}
