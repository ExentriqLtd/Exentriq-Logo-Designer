package com.emiketic.logodesigner.create;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.emiketic.logodesigner.R;

public class LogoNameFragment extends Fragment {

    OnNextSelectedListener mListener;

    private Button btnNext;
    private TextInputLayout textLogo;

    public LogoNameFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logo_name, container, false);
        btnNext = (Button) view.findViewById(R.id.button);
        textLogo = (TextInputLayout) view.findViewById(R.id.editText);
        textLogo.setErrorEnabled(true);

        getActivity().setTitle(getString(R.string.logo_name_fragment_title));

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onNextSelected(textLogo.getEditText().getText().toString());
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnNextSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnNextSelectedListener");
        }
    }

    public interface OnNextSelectedListener {
        void onNextSelected(String logoName);
    }

}
