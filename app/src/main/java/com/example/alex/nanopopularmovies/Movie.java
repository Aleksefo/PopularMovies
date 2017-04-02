package com.example.alex.nanopopularmovies;

/**
 * Created by Alex on 02-Apr-17.
 */

class Movie {

	private String poster_path;
	private String overview;
	private String release_date;
	private String title;
	private String backdrop_path;
	private String vote_average;

	public Movie(String poster_path, String overview, String release_date, String title,
		String backdrop_path, String vote_average) {
		this.poster_path = poster_path;
		this.overview = overview;
		this.release_date = release_date;
		this.title = title;
		this.backdrop_path = backdrop_path;
		this.vote_average = vote_average;
	}

	public String getPoster_path() {
		return poster_path;
	}

	public void setPoster_path(String poster_path) {
		this.poster_path = poster_path;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getRelease_date() {
		return release_date;
	}

	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBackdrop_path() {
		return backdrop_path;
	}

	public void setBackdrop_path(String backdrop_path) {
		this.backdrop_path = backdrop_path;
	}

	public String getVote_average() {
		return vote_average;
	}

	public void setVote_average(String vote_average) {
		this.vote_average = vote_average;
	}

	@Override
	public String toString() {
		return "Movie{" +
			"poster_path='" + poster_path + '\'' +
			", overview='" + overview + '\'' +
			", release_date='" + release_date + '\'' +
			", title='" + title + '\'' +
			", backdrop_path='" + backdrop_path + '\'' +
			", vote_average='" + vote_average + '\'' +
			'}';
	}
}
