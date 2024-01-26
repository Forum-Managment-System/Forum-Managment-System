package org.forum.web.forum.service;

import org.forum.web.forum.models.Post;
import org.forum.web.forum.models.User;
import org.forum.web.forum.models.Comment;


import java.util.List;

public interface CommentService {

    void create(Post post, User user, Comment comment);

    void update(Post post, User user, Comment comment);

    void delete(User user, Comment comment);

    Comment getById(int commentId);

    List<Comment> getAll();

//    void likeComment(int commentID, User user);

//    void dislikeComment(int commentID, User user);

}