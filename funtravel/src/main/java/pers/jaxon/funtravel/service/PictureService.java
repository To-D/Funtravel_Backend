package pers.jaxon.funtravel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import pers.jaxon.funtravel.domain.Picture;
import pers.jaxon.funtravel.repository.PictureRepository;

import java.util.List;

@Service
public class PictureService {
    PictureRepository pictureRepository;

    @Autowired
    public PictureService(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    public List<Picture> getHottestPictures() {
        return pictureRepository.findHottestPictures();
    }


    public List<Picture> getNewestPictures() {
        return pictureRepository.findNewestPictures();
    }
}
