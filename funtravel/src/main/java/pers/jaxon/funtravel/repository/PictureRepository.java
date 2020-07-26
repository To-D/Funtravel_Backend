package pers.jaxon.funtravel.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pers.jaxon.funtravel.domain.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
    List<Picture> findByUploader(int uploader);

    @Query(value = "select * from pictures order by collection_count desc limit 5", nativeQuery = true)
    List<Picture> findHottestPictures();

    @Query(value = "select * from pictures order by upload_time desc limit 3", nativeQuery = true)
    List<Picture> findNewestPictures();

    @Query(value = "select * from pictures where title Like :keyword ORDER BY collection_count DESC",nativeQuery = true)
    List<Picture> findByTitleAndCollectionCount(@Param("keyword") String keyword);

    @Query(value = "select * from pictures where title Like :keyword ORDER BY upload_time DESC",nativeQuery = true)
    List<Picture> findByTitleAndUploadTime(@Param("keyword") String keyword);

    @Query(value = "select * from pictures where id in (select picture_id from pictures_topics where topic_id in (select id from topics where topic like :keyword)) ORDER BY collection_count DESC;",nativeQuery = true)
    List<Picture> findByTopicAndCollectionCount(@Param("keyword") String keyword);

    @Query(value = "select * from pictures where id in (select picture_id from pictures_topics where topic_id in (select id from topics where topic like :keyword)) ORDER BY upload_time DESC;",nativeQuery = true)
    List<Picture> findByTopicAndUploadTime(@Param("keyword") String keyword);
}
