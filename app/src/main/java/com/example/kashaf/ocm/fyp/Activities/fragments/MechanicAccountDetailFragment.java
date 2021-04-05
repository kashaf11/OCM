package com.example.kashaf.ocm.fyp.Activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kashaf.ocm.fyp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MechanicAccountDetailFragment extends Fragment {

    TextView tvMechanicNameAccountDetails, tvMechanicEmailAccountDetails, tvMechanicPhoneNoAccountDetails,
            tvMechanicCNICAccountDetails, tvMechanicTypeDetails;
    String name, email, phoneNo, key, cnic, type;
    Button btnAccepted, btnRejected;
    DatabaseReference mechanicRef;

    public MechanicAccountDetailFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            key = args.getString("key");
            name = args.getString("name");
            email = args.getString("email");
            phoneNo = args.getString("phoneNo");
            cnic = args.getString("cnic");
            type = args.getString("type");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mechanic_account_details, container, false);

        btnAccepted = view.findViewById(R.id.btnAccpet);
        btnRejected = view.findViewById(R.id.btnReject);
        tvMechanicNameAccountDetails = view.findViewById(R.id.tvMechanicNameAccountDetails);
        tvMechanicEmailAccountDetails = view.findViewById(R.id.tvMechanicEmailAccountDetails);
        tvMechanicPhoneNoAccountDetails = view.findViewById(R.id.tvMechanicPhoneNoAccountDetails);
        tvMechanicCNICAccountDetails = view.findViewById(R.id.tvMechanicCNICAccountDetails);
        tvMechanicTypeDetails = view.findViewById(R.id.tvMechanicType);

        tvMechanicNameAccountDetails.setText(name);
        tvMechanicEmailAccountDetails.setText(email);
        tvMechanicPhoneNoAccountDetails.setText(phoneNo);
        mechanicRef = FirebaseDatabase.getInstance().getReference("Mechanic").child(key);

        btnAccepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mechanicRef.child("status").setValue("accepted");
                Toast.makeText(getActivity(), "accepted", Toast.LENGTH_SHORT).show();
            }
        });

        btnRejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mechanicRef.child("status").setValue("rejected");
                Toast.makeText(getActivity(), "rejected", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


}
