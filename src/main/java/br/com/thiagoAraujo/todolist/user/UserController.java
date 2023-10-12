package br.com.thiagoAraujo.todolist.user;

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

    @PostMapping("/")
    public void create(@RequestBody UserModel UserModel){
        System.out.println(UserModel.name);
        System.out.println(UserModel.userName);
        System.out.println(UserModel.password);
    }
}
