package com.onethousandprojects.appoeira.userDetailView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.userDetailView.content.UserGroupContent;
import com.onethousandprojects.appoeira.userDetailView.content.UserOnlineContent;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserOnlineRecyclerViewAdapter extends RecyclerView.Adapter<UserOnlineRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<UserOnlineContent> mValues;
    private OnOnlineDetailListener myOnOnlineDetailListener;

    public UserOnlineRecyclerViewAdapter(Context context, List<UserOnlineContent> items, OnOnlineDetailListener onOnlineDetailListener) {
        mValues = items;
        ctx = context;
        this.myOnOnlineDetailListener = onOnlineDetailListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_user_detail_item, parent, false);
        return new ViewHolder(view, myOnOnlineDetailListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if (!holder.mItem.getPicUrl().equals("")) {
            Picasso.with(ctx).load(holder.mItem.getPicUrl()).fit().into(holder.ivAvatar);
        }
        if (holder.mItem.getName().length() > 36) {
            holder.tvName.setText(holder.mItem.getName().substring(0, 35) + "...");
        } else {
            holder.tvName.setText(holder.mItem.getName());
        }
        holder.tvUserRank.setText(holder.mItem.getRole());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final ImageView ivAvatar;
        public final TextView tvName;
        public final TextView tvUserRank;
        public UserOnlineContent mItem;
        OnOnlineDetailListener onOnlineDetailListener;

        public ViewHolder(View view, OnOnlineDetailListener onOnlineDetailListener) {
            super(view);
            mView = view;
            ivAvatar = (ImageView) view.findViewById(R.id.userDetailAvatar);
            tvName = (TextView) view.findViewById(R.id.userDetailName);
            tvUserRank = (TextView) view.findViewById(R.id.userDetailRank);
            this.onOnlineDetailListener = onOnlineDetailListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onOnlineDetailListener.OnOnlineDetailClick(mItem.getId());
        }
    }
    // https://www.youtube.com/watch?v=69C1ljfDvl0
    public interface OnOnlineDetailListener {
        void OnOnlineDetailClick(int position);
    }


}