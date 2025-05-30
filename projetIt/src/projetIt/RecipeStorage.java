package projetIt;

import javax.swing.*;

import java.awt.Component;
import java.io.*;
import java.util.ArrayList;

public class RecipeStorage {

    public static void saveToFile(ArrayList<Recipe> recipes, Component parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Запази рецепти в файл");

        if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (PrintWriter writer = new PrintWriter(file)) {
                for (Recipe r : recipes) {
                    writer.println(r.name + "|" + r.ingredients + "|" + r.instructions + "|" + r.prepTime + "|" +
                            (r.imagePath != null ? r.imagePath : ""));
                }
                JOptionPane.showMessageDialog(parent, "Запазено успешно!");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(parent, "Грешка при запис!");
            }
        }
    }

    public static ArrayList<Recipe> loadFromFile(Component parent) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Избери файл с рецепти");

        if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 3) {
                        String prepTime = parts.length > 3 ? parts[3] : "";
                        String imagePath = parts.length > 4 ? parts[4] : null;
                        recipes.add(new Recipe(parts[0], parts[1], parts[2], prepTime, imagePath));
                    }
                }
                JOptionPane.showMessageDialog(parent, "Заредено успешно!");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(parent, "Грешка при зареждане!");
            }
        }
        return recipes;
    }
}
