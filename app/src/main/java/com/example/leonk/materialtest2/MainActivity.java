package com.example.leonk.materialtest2;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import tabs.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private SlidingTabLayout mTabs;

    private ViewPager mPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nav_above_appbar);

        toolbar = (Toolbar) findViewById(R.id.app_bar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);



        drawerFragment.setUp(R.id.fragment_navigation_drawer,(DrawerLayout)findViewById(R.id.drawer_layout),toolbar);

        mPager=(ViewPager) findViewById(R.id.pager);

        mPager.setAdapter(new MyPageAdapter(getSupportFragmentManager()));

        mTabs=(SlidingTabLayout) findViewById(R.id.tabs);

        mTabs.setViewPager(mPager);






    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.settings:

                Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_LONG).show();

                return true;

            case R.id.navigate:

                startActivity(new Intent(this, Main2Activity.class));

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class MyPageAdapter extends FragmentPagerAdapter{   //Adapter for the ViewPager

        String[] tabs;


        public MyPageAdapter(FragmentManager fm) {
            super(fm);

            tabs=getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {

            MyFragment myFragment= MyFragment.getInstance(position);

            return myFragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    public static class MyFragment extends Fragment {

        private TextView textView;

        public static MyFragment getInstance(int position) {  //int position to rep the current position being rep inside the fragment

            MyFragment myFragment = new MyFragment();

            Bundle args= new Bundle();

            args.putInt("Position",position);

            myFragment.setArguments(args);

            return myFragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,@Nullable
                                 Bundle savedInstanceState) {

            View layout=inflater.inflate(R.layout.fragment_my, container, false);

            textView=(TextView) layout.findViewById(R.id.position);

            Bundle bundle=getArguments();

            if(bundle!=null){

                textView.setText("The Page selected is "+bundle.getInt("Position"));
            }

            return layout;


        }

    }


}
