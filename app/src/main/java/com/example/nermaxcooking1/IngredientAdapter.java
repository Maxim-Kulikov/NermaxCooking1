package com.example.nermaxcooking1;

import static java.sql.Types.NULL;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nermaxcooking.R;

import java.util.ArrayList;
import java.util.Map;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    private ArrayList<Ingredient> chosenIngredients;
    private ArrayList<String> names;
    private ViewHolder holder;
    private Context context;

    IngredientAdapter(ArrayList<Ingredient> chosenIngredients, Context context) {
        this.chosenIngredients = chosenIngredients;
        names = new ArrayList<>();
        for (Map.Entry<String, Ingredient> entry: ControllerForRecipes.getController().getIngredients().entrySet()) {
            names.add(entry.getKey());
        }
        this.context = context;
    }

    public ViewHolder getHolder(){
        return this.holder;
    }

    public ArrayList<Ingredient> getChosenIngredients(){
        return this.chosenIngredients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_ingredient, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int finalPosition = position;
        if(chosenIngredients.get(position).getName()!=null && chosenIngredients.get(position).getWeight() != NULL){
            holder.autoComplete.setText(chosenIngredients.get(position).getName());
            holder.inputGrams.setText(String.valueOf(chosenIngredients.get(position).getWeight()));
        }

        this.holder = holder;

        this.holder.rubbishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getItemCount()>1) {
                    deleteIngredientAt(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                    setHolder(holder);
                }
            }
        });
    }

    public void setHolder(ViewHolder holder){
        this.holder = holder;
    }

    @Override
    public int getItemCount() {
        return this.chosenIngredients.size();
    }

    private void removeIngredient(int position){
        this.chosenIngredients.remove(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        AutoCompleteTextView autoComplete;
        EditText inputGrams;
        ImageButton rubbishButton;

        public ViewHolder(@NonNull View view) {
            super(view);
            autoComplete = view.findViewById(R.id.autoCompleteType);
            autoComplete.setAdapter(new ArrayAdapter<>(context, R.layout.ingredients_list, names));
            autoComplete.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String name = "";
                    if(!s.toString().isEmpty())name = s.toString();
                    getChosenIngredients().get(getAdapterPosition()).setName(name);
                }
            });
            inputGrams = view.findViewById(R.id.inputGramsET);
            inputGrams.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    double grams = NULL;
                    if(!s.toString().isEmpty()) grams = Double.parseDouble(s.toString());
                        getChosenIngredients().get(getAdapterPosition()).setWeight(grams);
                }
            });
            rubbishButton = view.findViewById(R.id.redRubbishBinIB);
        }
    }

    public void deleteIngredientAt(int position){
        this.chosenIngredients.remove(position);
    }

    public void updateChosenIngredientAt(int position, Ingredient ingredient){
        this.chosenIngredients.set(position, ingredient);
    }

    public void addIngredient(Ingredient ingredient){
        this.chosenIngredients.add(ingredient);
    }

    public boolean areIngredientsEmpty(){
        for(Ingredient ingredient: chosenIngredients){
            if(ingredient.getName().isEmpty() || ingredient.getWeight() == NULL){
                return true;
            }
        }
        return false;
    }

}



