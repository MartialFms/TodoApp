package fr.fms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.fms.entities.TaskTable;

public interface TaskTableRepository extends JpaRepository<TaskTable, Long> {

}
