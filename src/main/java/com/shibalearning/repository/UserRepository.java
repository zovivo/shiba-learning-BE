package com.shibalearning.repository;


import com.shibalearning.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findAll();
    Page<User> findAll(Pageable p);
    Page<User> findAllByUserNameContainingAndAndEmailContaining(Pageable p, String userName, String email);
    User findFirstByUserName(String userName);
    User findFirstByEmail(String email);
    User findById(long id);
    List<User> findAllByUserNameContaining(String userName);
    List<User> findAllByRole_Id(long roleId);
}
