package com.example.kashaf.ocm.fyp.Activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kashaf.ocm.fyp.R;

public class UpdateVehicleFragment extends Fragment {
    Spinner Update_Vehicle;
    EditText Update_edt;
    Button Update;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_update_vehicle,container,false);

        Update_Vehicle=view.findViewById(R.id.spinner_uv);
        Update_edt=view.findViewById(R.id.edtvuname);
        Update=view.findViewById(R.id.edtvuname);

        return view;
    }
}
