package br.edu.femass.library_system.gui;

import br.edu.femass.library_system.LibraryApplication;
import br.edu.femass.library_system.dao.UsuarioDao;
import br.edu.femass.library_system.model.Aluno;
import br.edu.femass.library_system.model.Professor;
import br.edu.femass.library_system.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserCadController implements Initializable {

    //region DAO Instances
    UsuarioDao usuarioDao = new UsuarioDao();
    //endregion

    //region JFX Componentes
    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCpf;

    @FXML
    private TextField txtMatricula;

    @FXML
    private TextField txtCurso;

    @FXML
    private TextField txtDepartamento;

    @FXML
    private CheckBox chkAluno;

    @FXML
    private CheckBox chkProfessor;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnCancelar;
    //endregion

    //region Initalize methods
    public static void display() throws IOException {
        Stage window = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(LibraryApplication.class.getResource("newuser-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        window.setScene(scene);

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Cadastrar Usuario");

        window.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtCurso.setDisable(true);
        txtDepartamento.setDisable(true);

        chkAluno.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && chkProfessor.isSelected()) {
                chkProfessor.setSelected(false);
                txtDepartamento.setStyle("");
            }
            txtCurso.setDisable(!newValue);
        });

        chkProfessor.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && chkAluno.isSelected()) {
                chkAluno.setSelected(false);
                txtCurso.setStyle("");
            }

            txtDepartamento.setDisable(!newValue);
        });

        txtCurso.disabledProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) txtCurso.setText(null);
        });

        txtDepartamento.disabledProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) txtDepartamento.setText(null);
        });

        makeTextFieldNumberOnly(txtMatricula);
        txtMatricula.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() > 10) txtMatricula.setText(oldValue);
        });

        maskTextFieldToCPF(txtCpf);
    }
    //endregion

    //region Listeners
    private void maskTextFieldToCPF(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            StringBuilder stringBuilder = new StringBuilder(newValue);
            if(newValue.length() == 4 || newValue.length() == 8){
                if(newValue.substring(newValue.length() - 1).compareTo(".") != 0){
                    stringBuilder.insert(newValue.length() - 1, ".");
                }
            }else if(newValue.length() == 12){
                if(newValue.substring(newValue.length() - 1).compareTo("-") != 0){
                    stringBuilder.insert(newValue.length() - 1, "-");
                }
            }
            else if (newValue.length() != 0){
                if(newValue.substring(newValue.length() - 1).matches("\\D")){
                    stringBuilder.deleteCharAt(newValue.length() - 1);
                }
            }
            textField.setText((textField.getText().length() > 14) ? oldValue : String.valueOf(stringBuilder));
        });
    }

    private void makeTextFieldNumberOnly(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("\\d"))
                textField.setText(newValue.replaceAll("\\D", ""));
        });
    }
    //endregion

    //region Button Methods
    @FXML
    private void btnCadastrar_Action() throws Exception {
        String nome = txtNome.getText();
        String cpf = txtCpf.getText().replaceAll("\\D", "");
        String matricula = txtMatricula.getText();
        String curso = txtCurso.getText();
        String departamento = txtDepartamento.getText();

        Usuario usuario = null;
        if (chkProfessor.isSelected()) usuario = new Professor(nome, cpf, matricula, departamento);
        if (chkAluno.isSelected()) usuario = new Aluno(nome, cpf, matricula, curso);

        if (isFormValid()) {
            usuarioDao.create(usuario);
            Stage stage = (Stage) Window.getWindows().get(0);
            LibraryController libraryController = (LibraryController) stage.getScene().getUserData();
            libraryController.updateUsuarios();
            libraryController.setSuccesStatus("Usuário cadastrado.");
            closeWindow();
        }
    }

    @FXML
    private void btnCancelar_Action() {
        Stage stage = (Stage) Window.getWindows().get(0);
        LibraryController libraryController = (LibraryController) stage.getScene().getUserData();
        libraryController.setDefaultStatus();
        closeWindow();
    }
    //endregion

    //region QOL Methods
    private boolean isFormValid() {
        return validateTextField(txtNome) && validateTextField(txtCpf) && validateTextField(txtMatricula)
                && (!chkAluno.isSelected() || validateTextField(txtCurso))
                && validateCheckBox(chkProfessor, chkAluno)
                && (!chkProfessor.isSelected() || validateTextField(txtDepartamento));
    }

    private boolean validateTextField(TextField textField) {
        if (textField.getText() == null || textField.getText().isBlank()) {
            textField.setStyle("-fx-text-box-border: red;");
            return false;
        }
        textField.setStyle("");
        return true;
    }

    private boolean validateCheckBox(CheckBox checkBox, CheckBox checkBox2) {
        if (!checkBox.isSelected() && !checkBox2.isSelected()) {
            checkBox.setStyle("-fx-border-color: red");
            checkBox2.setStyle("-fx-border-color: red");
            return false;
        }
        checkBox.setStyle("");
        checkBox2.setStyle("");
        return true;
    }


    private void closeWindow() {
        Stage stage = (Stage) Window.getWindows().get(1);
        stage.close();
    }
    //endregion
}

