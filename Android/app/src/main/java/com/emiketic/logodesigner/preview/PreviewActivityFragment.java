package com.emiketic.logodesigner.preview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.emiketic.logodesigner.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PreviewActivityFragment extends Fragment {

    OnExportSelectedListener mListener;

    byte[] mData;
    Bitmap mBitmap;

    ImageView image1;
    ImageView image2;

    public static PreviewActivityFragment newInstance(byte[] bytes) {
        PreviewActivityFragment fragment = new PreviewActivityFragment();
        Bundle args = new Bundle();
        args.putByteArray(PreviewActivity.ARG_BYTE_LOGO, bytes);
        fragment.setArguments(args);
        return fragment;
    }

    public PreviewActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mData = getArguments().getByteArray(PreviewActivity.ARG_BYTE_LOGO);
            mBitmap = BitmapFactory.decodeByteArray(mData, 0, mData.length, null);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_preview, container, false);
        image1 = (ImageView) view.findViewById(R.id.logo_card);
        image2 = (ImageView) view.findViewById(R.id.logo_shirt);
        image1.setImageBitmap(mBitmap);
        image2.setImageBitmap(mBitmap);

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnExportSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnExportSelectedListener");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.preview_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.export) {
            mListener.onExportSelected();
        }

        return super.onOptionsItemSelected(item);
    }

    public interface OnExportSelectedListener {
        void onExportSelected();
    }
}
