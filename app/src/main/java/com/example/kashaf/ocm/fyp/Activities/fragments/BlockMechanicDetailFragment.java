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

public class BlockMechanicDetailFragment extends Fragment {

    TextView tvMechanicNameAccountDetails, tvMechanicEmailAccountDetails, tvMechanicPhoneNoAccountDetails,
            tvMechanicCNICAccountDetails, tvMechanicTypeDetails;
    String name, email, phoneNo, key, cnic, type;
    Button btnBlocked, btnUnblocked;
    DatabaseReference mechanicRef;


    public BlockMechanicDetailFragment() {



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
        View view = inflater.inflate(R.layout.fragment_mechanic_blocked, container, false);


        btnUnblocked = view.findViewById(R.id.btnUnBlock);
        btnBlocked = view.findViewById(R.id.btnBlocked);
        tvMechanicNameAccountDetails = view.findViewById(R.id.tvMechanicNameAccountDetails);
        tvMechanicEmailAccountDetails = view.findViewById(R.id.tvMechanicEmailAccountDetails);
        tvMechanicPhoneNoAccountDetails = view.findViewById(R.id.tvMechanicPhoneNoAccountDetails);
        tvMechanicCNICAccountDetails = view.findViewById(R.id.tvMechanicCNICAccountDetails);
        tvMechanicTypeDetails = view.findViewById(R.id.tvMechanicType);

        tvMechanicNameAccountDetails.setText(name);
        tvMechanicEmailAccountDetails.setText(email);
        tvMechanicPhoneNoAccountDetails.setText(phoneNo);
        mechanicRef = FirebaseDatabase.getInstance().getReference("Mechanic").child(key);

        btnUnblocked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mechanicRef.child("status").setValue("ublocked");
                Toast.makeText(getActivity(), "UnBlocked", Toast.LENGTH_SHORT).show();
            }
        });

        btnBlocked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mechanicRef.child("status").setValue("blocked");
                Toast.makeText(getActivity(), "blocked", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
