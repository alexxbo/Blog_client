package com.alexander_borovskoy.blogclient.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.alexander_borovskoy.blogclient.R;
import com.alexander_borovskoy.blogclient.app.App;
import com.alexander_borovskoy.blogclient.databinding.ActivityMainBinding;
import com.alexander_borovskoy.blogclient.ui.postlist.PostListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);

        App.getInstance().getComponentsHolder().getDataSourceComponent();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .addToBackStack(PostListFragment.TAG)
                    .replace(R.id.container, PostListFragment.newInstance())
                    .commit();
        }
    }

    public void replaceFragment(Fragment fragment, String tag) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);

        if (currentFragment.getClass() == fragment.getClass()) {
            return;
        }

        if (getSupportFragmentManager().findFragmentByTag(tag) != null) {
            getSupportFragmentManager().popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(tag)
                .replace(R.id.container, fragment, tag)
                .commit();
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
