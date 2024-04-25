package com.example.mealplanner17.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner17.Listeners.RecipeClickListener;
import com.example.mealplanner17.ModelsAPI.Recipe;
import com.example.mealplanner17.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RandomRecipeAdapter extends RecyclerView.Adapter<RandomRecipeViewHolder> {

    Context context;
    List<Recipe> list;
    RecipeClickListener listener;

    public RandomRecipeAdapter(Context context, List<Recipe> list, RecipeClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RandomRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RandomRecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.list_random_recipe, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RandomRecipeViewHolder holder, int position) {
        Recipe recipe = list.get(position);

        holder.textView_title.setText(recipe.title);
        holder.textView_title.setSelected(true);

        holder.TextView_servings.setText(recipe.servings + " Servings");

        Picasso.get().load(recipe.image).into(holder.imageView_food);

        // Set favorite button status
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyFavoriteMeals", Context.MODE_PRIVATE);
        boolean isFavorite = sharedPreferences.getBoolean(String.valueOf(recipe.id), false);
        holder.btnFavorite.setSelected(isFavorite);

        holder.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavoriteStatus(recipe, holder.btnFavorite);
            }
        });

        holder.random_list_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRecipeClick(String.valueOf(recipe.id));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

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
    public void updateData(List<Recipe> newData) {
        this.list.clear();
        this.list.addAll(newData);
        notifyDataSetChanged();
    }

}
class RandomRecipeViewHolder extends RecyclerView.ViewHolder{
   CardView random_list_container;
   TextView textView_title,TextView_servings,TextView_like;
   ImageView imageView_food;
    Button btnFavorite;


    public RandomRecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        random_list_container = itemView.findViewById(R.id.random_list_container);
        textView_title = itemView.findViewById(R.id.textView_title);
        TextView_servings = itemView.findViewById(R.id.TextView_servings);
        imageView_food = itemView.findViewById(R.id.imageView_food);
        btnFavorite = itemView.findViewById(R.id.btn_favorite);
    }
}

