package com.fansen.phr.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.fansen.phr.entity.RequestedProcedure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yihui Fan on 2015/11/15.
 */
public class RequestedProcedureListAdapter extends RecyclerView.Adapter<RequestedProcedureListAdapter.RequestedProcedureListViewHolder>{
    private List<RequestedProcedure> requestedProcedureList;
    private RequestedProcedureItemClickListener requestedProcedureItemClickListener;

    public RequestedProcedureListAdapter(List<RequestedProcedure> requestedProcedureList) {
        if (requestedProcedureList == null){
            this.requestedProcedureList = new ArrayList<>();
        } else{
            this.requestedProcedureList = requestedProcedureList;
        }
    }

    public void addRequestedProcedure(RequestedProcedure requestedProcedure){
        requestedProcedureList.add(requestedProcedure);
        notifyDataSetChanged();
    }

    public void updateRequestedProcedure(int position, RequestedProcedure requestedProcedure){
        if (0 <= position && position <= requestedProcedureList.size()) {
            requestedProcedureList.set(position, requestedProcedure);
            notifyDataSetChanged();
        }
    }

    public void deleteRequestedProcedure(int position){
        if (0 <= position && position <= requestedProcedureList.size()) {
            requestedProcedureList.remove(position);
            notifyDataSetChanged();
        }
    }

    public void setRequestedProcedureList(List<RequestedProcedure> requestedProcedureList) {
        this.requestedProcedureList = requestedProcedureList;
        notifyDataSetChanged();
    }

    public void setRequestedProcedureItemClickListener(RequestedProcedureItemClickListener requestedProcedureItemClickListener) {
        this.requestedProcedureItemClickListener = requestedProcedureItemClickListener;
    }

    @Override
    public RequestedProcedureListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RequestedProcedureListViewHolder holder, int position) {
        //set the value to view elements
    }

    @Override
    public int getItemCount() {
        return requestedProcedureList.size();
    }

    public class RequestedProcedureListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //All view elements are defined here

        public RequestedProcedureListViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {
            if (requestedProcedureItemClickListener != null){
                requestedProcedureItemClickListener.itemClicked(v, getAdapterPosition());
            }
        }
    }


    public interface RequestedProcedureItemClickListener{
        public void itemClicked(View v, int position);
    }
}
