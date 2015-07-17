package com.emiketic.logodesigner.models;

import com.emiketic.logodesigner.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stoufa on 08/07/15.
 */
public class LogoCollection {

    private static LogoCollection instance;
    private List<Logo> mLogos;

    private LogoCollection() {
        mLogos = new ArrayList<>();

        mLogos.add(new Logo(R.drawable.checkbox1, "checkbox1"));
        mLogos.add(new Logo(R.drawable.checkbox2, "checkbox1"));
        mLogos.add(new Logo(R.drawable.circle, "circle"));
        mLogos.add(new Logo(R.drawable.polygonal, "polygonal"));
        mLogos.add(new Logo(R.drawable.rectangle1, "rectangle1"));
    }

    public static LogoCollection getInstance() {
        if (instance == null) {
            instance = new LogoCollection();
        }
        return instance;
    }

    public List<Logo> searchLogos(String query) {
        List<Logo> logos = new ArrayList<>();
        for (Logo logo : mLogos) {
            if (logo.getTag().contains(query))
                logos.add(logo);
        }
        return logos;
    }

    public List<Logo> getLogos() {
        return mLogos;
    }
}
