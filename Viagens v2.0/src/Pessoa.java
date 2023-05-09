import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import java.sql.*;

public class Pessoa{

    @FXML private AnchorPane cadastro_pessoa;

    public static Stage stage;
    @FXML private TextField nome_pessoa;
    @FXML private TextField cpf_pessoa;
    @FXML private DatePicker nascimento_pessoa;
    @FXML private RadioButton masculino;
    @FXML private RadioButton feminino;
    @FXML private RadioButton outro;

    String sexo;

    @FXML
    void initialize(){
        cpf_pessoa.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("\\d*") && change.getControlNewText().length() <= 11) {
                return change;
            }
            return null;
        }));
        nome_pessoa.setTextFormatter(new TextFormatter<>(change -> {
            if (!change.getText().matches("[0-9]")) {
                return change;
            }
            return null;
        }));
    }

    @FXML
    void masculinoSelecionado(ActionEvent event){
        if(masculino.isSelected()){
            sexo = "Homem";
            feminino.setSelected(false);
            outro.setSelected(false);
        }

    }
    
    @FXML
    void femininoSelecionado(ActionEvent event){
        if(feminino.isSelected()){
            sexo = "Mulher";
            masculino.setSelected(false);
            outro.setSelected(false);
        }

    }
    
    @FXML
    void outroSelecionado(ActionEvent event){
        if(outro.isSelected()){
            sexo = "Outro";
            masculino.setSelected(false);
            feminino.setSelected(false);
        }

    }

    @FXML
    void salvar(ActionEvent event){
        try{
            if(cpf_pessoa.getText().isEmpty() || nome_pessoa.getText().isEmpty() || nascimento_pessoa.getValue() == null ||
                (!masculino.isSelected() && !feminino.isSelected() && !outro.isSelected())){
                cadastro_pessoa.setDisable(true);
                telaAviso.titulo = "Alerta";
                telaAviso.texto = "Preencha todos os campos corretamente!";
                telaAviso telaAviso = new telaAviso();
                telaAviso.aviso();
                cadastro_pessoa.setDisable(false);
                
            } else if (cpf_pessoa.getLength() < 11){
                cadastro_pessoa.setDisable(true);
                telaAviso.titulo = "Alerta";
                telaAviso.texto = "O CPF deve ter pelo menos 11 caracteres!";
                telaAviso telaAviso = new telaAviso();
                telaAviso.aviso();
                cadastro_pessoa.setDisable(false);
                
            } else{
                Connection conexao = DriverManager.getConnection(Main.caminho, Main.usuario, Main.senha);
                PreparedStatement cpf = conexao.prepareStatement("SELECT * FROM pessoa WHERE cpf = ?");
                cpf.setString(1, (cpf_pessoa.getText()));
                ResultSet cpfrs = cpf.executeQuery();
    
                if (cpfrs.next()) {
                    cadastro_pessoa.setDisable(true);
                    telaAviso.titulo = "Alerta";
                    telaAviso.texto = "O CPF " + cpf_pessoa.getText() + " já está cadastrado";
                    telaAviso telaAviso = new telaAviso();
                    telaAviso.aviso();
                    cadastro_pessoa.setDisable(false);
    
                } else {
                    PreparedStatement stmt = conexao.prepareStatement("INSERT INTO pessoa (cpf, nome, nascimento, sexo) VALUES (?, ?, ?, ?)");

                    stmt.setString(1, cpf_pessoa.getText());
                    stmt.setString(2, nome_pessoa.getText());
                    stmt.setDate(3, Date.valueOf(nascimento_pessoa.getValue()));
                    stmt.setString(4, sexo);
                    stmt.executeUpdate();
                    stmt.close();
                    conexao.close();
                    cpf_pessoa.clear();
                    nome_pessoa.clear();
                    nascimento_pessoa.setValue(null);
                    masculino.setSelected(false);
                    feminino.setSelected(false);
                    outro.setSelected(false);

                    cadastro_pessoa.setDisable(true);
                    telaAviso.titulo = "Certo";
                    telaAviso.texto = "Registro realizado com sucesso!";
                    telaAviso telaAviso = new telaAviso();
                    telaAviso.aviso();
                    cadastro_pessoa.setDisable(false);
                }
            }
        }

        catch(Exception e){
            cadastro_pessoa.setDisable(true);
            telaAviso.titulo = "Alerta";
            telaAviso.texto = e.toString();
            telaAviso telaAviso = new telaAviso();
            telaAviso.aviso();
            cadastro_pessoa.setDisable(false);
        }
    }

    @FXML
    void voltar(ActionEvent event){
        Main.changeScreen("main");
    }
    public void cadastrar_pessoa(){
        Main.changeScreen("cadastrarPessoa");
    }
}
