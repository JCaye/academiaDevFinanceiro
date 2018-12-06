package br.com.academiadev.financeiro.exception;

public class ContaNaoEncontradaException extends ResourceNotFoundException {
	@Override
	public String getMessage() {
		return "Conta não encontrada.";
	}
}
