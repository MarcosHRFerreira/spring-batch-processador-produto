package br.com.fiap.tc.batch.produtos.processador.job;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Table
@Entity
@Getter
@Setter
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long produtoId;
    private String descricao;
    private Double preco;
    private Integer quantidade;


	
}
