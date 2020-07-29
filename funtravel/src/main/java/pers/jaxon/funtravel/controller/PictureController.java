package pers.jaxon.funtravel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pers.jaxon.funtravel.controller.request.*;
import pers.jaxon.funtravel.domain.Picture;
import pers.jaxon.funtravel.domain.Topic;
import pers.jaxon.funtravel.service.PictureService;


import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class PictureController {
    @Autowired
    private PictureService pictureService;

    @PostMapping("/getHottestPictures")
    public ResponseEntity<Map> getHot() {
        Map<String, List> hottest = pictureService.getHottestPictures();
        return ResponseEntity.ok(hottest);
    }

    @PostMapping("/getNewestPictures")
    public ResponseEntity<Map> getNew() {
        Map<String, List> newest = pictureService.getNewestPictures();
        return ResponseEntity.ok(newest);

    }

    @PostMapping("/getPictureDetail")
    public ResponseEntity<Map> getPictureDetail(@RequestBody GetPictureDetailRequest request){
        Picture picture = pictureService.getPictureDetail(request.getId());
        Set<Topic> topics = picture.getTopics();
        Map<String,Object> map = new HashMap<>();
        map.put("picture",picture);
        map.put("topics",topics);
        return ResponseEntity.ok(map);
    }

    @PostMapping("/postComment")
    public ResponseEntity<String> postComment(@RequestBody PostCommentRequest request){
        pictureService.addComment(request);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/isCollected")
    public ResponseEntity<Boolean> isCollected(@RequestBody CollectRequest request){
        Boolean res = pictureService.testCollected(request);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/collect")
    public ResponseEntity<String> collect(@RequestBody CollectRequest request){
        pictureService.addToFavorite(request);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/cancelCollect")
    public ResponseEntity<String> cancelCollect(@RequestBody CollectRequest request){
        pictureService.cancelFavorite(request);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/search")
    public ResponseEntity<List> search(@RequestBody SearchRequest request){
        List<Picture> res = pictureService.search(request);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPicture(HttpServletRequest request){
            String res = pictureService.uploadPicture(request);
            return ResponseEntity.ok(res);
    }

    @PostMapping("/canModify")
    public ResponseEntity<Map> canModify(@RequestBody CanModifyRequest request){
        Map<String,Object> res = pictureService.canModify(request);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/getMyPictures")
    public ResponseEntity<Set> getMyPictures(@RequestBody GetMyPicturesRequest request){
        Set<Picture> res = pictureService.getMyPictures(request);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/getMyFavorite")
    public ResponseEntity<Set> getMyFavorite(@RequestBody GetMyPicturesRequest request){
        Set<Picture> res = pictureService.getMyFavorite(request);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/deletePicture")
    public ResponseEntity<String> deletePicture(@RequestBody GetPictureDetailRequest request){
        String res = pictureService.deletePicture(request);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/getFriendFavorite")
    public ResponseEntity<Map> getFriendFavorite(@RequestBody GetMyPicturesRequest request) {
        Map<String,Object> res = pictureService.getFriendFavorite(request);
        return ResponseEntity.ok(res);
    }

}
