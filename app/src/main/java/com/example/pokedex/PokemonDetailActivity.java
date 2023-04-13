

package com.example.pokedex;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.pokedex.adapter.PokemonTypeAdapter;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.skydoves.progressview.ProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PokemonDetailActivity extends AppCompatActivity {

    TextView textView;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    ProgressView AttackBar,DefenceBar,SpeedBar,ExpBar;
    ProgressView HpBar;
    RelativeLayout relativeLayout;

    RecyclerView recyclerView;
    ImageView imageView;
    private static final String TAG = "Pokemon";

    String url ;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);


        recyclerView = (RecyclerView) findViewById(R.id.pokemonTypes);
        relativeLayout = (RelativeLayout) findViewById(R.id.pokemonDetailDominantColor);
        imageView = (ImageView) findViewById(R.id.pokemonDetailImage);
        textView = (TextView) findViewById(R.id.pokemonDetailName);
        textView2 = (TextView) findViewById(R.id.pokemonHeight);
        textView3 = (TextView) findViewById(R.id.pokemonWeight);
        HpBar = (ProgressView) findViewById(R.id.pokemonHP);
        AttackBar = (ProgressView) findViewById(R.id.pokemonAttack);
        DefenceBar=(ProgressView) findViewById(R.id.pokemonDefence);
        SpeedBar=(ProgressView) findViewById(R.id.pokemonSpeed);
        ExpBar=(ProgressView) findViewById(R.id.pokemonExp);
        textView1 = (TextView) findViewById(R.id.pokemonDetailID);

        PokemonTypeAdapter pokemonTypeAdapter = new PokemonTypeAdapter();
        FlexboxLayoutManager flexboxLayoutManager =  new FlexboxLayoutManager(this);
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setJustifyContent(JustifyContent.SPACE_AROUND);
        flexboxLayoutManager.setAlignItems(AlignItems.CENTER);
        recyclerView.setLayoutManager(flexboxLayoutManager);
        recyclerView.setAdapter(pokemonTypeAdapter);

        Intent intent = getIntent();

        url = "https://pokeapi.co/api/v2/pokemon/"+intent.getIntExtra("id",0);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    textView1.setText("#"+intent.getIntExtra("id",0));
                    textView2.setText(response.getInt("height")+"M");
                    textView3.setText(response.getInt("weight")+"KG");
                    JSONArray stats = response.getJSONArray("stats");
                    JSONArray types = response.getJSONArray("types");
                    ArrayList<String> dataset = new ArrayList<>();
                    for (int i = 0; i < types.length(); i++) {
                        JSONObject type = types.getJSONObject(i);
                        String[] typename=type.getString("type").split("\"");
                        dataset.add(typename[3]);
                    }
                    pokemonTypeAdapter.addAll(dataset);


                    HpBar.setProgress(stats.getJSONObject(0).getInt("base_stat"));
                    HpBar.setLabelText(stats.getJSONObject(0).getInt("base_stat")+"/150");
                    AttackBar.setProgress(stats.getJSONObject(1).getInt("base_stat"));
                    AttackBar.setLabelText(stats.getJSONObject(1).getInt("base_stat")+"/150");
                    DefenceBar.setProgress(stats.getJSONObject(2).getInt("base_stat"));
                    DefenceBar.setLabelText(stats.getJSONObject(2).getInt("base_stat")+"/150");
                    SpeedBar.setProgress(stats.getJSONObject(5).getInt("base_stat"));
                    SpeedBar.setLabelText(stats.getJSONObject(5).getInt("base_stat")+"/150");
                    ExpBar.setProgress(response.getInt("base_experience"));
                    ExpBar.setLabelText(response.getInt("base_experience")+"/300");

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error.getMessage());
            }
        });

        Volley.newRequestQueue(this).add(request);

        textView.setText(intent.getStringExtra("name"));
        Glide.with(this)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"+intent.getIntExtra("id",0)+".png")
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e,
                                                Object model,
                                                Target<Drawable> target,
                                                boolean isFirstResource) {
                        Log.d("Pokedex","Image Not Working");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource,
                                                   Object model,
                                                   Target<Drawable> target,
                                                   DataSource dataSource,
                                                   boolean isFirstResource) {
                        Palette.from(((BitmapDrawable)resource).getBitmap())
                                .generate(palette -> {
                                    int Colorid=palette.getDominantColor(0);
                                    relativeLayout.setBackgroundColor(Colorid);

                                });
                        return false;
                    }
                })
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);


    }


}