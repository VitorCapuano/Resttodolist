package br.senai.sp.todolist.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.auth0.jwt.JWTVerifier;

import br.senai.sp.todolist.controller.UsuarioController;

public class JwtInterceptor extends HandlerInterceptorAdapter {

	//não esquecer de configuraro spring-context.xml
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HandlerMethod method = (HandlerMethod) handler;
		
		//System.out.println("Controller chamado ==>" + method.getBean().getClass().getSimpleName());
		//System.out.println("Método chamado ==>" + method.getMethod().getName());
		
		if (request.getRequestURI().contains("login")) {
			return true;
		}
		
		String token = request.getHeader("Authorization");
		try {
			//responsavel porimplementar verificar e validade do token
			JWTVerifier verifier = new JWTVerifier(UsuarioController.SECRET);
			Map<String, Object> claims = verifier.verify(token);
			//System.out.println("claims chamado ==>" + claims);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if (token == null) {
				response.sendError(HttpStatus.UNAUTHORIZED.value());
			} else {
				response.sendError(HttpStatus.FORBIDDEN.value());
			}
			return false;
		}		
	}
}
