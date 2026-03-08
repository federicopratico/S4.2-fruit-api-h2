package cat.itacademy.s04.t02.n01.fruitapih2.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateFruitDTO(
        @NotBlank(message = "Provide a name for the new fruit")
        String name,

        @Positive(message = "Weight must be positive")
        int weightInKilos
) {}
