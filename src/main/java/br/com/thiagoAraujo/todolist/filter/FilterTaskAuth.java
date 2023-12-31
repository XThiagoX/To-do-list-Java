package br.com.thiagoAraujo.todolist.filter;

import java.io.IOException;
import java.util.Base64;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.thiagoAraujo.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component

public class FilterTaskAuth extends OncePerRequestFilter {    

    @Autowired
    private IUserRepository userRepository;

    @Override 

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
            var servletPah = request.getServletPath();
            if (servletPah.startsWith("/tasks/")) {
            // autenticação (usuário e senha)
                // desencriptação
                var authorization = request.getHeader("Authorization");                        
                var authEncoded = authorization.substring("Basic".length()).trim();
                byte[] authDecoded = Base64.getDecoder().decode(authEncoded);
                var authString = new String(authDecoded);
                // separaçãoda autenticação
                String[] credentials = authString.split(":");
                String username = credentials[0];
                String password = credentials[1];

                System.out.println("Authorization:");
                System.out.println(username);
                System.out.println(password);

                // validação
                var user = this.userRepository.findByUserName(username);
                if (user == null) {
                    response.sendError(401);
                }else{
                    //validar senha
                    var passeordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                    if (passeordVerify.verified) {
                        request.setAttribute("IdUser", user.getId());
                        filterChain.doFilter(request, response);
                    }else{
                    response.sendError(401);
                    }    
                }
            }else{
                filterChain.doFilter(request, response);
            }
        }
}
