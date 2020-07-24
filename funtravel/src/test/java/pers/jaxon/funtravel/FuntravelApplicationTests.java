package pers.jaxon.funtravel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.jaxon.funtravel.domain.Picture;
import pers.jaxon.funtravel.repository.PictureRepository;

import java.sql.SQLOutput;
import java.util.List;

@SpringBootTest
class FuntravelApplicationTests {

	@Autowired
	PictureRepository pictureRepository;

	@Test
	void contextLoads() {
	}

}
