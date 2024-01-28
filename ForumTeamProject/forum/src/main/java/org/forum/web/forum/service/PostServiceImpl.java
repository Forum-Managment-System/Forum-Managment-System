package org.forum.web.forum.service;

import org.forum.web.forum.exceptions.AuthorizationException;
import org.forum.web.forum.exceptions.EntityNotFoundException;
import org.forum.web.forum.models.LikePost;
import org.forum.web.forum.models.Post;
import org.forum.web.forum.models.User;
import org.forum.web.forum.models.filters.PostFilterOptions;
import org.forum.web.forum.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private static final String AUTHORIZATION_ERROR = "You are not authorized!";
    private final PostRepository postRepository;
    private final LikePostService likePostService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, LikePostService likePostService) {
        this.postRepository = postRepository;
        this.likePostService = likePostService;
    }

    @Override
    public List<Post> getByUserId(PostFilterOptions postFilterOptions, int id) {
        return postRepository.getByUserId(postFilterOptions, id);
    }

    @Override
    public List<Post> getFiltered(PostFilterOptions postFilterOptions) {
        return postRepository.getFiltered(postFilterOptions);
    }

    @Override
    public List<Post> getMostRecent() {
        return postRepository.getRecent();
    }

    @Override
    public List<Post> getMostCommented() {
        return postRepository.getMostCommented();
    }

    @Override
    public Post getById(int id) {
        return postRepository.getById(id);
    }

    @Override
    public void likePost(int id, User user) {
        Post post = postRepository.getById(id);
        try {
            LikePost likePost = likePostService.get(post, user);
            likePostService.delete(likePost);
        } catch (EntityNotFoundException e) {
            likePostService.create(post, user);
        }
    }

    @Override
    public void create(Post post, User user) {
        checkIfBanned(user);
        post.setCreator(user);
        post.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        postRepository.create(post);

    }

    @Override
    public void update(Post post, User user) {
        checkIfBanned(user);
        checkAuthor(post.getCreator(), user);
        postRepository.update(post);

    }

    @Override
    public void delete(Post post, User user) {
        checkAdmin(user);
        checkAuthor(post.getCreator(), user);
        checkIfBanned(user);
        postRepository.delete(post);
    }

    private void checkIfBanned(User user) {
        if (user.getBanStatus()) {
            throw new AuthorizationException("This user is banned!");
        }
    }

    private void checkAuthor(User user, User userToCheck) {
        if (userToCheck.getUserId() != user.getUserId()) {
            throw new AuthorizationException(AUTHORIZATION_ERROR);
        }
    }

    private void checkAdmin(User user) {
        if (!user.isAdmin()) {
            throw new AuthorizationException(AUTHORIZATION_ERROR);
        }
    }
}
