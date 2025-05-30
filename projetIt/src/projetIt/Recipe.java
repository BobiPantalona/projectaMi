package projetIt;
public class Recipe {
    public String name;
    public String ingredients;
    public String instructions;
    public String prepTime;
    public String imagePath;

    public Recipe(String name, String ingredients, String instructions) {
        this(name, ingredients, instructions, "", null);
    }

    public Recipe(String name, String ingredients, String instructions, String prepTime, String imagePath) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.prepTime = prepTime;
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return name;
    }
}
