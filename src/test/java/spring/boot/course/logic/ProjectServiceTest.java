package spring.boot.course.logic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spring.boot.course.TaskConfigurationProperties;
import spring.boot.course.model.TaskGroupRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectServiceTest {

    @Test
    @DisplayName("should throw IllegalStateException when configured to allow just 1 group and other undone group exists")
    void createGroup_noMultipleGroupsConfig_And_undoneGroupExists_throwsIllegalStateException() {
        // given
        var mockGroupRepository = mock(TaskGroupRepository.class);
        when(mockGroupRepository.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(true);

        var mockTemplate = mock(TaskConfigurationProperties.Template.class);
        when(mockTemplate.isAllowMultipleTasks()).thenReturn(false);

        var mockConfig = mock(TaskConfigurationProperties.class);
        when(mockConfig.getTemplate()).thenReturn(mockTemplate);

        // system under test
        var toTest = new ProjectService(null, mockGroupRepository, mockConfig);

        // when
        toTest.createGroup(LocalDateTime.now(), 0);

    }
}