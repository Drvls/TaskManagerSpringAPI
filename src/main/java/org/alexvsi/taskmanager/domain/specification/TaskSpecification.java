package org.alexvsi.taskmanager.domain.specification;

import jakarta.persistence.criteria.Predicate;
import org.alexvsi.taskmanager.domain.entity.Task;
import org.alexvsi.taskmanager.domain.enums.Priority;
import org.alexvsi.taskmanager.domain.enums.Status;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskSpecification {
    public static Specification<Task> filter(
            String title,
            Priority priority,
            LocalDate deadline,
            Status taskStatus
    ) {
        return ((root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();

                if(title != null) {
                    predicates.add(cb.like(root.get("title"), "%" + title + "%"));
                }

                if(priority != null) {
                    predicates.add(cb.equal(root.get("priority"), priority));
                }

                if(deadline != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("deadline"), deadline));
                }

                if(taskStatus != null) {
                    predicates.add(cb.equal(root.get("task_status"), taskStatus));
                }

                return cb.and(predicates.toArray(new Predicate[0]));
        });
    }
}
