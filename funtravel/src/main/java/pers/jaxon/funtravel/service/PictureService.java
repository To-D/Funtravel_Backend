package pers.jaxon.funtravel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.jaxon.funtravel.controller.request.PostCommentRequest;
import pers.jaxon.funtravel.controller.request.CollectRequest;
import pers.jaxon.funtravel.domain.Comment;
import pers.jaxon.funtravel.domain.Picture;
import pers.jaxon.funtravel.domain.User;
import pers.jaxon.funtravel.repository.CommentRepository;
import pers.jaxon.funtravel.repository.PictureRepository;
import pers.jaxon.funtravel.repository.UserRepository;

import java.util.*;

@Service
public class PictureService {
    PictureRepository pictureRepository;
    CommentRepository commentRepository;
    UserRepository userRepository;

    @Autowired
    public PictureService(PictureRepository pictureRepository,CommentRepository commentRepository,UserRepository userRepository) {
        this.pictureRepository = pictureRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public List<Picture> getHottestPictures() {
        return pictureRepository.findHottestPictures();
    }


    public List<Picture> getNewestPictures() {
        return pictureRepository.findNewestPictures();
    }

    public Picture getPictureDetail(Long id) {
        return pictureRepository.findById(id).get();
    }

    public void addComment(PostCommentRequest request) {
        String username = request.getUsername();
        String content = request.getComment();
        Long pictureId = request.getPictureId();
        Date date = new Date();

        // build relationship
        Picture picture = pictureRepository.findById(pictureId).get();
        Comment comment = new Comment(picture,username,content,date);
        picture.getComments().add(comment);
        commentRepository.save(comment);
    }

    public boolean testCollected(CollectRequest request) {
        String username = request.getUsername();
        Long pictureId = request.getPictureId();

        Picture picture = pictureRepository.findById(pictureId).get();
        Set<User> users = picture.getCollectors();
        for(User user:users){
            if(user.getUsername().equals(username)){
                return true;
            }
        }

        return false;
    }

    public void addToFavorite(CollectRequest request) {
        String username = request.getUsername();
        Long pictureId = request.getPictureId();

        Picture picture = pictureRepository.findById(pictureId).get();
        User user = userRepository.findByUsername(username);
        picture.getCollectors().add(user);
        picture.setCollectionCount(picture.getCollectionCount()+1);
        user.getCollections().add(picture);
        userRepository.save(user);
        pictureRepository.save(picture);
    }

    public void cancelFavorite(CollectRequest request){
        String username = request.getUsername();
        Long pictureId = request.getPictureId();
        Picture picture = pictureRepository.findById(pictureId).get();
        User user = userRepository.findByUsername(username);

        picture.getCollectors().remove(user);
        picture.setCollectionCount(picture.getCollectionCount()-1);
        user.getCollections().remove(picture);
        userRepository.save(user);
        pictureRepository.save(picture);
    }

}
