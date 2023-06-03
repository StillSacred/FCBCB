package Databases;

import model.animals.Pet;

import java.util.ArrayList;

public class PetDatabase extends Database<Pet> {
    @Override
    public ArrayList<Pet> getDatabase() {
        return super.getDatabase();
    }

    @Override
    public void add(Pet value) {
        super.add(value);
    }
}