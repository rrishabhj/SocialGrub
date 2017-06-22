package com.rishabh.socialgrub.tabs;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebBackForwardList;
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

		View rootView = inflater.inflate(R.layout.fragment_facebook, container, false);

		mWebView = (WebView) rootView.findViewById(R.id.wvFacebook);

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
		mWebView.setWebViewClient(new WebViewClient());
		String url="https://twitter.com/";
		if(url!=null) {
			mWebView.loadUrl(url);
		}else{
			Toast.makeText(getContext(), "URL Dead", Toast.LENGTH_SHORT).show();
		}

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


}
