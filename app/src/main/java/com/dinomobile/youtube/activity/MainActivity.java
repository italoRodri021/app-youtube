package com.dinomobile.youtube.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dinomobile.youtube.R;
import com.dinomobile.youtube.adapter.AdapterMovie;
import com.dinomobile.youtube.api.YoutubeServices;
import com.dinomobile.youtube.helper.RecyclerClick;
import com.dinomobile.youtube.helper.RetrofitConfig;
import com.dinomobile.youtube.helper.YoutubeConfig;
import com.dinomobile.youtube.model.json.Items;
import com.dinomobile.youtube.model.json.Result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerMovies;
    private SearchView searchView;
    private AdapterMovie adapter;
    private List<Items> movies = new ArrayList<>();
    private Result result;
    private ProgressBar progressBar;

    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = RetrofitConfig.getRetrofit();
        iniComponents();
        configSearch();

    }

    @Override
    protected void onStart() {
        super.onStart();
        getMovie("");
    }

    public void configSearch() {

        searchView.setQueryHint("Busque suas musicas prefereidas...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getMovie(newText);
                return true;
            }
        });

    }

    public void getMovie(String search) {

        // Removendo espacoes da query pesquisa
        String q = search.replaceAll(" ", "+");

        YoutubeServices services = retrofit
                .create(YoutubeServices.class);

        services.getMoviesChannel(
                "snippet", "date", "20", YoutubeConfig.KEY_YOUTUBE, YoutubeConfig.CHANNEL_ID, q).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                Log.d("RESULTADO", "result" + response.toString());

                if (response.isSuccessful()) {

                    progressBar.setVisibility(View.GONE);
                    result = response.body();
                    //Log.d("RESULTADO", "result" + result.items.get(2).id.videoId);

                    if (result != null) {

                        movies = result.items;

                        configRecycler();
                        itemClick();

                    }

                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {


            }
        });

    }

    public void itemClick() {

        recyclerMovies.addOnItemTouchListener(
                new RecyclerClick(this, recyclerMovies, new RecyclerClick.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Items item = movies.get(position);
                        String idMovie = item.id.videoId;

                        Intent i = new Intent(getApplicationContext(), PlayerActivity.class);
                        i.putExtra("TITLE", item.snippet.channelTitle);
                        i.putExtra("DESCRIPTION",item.snippet.description);
                        i.putExtra("ID_MOVIE", idMovie);
                        startActivity(i);

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }));

    }

    public void configRecycler() {

        adapter = new AdapterMovie(movies, this);
        recyclerMovies.setHasFixedSize(true);
        recyclerMovies.setLayoutManager(new LinearLayoutManager(this));
        recyclerMovies.setAdapter(adapter);

    }

    public void iniComponents() {

        Toolbar toolbar = findViewById(R.id.toobar);
        recyclerMovies = findViewById(R.id.recyclerVideos);
        progressBar = findViewById(R.id.progressBarVideos);
        searchView = findViewById(R.id.searchView);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

    }

    @Override
    public void onBackPressed() {

    }
}