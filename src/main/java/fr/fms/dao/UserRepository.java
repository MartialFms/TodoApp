package fr.fms.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.fms.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findAll();

	Page<User> findByUsernameContains(String keyword, Pageable pageable);

	Page<User> findById(long userId, Pageable pageable);

	User findFirstByUsername(String name);

}
