package com.example.kashaf.ocm.fyp.Activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.kashaf.ocm.fyp.R;

public class UserManagementFragment extends Fragment {

    CardView cardBlockUser, cardNewRequest;

    public UserManagementFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_user_managment, container, false);

        cardBlockUser = view.findViewById(R.id.cardUserBlock);
        cardNewRequest = view.findViewById(R.id.cardNewUser);

        cardNewRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRequestFragment nextFrag= new UserRequestFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, nextFrag, "frag_user_request")
                        .addToBackStack(null)
                        .commit();
            }
        });

        cardBlockUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlockUserManagmentFragment nextFrag= new BlockUserManagmentFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, nextFrag, "frag_user_block")
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}
