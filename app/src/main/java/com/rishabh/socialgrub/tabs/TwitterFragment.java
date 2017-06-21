package com.rishabh.socialgrub.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.rishabh.socialgrub.R;

public class TwitterFragment extends Fragment {

	private WebView mWebView;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("Tag1","GamesFrag");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_twitter, container, false);

		mWebView = (WebView) rootView.findViewById(R.id.wvTwitter);

		mWebView.setWebViewClient(new WebViewClient());
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);

		String url="https://twitter.com/";
		if(url!=null) {
			mWebView.loadUrl(url);
		}else{
			Toast.makeText(getContext(), "URL Dead", Toast.LENGTH_SHORT).show();
		}
		return rootView;
	}

}
