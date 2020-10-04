package com.onethousandprojects.appoeira.groupDetailMoreView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.groupDetailMoreView.content.GroupCommentsContent;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyGroupCommentsRecyclerViewAdapter extends RecyclerView.Adapter<MyGroupCommentsRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<GroupCommentsContent> mValues;
    private OnGroupDetailListener myOnGroupDetailListener;

    public MyGroupCommentsRecyclerViewAdapter(Context context, List<GroupCommentsContent> items, OnGroupDetailListener onGroupDetailListener) {
        mValues = items;
        ctx = context;
        this.myOnGroupDetailListener = onGroupDetailListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_comments_item, parent, false);
        return new ViewHolder(view, myOnGroupDetailListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Picasso.with(ctx).load(holder.mItem.getPicUrl()).fit().into(holder.ivAvatar);
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
        public GroupCommentsContent mItem;
        OnGroupDetailListener onGroupDetailListener;

        public ViewHolder(View view, OnGroupDetailListener onGroupDetailListener) {
            super(view);
            mView = view;
            ivAvatar = (ImageView) view.findViewById(R.id.UserCommentAvatar);
            tvApelhido = (TextView) view.findViewById(R.id.UserCommentApelhido);
            tvDate = (TextView) view.findViewById(R.id.UserCommentDate);
            tvComment = (TextView) view.findViewById(R.id.UserCommentContent);
            this.onGroupDetailListener = onGroupDetailListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onGroupDetailListener.OnGroupDetailClick(mItem.getUserId());
        }
    }
    // https://www.youtube.com/watch?v=69C1ljfDvl0
    public interface OnGroupDetailListener {
        void OnGroupDetailClick(int position);
    }


}