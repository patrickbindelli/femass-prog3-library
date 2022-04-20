package br.edu.femass.library_system.gui;

import br.edu.femass.library_system.dao.*;
import br.edu.femass.library_system.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class LibraryController implements Initializable {

    //region Dao Instances
    LivroDao livroDao = new LivroDao();
    AutorDao autorDao = new AutorDao();
    CopiaDao copiaDao = new CopiaDao();
    UsuarioDao usuarioDao = new UsuarioDao();
    EmprestimoDao emprestimoDao = new EmprestimoDao();
    //endregion

    //region Scene Components
    @FXML
    private Button btnEmprestar;
    @FXML
    private ListView<Livro> lstLivros;
    @FXML
    private ListView<Usuario> lstUsuarios;
    @FXML
    private ListView<Emprestimo> lstEmprestimos;
    @FXML
    private TextField txtTitulo;
    @FXML
    private TextField txtAno;
    @FXML
    private TextField txtEdicao;
    @FXML
    private TextField txtCopias;
    @FXML
    private TextField txtFixas;
    @FXML
    private Label lblVersion;
    @FXML
    private Label lblStatus;

    @FXML
    private Label lblEmprestimosHeader;
    @FXML
    private Button btnNovoLivro;
    @FXML
    private Button btnSalvarLivro;
    @FXML
    private Button btnCancelarLivro;
    @FXML
    private Button btnDevolver;
    @FXML
    private ComboBox<Genero> cbGenero;
    @FXML
    private ComboBox<Autor> cbAutor;
    @FXML
    private Button btnNovoAutor;
    @FXML
    private TextField txtNomeAutor;
    @FXML
    private TextField txtSobrenomeAutor;
    @FXML
    private Button btnSalvarAutor;

    public LibraryController() throws FileNotFoundException {
    }
    //endregion

    //region Initialize Method
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        final Properties properties = new Properties();
        try (InputStream input = new FileInputStream("src/main/resources/br/edu/femass/library_system/.properties")) {
            properties.load(input);
            lblVersion.setText("v" + properties.getProperty("version"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread.currentThread().setUncaughtExceptionHandler((t, e) -> {
            e.printStackTrace();
            SetErrorStatus(e.getCause().getCause().getMessage());
        });

        ObservableList<Genero> data = FXCollections.observableArrayList(Genero.values());
        cbGenero.setItems(data);

        disableDetalhes();
        updateAutores();
        updateLivros();
        updateUsuarios();

        makeTextFieldNumberOnly(txtAno);
        makeTextFieldNumberOnly(txtEdicao);
        makeTextFieldNumberOnly(txtCopias);
        makeTextFieldNumberOnly(txtFixas);

        handleTxtCopiasChange();
        handleTxtFixasChange();

        setDefaultStatus();
        lblEmprestimosHeader.setText("Código\tEmpréstimos\tDevolução");
    }
    //endregion Method

    //region Listeners
    private void makeTextFieldNumberOnly(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("\\d"))
                textField.setText(newValue.replaceAll("\\D", ""));
        });
    }

    private void handleTxtCopiasChange() {
        txtCopias.focusedProperty().addListener((observable, oldState, newState) -> {
            if (!newState && txtCopias.getText().length() == 0) txtCopias.setText("1");
        });

        txtCopias.textProperty().addListener((observable, oldValue, newValue) -> {
            int currentValue = Integer.parseInt(txtCopias.getText().length() == 0 ? "0" : txtCopias.getText());
            int minValue = Integer.parseInt(txtFixas.getText().length() == 0 ? "0" : txtFixas.getText());
            if (minValue > currentValue && currentValue != 0) txtFixas.setText(txtCopias.getText());
        });
    }

    private void handleTxtFixasChange() {
        txtFixas.textProperty().addListener((observable, oldValue, newValue) -> {
            int currentValue = Integer.parseInt(txtFixas.getText().length() == 0 ? "0" : txtFixas.getText());
            int maxValue = Integer.parseInt(txtCopias.getText().length() == 0 ? "0" : txtCopias.getText());
            if (currentValue < 0 || currentValue > maxValue) txtFixas.setText(oldValue);
        });

        txtFixas.focusedProperty().addListener((observable, oldState, newState) -> {
            if (!newState && txtFixas.getText().length() == 0) txtFixas.setText("1");
        });
    }

    //endregion

    //region Button Actions
    @FXML
    private void btnNovoUsuario_Action() throws IOException {
        setStatus("Cadastrando usuário");
        UserCadController.display();
    }

    @FXML
    private void btnEmprestar_Action() throws Exception {

        if (lstUsuarios.getSelectionModel().isEmpty() || lstLivros.getSelectionModel().isEmpty()) {
            throw new Exception("Selecione um livro e um usuário");
        }

        Usuario usuario = usuarioDao.read().get(usuarioDao.read().indexOf(lstUsuarios.getSelectionModel().getSelectedItem()));
        List<Copia> copias = livroDao.read().get(livroDao.read().indexOf(lstLivros.getSelectionModel().getSelectedItem())).getCopiasDisponiveis();

        if(copias.isEmpty()) throw new Exception("Nenhuma cópia disponível");

        usuario.addEmprestimo(
                new Emprestimo(usuario, copias.get(new Random().nextInt(copias.size())))
        );

        emprestimoDao.create(usuario.getEmprestimos().get(usuario.getEmprestimos().size() - 1));
        updateEmprestimos();
        setSuccesStatus("Empréstimo realizado.");
    }

    @FXML
    private void btnNovoLivro_Action() {
        txtTitulo.setText(null);
        txtAno.setText(null);
        txtEdicao.setText(null);
        cbAutor.setValue(null);
        cbGenero.setValue(null);
        txtCopias.setText("1");
        txtFixas.setText("1");
        lstLivros.setDisable(true);
        enableDetalhes();
        disableCadastroAutor();
        setStatus("Cadastrando Livro");
    }

    @FXML
    private void btnSalvarLivro_Action() throws Exception {
        if (isFormValid()) {
            livroDao.create(new Livro(
                    txtTitulo.getText(),
                    Integer.parseInt(txtAno.getText()),
                    Integer.parseInt(txtEdicao.getText()),
                    cbAutor.getSelectionModel().getSelectedItem(),
                    cbGenero.getSelectionModel().getSelectedItem(),
                    Integer.parseInt(txtCopias.getText()),
                    Integer.parseInt(txtFixas.getText())
            ));

            livroDao.read().get(livroDao.read().size() - 1).getCopias().forEach(i -> {
                try {
                    copiaDao.create(i);
                } catch (Exception e) { throw new RuntimeException(e); }
            });

            disableDetalhes();
            updateLivros();
            lstLivros.setDisable(false);
            lstLivros.getSelectionModel().select(livroDao.read().get(livroDao.read().size() - 1));
            updateDetalhes();
            setSuccesStatus("Livro cadastrado.");
        }else {
            throw new Exception("Há campos vazios");
        }
    }

    @FXML
    private void btnCancelarLivro_Action() {
        disableDetalhes();
        updateDetalhes();
        lstLivros.setDisable(false);
        setStatus("Cancelado.");
    }

    @FXML
    private void btnNovoAutor_Action() {
        if (btnSalvarAutor.isDisable()) {
            btnNovoAutor.setText("Cancelar");
            enableCadastroAutor();
            btnSalvarLivro.setDisable(true);
        } else {
            btnNovoAutor.setText("Novo");
            disableCadastroAutor();
            btnSalvarLivro.setDisable(false);
            updateSelectedAutor();
            txtNomeAutor.setStyle("");
            txtSobrenomeAutor.setStyle("");
        }
        setStatus("Cadastrando autor.");
    }

    @FXML
    private void btnSalvarAutor_Action() throws Exception {
        validateTextField(txtNomeAutor);
        validateTextField(txtSobrenomeAutor);
        autorDao.create(new Autor(txtNomeAutor.getText(), txtSobrenomeAutor.getText()));
        cbAutor.setValue(autorDao.read().get(autorDao.read().size() - 1));
        updateAutores();
        btnNovoAutor_Action();
        setStatus("Autor cadastrado. Cadastrando livro.");
    }

    @FXML
    private void btnDevolver_Action() throws Exception {
        if(lstEmprestimos.getSelectionModel().isEmpty()) throw new Exception("Selecione um livro para devolver");
        Emprestimo selectedEmprestimo = lstEmprestimos.getSelectionModel().getSelectedItem();
        emprestimoDao.read().get(emprestimoDao.read().indexOf(selectedEmprestimo)).setDataDevolucao(new Date());
        copiaDao.read().get(copiaDao.read().indexOf(selectedEmprestimo.getCopia())).rmEmprestimo();
        updateEmprestimos();
        setSuccesStatus("Devolução realizada.");
    }
    //endregion

    //region ListView Actions
    @FXML
    private void lstUsuarios_Action() {
        updateEmprestimosBySelectedUser();
    }

    @FXML
    private void lstLivros_Action() {
        updateEmprestimosBySelectedLivro();
        updateDetalhes();
    }

    @FXML
    private void lstEmprestimos_Action() {
        if(!lstLivros.getItems().isEmpty())
            lstLivros.getSelectionModel().select(lstEmprestimos.getSelectionModel().getSelectedItem().getCopia().getLivro());
    }

    //endregion

    //region ComboBox Actions
    @FXML
    private void cbAutor_Action() {
        updateSelectedAutor();
    }
    //endregion

    //region Update Components Methods

    protected void updateUsuarios() {
        List<Usuario> usuarioList;
        try {
            usuarioList = usuarioDao.read();
        } catch (Exception e) {
            usuarioList = new ArrayList<>();
        }

        ObservableList<Usuario> data = FXCollections.observableArrayList(usuarioList);
        lstUsuarios.setItems(data);
    }

    private void updateLivros() {
        List<Livro> livroList;
        try {
            livroList = livroDao.read();
        } catch (Exception e) {
            livroList = new ArrayList<>();
        }

        ObservableList<Livro> data = FXCollections.observableArrayList(livroList);
        lstLivros.setItems(data);
    }

    private void updateAutores() {
        List<Autor> autorList;
        try {
            autorList = autorDao.read();
        } catch (Exception e) {
            autorList = new ArrayList<>();
        }

        ObservableList<Autor> data = FXCollections.observableArrayList(autorList);
        cbAutor.setItems(data);
    }

    private void updateSelectedAutor() {
        txtNomeAutor.setText(cbAutor.getValue() == null ? null : cbAutor.getValue().getNome());
        txtSobrenomeAutor.setText(cbAutor.getValue() == null ? null : cbAutor.getValue().getSobrenome());
    }

    private void updateDetalhes() {
        txtTitulo.setStyle("");
        txtAno.setStyle("");
        txtEdicao.setStyle("");
        cbAutor.setStyle("");
        cbGenero.setStyle("");
        txtCopias.setStyle("");
        txtFixas.setStyle("");

        Livro selectedLivro = lstLivros.getSelectionModel().getSelectedItem();
        txtTitulo.setText(selectedLivro == null ? null : selectedLivro.getTitulo());
        txtAno.setText(selectedLivro == null ? null : selectedLivro.getAno().toString());
        txtEdicao.setText(selectedLivro == null ? null : selectedLivro.getEdicao().toString());
        cbAutor.setValue(selectedLivro == null ? null : selectedLivro.getAutor());
        cbGenero.setValue(selectedLivro == null ? null : selectedLivro.getGenero());
        txtCopias.setText(selectedLivro == null ? "1" : String.valueOf(selectedLivro.getCopias().size()));
        txtFixas.setText(selectedLivro == null ? "1" : String.valueOf(selectedLivro.getCopiasFixas().size()));
    }

    private void updateEmprestimos() {
        List<Emprestimo> emprestimoList = null;
        try {
            emprestimoList = emprestimoDao.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObservableList<Emprestimo> livroEmprestimos = FXCollections.observableArrayList(emprestimoList);
        lstEmprestimos.setItems(livroEmprestimos);
    }

    private void updateEmprestimosBySelectedLivro() {
        List<Emprestimo> emprestimoList = null;
        try {
            emprestimoList = emprestimoDao.getEmprestimosByLivro(lstLivros.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObservableList<Emprestimo> livroEmprestimos = FXCollections.observableArrayList(emprestimoList);
        lstEmprestimos.setItems(livroEmprestimos);
    }

    private void updateEmprestimosBySelectedUser() {
        List<Emprestimo> emprestimoList = null;
        try {
            emprestimoList = emprestimoDao.getEmprestimosByUser(lstUsuarios.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObservableList<Emprestimo> userEmprestimos = FXCollections.observableArrayList(emprestimoList);
        lstEmprestimos.setItems(userEmprestimos);
    }
    //endregion

    //region Disable/Enable Components
    private void disableDetalhes() {
        txtTitulo.setDisable(true);
        txtAno.setDisable(true);
        txtEdicao.setDisable(true);
        cbAutor.setDisable(true);
        btnNovoAutor.setDisable(true);
        cbGenero.setDisable(true);
        txtCopias.setDisable(true);
        txtFixas.setDisable(true);
        btnCancelarLivro.setDisable(true);
        btnSalvarLivro.setDisable(true);

        txtNomeAutor.setDisable(true);
        txtSobrenomeAutor.setDisable(true);
        btnSalvarAutor.setDisable(true);
    }

    private void disableCadastroAutor() {
        cbAutor.setDisable(false);
        txtNomeAutor.setText(null);
        txtSobrenomeAutor.setText(null);
        txtNomeAutor.setDisable(true);
        txtSobrenomeAutor.setDisable(true);
        btnSalvarAutor.setDisable(true);
    }

    private void enableDetalhes() {
        txtTitulo.setDisable(false);
        txtAno.setDisable(false);
        txtEdicao.setDisable(false);
        cbAutor.setDisable(false);
        btnNovoAutor.setDisable(false);
        cbGenero.setDisable(false);
        txtCopias.setDisable(false);
        txtFixas.setDisable(false);
        btnCancelarLivro.setDisable(false);
        btnSalvarLivro.setDisable(false);

        txtNomeAutor.setDisable(false);
        txtSobrenomeAutor.setDisable(false);
        btnSalvarAutor.setDisable(false);
    }

    private void enableCadastroAutor() {
        cbAutor.setDisable(true);
        txtNomeAutor.setText(null);
        txtSobrenomeAutor.setText(null);
        txtNomeAutor.setDisable(false);
        txtSobrenomeAutor.setDisable(false);
        btnSalvarAutor.setDisable(false);
    }

    //endregion

    //region QOL Methods
    private boolean isFormValid(){
        return validateTextField(txtTitulo) && validateTextField(txtAno)
                && validateTextField(txtEdicao) && validateTextField(txtEdicao)
                && validateTextField(txtCopias) && validateTextField(txtFixas)
                && validateComboBox(cbAutor) && validateComboBox(cbGenero);
    }

    private boolean validateTextField(TextField textField){
        if (textField.getText() == null || textField.getText().isBlank()) {
            textField.setStyle("-fx-text-box-border: red;");
            return false;
        }
        textField.setStyle("");
        return true;
    }

    private boolean validateComboBox(ComboBox comboBox){
        if (comboBox.getSelectionModel().getSelectedItem() == null || comboBox.getItems().isEmpty()) {
            comboBox.setStyle("-fx-border-color: red");
            return false;
        }
        comboBox.setStyle("");
        return true;
    }

    protected void setStatus(String message){
        lblStatus.setText(message);
        lblStatus.setTextFill(Color.BLACK);
    }

    protected void setSuccesStatus(String message){
        lblStatus.setText(message);
        lblStatus.setTextFill(Color.GREEN);
    }

    protected void SetErrorStatus(String message){
        lblStatus.setText(message);
        lblStatus.setTextFill(Color.RED);
    }

    protected void setDefaultStatus(){
        setStatus("Selecione uma opção.");
    }
    //endregion
}