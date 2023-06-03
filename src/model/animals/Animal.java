package model.animals;

import java.time.LocalDate;

public abstract class Animal {
    private String animalName;
    private String animalType;
    private LocalDate animalBirthday;

    public Animal(String animalName, String animalType, LocalDate animalBirthday) {
        this.animalName = animalName;
        this.animalType = animalType;
        this.animalBirthday = animalBirthday;
    }

    public String getAnimalName() {
        return animalName;
    }

    public String getAnimalType() {
        return animalType;
    }

    public LocalDate getAnimalBirthday() {
        return animalBirthday;
    }
}