package com.example.alex.nanopopularmovies.activity;

import static com.example.alex.nanopopularmovies.adapter.MoviesAdapter.IMAGE_URL_BASE_PATH;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.alex.nanopopularmovies.R;
import com.example.alex.nanopopularmovies.model.Movie;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailsActivity extends AppCompatActivity {

	private static final String TAG = "DetailsActivity";
	@BindView(R.id.poster)
	ImageView poster;
	@BindView(R.id.releaseDate)
	TextView releaseDate;
	@BindView(R.id.voteAverage)
	TextView voteAverage;
	@BindView(R.id.overview)
	TextView overview;
	@BindView(R.id.backdrop)
	ImageView backdrop;
	@BindView(R.id.back)
	ImageView back;
	@BindView(R.id.title)
	TextView title;
	@BindView(R.id.activity_details)
	ConstraintLayout activityDetails;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		supportRequestWindowFeature(AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY);
		setContentView(R.layout.activity_details);
		ButterKnife.bind(this);

		Intent intent = getIntent();
		Movie movie = (new Gson()).fromJson(intent.getStringExtra("DATA"), Movie.class);
		if (movie != null) {

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date date = format.parse(movie.getReleaseDate());
				format.applyPattern("MMMM yyyy");
				releaseDate.setText(format.format(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			voteAverage.setText("Average Rating: " + movie.getVoteAverage().toString() + "/10");
			overview.setText(movie.getOverview());
			title.setText(movie.getTitle());
//
//			TextView photoTags = (TextView) findViewById(R.id.photo_tags);
//			photoTags.setText("Tags: " + photo.getTags());
//
//			TextView photoAuthor = (TextView) findViewById(R.id.photo_author);
//			photoAuthor.setText(photo.getAuthor());
//
			String image_url = IMAGE_URL_BASE_PATH + movie.getPosterPath();
			Picasso.with(this)
				.load(image_url)
				.placeholder(android.R.drawable.sym_def_app_icon)
				.error(android.R.drawable.sym_def_app_icon)
				.into(poster);
			String backdrop_url = IMAGE_URL_BASE_PATH + movie.getBackdropPath();
			Log.d(TAG, "onCreate: " + backdrop_url);
			Picasso.with(this)
				.load(backdrop_url)
				.placeholder(android.R.drawable.sym_def_app_icon)
				.error(android.R.drawable.sym_def_app_icon)
				.into(backdrop);
		}
	}

	@OnClick(R.id.back)
	public void onViewClicked() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
}
