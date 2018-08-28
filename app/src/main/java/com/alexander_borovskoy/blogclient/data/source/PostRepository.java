package com.alexander_borovskoy.blogclient.data.source;

import android.support.annotation.NonNull;

import com.alexander_borovskoy.blogclient.data.Comment;
import com.alexander_borovskoy.blogclient.data.Mark;
import com.alexander_borovskoy.blogclient.data.Post;
import com.alexander_borovskoy.blogclient.data.source.local.LocalPostRepository;
import com.alexander_borovskoy.blogclient.data.source.remote.RemotePostRepository;

import java.util.List;

import javax.inject.Inject;

public class PostRepository implements PostsDataSource {

    private static final String TAG = "LOG TAG";
    private final RemotePostRepository remoteRepository;
    private final LocalPostRepository localRepository;

    @Inject
    public PostRepository(RemotePostRepository remoteRepository,
                          LocalPostRepository localRepository) {
        this.remoteRepository = remoteRepository;
        this.localRepository = localRepository;
    }

    @Override
    public void getAllPosts(@NonNull final LoadPostsCallback callback) {
        localRepository.getAllPosts(new LoadPostsCallback() {
            @Override
            public void onPostsLoaded(List<Post> postList) {
                callback.onPostsLoaded(postList);
            }

            @Override
            public void onDataNotAvailable() {
                getPostsFromRemoteDataSource(callback);
            }
        });
    }

    @Override
    public void getPost(final long postId, @NonNull final LoadPostCallback callback) {
        localRepository.getPost(postId, new LoadPostCallback() {
            @Override
            public void onPostLoaded(Post post) {
                callback.onPostLoaded(post);
            }

            @Override
            public void onDataNotAvailable() {
                // TODO: 27.08.2018 refactoring
                remoteRepository.getPost(postId, new LoadPostCallback() {
                    @Override
                    public void onPostLoaded(Post post) {
                        callback.onPostLoaded(post);
                        localRepository.addPost(post);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }

    @Override
    public void getPostMarks(final long postId, @NonNull final LoadPostMarksCallback callback) {
        localRepository.getPostMarks(postId, new LoadPostMarksCallback() {
            @Override
            public void onPostMarksLoaded(List<Mark> markList) {
                callback.onPostMarksLoaded(markList);
            }

            @Override
            public void onDataNotAvailable() {
                getMarksFromRemoteDataSource(postId, callback);
            }
        });
    }

    @Override
    public void getPostComments(final long postId, @NonNull final LoadPostCommentsCallback callback) {
        localRepository.getPostComments(postId, new LoadPostCommentsCallback() {
            @Override
            public void onPostCommentsLoaded(List<Comment> commentList) {
                callback.onPostCommentsLoaded(commentList);
            }

            @Override
            public void onDataNotAvailable() {
                getCommentsFromRemoteDataSource(postId, callback);
            }
        });
    }

    private void getPostsFromRemoteDataSource(final LoadPostsCallback callback) {
        remoteRepository.getAllPosts(new LoadPostsCallback() {
            @Override
            public void onPostsLoaded(List<Post> postList) {
                refreshLocalDataSource(postList);
                callback.onPostsLoaded(postList);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void getCommentsFromRemoteDataSource(final long postId,
                                                 final LoadPostCommentsCallback callback) {
        remoteRepository.getPostComments(postId, new LoadPostCommentsCallback() {
            @Override
            public void onPostCommentsLoaded(List<Comment> commentList) {
                callback.onPostCommentsLoaded(commentList);
                List<Comment> commentsWithPostId =
                        localRepository.setPostIdForComments(commentList, postId);
                localRepository.addComments(commentsWithPostId);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void getMarksFromRemoteDataSource(final long postId,
                                              final LoadPostMarksCallback callback) {
        remoteRepository.getPostMarks(postId, new LoadPostMarksCallback() {
            @Override
            public void onPostMarksLoaded(List<Mark> markList) {
                callback.onPostMarksLoaded(markList);
                List<Mark> marksWithPostId = localRepository.setPostIdForMarks(markList, postId);
                localRepository.addPostMarks(marksWithPostId);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshLocalDataSource(List<Post> postList) {
        localRepository.deleteAll();
        localRepository.addPosts(postList);
    }
}
