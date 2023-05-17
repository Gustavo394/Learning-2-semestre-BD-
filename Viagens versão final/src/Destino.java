import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;

import java.sql.*;

public class Destino{

    @FXML private AnchorPane cadastro_destino;

    @FXML private TextField cep_destino;
    @FXML private TextField endereco_destino;

    @FXML
    void initialize(){
        cep_destino.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("\\d*") && change.getControlNewText().length() <= 8) {
                return change;
            }
            return null;
        }));

    }

    @FXML
    void salvar(ActionEvent event){
        try {
            if(cep_destino.getText().isEmpty() || endereco_destino.getText().isEmpty()){
                cadastro_destino.setDisable(true);
                telaAviso.titulo = "Alerta";
                telaAviso.texto = "Preencha todos os campos corretamente!";
                telaAviso telaAviso = new telaAviso();
                telaAviso.aviso();
                cadastro_destino.setDisable(false);
                
            } else if (cep_destino.getText().length() < 8){
                cadastro_destino.setDisable(true);
                telaAviso.titulo = "Alerta";
                telaAviso.texto = "O CEP deve ter pelo menos 8 caracteres!";
                telaAviso telaAviso = new telaAviso();
                telaAviso.aviso();
                cadastro_destino.setDisable(false);

            } else {
                Connection conexao = DriverManager.getConnection(Main.caminho, Main.usuario, Main.senha);
                PreparedStatement cep = conexao.prepareStatement("SELECT * FROM destino WHERE cep = ?");
                cep.setInt(1, Integer.valueOf(cep_destino.getText()));
                ResultSet ceprs = cep.executeQuery();

                if (ceprs.next()) {
                    cadastro_destino.setDisable(true);
                    telaAviso.titulo = "Alerta";
                    telaAviso.texto = "O cep " + cep_destino.getText() + " já está cadastrado";
                    telaAviso telaAviso = new telaAviso();
                    telaAviso.aviso();
                    cadastro_destino.setDisable(false);

                } else {
                    PreparedStatement stmt = conexao.prepareStatement("INSERT INTO destino (cep, endereco) VALUES (?, ?)");
                    stmt.setInt(1, Integer.valueOf(cep_destino.getText()));
                    stmt.setString(2, endereco_destino.getText());
                    stmt.executeUpdate();
                    stmt.close();
                    conexao.close();

                    cep_destino.clear();
                    endereco_destino.clear();

                    cadastro_destino.setDisable(true);
                    telaAviso.titulo = "Certo";
                    telaAviso.texto = "Registro realizado com sucesso!";
                    telaAviso telaAviso = new telaAviso();
                    telaAviso.aviso();
                    cadastro_destino.setDisable(false);
                }
            }

        }catch(Exception e){
            cadastro_destino.setDisable(true);
             telaAviso.titulo = "Erro";
             telaAviso.texto = e.toString();
             telaAviso telaAviso = new telaAviso();
             telaAviso.aviso();
             cadastro_destino.setDisable(false);
        }
    }
    
    @FXML
    void voltar(ActionEvent event){
        Main.changeScreen(("main"));
    }

    public void cadastro_destino() {
        Main.changeScreen("cadastrarDestino");
    }    
}
