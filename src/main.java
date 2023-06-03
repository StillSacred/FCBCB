import model.Registry;

public class main {
    public static void main(String[] args) {
        Registry registry = new Registry("pets.txt", "packanimals.txt");
        Controller controller = new Controller(registry);
        View view = new View(controller);
        view.run();
    }
}