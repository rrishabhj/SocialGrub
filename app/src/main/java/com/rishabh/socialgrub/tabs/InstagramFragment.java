package com.rishabh.socialgrub.tabs;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieSyncManager;
import android.webkit.WebBackForwardList;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.rishabh.socialgrub.R;

public class InstagramFragment extends Fragment {

	private WebView mWebView;
	private SwipeRefreshLayout swipeContainer;
	private String url;
	private View progressBar;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_facebook, container, false);

		mWebView = (WebView) rootView.findViewById(R.id.wvFacebook);
		swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
		progressBar = rootView.findViewById(R.id.progressBar);

		CookieSyncManager.createInstance(getContext());
		CookieSyncManager.getInstance().startSync();

		final WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(false);

		mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		mWebView.getSettings().setBuiltInZoomControls(false);
		mWebView.setOnKeyListener(new View.OnKeyListener(){

			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == MotionEvent.ACTION_UP && mWebView.canGoBack()) {
					handler.sendEmptyMessage(1);
					return true;
				}

				return false;
			}

		});
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon)
			{
				super.onPageStarted(view, url, favicon);
				progressBar.setVisibility(View.VISIBLE);

			}

			@Override
			public void onPageFinished(WebView view, String url)
			{
				super.onPageFinished(view, url);

				progressBar.setVisibility(View.GONE);
				swipeContainer.setRefreshing(false);
			}
		});

		url="https://www.instagram.com/";
		if(url!=null) {
			mWebView.loadUrl(url);
		}else{
			Toast.makeText(getContext(), "URL Dead", Toast.LENGTH_SHORT).show();
		}

		// Setup refresh listener which triggers new data loading
		swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				mWebView.loadUrl(mWebView.getUrl());
			}
		});

		// Configure the refreshing colors
		swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);

		return rootView;
	}

	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message message) {
			switch (message.what) {
				case 1:{
					webViewGoBack();
				}break;
			}
		}
	};

	private void webViewGoBack(){

		WebBackForwardList history = mWebView.copyBackForwardList();
		int index = -1;
		String url = null;

		while (mWebView.canGoBackOrForward(index)) {
			if (!history.getItemAtIndex(history.getCurrentIndex() + index).getUrl().equals("about:blank")) {
				mWebView.goBackOrForward(index);
				url = history.getItemAtIndex(-index).getUrl();
				Log.e("tag","first non empty" + url);
				break;
			}
			index --;

		}
	}

	@Override
	public void onResume() {
		super.onResume();
		CookieSyncManager.getInstance().stopSync();
	}

	@Override
	public void onPause() {
		super.onPause();
		CookieSyncManager.getInstance().sync();
	}
}
