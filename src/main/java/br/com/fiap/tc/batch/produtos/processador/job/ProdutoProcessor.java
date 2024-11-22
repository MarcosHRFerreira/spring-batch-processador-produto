package br.com.fiap.tc.batch.produtos.processador.job;

import org.springframework.batch.item.ItemProcessor;

public class ProdutoProcessor implements ItemProcessor<Produto, Produto> {

	@Override
	public Produto process(Produto item) throws Exception {
//		if (item.getTipoIngresso().equalsIgnoreCase("vip")) {
//			item.setTaxaAdm(130.0);
//		} else if (item.getTipoIngresso().equalsIgnoreCase("camarote")) {
//			item.setTaxaAdm(80.0);
//		} else {
//			item.setTaxaAdm(50.0);
//		}

		return item; 

	}
}
