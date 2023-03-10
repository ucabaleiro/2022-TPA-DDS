package ar.edu.utn.frba.dds.impactoambiental.models;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.impactoambiental.models.da.DatoActividad;
import ar.edu.utn.frba.dds.impactoambiental.models.da.DatosActividadesParser;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodicidad;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DatosActividadesParserTest extends BaseTest {
  // [TPA2]: Se debe permitir la carga de mediciones por parte de una Organización, respetando la estructura del archivo
  // mencionada.
  // [TPA2]: Al cargar los Factores de Emisión (FE, entrega anterior), se debe validar que sus unidades coincidan con
  // las del Tipo de Consumo (TC) asociado.

  @Test
  public void sePuedeCargarDatosDeActividadConPeriodicidadMensual() {
    when(lector.getLineas()).thenReturn(Arrays.asList(
        "TIPO_CONSUMO;VALOR;PERIODICIDAD;PERIODO",
        "ELECTRICIDAD;122;MENSUAL;03/2002"
    ));
    DatosActividadesParser parser = crearParserDatosDeActividad();

    List<DatoActividad> datosActividad = parser.getDatosActividad();

    assertThat(datosActividad.get(0))
        .extracting("valor", "periodo.periodicidad", "periodo.inicioPeriodo", "tipoDeConsumo")
        .containsExactly(122.0, Periodicidad.MENSUAL, LocalDate.of(2002, 3, 1), electricidad);
  }

  @Test
  public void sePuedeCargarDatosDeActividadConPeriodicidadAnual() {
    when(lector.getLineas()).thenReturn(Arrays.asList(
        "TIPO_CONSUMO;VALOR;PERIODICIDAD;PERIODO",
        "NAFTA;5;ANUAL;2020"
    ));
    DatosActividadesParser parser = crearParserDatosDeActividad();

    List<DatoActividad> datosActividad = parser.getDatosActividad();

    assertThat(datosActividad.get(0))
        .extracting("valor", "periodo.periodicidad","periodo.inicioPeriodo", "tipoDeConsumo")
        .containsExactly(5.0, Periodicidad.ANUAL, LocalDate.of(2020,1,1), nafta);
  }

  @Test
  public void noSePuedenCargarDAsConCamposFaltantes() {
    DatosActividadesParser parser = crearParserDatosDeActividad();
    when(lector.getLineas()).thenReturn(Arrays.asList(
        "TIPO_CONSUMO;VALOR;PERIODICIDAD;PERIODO",
        "122;MENSUAL;03/2002",
        "NAFTA;5;2020"
    ));

    assertThatThrownBy(parser::getDatosActividad)
        .isExactlyInstanceOf(IllegalArgumentException.class)
        .hasMessage("La linea no tiene el numero adecuado de campos.");
  }

  @Test
  public void noSePuedenCargarTiposDeConsumoInexistentes() {
    DatosActividadesParser parser = crearParserDatosDeActividad();
    when(lector.getLineas()).thenReturn(Arrays.asList(
        "TIPO_CONSUMO;VALOR;PERIODICIDAD;PERIODO",
        "FANTA;5;ANUAL;2020"
    ));
    
    assertThatThrownBy(parser::getDatosActividad)
        .isExactlyInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("FANTA");
  }
}
