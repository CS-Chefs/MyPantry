package com.example.mypantry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.mypantry.GroceryList.GroceryListFragment;
import com.example.mypantry.Pantry.PantryFragment;
import com.example.mypantry.Profile.ProfileFragment;
import com.example.mypantry.Recipies.RecipiesFragment;
import com.example.mypantry.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    //private static final int SPLASH = 2000;

    //Animation topAnim, bottomAnim;
    //ImageView imageView;
    //TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        TO DO:
        1. ADD WAY TO DISPLAY SPLASH SCREEN FIRST THEN PROCEED TO LOGIN PAGE
        2. DISPLAY HOME SCREEN AFTER SUCCESSFUL LOGIN WITH NAV BAR
        */

        // THIS CODE LOADS SPLASH SCREEN FIRST, BUT CRASHES DUE TO THE NAVBAR MENU TRYING TO DISPLAY
        /*
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_open_app);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.text);

        imageView.setAnimation(topAnim);
        textView.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH);
        */

        // THIS CODE WORKS BUT LOADS INTO RECIPES PAGE IMMEDIATELY, MAKING USER DATABASE NOT EVEN LOAD
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new RecipiesFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch  (item.getItemId()){

                case R.id.Grocery_List:
                    replaceFragment(new GroceryListFragment());
                    break;
                case R.id.Pantry:
                    replaceFragment(new PantryFragment());
                    break;
                case R.id.Profile:
                    replaceFragment(new ProfileFragment());
                    break;
                case R.id.Recipes:
                    replaceFragment(new RecipiesFragment());
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