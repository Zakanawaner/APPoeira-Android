package com.onethousandprojects.appoeira.groupDetailMoreView.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.groupDetailMoreView.GroupDetailMoreActivity;

public class BeenHereRatingGroup extends DialogFragment {
    private boolean beenHere = false;
    private Integer stars;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_group_been_here, container, false);
        GroupDetailMoreActivity groupDetailMoreActivity = ((GroupDetailMoreActivity) requireActivity());
        ImageView ivClose = view.findViewById(R.id.rateGroupClose);
        LinearLayout llStars = view.findViewById(R.id.starsLayout);
        ImageButton btnStar1 = view.findViewById(R.id.favorite_button_1);
        ImageButton btnStar2 = view.findViewById(R.id.favorite_button_2);
        ImageButton btnStar3 = view.findViewById(R.id.favorite_button_3);
        ImageButton btnStar4 = view.findViewById(R.id.favorite_button_4);
        ImageButton btnStar5 = view.findViewById(R.id.favorite_button_5);
        TextView tvWouldYouRate = view.findViewById(R.id.wouldYouRate);
        Button btnBeenHere = view.findViewById(R.id.haveYouBeenHereButton);

        llStars.setVisibility(View.GONE);
        tvWouldYouRate.setVisibility(View.GONE);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupDetailMoreActivity.killFragment();
            }
        });
        btnBeenHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!beenHere) {
                    beenHere = true;
                    llStars.setVisibility(View.VISIBLE);
                    tvWouldYouRate.setVisibility(View.VISIBLE);
                    btnStar1.setImageResource(R.drawable.ic_star_void);
                    btnStar2.setImageResource(R.drawable.ic_star_void);
                    btnStar3.setImageResource(R.drawable.ic_star_void);
                    btnStar4.setImageResource(R.drawable.ic_star_void);
                    btnStar5.setImageResource(R.drawable.ic_star_void);
                    stars = 0;
                    btnStar1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            btnStar1.setImageResource(R.drawable.ic_star);
                            btnStar2.setImageResource(R.drawable.ic_star_void);
                            btnStar3.setImageResource(R.drawable.ic_star_void);
                            btnStar4.setImageResource(R.drawable.ic_star_void);
                            btnStar5.setImageResource(R.drawable.ic_star_void);
                            stars = 1;
                        }
                    });
                    btnStar2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            btnStar1.setImageResource(R.drawable.ic_star);
                            btnStar2.setImageResource(R.drawable.ic_star);
                            btnStar3.setImageResource(R.drawable.ic_star_void);
                            btnStar4.setImageResource(R.drawable.ic_star_void);
                            btnStar5.setImageResource(R.drawable.ic_star_void);
                            stars = 2;
                        }
                    });
                    btnStar3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            btnStar1.setImageResource(R.drawable.ic_star);
                            btnStar2.setImageResource(R.drawable.ic_star);
                            btnStar3.setImageResource(R.drawable.ic_star);
                            btnStar4.setImageResource(R.drawable.ic_star_void);
                            btnStar5.setImageResource(R.drawable.ic_star_void);
                            stars = 3;
                        }
                    });
                    btnStar4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            btnStar1.setImageResource(R.drawable.ic_star);
                            btnStar2.setImageResource(R.drawable.ic_star);
                            btnStar3.setImageResource(R.drawable.ic_star);
                            btnStar4.setImageResource(R.drawable.ic_star);
                            btnStar5.setImageResource(R.drawable.ic_star_void);
                            stars = 4;
                        }
                    });
                    btnStar5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            btnStar1.setImageResource(R.drawable.ic_star);
                            btnStar2.setImageResource(R.drawable.ic_star);
                            btnStar3.setImageResource(R.drawable.ic_star);
                            btnStar4.setImageResource(R.drawable.ic_star);
                            btnStar5.setImageResource(R.drawable.ic_star);
                            stars = 5;
                        }
                    });
                    btnBeenHere.setText(R.string.sendRating);
                } else {
                    groupDetailMoreActivity.groupDetailMoreServer.sendRating(groupDetailMoreActivity, groupDetailMoreActivity.fromGroupDetailActivity.getInt("groupId"), stars);
                }
            }
        });
        return view;
    }
}
