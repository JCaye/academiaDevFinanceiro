package br.com.academiadev.financeiro.exceptions;

public class ContaNaoEncontradaException extends ResourceNotFoundException {
	@Override
	public String getMessage() {
		return "Conta não encontrada.";
	}
}
