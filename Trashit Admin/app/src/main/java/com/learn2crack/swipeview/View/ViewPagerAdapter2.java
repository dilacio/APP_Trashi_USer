package com.learn2crack.swipeview.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter2 extends FragmentStatePagerAdapter {

    private static int TAB_COUNT = 3;

    public ViewPagerAdapter2(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return ViewAltaMateriales.newInstance();
            case 1:
                return ViewModificacionMateriales.newInstance();
            case 2:
                return ViewListadoMateriales.newInstance();

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
                return ViewAltaMateriales.TITLE;

            case 1:
                return ViewModificacionMateriales.TITLE;

            case 2:
                return ViewListadoMateriales.TITLE;
        }
        return super.getPageTitle(position);
    }
}
