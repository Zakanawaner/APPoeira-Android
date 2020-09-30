package com.onethousandprojects.appoeira.rodaListView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.rodaListView.content.RodaContent;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyRodaListRecyclerViewAdapter extends RecyclerView.Adapter<MyRodaListRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<RodaContent> mValues;
    private OnRodaListener myOnRodaListener;

    public MyRodaListRecyclerViewAdapter(Context context, List<RodaContent> items, OnRodaListener onRodaListener) {
        mValues = items;
        ctx = context;
        this.myOnRodaListener = onRodaListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_roda_item, parent, false);
        return new ViewHolder(view, myOnRodaListener);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if (!holder.mItem.getPicUrl().equals("")) {
            Picasso.with(ctx).load(holder.mItem.getPicUrl()).fit().into(holder.ivRodaAvatar);
        }
        if (holder.mItem.getName().length() > 25) {
            holder.tvRodaName.setText(holder.mItem.getName().substring(0,24) + "...");
        } else {
            holder.tvRodaName.setText(holder.mItem.getName());
        }
        String hola = holder.mItem.getDate();
        holder.tvRodaOwnerName.setText(holder.mItem.getOwnerApelhido());
        holder.tvRodaOwnerRank.setText(holder.mItem.getOwnerRank());
        holder.tvRodaDistance.setText(String.valueOf(holder.mItem.getDistance()));
        holder.tvRodaDate.setText(String.valueOf(holder.mItem.getDate()));
    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final View mView;
        public final ImageView ivRodaAvatar;
        public final TextView tvRodaName;
        public final TextView tvRodaOwnerName;
        public final TextView tvRodaOwnerRank;
        public final ImageView ivRodaVerified;
        public final TextView tvRodaDistance;
        public final TextView tvRodaDate;
        public RodaContent mItem;
        OnRodaListener onRodaListener;

        public ViewHolder(View view, OnRodaListener onRodaListener) {
            super(view);
            mView = view;
            ivRodaAvatar = (ImageView) view.findViewById(R.id.imageViewAvatar);
            tvRodaName = (TextView) view.findViewById(R.id.rodaName);
            tvRodaOwnerName = (TextView) view.findViewById(R.id.rodaOwner);
            tvRodaOwnerRank = (TextView) view.findViewById(R.id.rodaOwnerRank);
            ivRodaVerified = (ImageView) view.findViewById(R.id.checkBoxVerified);
            tvRodaDistance = (TextView) view.findViewById(R.id.rodaDistance);
            tvRodaDate = (TextView) view.findViewById(R.id.rodaDate);
            this.onRodaListener = onRodaListener;
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            onRodaListener.OnRodaClick(getAdapterPosition());
        }
    }
    public interface OnRodaListener {
        void OnRodaClick(int position);
    }
}