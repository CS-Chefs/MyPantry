package com.example.mypantry.Profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mypantry.R;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    public class ProfileFragment extends Fragment {

        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";

        // TODO: Rename and change types of parameters
        private String mParam1;
        private String mParam2;

        public ProfileFragment() {
            // Required empty public constructor
        }
        private FloatingActionButton floatingActionButton;
        private DatabaseReference reference;
        private FirebaseAuth mAuth;
        private FirebaseUser mUser;
        private String onlineUserID;
        private ProgressDialog loader;
        private String key = "";
        private Button btnLogout;
        private String nEmail = "";
        private String newPass = "";

        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";

        // TODO: Rename and change types of parameters
        private String mParam1;
        private String mParam2;


        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        public static ProfileFragment newInstance(String param1, String param2) {
            ProfileFragment fragment = new ProfileFragment();
            Bundle args = new Bundle();
            args.putString(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_profile, container, false);
        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_profile);

            getSupportActionBar().setTitle("");
            mAuth = FirebaseAuth.getInstance();

            recyclerView = findViewById(R.id.recyclerView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);

            loader = new ProgressDialog(this);

            mUser = mAuth.getCurrentUser();
            onlineUserID = mUser.getUid();
            reference = FirebaseDatabase.getInstance().getReference().child("Pantry Items").child(onlineUserID);
        }

        // if the user clicks the Update button
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mChangeEmail = pantryItem.getText().toString().trim();
                String mNewPass = description.getText().toString().trim();
                String id = reference.push().getKey();

                if (TextUtils.isEmpty(mPantryItem)) {
                    pantryItem.setError("Pantry Item Required");
                    return;
                }
                else {
                    loader.setMessage("Adding your pantry item");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    // use the Model class to pack up the data
                    PantryItem model = new PantryItem(mPantryItem, mDetails, id, mdate);

                    // update the data to Firebase
                    reference.child(id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> pantryItem) {
                            if (pantryItem.isSuccessful()) {
                                Toast.makeText(PantryActivity.this, "Pantry item has been added successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                String error = pantryItem.getException().toString();
                                Toast.makeText(PantryActivity.this, "Failed: " + error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });







        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            nEmail = mChangeEmail.getText().toString().trim();
            newPass = mNewPass.getText().toString().trim();
            }
        });



        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();
                Intent intent = new Intent(ProfileFragment.this, LoginActivity.class);
                StartActivity(intent);
                finish();
        }
        });
    }