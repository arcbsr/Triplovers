package com.arcadio.triplover.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BasicAdapter extends RecyclerView.Adapter<BasicAdapter.ViewHolder> {
    private BasicListener listener;

    public interface BasicListener {
        int getItem();

        int getLayoutId();

        void onBindViewHolder(BasicAdapter.ViewHolder holder, int position);
    }

    // creating a constructor for our variables.
    public BasicAdapter(BasicListener adapterListener) {
        this.listener = adapterListener;
    }


    @NonNull
    @Override
    public BasicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(listener.getLayoutId(), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BasicAdapter.ViewHolder holder, int position) {
        listener.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return listener.getItem();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
