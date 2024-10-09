package br.com.study.carros.domain.model;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "tb_acessorio")
public class Acessorio {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acessorio_seq")
  @SequenceGenerator(
      name = "acessorio_seq",
      sequenceName = "sq_acessorio",
      initialValue = 1,
      allocationSize = 1)
  private Long id;

  @Column(nullable = false, length = 50)
  private String nome;

  @Column(nullable = false, length = 50)
  private String descricao;

  @Column(nullable = false)
  private BigDecimal preco;

  @OneToOne
  @JoinColumn(
      name = "id_marca_fk",
      foreignKey = @ForeignKey(name = "fk_acessorio_marca"),
      referencedColumnName = "id",
      nullable = false)
  private Marca marca;

  @ManyToMany(mappedBy = "acessorios")
  private Set<Carro> carros;

  public Acessorio() {
  }

  public Acessorio(String nome, String descricao, BigDecimal preco, Marca marca) {
    this.nome = nome;
    this.descricao = descricao;
    this.preco = preco;
    this.marca = marca;
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

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public BigDecimal getPreco() {
    return preco;
  }

  public void setPreco(BigDecimal preco) {
    this.preco = preco;
  }

  public Set<Carro> getCarros() {
    return Collections.unmodifiableSet(carros);
  }

  public Marca getMarca() {
    return marca;
  }

  public void setMarca(Marca marca) {
    this.marca = marca;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Acessorio acessorio = (Acessorio) o;
    return Objects.equals(id, acessorio.id)
        && Objects.equals(nome, acessorio.nome)
        && Objects.equals(descricao, acessorio.descricao)
        && Objects.equals(preco, acessorio.preco)
        && Objects.equals(marca, acessorio.marca);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nome, descricao, preco, marca);
  }
}
