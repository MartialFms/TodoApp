package fr.fms.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.fms.entities.Role;
import fr.fms.entities.TaskTable;
import fr.fms.entities.TaskType;

public interface TaskTypeRepository extends JpaRepository<TaskType, Long> {

//		Page<Task> findByDescriptionContains(String description, Pageable pageable);
//		public List<Task> deleteById(long id);
//		public List<Task>findByCategoryId(long id);
//		public Page<Task> findByDescriptionContainsAndCategoryId(String description, Long categoryId, Pageable pageable);
//		Page<Task> findByCategoryId(long catId, Pageable pageable);
//		List<Task> findByBrandContains(String string);

}
