package ar.edu.utn.frba.dds.impactoambiental.controllers;

import ar.edu.utn.frba.dds.impactoambiental.controllers.helpers.UsuariosHelper;
import ar.edu.utn.frba.dds.impactoambiental.models.forms.Form;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioUsuarios;
import com.google.common.collect.ImmutableMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class UsuarioController implements Controlador {
  private RepositorioUsuarios usuarios = RepositorioUsuarios.getInstance();
  private UsuariosHelper usuariosHelper = new UsuariosHelper();


  public ModelAndView crearUsuario(Request req, Response res) {
    return usuariosHelper.validarNuevoUsuario(Form.of(req))
        .fold(
            errores -> {
              // TODO: Manejo de errores
              return null;
            },
            dto -> {
              // TODO: Crear usuario
              return null;
            }
        );
  }

  public ModelAndView verLogin(Request req, Response resp) {
    if (req.session().attribute("usuario") != null) {
      resp.redirect("/");
      return null;
    }

    List<String> errores = Optional.ofNullable(req.queryParams("errores"))
        .map(err -> Arrays.asList(decode(err).split(", ")))
        .orElse(Collections.emptyList());

    return new ModelAndView(ImmutableMap.of("errores", errores), "login.html.hbs");
  }

  public Void iniciarSesion(Request req, Response res) {
    return usuariosHelper.obtenerUsuario(Form.of(req))
        .fold(
            errores -> {
              res.redirect("/login?errores=" + encode(String.join(", ", errores)));
              return null;
            },
            usuario -> {
              req.session().attribute("usuario", usuario);
              res.redirect("/");
              return null;
            }
        );
  }

  public ModelAndView cerrarSesion(Request request, Response response) {
    request.session().removeAttribute("usuario");
    response.redirect("/");
    return null;
  }
}
