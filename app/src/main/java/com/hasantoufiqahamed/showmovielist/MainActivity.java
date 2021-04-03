package com.hasantoufiqahamed.showmovielist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RequestQueue mQueue;
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private List<Movie> movieList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQueue= Volley.newRequestQueue(this);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new MovieAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);

        readMovie("popular");

        findViewById(R.id.popular).setOnClickListener(popular->{
            movieList.clear();
            readMovie("popular");
        });

        findViewById(R.id.nowP).setOnClickListener(nowP->{
            movieList.clear();
            readMovie("now_playing");
        });

        findViewById(R.id.upC).setOnClickListener(upC->{
            movieList.clear();
            readMovie("upcoming");
        });

        findViewById(R.id.topR).setOnClickListener(topR->{
            movieList.clear();
            readMovie("top_rated");
        });
    }

    private void readMovie(String cat) {
        String url="https://api.themoviedb.org/3/movie/"+cat+"?api_key=b5f02f06da1166a51d16384706a3dcd6";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject mv = jsonArray.getJSONObject(i);

                        Movie movie=new Movie(
                                mv.getBoolean("adult"),
                                mv.getString("backdrop_path"),
                                mv.getInt("id"),
                                mv.getString("original_language"),
                                mv.getString("original_title"),
                                mv.getString("overview"),
                                mv.getDouble("popularity"),
                                mv.getString("poster_path"),
                                mv.getString("release_date"),
                                mv.getString("title"),
                                mv.getBoolean("video"),
                                mv.getDouble("vote_average"),
                                mv.getInt("vote_count"));

                        movieList.add(i, movie);
                        adapter.setData(movieList);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
            error.printStackTrace();
        });

        mQueue.add(request);
    }
}