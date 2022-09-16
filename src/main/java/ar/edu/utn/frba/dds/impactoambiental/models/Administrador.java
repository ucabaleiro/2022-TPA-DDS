package ar.edu.utn.frba.dds.impactoambiental.models;

import ar.edu.utn.frba.dds.impactoambiental.models.validador.Validador;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

public class Administrador {
  private String usuario;
  private String contrasena;

  public Administrador(Validador validador, String usuario, String contrasena) {
    validador.validar(usuario, contrasena);
    this.contrasena = sha256Hex(contrasena);
    this.usuario = usuario;
  }

  public String getUsuario() {
    return usuario;
  }

  public String getContrasena() {
    return contrasena;
  }
}