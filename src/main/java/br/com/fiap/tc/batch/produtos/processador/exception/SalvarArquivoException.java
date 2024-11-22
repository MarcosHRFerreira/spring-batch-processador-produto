package br.com.fiap.tc.batch.produtos.processador.exception;


import br.com.fiap.tc.batch.produtos.exception.SystemBaseException;
import lombok.Getter;

@Getter
public class SalvarArquivoException extends SystemBaseException {
	private static final long serialVersionUID = 4306545258260552901L;
	
	private final String code = "erroSalvarArquivo";//NOSONAR
	private final String message = "Erro ao salvar arquivo";//NOSONAR
	private final Integer httpStatus = 500;//NOSONAR
}
