package ar.edu.utn.frba.dds.impactoambiental.models.usuario;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;
import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.Validador;
import javax.persistence.Entity;

@Entity
public class Usuario extends EntidadPersistente {
  private String usuario;
  private String contrasena;

  protected Usuario() {}

  public Usuario(Validador<UsuarioDto> validador, UsuarioDto usuarioDto) {
    validador.validar(usuarioDto);
    this.contrasena = sha256Hex(usuarioDto.getContrasena());
    this.usuario = usuarioDto.getUsuario();
  }

  public String getUsuario() {
    return usuario;
  }

  public String getContrasena() {
    return contrasena;
  }
}