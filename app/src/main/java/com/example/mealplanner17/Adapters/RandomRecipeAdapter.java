package com.example.mealplanner17.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner17.Listeners.RecipeClickListener;
import com.example.mealplanner17.Models.Recipe;
import com.example.mealplanner17.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RandomRecipeAdapter extends RecyclerView.Adapter<RandomRecipeViewHolder> {

    Context context;
    List<Recipe> list;
    RecipeClickListener listener;

    public RandomRecipeAdapter(Context context, List<Recipe> list,RecipeClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener=listener;

    }

    @NonNull
    @Override
    public RandomRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RandomRecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.list_random_recipe,parent,false));

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RandomRecipeViewHolder holder, int position) {
      holder.textView_title.setText(list.get(position).title);
      holder.textView_title.setSelected(true);
      holder.TextView_like.setText(list.get(position).aggregateLikes+" Likes");
      holder.TextView_servings.setText(list.get(position).servings+" Servings");

      Picasso.get().load(list.get(position).image).into(holder.imageView_food);

      holder.random_list_container.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              listener.onRecipeClick(String.valueOf(list.get(holder.getAdapterPosition()).id));
          }
      });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class RandomRecipeViewHolder extends RecyclerView.ViewHolder{
   CardView random_list_container;
   TextView textView_title,TextView_servings,TextView_like;
   ImageView imageView_food;


    public RandomRecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        random_list_container= itemView.findViewById(R.id.random_list_container);
        textView_title= itemView.findViewById(R.id.textView_title);
        TextView_servings= itemView.findViewById(R.id.TextView_servings);
        TextView_like= itemView.findViewById(R.id.TextView_like);
        imageView_food= itemView.findViewById(R.id.imageView_food);

    }
}
