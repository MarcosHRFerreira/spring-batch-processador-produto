package br.com.fiap.tc.batch.produtos.processador.gateway;


import br.com.fiap.tc.batch.produtos.processador.domain.Carga;

public interface JobGateway {

	public void execute(Carga carga);

}
