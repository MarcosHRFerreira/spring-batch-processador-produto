package br.com.fiap.tc.batch.produtos.processador.gateway;


import br.com.fiap.tc.batch.produtos.processador.domain.Carga;

public interface FileGateway {
	
	public void salvar(Carga carga);

}
