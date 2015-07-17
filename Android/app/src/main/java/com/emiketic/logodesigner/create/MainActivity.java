package com.emiketic.logodesigner.create;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.emiketic.logodesigner.models.LogoCollection;
import com.emiketic.logodesigner.R;

public class MainActivity extends AppCompatActivity
        implements LogoNameFragment.OnNextSelectedListener,
        LogoDesignerFragment.OnDesignerListener,
        LogoPickerFragment.OnPickSelectedListener {

    private static final int DEFAULT_RESOURCE = 1;

    private String mLogoName;
    private int mLogoResource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            LogoNameFragment logoNameFragment = new LogoNameFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, logoNameFragment).commit();
        }
    }

    @Override
    public void onNextSelected(String logoName) {
        this.mLogoName = logoName;
        this.mLogoResource = LogoCollection.getInstance().getLogos().get(DEFAULT_RESOURCE).getResourceId();
        LogoDesignerFragment logoNameFragment = LogoDesignerFragment.newInstance(mLogoName, mLogoResource);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, logoNameFragment).commit();
    }

    @Override
    public void onChangeLogoListener() {
        LogoPickerFragment logoPickerFragment = LogoPickerFragment.newInstance(mLogoName, mLogoResource);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, logoPickerFragment).commit();
    }

    @Override
    public void onPickSelected(int resourceId) {
        this.mLogoResource = resourceId;
        LogoDesignerFragment logoDesignerFragment = LogoDesignerFragment.newInstance(mLogoName, mLogoResource);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, logoDesignerFragment).commit();
    }
}