package com.example.android.justjava;

        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.text.BoringLayout;
        import android.util.Log;
        import android.view.View;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrders(View view) {
        CheckBox getWhippedCream = (CheckBox) findViewById(R.id.WhippedCream);
        boolean hasWhippedCream = getWhippedCream.isChecked();
        CheckBox getChocolate = (CheckBox) findViewById(R.id.Chocolate);
        boolean hasChocolate = getChocolate.isChecked();
        EditText Name = (EditText) findViewById(R.id.UserName);
        String UserName = Name.getText().toString();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, UserName, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order to " + UserName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }


    /**
     * Calculates the price of the order.
     * @return total price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int baseprice= 5;
        if (hasWhippedCream) {
            baseprice =  baseprice + 1;
        }
        if (hasChocolate){
            baseprice = baseprice +2;
        }
        return quantity * baseprice;
    }


    private String createOrderSummary(int baseprice, String UserName, boolean hasWhippedCream, boolean hasChocolate){
        String priceMessage = getString(R.string.order_summary_name, UserName);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, hasWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, hasChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price,
                NumberFormat.getCurrencyInstance().format(baseprice));
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;

}


    public void increment(View view) {
        if (quantity==100){
            Toast.makeText(this, "You can't have more then 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity==1){
            Toast.makeText(this, "You can't have less then 1 coffee", Toast.LENGTH_SHORT).show();
        return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int liczba) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + liczba);
    }


}