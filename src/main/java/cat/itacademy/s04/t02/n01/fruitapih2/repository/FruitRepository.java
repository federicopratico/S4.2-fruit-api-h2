package cat.itacademy.s04.t02.n01.fruitapih2.repository;

import cat.itacademy.s04.t02.n01.fruitapih2.model.Fruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FruitRepository extends JpaRepository<Fruit, Long> {
}
