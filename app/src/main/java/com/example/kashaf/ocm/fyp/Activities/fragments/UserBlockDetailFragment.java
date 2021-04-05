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

public class UserBlockDetailFragment extends Fragment {

    TextView tvUserNameAccountDetails, tvUserEmailAccountDetails, tvUserPhoneNoAccountDetails;
    String name, email, phoneNo, key;
    Button btnUnBlocked, btnBlocked;
    DatabaseReference userRef;

    public UserBlockDetailFragment() {

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
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_block_detail, container, false);

        btnUnBlocked = view.findViewById(R.id.btnUnBlock);
        btnBlocked = view.findViewById(R.id.btnBlocked);
        tvUserNameAccountDetails = view.findViewById(R.id.tvUserNameAccountDetails);
        tvUserEmailAccountDetails = view.findViewById(R.id.tvUserEmailAccountDetails);
        tvUserPhoneNoAccountDetails = view.findViewById(R.id.tvUserPhoneNoAccountDetails);

        tvUserNameAccountDetails.setText(name);
        tvUserEmailAccountDetails.setText(email);
        tvUserPhoneNoAccountDetails.setText(phoneNo);

        btnUnBlocked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userRef = FirebaseDatabase.getInstance().getReference("User").child(key);
                userRef.child("status").setValue("unblocked");
                Toast.makeText(getActivity(), "UnBlocked", Toast.LENGTH_SHORT).show();
            }
        });

        btnBlocked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userRef = FirebaseDatabase.getInstance().getReference("User").child(key);
                userRef.child("status").setValue("blocked");
                Toast.makeText(getActivity(), "Blocked", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}
