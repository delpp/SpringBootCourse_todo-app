package spring.boot.course.logic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spring.boot.course.model.TaskGroup;
import spring.boot.course.model.TaskGroupRepository;
import spring.boot.course.model.TaskRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaskGroupTest {

    @Test
    @DisplayName("should throw IllegalStateException when group has one or more undone tasks")
    void toggleGroup_groupHasUndoneTasks_throwsIllegalStateException(){
        // given
        var mockTaskRepository = mock(TaskRepository.class);
        // system under test
        var toTest = new TaskGroupService(null, mockTaskRepository);

        // when
        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(true);
        // and
        var exception = catchThrowable(() -> toTest.toggleGroup(anyInt()));

        // then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                        .hasMessageContaining("Group has undone tasks.");
    }

    @Test
    @DisplayName("should throw IllegalArgumentException when TaskGroup's id not found")
    void toggleGroup_groupHasDoneTasks_And_notFoundTaskGroupId_throwsIllegalArgumentException(){
        // given
        var mockTaskGroupRepository = mock(TaskGroupRepository.class);
        // and
        var mockTaskRepository = mock(TaskRepository.class);
        // system under test
        var toTest = new TaskGroupService(mockTaskGroupRepository, mockTaskRepository);

        // when
        when(mockTaskGroupRepository.findById(anyInt())).thenReturn(Optional.empty());
        // and
        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(false);
        // and
        var exception = catchThrowable(() -> toTest.toggleGroup(anyInt()));

        // then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found");
    }

    @Test
    @DisplayName("should change status of done field on TaskGroup")
    void toggleGroup_groupHasDoneTasks_And_foundTaskGroupId_changesStatusOfFieldDone(){
        // given
        var mockTaskGroupRepository = mock(TaskGroupRepository.class);

        // and
        var mockTaskRepository = mock(TaskRepository.class);
        // and
        var taskGroup = new TaskGroup();
        //
        var statusAtStart = taskGroup.isDone();

        // system under test
        var toTest = new TaskGroupService(mockTaskGroupRepository, mockTaskRepository);

        // when
        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(false);
        // and
        when(mockTaskGroupRepository.findById(anyInt())).thenReturn(Optional.of(taskGroup));
        // and
        toTest.toggleGroup(anyInt());

        // then
        assertThat(taskGroup.isDone()).isEqualTo(!statusAtStart);

    }
}
