import model.Counter;
import model.animals.Command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class View {
    private Controller controller;

    public View(Controller controller) {
        this.controller = controller;
    }

    public void run() {
        try (Counter counter = new Counter()) {
            while (true) {
                try {
                    String command = prompt("""
                            Choose an action:
                            1 - Add new animal
                            2 - Show all pets
                            3 - Show all pack animals
                            4 - Display a list of commands that the animal can execute
                            5 - Teach an animal a new command
                            6 - Exit""");
                    switch (command) {
                        case "1":
                            String animalName = prompt("Enter name of new animal: ");
                            String animalType = prompt("Enter type of new animal: ");
                            LocalDate animalBirthday = LocalDate.parse(prompt("Enter date of birth of new animal in format YYYY-MM-DD "));
                            controller.addNewAnimal(animalName, animalType, animalBirthday);
                            System.out.println("New animal was added");
                            counter.addCount();
                            break;
                        case "2":
                            showList(controller.getPets());
                            break;
                        case "3":
                            showList(controller.getPackAnimals());
                            break;
                        case "4":
                            command = prompt("Choose 1 for pet or 2 for pack animal: ");
                            if (command.equals("1")) {
                                animalName = prompt("Enter pet name: ");
                                ArrayList<Command> petCommands = controller.getPetCommands(animalName);
                                if (!petCommands.isEmpty()) {
                                    showList(petCommands);
                                } else {
                                    System.out.println("This pet doesn't know any commands");
                                }
                                break;
                            } else if (command.equals("2")) {
                                animalName = prompt("Enter pack animal name: ");
                                ArrayList<Command> packAnimalCommands = controller.getPackAnimalCommands(animalName);
                                if (!packAnimalCommands.isEmpty()) {
                                    showList(packAnimalCommands);
                                } else {
                                    System.out.println("This pack animal doesn't know any commands");
                                }
                                break;
                            } else throw new RuntimeException("Wrong action");
                        case "5":
                            command = prompt("Choose 1 for pet or 2 for pack animal: ");
                            if (command.equals("1")) {
                                animalName = prompt("Enter pet name: ");
                                String commandName = prompt("Enter command name: ");
                                controller.addPetCommand(animalName, commandName);
                                System.out.println("Pet " + animalName + " learned new command");
                                break;
                            } else if (command.equals("2")) {
                                animalName = prompt("Enter pack animal name: ");
                                String commandName = prompt("Enter command name: ");
                                controller.addPackAnimalCommand(animalName, commandName);
                                System.out.println("Pack animal " + animalName + " learned new command");
                                break;
                            } else throw new RuntimeException("Wrong action");
                        case "6":
                            return;
                        default:
                            System.out.println("Wrong action! Try again");
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Incorrect date provided\n");
                }
            }
        } catch (Exception e) {
            System.out.println("Stream wasn't closed");
        }
    }

    private String prompt(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        return scanner.nextLine().strip().toLowerCase();
    }

    private <E> void showList(ArrayList<E> list) {
        for (E value : list) {
            System.out.println(value.toString());
        }
    }
}