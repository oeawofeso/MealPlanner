package com.mealproject.mealplanner17.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mealproject.mealplanner17.ModelsAPI.Recipe;
import com.mealproject.mealplanner17.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> implements View.OnClickListener {

    private final RecyclerView recyclerView;
    private Context context;
    private ArrayList<String> clickedRecipeIds;


    @Override
    public void onClick(View v) {
        int position = recyclerView.getChildAdapterPosition(v);
        String clickedRecipeId = clickedRecipeIds.get(position);


        Context context = v.getContext();


    }


    public interface SpoonacularService {
        @GET("recipes/{id}/information")
        Call<Recipe> getRecipeDetails(@Path("id") String recipeId, @Query("apiKey") String apiKey);
    }

    public HistoryAdapter(Context context, ArrayList<String> clickedRecipeIds, RecyclerView recyclerView) {
        this.context = context;
        this.clickedRecipeIds = clickedRecipeIds;
        this.recyclerView = recyclerView;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.history_item_layout, parent, false);

        view.setOnClickListener(this);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        String recipeId = clickedRecipeIds.get(position);


        fetchRecipeDetails(recipeId, holder);
    }

    private void fetchRecipeDetails(String recipeId, final HistoryViewHolder holder) {

        String apiKey = "3ef8f95c17944ed6bac930281b8b453e";


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spoonacular.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SpoonacularService service = retrofit.create(SpoonacularService.class);


        Call<Recipe> call = service.getRecipeDetails(recipeId, apiKey);
        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if (response.isSuccessful()) {
                    Recipe recipe = response.body();
                    String imageUrl= recipe.getImage();

                    if (recipe != null) {
                        holder.recipeTitleTextView.setText(recipe.getTitle());
                        Picasso.get().load(imageUrl).into(holder.imageFood);
                    } else {
                        holder.recipeTitleTextView.setText("Recipe Not Found");
                    }
                } else {
                    holder.recipeTitleTextView.setText("Failed to fetch recipe details");
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                holder.recipeTitleTextView.setText("Network Error");
            }
        });
    }

    @Override
    public int getItemCount() {
        return clickedRecipeIds.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {

        TextView recipeTitleTextView;
        ImageView imageFood;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            recipeTitleTextView = itemView.findViewById(R.id.history_item_title);
            imageFood = itemView.findViewById(R.id.history_item_image);
        }
    }
}
