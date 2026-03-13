package cat.itacademy.s04.t02.n01.fruitapih2.services;

import cat.itacademy.s04.t02.n01.fruitapih2.dtos.RequestFruitDTO;
import cat.itacademy.s04.t02.n01.fruitapih2.dtos.ResponseFruitDTO;
import cat.itacademy.s04.t02.n01.fruitapih2.exception.ResourceNotFoundException;
import cat.itacademy.s04.t02.n01.fruitapih2.model.Fruit;
import cat.itacademy.s04.t02.n01.fruitapih2.repository.FruitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class FruitServiceImplTest {

    @Mock
    private FruitRepository fruitRepository;

    @InjectMocks
    private FruitServiceImpl fruitService;

    @Test
    void createFruit_shouldReturnResponseFruitDTO() {
        RequestFruitDTO input = new RequestFruitDTO("Apple", 2);

        when(fruitRepository.save(any(Fruit.class)))
                .thenReturn(new Fruit(1L, "Apple", 2));

        ResponseFruitDTO result = fruitService.createFruit(input);

        assertNotNull(result);
        assertNotNull(result.id());
        assertEquals(1L, result.id());
        assertEquals(input.name(), result.name());
        assertEquals(input.weightInKilos(), result.weightInKilos());

        verify(fruitRepository).save(any(Fruit.class));
    }

    @Test
    void getAllFruits_whenDBisNotEmpty_shouldReturnListOfResponseFruitDTO() {
        Fruit apple = new Fruit(1L, "Apple", 1);
        Fruit peach = new Fruit(2L, "Peach", 2);

        List<Fruit> fruitList = List.of(apple, peach);

        when(fruitRepository.findAll()).thenReturn(fruitList);

        List<ResponseFruitDTO> result = fruitService.getAllFruits();

        assertFalse(result.isEmpty());
        assertEquals(2, result.size());

        ResponseFruitDTO first = result.getFirst();
        assertEquals(1L, first.id());
        assertEquals("Apple", first.name());
        assertEquals(1, first.weightInKilos());

        ResponseFruitDTO second = result.get(1);
        assertEquals(2L, second.id());
        assertEquals("Peach", second.name());
        assertEquals(2, second.weightInKilos());

        verify(fruitRepository).findAll();
    }

    @Test
    void getAllFruits_whenDBIsEmpty_shouldReturnTheEmptyList() {

        when(fruitRepository.findAll()).thenReturn(List.of());

        List<ResponseFruitDTO> result = fruitService.getAllFruits();

        assertTrue(result.isEmpty());
        assertEquals(0, result.size());

        verify(fruitRepository).findAll();
    }

    @Test
    void getFruitById_whenIdExists_shouldReturnTheFruit() {
        long id = 1L;
        Optional<Fruit> output = Optional.of(new Fruit(id, "Apple", 1));

        when(fruitRepository.findById(id)).thenReturn(output);

        ResponseFruitDTO result = fruitService.getFruitById(1L);

        assertNotNull(result);
        assertEquals(output.get().getId(), result.id());
        assertEquals(output.get().getName(), result.name());
        assertEquals(output.get().getWeightInKilos(), result.weightInKilos());

        verify(fruitRepository).findById(id);
    }

    @Test
    void getFruitById_whenIdDoesNotExists_shouldThrowResourceNotFoundException() {
        long id = 1L;
        when(fruitRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> fruitService.getFruitById(id));
        verify(fruitRepository).findById(id);
    }

    @Test
    void updateFruit_whenIdExists_shouldReturnUpdatedFruit() {
        long id = 1L;
        RequestFruitDTO input = new RequestFruitDTO("New Banana", 1);

        Fruit existentFruit = new Fruit(1L, "Old Banana", 4);
        Fruit updatedFruit = new Fruit(1L, "New Banana", 1);

        when(fruitRepository.findById(id)).thenReturn(Optional.of(existentFruit));
        when(fruitRepository.save(any(Fruit.class))).thenReturn(updatedFruit);

        ResponseFruitDTO result = fruitService.updateFruit(id, input);

        assertNotNull(result);
        assertEquals(id, result.id());
        assertEquals(input.name(), result.name());
        assertEquals(input.weightInKilos(), result.weightInKilos());

        verify(fruitRepository).findById(id);
        verify(fruitRepository).save(any(Fruit.class));
    }

    @Test
    void updateFruit_whenIdDoesNotExist_shouldThrowResourceNotFoundException() {
        long id = 1L;
        RequestFruitDTO input = new RequestFruitDTO("Dummy Fruit", 1000);

        when(fruitRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> fruitService.updateFruit(id, input));

        verify(fruitRepository).findById(id);
        verify(fruitRepository, never()).save(any(Fruit.class));
    }

    @Test
    void deleteFruit_whenIdExists_shouldDeleteFruitSuccessfully() {
        long id = 1L;
        Fruit existingFruit = new Fruit(id, "Apple", 2);

        when(fruitRepository.findById(id)).thenReturn(Optional.of(existingFruit));

        fruitService.deleteFruit(id);

        verify(fruitRepository).findById(id);
        verify(fruitRepository).delete(existingFruit);
    }

    @Test
    void deleteFruit_whenIdDoesNotExist_shouldThrowResourceNotFoundException() {
        long id = 1L;

        when(fruitRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> fruitService.deleteFruit(id));

        verify(fruitRepository).findById(id);
        verify(fruitRepository, never()).delete(any(Fruit.class));
    }
}