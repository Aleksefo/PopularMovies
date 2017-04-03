package com.example.alex.nanopopularmovies.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by Alex on 03-Apr-17.
 */

public class MovieResponse {
	@SerializedName("results")
	private List<Movie> results;
	public List<Movie> getResults() {
		return results;
	}
}
