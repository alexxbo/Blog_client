package com.alexander_borovskoy.blogclient.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.alexander_borovskoy.blogclient.R;
import com.alexander_borovskoy.blogclient.app.App;
import com.alexander_borovskoy.blogclient.databinding.ActivityMainBinding;
import com.alexander_borovskoy.blogclient.ui.postdetails.PostDetailFragment;
import com.alexander_borovskoy.blogclient.ui.postlist.PostListFragment;

public class MainActivity extends AppCompatActivity {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);

        App.getInstance().getComponentsHolder().getDataSourceComponent();

        if (binding.mainContent.detailContainer != null){
            mTwoPane = true;
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .addToBackStack(PostListFragment.TAG)
                    .replace(R.id.container, PostListFragment.newInstance())
                    .commit();
        }
    }

    public void replaceFragment(Fragment fragment, String tag) {

        if (mTwoPane){
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.detail_container, fragment, tag)
                .commit();
        } else {
            getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(tag)
                .replace(R.id.container, fragment, tag)
                .commit();
        }
    }

    @Override
    public void onBackPressed() {
        int fragmentsInStack = getSupportFragmentManager().getBackStackEntryCount();

        if (fragmentsInStack > 1) {
            getSupportFragmentManager().popBackStack();
        } else if (fragmentsInStack == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getInstance().getComponentsHolder().releaseDataSourceComponent();
    }
}
