package com.example.mealplanner17.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner17.Models.ExtendedIngredient;
import com.example.mealplanner17.R;
import com.squareup.picasso.Picasso;

import java.util.List;

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
        return new IngredientsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_meal_ingredients, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        ExtendedIngredient ingredient = list.get(position);

        String cleanName = removeHtmlTags(ingredient.name);
        String cleanQuantity = removeHtmlTags(ingredient.original);

        holder.textView_ingredients_name.setText(cleanName);
        holder.textView_ingredients_name.setSelected(true);

        holder.textView_ingredients_quantity.setText(cleanQuantity);
        holder.textView_ingredients_quantity.setSelected(true);

        Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/" + ingredient.image).into(holder.imageView_ingredients);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static String removeHtmlTags(String html) {
        // Replace <div> tags with line breaks
        html = html.replace("<div>", "").replace("</div>", "\n");
        // Replace <br> tags with line breaks
        html = html.replace("<br>", "\n");
        // Replace line breaks (from various sources) with a consistent representation
        html = html.replace("\r\n", "\n").replace("\r", "\n");
        // Remove remaining HTML tags
        html = html.replaceAll("\\<.*?\\>", "");
        return html;
    }
}

class IngredientsViewHolder extends RecyclerView.ViewHolder {
    TextView textView_ingredients_quantity, textView_ingredients_name;
    ImageView imageView_ingredients;

    public IngredientsViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_ingredients_quantity = itemView.findViewById(R.id.textView_ingredients_quantity);
        textView_ingredients_name = itemView.findViewById(R.id.textView_ingredients_name);
        imageView_ingredients = itemView.findViewById(R.id.imageView_ingredients);
    }
}
