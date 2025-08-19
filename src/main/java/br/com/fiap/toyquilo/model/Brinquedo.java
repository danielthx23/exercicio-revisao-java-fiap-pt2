package br.com.fiap.toyquilo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "JAVA_TDS_TB_BRINQUEDO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "seq_brinquedo_id", sequenceName = "seq_brinquedo_id", allocationSize = 1)
public class Brinquedo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_brinquedo_id")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "classificacao")
    private String classificacao;

    @Column(name = "tamanho", nullable = false)
    private Double tamanho;

    @Column(name = "preco", nullable = false)
    private Double preco;
}
