package com.onethousandprojects.appoeira.rodaModificationView.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.rodaModificationView.RodaModificationActivity;
import com.onethousandprojects.appoeira.searchView.content.UserSearchContent;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyRodaModificationInviteMembersRecyclerViewAdapter extends RecyclerView.Adapter<MyRodaModificationInviteMembersRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<UserSearchContent> mValues;
    private OnRodaModificationInviteMembersListener myOnRodaModificationInviteMembersListener;

    public MyRodaModificationInviteMembersRecyclerViewAdapter(Context context, List<UserSearchContent> items, OnRodaModificationInviteMembersListener onGroupDetailListener) {
        mValues = items;
        ctx = context;
        this.myOnRodaModificationInviteMembersListener = onGroupDetailListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_user_invite_item, parent, false);
        return new ViewHolder(view, myOnRodaModificationInviteMembersListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Picasso.with(ctx).load(holder.mItem.getPicUrl()).fit().into(holder.ivUserAvatar);
        holder.tvUserApelhido.setText(holder.mItem.getApelhido());
        holder.tvUserRank.setText(holder.mItem.getRank());
        holder.tvUSerPremium.setText(Constants.IS_PREMIUM);

        if (holder.mItem.isPremium()) {
            holder.tvUSerPremium.setVisibility(View.VISIBLE);
            holder.ivUserPremium.setVisibility(View.VISIBLE);
        } else {
            holder.tvUSerPremium.setVisibility(View.GONE);
            holder.ivUserPremium.setVisibility(View.GONE);
        }
        if (((RodaModificationActivity) ctx).checkMembers(holder.mItem.getId())){
            holder.invited[0] = true;
            iAmInvited(holder.ivAdd, holder.clLayout);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final ConstraintLayout clLayout;
        public final ImageView ivUserAvatar;
        public final ImageView ivUserPremium;
        public final TextView tvUserApelhido;
        public final TextView tvUserRank;
        public final TextView tvUSerPremium;
        public final ImageView ivAdd;
        public UserSearchContent mItem;
        final boolean[] invited = {false};
        OnRodaModificationInviteMembersListener onRodaModificationInviteMembersListener;

        public ViewHolder(View view, OnRodaModificationInviteMembersListener onRodaModificationInviteMembersListener) {
            super(view);
            mView = view;
            clLayout = (ConstraintLayout) view.findViewById(R.id.constraintLayout);
            ivUserAvatar = (ImageView) view.findViewById(R.id.imageViewUserAvatar);
            ivUserPremium = (ImageView) view.findViewById(R.id.imageUserPremium);
            tvUserApelhido = (TextView) view.findViewById(R.id.userApelhido);
            tvUserRank = (TextView) view.findViewById(R.id.userRank);
            tvUSerPremium = (TextView) view.findViewById(R.id.userPremium);
            ivAdd = (ImageView) view.findViewById(R.id.addUser);
            this.onRodaModificationInviteMembersListener = onRodaModificationInviteMembersListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            toggleSelection(invited, mItem.getId(), ivAdd, clLayout);
        }
    }
    // https://www.youtube.com/watch?v=69C1ljfDvl0
    public interface OnRodaModificationInviteMembersListener {
        void OnRodaInviteMemberClick(int position);
    }
    private void toggleSelection(boolean[] invited, Integer id, ImageView ivAdd, ConstraintLayout clLayout) {
        if (!invited[0]) {
            invited[0] = true;
            ((RodaModificationActivity) ctx).addInvited(id);
            ivAdd.setImageResource(R.drawable.ic_close);
            clLayout.setBackgroundColor(Color.parseColor(String.valueOf(R.color.selected)));
        } else {
            invited[0] = false;
            ((RodaModificationActivity) ctx).deleteInvited(id);
            ivAdd.setImageResource(R.drawable.ic_add_plus);
            clLayout.setBackgroundColor(Color.parseColor(String.valueOf(R.color.colorWhite)));
        }
    }
    private void iAmInvited(ImageView ivAdd, ConstraintLayout clLayout) {
        ivAdd.setImageResource(R.drawable.ic_close);
        clLayout.setBackgroundColor(Color.parseColor(String.valueOf(R.color.selected)));
    }
}