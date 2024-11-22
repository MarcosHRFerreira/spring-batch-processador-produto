package br.com.fiap.tc.batch.produtos.processador.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Carga {
	private String nome;
	private String diretorio;

	private byte[] binario;
	private LocalDateTime dataHora;

	public Carga(String nome, String diretorio, byte[] binario) {
		this.nome = nome;
		this.diretorio = diretorio;
		this.binario = binario;
		this.dataHora = LocalDateTime.now();
	}
}
