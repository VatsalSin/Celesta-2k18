package in.org.celesta2k18.adapters;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import in.org.celesta2k18.fragments.HighlightsPage;
import in.org.celesta2k18.fragments.HomePage;

/**
 * Created by mayank on 26/5/17.
 */
public class PageFragmentAdapter extends FragmentPagerAdapter {
    public PageFragmentAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomePage();
            case 1:
                return new HighlightsPage();
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}
