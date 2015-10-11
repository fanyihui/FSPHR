package com.fansen.phr.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fansen.phr.R;

import java.util.List;


/**
 * Created by Yihui Fan on 2015/10/11.
 */
public class ResultListAdapter extends RecyclerView.Adapter<ResultListAdapter.ResultListViewHolder>{



    @Override
    public void onBindViewHolder(ResultListViewHolder holder, int position) {

    }

    @Override
    public ResultListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ResultListViewHolder extends RecyclerView.ViewHolder{
        private TextView title = null;
        private ImageView resultTypeIcon = null;

        public ResultListViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.ent_date);
        }
    }
}
