package br.com.thiagoAraujo.todolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

// -> criação de rota
@Controller
@RestController
@RequestMapping("/fistRute")
public class FirstController{
    @GetMapping("/")
    // Método de Classe (funcionalidade de uma classe)
    public String firstMensage(){
        return "Tá voando pai";
    }
}