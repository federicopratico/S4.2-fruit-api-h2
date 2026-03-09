package cat.itacademy.s04.t02.n01.fruitapih2.controllers;

import cat.itacademy.s04.t02.n01.fruitapih2.DTOs.CreateFruitDTO;
import cat.itacademy.s04.t02.n01.fruitapih2.DTOs.ResponseFruitDTO;
import cat.itacademy.s04.t02.n01.fruitapih2.DTOs.UpdateFruitDTO;
import cat.itacademy.s04.t02.n01.fruitapih2.services.FruitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fruits")
public class FruitController {

    private final FruitService fruitService;

    public FruitController(FruitService fruitService) {
        this.fruitService = fruitService;
    }

    @PostMapping()
    public ResponseEntity<ResponseFruitDTO> addFruit(@Validated @RequestBody CreateFruitDTO createFruitDTO) {

        ResponseFruitDTO response = fruitService.createFruit(createFruitDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public void deleteFruit(@PathVariable long id) {
    }

    @GetMapping()
    public ResponseEntity<List<ResponseFruitDTO>> getAllFruits() {

        List<ResponseFruitDTO> response = fruitService.findAllFruits();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseFruitDTO getFruitById(@PathVariable long id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseFruitDTO updateFruit(@PathVariable long id, @RequestBody UpdateFruitDTO updateFruitDTO) {
        return null;
    }
}
