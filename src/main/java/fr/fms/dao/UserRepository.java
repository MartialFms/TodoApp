package fr.fms.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.fms.entities.TaskTable;
import fr.fms.entities.User;
import fr.fms.entities.UserTask;

public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findAll();
	Page<User> findByUsernameContains(String keyword, Pageable pageable);
	Page<User> findById(long userId, Pageable pageable);
//		Page<UserTask> findByDescriptionContains(String description, Pageable pageable);
//		public List<UserTask> deleteById(long id);
//		public List<UserTask>findByCategoryId(long id);
//		public Page<UserTask> findByDescriptionContainsAndCategoryId(String description, Long categoryId, Pageable pageable);
//		Page<UserTask> findByCategoryId(long catId, Pageable pageable);
//		List<UserTask> findByBrandContains(String string);

}
