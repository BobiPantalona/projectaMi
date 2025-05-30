package projetIt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class RecipeManager extends JFrame {
    private DefaultListModel<Recipe> recipeListModel;
    private JList<Recipe> recipeJList;
    private JTextArea ingredientsArea, instructionsArea, prepTimeArea;
    private JLabel imageLabel;
    private ArrayList<Recipe> recipes;

    public RecipeManager() {
        setTitle("Мениджър на рецепти");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        recipes = new ArrayList<>();
        recipeListModel = new DefaultListModel<>();
        recipeJList = new JList<>(recipeListModel);
        recipeJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        recipeJList.addListSelectionListener(e -> displaySelectedRecipe());

        JScrollPane listScrollPane = new JScrollPane(recipeJList);
        listScrollPane.setPreferredSize(new Dimension(200, 0));

        ingredientsArea = new JTextArea(5, 20);
        instructionsArea = new JTextArea(5, 20);
        prepTimeArea = new JTextArea(1, 20);

        ingredientsArea.setLineWrap(true);
        instructionsArea.setLineWrap(true);
        ingredientsArea.setWrapStyleWord(true);
        instructionsArea.setWrapStyleWord(true);

        ingredientsArea.setEditable(false);
        instructionsArea.setEditable(false);
        prepTimeArea.setEditable(false);

        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(200, 200));

        JPanel detailsPanel = new JPanel(new BorderLayout());

        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.add(new JScrollPane(ingredientsArea));
        infoPanel.add(new JScrollPane(instructionsArea));
        infoPanel.add(prepTimeArea);

        detailsPanel.add(infoPanel, BorderLayout.CENTER);
        detailsPanel.add(imageLabel, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel();
        addButton(buttonPanel, "Добави", e -> addRecipe());
        addButton(buttonPanel, "Редактирай", e -> editRecipe());
        addButton(buttonPanel, "Изтрий", e -> deleteRecipe());
        addButton(buttonPanel, "Сортирай", e -> sortRecipes());
        addButton(buttonPanel, "Запази", e -> RecipeStorage.saveToFile(recipes, this));
        addButton(buttonPanel, "Зареди", e -> loadRecipes());

        add(listScrollPane, BorderLayout.WEST);
        add(detailsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addButton(JPanel panel, String title, ActionListener action) {
        JButton button = new JButton(title);
        button.addActionListener(action);
        panel.add(button);
    }

    private void addRecipe() {
        Recipe newRecipe = RecipeEditor.showAddDialog(this, recipes);
        if (newRecipe != null) {
            recipes.add(newRecipe);
            recipeListModel.addElement(newRecipe);
        }
    }

    private void editRecipe() {
        int index = recipeJList.getSelectedIndex();
        if (index != -1) {
            Recipe selected = recipeListModel.get(index);
            Recipe updated = RecipeEditor.showEditDialog(this, selected);
            if (updated != null) {
                recipes.set(index, updated);
                recipeListModel.set(index, updated);
                displaySelectedRecipe();
            }
        }
    }

    private void deleteRecipe() {
        int index = recipeJList.getSelectedIndex();
        if (index != -1) {
            recipes.remove(index);
            recipeListModel.remove(index);
            clearDisplay();
        }
    }

    private void sortRecipes() {
        RecipeSorter.quickSort(recipes, 0, recipes.size() - 1);
        updateRecipeListModel();
    }

    private void loadRecipes() {
        ArrayList<Recipe> loaded = RecipeStorage.loadFromFile(this);
        if (!loaded.isEmpty()) {
            recipes = loaded;
            updateRecipeListModel();
        }
    }

    private void updateRecipeListModel() {
        recipeListModel.clear();
        for (Recipe r : recipes) {
            recipeListModel.addElement(r);
        }
    }

    private void displaySelectedRecipe() {
        Recipe selected = recipeJList.getSelectedValue();
        if (selected != null) {
            ingredientsArea.setText("Съставки:\n" + selected.ingredients);
            instructionsArea.setText("Инструкции:\n" + selected.instructions);
            prepTimeArea.setText("Време за приготвяне: " + selected.prepTime);

            if (selected.imagePath != null && !selected.imagePath.isEmpty()) {
                ImageIcon icon = new ImageIcon(selected.imagePath);
                Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(img));
            } else {
                imageLabel.setIcon(null);
            }
        }
    }

    private void clearDisplay() {
        ingredientsArea.setText("");
        instructionsArea.setText("");
        prepTimeArea.setText("");
        imageLabel.setIcon(null);
    }
}
