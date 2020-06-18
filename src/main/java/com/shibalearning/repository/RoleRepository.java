package com.shibalearning.repository;

import com.shibalearning.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "role", path = "role")
public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findById(long id);

    public List<Role> findAll();
}
