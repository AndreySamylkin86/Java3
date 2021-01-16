package lesson_1.task_3;

import lesson_1.task_3.fruit.Fruit;

import java.util.ArrayList;

public class Box<TypeFruits extends Fruit> {
    ArrayList<TypeFruits> contains;

    public Box(ArrayList<TypeFruits> contains) {
        this.contains = contains;
    }


    public float getWeight() {
        float sum = 0;
        for (TypeFruits fruit : contains) {
            sum += fruit.getWeight();
        }
        return sum;
    }

    public boolean compare(Box<?> anotherBox) {
        return this.getWeight() == anotherBox.getWeight();
    }

    public void combiningBoxes(Box<TypeFruits> anotherBox) {
        for (int i = 0; i != anotherBox.contains.size(); ) {
            this.contains.add(anotherBox.contains.remove(i));
        }
    }
}







