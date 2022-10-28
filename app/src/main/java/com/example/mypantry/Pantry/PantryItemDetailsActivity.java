package com.example.mypantry.Pantry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mypantry.R;

/**
 * Created by user on 07/09/2016.
 */
public class PantryItemDetailsActivity extends AppCompatActivity {

    TextView nameText;
    TextView detailsText;
    Button editButton;
    String mName;
    String mDetails;
    String mID;
    String mPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantry_item_details_activity);


//        SETS VARIABLES TO TEXT VIEW FIELDS THAT CAN BE CALLED IN NEXT METHOD
        nameText = (TextView) findViewById(R.id.viewName);
        detailsText = (TextView) findViewById(R.id.viewDetails);


//        RECEIVES INTENT FROM MAIN ACTIVITY AND SETS TEXTVIEW FIELDS TO RELEVANT VALUE
        Intent intent = getIntent();
        mName = intent.getStringExtra("Item Name");
        mDetails = intent.getStringExtra("Item Details");
        mID = intent.getStringExtra("Item ID");
        mPosition = intent.getStringExtra("Item position");

            nameText.setText(mName);
            detailsText.setText(mDetails);


//      CREATED A NEW INTENT TO SEND TO EDIT ITEM - IS THIS NECESSARY?
        editButton = (Button) findViewById(R.id.viewItemEditBtn);
        editButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PantryItemDetailsActivity.this, PantryItemEditActivity.class);
                intent.putExtra("Item Name", mName);
                intent.putExtra("Item Details", mDetails);
                intent.putExtra("Item ID", mID);
                intent.putExtra("Item position", mPosition);
                startActivity(intent);
            }
        });
    }
}

