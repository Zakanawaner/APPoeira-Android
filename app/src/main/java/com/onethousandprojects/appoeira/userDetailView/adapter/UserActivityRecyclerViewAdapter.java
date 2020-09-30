package com.onethousandprojects.appoeira.userDetailView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.userDetailView.content.UserActivityContent;
import com.onethousandprojects.appoeira.userDetailView.content.UserEventContent;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserActivityRecyclerViewAdapter extends RecyclerView.Adapter<UserActivityRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<UserActivityContent> mValues;
    private OnActivityDetailListener myOnActivityDetailListener;

    public UserActivityRecyclerViewAdapter(Context context, List<UserActivityContent> items, OnActivityDetailListener onActivityDetailListener) {
        mValues = items;
        ctx = context;
        this.myOnActivityDetailListener = onActivityDetailListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_user_detail_item, parent, false);
        return new ViewHolder(view, myOnActivityDetailListener);
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
        holder.tvDate.setText(holder.mItem.getDate());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final ImageView ivAvatar;
        public final TextView tvName;
        public final TextView tvDate;
        public UserActivityContent mItem;
        OnActivityDetailListener onActivityDetailListener;

        public ViewHolder(View view, OnActivityDetailListener onActivityDetailListener) {
            super(view);
            mView = view;
            ivAvatar = view.findViewById(R.id.userDetailAvatar);
            tvName = view.findViewById(R.id.userDetailName);
            tvDate = view.findViewById(R.id.userDetailRank);
            this.onActivityDetailListener = onActivityDetailListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onActivityDetailListener.OnActivityDetailClick(mItem.getId());
        }
    }
    // https://www.youtube.com/watch?v=69C1ljfDvl0
    public interface OnActivityDetailListener {
        void OnActivityDetailClick(int position);
    }


}