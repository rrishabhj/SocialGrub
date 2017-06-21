package com.rishabh.socialgrub.adaptor;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;
import com.rishabh.socialgrub.tabs.FacebookFragment;
import com.rishabh.socialgrub.tabs.InstagramFragment;
import com.rishabh.socialgrub.tabs.TwitterFragment;
import java.lang.ref.WeakReference;

public class TabsPagerAdapter extends FragmentStatePagerAdapter {

	private final SparseArray<WeakReference<Fragment>> instantiatedFragments = new SparseArray<>();


	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			FacebookFragment facebookFragment =new FacebookFragment();
			facebookFragment.setRetainInstance(true);
			return facebookFragment;
		case 1:
			InstagramFragment instagramFragment=new InstagramFragment();
			instagramFragment.setRetainInstance(true);
			return instagramFragment;
			case 2:
				TwitterFragment twitterFragment=new TwitterFragment();
				twitterFragment.setRetainInstance(true);
				return twitterFragment;
		default:
			return new FacebookFragment();
		}
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 2;
	}


	@Override public CharSequence getPageTitle(int position) {
		if(position==0){
			return new String("Download");
		}else {
			return new String("History");
		}
	}

	@Override
	public Object instantiateItem(final ViewGroup container, final int position) {
		final Fragment fragment = (Fragment) super.instantiateItem(container, position);
		instantiatedFragments.put(position, new WeakReference<>(fragment));
		return fragment;
	}

	@Override
	public void destroyItem(final ViewGroup container, final int position, final Object object) {
		instantiatedFragments.remove(position);
		super.destroyItem(container, position, object);
	}

	@Nullable
	public Fragment getFragment(final int position) {
		final WeakReference<Fragment> wr = instantiatedFragments.get(position);
		if (wr != null) {
			return wr.get();
		} else {
			return null;
		}
	}

}
