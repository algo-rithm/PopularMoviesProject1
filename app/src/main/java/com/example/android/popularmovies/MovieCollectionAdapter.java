package com.example.android.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Noodle Boy on 9/24/2015.
 */
public class MovieCollectionAdapter extends RecyclerView.Adapter<MovieCollectionAdapter.ViewHolder>{
    private String[] mDataset;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        public void onClick(View view, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView mTextView;

        public ViewHolder(TextView v){
            super(v);
            mTextView = v;
        }
    }

    public MovieCollectionAdapter(String[] dataSet, OnItemClickListener listener){
        mDataset = dataSet;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View v = li.inflate(R.layout.drawer_list_item, parent, false);
        TextView tv = (TextView) v.findViewById(android.R.id.text1);
        return new ViewHolder(tv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        holder.mTextView.setText(mDataset[position]);
        holder.mTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mListener.onClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount(){
        return mDataset.length;
    }

}