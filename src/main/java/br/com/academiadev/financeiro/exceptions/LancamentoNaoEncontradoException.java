package br.com.academiadev.financeiro.exceptions;

public class LancamentoNaoEncontradoException extends ResourceNotFoundException {
	@Override
	public String getMessage() {
		return "Lançamento não encontrado.";
	}
}
