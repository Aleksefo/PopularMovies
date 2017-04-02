package com.example.alex.nanopopularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alex on 02-Apr-17.
 */

class GetMovieData extends AsyncTask<String, Void, List<Movie>> implements GetRawData.OnDownloadComplete {

	private static final String TAG = "GetMovieData";

	private List<Movie> mMovieList = null;
	private String mBaseURL;
	private String mKey;
	private String mFilter;
//	private String mSize;

	private final OnDataAvailable mCallBack;

	interface OnDataAvailable {

		void onDataAvailable(List<Movie> data, DownloadStatus status);
	}

	public GetMovieData(String mBaseURL, String mKey, String mFilter,
		OnDataAvailable mCallBack) {
		Log.d(TAG, "GetMovieData: called");
		this.mBaseURL = mBaseURL;
		this.mKey = mKey;
		this.mFilter = mFilter;
//		this.mSize = mSize;
		this.mCallBack = mCallBack;
	}

	void executeOnSameThread(String searchCriteria) {
		Log.d(TAG, "executeOnSameThread: starts");
		String destinationUri = createUri(mFilter, mKey);
//		String destinationUri = createUri(searchCriteria, mKey, mfilter, mSize);

		GetRawData getRawData = new GetRawData(this);
		getRawData.execute(destinationUri);
		Log.d(TAG, "executeOnSameThread: ends");
	}

	@Override
	protected List<Movie> doInBackground(String... params) {
		Log.d(TAG, "doInBackground: starts");
		String destinationUri = createUri(params[0], mKey);

		GetRawData getRawData = new GetRawData(this);
		getRawData.execute(destinationUri);
		Log.d(TAG, "doInBackground: ends");
		return mMovieList;
	}

	@Override
	protected void onPostExecute(List<Movie> movies) {
		super.onPostExecute(movies);
	}

	private String createUri(String mFilter, String mKey) {
		Log.d(TAG, "createUri: starts");

		return Uri.parse(mBaseURL).buildUpon()
			.appendPath(mFilter)
			.appendQueryParameter("api_key", mKey)
			.build().toString();
	}

	@Override
	public void onDownloadComplete(String data, DownloadStatus status) {
		Log.d(TAG, "onDownloadComplete: start. Status = " + status);

		if(status == DownloadStatus.OK) {
			mMovieList = new ArrayList<>();
			try {
				JSONObject jsonData = new JSONObject(data);
				JSONArray resultsArray = jsonData.getJSONArray("results");

				for(int i=0; i<resultsArray.length(); i++) {
					JSONObject jsonMovie = resultsArray.getJSONObject(i);
					String poster_path = jsonMovie.getString("poster_path");
					String overview = jsonMovie.getString("overview");
					String release_date = jsonMovie.getString("release_date");
					String title = jsonMovie.getString("title");
					String backdrop_path = jsonMovie.getString("backdrop_path");
					String vote_average = jsonMovie.getString("vote_average");

					Movie movieObject = new Movie(poster_path, overview, release_date, title, backdrop_path, vote_average);
					mMovieList.add(movieObject);

					Log.d(TAG, "onDownloadComplete: "+ movieObject.toString());
				}
			} catch(JSONException jsone) {
				jsone.printStackTrace();
				Log.e(TAG, "onDownloadComplete: Error processing Json data " + jsone.getMessage());
				status = DownloadStatus.FAILED_OR_EMPTY;
			}
		}

		if(mCallBack != null) {
			//now inform the caller that processing is done - possibly returning null if there was an error
			mCallBack.onDataAvailable(mMovieList,status);
		}
		Log.d(TAG, "onDownloadComplete: ends");
	}
}
