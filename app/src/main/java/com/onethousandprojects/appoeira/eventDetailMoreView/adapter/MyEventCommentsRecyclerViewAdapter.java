package com.onethousandprojects.appoeira.eventDetailMoreView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.eventDetailMoreView.content.EventCommentsContent;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyEventCommentsRecyclerViewAdapter extends RecyclerView.Adapter<MyEventCommentsRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<EventCommentsContent> mValues;
    private OnEventDetailListener myOnEventDetailListener;

    public MyEventCommentsRecyclerViewAdapter(Context context, List<EventCommentsContent> items, OnEventDetailListener onEventDetailListener) {
        mValues = items;
        ctx = context;
        this.myOnEventDetailListener = onEventDetailListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_comments_item, parent, false);
        return new ViewHolder(view, myOnEventDetailListener);
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
        public EventCommentsContent mItem;
        OnEventDetailListener onEventDetailListener;

        public ViewHolder(View view, OnEventDetailListener onEventDetailListener) {
            super(view);
            mView = view;
            ivAvatar = (ImageView) view.findViewById(R.id.UserCommentAvatar);
            tvApelhido = (TextView) view.findViewById(R.id.UserCommentApelhido);
            tvDate = (TextView) view.findViewById(R.id.UserCommentDate);
            tvComment = (TextView) view.findViewById(R.id.UserCommentContent);
            this.onEventDetailListener = onEventDetailListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onEventDetailListener.OnEventDetailClick(mItem.getUserId());
        }
    }
    // https://www.youtube.com/watch?v=69C1ljfDvl0
    public interface OnEventDetailListener {
        void OnEventDetailClick(int position);
    }


}