package com.alexander_borovskoy.blogclient.ui.postlist;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexander_borovskoy.blogclient.R;
import com.alexander_borovskoy.blogclient.data.source.PostRepository;
import com.alexander_borovskoy.blogclient.databinding.FragmentPostListBinding;
import com.alexander_borovskoy.blogclient.data.Post;
import com.alexander_borovskoy.blogclient.ui.MainActivity;
import com.alexander_borovskoy.blogclient.ui.postdetails.PostDetailFragment;

import java.util.List;


public class PostListFragment extends Fragment implements PostListContract.View{
    public static final String TAG = "PostListFragment";
    private PostListContract.Presenter mPresenter;
    private PostAdapter mPostAdapter;
    private FragmentPostListBinding mBinding;
    private PostClickCallback mPostClickCallback = new PostClickCallback() {
        @Override
        public void onClick(Post post) {
            if (post != null) {
                mPresenter.openPostDetails(post);
            }
        }
    };
    private PostRepository mRepository;

    public PostListFragment() {
    }

    public static PostListFragment newInstance() {
        return new PostListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_list, container, false);
        mPostAdapter = new PostAdapter(mPostClickCallback);
        mBinding.postList.setAdapter(mPostAdapter);
        mRepository = PostRepository.getInstance();
        mPresenter = new PostsPresenter(mRepository, this);
        mPresenter.onViewCreated();
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mBinding.postListSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.updatePosts();
            }
        });
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        mBinding.postListSwipeRefreshLayout.setRefreshing(active);
        mBinding.postList.setVisibility(View.GONE);
    }

    @Override
    public void showPosts(List<Post> posts) {
        mPostAdapter.setPostList(posts);
        mBinding.postList.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPostDetails(Long postId) {
        if (getActivity() != null) {
            ((MainActivity) getActivity())
                    .replaceFragment(PostDetailFragment.newInstance(postId), PostDetailFragment.TAG);
        }
    }

    @Override
    public void showLoadingPostsError() {
        showMessage(getString(R.string.loading_error));
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showNoPosts() {
        mBinding.postList.setVisibility(View.GONE);
        mBinding.noPosts.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPresenter(@NonNull PostListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onViewDestroyed();
    }
}
