package ca.ulaval.ima.tp3.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import ca.ulaval.ima.tp3.MainActivity;
import ca.ulaval.ima.tp3.R;
import ca.ulaval.ima.tp3.ui.main.fragmentpaquets.UserDialogForAdvertisement;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1,
            R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return new Offers();
            case 1 :
                return new Sales();
            case 2 :
                return new Advertisement();
            default:
                return null ;
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0 :
                return mContext.getResources().getString(R.string.tab_text_1);
            case 1 :
                return mContext.getResources().getString(R.string.tab_text_2);
            case 2:
                return mContext.getResources().getString(R.string.tab_text_3);
            default:
                return null ;
        }

    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }
}