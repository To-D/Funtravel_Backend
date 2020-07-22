package pers.jaxon.funtravel.repository;

import pers.jaxon.funtravel.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findUserById(Long id);

    User findUserByEmail(String email);

}
