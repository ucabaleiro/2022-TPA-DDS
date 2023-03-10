package ar.edu.utn.frba.dds.impactoambiental.models.organizacion;

import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Miembro;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Trayecto;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Sector extends EntidadPersistente {
  private String nombre;
  private UUID codigoInvite;
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "sector_id")
  private Set<Vinculacion> vinculaciones;

  protected Sector() {
  }

  public Sector(String nombre, List<Vinculacion> vinculaciones) {
    this.nombre = nombre;
    this.vinculaciones = new HashSet<>(vinculaciones);
    this.codigoInvite = UUID.randomUUID();
  }

  public String getNombre() {
    return nombre;
  }

  public UUID getCodigoInvite() {
    return codigoInvite;
  }

  public Set<Vinculacion> getVinculaciones() {
    return vinculaciones;
  }

  public void solicitarVinculacion(Vinculacion vinculacion) {
    this.vinculaciones.add(vinculacion);
  }

  public List<Vinculacion> getVinculacionesPendientes() {
    return this.vinculaciones.stream()
        .filter(vinculacion -> vinculacion.getEstado() == EstadoVinculo.PENDIENTE)
        .collect(Collectors.toList());
  }
  public List<Miembro> getAllMiembros() {
    return this.vinculaciones.stream()
        .map(Vinculacion::getMiembro)
        .collect(Collectors.toList());
  }

  public List<Miembro> getMiembros() {
    return this.vinculaciones.stream()
        .filter(vinculacion -> vinculacion.getEstado() == EstadoVinculo.ACEPTADO)
        .map(Vinculacion::getMiembro)
        .collect(Collectors.toList());
  }

  public List<Trayecto> getTrayectosEnPeriodo(Periodo periodo) {
    return getMiembros().stream()
        .flatMap(miembro -> miembro.getTrayectosEnPeriodo(periodo).stream())
        .distinct()
        .collect(Collectors.toList());
  }

  public Double huellaCarbono(Periodo periodo) {
    return getTrayectosEnPeriodo(periodo).stream()
        .mapToDouble(Trayecto::carbonoEquivalente)
        .sum();
  }

  public Double huellaCarbonoPorMiembro(Periodo periodo) {
    return huellaCarbono(periodo) / getMiembros().size();
  }
}
