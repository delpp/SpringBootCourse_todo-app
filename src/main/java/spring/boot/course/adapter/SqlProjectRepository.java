package spring.boot.course.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spring.boot.course.model.Project;
import spring.boot.course.model.ProjectRepository;
import spring.boot.course.model.TaskGroup;

import java.util.List;

interface SqlProjectRepository extends ProjectRepository, JpaRepository<Project, Integer> {
    @Override
    @Query("from Project g join fetch g.projectSteps")
    List<Project> findAll();
}
