package br.com.study.carros.domain.model;

import br.com.study.carros.domain.enums.TipoCombustivel;
import java.math.BigDecimal;
import java.time.Year;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "tb_carro")
public class Carro {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carro_seq")
  @SequenceGenerator(
      name = "carro_seq",
      sequenceName = "sq_carro",
      initialValue = 1,
      allocationSize = 1)
  private Long id;

  @Column(nullable = false, length = 50)
  private String nome;

  @Column(nullable = false)
  private Year ano;

  @Column(nullable = false)
  private boolean automatico;

  @Column(nullable = false)
  private int numeroPortas;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private TipoCombustivel tipoCombustivel;

  @Column(nullable = false)
  private BigDecimal preco;

  @OneToOne
  @JoinColumn(
          name = "id_marca_fk",
          foreignKey = @ForeignKey(name = "fk_carro_marca"),
          referencedColumnName = "id",
          nullable = false)
  private Marca marca;

  @ManyToMany private Set<Acessorio> acessorios;

  public Carro() {
  }

  public Carro(
      String nome,
      Year ano,
      boolean automatico,
      int numeroPortas,
      TipoCombustivel tipoCombustivel,
      BigDecimal preco,
      Marca marca) {
    this.nome = nome;
    this.ano = ano;
    this.automatico = automatico;
    this.numeroPortas = numeroPortas;
    this.tipoCombustivel = tipoCombustivel;
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

  public Year getAno() {
    return ano;
  }

  public void setAno(Year ano) {
    this.ano = ano;
  }

  public boolean isAutomatico() {
    return automatico;
  }

  public void setAutomatico(boolean automatico) {
    this.automatico = automatico;
  }

  public int getNumeroPortas() {
    return numeroPortas;
  }

  public void setNumeroPortas(int numeroPortas) {
    this.numeroPortas = numeroPortas;
  }

  public TipoCombustivel getTipoCombustivel() {
    return tipoCombustivel;
  }

  public void setTipoCombustivel(TipoCombustivel tipoCombustivel) {
    this.tipoCombustivel = tipoCombustivel;
  }

  public BigDecimal getPreco() {
    return preco;
  }

  public void setPreco(BigDecimal preco) {
    this.preco = preco;
  }

  public Marca getMarca() {
    return marca;
  }

  public void setMarca(Marca marca) {
    this.marca = marca;
  }

  public Set<Acessorio> getAcessorios() {
    return Collections.unmodifiableSet(acessorios);
  }

  public void addAcessorio(Acessorio acessorio) {
    if (this.acessorios == null) {
      this.acessorios = new HashSet<>();
    }
    this.acessorios.add(acessorio);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Carro carro = (Carro) o;
    return automatico == carro.automatico
        && numeroPortas == carro.numeroPortas
        && Objects.equals(id, carro.id)
        && Objects.equals(nome, carro.nome)
        && Objects.equals(ano, carro.ano)
        && tipoCombustivel == carro.tipoCombustivel
        && Objects.equals(preco, carro.preco);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nome, ano, automatico, numeroPortas, tipoCombustivel, preco);
  }
}
