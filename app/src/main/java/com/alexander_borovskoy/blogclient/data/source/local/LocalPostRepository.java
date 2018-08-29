package com.alexander_borovskoy.blogclient.data.source.local;

import android.support.annotation.NonNull;

import com.alexander_borovskoy.blogclient.data.Comment;
import com.alexander_borovskoy.blogclient.data.Mark;
import com.alexander_borovskoy.blogclient.data.Post;
import com.alexander_borovskoy.blogclient.data.source.PostsDataSource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class LocalPostRepository implements PostsDataSource {

    private Realm realm;

    @Inject
    public LocalPostRepository(Realm realm) {
        this.realm = realm;
    }

    @Override
    public void getAllPosts(@NonNull LoadPostsCallback callback) {
        RealmResults<Post> posts = realm.where(Post.class).findAll();
        if (posts.size() == 0) {
            callback.onDataNotAvailable();
        } else {
            callback.onPostsLoaded(posts);
        }
    }

    @Override
    public void getPost(long postId, @NonNull LoadPostCallback callback) {
        Post post = realm.where(Post.class).equalTo("id", postId).findFirst();
        if (post == null) {
            callback.onDataNotAvailable();
        } else {
            callback.onPostLoaded(post);
        }
    }

    @Override
    public void getPostMarks(long postId, @NonNull LoadPostMarksCallback callback) {
        RealmResults<Mark> marks = realm
                .where(Mark.class)
                .equalTo("postId", postId)
                .findAll();
        if (marks.size() == 0) {
            callback.onDataNotAvailable();
        } else {
            callback.onPostMarksLoaded(marks);
        }
    }

    @Override
    public void getPostComments(long postId, @NonNull LoadPostCommentsCallback callback) {
        RealmResults<Comment> comments = realm
                .where(Comment.class)
                .equalTo("postId", postId)
                .findAll();
        if (comments.size() == 0) {
            callback.onDataNotAvailable();
        } else {
            callback.onPostCommentsLoaded(comments);
        }
    }

    public void addPosts(final List<Post> posts) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(posts);
            }
        });
    }

    public void addPostMarks(final List<Mark> marks) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.insert(marks);
            }
        });
    }

    public void addComments(final List<Comment> comments) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(comments);
            }
        });
    }

    public void updatePosts(final List<Post> postList) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                addPosts(postList);
            }
        });
    }

    public List<Mark> setPostIdForMarks(List<Mark> marks, long postId) {
        List<Mark> marksWithPostId = new ArrayList<>(marks.size());
        for (Mark mark : marks) {
            mark.setPostId(postId);
            marksWithPostId.add(mark);
        }
        return marksWithPostId;
    }

    public List<Comment> setPostIdForComments(List<Comment> comments, long postId) {
        List<Comment> commentsWithPostId = new ArrayList<>(comments.size());
        for (Comment comment : comments) {
            comment.setPostId(postId);
            commentsWithPostId.add(comment);
        }
        return commentsWithPostId;
    }

    public void addPost(final Post post) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(post);
            }
        });
    }

    public void closeRealm() {
        realm.close();
    }
}
