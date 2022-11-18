package com.example.mypantry.Pantry;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypantry.Profile.LoginActivity;
import com.example.mypantry.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PantryActivity extends AppCompatActivity {

    //private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String onlineUserID;

    private ProgressDialog loader;

    private String key = "";
    private String pantryItem;
    private String date;
    private String details;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantry_main_activity);

        //toolbar = findViewById(R.id.HomeToolbar);
        //toolbar.getBackground().setAlpha(0);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        mAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        //set up a loader
        loader = new ProgressDialog(this);

        mUser = mAuth.getCurrentUser();
        onlineUserID = mUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("tasks").child(onlineUserID);


        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPantryItem();
            }
        });

    }

    //function that adds a pantry item and uploads it to Firebase
    private void addPantryItem() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this); //create a alert dialog
        LayoutInflater inflater = LayoutInflater.from(this);

        //use the input_file layout as the view
        View myView = inflater.inflate(R.layout.pantry_add_item_activity, null);
        myDialog.setView(myView);

        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false); //touch outside doesn't cancel

        //mke a round-corner dialog
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //initialize the texts and btns
        final EditText pantryItem = myView.findViewById(R.id.item);
        final EditText description = myView.findViewById(R.id.description);
        final EditText date = myView.findViewById(R.id.date);

        Button save = myView.findViewById(R.id.saveBtn);
        Button cancel = myView.findViewById(R.id.CancelBtn);

        //if the user clicks cancel button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //if the user clicks save button
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mPantryItem = pantryItem.getText().toString().trim();
                String mDetails = description.getText().toString().trim();
                String mdate = date.getText().toString().trim();
                String id = reference.push().getKey();//get the key for each data set

//                String date = DateFormat.getDateInstance().format(new Date());

                if (TextUtils.isEmpty(mPantryItem)) {
                    pantryItem.setError("Pantry Item Required");
                    return;
                }
                /*
                if (TextUtils.isEmpty(mDetails)) {
                    description.setError("Item Details Required");
                    return;
                }
                */
                else {
                    loader.setMessage("Adding your pantry item");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    //use the Model class to pack up the data
                    PantryItem model = new PantryItem(mPantryItem, mDetails, id, mdate);
                    //update the data to Firebase
                    reference.child(id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> pantryItem) {
                            if (pantryItem.isSuccessful()) {
                                Toast.makeText(PantryActivity.this, "Pantry item has been added successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                String error = pantryItem.getException().toString();
                                Toast.makeText(PantryActivity.this, "Failed: " + error, Toast.LENGTH_SHORT).show();
                            }
                            loader.dismiss();
                        }
                    });

                }

                dialog.dismiss();
            }
        });

        dialog.show();//show the dialog
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<PantryItem> options = new FirebaseRecyclerOptions.Builder<PantryItem>().setQuery(reference,  PantryItem.class).build();

        FirebaseRecyclerAdapter<PantryItem, MyViewHolder> adapter = new FirebaseRecyclerAdapter<PantryItem, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull PantryItem model) {
                holder.setDate(model.getDate());
                holder.setTask(model.getPantryItem());
                holder.setDesc(model.getDetails());

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        key = getRef(position).getKey();
                        pantryItem = model.getPantryItem();
                        details = model.getDetails();
                        date = model.getDate();

                        updatePantryItem();
                    }
                });

            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pantry_item_activity, parent, false);
                return new MyViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTask(String task){
            TextView taskTextView = mView.findViewById(R.id.pantryItemTv);
            taskTextView.setText(task);
        }

        public void setDesc(String desc){
            TextView descTextView = mView.findViewById(R.id.detailsTv);
            descTextView.setText(desc);
        }

        public void setDate(String date){
            TextView dateTextView = mView.findViewById(R.id.dateTv);
            dateTextView.setText(date);
        }
    }

    private void updatePantryItem(){
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.pantry_item_update_activity, null);
        myDialog.setView(view);

        AlertDialog dialog = myDialog.create();

        //make the corner round
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText mTask = view.findViewById(R.id.mEditedItem);
        EditText mDescription = view.findViewById(R.id.mEditedDetails);
        EditText mDate = view.findViewById(R.id.mEditedDate);

        mTask.setText(pantryItem);
        mTask.setSelection(pantryItem.length());

        mDescription.setText(details);
        mDescription.setSelection(details.length());

        mDate.setText(date);
        mDate.setSelection(date.length());

        Button deleteBtn = view.findViewById(R.id.btnDelete);
        Button updateBtn = view.findViewById(R.id.btnUpdate);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pantryItem = mTask.getText().toString().trim();
                details = mDescription.getText().toString().trim();
                date = mDate.getText().toString().trim();

//                String date = DateFormat.getDateInstance().format(new Date());

                PantryItem model = new PantryItem(pantryItem, details, key, date);

                reference.child(key).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> pantryItem) {
                        if (pantryItem.isSuccessful()){
                            Toast.makeText(PantryActivity.this, "Pantry item has been updated successfully", Toast.LENGTH_SHORT).show();
                        } else{
                            String error = pantryItem.getException().toString();
                            Toast.makeText(PantryActivity.this, "Update failed" + error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.dismiss();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> pantryItem) {
                        if (pantryItem.isSuccessful()){
                            Toast.makeText(PantryActivity.this, "Item has been deleted successfully", Toast.LENGTH_SHORT).show();
                        } else{
                            String error = pantryItem.getException().toString();
                            Toast.makeText(PantryActivity.this, "Delete failed" + error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.dismiss();
            }
        });

        dialog.show();
    }
    /*
    //set up the log out function
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                mAuth.signOut();
                Intent intent = new Intent(PantryActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
    */
}