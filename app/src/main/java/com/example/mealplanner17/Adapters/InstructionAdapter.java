package com.example.mealplanner17.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner17.Models.Step;
import com.example.mealplanner17.R;

import java.util.List;

public class InstructionAdapter extends RecyclerView.Adapter<InstructionAdapter.InstructionViewHolder> {
    private List<Step> steps;
    private int tempLay;
    private Context holdCont;


    public InstructionAdapter(List<Step> steps, int rowLayout, Context context) {
        this.steps = steps;
        this.tempLay = rowLayout;
        this.holdCont = context;
    }


    public static class InstructionViewHolder extends RecyclerView.ViewHolder {
        CardView instructionsLayout;
        TextView steps;
        public InstructionViewHolder(@NonNull View itemView) {
            super(itemView);
            instructionsLayout = itemView.findViewById(R.id.instructionCard);
            steps =  itemView.findViewById(R.id.step);
        }
    }



    //un used methods, need to import for the extends class
    @NonNull
    @Override
    public InstructionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(tempLay,parent,false);
        return new InstructionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionViewHolder holder, int position) {
        holder.steps.setText(steps.get(position).getStep());


    }

    @Override
    public int getItemCount() {
        if (steps!= null){
            return steps.size();
        }

        return 0;
    }
}
