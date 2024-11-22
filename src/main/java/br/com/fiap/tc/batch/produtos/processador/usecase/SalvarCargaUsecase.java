package br.com.fiap.tc.batch.produtos.processador.usecase;


import br.com.fiap.tc.batch.produtos.processador.domain.Carga;
import br.com.fiap.tc.batch.produtos.processador.gateway.FileGateway;
import br.com.fiap.tc.batch.produtos.processador.gateway.JobGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalvarCargaUsecase {

	private final FileGateway fileGateway;
	private final JobGateway jobGateway;

	public void salvar(Carga carga) {
		fileGateway.salvar(carga);
		jobGateway.execute(carga);
	}
}
