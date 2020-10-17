package com.onethousandprojects.appoeira.eventDetailMoreView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.eventDetailMoreView.content.EventMembersContent;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyEventMembersRecyclerViewAdapter extends RecyclerView.Adapter<MyEventMembersRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<EventMembersContent> mValues;
    private OnEventDetailListener myOnEventDetailListener;

    public MyEventMembersRecyclerViewAdapter(Context context, List<EventMembersContent> items, OnEventDetailListener onEventDetailListener) {
        mValues = items;
        ctx = context;
        this.myOnEventDetailListener = onEventDetailListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_group_detail_item, parent, false);
        return new ViewHolder(view, myOnEventDetailListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if (!holder.mItem.getUserPicUrl().equals("")) {
            Picasso.with(ctx).load(holder.mItem.getUserPicUrl()).fit().into(holder.ivUserAvatar);
        }
        holder.tvUserApelhido.setText(holder.mItem.getUserApelhido());
        holder.tvUserRank.setText(holder.mItem.getUserRank());
        holder.tvUSerPremium.setText(Constants.IS_PREMIUM);

        if (holder.mItem.isUserPremium()) {
            holder.tvUSerPremium.setVisibility(View.VISIBLE);
            holder.ivUserPremium.setVisibility(View.VISIBLE);
        } else {
            holder.tvUSerPremium.setVisibility(View.GONE);
            holder.ivUserPremium.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final ImageView ivUserAvatar;
        public final ImageView ivUserPremium;
        public final TextView tvUserApelhido;
        public final TextView tvUserRank;
        public final TextView tvUSerPremium;
        public EventMembersContent mItem;
        OnEventDetailListener onEventDetailListener;

        public ViewHolder(View view, OnEventDetailListener onEventDetailListener) {
            super(view);
            mView = view;
            ivUserAvatar = (ImageView) view.findViewById(R.id.imageViewUserAvatar);
            ivUserPremium = (ImageView) view.findViewById(R.id.imageUserPremium);
            tvUserApelhido = (TextView) view.findViewById(R.id.userApelhido);
            tvUserRank = (TextView) view.findViewById(R.id.userRank);
            tvUSerPremium = (TextView) view.findViewById(R.id.userPremium);
            this.onEventDetailListener = onEventDetailListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onEventDetailListener.OnEventDetailClick(mItem.getUserId());
        }
    }
    // https://www.youtube.com/watch?v=69C1ljfDvl0
    public interface OnEventDetailListener {
        void OnEventDetailClick(int position);
    }


}