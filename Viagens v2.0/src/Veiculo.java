import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import java.sql.*;

public class Veiculo{

    @FXML private AnchorPane cadastro_veiculo;

    @FXML private TextField placa_veiculo;
    @FXML private TextField modelo_veiculo;
    @FXML private ComboBox<String> tipo_veiculo;
    @FXML private TextField ano_veiculo;

    @FXML
    void initialize(){
        tipo_veiculo.getItems().addAll("Carro", "Moto", "Outro");

        placa_veiculo.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 7) {
                return change;
            }
            return null;
        }));
        ano_veiculo.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("\\d*") && change.getControlNewText().length() <= 4){
                return change;
            }
            return null;
        }));
    }

    @FXML
    void salvar(ActionEvent event){
        try{
            if (placa_veiculo.getText().isEmpty() || modelo_veiculo.getText().isEmpty() || tipo_veiculo.getValue() == null || ano_veiculo.getText().isEmpty()){
                cadastro_veiculo.setDisable(true);
                telaAviso.titulo = "Alerta";
                telaAviso.texto = "Preencha todos os campos corretamente!";
                telaAviso telaAviso = new telaAviso();
                telaAviso.aviso();
                cadastro_veiculo.setDisable(false);

            } else if (placa_veiculo.getLength() < 7) {
                cadastro_veiculo.setDisable(true);
                telaAviso.titulo = "Alerta";
                telaAviso.texto = "A placa deve ter no mínimo 7 caracteres!";
                telaAviso telaAviso = new telaAviso();
                telaAviso.aviso();
                cadastro_veiculo.setDisable(false);

            } else if (!placa_veiculo.getText().toUpperCase().matches("[A-Z]{3}\\d{1}[A-Z]{1}\\d{2}") && !placa_veiculo.getText().toUpperCase().matches("[A-Z]{3}\\d{4}")){
                cadastro_veiculo.setDisable(true);
                telaAviso.titulo = "Alerta";
                telaAviso.texto = "Placa inválida\nA placa deve ser parecida com isso\nExemplo: AAA1A11 ou AAA1111!";
                telaAviso telaAviso = new telaAviso();
                telaAviso.aviso();
                cadastro_veiculo.setDisable(false);
                        
            } else if (ano_veiculo.getLength() < 4) {
                cadastro_veiculo.setDisable(true);
                telaAviso.titulo = "Alerta";
                telaAviso.texto = "Ano deve ter quatro números!";
                telaAviso telaAviso = new telaAviso();
                telaAviso.aviso();
                cadastro_veiculo.setDisable(false);
    
            } else {
                Connection conexao = DriverManager.getConnection(Main.caminho, Main.usuario, Main.senha);
                PreparedStatement placa = conexao.prepareStatement("SELECT * FROM veiculo WHERE placa = ?");
                placa.setString(1, placa_veiculo.getText().toUpperCase());
                ResultSet placars = placa.executeQuery();

                if (placars.next()) {
                    cadastro_veiculo.setDisable(true);
                    telaAviso.titulo = "Alerta";
                    telaAviso.texto = "A placa " + placa_veiculo.getText().toUpperCase() + " já está cadastrada";
                    telaAviso telaAviso = new telaAviso();
                    telaAviso.aviso();
                    cadastro_veiculo.setDisable(false);

                } else {
                    PreparedStatement stmt = conexao.prepareStatement("INSERT INTO veiculo (placa, modelo, tipo, ano) VALUES (?, ?, ?, ?)");
                    stmt.setString(1, placa_veiculo.getText().toUpperCase());
                    stmt.setString(2, modelo_veiculo.getText());
                    stmt.setString(3, tipo_veiculo.getValue());
                    stmt.setInt(4, Integer.valueOf(ano_veiculo.getText()));
                    stmt.executeUpdate();
                    stmt.close();
                    conexao.close();

                    placa_veiculo.clear();
                    modelo_veiculo.clear();
                    tipo_veiculo.setValue(null);
                    ano_veiculo.clear();

                    cadastro_veiculo.setDisable(true);
                    telaAviso.titulo = "Certo";
                    telaAviso.texto = "Registro realizado com sucesso!";
                    telaAviso telaAviso = new telaAviso();
                    telaAviso.aviso();
                    cadastro_veiculo.setDisable(false);
                }
            }

        }catch(SQLException e){
            cadastro_veiculo.setDisable(true);
            telaAviso.titulo = "Alerta";
            telaAviso.texto = e.toString();
            telaAviso telaAviso = new telaAviso();
            telaAviso.aviso();
            cadastro_veiculo.setDisable(false);
        }
    }

    @FXML
    void voltar(ActionEvent event){
        Main.changeScreen("main");
    }

    public void cadastro_veiculo(){
        Main.changeScreen("cadastrarVeiculo");
    }
}
