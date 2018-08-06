package com.alexander_borovskoy.blogclient.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alexander_borovskoy.blogclient.PostRepository;
import com.alexander_borovskoy.blogclient.R;
import com.alexander_borovskoy.blogclient.databinding.FragmentPostDetailBinding;
import com.alexander_borovskoy.blogclient.db.Comment;
import com.alexander_borovskoy.blogclient.db.Mark;
import com.alexander_borovskoy.blogclient.db.Post;
import com.alexander_borovskoy.blogclient.source.DataSource;

import java.util.List;

public class PostDetailFragment extends Fragment {
    private static final String ARG_POST_ID = "post id";
    public static final String TAG = "PostDetailFragment";

    private long mPostId;
    private FragmentPostDetailBinding mBinding;
    private CommentAdapter mCommentAdapter;

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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_detail, container, false);
        mCommentAdapter = new CommentAdapter();
        mBinding.commentsRecycler.setAdapter(mCommentAdapter);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        PostRepository repository = PostRepository.getInstance();
        repository.getPost(mPostId, new DataSource.GetPostsCallback() {
            @Override
            public void onPostsLoaded(Post post) {
                mBinding.tvTitle.setText(post.getTitle());
                mBinding.tvDate.setText(post.getDatePublic());
                mBinding.tvBody.setText(post.getText());
            }

            @Override
            public void onDataNotAvailable() {
                Toast.makeText(getContext(), "Post Not Available", Toast.LENGTH_SHORT).show();
            }
        });

        repository.getPostMarks(mPostId, new DataSource.LoadPostMarksCallback() {
            @Override
            public void onPostMarksLoaded(List<Mark> markList) {
                for (Mark mark : markList) {
                    mBinding.tvMark.append(mark.getName() + " ");
                }
            }

            @Override
            public void onDataNotAvailable() {
                Toast.makeText(getContext(), "Marks Not Available", Toast.LENGTH_SHORT).show();
            }
        });

        repository.getPostComments(mPostId, new DataSource.LoadPostCommentsCallback() {
            @Override
            public void onPostCommentsLoaded(List<Comment> commentList) {
                mCommentAdapter.setCommentList(commentList);
            }

            @Override
            public void onDataNotAvailable() {
                Toast.makeText(getContext(), "Comments Not Available", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
