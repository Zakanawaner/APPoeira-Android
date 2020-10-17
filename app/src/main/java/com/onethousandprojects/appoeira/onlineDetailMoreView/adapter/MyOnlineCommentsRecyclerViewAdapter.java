package com.onethousandprojects.appoeira.onlineDetailMoreView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.onlineDetailMoreView.content.OnlineCommentsContent;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyOnlineCommentsRecyclerViewAdapter extends RecyclerView.Adapter<MyOnlineCommentsRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<OnlineCommentsContent> mValues;
    private OnOnlineDetailListener myOnOnlineDetailListener;

    public MyOnlineCommentsRecyclerViewAdapter(Context context, List<OnlineCommentsContent> items, OnOnlineDetailListener onOnlineDetailListener) {
        mValues = items;
        ctx = context;
        this.myOnOnlineDetailListener = onOnlineDetailListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_comments_item, parent, false);
        return new ViewHolder(view, myOnOnlineDetailListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if (holder.mItem.getPicUrl().equals("")) {
            Picasso.with(ctx).load(holder.mItem.getPicUrl()).fit().into(holder.ivAvatar);
        }
        holder.tvApelhido.setText(holder.mItem.getUserApelhido());
        holder.tvDate.setText(holder.mItem.getDate());
        holder.tvComment.setText(holder.mItem.getComment());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final ImageView ivAvatar;
        public final TextView tvApelhido;
        public final TextView tvDate;
        public final TextView tvComment;
        public OnlineCommentsContent mItem;
        OnOnlineDetailListener onOnlineDetailListener;

        public ViewHolder(View view, OnOnlineDetailListener onOnlineDetailListener) {
            super(view);
            mView = view;
            ivAvatar = (ImageView) view.findViewById(R.id.UserCommentAvatar);
            tvApelhido = (TextView) view.findViewById(R.id.UserCommentApelhido);
            tvDate = (TextView) view.findViewById(R.id.UserCommentDate);
            tvComment = (TextView) view.findViewById(R.id.UserCommentContent);
            this.onOnlineDetailListener = onOnlineDetailListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onOnlineDetailListener.OnOnlineDetailClick(mItem.getUserId());
        }
    }
    // https://www.youtube.com/watch?v=69C1ljfDvl0
    public interface OnOnlineDetailListener {
        void OnOnlineDetailClick(int position);
    }


}