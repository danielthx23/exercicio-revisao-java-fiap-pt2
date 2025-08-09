package br.com.fiap.trankalma.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TDS_TB_BRINQUEDO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "seq_brinquedo_id", sequenceName = "seq_brinquedo_id", allocationSize = 1)
public class Brinquedo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_brinquedo_id")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "TIPO", nullable = false)
    private String tipo;

    @Column(name = "CLASSIFICACAO", nullable = false)
    private String classificacao;

    @Column(name = "TAMANHO", nullable = false)
    private Float tamanho;

    @Column(name = "PRECO", nullable = false)
    private Double preco;
}
