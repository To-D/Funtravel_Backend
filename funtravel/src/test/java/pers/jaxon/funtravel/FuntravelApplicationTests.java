package pers.jaxon.funtravel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.jaxon.funtravel.domain.Comment;
import pers.jaxon.funtravel.domain.Picture;
import pers.jaxon.funtravel.repository.CommentRepository;
import pers.jaxon.funtravel.repository.PictureRepository;

import java.sql.SQLOutput;
import java.util.List;

@SpringBootTest
class FuntravelApplicationTests {

	@Autowired
	PictureRepository pictureRepository;

	@Test
	void contextLoads() {
		Picture picture = pictureRepository.findById((long)1).get();
		System.out.println(picture);
//		System.out.println(pictureRepository.findAll());
//		System.out.println(commentRepository.findAll());
//		System.out.println(commentRepository.findById((long)2));

//		List<Comment> comments = commentRepository.findByPicture_Id((long)2);
//		System.out.println(comments.get(0).getComment());
//		System.out.println(comments.get(1).getComment());
//		System.out.println(commentRepository.findByPicture_Id((long)3));
	}

}

