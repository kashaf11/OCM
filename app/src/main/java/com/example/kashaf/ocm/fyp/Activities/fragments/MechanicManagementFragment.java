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

public class MechanicManagementFragment extends Fragment {

    CardView cardBlockMechanic, cardNewRequest;

    public MechanicManagementFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_mechanic_managment, container, false);

        cardNewRequest = view.findViewById(R.id.cardMechanicRequest);
        cardBlockMechanic = view.findViewById(R.id.cardBlockMechanic);

        cardNewRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MechanicRequestFragment nextFrag= new MechanicRequestFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, nextFrag, "frag_mechanic_request")
                        .addToBackStack("user_request")
                        .commit();
            }
        });

        cardBlockMechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MechanicBlockFragment nextFrag= new MechanicBlockFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, nextFrag, "frag_mechanic_block")
                        .addToBackStack("user_block")
                        .commit();
            }
        });


        return view;
    }
}
