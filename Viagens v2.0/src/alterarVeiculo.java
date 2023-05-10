import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import java.sql.*;

public class alterarVeiculo{

    @FXML private AnchorPane alterar_veiculo;

    @FXML private ComboBox<String> placa_veiculo;
    @FXML private TextField modelo_veiculo;
    @FXML private ComboBox<String> tipo_veiculo;
    @FXML private TextField ano_veiculo;

    private ObservableList<String> placaList = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        tipo_veiculo.getItems().addAll("Carro", "Moto", "Outro");
        ano_veiculo.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("\\d*") && change.getControlNewText().length() <= 4){
                return change;
            }
            return null;
        }));
        atualizar();
    }
    
    public void setStage(Stage stage){
        stage.setOnShowing(event -> atualizar());
    }

    public void atualizar(){
        placa_veiculo.setItems(null);
        placaList.clear();
        
        try{
            Connection conexao = DriverManager.getConnection(Main.caminho, Main.usuario, Main.senha);
            Statement stmt = conexao.createStatement();

            ResultSet cpfResultSet = stmt.executeQuery("SELECT placa FROM veiculo");{
                while (cpfResultSet.next()){
                    String cpf = cpfResultSet.getString("placa");
                    placaList.add(cpf);
                    
                }    
                placa_veiculo.setItems(placaList);
                cpfResultSet.close();

            }

            if (placaList.isEmpty()){
                alterar_veiculo.setDisable(true);
                telaAviso.titulo = "Alerta";
                telaAviso.texto = "Não existem veículos cadastrados!";
                telaAviso telaAviso = new telaAviso();
                telaAviso.aviso();
                alterar_veiculo.setDisable(false);
            }

            placa_veiculo.setValue(null);
            modelo_veiculo.clear();
            tipo_veiculo.setValue(null);
            ano_veiculo.clear();

        }catch(Exception e){
            alterar_veiculo.setDisable(true);
            telaAviso.titulo = "Erro";
            telaAviso.texto = e.toString();
            telaAviso telaAviso = new telaAviso();
            telaAviso.aviso();
            alterar_veiculo.setDisable(false);

        }
    }
    @FXML
    void selectec_placa(ActionEvent event){
        try(Connection conexao = DriverManager.getConnection(Main.caminho, Main.usuario, Main.senha);
            PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM veiculo WHERE placa = ?")){
            stmt.setString(1, placa_veiculo.getValue());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                modelo_veiculo.setText(rs.getString("modelo"));
                tipo_veiculo.setValue(rs.getString("tipo"));
                ano_veiculo.setText(rs.getString("ano"));
            }

        }catch(Exception e){
            alterar_veiculo.setDisable(true);
            telaAviso.titulo = "Erro";
            telaAviso.texto = e.toString();
            telaAviso telaAviso = new telaAviso();
            telaAviso.aviso();
            alterar_veiculo.setDisable(false);

        }

    }

    @FXML
    void alterar(ActionEvent event){
        try{
            if (ano_veiculo.getLength() < 4) {
                alterar_veiculo.setDisable(true);
                    telaAviso.titulo = "Alerta";
                    telaAviso.texto = "Ano deve ter quatro números!";
                    telaAviso telaAviso = new telaAviso();
                    telaAviso.aviso();
                    alterar_veiculo.setDisable(false);
    
                } else {
                    if (placa_veiculo.getValue() == null || modelo_veiculo.getText().isEmpty() || tipo_veiculo.getValue() == null || ano_veiculo.getText().isEmpty()){
                        alterar_veiculo.setDisable(true);
                        telaAviso.titulo = "Alerta";
                        telaAviso.texto = "Preencha todos os campos corretamente!";
                        telaAviso telaAviso = new telaAviso();
                        telaAviso.aviso();
                        alterar_veiculo.setDisable(false);
                        
                    }else{
                        Connection conexao = DriverManager.getConnection(Main.caminho, Main.usuario, Main.senha);
                        PreparedStatement placa = conexao.prepareStatement("SELECT placa_veiculo FROM viagem WHERE placa_veiculo = ?");
                        placa.setString(1, placa_veiculo.getValue());
                        ResultSet placars = placa.executeQuery();
            
                        if (placars.next()) {
                            alterar_veiculo.setDisable(true);
                            telaAviso.titulo = "Alerta";
                            telaAviso.texto = "A placa " + placa_veiculo.getValue() + " está registrada em uma viagem e não pode ser alterada!";
                            telaAviso telaAviso = new telaAviso();
                            telaAviso.aviso();
                            alterar_veiculo.setDisable(false);

                        } else {
                            PreparedStatement stmt = conexao.prepareStatement("UPDATE veiculo SET modelo = ?, tipo = ?, ano = ? WHERE placa = ?");

                            stmt.setString(1, modelo_veiculo.getText().toString());
                            stmt.setString(2, tipo_veiculo.getValue().toString());
                            stmt.setInt(3, Integer.valueOf(ano_veiculo.getText().toString()));
                            stmt.setString(4, placa_veiculo.getValue());
                            stmt.executeUpdate();

                            alterar_veiculo.setDisable(true);
                            telaAviso.titulo = "Certo";
                            telaAviso.texto = "Registro alterado com sucesso!";
                            telaAviso telaAviso = new telaAviso();
                            telaAviso.aviso();                
                            atualizar();
                            alterar_veiculo.setDisable(false);

                        }
                    }
                }
        }catch(Exception e){
            alterar_veiculo.setDisable(true);
            telaAviso.titulo = "Erro";
            telaAviso.texto = e.toString();
            telaAviso telaAviso = new telaAviso();
            telaAviso.aviso();
            alterar_veiculo.setDisable(false);

        }
    }

    @FXML
    void deletar(ActionEvent event){
        String placaSelecionada = placa_veiculo.getValue();

        try{
            if (placa_veiculo.getValue() == null){
                alterar_veiculo.setDisable(true);
                telaAviso.titulo = "Alerta";
                telaAviso.texto = "Preencha todos os campos corretamente!";
                telaAviso telaAviso = new telaAviso();
                telaAviso.aviso();
                alterar_veiculo.setDisable(false);

            }else{
                Connection conexao = DriverManager.getConnection(Main.caminho, Main.usuario, Main.senha);
                PreparedStatement placa = conexao.prepareStatement("SELECT placa_veiculo FROM viagem WHERE placa_veiculo = ?");
                placa.setString(1, placa_veiculo.getValue());
                ResultSet placars = placa.executeQuery();
    
                if (placars.next()) {
                    alterar_veiculo.setDisable(true);
                    telaAviso.titulo = "Alerta";
                    telaAviso.texto = "A placa " + placa_veiculo.getValue() + " está registrada em uma viagem e não pode ser deletada!";
                    telaAviso telaAviso = new telaAviso();
                    telaAviso.aviso();
                    alterar_veiculo.setDisable(false);

                } else {
                    PreparedStatement stmt = conexao.prepareStatement("DELETE FROM veiculo WHERE placa = ?");
                    stmt.setString(1, placaSelecionada);
                    stmt.executeUpdate();
    
                    alterar_veiculo.setDisable(true);
                    telaAviso.titulo = "Certo";
                    telaAviso.texto = "Registro deletado com sucesso!";
                    telaAviso telaAviso = new telaAviso();
                    telaAviso.aviso();
                    alterar_veiculo.setDisable(false);
    
                    atualizar();

                }

            }
        }catch(Exception e){
            alterar_veiculo.setDisable(true);
            telaAviso.titulo = "Erro";
            telaAviso.texto = e.toString();
            telaAviso telaAviso = new telaAviso();
            telaAviso.aviso();
            alterar_veiculo.setDisable(false);

        }
    }

    @FXML
    void voltar(ActionEvent event) {
        Main.changeScreen("main");

    }

    public void alterar_Veiculo(){
        Main.changeScreen("alterarVeiculo");

    }
}
