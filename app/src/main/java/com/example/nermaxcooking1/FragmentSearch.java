package com.example.nermaxcooking1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nermaxcooking.R;

import java.util.ArrayList;

public class FragmentSearch extends Fragment {
    ArrayList<Recipe> recipes = new ArrayList<>();
    ImageButton search;
    EditText editText;

    FragmentSearch(ArrayList<Recipe> recipes){
        this.recipes = recipes;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        search = view.findViewById(R.id.searchButton);
        editText = view.findViewById(R.id.searchET);

        RecyclerView recyclerView = view.findViewById(R.id.scrollRecipes);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
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
        System.out.println("Размер в поиске: " + recipes.size());
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                System.out.println("реакция");
                showResults(recipes, recyclerView, onRecipeClickListener, editText.getText().toString());
            }
        });

        return view;
    }

    private void showResults(ArrayList<Recipe> recipes, RecyclerView recyclerView, RecipeAdapter.OnRecipeClickListener onRecipeClickListener, String name){
        recipes = ControllerForRecipes.filterRecipes(recipes, editText.getText().toString());
        System.out.println("размер в реакции" + recipes.size());
        RecipeAdapter recipeAdapter = new RecipeAdapter(getContext(), onRecipeClickListener, recipes);
        recyclerView.setAdapter(recipeAdapter);
    }
}
