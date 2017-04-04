package com.example.alex.nanopopularmovies.activity;

import android.os.Bundle;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
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
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = MainActivity.class.getSimpleName();
	public static final String BASE_URL = "http://api.themoviedb.org/3/";
	private static Retrofit retrofit = null;
//	@BindView(R.id.toolbar)
//	Toolbar toolbar;
	private RecyclerView recyclerView = null;
	// insert your themoviedb.org API KEY here
	private final static String API_KEY = "96b3b30ed4fd5fac8de8fd6aee9f1af6";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		supportRequestWindowFeature(AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
		connectAndGetApiData("top");
	}

	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.top:
				connectAndGetApiData("top");
				return true;
			case R.id.pop:
				connectAndGetApiData("pop");
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	// This method create an instance of Retrofit
// set the base url
	public void connectAndGetApiData(String mSwitch) {
		if (retrofit == null) {
			retrofit = new Builder()
				.baseUrl(BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		}
		MovieApiService movieApiService = retrofit.create(MovieApiService.class);

		Call<MovieResponse> call;
		call = caller(mSwitch);
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

	public Call caller(String mSwitch) {
		MovieApiService movieApiService = retrofit.create(MovieApiService.class);
		Call<MovieResponse> call;
		switch (mSwitch) {
			case "top":
				call = movieApiService.getTopRatedMovies(API_KEY);
				return call;
			case "pop":
				call = movieApiService.getPopularMovies(API_KEY);
				return call;
			default:
				call = movieApiService.getTopRatedMovies(API_KEY);
				return call;
		}
	}

	//
//	@Override
//	public void onItem
}
