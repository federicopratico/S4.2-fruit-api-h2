package cat.itacademy.s04.t02.n01.fruitapih2.DTOs;

import jakarta.validation.constraints.Positive;

public record UpdateFruitDTO(
        @Positive(message = "Weight must be positive")
        int weightInKilos
) {}
