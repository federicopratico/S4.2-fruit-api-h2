package cat.itacademy.s04.t02.n01.fruitapih2.controllers;

import cat.itacademy.s04.t02.n01.fruitapih2.dtos.RequestFruitDTO;
import cat.itacademy.s04.t02.n01.fruitapih2.dtos.ResponseFruitDTO;
import cat.itacademy.s04.t02.n01.fruitapih2.services.FruitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseFruitDTO> createFruit(@Valid @RequestBody RequestFruitDTO createFruitDTO) {

        ResponseFruitDTO response = fruitService.createFruit(createFruitDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFruit(@PathVariable long id) {
        fruitService.deleteFruit(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<ResponseFruitDTO>> getAllFruits() {

        List<ResponseFruitDTO> response = fruitService.getAllFruits();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseFruitDTO> getFruitById(@PathVariable long id) {
        ResponseFruitDTO response = fruitService.getFruitById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseFruitDTO> updateFruit(@PathVariable long id, @Valid @RequestBody RequestFruitDTO updateFruitDTO) {
        ResponseFruitDTO response = fruitService.updateFruit(id, updateFruitDTO);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
