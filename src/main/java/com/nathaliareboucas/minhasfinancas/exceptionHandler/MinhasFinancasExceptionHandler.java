package com.nathaliareboucas.minhasfinancas.exceptionHandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.nathaliareboucas.minhasfinancas.exceptionHandler.exception.AutenticacaoException;
import com.nathaliareboucas.minhasfinancas.exceptionHandler.exception.RegraNegocioException;


@ControllerAdvice
public class MinhasFinancasExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(AutenticacaoException.class)
	public ResponseEntity<Object> handleAutenticacao(AutenticacaoException ex, WebRequest request) {
		
		StandardError err = new StandardError(
				HttpStatus.BAD_REQUEST.value(), ex.getMessage() , System.currentTimeMillis());
		
		return handleExceptionInternal(ex, err, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(RegraNegocioException.class)
	public ResponseEntity<Object> handleRegraNegocio(RegraNegocioException ex, WebRequest request) {
		
		StandardError err = new StandardError(
				HttpStatus.BAD_REQUEST.value(), ex.getMessage() , System.currentTimeMillis());
		
		return handleExceptionInternal(ex, err, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

}
