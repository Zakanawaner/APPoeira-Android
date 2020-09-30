package com.onethousandprojects.appoeira.getPermissionsView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.groupListView.GroupListActivity;

public class GetPermissionsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }
    public void grantPermissions(View view) {
        Intent goToGroupList = new Intent(this, GroupListActivity.class);
        goToGroupList.putExtra("allow", true);
        startActivity(goToGroupList);
        finish();
    }
}