package com.allianz.selenium.behaviour.pre.alfa.consultas;

import com.allianz.selenium.page.common.*;
import com.allianz.selenium.page.consultas.*;
import com.allianz.selenium.utils.annotations.SeleniumTest;
import com.allianz.selenium.utils.listeners.BooleanExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SeleniumTest
@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@DisplayName("Consultas Generales eContact")
public class ConsultasGeneralesTest {

    @Autowired
    private UrlPage urlPage;

    @Autowired
    private IndexPage indexPage;

    @Autowired
    private LoginPage loginPage;

    @Autowired
    private EContactMenuPage eContactMenuPage;

    @Autowired
    private DatosPopUpPage datosPopUpPage;

    @Autowired
    private NuevaLlamadaPage nuevaLlamadaPage;

    @Autowired
    private VistaGlobalPage vistaGlobalPage;

    @Autowired
    private DatosClientePage datosClientePage;

    @Autowired
    private VistaMatriculaPage vistaMatriculaPage;

    @Autowired
    private DetalleSiniestroPage detalleSiniestroPage;

    @Autowired
    private DetalleReciboPage detalleReciboPage;

    @Autowired
    private DetallePolizaPage detallePolizaPage;

    @Autowired
    private FramePage framePage;

    @Autowired
    private UtilsPage utilsPage;

    private static final String USERNAME = "BRINMCS0";
    private static final String PASSWORD = "despwd";
    private static final String CPF = "00486250000183";
    private static final String POLIZA = "103005864";
    private static final String RECIBO = "232989451";
    private static final String SINIESTRO = "205985793";
    private static final String MATRICULA = "OOL7636";
    private static final String MEDIADOR = "2511120";
    private static final String SUCURSAL = "251";

    @BeforeEach
    public void setUp() {
        if(!BooleanExecutor.setUp) {
            urlPage.loadEContactIndex();
            indexPage.clickLoginButton();
            loginPage.inputUserName(USERNAME);
            loginPage.inputPassword(PASSWORD);
            utilsPage.makeScreenshot();
            loginPage.clickEnterButton();
            utilsPage.waitForJStoLoad();
            utilsPage.sleepForFirefox(3000);

            eContactMenuPage.changeLanguageTo("pt [BR]");
            BooleanExecutor.setUp = true;
        }
    }

    @Test
    //añadir steps
    @DisplayName("Búsqueda por NIF Cliente")
    public void busquedaNIFCliente() {
        eContactMenuPage.clickNuevaLlamadaButton();

        nuevaLlamadaPage.selectClienteInterlocutor();
        nuevaLlamadaPage.inputNif(CPF);
        utilsPage.makeScreenshot();
        nuevaLlamadaPage.clickAceptarLlamadaButton();
        utilsPage.waitForJStoLoad();
        utilsPage.sleep(2000);

        datosPopUpPage.clickNoTitularCheckButton();

        assertEquals(CPF,vistaGlobalPage.getNifText());
        utilsPage.makeScreenshot();
        vistaGlobalPage.clickNombre();

        assertTrue(datosClientePage.isDatosCliente());
        utilsPage.makeScreenshot();
    }

    @Test
    @DisplayName("Búsqueda por NIF Prestador")
    public void busquedaNIFPrestador() {
        eContactMenuPage.clickNuevaLlamadaButton();

        nuevaLlamadaPage.selectPrestadorInterlocutor();
        nuevaLlamadaPage.inputNif(CPF);
        utilsPage.makeScreenshot();
        nuevaLlamadaPage.clickAceptarLlamadaButton();

        assertEquals(CPF,vistaGlobalPage.getNifText());
        utilsPage.makeScreenshot();
    }

    @Test
    @DisplayName("Búsqueda por Póliza")
    public void busquedaPoliza() {
        eContactMenuPage.clickNuevaLlamadaButton();

        nuevaLlamadaPage.selectClienteInterlocutor();
        nuevaLlamadaPage.inputPoliza(POLIZA);
        utilsPage.makeScreenshot();

        nuevaLlamadaPage.clickAceptarLlamadaButton();
        utilsPage.waitForJStoLoad();
        utilsPage.sleep(3000);

        datosPopUpPage.clickNoTitularCheckButton();

        assertTrue(vistaGlobalPage.isRelacionPolizas());
        utilsPage.makeScreenshot();
    }

    @Test
    @DisplayName("Búsqueda por Matrícula")
    public void busquedaMatricula() {
        eContactMenuPage.clickNuevaLlamadaButton();

        nuevaLlamadaPage.selectClienteInterlocutor();
        nuevaLlamadaPage.inputMatricula(MATRICULA);
        utilsPage.makeScreenshot();

        nuevaLlamadaPage.clickAceptarLlamadaButton();
        utilsPage.waitForJStoLoad();
        utilsPage.sleep(2000);

        datosPopUpPage.clickNoTitularCheckButton();

        assertTrue(vistaGlobalPage.isRelacionPolizas());
        utilsPage.makeScreenshot();
    }

    @Test
    @DisplayName("Búsqueda por Matrícula (Prestador)")
    public void busquedaMatriculaPrestador() {
        eContactMenuPage.clickNuevaLlamadaButton();

        nuevaLlamadaPage.selectPrestadorInterlocutor();
        nuevaLlamadaPage.inputMatricula(MATRICULA);
        utilsPage.makeScreenshot();
        nuevaLlamadaPage.clickAceptarLlamadaButton();

        assertEquals("VISTA MATRÍCULA "+MATRICULA,vistaMatriculaPage.getVistaMatriculaText());
        utilsPage.makeScreenshot();
    }

    @Test
    @DisplayName("Búsqueda por Siniestro")
    public void busquedaSiniestro() {
        eContactMenuPage.clickNuevaLlamadaButton();

        nuevaLlamadaPage.selectPrestadorInterlocutor();
        nuevaLlamadaPage.inputSiniestro(SINIESTRO);
        utilsPage.makeScreenshot();
        nuevaLlamadaPage.clickAceptarLlamadaButton();

        assertTrue(detalleSiniestroPage.isDetalleSiniestro());
        utilsPage.makeScreenshot();
        assertEquals(SINIESTRO,detalleSiniestroPage.getSiniestroValue());
    }

    @Test
    @DisplayName("Búsqueda por Recibo")
    public void busquedaRecibo() {
        eContactMenuPage.clickNuevaLlamadaButton();

        nuevaLlamadaPage.selectPrestadorInterlocutor();
        nuevaLlamadaPage.inputRecibo(RECIBO);
        utilsPage.makeScreenshot();
        nuevaLlamadaPage.clickAceptarLlamadaButton();

        assertTrue(detalleReciboPage.isDetalleRecibo());
        utilsPage.makeScreenshot();
    }

    @Test
    @DisplayName("Búsqueda por Póliza y Mediador")
    public void busquedaPolizaMediador() {
        eContactMenuPage.clickNuevaLlamadaButton();

        nuevaLlamadaPage.selectMediadorInterlocutor();
        nuevaLlamadaPage.inputSucursal(SUCURSAL);
        nuevaLlamadaPage.inputMediador(MEDIADOR);
        utilsPage.makeScreenshot();
        nuevaLlamadaPage.clickCambiarMediadorButton();
        utilsPage.waitForPageLoad();
        utilsPage.sleepForFirefox(2500);

        nuevaLlamadaPage.inputPoliza(POLIZA);
        utilsPage.makeScreenshot();
        nuevaLlamadaPage.clickAceptarLlamadaButton();
        utilsPage.acceptAlert();

        assertTrue(detallePolizaPage.isDetallePoliza());
        utilsPage.makeScreenshot();
    }
}
