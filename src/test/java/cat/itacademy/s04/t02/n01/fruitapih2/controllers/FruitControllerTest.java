package cat.itacademy.s04.t02.n01.fruitapih2.controllers;

import cat.itacademy.s04.t02.n01.fruitapih2.DTOs.CreateFruitDTO;
import cat.itacademy.s04.t02.n01.fruitapih2.DTOs.ResponseFruitDTO;
import cat.itacademy.s04.t02.n01.fruitapih2.services.FruitService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

        when(fruitService.createFruit(any(CreateFruitDTO.class)))
                .thenReturn(response);

        String jsonBody = objectMapper.writeValueAsString(new CreateFruitDTO("Watermelon", 4));

        mockMvc.perform(post("/fruits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Watermelon"))
                .andExpect(jsonPath("$.weightInKilos").value(4));
    }

    @Test
    void addFruit_invalidName_shouldThrowInvalidInputException() throws Exception {

        CreateFruitDTO invalidInput = new CreateFruitDTO(" ", 4);
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
    void addFruit_invalidWeight_shouldThrowInvalidInputException() throws Exception {

        CreateFruitDTO invalidInput = new CreateFruitDTO("Apple", -3);
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
}