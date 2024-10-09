package br.com.study.carros.domain.model;

import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "tb_marca")
public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "marca_seq")
    @SequenceGenerator(
            name = "marca_seq",
            sequenceName = "seq_marca",
            initialValue = 1,
            allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nome;
    @Column(nullable = false, length = 20)
    private String paisOrigem;

    public Marca() {
    }

    public Marca(String nome, String paisOrigem) {
        this.nome = nome;
        this.paisOrigem = paisOrigem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPaisOrigem() {
        return paisOrigem;
    }

    public void setPaisOrigem(String paisOrigem) {
        this.paisOrigem = paisOrigem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Marca marca = (Marca) o;
        return Objects.equals(id, marca.id) && Objects.equals(nome, marca.nome) && Objects.equals(paisOrigem, marca.paisOrigem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, paisOrigem);
    }
}
