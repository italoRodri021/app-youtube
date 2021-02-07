package com.dinomobile.youtube.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dinomobile.youtube.R;
import com.dinomobile.youtube.model.Movie;
import com.dinomobile.youtube.model.json.Items;

import java.util.List;

public class AdapterMovie extends RecyclerView.Adapter<AdapterMovie.MyViewHolder> {

    private final List<Items> movieList;
    private final Context context;

    public AdapterMovie(List<Items> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_movie, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Items movie = movieList.get(position);
        holder.textTitle.setText( movie.snippet.title );
        holder.textDescription.setText(movie.snippet.description);
        holder.textDatePublish.setText(movie.snippet.publishedAt);
        holder.textTimePublish.setText(movie.snippet.liveBroadcastContent);

        Uri url = Uri.parse( movie.snippet.thumbnails.high.url );
        Glide.with(context).load(url).into(holder.imageThumbnail);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle, textDescription, textDatePublish, textTimePublish;
        ImageView imageThumbnail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.titleMovieAdapter);
            imageThumbnail = itemView.findViewById(R.id.imageMovieAdapter);
            textDescription = itemView.findViewById(R.id.textDescricaoAdapter);
            textDatePublish = itemView.findViewById(R.id.textDataPublicacaoAdapter);
            textTimePublish = itemView.findViewById(R.id.textTempoPublicacaoAdapter);

        }
    }

}
