package pers.jaxon.funtravel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import pers.jaxon.funtravel.domain.Comment;
import pers.jaxon.funtravel.domain.Picture;
import pers.jaxon.funtravel.repository.CommentRepository;
import pers.jaxon.funtravel.repository.PictureRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PictureService {
    PictureRepository pictureRepository;
    CommentRepository commentRepository;

    @Autowired
    public PictureService(PictureRepository pictureRepository,CommentRepository commentRepository) {
        this.pictureRepository = pictureRepository;
        this.commentRepository = commentRepository;
    }

    public List<Picture> getHottestPictures() {
        return pictureRepository.findHottestPictures();
    }


    public List<Picture> getNewestPictures() {
        return pictureRepository.findNewestPictures();
    }

    public Map<String, Object> getPictureDetail(Long id) {
        System.out.println(id);
        List<Comment> comments = commentRepository.findByPicture_Id(id);
        Picture picture = pictureRepository.findById(id).get();
        Map<String,Object> res = new HashMap<>();
        res.put("picture",picture);
        res.put("comments",comments);
        return res;
    }
}
