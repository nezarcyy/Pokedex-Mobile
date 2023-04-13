
package com.example.pokedex.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.pokedex.R;
import com.example.pokedex.models.Pokedex;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder>{

    private ArrayList<Pokedex> dataset;
    private Context context;

    private ItemClickListener itemClickListener;



    public PokemonAdapter(Context context,ItemClickListener itemClickListener) {
        dataset=new ArrayList<>();
        this.context=context;
        this.itemClickListener=itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pokemon_item,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokedex p = dataset.get(position);
        holder.textView.setText(p.getName());
        holder.itemView.setOnClickListener(view -> {
            itemClickListener.onItemClick(p);
        });

        Glide.with(context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"+p.getId()+".png")
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e
                            , Object model
                            , Target<Drawable> target
                            , boolean isFirstResource) {
                        Log.d("Pokedex","Image Not Working");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource
                            , Object model
                            , Target<Drawable> target
                            , DataSource dataSource
                            , boolean isFirstResource) {
                        Palette.from(((BitmapDrawable)resource).getBitmap())
                                .generate(palette -> {
                                    int Colorid=palette.getDominantColor(0);
                                    holder.linearLayout.setBackgroundColor(Colorid);

                                });

                        return false;
                    }
                })
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);

    }



    public interface ItemClickListener{
        void onItemClick(Pokedex pokemon);
    }
    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addPokemonListe(ArrayList<Pokedex> pokemons) {
        dataset.addAll(pokemons);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView;
        private LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.pokemonImage);
            textView = (TextView) itemView.findViewById(R.id.pokemonName);
            linearLayout =(LinearLayout) itemView.findViewById(R.id.pokemonDominantColor);
        }
    }
}
