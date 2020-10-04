package com.onethousandprojects.appoeira.rodaDetailMoreView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.rodaDetailMoreView.content.RodaMembersContent;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyRodaMembersRecyclerViewAdapter extends RecyclerView.Adapter<MyRodaMembersRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<RodaMembersContent> mValues;
    private OnRodaDetailListener myOnRodaDetailListener;

    public MyRodaMembersRecyclerViewAdapter(Context context, List<RodaMembersContent> items, OnRodaDetailListener onRodaDetailListener) {
        mValues = items;
        ctx = context;
        this.myOnRodaDetailListener = onRodaDetailListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_group_detail_item, parent, false);
        return new ViewHolder(view, myOnRodaDetailListener);
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
        public RodaMembersContent mItem;
        OnRodaDetailListener onRodaDetailListener;

        public ViewHolder(View view, OnRodaDetailListener onRodaDetailListener) {
            super(view);
            mView = view;
            ivUserAvatar = (ImageView) view.findViewById(R.id.imageViewUserAvatar);
            ivUserPremium = (ImageView) view.findViewById(R.id.imageUserPremium);
            tvUserApelhido = (TextView) view.findViewById(R.id.userApelhido);
            tvUserRank = (TextView) view.findViewById(R.id.userRank);
            tvUSerPremium = (TextView) view.findViewById(R.id.userPremium);
            this.onRodaDetailListener = onRodaDetailListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onRodaDetailListener.OnRodaDetailClick(mItem.getUserId());
        }
    }
    // https://www.youtube.com/watch?v=69C1ljfDvl0
    public interface OnRodaDetailListener {
        void OnRodaDetailClick(int position);
    }


}