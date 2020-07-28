package pers.jaxon.funtravel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.jaxon.funtravel.controller.request.GetPictureDetailRequest;
import pers.jaxon.funtravel.domain.Comment;
import pers.jaxon.funtravel.domain.Picture;
import pers.jaxon.funtravel.domain.Topic;
import pers.jaxon.funtravel.repository.CommentRepository;
import pers.jaxon.funtravel.repository.PictureRepository;
import pers.jaxon.funtravel.repository.TopicRepository;
import pers.jaxon.funtravel.service.PictureService;

import java.sql.SQLOutput;
import java.util.List;

@SpringBootTest
class FuntravelApplicationTests {

	@Autowired
	PictureService pictureService;

	@Test
	void contextLoads() {
//		Topic topic = topicRepository.findByTopic("Nature");
//		System.out.println(topic.getTopic());
		GetPictureDetailRequest getPictureDetailRequest = new GetPictureDetailRequest();
		getPictureDetailRequest.setId((long)15);
 		pictureService.deletePicture(getPictureDetailRequest);
//		System.out.println("title and count");
//		List<Picture> pictures = pictureRepository.findByTitleAndCollectionCount("%t%");
//		for(Picture picture :pictures){
//			System.out.println(picture);
//		}
//
//		System.out.println("title and uploadtime");
//		pictures = pictureRepository.findByTitleAndUploadTime("%t%");
//		for(Picture picture :pictures){
//			System.out.println(picture);
//		}
//
//		System.out.println("topic and count");
//		pictures = pictureRepository.findByTopicAndCollectionCount("%t%");
//		for(Picture picture :pictures){
//			System.out.println(picture);
//		}
//
//		System.out.println("topic and uploadtime");
//		pictures = pictureRepository.findByTopicAndUploadTime("%t%");
//		for(Picture picture :pictures){
//			System.out.println(picture);
//		}

//		Picture picture = pictureRepository.findById((long)1).get();
//		System.out.println(picture);
//		System.out.println(pictureRepository.findAll());
//		System.out.println(commentRepository.findAll());
//		System.out.println(commentRepository.findById((long)2));

//		List<Comment> comments = commentRepository.findByPicture_Id((long)2);
//		System.out.println(comments.get(0).getComment());
//		System.out.println(comments.get(1).getComment());
//		System.out.println(commentRepository.findByPicture_Id((long)3));
	}

}

