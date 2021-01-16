package lesson_1.task_2;

import java.util.ArrayList;

public class Task_2 {

    public static <T> ArrayList<T> arrayChangeToArrayList(T... array) {
        ArrayList<T> list = new ArrayList<T>();
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }
        return list;
    }

    public static void main(String[] args) {
        Integer[] test1 = new Integer[]{1, 2, 3, 4, 5};
        System.out.println(arrayChangeToArrayList(test1).toString());

        String[] test2 = new String[]{"Привет!", "Как", "дела?"};
        System.out.println(arrayChangeToArrayList(test2).toString());
    }
}
