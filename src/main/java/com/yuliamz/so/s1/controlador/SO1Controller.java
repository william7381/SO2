package com.yuliamz.so.s1.controlador;

import com.yuliamz.so.s1.Modelo.AdministradorProcesos;
import com.yuliamz.so.s1.Modelo.Proceso;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Yuliamz
 */
public class SO1Controller implements Initializable {

    ObservableList<Proceso> listaProcesos;
    AdministradorProcesos ap;

    @FXML private AnchorPane root;
    @FXML private Pane panelProcesos, panelReportes;
    @FXML private TextField txtNombreProceso;
    @FXML private Spinner<Integer> numTiempo;
    @FXML private CheckBox checkBloqueo;
    @FXML private Label infoBloqueo, errorNombre;
    @FXML private Button btnCrearProceso, btnLimpiarCampos, btnIniciar, btnSalir, btnAcercaDe, btnReportes, btnProcesos;
    @FXML private TableView<Proceso> tablaProcesos;
    @FXML private TableColumn<Proceso, String> columnaNombre, ColumnaEstado;
    @FXML private TableColumn<Proceso, Integer> columnaTiempo;
    @FXML private ListView<String> viewListos, viewDespachados, viewEjecucion, viewExpTiempo, viewBloqueando, viewBloqueados, viewDespertados, viewFinalizados;

    @FXML
    void bloquear(ActionEvent event) {
        infoBloqueo.setVisible(checkBloqueo.isSelected());
    }

    @FXML
    void limpiar(ActionEvent event) {
        checkBloqueo.setSelected(false);
        numTiempo.getValueFactory().setValue(5);
        infoBloqueo.setVisible(false);
        errorNombre.setVisible(false);
    }

    boolean isValidName(String nombre){
        if ((!nombre.matches(".*\\d+.*"))) {
            errorNombre.setText("El nombre debe finalizar en un número");
            errorNombre.setVisible(true);
            return false;
        }
        if(listaProcesos.stream().anyMatch(e -> e.getNombre().equals(nombre))){
            errorNombre.setText("El nombre de este proceso ya existe, por favor elija uno nuevo");
            errorNombre.setVisible(true);
            return false;
        }
        return true;
    }
    
    @FXML
    void crearProceso(ActionEvent event) {
        String nombre= txtNombreProceso.getText().trim();
        if (isValidName(nombre)) {            
            listaProcesos.add(new Proceso(nombre, numTiempo.getValue(), checkBloqueo.isSelected()));
            limpiar(event);
        }
    }
    @FXML
    void reportes(ActionEvent event) {
        if (!panelReportes.isVisible()) mostrarReportes();
    }
    @FXML
    void procesos(ActionEvent event) {
        if (!panelProcesos.isVisible()) mostrarProcesos();
    }

    @FXML
    void iniciarProcesos(ActionEvent event) {
        ap = new AdministradorProcesos(new ArrayList<>(listaProcesos));
        try {
            ap.iniciarSecuencia();
            viewListos.setItems(getObservableListFrom(ap.getListos()));
            viewDespachados.setItems(getObservableListFrom(ap.getDespachados()));
            viewEjecucion.setItems(getObservableListFrom(ap.getEjecucion()));
            viewExpTiempo.setItems(getObservableListFrom(ap.getExpiracionTiempo()));
            viewBloqueando.setItems(getObservableListFrom(ap.getBloqueando()));
            viewBloqueados.setItems(getObservableListFrom(ap.getBloqueados()));
            viewDespertados.setItems(getObservableListFrom(ap.getDespertados()));
            viewFinalizados.setItems(getObservableListFrom(ap.getFinalizados()));

            mostrarReportes();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(SO1Controller.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error inesperado");
            alert.setHeaderText("Ocurrió un error en la ejecución");
            alert.setContentText("Asegurese que la información insertada sea correcta");
            alert.showAndWait();
        }

    }

    void mostrarReportes() {
        panelProcesos.setVisible(false);
        panelReportes.setVisible(true);
    }

    void mostrarProcesos() {
        panelReportes.setVisible(false);
        panelProcesos.setVisible(true);
    }

    ObservableList<String> getObservableListFrom(ArrayList<Proceso> list) {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        list.forEach(e -> observableList.add(e.getNombre() + " - " + e.getTiempo() + "(u)"));
        return observableList;
    }

    @FXML
    void acercaDe(ActionEvent event) {
         Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Acerca de...");
            alert.setHeaderText("Software 1 de Sistemas Operativos");
            alert.setContentText("Autores:\n    *Julian David Grijalba Bernal\n    *William Gil\n\nThird parties icons copyright\n"
                    + "Dave Gandy © SIL Open Font License (OFL)");
            alert.initOwner(panelProcesos.getScene().getWindow());
            alert.showAndWait();
    }

    @FXML
    void salir(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listaProcesos = FXCollections.observableArrayList();
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaTiempo.setCellValueFactory(new PropertyValueFactory<>("tiempo"));
        ColumnaEstado.setCellValueFactory(new PropertyValueFactory<>("isBloqueado"));
        ColumnaEstado.setCellValueFactory(e -> {
            return new ReadOnlyStringWrapper(e.getValue().isBloqueado() ? "Bloqueado" : "Sin bloqueo");
        });

        tablaProcesos.setItems(listaProcesos);
    }

}
//<?import com.yuliamz.so.s1.Vista.*?>