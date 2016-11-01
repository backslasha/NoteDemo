package mouse.com.cloudnote.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class FragPagerAdapter extends FragmentPagerAdapter {
    private FragmentManager mFragmentManager;
    private List<Fragment> fragments;

    public FragPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragmentManager = fm;
        this.fragments = fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
