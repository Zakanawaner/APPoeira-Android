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
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.userDetailView.UserDetailActivity;
import com.onethousandprojects.appoeira.userDetailView.content.UserActivityContent;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserActivityRecyclerViewAdapter extends RecyclerView.Adapter<UserActivityRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<UserActivityContent> mValues;
    private OnActivityDetailListener myOnActivityDetailListener;
    private UserDetailActivity userDetailActivity;

    public UserActivityRecyclerViewAdapter(Context context, List<UserActivityContent> items, OnActivityDetailListener onActivityDetailListener, UserDetailActivity UserDetailActivity) {
        mValues = items;
        ctx = context;
        this.myOnActivityDetailListener = onActivityDetailListener;
        this.userDetailActivity = UserDetailActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_user_activity_item, parent, false);
        return new ViewHolder(view, myOnActivityDetailListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if (!SharedPreferencesManager.getStringValue(Constants.PIC_URL).equals("")) {
            Picasso.with(ctx).load(userDetailActivity.myResponse.getPicUrl()).fit().into(holder.ivAvatar);
        }
        if (!holder.mItem.getPicUrl().equals("")) {
            Picasso.with(ctx).load(holder.mItem.getPicUrl()).fit().into(holder.ivObjectAvatar);
        }
        String name = userDetailActivity.myResponse.getRank() + " " + userDetailActivity.myResponse.getApelhido();
        holder.tvName.setText(name);
        if (holder.mItem.getName().length() > 36) {
            String objectName = holder.mItem.getName().substring(0, 35) + "...";
            holder.tvObjectName.setText(objectName);
        } else {
            holder.tvObjectName.setText(holder.mItem.getName());
        }
        if (!holder.mItem.getDate().equals("")) {
            holder.tvDate.setText(holder.mItem.getDate());
        } else {
            holder.tvDate.setVisibility(View.GONE);
        }
        switch (holder.mItem.getType()) {
            case 1:
                holder.tvInfo.setText(R.string.activityWasFollowed);
                break;
            case 2:
                holder.tvInfo.setText(R.string.activityFollowed);
                break;
            case 3:
                holder.tvInfo.setText(R.string.activityJoinedGroup);
                break;
            case 4:
                holder.tvInfo.setText(R.string.activityJoinedEvent);
                break;
            case 5:
                holder.tvInfo.setText(R.string.activityjoinedRoda);
                break;
            case 6:
                holder.tvInfo.setText(R.string.activityJoinedOnline);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final ImageView ivAvatar;
        public final ImageView ivObjectAvatar;
        public final TextView tvName;
        public final TextView tvObjectName;
        public final TextView tvInfo;
        public final TextView tvDate;
        public UserActivityContent mItem;
        OnActivityDetailListener onActivityDetailListener;

        public ViewHolder(View view, OnActivityDetailListener onActivityDetailListener) {
            super(view);
            mView = view;
            ivAvatar = view.findViewById(R.id.userActivityAvatar);
            ivObjectAvatar = view.findViewById(R.id.objectActivityAvatar);
            tvName = view.findViewById(R.id.userActivityName);
            tvObjectName = view.findViewById(R.id.objectActivityName);
            tvInfo = view.findViewById(R.id.userActivityInfo);
            tvDate = view.findViewById(R.id.userActivityDate);
            this.onActivityDetailListener = onActivityDetailListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onActivityDetailListener.OnActivityDetailClick(mItem.getId(), mItem.getType());
        }
    }
    public interface OnActivityDetailListener {
        void OnActivityDetailClick(int id, int type);
    }


}