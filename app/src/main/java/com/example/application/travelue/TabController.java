package com.example.application.travelue;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Adri on 16/02/2017.
 */
public class TabController extends Fragment {
    View view;
    ViewPager viewPager;
    TabLayout tabLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_tab_controller, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new sliderAdapter(getChildFragmentManager()));
        tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
                tabLayout.setupWithViewPager(viewPager);

                for (int i = 0; i < tabLayout.getTabCount(); i++) {
                    tabLayout.getTabAt(i).setIcon(R.drawable.rabanopng);
                }
            }
        });


        return view;
    }
    private class sliderAdapter extends FragmentPagerAdapter {
        private int[] imageResId = {
                R.drawable.button_custom,

        };
        final  String tabs[]={"Rutas", "tab2", "prueba"};
        public sliderAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new TabRutasTotales();
                case 1:
                    return new TabRutasTotales();
                case 2:
                    return new TabRutasTotales();
                default:
                    return null;
            }

        }



        @Override
        public int getCount() {
            return 3;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];

        }
    }
}
