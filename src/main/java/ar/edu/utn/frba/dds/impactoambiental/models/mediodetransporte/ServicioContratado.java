package ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte;

public class ServicioContratado implements MedioDeTransporte {
  private TipoDeServicioContratado tipoDeServicioContratado;

  public ServicioContratado(TipoDeServicioContratado tipoDeServicioContratado) {
    this.tipoDeServicioContratado = tipoDeServicioContratado;
  }

  @Override
  public Double carbonoEquivalentePorKM() {
    return tipoDeServicioContratado.carbonoEquivalentePorKM();
  }
  
  @Override
  public boolean esCompartible() {
    return true;
  }
}