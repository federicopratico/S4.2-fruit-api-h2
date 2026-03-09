package cat.itacademy.s04.t02.n01.fruitapih2.services;

import cat.itacademy.s04.t02.n01.fruitapih2.DTOs.CreateFruitDTO;
import cat.itacademy.s04.t02.n01.fruitapih2.DTOs.ResponseFruitDTO;
import cat.itacademy.s04.t02.n01.fruitapih2.model.Fruit;
import cat.itacademy.s04.t02.n01.fruitapih2.repository.FruitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class FruitServiceImplTest {

    @Mock
    private FruitRepository fruitRepository;

    @InjectMocks
    private FruitServiceImpl fruitService;

    @Test
    void createFruit_shouldReturnResponseFruitDTO() {
        CreateFruitDTO input = new CreateFruitDTO("Apple", 2);

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
}