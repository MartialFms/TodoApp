package fr.fms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.fms.entities.TaskType;

public interface TaskTypeRepository extends JpaRepository<TaskType, Long> {

}
