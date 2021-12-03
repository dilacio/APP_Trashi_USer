package com.learn2crack.swipeview.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private static int TAB_COUNT = 3;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return ViewAltaPuntos.newInstance();
            case 1:
                return ViewModificacionPuntos.newInstance();
            case 2:
                return ViewListadoPuntos.newInstance();

        }
        return null;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return ViewAltaPuntos.TITLE;

            case 1:
                return ViewModificacionPuntos.TITLE;

            case 2:
                return ViewListadoPuntos.TITLE;
        }
        return super.getPageTitle(position);
    }
}
