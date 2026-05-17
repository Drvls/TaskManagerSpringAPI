package org.alexvsi.taskmanager.infra.repository;

import org.alexvsi.taskmanager.domain.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
