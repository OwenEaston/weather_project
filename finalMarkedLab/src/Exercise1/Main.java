package Exercise1;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random rand = new Random();
        int totalShapes = rand.nextInt(5) + 4;

        Shape[] shapes = new Shape[totalShapes];

        for (int i = 0; i < shapes.length; i++) {
            int randomSize = rand.nextInt(6) + 3;
            if (rand.nextBoolean()) {
                shapes[i] = new Square(randomSize);
            }
            else {
                shapes[i] = new Triangle(randomSize);
            }
        }
        for (Shape shape : shapes) {
            shape.draw();
        }
    }
}
