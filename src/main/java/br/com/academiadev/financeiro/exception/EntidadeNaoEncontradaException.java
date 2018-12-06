package br.com.academiadev.financeiro.exception;

public class EntidadeNaoEncontradaException extends ResourceNotFoundException {
	@Override
	public String getMessage() {
		return "Entidade não encontrada.";
	}
}
