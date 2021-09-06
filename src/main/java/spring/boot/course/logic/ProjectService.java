package spring.boot.course.logic;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import spring.boot.course.TaskConfigurationProperties;
import spring.boot.course.model.*;
import spring.boot.course.model.projection.GroupReadModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequestScope
public class ProjectService {
    private final ProjectRepository repository;
    private final TaskGroupRepository taskGroupRepository;
    private final TaskConfigurationProperties taskConfigurationProperties;

    public ProjectService(ProjectRepository repository, TaskGroupRepository taskGroupRepository, TaskConfigurationProperties taskConfigurationProperties) {
        this.repository = repository;
        this.taskGroupRepository = taskGroupRepository;
        this.taskConfigurationProperties = taskConfigurationProperties;
    }

    public List<Project> readAll(){
        return repository.findAll();
    }

    public Project save(final Project toSave){
        return repository.save(toSave);
    }

    public GroupReadModel createGroup(LocalDateTime date, int projectId){
        Project project = repository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Wrong project's id"));

        if (!taskConfigurationProperties.getTemplate().isAllowMultipleTasks()
                && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)) {
            throw new IllegalStateException("Only one undone group from project is allowed");
        }

        TaskGroup taskGroup = new TaskGroup();
        taskGroup.setDescription(project.getDescription());
        taskGroup.setProject(project);

        taskGroup.setTasks(
            project.getSteps().stream()
                    .map(step -> new Task(step.getDescription(), date.plusDays(step.getDaysToDeadline())))
                    .collect(Collectors.toSet()));

        taskGroupRepository.save(taskGroup);

        return new GroupReadModel(taskGroup);
    }
}
