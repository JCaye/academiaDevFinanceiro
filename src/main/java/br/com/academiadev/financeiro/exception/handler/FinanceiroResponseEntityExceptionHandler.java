package br.com.academiadev.financeiro.exception.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.academiadev.financeiro.exception.ResourceNotFoundException;

@ControllerAdvice
public class FinanceiroResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	@Autowired
	private ObjectMapper objectMapper;
	
	@ExceptionHandler({
		ResourceNotFoundException.class
	})
	public ResponseEntity<Object> handleNotFoundExceptions(Exception e, WebRequest request) throws JsonProcessingException{
		final ObjectNode errorNode = objectMapper.createObjectNode();
		errorNode.put("code", 404);
		errorNode.put("message", e.getMessage());
		
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		
		return handleExceptionInternal(e, objectMapper.writeValueAsString(errorNode), headers, HttpStatus.NOT_FOUND, request);
	}
}
