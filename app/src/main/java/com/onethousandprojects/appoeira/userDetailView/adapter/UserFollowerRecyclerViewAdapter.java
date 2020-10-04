package com.onethousandprojects.appoeira.userDetailView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.userDetailView.content.UserFollowersContent;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserFollowerRecyclerViewAdapter extends RecyclerView.Adapter<UserFollowerRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<UserFollowersContent> mValues;
    private OnUserDetailListener myOnUserDetailListener;

    public UserFollowerRecyclerViewAdapter(Context context, List<UserFollowersContent> items, OnUserDetailListener onUserDetailListener) {
        mValues = items;
        ctx = context;
        this.myOnUserDetailListener = onUserDetailListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_user_detail_followers_item, parent, false);
        return new ViewHolder(view, myOnUserDetailListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if (!holder.mItem.getUserPicUrl().equals("")) {
            Picasso.with(ctx).load(holder.mItem.getUserPicUrl()).fit().into(holder.ivAvatar);
        }
        holder.tvName.setText(holder.mItem.getUserApelhido());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final ImageView ivAvatar;
        public final TextView tvName;
        public UserFollowersContent mItem;
        OnUserDetailListener onUserDetailListener;

        public ViewHolder(View view, OnUserDetailListener onUserDetailListener) {
            super(view);
            mView = view;
            ivAvatar = (ImageView) view.findViewById(R.id.userDetailAvatar);
            tvName = (TextView) view.findViewById(R.id.userDetailName);
            this.onUserDetailListener = onUserDetailListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onUserDetailListener.OnUserDetailClick(mItem.getUserId());
        }
    }
    // https://www.youtube.com/watch?v=69C1ljfDvl0
    public interface OnUserDetailListener {
        void OnUserDetailClick(int position);
    }


}