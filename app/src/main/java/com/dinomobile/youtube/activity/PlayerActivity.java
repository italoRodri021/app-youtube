package com.dinomobile.youtube.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dinomobile.youtube.R;
import com.dinomobile.youtube.helper.YoutubeConfig;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private String ID_MOVIE;
    private YouTubePlayerView youTubePlayer;
    private TextView textTitle, textDescription;
    private LinearLayout linearLayout;
    private Button btnFullScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        linearLayout = findViewById(R.id.contentPlayerVideo);
        textTitle = findViewById(R.id.textTituloPlayer);
        btnFullScreen = findViewById(R.id.btnAssistirTelaCheia);
        textDescription = findViewById(R.id.textDescricaoPlayer);
        youTubePlayer = findViewById(R.id.youtubePlayerView);
        youTubePlayer.initialize(YoutubeConfig.KEY_YOUTUBE, this);

        Intent i = getIntent();
        ID_MOVIE = i.getStringExtra("ID_MOVIE");

        textTitle.setText(i.getStringExtra("TITLE"));
        textDescription.setText(i.getStringExtra("DESCRIPTION"));

    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        youTubePlayer.setFullscreen(false);
        youTubePlayer.setShowFullscreenButton(false);
        youTubePlayer.loadVideo( ID_MOVIE );

        btnFullScreen.setOnClickListener((v)->{

            //linearLayout.setVisibility(View.GONE);
            //youTubePlayer.setFullscreen(true);

        });

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        onBackPressed();
        Toast.makeText(this, "Falha no carregamento do vídeo!", Toast.LENGTH_SHORT).show();
    }
}