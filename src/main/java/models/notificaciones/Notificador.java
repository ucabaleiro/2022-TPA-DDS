package models.notificaciones;

import models.organizacion.Contacto;

import java.util.List;

public interface Notificador {
  void enviarGuiaRecomendacion(Contacto contacto, String mensaje);
}
