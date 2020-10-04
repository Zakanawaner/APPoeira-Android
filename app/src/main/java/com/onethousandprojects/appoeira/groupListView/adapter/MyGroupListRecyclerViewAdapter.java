package com.onethousandprojects.appoeira.groupListView.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.groupListView.content.GroupContent;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyGroupListRecyclerViewAdapter extends RecyclerView.Adapter<MyGroupListRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<GroupContent> mValues;
    private OnGroupListener myOnGroupListener;

    public MyGroupListRecyclerViewAdapter(Context context, List<GroupContent> items, OnGroupListener onGroupListener) {
        mValues = items;
        ctx = context;
        this.myOnGroupListener = onGroupListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_group_item, parent, false);
        return new ViewHolder(view, myOnGroupListener);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Picasso.with(ctx).load(holder.mItem.getPicUrl()).fit().into(holder.ivGroupAvatar);
        if (holder.mItem.getName().length() > 25) {
            String name = holder.mItem.getName().substring(0,24) + "...";
            holder.tvGroupName.setText(name);
        } else {
            holder.tvGroupName.setText(holder.mItem.getName());
        }
        holder.tvGroupPhone.setText(holder.mItem.getPhone());
        if (holder.mItem.getVerified()) {
            holder.ivGroupVerified.setImageResource(R.drawable.verified);
            List<ImageView> stars = CommonMethods.SetStars(holder.mItem.getRating(), holder.ivGroupStar1, holder.ivGroupStar2, holder.ivGroupStar3, holder.ivGroupStar4, holder.ivGroupStar5);
            holder.ivGroupStar1 = stars.get(0);
            holder.ivGroupStar2 = stars.get(1);
            holder.ivGroupStar3 = stars.get(2);
            holder.ivGroupStar4 = stars.get(3);
            holder.ivGroupStar5 = stars.get(4);
        }
        holder.tvGroupDistance.setText(String.valueOf(holder.mItem.getDistance()));
    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final View mView;
        public final ImageView ivGroupAvatar;
        public final TextView tvGroupName;
        public final TextView tvGroupPhone;
        public final ImageView ivGroupVerified;
        public final TextView tvGroupDistance;
        public ImageView ivGroupStar1;
        public ImageView ivGroupStar2;
        public ImageView ivGroupStar3;
        public ImageView ivGroupStar4;
        public ImageView ivGroupStar5;
        public GroupContent mItem;
        OnGroupListener onGroupListener;

        public ViewHolder(View view, OnGroupListener onGroupListener) {
            super(view);
            mView = view;
            ivGroupAvatar = (ImageView) view.findViewById(R.id.imageViewAvatar);
            tvGroupName = (TextView) view.findViewById(R.id.groupName);
            tvGroupPhone = (TextView) view.findViewById(R.id.groupPhone);
            ivGroupVerified = (ImageView) view.findViewById(R.id.checkBoxVerified);
            tvGroupDistance = (TextView) view.findViewById(R.id.groupDistance);
            ivGroupStar1 = (ImageView) view.findViewById(R.id.groupStar1);
            ivGroupStar2 = (ImageView) view.findViewById(R.id.groupStar2);
            ivGroupStar3 = (ImageView) view.findViewById(R.id.groupStar3);
            ivGroupStar4 = (ImageView) view.findViewById(R.id.groupStar4);
            ivGroupStar5 = (ImageView) view.findViewById(R.id.groupStar5);
            this.onGroupListener = onGroupListener;
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            onGroupListener.OnGroupClick(getAdapterPosition());
        }
    }
    public interface OnGroupListener {
        void OnGroupClick(int position);
    }
}