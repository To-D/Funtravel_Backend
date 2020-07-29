package pers.jaxon.funtravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pers.jaxon.funtravel.domain.Message;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
