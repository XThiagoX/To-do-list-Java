package br.com.thiagoAraujo.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * MODIFICADORES - tipos de acesso
 * public
 * private
 * protected
 * 
*/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/")
    public UserModel create(@RequestBody UserModel UserModel){
        var userCreated = this.userRepository.save(UserModel);
        return userCreated;
    }
}
