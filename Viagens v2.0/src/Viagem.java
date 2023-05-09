import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;
import javafx.scene.layout.AnchorPane;
import java.sql.*;
import java.text.SimpleDateFormat;

public class Viagem{

    @FXML private AnchorPane cadastro_viagem;

    @FXML private DatePicker data_partida;
    @FXML private ComboBox<Integer> hora_partida;
    @FXML private ComboBox<Integer> minutos_partida;

    @FXML private DatePicker data_retorno;
    @FXML private ComboBox<Integer> hora_retorno;
    @FXML private ComboBox<Integer> minutos_retorno;

    @FXML private ComboBox<String> cpf_viajante_final;
    @FXML private TextField nome_viajante_final;
    @FXML private TextField nascimento_viajante_final;
    @FXML private TextField sexo_viajante_final;

    @FXML private ComboBox<String> placa_veiculo_final;
    @FXML private TextField modelo_veiculo_final;
    @FXML private TextField tipo_veiculo_final;
    @FXML private TextField ano_veiculo_final;

    @FXML private ComboBox<Integer> cep_destino_final;
    @FXML private TextField endereco_destino_final;

    private ObservableList<String> cpfList = FXCollections.observableArrayList();
    private ObservableList<String> placaList = FXCollections.observableArrayList();
    private ObservableList<Integer> cepList = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        atualizar();
    }

    public void setStage(Stage stage){
        stage.setOnShowing(event -> atualizar());
    }
    
    public void atualizar(){
        cpfList.clear();
        placaList.clear();
        cepList.clear();

        ObservableList<Integer> horas = FXCollections.observableArrayList();
        ObservableList<Integer> minutos = FXCollections.observableArrayList();

        for(int i = 0; i <= 23; i++){
            horas.add(i);

        }
        hora_partida.setItems(horas);
        hora_retorno.setItems(horas);


        for(int j = 0; j <= 59; j++){
            minutos.add(j);

        }
        minutos_partida.setItems(minutos);
        minutos_retorno.setItems(minutos);


        try{
            Connection conexao = DriverManager.getConnection(Main.caminho, Main.usuario, Main.senha);
            Statement stmt = conexao.createStatement();

            ResultSet cpfResultSet = stmt.executeQuery("SELECT cpf FROM pessoa");{
                while (cpfResultSet.next()){
                    String cpf = cpfResultSet.getString("cpf");
                    cpfList.add(cpf);
                }    
                
                cpf_viajante_final.setItems(cpfList);
                cpfResultSet.close();
            }

            ResultSet placaResultSet = stmt.executeQuery("SELECT placa FROM veiculo");{
                while (placaResultSet.next()){
                    String placa = placaResultSet.getString("placa");
                    placaList.add(placa);
                }    

                placa_veiculo_final.setItems(placaList);
                placaResultSet.close();
            }

            ResultSet cepResultSet = stmt.executeQuery("SELECT cep FROM destino");{
                while (cepResultSet.next()){
                    Integer cep = cepResultSet.getInt("cep");
                    cepList.add(cep);
                }

                cep_destino_final.setItems(cepList);
                cepResultSet.close();
            }

            conexao.close();
            stmt.close();

            cpf_viajante_final.setValue(null);
            nome_viajante_final.clear();
            nascimento_viajante_final.clear();
            sexo_viajante_final.clear();

            placa_veiculo_final.setValue(null);
            modelo_veiculo_final.clear();
            tipo_veiculo_final.clear();
            ano_veiculo_final.clear();

            cep_destino_final.setValue(null);
            endereco_destino_final.clear();

            data_partida.setValue(null);
            hora_partida.setValue(null);
            minutos_partida.setValue(null);

            data_retorno.setValue(null);
            hora_retorno.setValue(null);
            minutos_retorno.setValue(null);

        }catch(Exception e){
            cadastro_viagem.setDisable(true);
            telaAviso.titulo = "Erro";
            telaAviso.texto = e.toString();
            telaAviso telaAviso = new telaAviso();
            telaAviso.aviso();
            cadastro_viagem.setDisable(false);
        }
    }

    @FXML
    void selectec_cpf_viajante_final(ActionEvent event){
        if (cpf_viajante_final.getValue() != null){
            try(Connection conexao = DriverManager.getConnection(Main.caminho, Main.usuario, Main.senha);
                PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM pessoa WHERE cpf = ?")){
                stmt.setString(1, cpf_viajante_final.getValue());
                ResultSet rs = stmt.executeQuery();
    
                if (rs.next()) {

                    Date date = rs.getDate("nascimento");
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String formattedDate = sdf.format(date);

                    nome_viajante_final.setText(rs.getString("nome"));
                    nascimento_viajante_final.setText(formattedDate);
                    sexo_viajante_final.setText(rs.getString("sexo"));
                }
    
            }catch(Exception e){
                cadastro_viagem.setDisable(true);
                telaAviso.titulo = "Erro";
                telaAviso.texto = e.toString();
                telaAviso telaAviso = new telaAviso();
                telaAviso.aviso();
                cadastro_viagem.setDisable(false);
            }
        }
    }

    @FXML
    void selectec_placa_carro_final(ActionEvent event){
        if (placa_veiculo_final.getValue() != null){
            try(Connection conexao = DriverManager.getConnection(Main.caminho, Main.usuario, Main.senha);
                PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM veiculo WHERE placa = ?")){
                stmt.setString(1, placa_veiculo_final.getValue());
                ResultSet rs = stmt.executeQuery();
    
                if (rs.next()) {
                    modelo_veiculo_final.setText(rs.getString("modelo"));
                    tipo_veiculo_final.setText(rs.getString("tipo"));
                    ano_veiculo_final.setText(rs.getString("ano"));
                }
    
            }catch(Exception e){
                cadastro_viagem.setDisable(true);
                telaAviso.titulo = "Erro";
                telaAviso.texto = e.toString();
                telaAviso telaAviso = new telaAviso();
                telaAviso.aviso();
                cadastro_viagem.setDisable(false);
            }
        }
    }

    @FXML
    void selectec_cep_destino_final(ActionEvent event){
        if (cep_destino_final.getValue() != null){
            try(Connection conexao = DriverManager.getConnection(Main.caminho, Main.usuario, Main.senha);
            PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM destino WHERE cep = ?")){
            stmt.setInt(1, Integer.valueOf(cep_destino_final.getValue()));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                endereco_destino_final.setText(rs.getString("endereco"));
            }

            }catch(Exception e){
                cadastro_viagem.setDisable(true);
                telaAviso.titulo = "Erro";
                telaAviso.texto = e.toString();
                telaAviso telaAviso = new telaAviso();
                telaAviso.aviso();
                cadastro_viagem.setDisable(false);
            }
        }
    }

    @FXML
    void salvar(ActionEvent event){
        LocalDate partida = data_partida.getValue();
        LocalDate retorno = data_retorno.getValue();

        try{
            if (cpf_viajante_final.getValue() == null || placa_veiculo_final.getValue() == null || cep_destino_final.getValue() == null ||
                data_partida.getValue() == null || hora_partida.getValue() == null || minutos_partida.getValue() == null ||
                data_retorno.getValue() == null || hora_retorno.getValue() == null || minutos_retorno.getValue() == null){

                cadastro_viagem.setDisable(true);
                telaAviso.titulo = "Alerta";
                telaAviso.texto = "Preencha todos os campos corretamente!";
                telaAviso telaAviso = new telaAviso();
                telaAviso.aviso();
                cadastro_viagem.setDisable(false);

            } else if (data_partida.getValue().equals(data_retorno.getValue()) || !retorno.isAfter(partida.plusDays(0))) {
                cadastro_viagem.setDisable(true);
                telaAviso.titulo = "Alerta";
                telaAviso.texto = "A data de retorno deve ser no mínimo um dia após a data de partida!";
                telaAviso telaAviso = new telaAviso();
                telaAviso.aviso();
                cadastro_viagem.setDisable(false);
            } else {
                    Connection conexao = DriverManager.getConnection(Main.caminho, Main.usuario, Main.senha);
                    PreparedStatement stmt = conexao.prepareStatement("INSERT INTO viagem (cpf_viajante, placa_veiculo, cep_destino, data_hora_partida, data_hora_retorno)" +
                    " VALUES (?, ?, ?, ?, ?)");
                    stmt.setString(1, cpf_viajante_final.getValue());
                    stmt.setString(2, placa_veiculo_final.getValue());
                    stmt.setInt(3, Integer.valueOf(cep_destino_final.getValue()));
                    stmt.setTimestamp(4, java.sql.Timestamp.valueOf(data_partida.getValue() + " " + hora_partida.getValue().toString() + ":" + minutos_partida.getValue().toString() + ":00"));
                    stmt.setTimestamp(5, java.sql.Timestamp.valueOf(data_retorno.getValue() + " " + hora_retorno.getValue().toString() + ":" + minutos_retorno.getValue().toString() + ":00"));
                    stmt.executeUpdate();
                    conexao.close();
                    stmt.close();

                    cadastro_viagem.setDisable(true);
                    telaAviso.titulo = "Certo";
                    telaAviso.texto = "Registro realizado com sucesso!";
                    telaAviso telaAviso = new telaAviso();
                    telaAviso.aviso();
                    cadastro_viagem.setDisable(false);
                    
                    atualizar();
            }
                                
        }catch(Exception e){
            cadastro_viagem.setDisable(true);
            telaAviso.titulo = "Erro";
            telaAviso.texto = e.toString();
            telaAviso telaAviso = new telaAviso();
            telaAviso.aviso();
            cadastro_viagem.setDisable(false);
        }
    }

    @FXML
    void voltar(ActionEvent event) {
        Main.changeScreen("main");
    }

    public void cadastro_viagem(Stage stage){
        Main main = new Main();
        main.tela("/launchs/cadastro_viagem.fxml", "Cadastro de viagem", stage);
    }
}
