package com.mounacheikhna.decor.samples;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

public class SampleFragment extends Fragment /*android.app.Fragment*/ {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_frag, container, false);

        ViewStub stubWithDecorAttr = (ViewStub) view.findViewById(R.id.stub_with_decor_attr);
        stubWithDecorAttr.inflate();
        return view;
    }

}
    