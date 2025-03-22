package com.todolist.backend.service;

import com.todolist.backend.model.Task;
import com.todolist.backend.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    public List<Task> getTasksByStatus(boolean completed) {
        return taskRepository.findByCompleted(completed);
    }
    
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }
    
    public Optional<Task> updateTask(Long id, Task taskDetails) {
        return taskRepository.findById(id)
            .map(existingTask -> {
                existingTask.setTitle(taskDetails.getTitle());
                existingTask.setDescription(taskDetails.getDescription());
                existingTask.setCompleted(taskDetails.isCompleted());
                existingTask.setDueDate(taskDetails.getDueDate());
                return taskRepository.save(existingTask);
            });
    }
    
    public boolean deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public Optional<Task> toggleTaskStatus(Long id) {
        return taskRepository.findById(id)
            .map(task -> {
                task.setCompleted(!task.isCompleted());
                return taskRepository.save(task);
            });
    }
} 