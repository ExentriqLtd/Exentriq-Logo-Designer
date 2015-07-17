package com.emiketic.logodesigner.create;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.emiketic.logodesigner.models.Logo;
import com.emiketic.logodesigner.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by stoufa on 08/07/15.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    OnItemClickListener mListener;

    // each data item is just an int representing the drawable id in this case
    private List<Logo> mDataset;

    public RecyclerViewAdapter() {
        mDataset = new ArrayList<>();
    }

    public void addAll(Collection<Logo> logoCollection) {
        mDataset.addAll(logoCollection);
    }

    public void removeAll() {
        mDataset.clear();
    }

    public void replaceAll(Collection<Logo> logoCollection) {
        removeAll();
        addAll(logoCollection);
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.logo_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mImageView.setImageResource(mDataset.get(position).getResourceId());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void seOnItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }


    public interface OnItemClickListener {
        void onItemClicked(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;

        public ViewHolder(View v) {
            super(v);
            mImageView = (ImageView) v.findViewById(R.id.image);

            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null)
                        mListener.onItemClicked(view, getLayoutPosition());
                }
            });
        }
    }
}