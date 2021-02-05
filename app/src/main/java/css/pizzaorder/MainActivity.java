package css.pizzaorder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.chip.Chip;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBarSize;
    Button buttonAddToOrder;
    Button buttonPlaceOrder;
    Chip chipPepperoni, chipChicken, chipGreenPepper;
    TextView textViewSize;
    EditText textOrder;
    Integer pizzaSize = 1;     // Pizza sizes are 0=Small, 1=Medium, 2=Large, 3=X-large
    final String[] PIZZA_SIZES = {"Small","Medium","Large","X-Large"};
    MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connect MainViewModel class to the main activity class, used to access the ViewModel class
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        //Connect components with the xml parts
        seekBarSize = findViewById(R.id.seekBarSize);
        buttonAddToOrder = findViewById(R.id.buttonAddToOrder);
        buttonPlaceOrder = findViewById(R.id.buttonPlaceOrder);
        textViewSize = findViewById(R.id.textViewSize);
        textOrder = findViewById(R.id.editTextOrder);
        chipPepperoni = findViewById(R.id.chipPepperoni);
        chipChicken = findViewById(R.id.chipChicken);
        chipGreenPepper = findViewById(R.id.chipGreenPeppers);

        /***
         *   Handle SeekBar changes
         */
        seekBarSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pizzaSize = progress;      // progress will be seekBar position of 0, 1, 2, or 3
                textViewSize.setText(PIZZA_SIZES[pizzaSize]);
            }
            // Required for SeekBar, but not needed in this program
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {    }
            // Required for SeekBar, but not needed in this program
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {     }
        });

        /***
         *   Handle Place Order button clicks
         */
        buttonPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CIS 3334", "Place order button clicked");   // log button click for debugging using "CIS 3334" tag
                //When button is clicked send the order to the placeOrder() method in the MainViewModel Class
                mainViewModel.placeOrder();
            }
        });

        /***
         *   Handle Add To Order button clicks
         */
        buttonAddToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Main View Model will call the method to add to the order
                //Passing in values to the order method
                mainViewModel.AddPizzaToOrder(getToppings(), pizzaSize);
                //Set text area to the order, ViewModel connected to the pizza order
                textOrder.setText(mainViewModel.GetOrderAsString());
                //Log is the way to print to the console in android studio
                Log.d("CIS 3334", "Add To Order button clicked");// log button click for debugging using "CIS 3334" tag


            }
        });

        // Create the observer which updates and watches for activity in the UI.
        final Observer<String> statusObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String newStatus) {
                // Update the UI, in this case, a TextView.
                textOrder.setText(textOrder.getText().toString() + "\n" + newStatus );
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        //The observer watches for updates and activity being passed in
        mainViewModel.getOrderStatus().observe(this, statusObserver);

    }

    /***
     *   Check what topping chips have been checked and create a string containing them
     */
    private String getToppings() {
        String toppings = "";
        if (chipChicken.isChecked()) {
            toppings += chipChicken.getText() +" - ";
        }
        if (chipGreenPepper.isChecked()) {
            toppings += chipGreenPepper.getText() +" - ";
        }
        if (chipPepperoni.isChecked()) {
            toppings += chipPepperoni.getText() +" - ";
        }
        return toppings;
    }

}