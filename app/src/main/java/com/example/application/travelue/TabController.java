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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by Adri on 16/02/2017.
 */
public class TabController extends Fragment {
    View view;
    ViewPager viewPager;
    TabLayout tabLayout;
    Context context;



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
                //tabLayout.setupWithViewPager(viewPager);




                    tabLayout.getTabAt(0).setIcon(R.drawable.ic_explore_white_24dp);
                    tabLayout.getTabAt(1).setIcon(R.drawable.misrutasbuenas);
                    tabLayout.getTabAt(2).setIcon(R.drawable.ic_mail_outline_white_24dp);

/**
                view = getActivity().getLayoutInflater().inflate(R.layout.customtab, null);
                view.findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_explore_white_24dp);
                tabLayout.addTab(tabLayout.newTab().setCustomView(view));


                view = getActivity().getLayoutInflater().inflate(R.layout.customtab, null);
                view.findViewById(R.id.icon).setBackgroundResource(R.drawable.misrutasbuenas);
                tabLayout.addTab(tabLayout.newTab().setCustomView(view));


                view = getActivity().getLayoutInflater().inflate(R.layout.customtab, null);
                view.findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_mail_outline_white_24dp);
                tabLayout.addTab(tabLayout.newTab().setCustomView(view));
*/


            }
        });


        return view;
    }
    private class sliderAdapter extends FragmentPagerAdapter {
        private int[] imageResId = {
                R.drawable.button_custom,

        };
        //final  String tabs[]={"Rutas", "tab2", "prueba"};
        public sliderAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new TabRutasTotales();
                case 1:
                    return new TabMisRutas();
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
        /**
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];


        }
        */
    }
}
