package com.example.examenmetabolismo;

import com.example.examenmetabolismo.domain.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private TextField txtPeso;
    @FXML
    private TextField txtNombre;
    @FXML
    private Label lblMensaje;
    @FXML
    private Button btnGuardar;
    @FXML
    private TextField txtTalla;
    @FXML
    private TextField txtEdad;
    @FXML
    private TextArea txtObservaciones;
    @FXML
    private ComboBox<String> comboActividad;
    @FXML
    private Button btnDescargar;
    @FXML
    private RadioButton rbMujer;
    @FXML
    private RadioButton rbHombre;

    private final ToggleGroup toggleGroup = new ToggleGroup();
    @FXML
    public void guardar(ActionEvent actionEvent) {
        if (!txtNombre.getText().isEmpty() && comboActividad.getValue() != null && !txtEdad.getText().isEmpty()
                 &&!txtPeso.getText().isEmpty() && toggleGroup.getSelectedToggle() !=null && !txtTalla.getText().isEmpty() && !txtObservaciones.getText().isEmpty()) {
            Cliente c = new Cliente();
            c.setNombre(txtNombre.getText());
            c.setTipoActividad(comboActividad.getValue());
            c.setEdad(Integer.valueOf(txtEdad.getText()));
            c.setPeso(Double.valueOf(txtPeso.getText()));
            RadioButton seleccion = (RadioButton) toggleGroup.getSelectedToggle();
            c.setSexo(seleccion.getText());
            c.setTalla(Double.valueOf(txtTalla.getText()));

            double ger;
            double get = 0;
            if (c.getSexo().equals("Hombre")){
                 ger = (66.473 + (13.751* (c.getPeso())) + (5.0033*(c.getTalla())) - (6.755*(c.getEdad())));
                HashMap<String, Double> comboAct = new HashMap<>();
                comboAct.put("Sedentario", 1.3);
                comboAct.put("Moderado", 1.6);
                comboAct.put("Activo", 1.7);
                comboAct.put("Muy activo", 2.1);
                if (comboAct.containsKey(c.getTipoActividad())) {
                    get = ger * comboAct.get(c.getTipoActividad());
                }

            }else {
                ger = (655.0955 + (9.463 * (c.getPeso())) + (1.8496 * (c.getTalla())) - (4.6756 * (c.getEdad())));
                HashMap<String, Double> comboActMujer = new HashMap<>();
                comboActMujer.put("Sedentario", 1.3);
                comboActMujer.put("Moderado", 1.5);
                comboActMujer.put("Activo", 1.6);
                comboActMujer.put("Muy activo", 1.9);
                if (comboActMujer.containsKey(c.getTipoActividad())) {
                    get = ger * comboActMujer.get(c.getTipoActividad());
                }

            }
            lblMensaje.setText("El cliente "+ c.getNombre()+" tiene un GER de "+ ger + " y un GET de "+ get);
        } else {
            lblMensaje.setText("Introduce todos los campos.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> actividades = FXCollections.observableArrayList();
        actividades.addAll("Sedentario", "Moderado", "Activo","Muy activo");
        comboActividad.setItems(actividades);

        rbMujer.setToggleGroup(toggleGroup);
        rbHombre.setToggleGroup(toggleGroup);
    }

    @FXML
    public void descargar(ActionEvent actionEvent) throws SQLException, JRException {
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/informacioncliente", "root", "");
        JasperPrint jasperPrint = JasperFillManager.fillReport("ListadoClientes.jasper", null, c);

        JasperViewer.viewReport(jasperPrint, false);

        JRPdfExporter exp = new JRPdfExporter();
        exp.setExporterInput(new SimpleExporterInput(jasperPrint));
        exp.setExporterOutput(new SimpleOutputStreamExporterOutput("ListadoClientes.pdf"));
        exp.setConfiguration(new SimplePdfExporterConfiguration());
        exp.exportReport();
    }
}