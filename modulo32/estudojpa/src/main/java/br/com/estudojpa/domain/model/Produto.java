package br.com.estudojpa.domain.model;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "tb_produto")
public class Produto {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "produto_seq")
  @SequenceGenerator(
      name = "produto_seq",
      sequenceName = "sq_produto",
      initialValue = 1,
      allocationSize = 1)
  private Long id;

  @Column(name = "codigo", length = 50, nullable = false, unique = true)
  private String codigo;

  @Column(name = "nome", length = 50, nullable = false)
  private String nome;

  @Column(name = "marca", length = 50, nullable = false)
  private String marca;

  @Column(name = "preco", nullable = false)
  private BigDecimal preco;

  public Produto(String codigo, String nome, String marca, BigDecimal preco) {
    this.codigo = codigo;
    this.nome = nome;
    this.marca = marca;
    this.preco = preco;
  }

  public Produto() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getMarca() {
    return marca;
  }

  public void setMarca(String marca) {
    this.marca = marca;
  }

  public BigDecimal getPreco() {
    return preco;
  }

  public void setPreco(BigDecimal preco) {
    this.preco = preco;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Produto produto = (Produto) o;
    return Objects.equals(id, produto.id)
        && Objects.equals(codigo, produto.codigo)
        && Objects.equals(nome, produto.nome)
        && Objects.equals(marca, produto.marca)
        && Objects.equals(preco, produto.preco);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, codigo, nome, marca, preco);
  }

  @Override
  public String toString() {
    return "Produto{" +
            "id=" + id +
            ", codigo='" + codigo + '\'' +
            ", nome='" + nome + '\'' +
            ", marca='" + marca + '\'' +
            ", preco=" + preco +
            '}';
  }
}
