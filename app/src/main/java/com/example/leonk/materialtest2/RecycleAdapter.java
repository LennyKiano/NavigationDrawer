package com.example.leonk.materialtest2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by Leonk on 9/14/2017.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {

    private  Context context;
    private  LayoutInflater inflater;

    List<Information> data= Collections.emptyList();        //To handle data and prevent null pointer exception

    public RecycleAdapter(Context context, List<Information> data){


        this.context=context;


        inflater= LayoutInflater.from(context);

        this.data=data;


    }


    public void delete(int position){         //Method to handle a deleted item in the recycle view

        data.remove(position);

        notifyItemRemoved(position);




    }




    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=inflater.inflate(R.layout.custom_row,parent,false);   //passes root layout in the custom_row that is the linear_layout

        MyViewHolder holder= new MyViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        //Current data from Arraylist

        Information current=data.get(position);

        holder.icon.setImageResource(current.iconId);

        holder.title.setText(current.title);

    }

    @Override
    public int getItemCount() {

        return data.size();  //returns the total number of items in the data set hold by the adapter


    }




    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{   //to handle on click

        //Passing views in the custom layout

        ImageView icon;
        TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);


            icon= (ImageView) itemView.findViewById(R.id.listIcon);

            title=(TextView) itemView.findViewById(R.id.listText);

            icon.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

//            Toast.makeText(itemView.getContext(),"Item clicked at "+getAdapterPosition(),Toast.LENGTH_SHORT).show();

            delete(getAdapterPosition());

        }
    }
}