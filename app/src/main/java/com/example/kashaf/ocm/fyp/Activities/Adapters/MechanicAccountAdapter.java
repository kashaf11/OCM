package com.example.kashaf.ocm.fyp.Activities.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kashaf.ocm.fyp.Activities.Models.Mechanic;
import com.example.kashaf.ocm.fyp.Activities.Models.Users;
import com.example.kashaf.ocm.fyp.R;

import java.util.List;

public class MechanicAccountAdapter extends RecyclerView.Adapter<MechanicAccountAdapter.MechanicAccountRequestViewHolder> {

    private List<Mechanic> mechanicList;
    private IClickListener listener;


    public MechanicAccountAdapter(List<Mechanic> mechanicList, IClickListener listener) {
        this.mechanicList = mechanicList;
        this.listener = listener;
    }

    @Override
    public MechanicAccountRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_account_mechanic_request, parent, false);

        return new MechanicAccountRequestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MechanicAccountRequestViewHolder holder, int position) {
        Mechanic mechanic = mechanicList.get(position);
        holder.name.setText(mechanic.getName());
        holder.email.setText(mechanic.getEmail());
        holder.phoneNo.setText(mechanic.getPhoneNo() + "");
        holder.cnic.setText(mechanic.getCnic() + "");
        holder.type.setText(mechanic.getType());
    }


    public class MechanicAccountRequestViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {
        TextView name, email, phoneNo, cnic, type;
        Button btnViewDetails;

        public MechanicAccountRequestViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tvMechanicName);
            email = view.findViewById(R.id.tvMechanicEmail);
            phoneNo = view.findViewById(R.id.tvMechanicPhoneNo);
            cnic = view.findViewById(R.id.tvMechanicCNIC);
            type = view.findViewById(R.id.tvMechanicType);
            btnViewDetails = view.findViewById(R.id.btnViewDetails);
            btnViewDetails.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btnViewDetails:
                    listener.onItemClicked(getAdapterPosition());
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mechanicList.size();
    }
}
