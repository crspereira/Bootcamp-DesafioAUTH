/* Classe responsável por tratar de maneira apropriada o erro 500,
 * além de poder personalizar o body da resposta.
 */
package com.devsuperior.desafioAUTHEntrega.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	//Contrutor que passa a mensagem de erro para o contrutuor da
	//super classe RunTimeExcetion
	public ResourceNotFoundException(String msg) {
		super(msg);
	}

}
