package az.code.auction.repo;

import az.code.auction.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IUserRepo extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);
    List<User> findByIgnoreCode(Long ignoreCode);
//    @Query("select u from User u where u.email = :email and u.password = :password")
    Optional<User> findUserByEmail(String email);
}
