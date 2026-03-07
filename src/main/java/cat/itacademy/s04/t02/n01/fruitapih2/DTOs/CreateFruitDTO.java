package cat.itacademy.s04.t02.n01.fruitapih2.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateFruitDTO(
        @NotBlank(message = "Provide a name for the new fruit")
        String name,

        @NotNull(message = "Provide weight for the new fruit")
        int weightInKilos
) {}
