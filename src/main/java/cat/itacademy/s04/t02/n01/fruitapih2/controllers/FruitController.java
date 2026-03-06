package cat.itacademy.s04.t02.n01.fruitapih2.controllers;

import cat.itacademy.s04.t02.n01.fruitapih2.DTOs.CreateFruitDTO;
import cat.itacademy.s04.t02.n01.fruitapih2.DTOs.ResponseFruitDTO;
import cat.itacademy.s04.t02.n01.fruitapih2.DTOs.UpdateFruitDTO;
import cat.itacademy.s04.t02.n01.fruitapih2.services.FruitService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FruitController {

    private final FruitService fruitService;

    public FruitController(FruitService fruitService) {
        this.fruitService = fruitService;
    }

    @PostMapping("/fruits")
    public ResponseFruitDTO add(@RequestBody CreateFruitDTO createFruitDTO) {
        return null;
    }

    @DeleteMapping("/fruits/{id}")
    public void delete(@PathVariable long id) {
    }

    @GetMapping("/fruits")
    public List<ResponseFruitDTO> getAll() {
        return null;
    }

    @GetMapping("/fruits/{id}")
    public ResponseFruitDTO getById(@PathVariable long id) {
        return null;
    }

    @PutMapping("/fruits/{id}")
    public ResponseFruitDTO update(@PathVariable long id, @RequestBody UpdateFruitDTO updateFruitDTO) {
        return null;
    }
}
