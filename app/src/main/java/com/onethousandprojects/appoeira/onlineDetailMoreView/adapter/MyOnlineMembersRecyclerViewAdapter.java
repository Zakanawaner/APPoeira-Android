package com.onethousandprojects.appoeira.onlineDetailMoreView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.onlineDetailMoreView.content.OnlineMembersContent;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyOnlineMembersRecyclerViewAdapter extends RecyclerView.Adapter<MyOnlineMembersRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<OnlineMembersContent> mValues;
    private OnOnlineDetailListener myOnOnlineDetailListener;

    public MyOnlineMembersRecyclerViewAdapter(Context context, List<OnlineMembersContent> items, OnOnlineDetailListener onOnlineDetailListener) {
        mValues = items;
        ctx = context;
        this.myOnOnlineDetailListener = onOnlineDetailListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_group_detail_item, parent, false);
        return new ViewHolder(view, myOnOnlineDetailListener);
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
        public OnlineMembersContent mItem;
        OnOnlineDetailListener onOnlineDetailListener;

        public ViewHolder(View view, OnOnlineDetailListener onOnlineDetailListener) {
            super(view);
            mView = view;
            ivUserAvatar = (ImageView) view.findViewById(R.id.imageViewUserAvatar);
            ivUserPremium = (ImageView) view.findViewById(R.id.imageUserPremium);
            tvUserApelhido = (TextView) view.findViewById(R.id.userApelhido);
            tvUserRank = (TextView) view.findViewById(R.id.userRank);
            tvUSerPremium = (TextView) view.findViewById(R.id.userPremium);
            this.onOnlineDetailListener = onOnlineDetailListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onOnlineDetailListener.OnOnlineDetailClick(mItem.getUserId());
        }
    }
    // https://www.youtube.com/watch?v=69C1ljfDvl0
    public interface OnOnlineDetailListener {
        void OnOnlineDetailClick(int position);
    }


}