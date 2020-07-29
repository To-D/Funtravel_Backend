package pers.jaxon.funtravel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import pers.jaxon.funtravel.controller.request.*;
import pers.jaxon.funtravel.domain.Comment;
import pers.jaxon.funtravel.domain.Picture;
import pers.jaxon.funtravel.domain.Topic;
import pers.jaxon.funtravel.domain.User;
import pers.jaxon.funtravel.repository.CommentRepository;
import pers.jaxon.funtravel.repository.PictureRepository;
import pers.jaxon.funtravel.repository.TopicRepository;
import pers.jaxon.funtravel.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

@Service
public class PictureService {
    PictureRepository pictureRepository;
    CommentRepository commentRepository;
    UserRepository userRepository;
    TopicRepository topicRepository;

    @Autowired
    public PictureService(PictureRepository pictureRepository,CommentRepository commentRepository,UserRepository userRepository,TopicRepository topicRepository) {
        this.pictureRepository = pictureRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }

    public Map<String,List> getHottestPictures() {
        List<Picture> pictures = pictureRepository.findHottestPictures();
        List<Set> topics = new LinkedList<>();
        for(Picture picture:pictures){
            topics.add(picture.getTopics());
        }
        Map<String,List> res = new HashMap<>();
        res.put("pictures",pictures);
        res.put("topics",topics);
        return res;
    }


    public Map<String,List> getNewestPictures() {
        List<Picture> pictures = pictureRepository.findNewestPictures();
        List<Set> topics = new LinkedList<>();
        for(Picture picture:pictures){
            topics.add(picture.getTopics());
        }
        Map<String,List> res = new HashMap<>();
        res.put("pictures",pictures);
        res.put("topics",topics);
        return res;
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

    public List<Picture> search(SearchRequest request) {
        String keyword = "%"+request.getKeyword()+"%";
        String filter = request.getFilter();
        String sort = request.getSort();
        List<Picture> res = new LinkedList<>();

        if(filter.equals("TITLE") && sort.equals("FAVORITE HOT") ){
             return pictureRepository.findByTitleAndCollectionCount(keyword);
        }

        if(filter.equals("TITLE") && sort.equals("UPDATE TIME") ){
            return pictureRepository.findByTitleAndUploadTime(keyword);
        }

        if(filter.equals("TOPIC") && sort.equals("FAVORITE HOT")){
            return pictureRepository.findByTopicAndCollectionCount(keyword);
        }

        if(filter.equals("TOPIC") && sort.equals("UPDATE TIME")){
            return pictureRepository.findByTopicAndUploadTime(keyword);
        }
        return res;
    }

    public String uploadPicture(HttpServletRequest request) {
        MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);

        // Prepare generic params
        String title = params.getParameter("title");
        String author = params.getParameter("author");
        String nation = params.getParameter("nation");
        String city = params.getParameter("city");
        String intro = params.getParameter("intro");
        String[] topics = params.getParameter("topics").split(",");
        Date uploadTime = new Date();
        String url;

        String pictureId = params.getParameter("pictureId");
        Picture picture;
        // pictureId == null means it's a upload
        if(pictureId == null){
            //Create new picture
            picture = new Picture();

            // Rename file
            long time = System.currentTimeMillis();
            url = title  + Long.toString(time) + ".jpg";
            picture.setUrl(url); //修改的时候不改文件存储位置，不然映射出错

            // Build relationship: user and picture
            String username = params.getParameter("username");
            User user = userRepository.findByUsername(username);
            picture.setUploader(user);
            user.getUploads().add(picture);
            userRepository.save(user);
        }else{ // picture ！= null means it's a modification
            picture = pictureRepository.findById(Long.parseLong(pictureId)).get();
            url = picture.getUrl();
        }
        // Set generic params
        picture.setTitle(title);
        picture.setAuthor(author);
        picture.setNation(nation);
        picture.setCity(city);
        picture.setIntro(intro);
        picture.setCollectionCount((long)0);
        picture.setUploadTime(uploadTime);

        //Build relationship: picture and topic
        Set<Topic> picture_topics = new HashSet<>();
        picture.setTopics(picture_topics);
        Topic tmpTopic;
        for(String topic :topics){
            System.out.println(topic);
            tmpTopic = topicRepository.findByTopic(topic);
            if(tmpTopic != null){
                tmpTopic.getPictures().add(picture);
                picture.getTopics().add(tmpTopic);
            }else{
                tmpTopic = new Topic(topic);
                picture.getTopics().add(tmpTopic);
                tmpTopic.getPictures().add(picture);
            }
            topicRepository.save(tmpTopic);
        }

        // Store file
        String storePath = "D:\\images\\" + url;
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
        if(file != null) {
            String type = file.getContentType();
            assert type != null;
            if (!type.equals("image/jpeg")) {
                return "Only JPG is allowed";
            }
            if (!file.isEmpty()) {
                try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(
                        new File(storePath)))) {
                    byte[] bytes = file.getBytes();
                    stream.write(bytes);
                } catch (Exception e) {
                    return "You failed to upload " + " => " + e.getMessage();
                }
            } else {
                return "You failed to upload " + " because the file was empty.";
            }

        }
        pictureRepository.save(picture);
        return "success";
    }

    public Map<String, Object> canModify(CanModifyRequest request) {
        Map<String,Object> res = new HashMap<>();

        String username = request.getUsername();
        Long pictureId = request.getPictureId();
        User user = userRepository.findByUsername(username);
        Picture picture = pictureRepository.findById(pictureId).get();
        if(picture.getUploader() == user){
            res.put("message","yes");
            res.put("picture",picture);
            res.put("topics",picture.getTopics());
        }else{
            res.put("message","no");
        }
        return res;
    }

    public Set<Picture> getMyPictures(GetMyPicturesRequest request) {
        String username = request.getUsername();
        User user = userRepository.findByUsername(username);
        return user.getUploads();
    }

    public String deletePicture(GetPictureDetailRequest request) {
        Long id = request.getId();
        Picture picture = pictureRepository.findById(id).get();

        // 从收藏关系的维护端删除关系
        Set<User> users = picture.getCollectors();
        for(User user: users){
            user.getCollections().remove(picture);
            userRepository.save(user);
        }
        pictureRepository.delete(picture);
        return "success";
    }

    public Set<Picture> getMyFavorite(GetMyPicturesRequest request) {
        String username = request.getUsername();
        User user = userRepository.findByUsername(username);
        return user.getCollections();
    }

    public Map<String,Object> getFriendFavorite(GetMyPicturesRequest request) {
        Map<String,Object> res = new HashMap<>();

        String username = request.getUsername();
        User user = userRepository.findByUsername(username);
        int view = user.getView();
        res.put("view",view);
        if(view == 1){
            res.put("pictures",user.getCollections());
        }
        return res;
    }
}
