package projetIt;
import java.util.ArrayList;

public class RecipeSorter {
    public static void quickSort(ArrayList<Recipe> list, int low, int high) {
        if (low < high) {
            int pi = partition(list, low, high);
            quickSort(list, low, pi - 1);
            quickSort(list, pi + 1, high);
        }
    }

    private static int partition(ArrayList<Recipe> list, int low, int high) {
        Recipe pivot = list.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (list.get(j).name.compareToIgnoreCase(pivot.name) <= 0) {
                i++;
                Recipe temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }
        Recipe temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);
        return i + 1;
    }
}
