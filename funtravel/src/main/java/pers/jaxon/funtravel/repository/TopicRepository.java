package pers.jaxon.funtravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pers.jaxon.funtravel.domain.Topic;


@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Topic findByTopic(String  topic);
}
