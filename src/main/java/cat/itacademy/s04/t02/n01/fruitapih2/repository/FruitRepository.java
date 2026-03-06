package cat.itacademy.s04.t02.n01.fruitapih2.repository;

import cat.itacademy.s04.t02.n01.fruitapih2.model.Fruit;
import org.springframework.data.repository.CrudRepository;

public interface FruitRepository extends CrudRepository<Fruit, Long> {
}
