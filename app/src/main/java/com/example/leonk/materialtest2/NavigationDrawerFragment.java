package com.example.leonk.materialtest2;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;


/**
 * A simple {@link Fragment} subclass.
 */


/*

   STEPS TO HANDLE THE RECYCLER CLICK

   1. Create a class that EXTENDS RecyclerView.OnItemTouchListener
   2. Create an interface inside that class that supports click and long click and indicates the view that was clicked and the position where it was clicked
   3. Create a GestureDetector to detect ACTION_UP single tap and long press events
   4. Return true from the singleTap to indicate your Gesture Detector has consumed the event
   5. Find the childView containing the coordinates specified by the motionEvent and if the childView is not null and the listener is not null either fire a long click event
   6. Use the OnInterceptTouchEvent of your RecyclerView to check if the childView is not null, the listener is not null and the gesture detector consumed the touch event
   7. If the above condition holds true, fire the click event.




 */
public class NavigationDrawerFragment extends Fragment {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private boolean mUserLearnedDrawer;   //indicates if the user knows the drawer is there or not
    private boolean mFromSavedInstanceState; //if the fragment is being started for the first time or from Rotation.

    public static final String PREF_FILE_NAME="testpref";
    public static final String KEY_USER_LEARED_DRAWER="user_learned_drawer";

    private View containerView;

    private RecyclerView recyclerView;

    private RecycleAdapter adapter;     // the recycleView adapter



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserLearnedDrawer=Boolean.valueOf(readFromPreferences(getActivity(),KEY_USER_LEARED_DRAWER,"false"));

        if( savedInstanceState!=null){    //means that the fragment is coming from rotation/already made

            mFromSavedInstanceState=true;


        }


    }

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View layout=inflater.inflate(R.layout.fragment_navigation_drawer, container, false);


        //Setting up RecyclerView,adapter and layoutManger

        recyclerView=(RecyclerView) layout.findViewById(R.id.drawerRecycleList);


        adapter=new RecycleAdapter(getActivity(),getData());

//        adapter.setClickListener(this);       //listener for the interface  the fragment is the object that implements the ClickListener

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));   //We are using a linear layout to display thing in order

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Toast.makeText(getActivity(),"OnClick"+position,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onLongClick(View view, int position) {

                Toast.makeText(getActivity(),"OnLongClick"+position,Toast.LENGTH_SHORT).show();


            }
        }));

        return layout;
    }



    public static  List<Information> getData(){

        List<Information> data=new ArrayList<>();   //adapter requires array list of data

        int[] icons={R.drawable.ic_filter_1_black_24dp,R.drawable.ic_filter_2_black_24dp,R.drawable.ic_filter_3_black_24dp,R.drawable.ic_filter_4_black_24dp};

        String[] titles={"Home","About","Contact Us","Settings"};


        for(int i=0;i<icons.length && i<titles.length;i++){   //for adding the items ,Setting .length to prevent app from  crushing in the case for items are added

            Information current=new Information();

            current.iconId=icons[i];
            current.title=titles[i];

            data.add(current);      //adding it to the arraylist needed for the adapter


        }

        return data;


    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolBar) {

        containerView=getActivity().findViewById(fragmentId);

        mDrawerLayout=drawerLayout;
        mDrawerToggle=new ActionBarDrawerToggle(getActivity(),drawerLayout,toolBar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if(!mUserLearnedDrawer){ //condition hasn't seen it

                    mFromSavedInstanceState=true;  //user just saw the drawer

                    saveToPreferences(getActivity(),KEY_USER_LEARED_DRAWER,mUserLearnedDrawer+"");

                }
                getActivity().invalidateOptionsMenu();
            }


            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);


                getActivity().invalidateOptionsMenu();

            }

            //method for dimming toolbar when the navigation Drawer is below the toolbar

//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
//
//
//                if(slideOffset<0.6){
//
//                    toolBar.setAlpha(1-slideOffset);   //1 because at slide off is 0 the toolbar will not be visible
//
//                }
//
//
//
//
//
//                //super.onDrawerSlide(drawerView, slideOffset);
//            }
        };

        if (!mUserLearnedDrawer && mFromSavedInstanceState){
            //user has opened app for the very first time

            mDrawerLayout.openDrawer(containerView);


        }

        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {

                mDrawerToggle.syncState();   //hamburger
            }
        });

    }

    public static void saveToPreferences(Context context,String preferenceName,String preferenceValue){


        SharedPreferences sharedPreferences=context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);  //To ensure only app can change preference file
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(preferenceName,preferenceValue);
        editor.apply();   // makes it save faster

    }

    public static String  readFromPreferences(Context context,String preferenceName,String defaultValue){

        SharedPreferences sharedPreferences=context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);

        return sharedPreferences.getString(preferenceName,defaultValue);


    }


//    @Override
//    public void itemClicked(View view, int position) {
//
//
//         startActivity(new Intent(getActivity(),Main2Activity.class));
//
//
//
//    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{ //Step 1

        private GestureDetector gestureDetector;  //Step 3
        private ClickListener clickListener;


        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener){

            Log.d("RecyclerView","Constructor Invoked");

            this.clickListener=clickListener;

            gestureDetector= new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){

                @Override
                public boolean onSingleTapUp(MotionEvent e) {

                    Log.d("Gesture","onSingleTapUp" +e);

                    return true;           //step 4


                }

                @Override
                public void onLongPress(MotionEvent e) {

                    View child=recyclerView.findChildViewUnder(e.getX(),e.getY());      //step 5

                    if(child!=null && clickListener!=null){

                        clickListener.onLongClick(child,recyclerView.getChildAdapterPosition(child));
                    }

                    Log.d("Gesture","onLongPress" +e);


                    super.onLongPress(e);
                }
            });


        }


        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child=rv.findChildViewUnder(e.getX(),e.getY());      //step 5

            if(child!=null && clickListener!=null && gestureDetector.onTouchEvent(e)){

                clickListener.onClick(child,rv.getChildAdapterPosition(child));


            }




//            Log.d("RecyclerView","onInterceptTouchEvent"+gestureDetector.onTouchEvent(e)+" " +e);

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            Log.d("RecyclerView","onTouchEvent" +e);


        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public static interface ClickListener{  //Step 2

        public void onClick(View view,int position);
        public void onLongClick(View view,int position);





    }
}
