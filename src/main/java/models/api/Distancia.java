package models.api;

public class Distancia {
  private String valor;
  private String unidad;

  public Distancia(String valor, String unidad) {
    this.valor = valor;
    this.unidad = unidad;
  }

  @Override
  public String toString() {
    return "Distancia{" +
        "valor='" + valor + '\'' +
        ", unidad='" + unidad + '\'' +
        '}';
  }
}
