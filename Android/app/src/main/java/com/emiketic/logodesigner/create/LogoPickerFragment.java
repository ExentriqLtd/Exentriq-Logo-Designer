package com.emiketic.logodesigner.create;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.emiketic.logodesigner.R;
import com.emiketic.logodesigner.models.LogoCollection;

/**
 * Created by stoufa on 08/07/15.
 */
public class LogoPickerFragment extends Fragment
        implements RecyclerViewAdapter.OnItemClickListener {

    private static final String ARG_LOGO_NAME = "name";
    private static final String ARG_LOGO_RESOURCE = "id";
    OnPickSelectedListener mListener;
    private String mLogoName;
    private int mLogoResource;
    private ImageView image;
    private TextInputLayout edit;

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public LogoPickerFragment() {
    }

    public static LogoPickerFragment newInstance(String logoName, int logoResource) {
        LogoPickerFragment fragment = new LogoPickerFragment();
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
            mLogoResource = getArguments().getInt(ARG_LOGO_RESOURCE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logo_picker, container, false);

        getActivity().setTitle(getString(R.string.logo_picker_fragment_title));

        setHasOptionsMenu(true);

        image = (ImageView) view.findViewById(R.id.image);
        image.setImageResource(mLogoResource);

        edit = (TextInputLayout) view.findViewById(R.id.edit);
        edit.getEditText().requestFocus();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.logo_recycler);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerViewAdapter();
        mAdapter.addAll(LogoCollection.getInstance().getLogos());
        mAdapter.seOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        final EditText editText = edit.getEditText();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchQuery = editText.getText().toString().toLowerCase();
                mAdapter.replaceAll(LogoCollection.getInstance().searchLogos(searchQuery));
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.picker_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.done) {
            mListener.onPickSelected(mLogoResource);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnPickSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnPickSelectedListener");
        }
    }

    @Override
    public void onItemClicked(View v, int position) {
        mLogoResource = LogoCollection.getInstance().getLogos().get(position).getResourceId();
        image.setImageResource(mLogoResource);
    }

    public interface OnPickSelectedListener {
        void onPickSelected(int resourceId);
    }
}