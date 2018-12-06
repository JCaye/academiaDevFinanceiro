package br.com.academiadev.financeiro.exception;

public class UsuarioNaoEncontradoException extends ResourceNotFoundException {
	
	@Override
	public String getMessage() {
		return "Usuario não encontrado.";
	}
}
