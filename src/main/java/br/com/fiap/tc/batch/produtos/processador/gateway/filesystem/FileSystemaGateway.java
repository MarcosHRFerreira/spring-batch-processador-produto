package br.com.fiap.tc.batch.produtos.processador.gateway.filesystem;


import br.com.fiap.tc.batch.produtos.processador.domain.Carga;
import br.com.fiap.tc.batch.produtos.processador.exception.SalvarArquivoException;
import br.com.fiap.tc.batch.produtos.processador.gateway.FileGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
public class FileSystemaGateway implements FileGateway {

	@Override
	public void salvar(Carga carga) {
		try {
			
			Path caminhoArquivo = Paths.get(carga.getDiretorio(), carga.getNome());
			
			Files.createDirectories(caminhoArquivo.getParent());

			Files.write(caminhoArquivo, carga.getBinario());
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new SalvarArquivoException();
		}
	}
}
