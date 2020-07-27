package pers.jaxon.funtravel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import pers.jaxon.funtravel.controller.request.PostCommentRequest;
import pers.jaxon.funtravel.controller.request.CollectRequest;
import pers.jaxon.funtravel.controller.request.SearchRequest;
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

        // Get request params
        String title = params.getParameter("title");
        String author = params.getParameter("author");
        String intro = params.getParameter("intro");
        String nation = params.getParameter("nation");
        String city = params.getParameter("city");
        String username = params.getParameter("username");
        Date uploadTime = new Date();
        String[] topics = params.getParameter("topics").split(",");

        // Rename file
        long time = System.currentTimeMillis();
        String url = title  + Long.toString(time) + ".jpg";
        String storePath = "D:\\images\\" + url;

        // Get file
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
        assert file != null;
        String type = file.getContentType();
        assert type != null;
        if(!type.equals("image/jpeg")){
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

        // Build new picture
        Picture picture = new Picture();
        picture.setUrl(url);
        picture.setTitle(title);
        picture.setNation(nation);
        picture.setCity(city);
        picture.setIntro(intro);
        picture.setAuthor(author);
        picture.setCollectionCount((long)0);
        picture.setUploadTime(uploadTime);
        Set<Topic> picture_topics = new HashSet<>();
        picture.setTopics(picture_topics);

        // Build relationship
        // user and picture
        User user = userRepository.findByUsername(username);
        picture.setUploader(user);
        user.getUploads().add(picture);

        //picture and topic
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

        pictureRepository.save(picture);
        userRepository.save(user);
        return "success";
    }
}
