package com.example.mypantry.Pantry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mypantry.R;

public class PantryItemEditActivity extends AppCompatActivity {

    EditText editText;
    EditText newDetails;
    String mName="";
    String mDetails ="";
    String mId = "";
    int position;


// CHECKS INTENT TO SEE IF VALUES ALREADY EXIST AND IF SO PASSES THEM INTO EDITTEXT FIELD
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantry_edit_item_activity);

        Bundle intent = getIntent().getExtras();
        if (intent.containsKey("Item Name"))
            mName = intent.getString("Item Name");
        if (intent.containsKey("Item ID"))
            mId = intent.getString("Item ID");
        if (intent.containsKey("Item Details"))
            mDetails = intent.getString("Item Details");
        if (intent.containsKey("Item position"))
            position = intent.getInt("Item position");


        editText = (EditText) findViewById(R.id.etEditText);
        editText.setText(mName);
        editText.setSelection(mName.length());
        newDetails = (EditText) findViewById(R.id.etDetails);
        newDetails.setText(mDetails);
        newDetails.setSelection(mDetails.length());
    }

//    SAVES ANY EDITED VALUES TO A NEW PANTRYITEM AND PASSES IT INTO THE UPDATEITEM METHOD TO UPDATE IN DB
    public void onSaveBtn(View view) {

        Intent data = new Intent(this, PantryItemDetailsActivity.class);
        data.putExtra("Item Name", editText.getText().toString());
        data.putExtra("Item Details", newDetails.getText().toString());
        data.putExtra("Item position", position);
        data.putExtra("Item ID", mId);

        pantryItem pantryItem = new pantryItem(Long.parseLong(mId), editText.getText().toString(), newDetails.getText().toString());
        SqlDatabaseHelper sql = new SqlDatabaseHelper(this);
        sql.updateItem(pantryItem);
        startActivity(data);
        Toast.makeText(getApplicationContext(), "Item Updated!", Toast.LENGTH_LONG).show();
    }


}
