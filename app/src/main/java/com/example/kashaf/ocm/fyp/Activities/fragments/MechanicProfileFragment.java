package com.example.kashaf.ocm.fyp.Activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kashaf.ocm.fyp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MechanicProfileFragment extends Fragment {

    DatabaseReference mechanicRef;
    FirebaseAuth firebaseAuth;

    public MechanicProfileFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mechanic_profile, container, false);


        mechanicRef = FirebaseDatabase.getInstance().getReference("Mechanic").child(firebaseAuth.getCurrentUser().getUid());

        TextView tvUserName, tvUserEmail, tvMechanicPhoneNo, tvMechanicCNIC;
        tvUserName = view.findViewById(R.id.tvMechanicName);
        tvUserEmail = view.findViewById(R.id.tvMechanicEmail);
        tvMechanicPhoneNo = view.findViewById(R.id.tvMechanicPhoneNo);
        tvMechanicCNIC = view.findViewById(R.id.tvMechanicCNIC);


        mechanicRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name = snapshot.child("name").getValue().toString();
                String email = snapshot.child("email").getValue().toString();
                String phoneNo = snapshot.child("phoneNo").getValue().toString();
                String cnic = snapshot.child("cnic").getValue().toString();

                tvUserName.setText(name);
                tvUserEmail.setText(email);
                tvMechanicPhoneNo.setText(phoneNo + "");
                tvMechanicCNIC.setText(cnic + "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}
