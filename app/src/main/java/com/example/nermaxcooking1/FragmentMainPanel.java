package com.example.nermaxcooking1;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nermaxcooking.R;

import java.util.ArrayList;

public class FragmentMainPanel extends Fragment {

    RecipeSending recipeSending;
    private ArrayList<Recipe> recipes;

    public interface RecipeSending {
        public void goToRecipe(Recipe recipe);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            recipeSending = (RecipeSending) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement RecipeSending");
        }
    }

    @Override
    public void onDetach() {
        recipeSending = null;
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_panel, container, false);

        setInitialData();
        // получаем элемент ListView
        RecyclerView recyclerView = view.findViewById(R.id.scrollRecipesList);
        // создаем адаптер
        RecipeAdapter.OnRecipeClickListener recipeClickListener = new RecipeAdapter.OnRecipeClickListener() {

            @Override
            public void onRecipeClick(int position) {
                if (savedInstanceState == null) {
                    recipeSending.goToRecipe(recipes.get(position));
                }
            }
        };
            RecipeAdapter adapter = new RecipeAdapter(getContext(), recipeClickListener, recipes);
            // устанавливаем для списка адаптер
            recyclerView.setAdapter(adapter);


            return view;
        }

        public void setInitialData(){

        }
}

