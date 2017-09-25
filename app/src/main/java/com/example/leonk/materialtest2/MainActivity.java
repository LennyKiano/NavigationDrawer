package com.example.leonk.materialtest2;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
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

        mTabs.setDistributeEvenly(true);                 //Tabs take equal space on the screen

        mTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {

                return ContextCompat.getColor(getApplicationContext(),R.color.colorAccent);
            }
        });

        mTabs.setCustomTabView(R.layout.custom_tab_view,R.id.tabText);

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

        String[] tabText=getResources().getStringArray(R.array.tabs);

        int[] icons={R.drawable.ic_action_home,R.drawable.ic_action_hot,R.drawable.ic_action_person};


        public MyPageAdapter(FragmentManager fm) {
            super(fm);

            tabText=getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {

            MyFragment myFragment= MyFragment.getInstance(position);

            return myFragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {    //where you display content of the tab

            Drawable drawable= ResourcesCompat.getDrawable(getResources(),icons[position],null);

            drawable.setBounds(0,0,36,36);   //To set space for icons to take up

            ImageSpan imageSpan=new ImageSpan(drawable);          //To embed images in the text because this method returns Char and not images

            SpannableString spannableString= new SpannableString(" ");        //To mix text and String. The space is to make sure the icon appear

            spannableString.setSpan(imageSpan,0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);   //Merge Type

//            return tabText[position];

            return spannableString;
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
