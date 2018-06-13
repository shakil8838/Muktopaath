package com.example.muktopaath;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity {

	private WebView webview;
    private static final String TAG = "Main";
    private ProgressDialog progressBar;  

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getWindow().requestFeature(Window.FEATURE_PROGRESS);

        // Makes Progress bar Visible
        getWindow().setFeatureInt( Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);

        this.webview = (WebView)findViewById(R.id.webView);

        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        progressBar = ProgressDialog.show(MainActivity.this, "Loading", "Loading...");

        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {

                Log.i(TAG, "Finished loading URL: " +url);

                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                Log.e(TAG, "Error: " + description);

                // showing an Alert Dialog
                alertDialog.setTitle("Error");
                alertDialog.setMessage(description);
                alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

        // Loading URL into WebView
        webview.loadUrl("http://muktopaath.gov.bd/login/auth");
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		 switch(item.getItemId()){
         case R.id.open:       	 
             OpenNew();
             return true;
         default:
             return super.onOptionsItemSelected(item);
		 }
		 
	}

	public void OpenNew() {
		
		Uri openPage = Uri.parse("http://muktopaath.gov.bd/login/auth");
		Intent intent = new Intent(Intent.ACTION_VIEW, openPage);
		startActivity(intent);
	}
    
    
}
