package com.example.alex.nanopopularmovies.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import com.example.alex.nanopopularmovies.R;
import com.example.alex.nanopopularmovies.adapter.MoviesAdapter;
import com.example.alex.nanopopularmovies.model.Movie;
import com.example.alex.nanopopularmovies.model.MovieResponse;
import com.example.alex.nanopopularmovies.rest.MovieApiService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = MainActivity.class.getSimpleName();
	public static final String BASE_URL = "http://api.themoviedb.org/3/";
	private static Retrofit retrofit = null;
	private RecyclerView recyclerView = null;
	// insert your themoviedb.org API KEY here
	private final static String API_KEY = "96b3b30ed4fd5fac8de8fd6aee9f1af6";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
		connectAndGetApiData();
	}
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	// This method create an instance of Retrofit
// set the base url
	public void connectAndGetApiData() {
		if (retrofit == null) {
			retrofit = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		}
		MovieApiService movieApiService = retrofit.create(MovieApiService.class);
		Call<MovieResponse> call = movieApiService.getTopRatedMovies(API_KEY);
		call.enqueue(new Callback<MovieResponse>() {
			@Override
			public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
				List<Movie> movies = response.body().getResults();
				recyclerView.setAdapter(
					new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
				Log.d(TAG, "Number of movies received: " + movies.size());
			}

			@Override
			public void onFailure(Call<MovieResponse> call, Throwable throwable) {
				Log.e(TAG, throwable.toString());
			}
		});
	}

	//
//	@Override
//	public void onItem
}
