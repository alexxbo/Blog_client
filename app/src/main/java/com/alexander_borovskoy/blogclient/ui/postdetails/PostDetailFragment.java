package com.alexander_borovskoy.blogclient.ui.postdetails;

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
import com.alexander_borovskoy.blogclient.app.App;
import com.alexander_borovskoy.blogclient.data.Comment;
import com.alexander_borovskoy.blogclient.data.Mark;
import com.alexander_borovskoy.blogclient.data.Post;
import com.alexander_borovskoy.blogclient.data.source.PostsDataSource;
import com.alexander_borovskoy.blogclient.databinding.FragmentPostDetailBinding;

import java.util.List;

import javax.inject.Inject;

public class PostDetailFragment extends Fragment implements PostDetailContract.View {
    private static final String ARG_POST_ID = "post id";
    public static final String TAG = "PostDetailFragment";

    private long mPostId;
    private FragmentPostDetailBinding mBinding;
    @Inject
    CommentAdapter mCommentAdapter;
    @Inject
    PostsDataSource mRepository;
    @Inject
    PostDetailContract.Presenter mPresenter;

    public PostDetailFragment() {
    }

    public static PostDetailFragment newInstance(long postId) {
        PostDetailFragment fragment = new PostDetailFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_POST_ID, postId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPostId = getArguments().getLong(ARG_POST_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_post_detail, container, false);

        App.getInstance()
                .getComponentsHolder()
                .getPostDetailComponent(this, mPostId)
                .inject(this);

        mBinding.commentsRecycler.setAdapter(mCommentAdapter);
        mPresenter.onViewCreated();
        mBinding.postCommentsSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.updatePostComments();
            }
        });

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        // TODO: 16.08.2018 implements method setLoadingIndicator
        mBinding.postCommentsSwipeRefreshLayout.setRefreshing(active);
        mBinding.commentsRecycler.setVisibility(View.GONE);
    }

    @Override
    public void showPost(Post post) {
        mBinding.tvTitle.setText(post.getTitle());
        mBinding.tvDate.setText(post.getDatePublic());
        mBinding.tvBody.setText(post.getText());
    }

    @Override
    public void showPostMarks(List<Mark> marks) {
        for (Mark mark : marks) {
            mBinding.tvMark.append(mark.getName() + "  ");
        }
    }

    @Override
    public void showPostComments(List<Comment> comments) {
        mBinding.commentsRecycler.setVisibility(View.VISIBLE);
        mCommentAdapter.setCommentList(comments);
    }

    @Override
    public void showLoadingPostError() {
        showMessage(getString(R.string.loading_error));
    }

    @Override
    public void showLoadingPostCommentsError() {
        if (this.isDetached()) {
            showMessage(getString(R.string.loading_error));
        }
    }

    private void showMessage(String message) {
        Snackbar.make(mBinding.commentsRecycler, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showNoComments() {
        mBinding.noComments.setVisibility(View.VISIBLE);
        mBinding.commentsRecycler.setVisibility(View.GONE);
    }

    @Override
    public void setPresenter(@NonNull PostDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onViewDestroyed();
        App.getInstance().getComponentsHolder().releasePostDetailComponent();
    }
}
