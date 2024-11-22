package br.com.fiap.tc.batch.produtos.processador.job;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.time.format.DateTimeFormatter;

public class ProdutoMapper implements FieldSetMapper<Produto> {
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	//private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


	@Override
	public Produto mapFieldSet(FieldSet fieldSet) throws BindException {
		Produto produto = new Produto();
		produto.setDescricao(fieldSet.readString("descricao"));
		produto.setPreco(fieldSet.readDouble("preco"));
		produto.setQuantidade(fieldSet.readInt("quantidade"));

		return produto;
	} 
}
