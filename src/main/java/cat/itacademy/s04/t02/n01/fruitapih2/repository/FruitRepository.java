package cat.itacademy.s04.t02.n01.fruitapih2.repository;

import cat.itacademy.s04.t02.n01.fruitapih2.model.Fruit;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface FruitRepository extends Repository<Fruit, Long> {
    Fruit save(Fruit fruit);

    Fruit update(Fruit fruit);

    void delete(long id);

    List<Fruit> findAll();

    Optional<Fruit> findFruitById(Long id);
}
