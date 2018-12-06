package br.com.academiadev.financeiro.exceptions;

public class UsuarioNaoEncontradoException extends ResourceNotFoundException {
	
	@Override
	public String getMessage() {
		return "Usuario não encontrado.";
	}
}
