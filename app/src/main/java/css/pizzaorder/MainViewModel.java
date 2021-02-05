package css.pizzaorder;

import android.os.Handler;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;


public class MainViewModel extends ViewModel {

    PizzaOrder pizzaOrder = new PizzaOrder();

    //Method to add toppings and a size to an order, takes in values into the method for the order
    public void AddPizzaToOrder(String toppings, Integer size){
        pizzaOrder.OrderPizza(toppings, size);
    }

    //Call the PizzaTimer from this method to get the current status of the order
    public void placeOrder() {
        orderStatus.postValue("Order Placed");
        startPizzaTimer();
    }

    //Return pizzas in the order, get the orders from the list
    public String GetOrderAsString(){
        List<String> order = pizzaOrder.getOrder();
        String strOrder = "Pizza Order: \n";
        for(String strPizza: order){
            strOrder = strOrder + "\n" + strPizza;
        }
        return strOrder;
    }

    // For live data updates
    // https://developer.android.com/topic/libraries/architecture/livedata
    private MutableLiveData<String> orderStatus;  // String to store the order status which is updated by the pizza timer

    //getOrderStatus() will provide live data updates to the orderStatus variable
    //@return the orderStatus string
    public MutableLiveData<String> getOrderStatus() {
        if (orderStatus == null) {
            orderStatus = new MutableLiveData<String>();
        }
        return orderStatus;
    }

    //This class implements a timer for the baking pizza
    private static Runnable pizzaTimer;
    private Handler handler;
    private void startPizzaTimer(){
        handler = new Handler();
        pizzaTimer = new PizzaTimer();
        handler.postDelayed(pizzaTimer, 1000);
    }
    private class PizzaTimer implements Runnable {
        private Integer count = 0;
        @Override
        public void run() {
            count++;
            if (count > 2) {
                orderStatus.postValue("Pizza ready to eat ");
            } else if (count == 2) {
                orderStatus.postValue("Pizza is cooling ");
                handler.postDelayed(this, 2000);        // cool pizza for 2 seconds
            } else if (count == 1) {
                orderStatus.postValue("Pizza is baking ");
                handler.postDelayed(this, 5000);        // bake pizza for 5 seconds
            } else {
                orderStatus.postValue("Pizza is being prepared ");
                handler.postDelayed(this, 2000);        // wait 2 seconds for pizza to be prepared
            }

        }
    }

}
