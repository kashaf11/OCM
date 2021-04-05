package com.example.kashaf.ocm.fyp.Activities.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kashaf.ocm.fyp.Activities.Adapters.IClickListener;
import com.example.kashaf.ocm.fyp.Activities.Adapters.MechanicAccountAdapter;
import com.example.kashaf.ocm.fyp.Activities.Models.Mechanic;
import com.example.kashaf.ocm.fyp.Activities.network.NetworkManager;
import com.example.kashaf.ocm.fyp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class MechanicRequestFragment extends Fragment {

    private DatabaseReference mechanicRef, MechanicRefDetails;
    String name, email, phoneNo, status, profilURL, type, cnic;
    NetworkManager manager = new NetworkManager();
    RecyclerView recyclerMechanicRequest;
    MechanicAccountAdapter MechanicAccountAdapter;
    List<Mechanic> mechanicList = new ArrayList<>();


    public MechanicRequestFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_request, container, false);

        mechanicRef = FirebaseDatabase.getInstance().getReference("Mechanic");
        recyclerMechanicRequest = view.findViewById(R.id.recyclerUserRequest);
        if (manager.checkInternertConnection(getActivity())) {
            loadMechanics();
        } else {
            Toast.makeText(getActivity(), "Check your internet connection!", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void loadMechanics() {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading please wait!");
        progressDialog.show();

        mechanicRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                mechanicList.clear();
                progressDialog.dismiss();


                for (DataSnapshot s : snapshot.getChildren()) {
                    String key = s.getKey().toString();

                    MechanicRefDetails = FirebaseDatabase.getInstance().getReference("Mechanic").child(key);
                    MechanicRefDetails.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            status = snapshot.child("status").getValue().toString();

                            if (status.equalsIgnoreCase("pending")) {

                                progressDialog.dismiss();

                                name = snapshot.child("name").getValue().toString();
                                email = snapshot.child("email").getValue().toString();
                                phoneNo = snapshot.child("phoneNo").getValue().toString();
                                cnic = snapshot.child("cnic").getValue().toString();
                                type = snapshot.child("type").getValue().toString();
                                profilURL = snapshot.child("profile_url").getValue().toString();

                                mechanicList.add(new Mechanic(key, name, email, phoneNo, cnic, profilURL, status, type));
                                MechanicAccountAdapter = new MechanicAccountAdapter(mechanicList, new IClickListener() {
                                    @Override
                                    public void onItemClicked(int position) {
                                        MechanicAccountDetailFragment mechanicAccountDetailFragment = new MechanicAccountDetailFragment();
                                        Bundle args = new Bundle();
                                        args.putString("key", mechanicList.get(position).getKey());
                                        args.putString("name", mechanicList.get(position).getName());
                                        args.putString("email", mechanicList.get(position).getEmail());
                                        args.putString("phoneNo", mechanicList.get(position).getPhoneNo());
                                        args.putString("cnic", mechanicList.get(position).getCnic());
                                        args.putString("type", mechanicList.get(position).getType());
                                        mechanicAccountDetailFragment.setArguments(args);
                                        getFragmentManager().beginTransaction()
                                                .replace(R.id.container, mechanicAccountDetailFragment)
                                                .commit();
                                    }
                                });
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                recyclerMechanicRequest.setHasFixedSize(true);
                                recyclerMechanicRequest.setLayoutManager(mLayoutManager);
                                recyclerMechanicRequest.setItemAnimator(new DefaultItemAnimator());
                                recyclerMechanicRequest.setAdapter(MechanicAccountAdapter);
                                MechanicAccountAdapter.notifyDataSetChanged();

                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
