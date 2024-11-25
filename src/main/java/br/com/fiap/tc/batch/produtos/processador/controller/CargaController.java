package br.com.fiap.tc.batch.produtos.processador.controller;


import br.com.fiap.tc.batch.produtos.processador.domain.Carga;
import br.com.fiap.tc.batch.produtos.processador.usecase.SalvarCargaUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("carga")
@RequiredArgsConstructor
public class CargaController {

    private final SalvarCargaUsecase salvarCargaUsecase;

    @Value("${carga.input-path}")
    private String diretorio;

    @PostMapping(value ="/importar")
	public ResponseEntity<Void> handleFileUpload(@RequestParam("file") MultipartFile file) {

    	try {
    		Carga carga = new Carga("dados.csv" , diretorio, file.getBytes());
			salvarCargaUsecase.salvar(carga);

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}


	}


}
