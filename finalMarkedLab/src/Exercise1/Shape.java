package Exercise1;

abstract class Shape {
    int shapeSize;

    public Shape(int shapeSize) {
        this.shapeSize = shapeSize;
    }

    abstract void draw();
}

class Square extends Shape {
    public Square(int shapeSize) {
        super(shapeSize);
    }

    @Override
    void draw() {
        for (int i = 0; i < shapeSize; i++) {
            for (int j = 0; j < shapeSize; j++) {
                System.out.print("* ");
            }
            System.out.println();
        }
        System.out.println();
    }
}

class Triangle extends Shape {
    public Triangle(int shapeSize) {
        super(shapeSize);
    }

    @Override
    void draw() {
        for (int i = 1; i <= shapeSize; i++) {
            for (int j = 0; j < i; j++) {
                System.out.print("* ");
            }
            System.out.println();
        }
        System.out.println();
    }
}