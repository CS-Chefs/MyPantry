package com.example.mypantry.Recipes;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mypantry.R;

public class RecipesMainFragment extends Fragment {

    Button gotoRandomRecipesSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes_main, container, false);

        gotoRandomRecipesSearch = view.findViewById(R.id.btnClickRandomRecipes);


        gotoRandomRecipesSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment RandomRecipesSearch = new RandomRecipesOptionFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(android.R.id.content, RandomRecipesSearch).commit();

            }
        });


        return view;
    }
}
