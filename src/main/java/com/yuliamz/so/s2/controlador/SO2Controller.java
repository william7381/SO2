package com.yuliamz.so.s2.controlador;

import com.yuliamz.so.s2.Modelo.AdministradorProcesos;
import com.yuliamz.so.s2.Modelo.Proceso;
import com.yuliamz.so.s2.Vista.MaskField;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Yuliamz
 */
public class SO2Controller implements Initializable {

    ObservableList<Proceso> listaProcesos;
    AdministradorProcesos ap;

    @FXML private AnchorPane root;
    @FXML private Pane panelProcesos, panelReportes;
    @FXML private MaskField txtNombreProceso;
    @FXML private MaskField numTiempo;
    @FXML private CheckBox checkBloqueo;
    @FXML private Label infoBloqueo, errorNombre;
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
        numTiempo.setPlainText("5");
        txtNombreProceso.setPlainText("P");
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
            errorNombre.setText("El nombre ya registrado, por favor elija uno nuevo");
            errorNombre.setVisible(true);
            return false;
        }
        if(numTiempo.getText().equals("")){
            errorNombre.setText("El tiempo del proceso no puede estar vacío");
            errorNombre.setVisible(true);
            return false;
        }
        if(numTiempo.getText().replaceAll("0+", "0").equals("0")){
            errorNombre.setText("El tiempo del proceso debe ser superior a 0");
            errorNombre.setVisible(true);
            return false;
        }
        return true;
    }
    
    private boolean isValidPriority(int priority) {
        if(priority > 0 && listaProcesos.stream().anyMatch(p -> p.getPrioridad() != priority)) {
            return true;
        }
        return false;
    }
    
    @FXML
    void crearProceso(ActionEvent event) {
        String nombre = txtNombreProceso.getText().trim();
//        int prioridad = maskFilePrioridad.getText().trim();
        if (isValidName(nombre) /*&& isValidPriority(prioridad)*/) {
            int tiempo = Integer.parseInt(numTiempo.getText());
            listaProcesos.add(new Proceso(nombre, tiempo, checkBloqueo.isSelected(), 0, 0, true, null));
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
        if (listaProcesos.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("No hay procesos");
            alert.setHeaderText("No existen procesos para la simulación");
            alert.setContentText("Asegurese de ingresar al menos un(1) proceso antes de iniciar la simulación");
            alert.initOwner(panelProcesos.getScene().getWindow());
            alert.showAndWait();
        }else{
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
            Logger.getLogger(SO2Controller.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error inesperado");
            alert.setHeaderText("Ocurrió un error en la ejecución");
            alert.setContentText("Asegurese que la información insertada sea correcta");
            alert.initOwner(panelProcesos.getScene().getWindow());
            alert.showAndWait();
        }
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
        alert.setHeaderText("Software 2 de Sistemas Operativos");
        alert.setContentText("Este software tiene como objetivo simular\nla transmicion de estados de los procesos\nmanejados mediante una computadora.\n\nAutores:\n    *Julian David Grijalba Bernal\n    *William Desiderio Gil Farfan\n\nThird parties icons copyright\n"
                + "Dave Gandy © SIL Open Font License (OFL)");
        alert.initOwner(panelProcesos.getScene().getWindow());
        alert.showAndWait();
    }

    @FXML
    void salir(ActionEvent event) {
        System.exit(0);
    }
    
    @FXML
    private void eliminarProceso(ActionEvent event) {
        if (tablaProcesos.getSelectionModel().getSelectedIndex() >= 0) {
            tablaProcesos.getItems().remove(tablaProcesos.getSelectionModel().getFocusedIndex());
        }else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("No se puede eliminar");
            alert.setHeaderText("Debe seleccionar el proceso que desea eliminar");
            alert.initOwner(panelProcesos.getScene().getWindow());
            alert.showAndWait();
        }
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
        txtNombreProceso.setTooltip(new Tooltip("El nombre del proceso debe iniciar con una \"P\" seguido de un número identificador"));
        numTiempo.setTooltip(new Tooltip("El tiempo del proceso debe ser superior a 0 y no puede estar vacío"));
    }

}
//<?import com.yuliamz.so.s1.Vista.*?>
