package css.pizzaorder;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

public class PizzaOrder {
    public static final Double DELIVERY_PRICE = 2.50;

    private List<Pizza> pizzasInOrder;          // list of pizzas ordered so far
    private boolean delivery=false;             // true if customer wants order delivered

    /***
     *  Constructor initializes the Arraylist holding the pizzas in the order
     */
    public PizzaOrder ( ) {
        pizzasInOrder = new ArrayList<Pizza>();
    }

    /***
     * Add an pizza to the order
     * @param topping - A string listing all the toppings on the pizza
     * @param size - The size of the pizza as an integer -- see Pizza class for details
     * @return the description of the pizza is returned if needed
     */
    public String OrderPizza(String topping, Integer size){
        Pizza newPizza = new Pizza(topping, size);
        pizzasInOrder.add(newPizza);
        return newPizza.toString();             // return a description of what was ordered
    }

    /***
     * Retrieve the full order
     * @return a list of pizzas, each one describe by a single string
     */
    public List<String> getOrder() {
        ArrayList<String> strOrder = new ArrayList<String>();
        for (Pizza p:pizzasInOrder ){
            strOrder.add(p.toString());
        }
        return strOrder;
    }

    /***
     * Calculate the total bill for this order
     * @return total bill as Double
     */
    public Double getTotalBill() {
        Double total = 0.0;
        for (Pizza p:pizzasInOrder ){
            total += p.getPrice();
        }
        if (delivery) {
            total += DELIVERY_PRICE;
        }
        return total;
    }

    public void setDelivery(boolean deliver) {
        this.delivery = deliver;
    }

    public boolean getDelivery() {
        return delivery;
    }

}
