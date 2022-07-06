package fr.fms.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.fms.entities.UserTask;

public interface UserTaskRepository extends JpaRepository<UserTask, Long> {

	Page<UserTask> findByTitleContains(String keyword, Pageable pageable);
	List<UserTask> findAll();
	Page<UserTask> findById(long userId, Pageable pageable);
//		Page<User> findByDescriptionContains(String description, Pageable pageable);
//		public List<User> deleteById(long id);
//		public List<User>findByCategoryId(long id);
//		public Page<User> findByDescriptionContainsAndCategoryId(String description, Long categoryId, Pageable pageable);
//		Page<User> findByCategoryId(long catId, Pageable pageable);
//		List<User> findByBrandContains(String string);

}
