package orders;

import java.util.List;
public class OrdersGenerator {
    public static Orders getRandomOrder(){
        return new Orders(List.of("61c0c5a71d1f82001bdaaa73", "61c0c5a71d1f82001bdaaa77", "61c0c5a71d1f82001bdaaa6d"));//id ingredients
    }
    public static Orders getIncorrectOrder(){
        return new Orders(List.of("invalid"));
    }

}
