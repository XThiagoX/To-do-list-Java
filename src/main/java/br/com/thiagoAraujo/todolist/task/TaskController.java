package br.com.thiagoAraujo.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiagoAraujo.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/tasks/")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request ){    
        var IdUser = request.getAttribute("IdUser");
        taskModel.setIdUser((UUID)IdUser);

        var currentDate = LocalDateTime.now();
        if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de início/término deve ser maior que a data atual");
        }
        if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de início deve ser menor que a data término");
        }

        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);        
    }

    @GetMapping("/")
    public java.util.List<TaskModel> List(HttpServletRequest request){
        var IdUser = request.getAttribute("IdUser");
        var tasksList = this.taskRepository.findByIdUser((UUID)IdUser);
        return tasksList;
    }
    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id){        
        var task = this.taskRepository.findById(id).orElse(null);

        if (task == null) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Tarefa não encontrada");
        }

        var IdUser = request.getAttribute("IdUser");

        if (!task.getIdUser().equals(IdUser)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Usuário não tem permição para alterar essa tarefa");
        }

        Utils.copyNonNullProperties(taskModel, task);
        var taskUpdated = this.taskRepository.save(task);
        return ResponseEntity.ok().body(taskUpdated);
    }
}
