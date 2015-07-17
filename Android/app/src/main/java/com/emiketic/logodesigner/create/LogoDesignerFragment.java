package com.emiketic.logodesigner.create;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.emiketic.logodesigner.R;
import com.emiketic.logodesigner.preview.PreviewActivity;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class LogoDesignerFragment extends Fragment {

    private static final String ARG_LOGO_NAME = "name";
    private static final String ARG_LOGO_RESOURCE = "id";

    private String mLogoName;
    private int mResourceName;

    private long firstTouch = 0, secondTouch = 0;

    private OnDesignerListener mListener;

    private FrameLayout frame;
    private TextView textLogo;
    private ImageView imageLogo;
    FloatingActionButton btnShare;

    public LogoDesignerFragment() {
        // Required empty public constructor
    }

    public static LogoDesignerFragment newInstance(String logoName, int logoResource) {
        LogoDesignerFragment fragment = new LogoDesignerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LOGO_NAME, logoName);
        args.putInt(ARG_LOGO_RESOURCE, logoResource);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLogoName = getArguments().getString(ARG_LOGO_NAME);
            mResourceName = getArguments().getInt(ARG_LOGO_RESOURCE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        frame.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.backrepeat));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logo_designer, container, false);
        frame = (FrameLayout) view.findViewById(R.id.container);
        textLogo = (TextView) view.findViewById(R.id.text_logo);
        imageLogo = (ImageView) view.findViewById(R.id.image_logo);
        btnShare = (FloatingActionButton) view.findViewById(R.id.button_share);

        Toast.makeText(getActivity(), "Double tap to change symbol", Toast.LENGTH_SHORT).show();

        getActivity().setTitle(getString(R.string.logo_designer_fragment_title));

        textLogo.setText(mLogoName);
        imageLogo.setImageResource(mResourceName);

        initTouchEvents();

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), PreviewActivity.class);
                i.putExtra(PreviewActivity.ARG_BYTE_LOGO, getByte());
                i.putExtra(PreviewActivity.ARG_NAME_LOGO, mLogoName);
                startActivity(i);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnDesignerListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void initTouchEvents() {
        final int frameHeight = (int) getResources().getDimension(R.dimen.designer_height);
        imageLogo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                boolean moved = false;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        imageLogo.setColorFilter(Color.DKGRAY);
                        params.topMargin = (int) event.getRawY() - view.getHeight() - convertPixelsToDp(frameHeight);
                        params.leftMargin = (int) event.getRawX() - view.getWidth();
                        view.setLayoutParams(params);
                        moved = true;
                        break;

                    case MotionEvent.ACTION_UP:
                        if (moved) {
                            params.topMargin = (int) event.getRawY() - view.getHeight() - convertPixelsToDp(frameHeight);
                            params.leftMargin = (int) event.getRawX() - view.getWidth();
                            moved = false;
                            view.setLayoutParams(params);
                        }
                        imageLogo.clearColorFilter();
                        break;

                    case MotionEvent.ACTION_DOWN:
                        view.setLayoutParams(params);
                        if (firstTouch == 0) {
                            firstTouch = Calendar.getInstance().getTimeInMillis();
                        } else {
                            secondTouch = Calendar.getInstance().getTimeInMillis();
                            if (secondTouch - firstTouch < 200) {
                                mListener.onChangeLogoListener();
                            }
                            resetTimers();
                        }
                        break;
                }
                return true;
            }
        });

        textLogo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int color = textLogo.getCurrentTextColor();
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        textLogo.setTextColor(Color.DKGRAY);
                        //TODO
                        // 200 is experimental to manually adjust the margin
                        params.topMargin = (int) event.getRawY() - view.getHeight() - convertPixelsToDp(frameHeight) - 200;
                        params.leftMargin = (int) event.getRawX() - view.getWidth();
                        view.setLayoutParams(params);
                        break;

                    case MotionEvent.ACTION_UP:
                        params.topMargin = (int) event.getRawY() - view.getHeight() - convertPixelsToDp(frameHeight) - 200;
                        params.leftMargin = (int) event.getRawX() - view.getWidth();
                        textLogo.setTextColor(color);
                        view.setLayoutParams(params);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        view.setLayoutParams(params);
                        break;
                }
                return true;
            }
        });
    }

    private void resetTimers() {
        firstTouch = 0;
        secondTouch = 0;
    }

    private void executeNext() {
        if (firstTouch == 0) {
            firstTouch = Calendar.getInstance().getTimeInMillis();
        } else {
            secondTouch = Calendar.getInstance().getTimeInMillis();
            if (secondTouch - firstTouch < 200) {
                mListener.onChangeLogoListener();
                resetTimers();
            }
        }
    }

    private Bitmap drawBitmap() {
        frame.setDrawingCacheEnabled(true);
        frame.setBackgroundColor(Color.TRANSPARENT);
        frame.buildDrawingCache();
        return frame.getDrawingCache();
    }

    private byte[] getByte() {
        Bitmap bmp = drawBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public static int convertPixelsToDp(float px) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return Math.round(dp);
    }


    public interface OnDesignerListener {
        void onChangeLogoListener();
    }

}