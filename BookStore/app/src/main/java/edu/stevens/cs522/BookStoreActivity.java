package edu.stevens.cs522;

/**
 * Created by Poonam on 2/6/2015.
 */


        import java.util.ArrayList;
        import android.app.ListActivity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import edu.stevens.cs522.entities.Book;

public class BookStoreActivity extends ListActivity {

    // Use this when logging errors and warnings.
    @SuppressWarnings("unused")
    private static final String TAG = BookStoreActivity.class.getCanonicalName();

    // These are request codes for subactivity request calls
    static final private int ADD_REQUEST = 1;

    @SuppressWarnings("unused")
    static final private int CHECKOUT_REQUEST = ADD_REQUEST + 1;

    static final String SHOPPING_CART = "shoppingCart";


    // There is a reason this must be an ArrayList instead of a List.
    @SuppressWarnings("unused")
    private ArrayList<Book> shoppingCart = new ArrayList<Book>() ;
    public final static String CHECKOUT_EXTRA = "edu.stevens.cs522.CheckoutMessage";

    private ArrayAdapter<Book> adapter;
    private int selectedItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO check if there is saved UI state, and if so, restore it (i.e. the cart contents)
        if(savedInstanceState != null){
            shoppingCart = savedInstanceState.getParcelableArrayList(SHOPPING_CART);
            Log.i(TAG,"Shopping Cart data restored");
        }

        // TODO Set the layout (use cart.xml layout)
        setContentView(R.layout.cart);

        // TODO use an array adapter to display the cart contents.
        adapter = new ArrayAdapter<Book>(this, R.layout.cart_row, R.id.cart_row_title, shoppingCart);
        setListAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // TODO provide ADD, DELETE and CHECKOUT options
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bookstore_menu, menu);
        Log.i(TAG,"Menu Item created");
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        // TODO

        // ADD provide the UI for adding a book
        // Intent addIntent = new Intent(this, AddBookActivity.class);
        // startActivityForResult(addIntent, ADD_REQUEST);
        switch (item.getItemId()) {
            case R.id.add:
                Log.i(TAG,"Add Option Selected");
                Intent addIntent = new Intent(this, AddBookActivity.class);
                startActivityForResult(addIntent, ADD_REQUEST);
                return true;

            // DELETE delete the currently selected book
            case R.id.delete:
                Log.i(TAG,"Delected Option Selected");
                Log.d(TAG,"Item Selected: " + selectedItem);
                if(selectedItem >= 0 && selectedItem < shoppingCart.size()){
                    shoppingCart.remove(selectedItem);
                    adapter.notifyDataSetChanged();
                }
                return true;

            // CHECKOUT provide the UI for checking out
            case R.id.checkout:
                Log.i(TAG,"Checkout Option Selected");
                Intent checkoutIntent = new Intent(this, CheckoutActivity.class);
                checkoutIntent.putExtra(CHECKOUT_EXTRA, shoppingCart.size());
                startActivityForResult(checkoutIntent, CHECKOUT_REQUEST);
                return true;
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        // TODO Handle results from the Search and Checkout activities.

        // Use SEARCH_REQUEST and CHECKOUT_REQUEST codes to distinguish the cases.
        // SEARCH: add the book that is returned to the shopping cart.
        if(requestCode == ADD_REQUEST){
            if(resultCode == RESULT_OK){
                Book book = intent.getParcelableExtra(AddBookActivity.BOOK_RESULT_KEY);
                shoppingCart.add(book);
                adapter.notifyDataSetChanged();
            }
        }
        // CHECKOUT: empty the shopping cart.
        else if(requestCode == CHECKOUT_REQUEST){
            if(resultCode == RESULT_OK){
                shoppingCart.clear();
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // TODO save the shopping cart contents (which should be a list of parcelables).

        savedInstanceState.putParcelableArrayList(SHOPPING_CART, shoppingCart);
        super.onSaveInstanceState(savedInstanceState);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        //Event Handler to get selected item
        selectedItem = position;
    }

}