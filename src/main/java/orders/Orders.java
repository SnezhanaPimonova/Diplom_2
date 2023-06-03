package orders;

import java.util.List;

public class Orders {
    public List<String> ingredients;

    public Orders() {
    }

    public Orders(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

}

