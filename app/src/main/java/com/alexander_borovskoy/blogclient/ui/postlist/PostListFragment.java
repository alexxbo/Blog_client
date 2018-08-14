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
import android.widget.Toast;

import com.alexander_borovskoy.blogclient.R;
import com.alexander_borovskoy.blogclient.data.source.PostRepository;
import com.alexander_borovskoy.blogclient.data.source.PostsDataSource;
import com.alexander_borovskoy.blogclient.databinding.FragmentPostListBinding;
import com.alexander_borovskoy.blogclient.data.Post;
import com.alexander_borovskoy.blogclient.ui.MainActivity;
import com.alexander_borovskoy.blogclient.ui.postdetails.PostDetailFragment;

import java.util.List;


public class PostListFragment extends Fragment implements PostListContract.View{
    public static final String TAG = "PostListFragment";
//    private static final String ARG_PARAM1 = "param1";

    //    private String mParam1;
    private PostListContract.Presenter mPresenter;
    private PostAdapter mPostAdapter;
    private FragmentPostListBinding mBinding;
    private PostClickCallback mPostClickCallback = new PostClickCallback() {
        @Override
        public void onClick(Post post) {
            if (post != null) {
//                ((MainActivity) getActivity()).replaceFragment(PostDetailFragment.newInstance(post.getId()), PostDetailFragment.TAG);
                mPresenter.openPostDetails(post);
            }
        }
    };
    private PostRepository mRepository;

    public PostListFragment() {
    }

    public static PostListFragment newInstance(/*String param1, String param2*/) {
        PostListFragment fragment = new PostListFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
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

//        mRepository = PostRepository.getInstance();
//        mRepository.getAllPosts(new PostsDataSource.LoadPostsCallback() {
//            @Override
//            public void onPostsLoaded(List<Post> postList) {
//                mPostAdapter.setPostList(postList);
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//                Toast.makeText(getContext(), "Data Not Available", Toast.LENGTH_SHORT).show();
//            }
//        });
        mBinding.postListSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
    }

    private void refreshContent() {
        mRepository.getAllPosts(new PostsDataSource.LoadPostsCallback() {
            @Override
            public void onPostsLoaded(List<Post> postList) {
                mBinding.postListSwipeRefreshLayout.setRefreshing(false);
                mPostAdapter.setPostList(postList);
            }

            @Override
            public void onDataNotAvailable() {
                mBinding.postListSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), "Data Not Available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        mBinding.loadProgress.setVisibility(View.VISIBLE);
        mBinding.postList.setVisibility(View.GONE);
    }

    @Override
    public void showPosts(List<Post> posts) {
        mPostAdapter.setPostList(posts);
        mBinding.loadProgress.setVisibility(View.GONE);
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
        showMessage(getString(R.string.loading_posts_error));
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showNoPosts() {
        mBinding.postList.setVisibility(View.GONE);
        mBinding.noPosts.setVisibility(View.VISIBLE);
        mBinding.tvNoPosts.setText(getResources().getString(R.string.no_posts_all));
        mBinding.noPostsIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_error_grey_700_36dp));
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
