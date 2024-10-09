package br.com.study.carros.domain.enums;

public enum TipoCombustivel {
  GASOLINA("G"),
  ALCOOL("A"),
  FLEX("F"),
  HIBRIDO("H"),
  ELETRICO("E");

  private String tipo;

  TipoCombustivel(String tipo) {
    this.tipo = tipo;
  }

  public static TipoCombustivel getByTipo(String tipo) {
    TipoCombustivel tipoCombustivel = null;
    for (TipoCombustivel value : TipoCombustivel.values()) {
      if (value.tipo.equalsIgnoreCase(tipo)) {
        tipoCombustivel = value;
      }
    }
    return tipoCombustivel;
  }
}
