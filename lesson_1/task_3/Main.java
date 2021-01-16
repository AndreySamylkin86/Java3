package lesson_1.task_3;

import lesson_1.task_3.fruit.Apple;
import lesson_1.task_3.fruit.Fruit;
import lesson_1.task_3.fruit.Orange;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Box<Apple> aBoxOfApple1 = new Box<>(fruitsGenerate(25, Apple.class));
        System.out.println("Вес коробки с яблоками №1: " + aBoxOfApple1.getWeight());

        Box<Apple> aBoxOfApple2 = new Box<>(fruitsGenerate(30, Apple.class));
        System.out.println("Вес коробки с яблоками №2: " + aBoxOfApple2.getWeight());

        Box<Orange> aBoxOfOrange = new Box<>(fruitsGenerate(35, Orange.class));
        System.out.println("Вес коробки с апельсинами: " + aBoxOfOrange.getWeight());

        System.out.println("Вес коробки с яблоками №1 и №2 равны: " + aBoxOfApple1.compare(aBoxOfOrange));

        System.out.println("Пересыпали яблоки из коробки №2 в коробку №1");
        aBoxOfApple1.combiningBoxes(aBoxOfApple2);
//     будет ошибка   aBoxOfApple1.combiningBoxes(aBoxOfOrange);
        System.out.println("Вес коробки с яблоками №1: " + aBoxOfApple1.getWeight());
        System.out.println("Вес коробки с яблоками №2: " + aBoxOfApple2.getWeight());
    }

    // Метод создания фрутов
    static <T extends Fruit> ArrayList<T> fruitsGenerate(int number, Class<T> clazz) {
        ArrayList<T> fruits = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            if (clazz.equals(Apple.class))
                fruits.add((T) new Apple());
            if (clazz.equals(Orange.class))
                fruits.add((T) new Orange());
        }
        return fruits;
    }
}


