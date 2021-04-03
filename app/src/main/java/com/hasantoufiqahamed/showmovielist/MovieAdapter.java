package com.hasantoufiqahamed.showmovielist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    static int otherMessage = 0;
    private int percent;

    Context context;
    List<Movie> movie;

    public MovieAdapter(Context context) {
        this.context = context;
        movie = new ArrayList<>();
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatBubble = null;

        return new MovieHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movie, parent, false));
    }

    @Override
    public int getItemViewType(int position) {

        return otherMessage;
    }


    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        try {
            holder.bindView(movie.get(position));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return movie.size();
    }

    public void setData(List<Movie> code) {
        this.movie = code;
        notifyDataSetChanged();
    }

    class MovieHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView title;
        private TextView rating;
        private TextView rDate;
        private TextView language;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.imageView);
            title=itemView.findViewById(R.id.title);
            rating=itemView.findViewById(R.id.rating);
            rDate=itemView.findViewById(R.id.rDate);
            language=itemView.findViewById(R.id.language);
        }


        public void bindView(Movie movie) throws IOException {

            Glide.with(imageView.getContext()).load("https://image.tmdb.org/t/p/w500"+movie.getPoster_path()).centerCrop().into(imageView);
            title.setText(movie.getTitle());
            rating.setText(movie.getVote_average().toString());
            rDate.setText(movie.getRelease_date());
            language.setText(movie.getOriginal_language());
        }
    }
}
