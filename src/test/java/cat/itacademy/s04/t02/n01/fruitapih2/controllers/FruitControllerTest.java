package cat.itacademy.s04.t02.n01.fruitapih2.controllers;

import cat.itacademy.s04.t02.n01.fruitapih2.DTOs.RequestFruitDTO;
import cat.itacademy.s04.t02.n01.fruitapih2.DTOs.ResponseFruitDTO;
import cat.itacademy.s04.t02.n01.fruitapih2.exception.ResourceNotFoundException;
import cat.itacademy.s04.t02.n01.fruitapih2.services.FruitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FruitController.class)
class FruitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FruitService fruitService;

    @Test
    void  addFruit_validInput_shouldReturnCreatedFruitAnd201() throws Exception {
        ResponseFruitDTO response = new ResponseFruitDTO(1L, "Watermelon", 4);

        when(fruitService.createFruit(any(RequestFruitDTO.class)))
                .thenReturn(response);

        String jsonBody = objectMapper.writeValueAsString(new RequestFruitDTO("Watermelon", 4));

        mockMvc.perform(post("/fruits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Watermelon"))
                .andExpect(jsonPath("$.weightInKilos").value(4));
    }

    @Test
    void addFruit_invalidName_shouldReturn400ThrownMethodArgumentNotValidException() throws Exception {

        RequestFruitDTO invalidInput = new RequestFruitDTO(" ", 4);
        String jsonBody = objectMapper.writeValueAsString(invalidInput);

        mockMvc.perform(post("/fruits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors.name").value("Provide a name for the new fruit"));


        verify(fruitService, never()).createFruit(any());
    }

    @Test
    void addFruit_invalidWeight_shouldReturn400ThrownMethodArgumentNotValidException() throws Exception {

        RequestFruitDTO invalidInput = new RequestFruitDTO("Apple", -3);
        String jsonBody = objectMapper.writeValueAsString(invalidInput);

        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors.weightInKilos").value("Weight must be positive"));


        verify(fruitService, never()).createFruit(any());
    }

    @Test
    void getAllFruits_whenThereAreFruits_shouldReturn200andAListOfResponseFruitDTO() throws Exception {

        List<ResponseFruitDTO> output = List.of(
                new ResponseFruitDTO(1L, "Apple", 2),
                new ResponseFruitDTO(2L, "Banana", 1)
        );

        when(fruitService.getAllFruits()).thenReturn(output);

        mockMvc.perform(get("/fruits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Apple"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Banana"));

    }

    @Test
    void getAllFruits_whenThereAreNoFruits_shouldReturn204TheEmptyList() throws Exception {

        when(fruitService.getAllFruits()).thenReturn(List.of());

        // ACT + ASSERT
        mockMvc.perform(get("/fruits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getFruitByID_whenFruitExists_shouldReturn200andTheFruitRequested() throws Exception {

        long id = 1L;
        ResponseFruitDTO output = new ResponseFruitDTO(id, "Apple", 2);

        when(fruitService.getFruitById(id)).thenReturn(output);

        mockMvc.perform(get("/fruits/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(output.id()))
                .andExpect(jsonPath("$.name").value(output.name()))
                .andExpect(jsonPath("$.weightInKilos").value(output.weightInKilos()));

        verify(fruitService).getFruitById(id);
    }

    @Test
    void getFruitByID_whenFruitDoesNotExists_shouldReturn404() throws Exception {

        long id = 1L;

        when(fruitService.getFruitById(id))
                .thenThrow(new ResourceNotFoundException("Fruit not found with id: " +id));

        mockMvc.perform(get("/fruits/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value("Fruit not found with id: " +id))
                .andExpect(jsonPath("$.errors").doesNotExist());

        verify(fruitService).getFruitById(id);
    }

    @Test
    void updateFruit_whenFruitExists_shouldReturn200andTheUpdatedFruit() throws Exception {

        long id = 1L;
        RequestFruitDTO input =  new RequestFruitDTO("New Fruit", 10);
        ResponseFruitDTO output = new ResponseFruitDTO(id, "New Fruit", 10);

        when(fruitService.updateFruit(eq(id), any(RequestFruitDTO.class))).thenReturn(output);

        String jsonBody = objectMapper.writeValueAsString(input);

        mockMvc.perform(put("/fruits/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(output.id()))
                .andExpect(jsonPath("$.name").value(output.name()))
                .andExpect(jsonPath("$.weightInKilos").value(output.weightInKilos()));

        verify(fruitService).updateFruit(eq(id), any(RequestFruitDTO.class));
    }

    @Test
    void updateFruit_whenFruitDoesNotExist_shouldReturn404() throws Exception {

        long id = 1L;
        RequestFruitDTO input =  new RequestFruitDTO("New Fruit", 10);

        when(fruitService.updateFruit(eq(id), any(RequestFruitDTO.class)))
                .thenThrow(new ResourceNotFoundException("Fruit to update not found with id: " + id));

        String jsonBody = objectMapper.writeValueAsString(input);

        mockMvc.perform(put("/fruits/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value("Fruit to update not found with id: " +id))
                .andExpect(jsonPath("$.errors").doesNotExist());

        verify(fruitService).updateFruit(eq(id), any(RequestFruitDTO.class));
    }

    @Test
    void updateFruit_invalidFruitName_shouldReturn400ThrownMethodArgumentNotValidException() throws Exception {

        long id = 1L;
        RequestFruitDTO invalidInput = new RequestFruitDTO(" ", 3);

        String jsonBody = objectMapper.writeValueAsString(invalidInput);

        mockMvc.perform(put("/fruits/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors.name").value("Provide a name for the new fruit"));


        verify(fruitService, never()).updateFruit(eq(id), any(RequestFruitDTO.class));
    }

    @Test
    void updateFruit_invalidInput_shouldReturn400AndThrownMethodArgumentNotValidException() throws Exception {

        long id = 1L;
        RequestFruitDTO invalidInput = new RequestFruitDTO("Apple", -50);

        String jsonBody = objectMapper.writeValueAsString(invalidInput);

        mockMvc.perform(put("/fruits/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors.weightInKilos").value("Weight must be positive"));


        verify(fruitService, never()).updateFruit(eq(id), any(RequestFruitDTO.class));
    }

    @Test
    void deleteFruit_whenFruitExists_shouldReturn204andDeleteFruitSuccessfully() throws Exception {
        long id = 1L;

        doNothing().when(fruitService).deleteFruit(id);

        mockMvc.perform(delete("/fruits/{id}", id))
                .andExpect(status().isNoContent());

        verify(fruitService).deleteFruit(id);
    }

    @Test
    void deleteFruit_whenFruitDoesNotExist_shouldReturn404ThrowResourceNotFoundException() throws Exception {
        long id = 1L;

        doThrow(new ResourceNotFoundException("Fruit to delete not found with id: " + id))
                .when(fruitService).deleteFruit(id);

        mockMvc.perform(delete("/fruits/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value("Fruit to delete not found with id: " + id))
                .andExpect(jsonPath("$.errors").doesNotExist());

        verify(fruitService).deleteFruit(id);
    }
}