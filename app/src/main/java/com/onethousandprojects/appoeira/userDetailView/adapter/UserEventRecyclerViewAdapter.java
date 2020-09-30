package com.onethousandprojects.appoeira.userDetailView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.userDetailView.content.UserEventContent;
import com.onethousandprojects.appoeira.userDetailView.content.UserGroupContent;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserEventRecyclerViewAdapter extends RecyclerView.Adapter<UserEventRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<UserEventContent> mValues;
    private OnEventDetailListener myOnEventDetailListener;

    public UserEventRecyclerViewAdapter(Context context, List<UserEventContent> items, OnEventDetailListener onEventDetailListener) {
        mValues = items;
        ctx = context;
        this.myOnEventDetailListener = onEventDetailListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_user_detail_item, parent, false);
        return new ViewHolder(view, myOnEventDetailListener);
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
        public UserEventContent mItem;
        OnEventDetailListener onEventDetailListener;

        public ViewHolder(View view, OnEventDetailListener onEventDetailListener) {
            super(view);
            mView = view;
            ivAvatar = (ImageView) view.findViewById(R.id.userDetailAvatar);
            tvName = (TextView) view.findViewById(R.id.userDetailName);
            tvUserRank = (TextView) view.findViewById(R.id.userDetailRank);
            this.onEventDetailListener = onEventDetailListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onEventDetailListener.OnEventDetailClick(mItem.getId());
        }
    }
    // https://www.youtube.com/watch?v=69C1ljfDvl0
    public interface OnEventDetailListener {
        void OnEventDetailClick(int position);
    }


}