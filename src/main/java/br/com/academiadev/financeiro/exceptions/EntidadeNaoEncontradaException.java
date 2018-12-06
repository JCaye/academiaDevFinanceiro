package br.com.academiadev.financeiro.exceptions;

public class EntidadeNaoEncontradaException extends ResourceNotFoundException {
	@Override
	public String getMessage() {
		return "Entidade não encontrada.";
	}
}
