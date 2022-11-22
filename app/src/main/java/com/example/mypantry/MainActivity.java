package com.example.mypantry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.mypantry.GroceryList.GroceryListFragment;
import com.example.mypantry.Pantry.PantryActivity;
import com.example.mypantry.Pantry.PantryFragment;
import com.example.mypantry.Profile.ProfileFragment;
import com.example.mypantry.Recipes.RecipesFragment;
import com.example.mypantry.databinding.ActivityMainBinding;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new RecipesFragment());
        /*
        TO DO:
        1. ADD WAY TO DEFAULT LOGIN SCREEN WHEN NOT LOGGED IN ALREADY
        2. ADD EACH FRAGMENTS UNIQUE LAYOUT
        3. ADD FUNCTIONALITY TO EACH FRAGMENT
         */

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch  (item.getItemId()){

                case R.id.Grocery_List:
                    replaceFragment(new GroceryListFragment());
                    break;
                case R.id.Pantry:
                    Intent i = new Intent(MainActivity.this, PantryActivity.class);
                    startActivity(i);
                    overridePendingTransition(0,0);
                    break;
                case R.id.Profile:
                    replaceFragment(new ProfileFragment());
                    break;
                case R.id.Recipes:
                    replaceFragment(new RecipesFragment());
                    break;
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}