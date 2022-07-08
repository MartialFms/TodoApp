package fr.fms.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.fms.entities.User;
import fr.fms.entities.UserTask;

public interface UserTaskRepository extends JpaRepository<UserTask, Long> {

	Page<UserTask> findByTitleContains(String keyword, Pageable pageable);

	List<UserTask> findAll();

	Page<UserTask> findById(long userId, Pageable pageable);

	UserTask deleteById(long id);

	Page<UserTask> findByUser(User connectedUser, Pageable pageable);

}
