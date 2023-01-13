package com.example.nermaxcooking1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nermaxcooking.R;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private ArrayList<Recipe> recipes;
    private final LayoutInflater inflater;

    interface OnRecipeClickListener{
        void onRecipeClick(int position);
    }

    private OnRecipeClickListener onRecipeClickListener;

    RecipeAdapter(Context context, OnRecipeClickListener onRecipeClickListener, ArrayList<Recipe> recipes) {
        this.inflater = LayoutInflater.from(context);
        this.onRecipeClickListener = onRecipeClickListener;
        this.recipes = recipes;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recipes_list, viewGroup, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        position=viewHolder.getAdapterPosition();
        Recipe recipe = recipes.get(position);

        //viewHolder.imageIW.setImageBitmap(ControllerForRecipes.getBitmapFromByteArray(recipe.getPhoto().getBytes(StandardCharsets.UTF_8)));
        viewHolder.imageIW.setImageResource(R.drawable.ic_add);
        viewHolder.nameTW.setText(recipe.getName());
        viewHolder.cookingTimeTW.setText(Integer.toString(recipe.getCookingTime()) + " мин.");

        int finalPosition = position;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                System.out.println("Recipeadapter обработчик");
                onRecipeClickListener.onRecipeClick(finalPosition);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageIW;
        final TextView nameTW, cookingTimeTW;

        public ViewHolder(View view) {
            super(view);
            imageIW = view.findViewById(R.id.photo);
            nameTW = view.findViewById(R.id.nameTW);
            cookingTimeTW = view.findViewById(R.id.cookingTimeTW);
        }
    }
}
