package spring.boot.course.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Project's description must not be empty")
    private String description;

    @OneToMany
    @JoinColumn(name = "task_group_id")
    private Set<TaskGroup> taskGroups;

    @OneToMany
    @JoinColumn(name = "project_steps_id")
    private Set<ProjectSteps> projectSteps;

    public Project() {
    }

    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    Set<TaskGroup> getTaskGroups() {
        return taskGroups;
    }

    void setTaskGroups(Set<TaskGroup> group) {
        this.taskGroups = group;
    }
}
