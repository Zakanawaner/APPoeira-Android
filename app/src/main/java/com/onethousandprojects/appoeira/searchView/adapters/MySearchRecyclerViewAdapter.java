package com.onethousandprojects.appoeira.searchView.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.searchView.content.SearchContent;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class MySearchRecyclerViewAdapter extends RecyclerView.Adapter<MySearchRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<SearchContent> mValues;
    private OnSearchListener onSearchListener;

    public MySearchRecyclerViewAdapter(Context context, List<SearchContent> items, OnSearchListener onSearchListener) {
        mValues = items;
        ctx = context;
        this.onSearchListener = onSearchListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_search_item, parent, false);
        return new ViewHolder(view, onSearchListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if (!holder.mItem.getPicUrl().equals("")) {
            Picasso.with(ctx).load(holder.mItem.getPicUrl()).fit().into(holder.ivAvatar);
        }
        if (holder.mItem.getName().length() > 20) {
            String name = holder.mItem.getName().substring(0,19) + "...";
            holder.tvName.setText(name);
        } else {
            holder.tvName.setText(holder.mItem.getName());
        }
        holder.tvRank.setText(holder.mItem.getRank());

        holder.llStars.setVisibility(View.GONE);
        holder.ivVerified.setVisibility(View.GONE);
        if (holder.mItem.isVerified()) {
            holder.ivVerified.setVisibility(View.VISIBLE);
            if (holder.mItem.getType() == 2) {
                holder.llStars.setVisibility(View.VISIBLE);
                List<ImageView> stars = CommonMethods.SetStars(holder.mItem.getRating(), holder.ibStar1, holder.ibStar2, holder.ibStar3, holder.ibStar4, holder.ibStar5);
                holder.ibStar1 = stars.get(0);
                holder.ibStar2 = stars.get(1);
                holder.ibStar3 = stars.get(2);
                holder.ibStar4 = stars.get(3);
                holder.ibStar5 = stars.get(4);
            }
        }

        if (holder.mItem.getType() == 1) { holder.tvType.setVisibility(View.GONE); }
        if (holder.mItem.getType() == 2) { holder.tvType.setText(R.string.objectGroupName); }
        if (holder.mItem.getType() == 3) { holder.tvType.setText(R.string.objectRodaName); }
        if (holder.mItem.getType() == 4) { holder.tvType.setText(R.string.objectEventName); }
        if (holder.mItem.getType() == 5) { holder.tvType.setText(R.string.objectOnlineName); }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final ImageView ivAvatar;
        public final ImageView ivVerified;
        public final TextView tvName;
        public final TextView tvRank;
        public final LinearLayout llStars;
        public ImageView ibStar1;
        public ImageView ibStar2;
        public ImageView ibStar3;
        public ImageView ibStar4;
        public ImageView ibStar5;
        public final TextView tvType;
        public SearchContent mItem;
        OnSearchListener onSearchListener;

        public ViewHolder(View view, OnSearchListener onSearchListener) {
            super(view);
            mView = view;
            ivAvatar = (ImageView) view.findViewById(R.id.avatar);
            ivVerified = (ImageView) view.findViewById(R.id.imageVerified);
            tvName = (TextView) view.findViewById(R.id.name);
            tvRank = (TextView) view.findViewById(R.id.rank);
            llStars = (LinearLayout) view.findViewById(R.id.bottomLayout);
            ibStar1 = (ImageView) view.findViewById(R.id.groupStar1);
            ibStar2 = (ImageView) view.findViewById(R.id.groupStar2);
            ibStar3 = (ImageView) view.findViewById(R.id.groupStar3);
            ibStar4 = (ImageView) view.findViewById(R.id.groupStar4);
            ibStar5 = (ImageView) view.findViewById(R.id.groupStar5);
            tvType = (TextView) view.findViewById(R.id.objectType);
            this.onSearchListener = onSearchListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onSearchListener.OnSearchClick(mValues.get(getAdapterPosition()).getId(), mValues.get(getAdapterPosition()).getType());
        }
    }
    // https://www.youtube.com/watch?v=69C1ljfDvl0
    public interface OnSearchListener {
        void OnSearchClick(int id, int type);
    }
}