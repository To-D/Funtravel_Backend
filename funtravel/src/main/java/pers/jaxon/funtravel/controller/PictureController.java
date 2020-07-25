package pers.jaxon.funtravel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pers.jaxon.funtravel.controller.request.GetPictureDetailRequest;
import pers.jaxon.funtravel.controller.request.PostCommentRequest;
import pers.jaxon.funtravel.controller.request.RegisterRequest;
import pers.jaxon.funtravel.domain.Picture;
import pers.jaxon.funtravel.service.PictureService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
public class PictureController {
    @Autowired
    private PictureService pictureService;

    @PostMapping("/getHottestPictures")
    public ResponseEntity<List> getHot() {
        List<Picture> hottest = pictureService.getHottestPictures();
        return ResponseEntity.ok(hottest);
    }

    @PostMapping("/getNewestPictures")
    public ResponseEntity<List> getNew() {
//        List<Picture> newest = pictureService.getNewestPictures();
        List<String> newest = new LinkedList<>();
        return ResponseEntity.ok(newest);

    }

    @PostMapping("/getPictureDetail")
    public ResponseEntity<Picture> getPictureDetail(@RequestBody GetPictureDetailRequest request){
        Picture picture = pictureService.getPictureDetail(request.getId());
        return ResponseEntity.ok(picture);
    }

    @PostMapping("/postComment")
    public ResponseEntity<String> postComment(@RequestBody PostCommentRequest request){
        pictureService.addComment(request);
        return ResponseEntity.ok("success");
    }
}
