package css.pizzaorder;

import androidx.lifecycle.ViewModel;

import java.util.List;

public class MainViewModel extends ViewModel {

    PizzaOrder pizzaOrder = new PizzaOrder();

    //Method to add toppings and a size to an order, takes in values into the method for the order
    public void AddPizzaToOrder(String toppings, Integer size){
        pizzaOrder.OrderPizza(toppings, size);
    }
    //Return pizzas in the order, get the orders from the list
    public String GetOrderAsString(){
        List<String> order = pizzaOrder.getOrder();
        String strOrder = "Pizza Order: /n";
        for(String strPizza: order){
            strOrder = strOrder + "/n" + strPizza;
        }
        return strOrder;
    }

}
