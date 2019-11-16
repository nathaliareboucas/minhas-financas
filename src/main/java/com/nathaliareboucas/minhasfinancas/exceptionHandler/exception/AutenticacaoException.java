package com.nathaliareboucas.minhasfinancas.exceptionHandler.exception;

public class AutenticacaoException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public AutenticacaoException(String msg) {
		super(msg);
	}

}
