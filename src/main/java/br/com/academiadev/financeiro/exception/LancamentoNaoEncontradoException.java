package br.com.academiadev.financeiro.exception;

public class LancamentoNaoEncontradoException extends ResourceNotFoundException {
	@Override
	public String getMessage() {
		return "Lançamento não encontrado.";
	}
}
