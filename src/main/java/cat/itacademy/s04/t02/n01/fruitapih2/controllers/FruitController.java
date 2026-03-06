package cat.itacademy.s04.t02.n01.fruitapih2.controllers;

import cat.itacademy.s04.t02.n01.fruitapih2.DTOs.FruitDTO;
import cat.itacademy.s04.t02.n01.fruitapih2.model.Fruit;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FruitController {

    @PostMapping("/fruits")
    public Fruit add(@RequestBody FruitDTO fruitDTO) {
        return new Fruit();
    }

    @DeleteMapping("/fruits/{id}")
    public void delete(@PathVariable long id) {

    }

    @GetMapping("/fruits")
    public List<Fruit> getAll() {
        return new ArrayList<>();
    }

    @GetMapping("/fruits/{id}")
    public Fruit getById(@PathVariable long id) {
        return new Fruit();
    }

    @PutMapping("/fruits/{id}")
    public Fruit update(@PathVariable long id, @RequestBody FruitDTO fruitDTO) {
        return new Fruit();
    }
}
