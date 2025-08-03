/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ciber.javafxcrud;

import com.ciber.javafxcrud.database.AppQuery;
import com.ciber.javafxcrud.model.Student;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EstudiantesController implements Initializable {

    /**
     * @return the stage
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * @param aStage the stage to set
     */
    public static void setStage(Stage aStage) {
        stage = aStage;
    }
    
    private static Stage stage;

    // Método que se ejecuta al iniciar la vista FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showStudents(); // Carga la lista de estudiantes
        btnactualizar.setDisable(true);
        btneliminar.setDisable(true);

        // Añade listener para filtrar estudiantes mientras se escribe en el campo de búsqueda
        fieldSearch.textProperty().addListener((ObservableList, oldValue, newValue) -> {
            filtro(newValue);
        });
    }

    // Campos de texto del formulario
    @FXML public TextField fieldnombre;
    @FXML public TextField fieldapellidopaterno;
    @FXML public TextField fieldapellidomaterno;
    @FXML public TextField fieldSearch;

    // Botones del formulario
    @FXML public Button btnnuevo;
    @FXML public Button btnguardar;
    @FXML public Button btnactualizar;
    @FXML public Button btneliminar;
    @FXML public Button salir;

    // Tabla y columnas
    @FXML public TableView<Student> tabla;
    @FXML public TableColumn<Student, Integer> colId;
    @FXML public TableColumn<Student, String> colNombre;
    @FXML public TableColumn<Student, String> colApellido_Paterno;
    @FXML public TableColumn<Student, String> colApellido_Materno;

    private Student student; // Variable temporal para almacenar estudiante seleccionado

    // Agrega un nuevo estudiante después de confirmar con el usuario
    @FXML
    private void addStudent() {
        // Dialogo de confirmación
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("NUEVO REGISTRO");
        dialog.setHeaderText("Esta seguro de guardar el registro");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(getStage());

        Label label = new Label("Nombre: " + fieldnombre.getText() + " " + fieldapellidopaterno.getText() + " " + fieldapellidomaterno.getText());
        dialog.getDialogPane().setContent(label);

        // Botones del diálogo
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("CANCELAR", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

        Optional<ButtonType> result = dialog.showAndWait();

        // Si el usuario confirma, se guarda el estudiante
        if (result.isPresent() && result.get() == okButton) {
            Student student = new Student(fieldnombre.getText(), fieldapellidopaterno.getText(), fieldapellidomaterno.getText());
            AppQuery query = new AppQuery();
            query.addStudent(student);
            showStudents(); // Recarga la tabla
        }
    }

    // Muestra la lista de estudiantes en la tabla
    @FXML
    private void showStudents() {
        AppQuery query = new AppQuery();
        ObservableList<Student> list = query.getStudentList();

        // Asigna los datos a cada columna
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido_Paterno.setCellValueFactory(new PropertyValueFactory<>("apellidopaterno"));
        colApellido_Materno.setCellValueFactory(new PropertyValueFactory<>("apellidomaterno"));

        tabla.setItems(list); // Establece los datos en la tabla
    }

    // Se ejecuta al hacer clic sobre un elemento de la tabla
    @FXML
    public void mouseClicked(MouseEvent e) {
        try {
            Student student = tabla.getSelectionModel().getSelectedItem();
            // Copia del estudiante seleccionado
            student = new Student(student.getId(), student.getNombre(), student.getApellidopaterno(), student.getApellidomaterno());
            this.student = student;

            // Llena los campos con los datos del estudiante
            fieldnombre.setText(student.getNombre());
            fieldapellidopaterno.setText(student.getApellidopaterno());
            fieldapellidomaterno.setText(student.getApellidomaterno());

            // Habilita los botones de actualizar y eliminar
            btnactualizar.setDisable(false);
            btneliminar.setDisable(false);
            btnguardar.setDisable(true);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Actualiza los datos de un estudiante seleccionado
    @FXML
    private void updateStudent() {
        try {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("ACTUALIZAR REGISTRO");
            dialog.setHeaderText("Esta seguro de actualizar el registro");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(getStage());

            Label label = new Label("Nombre: " + fieldnombre.getText() + " " + fieldapellidopaterno.getText() + " " + fieldapellidomaterno.getText());
            dialog.getDialogPane().setContent(label);

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("CANCELAR", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == okButton) {
                AppQuery query = new AppQuery();
                Student student = new Student(this.student.getId(), fieldnombre.getText(), fieldapellidopaterno.getText(), fieldapellidomaterno.getText());
                query.updateStudent(student);
                showStudents(); // Recarga la tabla
                clearFields();  // Limpia campos
                btnactualizar.setDisable(true);
                btneliminar.setDisable(true);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Elimina el estudiante seleccionado
    @FXML
    private void deleteStudent() {
        try {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("ELIMINAR REGISTRO");
            dialog.setHeaderText("Esta seguro de eliminar el registro");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(getStage());

            Label label = new Label("Nombre: " + fieldnombre.getText() + " " + fieldapellidopaterno.getText() + " " + fieldapellidomaterno.getText());
            dialog.getDialogPane().setContent(label);

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("CANCELAR", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == okButton) {
                AppQuery query = new AppQuery();
                Student student = new Student(this.student.getId(), fieldnombre.getText(), fieldapellidopaterno.getText(), fieldapellidomaterno.getText());
                query.deleteStudent(student);
                showStudents(); // Recarga la tabla
                clearFields();  // Limpia los campos
                btnactualizar.setDisable(true);
                btneliminar.setDisable(true);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Limpia los campos del formulario
    private void clearFields() {
        fieldnombre.setText("");
        fieldapellidopaterno.setText("");
        fieldapellidomaterno.setText("");
    }

    // Cierra la aplicación
    @FXML
    private void close() {
        System.exit(0);
    }

    // Prepara el formulario para agregar un nuevo registro
    @FXML
    private void clickNew() {
        btnactualizar.setDisable(true);
        btneliminar.setDisable(true);
        clearFields();
        btnguardar.setDisable(false);
    }

    // Filtra la tabla según lo que se escriba en el campo de búsqueda
    private void filtro(String searchName) {
        ObservableList<Student> filterData = FXCollections.observableArrayList();
        AppQuery query = new AppQuery();
        ObservableList<Student> list = query.getStudentList();

        // Verifica si coincide el nombre o los apellidos
        for (Student student : list) {
            if (student.getNombre().toLowerCase().contains(searchName.toLowerCase())
                    || student.getApellidopaterno().toLowerCase().contains(searchName.toLowerCase())
                    || student.getApellidomaterno().toLowerCase().contains(searchName.toLowerCase())) {
                filterData.add(student);
            }
        }
        tabla.setItems(filterData); // Muestra la lista filtrada
    }
}