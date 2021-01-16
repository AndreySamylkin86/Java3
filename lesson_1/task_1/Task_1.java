package lesson_1.task_1;

import java.util.Arrays;

public class Task_1 {

    public static <T> T[] swap(int indexOne, int indexTwo, T... array) {
        System.out.print("Изначальный массив: " + Arrays.toString(array) + "\n");

        if (indexOne < 0 || indexOne >= array.length
                || indexTwo < 0 || indexTwo >= array.length)
            throw new ArrayIndexOutOfBoundsException();

        T var1 = array[indexOne];
        T var2 = array[indexTwo];
        array[indexOne] = var2;
        array[indexTwo] = var1;

        System.out.print("Элементы с индексом " + indexOne + " и " + indexTwo + " поменяли местами: "
                + Arrays.toString(array) + "\n");

        return array;
    }

    public static void main(String[] args) {

        Integer[] test1 = new Integer[]{1, 2, 3, 4, 5};
        swap(0, 3, test1);


        String[] test2 = new String[]{"Привет!", "Как", "дела?"};
        swap(0, 2, test2);
    }
}
