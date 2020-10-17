package com.onethousandprojects.appoeira.rodaListView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.rodaListView.content.RodaContent;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyRodaListRecyclerViewAdapter extends RecyclerView.Adapter<MyRodaListRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<RodaContent> mValues;
    private OnRodaListener myOnRodaListener;

    public MyRodaListRecyclerViewAdapter(Context context, List<RodaContent> items, OnRodaListener onRodaListener) {
        mValues = items;
        ctx = context;
        this.myOnRodaListener = onRodaListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_roda_item, parent, false);
        return new ViewHolder(view, myOnRodaListener);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if (!holder.mItem.getPicUrl().equals("")) {
            Picasso.with(ctx).load(holder.mItem.getPicUrl()).fit().into(holder.ivRodaAvatar);
        }
        if (holder.mItem.getName().length() > 25) {
            String name = holder.mItem.getName().substring(0,24) + "...";
            holder.tvRodaName.setText(name);
        } else {
            holder.tvRodaName.setText(holder.mItem.getName());
        }
        if (holder.mItem.getVerified()) {
            holder.ivRodaVerified.setVisibility(View.VISIBLE);
            holder.ivRodaVerified.setImageResource(R.drawable.verified);
            List<ImageView> stars = CommonMethods.SetStars(holder.mItem.getRating(), holder.ivGroupStar1, holder.ivGroupStar2, holder.ivGroupStar3, holder.ivGroupStar4, holder.ivGroupStar5);
            holder.ivGroupStar1 = stars.get(0);
            holder.ivGroupStar2 = stars.get(1);
            holder.ivGroupStar3 = stars.get(2);
            holder.ivGroupStar4 = stars.get(3);
            holder.ivGroupStar5 = stars.get(4);
        } else {
            holder.ivGroupStar1.setVisibility(View.GONE);
            holder.ivGroupStar2.setVisibility(View.GONE);
            holder.ivGroupStar3.setVisibility(View.GONE);
            holder.ivGroupStar4.setVisibility(View.GONE);
            holder.ivGroupStar5.setVisibility(View.GONE);
            holder.ivRodaVerified.setVisibility(View.GONE);
        }
        holder.tvRodaOwnerName.setText(holder.mItem.getOwnerApelhido());
        holder.tvRodaOwnerRank.setText(holder.mItem.getOwnerRank());
        holder.tvRodaDistance.setText(String.valueOf(holder.mItem.getDistance()));
        holder.tvRodaDate.setText(String.valueOf(holder.mItem.getDate()));
    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final View mView;
        public final ImageView ivRodaAvatar;
        public final TextView tvRodaName;
        public final TextView tvRodaOwnerName;
        public final TextView tvRodaOwnerRank;
        public final ImageView ivRodaVerified;
        public final TextView tvRodaDistance;
        public final TextView tvRodaDate;
        public ImageView ivGroupStar1;
        public ImageView ivGroupStar2;
        public ImageView ivGroupStar3;
        public ImageView ivGroupStar4;
        public ImageView ivGroupStar5;
        public RodaContent mItem;
        OnRodaListener onRodaListener;

        public ViewHolder(View view, OnRodaListener onRodaListener) {
            super(view);
            mView = view;
            ivRodaAvatar = (ImageView) view.findViewById(R.id.imageViewAvatar);
            tvRodaName = (TextView) view.findViewById(R.id.rodaName);
            tvRodaOwnerName = (TextView) view.findViewById(R.id.rodaOwner);
            tvRodaOwnerRank = (TextView) view.findViewById(R.id.rodaOwnerRank);
            ivRodaVerified = (ImageView) view.findViewById(R.id.checkBoxVerified);
            tvRodaDistance = (TextView) view.findViewById(R.id.rodaDistance);
            tvRodaDate = (TextView) view.findViewById(R.id.rodaDate);
            ivGroupStar1 = (ImageView) view.findViewById(R.id.groupStar1);
            ivGroupStar2 = (ImageView) view.findViewById(R.id.groupStar2);
            ivGroupStar3 = (ImageView) view.findViewById(R.id.groupStar3);
            ivGroupStar4 = (ImageView) view.findViewById(R.id.groupStar4);
            ivGroupStar5 = (ImageView) view.findViewById(R.id.groupStar5);
            this.onRodaListener = onRodaListener;
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            onRodaListener.OnRodaClick(getAdapterPosition());
        }
    }
    public interface OnRodaListener {
        void OnRodaClick(int position);
    }
}