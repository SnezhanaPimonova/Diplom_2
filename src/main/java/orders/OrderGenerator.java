package orders;

import java.util.List;
public class OrderGenerator {
    public static Order getOrderData(){
        return new Order(List.of("61c0c5a71d1f82001bdaaa73", "61c0c5a71d1f82001bdaaa77", "61c0c5a71d1f82001bdaaa6d"));//id ingredients
    }
    public static Order getIncorrectOrder(){
        return new Order(List.of("invalid"));
    }

}
