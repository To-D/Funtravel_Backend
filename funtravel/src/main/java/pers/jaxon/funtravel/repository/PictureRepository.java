package pers.jaxon.funtravel.repository;

import org.springframework.data.jpa.repository.Query;
import pers.jaxon.funtravel.domain.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
    List<Picture> findByUploader(Long uploader);

    @Query(value = "select * from pictures order by collection_count desc limit 5", nativeQuery = true)
    List<Picture> findHottestPictures();

    @Query(value = "select * from pictures order by release_time desc limit 6", nativeQuery = true)
    List<Picture> findNewestPictures();
}
