/* Classe responsável por tratar de maneira apropriada o erro
 * de Exclusão de Objeto com depencia relacional além de poder
 * personalizar o body da resposta.
 */
package com.devsuperior.desafioAUTHEntrega.services.exceptions;

public class ForbiddenException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	//Contrutor que passa a mensagem de erro para o contrutuor da
	//super classe RunTimeExcetion
	public ForbiddenException(String msg) {
		super(msg);
	}

}
