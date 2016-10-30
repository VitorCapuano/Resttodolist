package br.senai.sp.todolist.model;

public class TokenJwt {

		private String token;

		public TokenJwt(String token) {
			this.token = token;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}
		
		
}
