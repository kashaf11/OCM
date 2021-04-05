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
import com.example.kashaf.ocm.fyp.Activities.Adapters.UserAccountAdapter;
import com.example.kashaf.ocm.fyp.Activities.Models.Users;
import com.example.kashaf.ocm.fyp.Activities.network.NetworkManager;
import com.example.kashaf.ocm.fyp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BlockUserManagmentFragment extends Fragment {

    private DatabaseReference userRef, userRefDetails;
    String name, email, phoneNo, status, profilURL;
    NetworkManager manager = new NetworkManager();
    RecyclerView recyclerUserRequest;
    UserAccountAdapter userAccountAdapter;
    List<Users> usersList = new ArrayList<>();

    public BlockUserManagmentFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_request, container, false);

        userRef = FirebaseDatabase.getInstance().getReference("User");
        recyclerUserRequest = view.findViewById(R.id.recyclerUserRequest);
        if (manager.checkInternertConnection(getActivity())) {
            loadUsers();
        } else {
            Toast.makeText(getActivity(), "Check your internet connection!", Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    private void loadUsers() {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading please wait!");
        progressDialog.show();

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                progressDialog.dismiss();


                for (DataSnapshot s : snapshot.getChildren()) {
                    String key = s.getKey().toString();

                    usersList.clear();

                    userRefDetails = FirebaseDatabase.getInstance().getReference("User").child(key);
                    userRefDetails.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            status = snapshot.child("status").getValue().toString();

                            if (status.equalsIgnoreCase("blocked")
                                    || status.equalsIgnoreCase("accepted")
                                    || status.equalsIgnoreCase("unblocked")) {

                                progressDialog.dismiss();

                                name = snapshot.child("name").getValue().toString();
                                email = snapshot.child("email").getValue().toString();
                                phoneNo = snapshot.child("phoneNo").getValue().toString();
                                profilURL = snapshot.child("profile_url").getValue().toString();

                                usersList.add(new Users(key, name, email, phoneNo, profilURL, status));
                                userAccountAdapter = new UserAccountAdapter(usersList, new IClickListener() {
                                    @Override
                                    public void onItemClicked(int position) {

                                        UserBlockDetailFragment userBlockDetailFragment = new UserBlockDetailFragment();
                                        Bundle args = new Bundle();
                                        args.putString("key", usersList.get(position).getKey());
                                        args.putString("name", usersList.get(position).getName());
                                        args.putString("email", usersList.get(position).getEmail());
                                        args.putString("phoneNo", usersList.get(position).getPhoneNo());
                                        userBlockDetailFragment.setArguments(args);
                                        getFragmentManager().beginTransaction()
                                                .replace(R.id.container, userBlockDetailFragment)
                                                .commit();
                                    }
                                });
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                recyclerUserRequest.setHasFixedSize(true);
                                recyclerUserRequest.setLayoutManager(mLayoutManager);
                                recyclerUserRequest.setItemAnimator(new DefaultItemAnimator());
                                recyclerUserRequest.setAdapter(userAccountAdapter);
                                userAccountAdapter.notifyDataSetChanged();

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
