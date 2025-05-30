package projetIt;

import javax.swing.*;
import java.io.File;

public class RecipeEditor {
    public static Recipe showAddDialog(JFrame parent, java.util.List<Recipe> existing) {
        String name = JOptionPane.showInputDialog("Име на рецепта:");
        if (name == null || name.isEmpty()) return null;
        for (Recipe r : existing) {
            if (r.name.equalsIgnoreCase(name)) {
                JOptionPane.showMessageDialog(parent, "Рецептата вече съществува!");
                return null;
            }
        }
        return getRecipeInput(name, "", "", "", parent);
    }

    public static Recipe showEditDialog(JFrame parent, Recipe recipe) {
        return getRecipeInput(recipe.name, recipe.ingredients, recipe.instructions, recipe.prepTime, parent);
    }

    private static Recipe getRecipeInput(String name, String ingredients, String instructions, String prepTime, JFrame parent) {
        String newIngredients = JOptionPane.showInputDialog("Съставки:", ingredients);
        String newInstructions = JOptionPane.showInputDialog("Инструкции:", instructions);
        String newPrepTime = JOptionPane.showInputDialog("Време за приготвяне:", prepTime);

        JFileChooser fileChooser = new JFileChooser();
        String imagePath = null;
        if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            imagePath = file.getAbsolutePath();
        }

        return new Recipe(name, newIngredients, newInstructions, newPrepTime, imagePath);
    }
}
