package model;

import Databases.PackAnimalDatabase;
import Databases.PetDatabase;
import model.animals.Animal;
import model.animals.Command;
import model.animals.PackAnimal;
import model.animals.Pet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Registry {
    private PetDatabase petDB;
    private PackAnimalDatabase packAnimalDB;
    private Reader reader;

    public Registry(String availablePetsFileName, String availablePackAnimalsFilename) {
        this.petDB = new PetDatabase();
        this.packAnimalDB = new PackAnimalDatabase();
        this.reader = new Reader(availablePetsFileName, availablePackAnimalsFilename);
    }

    public ArrayList<Pet> getPets() {
        return petDB.getDatabase();
    }

    public ArrayList<PackAnimal> getPackAnimals() {
        return packAnimalDB.getDatabase();
    }
    public void addNewAnimal(String animalName, String animalType, LocalDate animalBIrthday) {
        ArrayList<String> availablePets = reader.getAvailablePets();
        ArrayList<String> availablePackAnimals = reader.getAvailablePackAnimals();
        if (availablePets.contains(animalType)) {
            petDB.add(new Pet(animalName, animalType, animalBIrthday));
        } else if (availablePackAnimals.contains(animalType)) {
            packAnimalDB.add(new PackAnimal(animalName, animalType, animalBIrthday));
        } else throw new RuntimeException("We don't know this animal type\n");
    }

    public void addCommandToPet(String targetPetName, String commandName) {
        for (Pet pet : petDB.getDatabase()) {
            if (pet.getAnimalName().equalsIgnoreCase(targetPetName)) {
                pet.addCommand(new Command(commandName));
                return;
            }
        }
        throw new RuntimeException("Pet not found\n");
    }

    public void addCommandToPackAnimal(String targetPackAnimalName, String commandName) {
        for (PackAnimal packAnimal : packAnimalDB.getDatabase()) {
            if (packAnimal.getAnimalName().equalsIgnoreCase(targetPackAnimalName)) {
                packAnimal.addCommand(new Command(commandName));
                return;
            }
        }
        throw new RuntimeException("Pack animal not found\n");
    }

    private Pet findPetByName(String targetPetName) {
        for (Pet pet : petDB.getDatabase()) {
            if (pet.getAnimalName().equalsIgnoreCase(targetPetName)) {
                return pet;
            }
        }
        throw new RuntimeException("Pet not found\n");
    }

    private PackAnimal findPackAnimalByName(String targetPackAnimalName) {
        for (PackAnimal packAnimal : packAnimalDB.getDatabase()) {
            if (packAnimal.getAnimalName().equalsIgnoreCase(targetPackAnimalName)) {
                return packAnimal;
            }
        }
        throw new RuntimeException("Pack animal not found\n");
    }

    public ArrayList<Command> getPetCommands(String petName) {
        return findPetByName(petName).getCommandsList();
    }

    public ArrayList<Command> getPackAnimalCommands(String packAnimalName) {
        return findPackAnimalByName(packAnimalName).getCommandsList();
    }

    private class Reader {
        String petsFileName;
        String packAnimalsFileName;

        public Reader(String petsFileName, String packAnimalsFileName) {
            this.petsFileName = petsFileName;
            this.packAnimalsFileName = packAnimalsFileName;
        }

        private String readline(String fileName) {
            try (BufferedReader bR = new BufferedReader(new FileReader(fileName))) {
                return bR.readLine();
            } catch (IOException e) {
                throw new RuntimeException("Something went wrong...\n");
            }
        }

        public ArrayList<String> getAvailablePets() {
            List<String> stringList = Arrays.asList(readline(petsFileName).split(";"));
            return new ArrayList<>(stringList);
        }

        public ArrayList<String> getAvailablePackAnimals() {
            List<String> stringList = Arrays.asList(readline(packAnimalsFileName).split(";"));
            return new ArrayList<>(stringList);
        }
    }
}