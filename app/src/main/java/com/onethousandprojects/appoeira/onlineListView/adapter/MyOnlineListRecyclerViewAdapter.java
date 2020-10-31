package com.onethousandprojects.appoeira.onlineListView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.onlineListView.content.OnlineContent;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyOnlineListRecyclerViewAdapter extends RecyclerView.Adapter<MyOnlineListRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<OnlineContent> mValues;
    private OnOnlineListener myOnOnlineListener;

    public MyOnlineListRecyclerViewAdapter(Context context, List<OnlineContent> items, OnOnlineListener onOnlineListener) {
        mValues = items;
        ctx = context;
        this.myOnOnlineListener = onOnlineListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_event_item, parent, false);
        return new ViewHolder(view, myOnOnlineListener);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if (!holder.mItem.getPicUrl().equals("")) {
            Picasso.with(ctx).load(holder.mItem.getPicUrl()).fit().into(holder.ivOnlineAvatar);
        }
        if (holder.mItem.getName().length() > 25) {
            holder.tvOnlineName.setText(holder.mItem.getName().substring(0,24) + "...");
        } else {
            holder.tvOnlineName.setText(holder.mItem.getName());
        }
        holder.tvOnlineOwnerName.setText(holder.mItem.getOwnerApelhido());
        holder.tvOnlineOwnerRank.setText(holder.mItem.getOwnerRank());
        holder.tvOnlineDistance.setText(CommonMethods.fromPlatformIdToPlatformName(holder.mItem.getPlatform(), ctx));
        holder.tvDistanceKm.setText("");
        if (holder.mItem.getVerified()) {
            holder.ivOnlineVerified.setVisibility(View.VISIBLE);
            holder.ivOnlineVerified.setImageResource(R.drawable.verified);
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
            holder.ivOnlineVerified.setVisibility(View.GONE);
        }
        holder.tvOnlineDate.setText(String.valueOf(holder.mItem.getDate()));
    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final View mView;
        public final ImageView ivOnlineAvatar;
        public final TextView tvOnlineName;
        public final TextView tvOnlineOwnerName;
        public final TextView tvOnlineOwnerRank;
        public final ImageView ivOnlineVerified;
        public final TextView tvOnlineDistance;
        public final TextView tvDistanceKm;
        public final TextView tvOnlineDate;
        public ImageView ivGroupStar1;
        public ImageView ivGroupStar2;
        public ImageView ivGroupStar3;
        public ImageView ivGroupStar4;
        public ImageView ivGroupStar5;
        public OnlineContent mItem;
        OnOnlineListener onOnlineListener;

        public ViewHolder(View view, OnOnlineListener onOnlineListener) {
            super(view);
            mView = view;
            ivOnlineAvatar = (ImageView) view.findViewById(R.id.imageViewAvatar);
            tvOnlineName = (TextView) view.findViewById(R.id.eventName);
            tvOnlineOwnerName = (TextView) view.findViewById(R.id.eventOwner);
            tvOnlineOwnerRank = (TextView) view.findViewById(R.id.eventOwnerRank);
            ivOnlineVerified = (ImageView) view.findViewById(R.id.checkBoxVerified);
            tvOnlineDistance = (TextView) view.findViewById(R.id.eventDistance);
            tvDistanceKm = (TextView) view.findViewById(R.id.textViewKm);
            ivGroupStar1 = (ImageView) view.findViewById(R.id.groupStar1);
            ivGroupStar2 = (ImageView) view.findViewById(R.id.groupStar2);
            ivGroupStar3 = (ImageView) view.findViewById(R.id.groupStar3);
            ivGroupStar4 = (ImageView) view.findViewById(R.id.groupStar4);
            ivGroupStar5 = (ImageView) view.findViewById(R.id.groupStar5);
            tvOnlineDate = (TextView) view.findViewById(R.id.eventDate);
            this.onOnlineListener = onOnlineListener;
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            onOnlineListener.OnOnlineClick(getAdapterPosition());
        }
    }
    public interface OnOnlineListener {
        void OnOnlineClick(int position);
    }
}