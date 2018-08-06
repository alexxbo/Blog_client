package com.alexander_borovskoy.blogclient.ui;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alexander_borovskoy.blogclient.PostRepository;
import com.alexander_borovskoy.blogclient.R;
import com.alexander_borovskoy.blogclient.databinding.FragmentPostListBinding;
import com.alexander_borovskoy.blogclient.db.Post;
import com.alexander_borovskoy.blogclient.source.DataSource;

import java.util.ArrayList;
import java.util.List;


public class PostListFragment extends Fragment {
    public static final String TAG = "PostListFragment";
//    private static final String ARG_PARAM1 = "param1";

    //    private String mParam1;
    private PostAdapter mPostAdapter;
    private FragmentPostListBinding mBinding;
    private PostClickCallback mPostClickCallback = new PostClickCallback() {
        @Override
        public void onClick(Post post) {
            if (post != null) {
                ((MainActivity) getActivity()).replaceFragment(PostDetailFragment.newInstance(post.getId()), PostDetailFragment.TAG);
            }
        }
    };

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
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Fake post list
//        List<Post> posts = new ArrayList<>();
//        posts.add(new Post(21, "Title", "", "20.02.18"));
//        posts.add(new Post(31, "Hello world", "", "21.02.18"));
//        posts.add(new Post(141, "New Happy day", "", "22.02.18"));
//        posts.add(new Post(151, "My work", "", "23.02.18"));
//        posts.add(new Post(161, "Bad weather", "", "24.02.18"));
//        posts.add(new Post(171, "Hobby", "", "28.02.18"));
//
//        mPostAdapter.setPostList(posts);

        PostRepository repository = PostRepository.getInstance();
        repository.getAllPosts(new DataSource.LoadPostsCallback() {
            @Override
            public void onPostsLoaded(List<Post> postList) {
                mPostAdapter.setPostList(postList);
            }

            @Override
            public void onDataNotAvailable() {
                Toast.makeText(getContext(), "Data Not Available", Toast.LENGTH_SHORT).show();
            }
        });
        mBinding.postListSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
    }

    private void refreshContent() {
        PostRepository repository = PostRepository.getInstance();
        repository.getAllPosts(new DataSource.LoadPostsCallback() {
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
}
