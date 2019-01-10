package com.yelatpv.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yelatpv.Principal.Home;
import com.yelatpv.R;


public class MiEmpresa extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_empresa, container, false);
        ((Home) getActivity()).getSupportActionBar().setTitle("Mi Empresa");
        return view;
    }

}