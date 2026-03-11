package cat.itacademy.s04.t02.n01.fruitapih2.integration;

import cat.itacademy.s04.t02.n01.fruitapih2.DTOs.RequestFruitDTO;
import cat.itacademy.s04.t02.n01.fruitapih2.model.Fruit;
import cat.itacademy.s04.t02.n01.fruitapih2.repository.FruitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FruitIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FruitRepository repository;

    @Autowired
    ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void createFruit_validInput_shouldReturnCreatedFruit() throws Exception {

        RequestFruitDTO newFruit = new RequestFruitDTO("Apple", 3);
        String jsonBody = mapper.writeValueAsString(newFruit);

        mockMvc.perform(post("/fruits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(newFruit.name()))
                .andExpect(jsonPath("$.weightInKilos").value(newFruit.weightInKilos()));

        List<Fruit> fruits = repository.findAll();

        assertFalse(fruits.isEmpty());
        assertEquals(1, fruits.size());
        assertEquals(newFruit.name(), fruits.getFirst().getName());
        assertEquals(newFruit.weightInKilos(), fruits.getFirst().getWeightInKilos());
        assertNotNull(fruits.getFirst().getId());
    }

    @Test
    void deleteFruit_whenFruitExists_shouldReturn204AndDeleteFruitSuccessfully() throws Exception {

        Fruit existingFruit = repository.save(new Fruit(null, "Apple", 3));
        long  id = existingFruit.getId();

        mockMvc.perform(delete("/fruits/{id}", id))
                .andExpect(status().isNoContent());

        boolean exists = repository.findById(id).isPresent();
        assertFalse(exists);
    }

    @Test
    void updateFruit_existingId_shouldUpdateInDatabase() throws Exception {

        Fruit saved = repository.save(new Fruit(null, "Old Apple", 2));
        Long id = saved.getId();

        RequestFruitDTO updateDto = new RequestFruitDTO("New Apple", 10);
        String jsonBody = mapper.writeValueAsString(updateDto);

        mockMvc.perform(put("/fruits/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Apple"))
                .andExpect(jsonPath("$.weightInKilos").value(10));

        Fruit updated = repository.findById(id).orElseThrow();
        assertNotNull(updated.getId());
        assertEquals(updateDto.name(), updated.getName());
        assertEquals(updateDto.weightInKilos(), updated.getWeightInKilos());
    }

    @Test
    void getAllFruits_shouldReturnAllFruitsFromDatabase() throws Exception {

        repository.save(new Fruit(null, "Apple", 5));
        repository.save(new Fruit(null, "Banana", 3));

        mockMvc.perform(get("/fruits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.[0].id").exists())
                .andExpect(jsonPath("$.[0].name").value("Apple"))
                .andExpect(jsonPath("$.[0].weightInKilos").value(5))
                .andExpect(jsonPath("$.[1].id").exists())
                .andExpect(jsonPath("$.[1].name").value("Banana"))
                .andExpect(jsonPath("$.[1].weightInKilos").value(3));
    }

    @Test
    void getFruitById_existingId_shouldReturnFruit() throws Exception {

        Fruit saved = repository.save(new Fruit(null, "Apple", 5));
        Long id = saved.getId();

        mockMvc.perform(get("/fruits/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(saved.getName()))
                .andExpect(jsonPath("$.weightInKilos").value(saved.getWeightInKilos()));
    }


    @Test
    void getFruitById_nonExistingId_shouldReturn404() throws Exception {

        mockMvc.perform(get("/fruits/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void createFruit_invalidInput_shouldReturn400AndNotPersist() throws Exception {

        RequestFruitDTO newFruit = new RequestFruitDTO("", -3);
        String jsonBody = mapper.writeValueAsString(newFruit);

        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists());

        assertTrue(repository.findAll().isEmpty());
    }
}
