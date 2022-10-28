package com.example.mypantry.Pantry;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mypantry.R;
import java.util.ArrayList;
import java.util.List;

public class PantryMainActivity extends AppCompatActivity {

    List<pantryItem> pantryItems;
    PantryAdapter myPantryAdapter;
    ListView lvItems;
    SqlDatabaseHelper sqlDatabase;
    Context context = this;
    EditText submitText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantry_main_activity);

        sqlDatabase = new SqlDatabaseHelper(this);
        populateArrayItems();                                   // DEFINED AT BOTTOM OF THIS CLASS
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(myPantryAdapter);
        //submitText = (EditText) findViewById(R.id.itemName);  // FIX THIS


        //ON ITEM LONG CLICK WITHIN ON CREATE METHOD
        //THIS METHOD ALLOWS YOU TO LONG CLICK ON AN ITEM IN LIST AND DELETE IT FROM DB
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                sqlDatabase.deleteDb(pantryItems.get(position).getName());
                pantryItems.remove(position);
                myPantryAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Item Deleted!", Toast.LENGTH_LONG).show();
                return true;
            }
        });

        //ON ITEM CLICK WITHIN ON CREATE METHOD
        // THIS METHOD ALLOWS YOU TO SHORT CLICK ON ITEM AND GO TO ITEM DETAILS
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pantryItem selectedItem = pantryItems.get(position);

                Intent intent = new Intent(PantryMainActivity.this, PantryItemDetailsActivity.class);
                intent.putExtra("Item Name", selectedItem.getName());
                intent.putExtra("Item Details", selectedItem.getDetails());
                intent.putExtra("Item ID", selectedItem.getId() + "");
                intent.putExtra("Item position", position);
                startActivity(intent);
            }
        });
    }       // THIS BRACKET CLOSES ON CREATE METHOD



    // CLICKING ADD BUTTON MAKES DIALOG APPEAR TO ENTER NEW ENTRY
    public void addItem(View view) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.pantry_add_item_activity, null);
        dialogBuilder.setView(dialogView);

        final EditText edtName = (EditText) dialogView.findViewById(R.id.addItemName);
        final EditText edtDetails = (EditText) dialogView.findViewById(R.id.addItemDetails);

        dialogBuilder.setTitle("Add Item To List");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String itemName = edtName.getText().toString();
                String itemDetails = edtDetails.getText().toString();


                // CREATE NEW INSTANCE OF TODOITEM AND THEN SET VALUES USING OUTPUT OF DIALOG
                pantryItem item = new pantryItem();
                item.setName(itemName);
                item.setDetails(itemDetails);
                myPantryAdapter.add(item);        // ADD NEW TODOITEM TO ADAPTER
                writeItems(item);                   //SAVE INTO DATABASE
                Toast.makeText(getApplicationContext(), "Item Added!", Toast.LENGTH_LONG).show();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    // CREATE NEW ENTRY IN DATABASE
    private void writeItems(pantryItem temp){
        long id = sqlDatabase.addDb(temp);
        temp.setId(id);
        myPantryAdapter.notifyDataSetChanged();
    }


    // READ ALL ITEMS IN DATABASE
    private void readItems(){
        if(sqlDatabase.getAllItems() != null){
            List<pantryItem> dbItems = sqlDatabase.getAllItems();
            pantryItems.clear();
            pantryItems.addAll(dbItems);
        }
    }


    // CALLS DATABASE UPDATE METHOD TO ALLOW YOU TO UPDATE ITEM
    private void updateItem(pantryItem item) {
        sqlDatabase.updateItem(item);
    }


    // POPULATES A NEW ARRAY LIST OF PANTRYITEM WITH ALL ITEMS IN DATABASE
    // USES ADAPTER TO SET APPROPRIATE LAYOUT
    public void populateArrayItems(){
        pantryItems = new ArrayList<pantryItem>();
        readItems();
        myPantryAdapter = new PantryAdapter(this, R.layout.pantry_item_activity, R.id.pantryItemName, pantryItems);
    }



}