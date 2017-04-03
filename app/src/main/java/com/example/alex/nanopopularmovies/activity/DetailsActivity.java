package com.example.alex.nanopopularmovies.activity;

import static com.example.alex.nanopopularmovies.adapter.MoviesAdapter.IMAGE_URL_BASE_PATH;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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

			voteAverage.setText("Average Rating: "+movie.getVoteAverage().toString()+"/10");
			overview.setText(movie.getOverview());
//
//			TextView photoTags = (TextView) findViewById(R.id.photo_tags);
//			photoTags.setText("Tags: " + photo.getTags());
//
//			TextView photoAuthor = (TextView) findViewById(R.id.photo_author);
//			photoAuthor.setText(photo.getAuthor());
//
			String image_url = IMAGE_URL_BASE_PATH + movie.getPosterPath();
			Log.d(TAG, "onCreate: " + image_url);
			Picasso.with(this)
				.load(image_url)
				.placeholder(android.R.drawable.sym_def_app_icon)
				.error(android.R.drawable.sym_def_app_icon)
				.into(poster);

//			Picasso.with(this).load(photo.getLink())
//				.error(R.drawable.placeholder)
//				.placeholder(R.drawable.placeholder)
//				.into(photoImage);
		}
	}

	@OnClick({R.id.releaseDate, R.id.voteAverage, R.id.overview})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.releaseDate:
				break;
			case R.id.voteAverage:
				break;
			case R.id.overview:
				break;
		}
	}
}
