package com.onethousandprojects.appoeira.groupDetailMoreView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.groupDetailMoreView.content.GroupMembersContent;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyGroupMembersRecyclerViewAdapter extends RecyclerView.Adapter<MyGroupMembersRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<GroupMembersContent> mValues;
    private OnGroupDetailListener myOnGroupDetailListener;

    public MyGroupMembersRecyclerViewAdapter(Context context, List<GroupMembersContent> items, OnGroupDetailListener onGroupDetailListener) {
        mValues = items;
        ctx = context;
        this.myOnGroupDetailListener = onGroupDetailListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_group_detail_item, parent, false);
        return new ViewHolder(view, myOnGroupDetailListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Picasso.with(ctx).load(holder.mItem.getUserPicUrl()).fit().into(holder.ivUserAvatar);
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
        public GroupMembersContent mItem;
        OnGroupDetailListener onGroupDetailListener;

        public ViewHolder(View view, OnGroupDetailListener onGroupDetailListener) {
            super(view);
            mView = view;
            ivUserAvatar = (ImageView) view.findViewById(R.id.imageViewUserAvatar);
            ivUserPremium = (ImageView) view.findViewById(R.id.imageUserPremium);
            tvUserApelhido = (TextView) view.findViewById(R.id.userApelhido);
            tvUserRank = (TextView) view.findViewById(R.id.userRank);
            tvUSerPremium = (TextView) view.findViewById(R.id.userPremium);
            this.onGroupDetailListener = onGroupDetailListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onGroupDetailListener.OnGroupDetailClick(mItem.getUserId());
        }
    }
    // https://www.youtube.com/watch?v=69C1ljfDvl0
    public interface OnGroupDetailListener {
        void OnGroupDetailClick(int position);
    }


}