import model.Registry;
import model.animals.Command;
import model.animals.PackAnimal;
import model.animals.Pet;

import java.time.LocalDate;
import java.util.ArrayList;

public class Controller {
    private Registry registry;

    public Controller(Registry registry) {
        this.registry = registry;
    }

    public ArrayList<Pet> getPets() {
        return registry.getPets();
    }

    public ArrayList<PackAnimal> getPackAnimals() {
        return registry.getPackAnimals();
    }

    public ArrayList<Command> getPetCommands(String petName) {
        return registry.getPetCommands(petName);
    }

    public ArrayList<Command> getPackAnimalCommands(String packAnimalName) {
        return registry.getPackAnimalCommands(packAnimalName);
    }

    public void addNewAnimal(String animalName, String animalType, LocalDate animalBirthday) {
        registry.addNewAnimal(animalName, animalType, animalBirthday);
    }

    public void addPetCommand(String petName, String commandName) {
        registry.addCommandToPet(petName,commandName);
    }

    public void addPackAnimalCommand(String packAnimalName, String commandName) {
        registry.addCommandToPackAnimal(packAnimalName, commandName);
    }
}