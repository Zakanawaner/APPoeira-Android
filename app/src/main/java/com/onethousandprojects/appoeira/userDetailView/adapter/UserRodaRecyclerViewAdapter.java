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
import com.onethousandprojects.appoeira.userDetailView.content.UserRodaContent;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserRodaRecyclerViewAdapter extends RecyclerView.Adapter<UserRodaRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<UserRodaContent> mValues;
    private OnRodaDetailListener myOnRodaDetailListener;

    public UserRodaRecyclerViewAdapter(Context context, List<UserRodaContent> items, OnRodaDetailListener onRodaDetailListener) {
        mValues = items;
        ctx = context;
        this.myOnRodaDetailListener = onRodaDetailListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_user_detail_item, parent, false);
        return new ViewHolder(view, myOnRodaDetailListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if (!holder.mItem.getPicUrl().equals("")) {
            Picasso.with(ctx).load(holder.mItem.getPicUrl()).fit().into(holder.ivAvatar);
        }
        if (holder.mItem.getName().length() > 36) {
            String name = holder.mItem.getName().substring(0, 35) + "...";
            holder.tvName.setText(name);
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
        public UserRodaContent mItem;
        OnRodaDetailListener onRodaDetailListener;

        public ViewHolder(View view, OnRodaDetailListener onRodaDetailListener) {
            super(view);
            mView = view;
            ivAvatar = (ImageView) view.findViewById(R.id.userDetailAvatar);
            tvName = (TextView) view.findViewById(R.id.userDetailName);
            tvUserRank = (TextView) view.findViewById(R.id.userDetailRank);
            this.onRodaDetailListener = onRodaDetailListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onRodaDetailListener.OnRodaDetailClick(mItem.getId());
        }
    }
    // https://www.youtube.com/watch?v=69C1ljfDvl0
    public interface OnRodaDetailListener {
        void OnRodaDetailClick(int position);
    }


}