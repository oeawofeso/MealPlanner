package com.example.mealplanner17.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner17.Listeners.RecipeClickListener;
import com.example.mealplanner17.ModelsAPI.Recipe;
import com.example.mealplanner17.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RandomRecipeAdapter extends RecyclerView.Adapter<RandomRecipeAdapter.RandomRecipeViewHolder> {
    private Context context;
    private List<Recipe> recipeList;
    private RecipeClickListener listener;

    // Constructor for the adapter
    public RandomRecipeAdapter(Context context, List<Recipe> recipeList, RecipeClickListener listener) {
        this.context = context;
        this.recipeList = recipeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RandomRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_random_recipe, parent, false);
        return new RandomRecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RandomRecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);

        holder.textViewTitle.setText(recipe.title);
        holder.textViewTitle.setSelected(true);
        holder.textViewLikes.setText(recipe.aggregateLikes + " Likes");
        holder.textViewServings.setText(recipe.servings + " Servings");

        Picasso.get().load(recipe.image).into(holder.imageViewFood);

        // Set favorite button status
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyFavoriteMeals", Context.MODE_PRIVATE);
        boolean isFavorite = sharedPreferences.getBoolean(String.valueOf(recipe.id), false);
        holder.btnFavorite.setSelected(isFavorite);

        holder.btnFavorite.setOnClickListener(v -> toggleFavoriteStatus(recipe, holder.btnFavorite));

        holder.randomListContainer.setOnClickListener(v -> listener.onRecipeClick(String.valueOf(recipe.id)));
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    // Method to update the data in the adapter
    public void updateData(List<Recipe> newRecipes) {
        // Clear the current list and add the new recipes
        recipeList.clear();
        recipeList.addAll(newRecipes);
        // Notify the adapter that the data has changed
        notifyDataSetChanged();
    }

    // Method to toggle favorite status of a recipe
    private void toggleFavoriteStatus(Recipe recipe, Button btnFavorite) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyFavoriteMeals", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (btnFavorite.isSelected()) {
            editor.putBoolean(String.valueOf(recipe.id), false);
            Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show();
        } else {
            editor.putBoolean(String.valueOf(recipe.id), true);
            Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show();
        }

        editor.apply();
        btnFavorite.setSelected(!btnFavorite.isSelected());
    }

    // Inner ViewHolder class
    public static class RandomRecipeViewHolder extends RecyclerView.ViewHolder {
        CardView randomListContainer;
        TextView textViewTitle, textViewServings, textViewLikes;
        ImageView imageViewFood;
        Button btnFavorite;

        public RandomRecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            randomListContainer = itemView.findViewById(R.id.random_list_container);
            textViewTitle = itemView.findViewById(R.id.textView_title);
            textViewServings = itemView.findViewById(R.id.textView_servings);
            textViewLikes = itemView.findViewById(R.id.textView_like);
            imageViewFood = itemView.findViewById(R.id.imageView_food);
            btnFavorite = itemView.findViewById(R.id.btn_favorite);
        }
    }
}

