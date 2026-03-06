package cat.itacademy.s04.t02.n01.fruitapih2.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@EqualsAndHashCode
public class Fruit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter private Long id;

    @Getter private String name;

    @Getter private int weightInKilos;
}
