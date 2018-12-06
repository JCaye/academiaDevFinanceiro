package br.com.academiadev.financeiro.exception;

public abstract class APIException extends RuntimeException {
	public abstract String getMessage();
}
