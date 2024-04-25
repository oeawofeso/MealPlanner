package com.example.mealplanner17.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner17.ModelsAPI.ExtendedIngredient;
import com.example.mealplanner17.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * The IngredientsAdapter class is responsible for populating a RecyclerView with a list of ingredients for a meal.
 * It displays each ingredient's name, quantity, and image using a custom ViewHolder.

 * The data for the adapter is provided as a list of ExtendedIngredient objects.

 * The implementation of this adapter is based on the Spoonacular API documentation:
 * https://spoonacular.com/food-api/docs#Get-Random-Recipes
 * The documentation was used as a reference to understand the structure of the API response and to correctly display the ingredient data.

 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsViewHolder> {

    Context context;
    List<ExtendedIngredient> list;

    public IngredientsAdapter(Context context, List<ExtendedIngredient> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IngredientsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_meal_ingredients,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        holder.textView_ingredients_name.setText(list.get(position).name);
        holder.textView_ingredients_name.setSelected(true);
        holder.textView_ingredients_quantity.setText(list.get(position).original);
        holder.textView_ingredients_quantity.setSelected(true);
        Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/"+list.get(position).image).into(holder.imageView_ingredients);




    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class IngredientsViewHolder extends RecyclerView.ViewHolder {
    TextView textView_ingredients_quantity, textView_ingredients_name;
    ImageView imageView_ingredients;
    public IngredientsViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_ingredients_quantity= itemView.findViewById(R.id.textView_ingredients_quantity);
        textView_ingredients_name =itemView.findViewById(R.id.textView_ingredients_name);
        imageView_ingredients= itemView.findViewById(R.id.imageView_ingredients);

    }
}
