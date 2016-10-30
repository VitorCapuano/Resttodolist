package br.senai.sp.todolist.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWTSigner;

import br.senai.sp.todolist.dao.UsuarioDAO;
import br.senai.sp.todolist.model.TokenJwt;
import br.senai.sp.todolist.model.Usuario;

@RestController
public class UsuarioController {

	public static final String SECRET = "todolistsenai";
	public static final String ISSUER = "http://www.sp.senai.br";
	
	@Autowired
	private UsuarioDAO dao;

	@RequestMapping(value = "/usuario", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Usuario> inserirUsuario(@RequestBody Usuario usuario) {
		try {
			dao.inserir(usuario);
			return ResponseEntity.created(new URI("/usuario/" + usuario.getId())).body(usuario);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/usuario", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Usuario> listar() {
		return dao.listar();
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<TokenJwt> logar(@RequestBody Usuario usuario) {
		try {
			usuario = dao.logar(usuario);
			if (usuario != null) {
				//hora em segundo que o token foi emitido
				long iat = System.currentTimeMillis() / 1000;
				// hora de expiração = emissão + 36000
				long exp = iat + 3600;
				// imprementação e local onde vai guradar o nome do token
				JWTSigner signer = new JWTSigner(SECRET);
				HashMap<String, Object> claims = new HashMap<>();
				claims.put("iss", ISSUER);
				claims.put("exp", exp);
				claims.put("iat", iat);
				claims.put("id_user", usuario.getId());
				String jwt = signer.sign(claims);
				// criar construtor na model
				TokenJwt token = new TokenJwt(jwt);
				return ResponseEntity.ok(token);
			} else {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

}
