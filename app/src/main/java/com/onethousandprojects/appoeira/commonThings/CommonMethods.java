package com.onethousandprojects.appoeira.commonThings;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.ActionMenuItemView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.authView.LoginActivity;
import com.onethousandprojects.appoeira.authView.SignUpActivity;
import com.onethousandprojects.appoeira.eventDetailMoreView.EventDetailMoreActivity;
import com.onethousandprojects.appoeira.eventDetailView.EventDetailActivity;
import com.onethousandprojects.appoeira.eventListView.EventListActivity;
import com.onethousandprojects.appoeira.eventModificationView.EventModificationActivity;
import com.onethousandprojects.appoeira.groupDetailMoreView.GroupDetailMoreActivity;
import com.onethousandprojects.appoeira.groupDetailView.GroupDetailActivity;
import com.onethousandprojects.appoeira.groupListView.GroupListActivity;
import com.onethousandprojects.appoeira.groupListView.fragments.GroupFragment;
import com.onethousandprojects.appoeira.groupListView.fragments.GroupMapsFragment;
import com.onethousandprojects.appoeira.groupModificationView.GroupModificationActivity;
import com.onethousandprojects.appoeira.helpView.HelpActivity;
import com.onethousandprojects.appoeira.newsView.NewsActivity;
import com.onethousandprojects.appoeira.onlineDetailMoreView.OnlineDetailMoreActivity;
import com.onethousandprojects.appoeira.onlineDetailView.OnlineDetailActivity;
import com.onethousandprojects.appoeira.onlineListView.OnlineListActivity;
import com.onethousandprojects.appoeira.onlineModificationView.OnlineModificationActivity;
import com.onethousandprojects.appoeira.rodaDetailMoreView.RodaDetailMoreActivity;
import com.onethousandprojects.appoeira.rodaDetailView.RodaDetailActivity;
import com.onethousandprojects.appoeira.rodaListView.RodaListActivity;
import com.onethousandprojects.appoeira.rodaListView.fragments.RodaFragment;
import com.onethousandprojects.appoeira.rodaListView.fragments.RodaMapsFragment;
import com.onethousandprojects.appoeira.rodaModificationView.RodaModificationActivity;
import com.onethousandprojects.appoeira.searchView.SearchActivity;
import com.onethousandprojects.appoeira.userDetailView.UserDetailActivity;
import com.onethousandprojects.appoeira.userModificationView.ProfileModificationActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class CommonMethods {
    public static Integer fromRankNameToRankId(String rank, Context ctx) {
        if (rank.equals(ctx.getResources().getString(R.string.rankSpinner1))) {return 1;}
        if (rank.equals(ctx.getResources().getString(R.string.rankSpinner2))) {return 2;}
        if (rank.equals(ctx.getResources().getString(R.string.rankSpinner3))) {return 3;}
        if (rank.equals(ctx.getResources().getString(R.string.rankSpinner4))) {return 4;}
        if (rank.equals(ctx.getResources().getString(R.string.rankSpinner5))) {return 5;}
        if (rank.equals(ctx.getResources().getString(R.string.rankSpinner6))) {return 6;}
        if (rank.equals(ctx.getResources().getString(R.string.rankSpinner7))) {return 7;}
        if (rank.equals(ctx.getResources().getString(R.string.rankSpinner8))) {return 8;}
        if (rank.equals(ctx.getResources().getString(R.string.rankSpinner9))) {return 9;}
        if (rank.equals(ctx.getResources().getString(R.string.rankSpinner10))) {return 10;}
        if (rank.equals(ctx.getResources().getString(R.string.rankSpinner11))) {return 11;}
        if (rank.equals(ctx.getResources().getString(R.string.rankSpinner12))) {return 12;}
        if (rank.equals(ctx.getResources().getString(R.string.rankSpinner13))) {return 13;}
        if (rank.equals(ctx.getResources().getString(R.string.rankSpinner14))) {return 14;}
        if (rank.equals(ctx.getResources().getString(R.string.rankSpinner15))) {return 15;}
        if (rank.equals(ctx.getResources().getString(R.string.rankSpinner16))) {return 16;}
        return 0;
    }
    public static String fromRankIdToRankName(Integer rank, Context ctx) {
        if (rank == 1) {return ctx.getResources().getString(R.string.rankSpinner1);}
        if (rank == 2) {return ctx.getResources().getString(R.string.rankSpinner2);}
        if (rank == 3) {return ctx.getResources().getString(R.string.rankSpinner3);}
        if (rank == 4) {return ctx.getResources().getString(R.string.rankSpinner4);}
        if (rank == 5) {return ctx.getResources().getString(R.string.rankSpinner5);}
        if (rank == 6) {return ctx.getResources().getString(R.string.rankSpinner6);}
        if (rank == 7) {return ctx.getResources().getString(R.string.rankSpinner7);}
        if (rank == 8) {return ctx.getResources().getString(R.string.rankSpinner8);}
        if (rank == 9) {return ctx.getResources().getString(R.string.rankSpinner9);}
        if (rank == 10) {return ctx.getResources().getString(R.string.rankSpinner10);}
        if (rank == 11) {return ctx.getResources().getString(R.string.rankSpinner11);}
        if (rank == 12) {return ctx.getResources().getString(R.string.rankSpinner12);}
        if (rank == 13) {return ctx.getResources().getString(R.string.rankSpinner13);}
        if (rank == 14) {return ctx.getResources().getString(R.string.rankSpinner14);}
        if (rank == 15) {return ctx.getResources().getString(R.string.rankSpinner15);}
        if (rank == 16) {return ctx.getResources().getString(R.string.rankSpinner16);}
        return "";
    }
    public static String fromPlatformIdToPlatformName(Integer platform, Context ctx) {
        if (platform == 1) {return ctx.getResources().getString(R.string.platformSpinner1);}
        if (platform == 2) {return ctx.getResources().getString(R.string.platformSpinner2);}
        if (platform == 3) {return ctx.getResources().getString(R.string.platformSpinner3);}
        if (platform == 4) {return ctx.getResources().getString(R.string.platformSpinner4);}
        if (platform == 5) {return ctx.getResources().getString(R.string.platformSpinner5);}
        if (platform == 6) {return ctx.getResources().getString(R.string.platformSpinner6);}
        return "";
    }
    public static Integer fromPlatformNameToPlatformId(String platform, Context ctx) {
        if (platform.equals(ctx.getResources().getString(R.string.platformSpinner1))) {return 1;}
        if (platform.equals(ctx.getResources().getString(R.string.platformSpinner2))) {return 2;}
        if (platform.equals(ctx.getResources().getString(R.string.platformSpinner3))) {return 3;}
        if (platform.equals(ctx.getResources().getString(R.string.platformSpinner4))) {return 4;}
        if (platform.equals(ctx.getResources().getString(R.string.platformSpinner5))) {return 5;}
        if (platform.equals(ctx.getResources().getString(R.string.platformSpinner6))) {return 6;}
        return 0;
    }
    public static class NewsVariable {
        public boolean gotNews;
        private ChangeListener listener;
        public NewsVariable(boolean gotNews) {
            this.gotNews = gotNews;
        }
        public boolean isGotNews() {
            return gotNews;
        }
        public void setGotNews(boolean gotNews) {
            this.gotNews = gotNews;
            if (listener != null) listener.onChange();
        }
        public ChangeListener getListener() {
            return listener;
        }
        public void setListener(ChangeListener listener) {
            this.listener = listener;
        }
        public interface ChangeListener {
            void onChange();
        }
    }
    public static void fromPlatformNameToPlatformLogo(String platform, Context ctx, ImageView imageView) {
        if (platform.equals(ctx.getResources().getString(R.string.platformSpinner1))) {imageView.setImageResource(R.drawable.ic_launcher_foreground);}
        if (platform.equals(ctx.getResources().getString(R.string.platformSpinner2))) {imageView.setImageResource(R.drawable.ic_zoom_logo);}
        if (platform.equals(ctx.getResources().getString(R.string.platformSpinner3))) {imageView.setImageResource(R.drawable.ic_instagram_logo);}
        if (platform.equals(ctx.getResources().getString(R.string.platformSpinner4))) {imageView.setImageResource(R.drawable.ic_skype);}
        if (platform.equals(ctx.getResources().getString(R.string.platformSpinner5))) {imageView.setImageResource(R.drawable.ic_jitsi_logo);}
    }
    public static class CircleTransform  implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }
            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);
            float r = size/2f;
            canvas.drawCircle(r, r, r, paint);
            squaredBitmap.recycle();
            return bitmap;
        }
        @Override
        public String key() {
            return "circle";
        }
    }
    public static boolean AmILogged() {
        return SharedPreferencesManager.getIntegerValue(Constants.ID) != 0 && SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN) != null;
    }
    public static boolean AmIVerified() {
        return SharedPreferencesManager.getBooleanValue(Constants.EMAIL_VERIFIED);
    }
    public static BottomNavigationView.OnNavigationItemSelectedListener BottomNavigationMenuHandler(String origin, NavParams activities) {
        GroupListActivity groupListActivity;
        GroupDetailActivity groupDetailActivity;
        GroupDetailMoreActivity groupDetailMoreActivity;
        GroupModificationActivity groupModificationActivity;
        EventListActivity eventListActivity;
        EventDetailActivity eventDetailActivity;
        EventDetailMoreActivity eventDetailMoreActivity;
        EventModificationActivity eventModificationActivity;
        RodaListActivity rodaListActivity;
        RodaDetailActivity rodaDetailActivity;
        RodaDetailMoreActivity rodaDetailMoreActivity;
        RodaModificationActivity rodaModificationActivity;
        OnlineListActivity onlineListActivity;
        OnlineDetailActivity onlineDetailActivity;
        OnlineDetailMoreActivity onlineDetailMoreActivity;
        OnlineModificationActivity onlineModificationActivity;
        UserDetailActivity userDetailActivity;
        ProfileModificationActivity profileModificationActivity;
        SearchActivity searchActivity;
        NewsActivity newsActivity;
        HelpActivity helpActivity;
        LoginActivity loginActivity;
        SignUpActivity signUpActivity;
        switch (origin) {
            case "GroupListActivity":
                groupListActivity = activities.groupList;
                return new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.groups_menu:
                                if (!groupListActivity.toggle_menu) {
                                    groupListActivity.fbtnDistance.setVisibility(View.GONE);
                                    groupListActivity.fbtnDistance.setImageResource(R.drawable.ic_road);
                                    groupListActivity.tvDistance.setVisibility(View.GONE);
                                    groupListActivity.sbDistance.setVisibility(View.GONE);
                                    groupListActivity.srGroupList.setEnabled(false);
                                    groupListActivity.toggle_menu = true;
                                    groupListActivity.getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(groupListActivity.getSupportFragmentManager().findFragmentByTag("GroupListFragment"))).commit();
                                    groupListActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new GroupMapsFragment(), "GroupMapFragment").commit();
                                } else {
                                    groupListActivity.fbtnDistance.setVisibility(View.VISIBLE);
                                    groupListActivity.srGroupList.setEnabled(true);
                                    groupListActivity.toggle_menu = false;
                                    groupListActivity.getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(groupListActivity.getSupportFragmentManager().findFragmentByTag("GroupMapFragment"))).commit();
                                    groupListActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new GroupFragment(), "GroupListFragment").commit();
                                }
                                break;
                            case R.id.rodas_menu:
                                Intent toRodaListActivity = new Intent(groupListActivity, RodaListActivity.class);
                                groupListActivity.startActivity(toRodaListActivity);
                                groupListActivity.finish();
                                break;
                            case R.id.events_menu:
                                Intent toEventListActivity = new Intent(groupListActivity, EventListActivity.class);
                                groupListActivity.startActivity(toEventListActivity);
                                groupListActivity.finish();
                                break;
                            case R.id.onlines_menu:
                                Intent toOnlineListActivity = new Intent(groupListActivity, OnlineListActivity.class);
                                groupListActivity.startActivity(toOnlineListActivity);
                                groupListActivity.finish();
                                break;
                        }
                        return false;
                    }
                };
            case "GroupDetailActivity":
                groupDetailActivity = activities.getGroupDetail();
                return new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.groups_menu:
                                Intent toGroupListActivity = new Intent(groupDetailActivity, GroupListActivity.class);
                                groupDetailActivity.startActivity(toGroupListActivity);
                                break;
                            case R.id.rodas_menu:
                                Intent toRodaListActivity = new Intent(groupDetailActivity, RodaListActivity.class);
                                groupDetailActivity.startActivity(toRodaListActivity);
                                break;
                            case R.id.events_menu:
                                Intent toEventListActivity = new Intent(groupDetailActivity, EventListActivity.class);
                                groupDetailActivity.startActivity(toEventListActivity);
                                break;
                            case R.id.onlines_menu:
                                Intent toOnlineListActivity = new Intent(groupDetailActivity, OnlineListActivity.class);
                                groupDetailActivity.startActivity(toOnlineListActivity);
                                break;
                        }
                        return false;
                    }
                };
            case "GroupDetailMoreActivity":
                groupDetailMoreActivity = activities.getGroupDetailMore();
                return new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.groups_menu:
                                Intent toGroupListActivity = new Intent(groupDetailMoreActivity, GroupListActivity.class);
                                groupDetailMoreActivity.startActivity(toGroupListActivity);
                                break;
                            case R.id.rodas_menu:
                                Intent toRodaListActivity = new Intent(groupDetailMoreActivity, RodaListActivity.class);
                                groupDetailMoreActivity.startActivity(toRodaListActivity);
                                break;
                            case R.id.events_menu:
                                Intent toEventListActivity = new Intent(groupDetailMoreActivity, EventListActivity.class);
                                groupDetailMoreActivity.startActivity(toEventListActivity);
                                break;
                            case R.id.onlines_menu:
                                Intent toOnlineListActivity = new Intent(groupDetailMoreActivity, OnlineListActivity.class);
                                groupDetailMoreActivity.startActivity(toOnlineListActivity);
                                break;
                        }
                        return false;
                    }
                };
            case "GroupModificationActivity":
                groupModificationActivity = activities.getGroupModification();
                return new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.groups_menu:
                                Intent toGroupListActivity = new Intent(groupModificationActivity, GroupListActivity.class);
                                groupModificationActivity.startActivity(toGroupListActivity);
                                groupModificationActivity.finish();
                                break;
                            case R.id.rodas_menu:
                                Intent toRodaListActivity = new Intent(groupModificationActivity, RodaListActivity.class);
                                groupModificationActivity.startActivity(toRodaListActivity);
                                groupModificationActivity.finish();
                                break;
                            case R.id.events_menu:
                                Intent toEventListActivity = new Intent(groupModificationActivity, EventListActivity.class);
                                groupModificationActivity.startActivity(toEventListActivity);
                                groupModificationActivity.finish();
                                break;
                            case R.id.onlines_menu:
                                Intent toOnlineListActivity = new Intent(groupModificationActivity, OnlineListActivity.class);
                                groupModificationActivity.startActivity(toOnlineListActivity);
                                groupModificationActivity.finish();
                                break;
                        }
                        return false;
                    }
                };
            case "EventListActivity":
                eventListActivity = activities.getEventList();
                return new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.groups_menu:
                                Intent toGroupListActivity = new Intent(eventListActivity, GroupListActivity.class);
                                eventListActivity.startActivity(toGroupListActivity);
                                eventListActivity.finish();
                                break;
                            case R.id.rodas_menu:
                                Intent toRodaListActivity = new Intent(eventListActivity, RodaListActivity.class);
                                eventListActivity.startActivity(toRodaListActivity);
                                eventListActivity.finish();
                                break;
                            case R.id.events_menu:
                                break;
                            case R.id.onlines_menu:
                                Intent toOnlineListActivity = new Intent(eventListActivity, OnlineListActivity.class);
                                eventListActivity.startActivity(toOnlineListActivity);
                                eventListActivity.finish();
                                break;
                        }
                        return false;
                    }
                };
            case "EventDetailActivity":
                eventDetailActivity = activities.getEventDetail();
                return new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.groups_menu:
                                Intent toGroupListActivity = new Intent(eventDetailActivity, GroupListActivity.class);
                                eventDetailActivity.startActivity(toGroupListActivity);
                                break;
                            case R.id.rodas_menu:
                                Intent toRodaListActivity = new Intent(eventDetailActivity, RodaListActivity.class);
                                eventDetailActivity.startActivity(toRodaListActivity);
                                break;
                            case R.id.events_menu:
                                Intent toEventListActivity = new Intent(eventDetailActivity, EventListActivity.class);
                                eventDetailActivity.startActivity(toEventListActivity);
                                break;
                            case R.id.onlines_menu:
                                Intent toOnlineListActivity = new Intent(eventDetailActivity, OnlineListActivity.class);
                                eventDetailActivity.startActivity(toOnlineListActivity);
                                break;
                        }
                        return false;
                    }
                };
            case "EventDetailMoreActivity":
                eventDetailMoreActivity = activities.getEventDetailMore();
                return new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.groups_menu:
                                Intent toGroupListActivity = new Intent(eventDetailMoreActivity, GroupListActivity.class);
                                eventDetailMoreActivity.startActivity(toGroupListActivity);
                                break;
                            case R.id.rodas_menu:
                                Intent toRodaListActivity = new Intent(eventDetailMoreActivity, RodaListActivity.class);
                                eventDetailMoreActivity.startActivity(toRodaListActivity);
                                break;
                            case R.id.events_menu:
                                Intent toEventListActivity = new Intent(eventDetailMoreActivity, EventListActivity.class);
                                eventDetailMoreActivity.startActivity(toEventListActivity);
                                break;
                            case R.id.onlines_menu:
                                Intent toOnlineListActivity = new Intent(eventDetailMoreActivity, OnlineListActivity.class);
                                eventDetailMoreActivity.startActivity(toOnlineListActivity);
                                break;
                        }
                        return false;
                    }
                };
            case "EventModificationActivity":
                eventModificationActivity = activities.getEventModification();
                return new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.groups_menu:
                                Intent toGroupListActivity = new Intent(eventModificationActivity, GroupListActivity.class);
                                eventModificationActivity.startActivity(toGroupListActivity);
                                eventModificationActivity.finish();
                                break;
                            case R.id.rodas_menu:
                                Intent toRodaListActivity = new Intent(eventModificationActivity, RodaListActivity.class);
                                eventModificationActivity.startActivity(toRodaListActivity);
                                eventModificationActivity.finish();
                                break;
                            case R.id.events_menu:
                                Intent toEventListActivity = new Intent(eventModificationActivity, EventListActivity.class);
                                eventModificationActivity.startActivity(toEventListActivity);
                                eventModificationActivity.finish();
                                break;
                            case R.id.onlines_menu:
                                Intent toOnlineListActivity = new Intent(eventModificationActivity, OnlineListActivity.class);
                                eventModificationActivity.startActivity(toOnlineListActivity);
                                eventModificationActivity.finish();
                                break;
                        }
                        return false;
                    }
                };
            case "RodaListActivity":
                rodaListActivity = activities.getRodaList();
                return new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.groups_menu:
                                Intent toGroupListActivity = new Intent(rodaListActivity, GroupListActivity.class);
                                rodaListActivity.startActivity(toGroupListActivity);
                                rodaListActivity.finish();
                                break;
                            case R.id.rodas_menu:
                                if (!rodaListActivity.toggle_menu) {
                                    rodaListActivity.fbtnDistance.setVisibility(View.GONE);
                                    rodaListActivity.fbtnDistance.setImageResource(R.drawable.ic_road);
                                    rodaListActivity.tvDistance.setVisibility(View.GONE);
                                    rodaListActivity.sbDistance.setVisibility(View.GONE);
                                    rodaListActivity.srRodaList.setEnabled(false);
                                    rodaListActivity.toggle_menu = true;
                                    rodaListActivity.getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(rodaListActivity.getSupportFragmentManager().findFragmentByTag("RodaListFragment"))).commit();
                                    rodaListActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new RodaMapsFragment(), "RodaMapFragment").commit();
                                } else {
                                    rodaListActivity.fbtnDistance.setVisibility(View.VISIBLE);
                                    rodaListActivity.srRodaList.setEnabled(true);
                                    rodaListActivity.toggle_menu = false;
                                    rodaListActivity.getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(rodaListActivity.getSupportFragmentManager().findFragmentByTag("RodaMapFragment"))).commit();
                                    rodaListActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new RodaFragment(), "RodaListFragment").commit();
                                }
                                break;
                            case R.id.events_menu:
                                Intent toEventListActivity = new Intent(rodaListActivity, EventListActivity.class);
                                rodaListActivity.startActivity(toEventListActivity);
                                rodaListActivity.finish();
                                break;
                            case R.id.onlines_menu:
                                Intent toOnlineListActivity = new Intent(rodaListActivity, OnlineListActivity.class);
                                rodaListActivity.startActivity(toOnlineListActivity);
                                rodaListActivity.finish();
                                break;
                        }
                        return false;
                    }
                };
            case "RodaDetailActivity":
                rodaDetailActivity = activities.getRodaDetail();
                return new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.groups_menu:
                                Intent toGroupListActivity = new Intent(rodaDetailActivity, GroupListActivity.class);
                                rodaDetailActivity.startActivity(toGroupListActivity);
                                break;
                            case R.id.rodas_menu:
                                Intent toRodaListActivity = new Intent(rodaDetailActivity, RodaListActivity.class);
                                rodaDetailActivity.startActivity(toRodaListActivity);
                                break;
                            case R.id.events_menu:
                                Intent toEventListActivity = new Intent(rodaDetailActivity, EventListActivity.class);
                                rodaDetailActivity.startActivity(toEventListActivity);
                                break;
                            case R.id.onlines_menu:
                                Intent toOnlineListActivity = new Intent(rodaDetailActivity, OnlineListActivity.class);
                                rodaDetailActivity.startActivity(toOnlineListActivity);
                                break;
                        }
                        return false;
                    }
                };
            case "RodaDetailMoreActivity":
                rodaDetailMoreActivity = activities.getRodaDetailMore();
                return new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.groups_menu:
                                Intent toGroupListActivity = new Intent(rodaDetailMoreActivity, GroupListActivity.class);
                                rodaDetailMoreActivity.startActivity(toGroupListActivity);
                                break;
                            case R.id.rodas_menu:
                                Intent toRodaListActivity = new Intent(rodaDetailMoreActivity, RodaListActivity.class);
                                rodaDetailMoreActivity.startActivity(toRodaListActivity);
                                break;
                            case R.id.events_menu:
                                Intent toEventListActivity = new Intent(rodaDetailMoreActivity, EventListActivity.class);
                                rodaDetailMoreActivity.startActivity(toEventListActivity);
                                break;
                            case R.id.onlines_menu:
                                Intent toOnlineListActivity = new Intent(rodaDetailMoreActivity, OnlineListActivity.class);
                                rodaDetailMoreActivity.startActivity(toOnlineListActivity);
                                break;
                        }
                        return false;
                    }
                };
            case "RodaModificationActivity":
                rodaModificationActivity = activities.getRodaModification();
                return new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.groups_menu:
                                Intent toGroupListActivity = new Intent(rodaModificationActivity, GroupListActivity.class);
                                rodaModificationActivity.startActivity(toGroupListActivity);
                                rodaModificationActivity.finish();
                                break;
                            case R.id.rodas_menu:
                                Intent toRodaListActivity = new Intent(rodaModificationActivity, RodaListActivity.class);
                                rodaModificationActivity.startActivity(toRodaListActivity);
                                rodaModificationActivity.finish();
                            case R.id.events_menu:
                                Intent toEventListActivity = new Intent(rodaModificationActivity, EventListActivity.class);
                                rodaModificationActivity.startActivity(toEventListActivity);
                                rodaModificationActivity.finish();
                                break;
                            case R.id.onlines_menu:
                                Intent toOnlineListActivity = new Intent(rodaModificationActivity, OnlineListActivity.class);
                                rodaModificationActivity.startActivity(toOnlineListActivity);
                                rodaModificationActivity.finish();
                                break;
                        }
                        return false;
                    }
                };
            case "OnlineListActivity":
                onlineListActivity = activities.getOnlineList();
                return new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.groups_menu:
                                Intent toGroupListActivity = new Intent(onlineListActivity, GroupListActivity.class);
                                onlineListActivity.startActivity(toGroupListActivity);
                                onlineListActivity.finish();
                                break;
                            case R.id.rodas_menu:
                                Intent toRodaListActivity = new Intent(onlineListActivity, RodaListActivity.class);
                                onlineListActivity.startActivity(toRodaListActivity);
                                onlineListActivity.finish();
                                break;
                            case R.id.events_menu:
                                Intent toEventListActivity = new Intent(onlineListActivity, EventListActivity.class);
                                onlineListActivity.startActivity(toEventListActivity);
                                onlineListActivity.finish();
                                break;
                            case R.id.onlines_menu:
                                break;
                        }
                        return false;
                    }
                };
            case "OnlineDetailActivity":
                onlineDetailActivity = activities.getOnlineDetail();
                return new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.groups_menu:
                                Intent toGroupListActivity = new Intent(onlineDetailActivity, GroupListActivity.class);
                                onlineDetailActivity.startActivity(toGroupListActivity);
                                break;
                            case R.id.rodas_menu:
                                Intent toRodaListActivity = new Intent(onlineDetailActivity, RodaListActivity.class);
                                onlineDetailActivity.startActivity(toRodaListActivity);
                                break;
                            case R.id.events_menu:
                                Intent toEventListActivity = new Intent(onlineDetailActivity, EventListActivity.class);
                                onlineDetailActivity.startActivity(toEventListActivity);
                                break;
                            case R.id.onlines_menu:
                                Intent toOnlineListActivity = new Intent(onlineDetailActivity, OnlineListActivity.class);
                                onlineDetailActivity.startActivity(toOnlineListActivity);
                                break;
                        }
                        return false;
                    }
                };
            case "OnlineDetailMoreActivity":
                onlineDetailMoreActivity = activities.getOnlineDetailMore();
                return new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.groups_menu:
                                Intent toGroupListActivity = new Intent(onlineDetailMoreActivity, GroupListActivity.class);
                                onlineDetailMoreActivity.startActivity(toGroupListActivity);
                                break;
                            case R.id.rodas_menu:
                                Intent toRodaListActivity = new Intent(onlineDetailMoreActivity, RodaListActivity.class);
                                onlineDetailMoreActivity.startActivity(toRodaListActivity);
                                break;
                            case R.id.events_menu:
                                Intent toEventListActivity = new Intent(onlineDetailMoreActivity, EventListActivity.class);
                                onlineDetailMoreActivity.startActivity(toEventListActivity);
                                break;
                            case R.id.onlines_menu:
                                Intent toOnlineListActivity = new Intent(onlineDetailMoreActivity, OnlineListActivity.class);
                                onlineDetailMoreActivity.startActivity(toOnlineListActivity);
                                break;
                        }
                        return false;
                    }
                };
            case "OnlineModificationActivity":
                onlineModificationActivity = activities.getOnlineModification();
                return new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.groups_menu:
                                Intent toGroupListActivity = new Intent(onlineModificationActivity, GroupListActivity.class);
                                onlineModificationActivity.startActivity(toGroupListActivity);
                                onlineModificationActivity.finish();
                                break;
                            case R.id.rodas_menu:
                                Intent toRodaListActivity = new Intent(onlineModificationActivity, RodaListActivity.class);
                                onlineModificationActivity.startActivity(toRodaListActivity);
                                onlineModificationActivity.finish();
                                break;
                            case R.id.events_menu:
                                Intent toEventListActivity = new Intent(onlineModificationActivity, EventListActivity.class);
                                onlineModificationActivity.startActivity(toEventListActivity);
                                onlineModificationActivity.finish();
                                break;
                            case R.id.onlines_menu:
                                Intent toOnlineListActivity = new Intent(onlineModificationActivity, OnlineListActivity.class);
                                onlineModificationActivity.startActivity(toOnlineListActivity);
                                onlineModificationActivity.finish();
                                break;
                        }
                        return false;
                    }
                };
            case "UserDetailActivity":
                userDetailActivity = activities.getUserDetail();
                return new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.groups_menu:
                                Intent toGroupListActivity = new Intent(userDetailActivity, GroupListActivity.class);
                                userDetailActivity.startActivity(toGroupListActivity);
                                break;
                            case R.id.rodas_menu:
                                Intent toRodaListActivity = new Intent(userDetailActivity, RodaListActivity.class);
                                userDetailActivity.startActivity(toRodaListActivity);
                                break;
                            case R.id.events_menu:
                                Intent toEventListActivity = new Intent(userDetailActivity, EventListActivity.class);
                                userDetailActivity.startActivity(toEventListActivity);
                                break;
                            case R.id.onlines_menu:
                                Intent toOnlineListActivity = new Intent(userDetailActivity, OnlineListActivity.class);
                                userDetailActivity.startActivity(toOnlineListActivity);
                                break;
                        }
                        return false;
                    }
                };
            case "ProfileModificationActivity":
                profileModificationActivity = activities.getProfileModification();
                return new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.groups_menu:
                                Intent toGroupListActivity = new Intent(profileModificationActivity, GroupListActivity.class);
                                profileModificationActivity.startActivity(toGroupListActivity);
                                profileModificationActivity.finish();
                                break;
                            case R.id.rodas_menu:
                                Intent toRodaListActivity = new Intent(profileModificationActivity, RodaListActivity.class);
                                profileModificationActivity.startActivity(toRodaListActivity);
                                profileModificationActivity.finish();
                                break;
                            case R.id.events_menu:
                                Intent toEventListActivity = new Intent(profileModificationActivity, EventListActivity.class);
                                profileModificationActivity.startActivity(toEventListActivity);
                                profileModificationActivity.finish();
                                break;
                            case R.id.onlines_menu:
                                Intent toOnlineListActivity = new Intent(profileModificationActivity, OnlineListActivity.class);
                                profileModificationActivity.startActivity(toOnlineListActivity);
                                profileModificationActivity.finish();
                                break;
                        }
                        return false;
                    }
                };
            case "SearchActivity":
                searchActivity = activities.getSearch();
                return new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.groups_menu:
                                Intent toGroupListActivity = new Intent(searchActivity, GroupListActivity.class);
                                searchActivity.startActivity(toGroupListActivity);
                                break;
                            case R.id.rodas_menu:
                                Intent toRodaListActivity = new Intent(searchActivity, RodaListActivity.class);
                                searchActivity.startActivity(toRodaListActivity);
                                break;
                            case R.id.events_menu:
                                Intent toEventListActivity = new Intent(searchActivity, EventListActivity.class);
                                searchActivity.startActivity(toEventListActivity);
                                break;
                            case R.id.onlines_menu:
                                Intent toOnlineListActivity = new Intent(searchActivity, OnlineListActivity.class);
                                searchActivity.startActivity(toOnlineListActivity);
                                break;
                        }
                        return false;
                    }
                };
            case "HelpActivity":
                helpActivity = activities.getHelp();
                return new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.groups_menu:
                                Intent toGroupListActivity = new Intent(helpActivity, GroupListActivity.class);
                                helpActivity.startActivity(toGroupListActivity);
                                break;
                            case R.id.rodas_menu:
                                Intent toRodaListActivity = new Intent(helpActivity, RodaListActivity.class);
                                helpActivity.startActivity(toRodaListActivity);
                                break;
                            case R.id.events_menu:
                                Intent toEventListActivity = new Intent(helpActivity, EventListActivity.class);
                                helpActivity.startActivity(toEventListActivity);
                                break;
                            case R.id.onlines_menu:
                                Intent toOnlineListActivity = new Intent(helpActivity, OnlineListActivity.class);
                                helpActivity.startActivity(toOnlineListActivity);
                                break;
                        }
                        return false;
                    }
                };
            case "NewsActivity":
                newsActivity = activities.getNews();
                return new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.groups_menu:
                                Intent toGroupListActivity = new Intent(newsActivity, GroupListActivity.class);
                                newsActivity.startActivity(toGroupListActivity);
                                break;
                            case R.id.rodas_menu:
                                Intent toRodaListActivity = new Intent(newsActivity, RodaListActivity.class);
                                newsActivity.startActivity(toRodaListActivity);
                                break;
                            case R.id.events_menu:
                                Intent toEventListActivity = new Intent(newsActivity, EventListActivity.class);
                                newsActivity.startActivity(toEventListActivity);
                                break;
                            case R.id.onlines_menu:
                                Intent toOnlineListActivity = new Intent(newsActivity, OnlineListActivity.class);
                                newsActivity.startActivity(toOnlineListActivity);
                                break;
                        }
                        return false;
                    }
                };
            case "LoginActivity":
                loginActivity = activities.getLogin();
                return new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.groups_menu:
                                Intent toGroupListActivity = new Intent(loginActivity, GroupListActivity.class);
                                loginActivity.startActivity(toGroupListActivity);
                                loginActivity.finish();
                                break;
                            case R.id.rodas_menu:
                                Intent toRodaListActivity = new Intent(loginActivity, RodaListActivity.class);
                                loginActivity.startActivity(toRodaListActivity);
                                loginActivity.finish();
                                break;
                            case R.id.events_menu:
                                Intent toEventListActivity = new Intent(loginActivity, EventListActivity.class);
                                loginActivity.startActivity(toEventListActivity);
                                loginActivity.finish();
                                break;
                            case R.id.onlines_menu:
                                Intent toOnlineListActivity = new Intent(loginActivity, OnlineListActivity.class);
                                loginActivity.startActivity(toOnlineListActivity);
                                loginActivity.finish();
                                break;
                        }
                        return false;
                    }
                };
            case "SignUpActivity":
                signUpActivity = activities.getSignUp();
                return new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.groups_menu:
                                Intent toGroupListActivity = new Intent(signUpActivity, GroupListActivity.class);
                                signUpActivity.startActivity(toGroupListActivity);
                                signUpActivity.finish();
                                break;
                            case R.id.rodas_menu:
                                Intent toRodaListActivity = new Intent(signUpActivity, RodaListActivity.class);
                                signUpActivity.startActivity(toRodaListActivity);
                                signUpActivity.finish();
                                break;
                            case R.id.events_menu:
                                Intent toEventListActivity = new Intent(signUpActivity, EventListActivity.class);
                                signUpActivity.startActivity(toEventListActivity);
                                signUpActivity.finish();
                                break;
                            case R.id.onlines_menu:
                                Intent toOnlineListActivity = new Intent(signUpActivity, OnlineListActivity.class);
                                signUpActivity.startActivity(toOnlineListActivity);
                                signUpActivity.finish();
                                break;
                        }
                        return false;
                    }
                };
            default: break;
        }
        return null;
    }
    public static MaterialToolbar.OnMenuItemClickListener TopNavigationMenuHandler(String origin, NavParams activities) {
        GroupListActivity groupListActivity;
        GroupDetailActivity groupDetailActivity;
        GroupDetailMoreActivity groupDetailMoreActivity;
        GroupModificationActivity groupModificationActivity;
        EventListActivity eventListActivity;
        EventDetailActivity eventDetailActivity;
        EventDetailMoreActivity eventDetailMoreActivity;
        EventModificationActivity eventModificationActivity;
        RodaListActivity rodaListActivity;
        RodaDetailActivity rodaDetailActivity;
        RodaDetailMoreActivity rodaDetailMoreActivity;
        RodaModificationActivity rodaModificationActivity;
        OnlineListActivity onlineListActivity;
        OnlineDetailActivity onlineDetailActivity;
        OnlineDetailMoreActivity onlineDetailMoreActivity;
        OnlineModificationActivity onlineModificationActivity;
        UserDetailActivity userDetailActivity;
        ProfileModificationActivity profileModificationActivity;
        SearchActivity searchActivity;
        NewsActivity newsActivity;
        HelpActivity helpActivity;
        LoginActivity loginActivity;
        SignUpActivity signUpActivity;
        switch (origin) {
            case "GroupListActivity":
                groupListActivity = activities.groupList;
                return new MaterialToolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (CommonMethods.AmILogged()) {
                            switch (item.getItemId()) {
                                case R.id.login:
                                    Intent goToUserProfileActivity = new Intent(groupListActivity, UserDetailActivity.class);
                                    goToUserProfileActivity.putExtra("id", SharedPreferencesManager.getIntegerValue(Constants.ID));
                                    groupListActivity.startActivity(goToUserProfileActivity);
                                    break;
                                case R.id.search:
                                    Intent goToSearchActivity = new Intent(groupListActivity, SearchActivity.class);
                                    groupListActivity.startActivity(goToSearchActivity);
                                    break;
                                case R.id.news:
                                    Intent goToNewsActivity = new Intent(groupListActivity, NewsActivity.class);
                                    groupListActivity.startActivity(goToNewsActivity);
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            Intent goToLoginActivity = new Intent(groupListActivity, LoginActivity.class);
                            groupListActivity.startActivity(goToLoginActivity);
                        }
                        return false;
                    }
                };
            case "GroupDetailActivity":
                groupDetailActivity = activities.getGroupDetail();
                return new MaterialToolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (CommonMethods.AmILogged()) {
                            switch (item.getItemId()) {
                                case R.id.login:
                                    Intent goToUserProfileActivity = new Intent(groupDetailActivity, UserDetailActivity.class);
                                    goToUserProfileActivity.putExtra("id", SharedPreferencesManager.getIntegerValue(Constants.ID));
                                    groupDetailActivity.startActivity(goToUserProfileActivity);
                                    break;
                                case R.id.search:
                                    Intent goToSearchActivity = new Intent(groupDetailActivity, SearchActivity.class);
                                    groupDetailActivity.startActivity(goToSearchActivity);
                                    break;
                                case R.id.news:
                                    Intent goToNewsActivity = new Intent(groupDetailActivity, NewsActivity.class);
                                    groupDetailActivity.startActivity(goToNewsActivity);
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            Intent goToLoginActivity = new Intent(groupDetailActivity, LoginActivity.class);
                            groupDetailActivity.startActivity(goToLoginActivity);
                        }
                        return false;
                    }
                };
            case "GroupModificationActivity":
                groupModificationActivity = activities.getGroupModification();
                return new MaterialToolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (CommonMethods.AmILogged()) {
                            switch (item.getItemId()) {
                                case R.id.login:
                                    Intent goToUserProfileActivity = new Intent(groupModificationActivity, UserDetailActivity.class);
                                    goToUserProfileActivity.putExtra("id", SharedPreferencesManager.getIntegerValue(Constants.ID));
                                    groupModificationActivity.startActivity(goToUserProfileActivity);
                                    break;
                                case R.id.search:
                                    Intent goToSearchActivity = new Intent(groupModificationActivity, SearchActivity.class);
                                    groupModificationActivity.startActivity(goToSearchActivity);
                                    break;
                                case R.id.news:
                                    Intent goToNewsActivity = new Intent(groupModificationActivity, NewsActivity.class);
                                    groupModificationActivity.startActivity(goToNewsActivity);
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            Intent goToLoginActivity = new Intent(groupModificationActivity, LoginActivity.class);
                            groupModificationActivity.startActivity(goToLoginActivity);
                        }
                        return false;
                    }
                };
            case "GroupDetailMoreActivity":
                groupDetailMoreActivity = activities.getGroupDetailMore();
                return new MaterialToolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (CommonMethods.AmILogged()) {
                            switch (item.getItemId()) {
                                case R.id.login:
                                    Intent goToUserProfileActivity = new Intent(groupDetailMoreActivity, UserDetailActivity.class);
                                    goToUserProfileActivity.putExtra("id", SharedPreferencesManager.getIntegerValue(Constants.ID));
                                    groupDetailMoreActivity.startActivity(goToUserProfileActivity);
                                    break;
                                case R.id.search:
                                    Intent goToSearchActivity = new Intent(groupDetailMoreActivity, SearchActivity.class);
                                    groupDetailMoreActivity.startActivity(goToSearchActivity);
                                    break;
                                case R.id.news:
                                    Intent goToNewsActivity = new Intent(groupDetailMoreActivity, NewsActivity.class);
                                    groupDetailMoreActivity.startActivity(goToNewsActivity);
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            Intent goToLoginActivity = new Intent(groupDetailMoreActivity, LoginActivity.class);
                            groupDetailMoreActivity.startActivity(goToLoginActivity);
                        }
                        return false;
                    }
                };
            case "EventListActivity":
                eventListActivity = activities.getEventList();
                return new MaterialToolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (CommonMethods.AmILogged()) {
                            switch (item.getItemId()) {
                                case R.id.login:
                                    Intent goToUserProfileActivity = new Intent(eventListActivity, UserDetailActivity.class);
                                    goToUserProfileActivity.putExtra("id", SharedPreferencesManager.getIntegerValue(Constants.ID));
                                    eventListActivity.startActivity(goToUserProfileActivity);
                                    break;
                                case R.id.search:
                                    Intent goToSearchActivity = new Intent(eventListActivity, SearchActivity.class);
                                    eventListActivity.startActivity(goToSearchActivity);
                                    break;
                                case R.id.news:
                                    Intent goToNewsActivity = new Intent(eventListActivity, NewsActivity.class);
                                    eventListActivity.startActivity(goToNewsActivity);
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            Intent goToLoginActivity = new Intent(eventListActivity, LoginActivity.class);
                            eventListActivity.startActivity(goToLoginActivity);
                        }
                        return false;
                    }
                };
            case "EventDetailActivity":
                eventDetailActivity = activities.getEventDetail();
                return new MaterialToolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (CommonMethods.AmILogged()) {
                            switch (item.getItemId()) {
                                case R.id.login:
                                    Intent goToUserProfileActivity = new Intent(eventDetailActivity, UserDetailActivity.class);
                                    goToUserProfileActivity.putExtra("id", SharedPreferencesManager.getIntegerValue(Constants.ID));
                                    eventDetailActivity.startActivity(goToUserProfileActivity);
                                    break;
                                case R.id.search:
                                    Intent goToSearchActivity = new Intent(eventDetailActivity, SearchActivity.class);
                                    eventDetailActivity.startActivity(goToSearchActivity);
                                    break;
                                case R.id.news:
                                    Intent goToNewsActivity = new Intent(eventDetailActivity, NewsActivity.class);
                                    eventDetailActivity.startActivity(goToNewsActivity);
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            Intent goToLoginActivity = new Intent(eventDetailActivity, LoginActivity.class);
                            eventDetailActivity.startActivity(goToLoginActivity);
                        }
                        return false;
                    }
                };
            case "EventDetailMoreActivity":
                eventDetailMoreActivity = activities.getEventDetailMore();
                return new MaterialToolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (CommonMethods.AmILogged()) {
                            switch (item.getItemId()) {
                                case R.id.login:
                                    Intent goToUserProfileActivity = new Intent(eventDetailMoreActivity, UserDetailActivity.class);
                                    goToUserProfileActivity.putExtra("id", SharedPreferencesManager.getIntegerValue(Constants.ID));
                                    eventDetailMoreActivity.startActivity(goToUserProfileActivity);
                                    break;
                                case R.id.search:
                                    Intent goToSearchActivity = new Intent(eventDetailMoreActivity, SearchActivity.class);
                                    eventDetailMoreActivity.startActivity(goToSearchActivity);
                                    break;
                                case R.id.news:
                                    Intent goToNewsActivity = new Intent(eventDetailMoreActivity, NewsActivity.class);
                                    eventDetailMoreActivity.startActivity(goToNewsActivity);
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            Intent goToLoginActivity = new Intent(eventDetailMoreActivity, LoginActivity.class);
                            eventDetailMoreActivity.startActivity(goToLoginActivity);
                        }
                        return false;
                    }
                };
            case "EventModificationActivity":
                eventModificationActivity = activities.getEventModification();
                return new MaterialToolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (CommonMethods.AmILogged()) {
                            switch (item.getItemId()) {
                                case R.id.login:
                                    Intent goToUserProfileActivity = new Intent(eventModificationActivity, UserDetailActivity.class);
                                    goToUserProfileActivity.putExtra("id", SharedPreferencesManager.getIntegerValue(Constants.ID));
                                    eventModificationActivity.startActivity(goToUserProfileActivity);
                                    break;
                                case R.id.search:
                                    Intent goToSearchActivity = new Intent(eventModificationActivity, SearchActivity.class);
                                    eventModificationActivity.startActivity(goToSearchActivity);
                                    break;
                                case R.id.news:
                                    Intent goToNewsActivity = new Intent(eventModificationActivity, NewsActivity.class);
                                    eventModificationActivity.startActivity(goToNewsActivity);
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            Intent goToLoginActivity = new Intent(eventModificationActivity, LoginActivity.class);
                            eventModificationActivity.startActivity(goToLoginActivity);
                        }
                        return false;
                    }
                };
            case "UserDetailActivity":
                userDetailActivity = activities.getUserDetail();
                return new MaterialToolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (CommonMethods.AmILogged()) {
                            switch (item.getItemId()) {
                                case R.id.login:
                                    Intent goToUserProfileActivity = new Intent(userDetailActivity, UserDetailActivity.class);
                                    goToUserProfileActivity.putExtra("id", SharedPreferencesManager.getIntegerValue(Constants.ID));
                                    userDetailActivity.startActivity(goToUserProfileActivity);
                                    break;
                                case R.id.search:
                                    Intent goToSearchActivity = new Intent(userDetailActivity, SearchActivity.class);
                                    userDetailActivity.startActivity(goToSearchActivity);
                                    break;
                                case R.id.news:
                                    Intent goToNewsActivity = new Intent(userDetailActivity, NewsActivity.class);
                                    userDetailActivity.startActivity(goToNewsActivity);
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            Intent goToLoginActivity = new Intent(userDetailActivity, LoginActivity.class);
                            userDetailActivity.startActivity(goToLoginActivity);
                        }
                        return false;
                    }
                };
            case "ProfileModificationActivity":
                profileModificationActivity = activities.getProfileModification();
                return new MaterialToolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (CommonMethods.AmILogged()) {
                            switch (item.getItemId()) {
                                case R.id.login:
                                    Intent goToUserProfileActivity = new Intent(profileModificationActivity, UserDetailActivity.class);
                                    goToUserProfileActivity.putExtra("id", SharedPreferencesManager.getIntegerValue(Constants.ID));
                                    profileModificationActivity.startActivity(goToUserProfileActivity);
                                    break;
                                case R.id.search:
                                    Intent goToSearchActivity = new Intent(profileModificationActivity, SearchActivity.class);
                                    profileModificationActivity.startActivity(goToSearchActivity);
                                    break;
                                case R.id.news:
                                    Intent goToNewsActivity = new Intent(profileModificationActivity, NewsActivity.class);
                                    profileModificationActivity.startActivity(goToNewsActivity);
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            Intent goToLoginActivity = new Intent(profileModificationActivity, LoginActivity.class);
                            profileModificationActivity.startActivity(goToLoginActivity);
                        }
                        return false;
                    }
                };
            case "RodaListActivity":
                rodaListActivity = activities.getRodaList();
                return new MaterialToolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (CommonMethods.AmILogged()) {
                            switch (item.getItemId()) {
                                case R.id.login:
                                    Intent goToUserProfileActivity = new Intent(rodaListActivity, UserDetailActivity.class);
                                    goToUserProfileActivity.putExtra("id", SharedPreferencesManager.getIntegerValue(Constants.ID));
                                    rodaListActivity.startActivity(goToUserProfileActivity);
                                    break;
                                case R.id.search:
                                    Intent goToSearchActivity = new Intent(rodaListActivity, SearchActivity.class);
                                    rodaListActivity.startActivity(goToSearchActivity);
                                    break;
                                case R.id.news:
                                    Intent goToNewsActivity = new Intent(rodaListActivity, NewsActivity.class);
                                    rodaListActivity.startActivity(goToNewsActivity);
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            Intent goToLoginActivity = new Intent(rodaListActivity, LoginActivity.class);
                            rodaListActivity.startActivity(goToLoginActivity);
                        }
                        return false;
                    }
                };
            case "RodaDetailActivity":
                rodaDetailActivity = activities.getRodaDetail();
                return new MaterialToolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (CommonMethods.AmILogged()) {
                            switch (item.getItemId()) {
                                case R.id.login:
                                    Intent goToUserProfileActivity = new Intent(rodaDetailActivity, UserDetailActivity.class);
                                    goToUserProfileActivity.putExtra("id", SharedPreferencesManager.getIntegerValue(Constants.ID));
                                    rodaDetailActivity.startActivity(goToUserProfileActivity);
                                    break;
                                case R.id.search:
                                    Intent goToSearchActivity = new Intent(rodaDetailActivity, SearchActivity.class);
                                    rodaDetailActivity.startActivity(goToSearchActivity);
                                    break;
                                case R.id.news:
                                    Intent goToNewsActivity = new Intent(rodaDetailActivity, NewsActivity.class);
                                    rodaDetailActivity.startActivity(goToNewsActivity);
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            Intent goToLoginActivity = new Intent(rodaDetailActivity, LoginActivity.class);
                            rodaDetailActivity.startActivity(goToLoginActivity);
                        }
                        return false;
                    }
                };
            case "RodaDetailMoreActivity":
                rodaDetailMoreActivity = activities.getRodaDetailMore();
                return new MaterialToolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (CommonMethods.AmILogged()) {
                            switch (item.getItemId()) {
                                case R.id.login:
                                    Intent goToUserProfileActivity = new Intent(rodaDetailMoreActivity, UserDetailActivity.class);
                                    goToUserProfileActivity.putExtra("id", SharedPreferencesManager.getIntegerValue(Constants.ID));
                                    rodaDetailMoreActivity.startActivity(goToUserProfileActivity);
                                    break;
                                case R.id.search:
                                    Intent goToSearchActivity = new Intent(rodaDetailMoreActivity, SearchActivity.class);
                                    rodaDetailMoreActivity.startActivity(goToSearchActivity);
                                    break;
                                case R.id.news:
                                    Intent goToNewsActivity = new Intent(rodaDetailMoreActivity, NewsActivity.class);
                                    rodaDetailMoreActivity.startActivity(goToNewsActivity);
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            Intent goToLoginActivity = new Intent(rodaDetailMoreActivity, LoginActivity.class);
                            rodaDetailMoreActivity.startActivity(goToLoginActivity);
                        }
                        return false;
                    }
                };
            case "RodaModificationActivity":
                rodaModificationActivity = activities.getRodaModification();
                return new MaterialToolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (CommonMethods.AmILogged()) {
                            switch (item.getItemId()) {
                                case R.id.login:
                                    Intent goToUserProfileActivity = new Intent(rodaModificationActivity, UserDetailActivity.class);
                                    goToUserProfileActivity.putExtra("id", SharedPreferencesManager.getIntegerValue(Constants.ID));
                                    rodaModificationActivity.startActivity(goToUserProfileActivity);
                                    break;
                                case R.id.search:
                                    Intent goToSearchActivity = new Intent(rodaModificationActivity, SearchActivity.class);
                                    rodaModificationActivity.startActivity(goToSearchActivity);
                                    break;
                                case R.id.news:
                                    Intent goToNewsActivity = new Intent(rodaModificationActivity, NewsActivity.class);
                                    rodaModificationActivity.startActivity(goToNewsActivity);
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            Intent goToLoginActivity = new Intent(rodaModificationActivity, LoginActivity.class);
                            rodaModificationActivity.startActivity(goToLoginActivity);
                        }
                        return false;
                    }
                };
            case "OnlineListActivity":
                onlineListActivity = activities.getOnlineList();
                return new MaterialToolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (CommonMethods.AmILogged()) {
                            switch (item.getItemId()) {
                                case R.id.login:
                                    Intent goToUserProfileActivity = new Intent(onlineListActivity, UserDetailActivity.class);
                                    goToUserProfileActivity.putExtra("id", SharedPreferencesManager.getIntegerValue(Constants.ID));
                                    onlineListActivity.startActivity(goToUserProfileActivity);
                                    break;
                                case R.id.search:
                                    Intent goToSearchActivity = new Intent(onlineListActivity, SearchActivity.class);
                                    onlineListActivity.startActivity(goToSearchActivity);
                                    break;
                                case R.id.news:
                                    Intent goToNewsActivity = new Intent(onlineListActivity, NewsActivity.class);
                                    onlineListActivity.startActivity(goToNewsActivity);
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            Intent goToLoginActivity = new Intent(onlineListActivity, LoginActivity.class);
                            onlineListActivity.startActivity(goToLoginActivity);
                        }
                        return false;
                    }
                };
            case "OnlineDetailActivity":
                onlineDetailActivity = activities.getOnlineDetail();
                return new MaterialToolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (CommonMethods.AmILogged()) {
                            switch (item.getItemId()) {
                                case R.id.login:
                                    Intent goToUserProfileActivity = new Intent(onlineDetailActivity, UserDetailActivity.class);
                                    goToUserProfileActivity.putExtra("id", SharedPreferencesManager.getIntegerValue(Constants.ID));
                                    onlineDetailActivity.startActivity(goToUserProfileActivity);
                                    break;
                                case R.id.search:
                                    Intent goToSearchActivity = new Intent(onlineDetailActivity, SearchActivity.class);
                                    onlineDetailActivity.startActivity(goToSearchActivity);
                                    break;
                                case R.id.news:
                                    Intent goToNewsActivity = new Intent(onlineDetailActivity, NewsActivity.class);
                                    onlineDetailActivity.startActivity(goToNewsActivity);
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            Intent goToLoginActivity = new Intent(onlineDetailActivity, LoginActivity.class);
                            onlineDetailActivity.startActivity(goToLoginActivity);
                        }
                        return false;
                    }
                };
            case "OnlineDetailMoreActivity":
                onlineDetailMoreActivity = activities.getOnlineDetailMore();
                return new MaterialToolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (CommonMethods.AmILogged()) {
                            switch (item.getItemId()) {
                                case R.id.login:
                                    Intent goToUserProfileActivity = new Intent(onlineDetailMoreActivity, UserDetailActivity.class);
                                    goToUserProfileActivity.putExtra("id", SharedPreferencesManager.getIntegerValue(Constants.ID));
                                    onlineDetailMoreActivity.startActivity(goToUserProfileActivity);
                                    break;
                                case R.id.search:
                                    Intent goToSearchActivity = new Intent(onlineDetailMoreActivity, SearchActivity.class);
                                    onlineDetailMoreActivity.startActivity(goToSearchActivity);
                                    break;
                                case R.id.news:
                                    Intent goToNewsActivity = new Intent(onlineDetailMoreActivity, NewsActivity.class);
                                    onlineDetailMoreActivity.startActivity(goToNewsActivity);
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            Intent goToLoginActivity = new Intent(onlineDetailMoreActivity, LoginActivity.class);
                            onlineDetailMoreActivity.startActivity(goToLoginActivity);
                        }
                        return false;
                    }
                };
            case "OnlineModificationActivity":
                onlineModificationActivity = activities.getOnlineModification();
                return new MaterialToolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (CommonMethods.AmILogged()) {
                            switch (item.getItemId()) {
                                case R.id.login:
                                    Intent goToUserProfileActivity = new Intent(onlineModificationActivity, UserDetailActivity.class);
                                    goToUserProfileActivity.putExtra("id", SharedPreferencesManager.getIntegerValue(Constants.ID));
                                    onlineModificationActivity.startActivity(goToUserProfileActivity);
                                    break;
                                case R.id.search:
                                    Intent goToSearchActivity = new Intent(onlineModificationActivity, SearchActivity.class);
                                    onlineModificationActivity.startActivity(goToSearchActivity);
                                    break;
                                case R.id.news:
                                    Intent goToNewsActivity = new Intent(onlineModificationActivity, NewsActivity.class);
                                    onlineModificationActivity.startActivity(goToNewsActivity);
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            Intent goToLoginActivity = new Intent(onlineModificationActivity, LoginActivity.class);
                            onlineModificationActivity.startActivity(goToLoginActivity);
                        }
                        return false;
                    }
                };
            case "SearchActivity":
                searchActivity = activities.getSearch();
                return new MaterialToolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (CommonMethods.AmILogged()) {
                            switch (item.getItemId()) {
                                case R.id.login:
                                    Intent goToUserProfileActivity = new Intent(searchActivity, UserDetailActivity.class);
                                    goToUserProfileActivity.putExtra("id", SharedPreferencesManager.getIntegerValue(Constants.ID));
                                    searchActivity.startActivity(goToUserProfileActivity);
                                    break;
                                case R.id.news:
                                    Intent goToNewsActivity = new Intent(searchActivity, NewsActivity.class);
                                    searchActivity.startActivity(goToNewsActivity);
                                    break;
                                case R.id.search:
                                default:
                                    break;
                            }
                        } else {
                            Intent goToLoginActivity = new Intent(searchActivity, LoginActivity.class);
                            searchActivity.startActivity(goToLoginActivity);
                        }
                        return false;
                    }
                };
            case "NewsActivity":
                newsActivity = activities.getNews();
                return new MaterialToolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (CommonMethods.AmILogged()) {
                            switch (item.getItemId()) {
                                case R.id.login:
                                    Intent goToUserProfileActivity = new Intent(newsActivity, UserDetailActivity.class);
                                    goToUserProfileActivity.putExtra("id", SharedPreferencesManager.getIntegerValue(Constants.ID));
                                    newsActivity.startActivity(goToUserProfileActivity);
                                    break;
                                case R.id.search:
                                    Intent goToSearchActivity = new Intent(newsActivity, SearchActivity.class);
                                    newsActivity.startActivity(goToSearchActivity);
                                    break;
                                case R.id.news:
                                default:
                                    break;
                            }
                        } else {
                            Intent goToLoginActivity = new Intent(newsActivity, LoginActivity.class);
                            newsActivity.startActivity(goToLoginActivity);
                        }
                        return false;
                    }
                };
            case "HelpActivity":
                helpActivity = activities.getHelp();
                return new MaterialToolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (CommonMethods.AmILogged()) {
                            switch (item.getItemId()) {
                                case R.id.login:
                                    Intent goToUserProfileActivity = new Intent(helpActivity, UserDetailActivity.class);
                                    goToUserProfileActivity.putExtra("id", SharedPreferencesManager.getIntegerValue(Constants.ID));
                                    helpActivity.startActivity(goToUserProfileActivity);
                                    break;
                                case R.id.search:
                                    Intent goToSearchActivity = new Intent(helpActivity, SearchActivity.class);
                                    helpActivity.startActivity(goToSearchActivity);
                                    break;
                                case R.id.news:
                                    Intent goToNewsActivity = new Intent(helpActivity, NewsActivity.class);
                                    helpActivity.startActivity(goToNewsActivity);
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            Intent goToLoginActivity = new Intent(helpActivity, LoginActivity.class);
                            helpActivity.startActivity(goToLoginActivity);
                        }
                        return false;
                    }
                };
            case "LoginActivity":
            case "SignUpActivity":
                return new MaterialToolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return false;
                    }
                };
            default:
                System.exit(0);
        }
        return null;
    }

    public static Target GetTarGetForAvatar(ActionMenuItemView ivTopMenuLogin){
        return new Target() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Bitmap b = Bitmap.createScaledBitmap(bitmap, 120, 120, false);
                BitmapDrawable icon = new BitmapDrawable(ivTopMenuLogin.getResources(), b);
                ivTopMenuLogin.setIcon(icon);
            }
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };
    }
    public static Target GetTarGetForAvatarImage(ImageView ivAvatar){
        return new Target() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Bitmap b = Bitmap.createScaledBitmap(bitmap, 120, 120, false);
                BitmapDrawable icon = new BitmapDrawable(ivAvatar.getResources(), b);
                ivAvatar.setImageDrawable(icon);
            }
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };
    }
    public static List<ImageView> SetStars(Double rating, ImageView ivGroupStar1, ImageView ivGroupStar2, ImageView ivGroupStar3, ImageView ivGroupStar4, ImageView ivGroupStar5) {
        ivGroupStar1.setImageResource(R.drawable.ic_star_void);
        ivGroupStar2.setImageResource(R.drawable.ic_star_void);
        ivGroupStar3.setImageResource(R.drawable.ic_star_void);
        ivGroupStar4.setImageResource(R.drawable.ic_star_void);
        ivGroupStar5.setImageResource(R.drawable.ic_star_void);
        if (rating >= 0.5 && rating < 1.0) {
            ivGroupStar1.setImageResource(R.drawable.ic_star_half);
        } else if (rating >= 1.0 && rating < 1.5) {
            ivGroupStar1.setImageResource(R.drawable.ic_star);
        } else if (rating >= 1.5 && rating < 2.0) {
            ivGroupStar1.setImageResource(R.drawable.ic_star);
            ivGroupStar2.setImageResource(R.drawable.ic_star_half);
        } else if (rating >= 2.0 && rating < 2.5) {
            ivGroupStar1.setImageResource(R.drawable.ic_star);
            ivGroupStar2.setImageResource(R.drawable.ic_star);
        } else if (rating >= 2.5 && rating < 3.0) {
            ivGroupStar1.setImageResource(R.drawable.ic_star);
            ivGroupStar2.setImageResource(R.drawable.ic_star);
            ivGroupStar3.setImageResource(R.drawable.ic_star_half);
        } else if (rating >= 3.0 && rating < 3.5) {
            ivGroupStar1.setImageResource(R.drawable.ic_star);
            ivGroupStar2.setImageResource(R.drawable.ic_star);
            ivGroupStar3.setImageResource(R.drawable.ic_star);
        } else if (rating >= 3.5 && rating < 4.0) {
            ivGroupStar1.setImageResource(R.drawable.ic_star);
            ivGroupStar2.setImageResource(R.drawable.ic_star);
            ivGroupStar3.setImageResource(R.drawable.ic_star);
            ivGroupStar4.setImageResource(R.drawable.ic_star_half);
        } else if (rating >= 4.0 && rating < 4.5) {
            ivGroupStar1.setImageResource(R.drawable.ic_star);
            ivGroupStar2.setImageResource(R.drawable.ic_star);
            ivGroupStar3.setImageResource(R.drawable.ic_star);
            ivGroupStar4.setImageResource(R.drawable.ic_star);
        } else if (rating >= 4.5 && rating < 5.0) {
            ivGroupStar1.setImageResource(R.drawable.ic_star);
            ivGroupStar2.setImageResource(R.drawable.ic_star);
            ivGroupStar3.setImageResource(R.drawable.ic_star);
            ivGroupStar4.setImageResource(R.drawable.ic_star);
            ivGroupStar5.setImageResource(R.drawable.ic_star_half);
        } else if (rating == 5.0) {
            ivGroupStar1.setImageResource(R.drawable.ic_star);
            ivGroupStar2.setImageResource(R.drawable.ic_star);
            ivGroupStar3.setImageResource(R.drawable.ic_star);
            ivGroupStar4.setImageResource(R.drawable.ic_star);
            ivGroupStar5.setImageResource(R.drawable.ic_star);
        }
        List<ImageView> stars = new ArrayList<>();
        stars.add(ivGroupStar1);
        stars.add(ivGroupStar2);
        stars.add(ivGroupStar3);
        stars.add(ivGroupStar4);
        stars.add(ivGroupStar5);
        return stars;
    }
    public static MultipartBody.Part fromBitmapToFile(Context ctx, Bitmap bitmapImage, String objectType, String objectSubType, Integer typeId, Integer subTypeId) throws IOException {
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy-HH:mm:ss", Locale.getDefault());
        String formattedDate = df.format(Calendar.getInstance().getTime());
        String auxSubTypeId;
        if (subTypeId == 0) {
            auxSubTypeId = "";
        } else {
            auxSubTypeId = String.valueOf(subTypeId);
        }
        File f = new File(ctx.getCacheDir(), objectType + "_" + objectSubType + "_" + typeId + "_" + auxSubTypeId + "_" + formattedDate);
        f.createNewFile();
        //Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bitmapdata = bos.toByteArray();

        //write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            assert fos != null;
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), f);
        return MultipartBody.Part.createFormData("upload", f.getName(), reqFile);
    }
}

