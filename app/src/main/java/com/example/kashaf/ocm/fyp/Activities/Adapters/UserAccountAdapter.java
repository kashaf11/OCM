package com.example.kashaf.ocm.fyp.Activities.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kashaf.ocm.fyp.Activities.Models.Users;
import com.example.kashaf.ocm.fyp.R;

import java.util.List;

public class UserAccountAdapter extends RecyclerView.Adapter<UserAccountAdapter.UserAccountRequestViewHolder> {

    private List<Users> usersList;
    private IClickListener listener;


    public UserAccountAdapter(List<Users> usersList, IClickListener listener) {
        this.usersList = usersList;
        this.listener = listener;
    }

    @Override
    public UserAccountRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_account_requrest, parent, false);

        return new UserAccountRequestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserAccountRequestViewHolder holder, int position) {
        Users users = usersList.get(position);
        holder.name.setText(users.getName());
        holder.email.setText(users.getEmail());
        holder.phoneNo.setText(users.getPhoneNo() + "");

    }

    public class UserAccountRequestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name, email, phoneNo;
        Button btnViewDetails;

        public UserAccountRequestViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tvUserName);
            email = view.findViewById(R.id.tvUserEmail);
            phoneNo = view.findViewById(R.id.tvUserPhoneNo);
            btnViewDetails = view.findViewById(R.id.btnViewDetails);
            btnViewDetails.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.btnViewDetails:
                    listener.onItemClicked(getAdapterPosition());
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }
}
