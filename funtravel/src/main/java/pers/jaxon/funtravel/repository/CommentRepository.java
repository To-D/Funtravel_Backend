package pers.jaxon.funtravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pers.jaxon.funtravel.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
