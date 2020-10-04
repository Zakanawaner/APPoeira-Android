package com.onethousandprojects.appoeira.eventListView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.eventListView.content.EventContent;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyEventListRecyclerViewAdapter extends RecyclerView.Adapter<MyEventListRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<EventContent> mValues;
    private OnEventListener myOnEventListener;

    public MyEventListRecyclerViewAdapter(Context context, List<EventContent> items, OnEventListener onEventListener) {
        mValues = items;
        ctx = context;
        this.myOnEventListener = onEventListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_event_item, parent, false);
        return new ViewHolder(view, myOnEventListener);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if (!holder.mItem.getPicUrl().equals("")) {
            Picasso.with(ctx).load(holder.mItem.getPicUrl()).fit().into(holder.ivEventAvatar);
        }
        if (holder.mItem.getName().length() > 25) {
            holder.tvEventName.setText(holder.mItem.getName().substring(0,24) + "...");
        } else {
            holder.tvEventName.setText(holder.mItem.getName());
        }
        holder.tvEventOwnerName.setText(holder.mItem.getOwnerApelhido());
        holder.tvEventOwnerRank.setText(holder.mItem.getOwnerRank());
        if (holder.mItem.getLatitude() != null) {
            holder.tvEventDistance.setText(String.valueOf(holder.mItem.getDistance()));
            holder.tvDistanceKm.setVisibility(View.VISIBLE);
        } else {
            String f =CommonMethods.fromPlatformIdToPlatformName(holder.mItem.getPlatform(), ctx);
            holder.tvEventDistance.setText(CommonMethods.fromPlatformIdToPlatformName(holder.mItem.getPlatform(), ctx));
            holder.tvDistanceKm.setText("");
        }
        holder.tvEventDate.setText(String.valueOf(holder.mItem.getDate()));
    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final View mView;
        public final ImageView ivEventAvatar;
        public final TextView tvEventName;
        public final TextView tvEventOwnerName;
        public final TextView tvEventOwnerRank;
        public final ImageView ivEventVerified;
        public final TextView tvEventDistance;
        public final TextView tvDistanceKm;
        public final TextView tvEventDate;
        public EventContent mItem;
        OnEventListener onEventListener;

        public ViewHolder(View view, OnEventListener onEventListener) {
            super(view);
            mView = view;
            ivEventAvatar = (ImageView) view.findViewById(R.id.imageViewAvatar);
            tvEventName = (TextView) view.findViewById(R.id.eventName);
            tvEventOwnerName = (TextView) view.findViewById(R.id.eventOwner);
            tvEventOwnerRank = (TextView) view.findViewById(R.id.eventOwnerRank);
            ivEventVerified = (ImageView) view.findViewById(R.id.checkBoxVerified);
            tvEventDistance = (TextView) view.findViewById(R.id.eventDistance);
            tvDistanceKm = (TextView) view.findViewById(R.id.textViewKm) ;
            tvEventDate = (TextView) view.findViewById(R.id.eventDate);
            this.onEventListener = onEventListener;
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            onEventListener.OnEventClick(getAdapterPosition());
        }
    }
    public interface OnEventListener {
        void OnEventClick(int position);
    }
}