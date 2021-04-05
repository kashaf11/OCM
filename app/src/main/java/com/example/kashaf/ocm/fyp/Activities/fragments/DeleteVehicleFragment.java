package com.example.kashaf.ocm.fyp.Activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.kashaf.ocm.fyp.R;

public class DeleteVehicleFragment {
    Spinner Delete_Vehicle;
    TextView DeleteTxt;
    Button Delete;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_delete_vehicle,container,false);

        Delete_Vehicle=view.findViewById(R.id.spinner_dv);
        Delete=view.findViewById(R.id.btn_deletev);
        DeleteTxt=view.findViewById(R.id.txt_delete);

        return view;
}
}
