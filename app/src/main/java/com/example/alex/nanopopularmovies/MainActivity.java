package com.example.alex.nanopopularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

	@BindView(R.id.button)
	Button button;
	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate: start");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		GetRawData getRawData = new GetRawData();
		getRawData.execute("http://api.themoviedb.org/3/movie/top_rated?api_key=96b3b30ed4fd5fac8de8fd6aee9f1af6");
		Log.d(TAG, "onCreate: end");
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		Log.d(TAG, "onCreateOptionsMenu() returned: " + true);
		return true;
	}
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
//		switch (item.getItemId()) {
//			case R.id.new_game:
//				newGame();
//				return true;
//			case R.id.help:
//				showHelp();
//				return true;
//			default:
//				return super.onOptionsItemSelected(item);
//		}
//	}

	@OnClick(R.id.button)
	public void onViewClicked() {
		Log.d(TAG, "onViewClicked: ");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
