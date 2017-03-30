package com.example.alex.nanopopularmovies;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

enum DownloadStatus {IDLE, PROCESSING, NOT_INITIALIZED, FAILED_OR_EMPTY, OK}

class GetRawData extends AsyncTask<String, Void, String> {

	private static final String TAG = "GetRawData";
	private DownloadStatus mDownloadStatus;

	public GetRawData() {
		this.mDownloadStatus = DownloadStatus.IDLE;
	}

	@Override
	protected void onPostExecute(String s) {
		Log.d(TAG, "onPostExecute: parameter = " + s);
//		super.onPostExecute(s);
	}

	@Override
	protected String doInBackground(String... params) {
		HttpURLConnection connection = null;
		BufferedReader reader = null;

		if (params == null) {
			mDownloadStatus = DownloadStatus.NOT_INITIALIZED;
		}

		try {
			mDownloadStatus = DownloadStatus.PROCESSING;
			URL url = new URL(params[0]);

			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			int response = connection.getResponseCode();
			Log.d(TAG, "doInBackground: The response was " + response);
			StringBuilder result = new StringBuilder();

			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String line;
			while (null != (line = reader.readLine())) {
				result.append(line).append("\n");
			}

			mDownloadStatus = DownloadStatus.OK;
			return result.toString();

		} catch (MalformedURLException e) {
			Log.e(TAG, "doInBackground: Invalid URL " + e.getMessage());
		} catch (IOException e) {
			Log.e(TAG, "doInBackground: IO Exception reading data:" + e.getMessage());
		} catch (SecurityException e) {
			Log.e(TAG, "doInBackground: Security Exception. Need permission?" + e.getMessage());
		} finally {
			if (connection != null) {
				try {
					reader.close();
				} catch (IOException e) {
					Log.e(TAG, "doInBackground: Error closing stream " + e.getMessage());
				}
			}
		}

		mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
		return null;
	}
}