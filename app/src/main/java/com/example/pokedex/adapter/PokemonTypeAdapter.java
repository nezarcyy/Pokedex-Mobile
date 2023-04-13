
package com.example.pokedex.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex.R;
import com.skydoves.androidribbon.RibbonView;

import java.util.ArrayList;

public class PokemonTypeAdapter extends RecyclerView.Adapter<PokemonTypeAdapter.ViewHolder> {


    ArrayList<String> dataset ;

    public PokemonTypeAdapter() {
        this.dataset = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pokemon_type,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = dataset.get(position);
        holder.ribbonView.setText(name);
        if(name.contains("water")) holder.ribbonView.setRibbonBackgroundColor(Color.parseColor("#063970"));
        if(name.contains("normal")) holder.ribbonView.setRibbonBackgroundColor(Color.parseColor("#b8997b"));
        if(name.contains("fairy")) holder.ribbonView.setRibbonBackgroundColor(Color.parseColor("#873e23"));
        if(name.contains("fire")) holder.ribbonView.setRibbonBackgroundColor(Color.parseColor("#e8844a"));
        if(name.contains("electric")) holder.ribbonView.setRibbonBackgroundColor(Color.parseColor("#dedc73"));
        if(name.contains("poison")) holder.ribbonView.setRibbonBackgroundColor(Color.parseColor("#ab60d6"));
        if(name.contains("grass")) holder.ribbonView.setRibbonBackgroundColor(Color.parseColor("#1e6124"));
        if(name.contains("flying")) holder.ribbonView.setRibbonBackgroundColor(Color.parseColor("#02b4ba"));
        if(name.contains("fighting")) holder.ribbonView.setRibbonBackgroundColor(Color.parseColor("#73563f"));
        if(name.contains("ground")) holder.ribbonView.setRibbonBackgroundColor(Color.parseColor("#42362d"));
        if(name.contains("bug")) holder.ribbonView.setRibbonBackgroundColor(Color.parseColor("#4be364"));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addAll(ArrayList<String> dataset) {
        this.dataset.addAll(dataset);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RibbonView ribbonView ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ribbonView =(RibbonView) itemView.findViewById(R.id.pokemonTypeName);
        }
    }
}
