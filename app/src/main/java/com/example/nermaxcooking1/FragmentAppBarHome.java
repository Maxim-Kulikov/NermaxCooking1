package com.example.nermaxcooking1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nermaxcooking.R;

import java.util.ArrayList;

public class FragmentAppBarHome extends Fragment {
    Button sortButton;
    Button filterButton;
    ArrayList<Recipe> recipes;
    FragmentChooseFilter fragmentChooseFilter = new FragmentChooseFilter();
    FragmentRecipeSorting fragmentRecipeSorting = new FragmentRecipeSorting();

    FragmentAppBarHome(ArrayList<Recipe> recipes){
        this.recipes = recipes;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_bar_home, container, false);
        sortButton = view.findViewById(R.id.sort_btn);
        filterButton = view.findViewById(R.id.filter_btn);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, fragmentChooseFilter)
                        .addToBackStack(null)
                        .commit();
            }
        });
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentRecipeSorting.setRecipes(recipes);
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, fragmentRecipeSorting)
                        .addToBackStack(null)
                        .commit();
            }
        });
       RecyclerView recyclerView = view.findViewById(R.id.scrollRecipesList);
        RecipeAdapter.OnRecipeClickListener onRecipeClickListener = new RecipeAdapter.OnRecipeClickListener() {
            @Override
            public void onRecipeClick(int position) {
                FragmentFullRecipeDescription fragmentFullRecipeDescription = new FragmentFullRecipeDescription();
                fragmentFullRecipeDescription.setRecipe(recipes.get(position));
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, fragmentFullRecipeDescription)
                        .addToBackStack(null)
                        .commit();
            }
        };
        RecipeAdapter recipeAdapter = new RecipeAdapter(getContext(), onRecipeClickListener, recipes);
        recyclerView.setAdapter(recipeAdapter);

        return view;
    }
}