import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javafx.scene.layout.AnchorPane;
import java.sql.*;
import java.text.SimpleDateFormat;

public class alterarViagem{

    @FXML private AnchorPane alterar_viagem;

    @FXML private ComboBox<Integer> id_viagem;

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
    
    private ObservableList<Integer> idList = FXCollections.observableArrayList();
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
        {
            idList.clear();
            id_viagem.getItems().clear();
    
            data_partida.setValue(null);
            hora_partida.setValue(null);
            minutos_partida.setValue(null);
    
            data_retorno.setValue(null);
            hora_retorno.setValue(null);
            minutos_retorno.setValue(null);
    
            cpfList.clear();
            cpf_viajante_final.getItems().clear();
            nome_viajante_final.clear();
            nascimento_viajante_final.clear();
            sexo_viajante_final.clear();
            
            placaList.clear();
            placa_veiculo_final.getItems().clear();
            modelo_veiculo_final.clear();
            tipo_veiculo_final.clear();
            ano_veiculo_final.clear();
            
            cepList.clear();
            cep_destino_final.getItems().clear();
            endereco_destino_final.clear();
    
            data_partida.setDisable(true);
            hora_partida.setDisable(true);
            minutos_partida.setDisable(true);
    
            data_retorno.setDisable(true);
            hora_retorno.setDisable(true);
            minutos_retorno.setDisable(true);
            
            cpf_viajante_final.setDisable(true);
            nome_viajante_final.setDisable(true);
            nascimento_viajante_final.setDisable(true);
            sexo_viajante_final.setDisable(true);
    
            placa_veiculo_final.setDisable(true);
            modelo_veiculo_final.setDisable(true);
            tipo_veiculo_final.setDisable(true);
            ano_veiculo_final.setDisable(true);
            
            cep_destino_final.setDisable(true);
            endereco_destino_final.setDisable(true);
        }
    
        ObservableList<Integer> horas = FXCollections.observableArrayList();
        ObservableList<Integer> minutos = FXCollections.observableArrayList();

        for(int i = 0; i <= 24; i++){
            horas.add(i);

        }
        hora_partida.setItems(horas);
        hora_retorno.setItems(horas);

        for(int j = 0; j <= 60; j++){
            minutos.add(j);

        }
        minutos_partida.setItems(minutos);
        minutos_retorno.setItems(minutos);
        
        try{
            Connection conexao = DriverManager.getConnection(Main.caminho, Main.usuario, Main.senha);
            Statement stmt = conexao.createStatement();
            ResultSet idResultSet = stmt.executeQuery("SELECT id FROM viagem");{
                while (idResultSet.next()){
                    int id = idResultSet.getInt("id");
                    idList.add(id);
                }
                id_viagem.setItems(idList);
                idResultSet.close();
            }
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

            if (idList.isEmpty()){
                alterar_viagem.setDisable(true);
                telaAviso.titulo = "Alerta";
                telaAviso.texto = "Não existem viagens cadastradas!";
                telaAviso telaAviso = new telaAviso();
                telaAviso.aviso();
                alterar_viagem.setDisable(false);
            }

            conexao.close();
            stmt.close();

        }catch(Exception e){
            alterar_viagem.setDisable(true);
            telaAviso.titulo = "Erro";
            telaAviso.texto = e.toString();
            telaAviso telaAviso = new telaAviso();
            telaAviso.aviso();
            alterar_viagem.setDisable(false);
        }
    }

    @FXML
    void selectec_id_viagem(ActionEvent event) {
        if (id_viagem.getValue() != null){
            try(Connection conexao = DriverManager.getConnection(Main.caminho, Main.usuario, Main.senha);
                PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM viagem WHERE id = ?")){
                stmt.setInt(1, id_viagem.getValue());
                ResultSet rs = stmt.executeQuery();
    
                if (rs.next()) {
                    cpf_viajante_final.setValue(rs.getString("cpf_viajante"));
                    placa_veiculo_final.setValue(rs.getString("placa_veiculo"));
                    cep_destino_final.setValue(rs.getInt("cep_destino"));

                    LocalDateTime dataHoraPartida = rs.getTimestamp("data_hora_partida").toLocalDateTime();
                    LocalDate dataPartida = dataHoraPartida.toLocalDate();
                    data_partida.setValue(dataPartida);
                    LocalTime horaP = dataHoraPartida.toLocalTime();
                    int horaPartida = horaP.getHour();
                    hora_partida.setValue(horaPartida);
                    int minutosPartida = horaP.getMinute();
                    minutos_partida.setValue(minutosPartida);

                    LocalDateTime dataHoraRetorno = rs.getTimestamp("data_hora_retorno").toLocalDateTime();
                    LocalDate dataRetorno = dataHoraRetorno.toLocalDate();
                    data_retorno.setValue(dataRetorno);
                    LocalTime horaR = dataHoraRetorno.toLocalTime();
                    int horaRetorno = horaR.getHour();
                    hora_retorno.setValue(horaRetorno);
                    int minutoRetorno = horaR.getMinute();
                    minutos_retorno.setValue(minutoRetorno);
                    
                }
                rs.close();
                stmt.close();
                conexao.close();

                data_partida.setDisable(false);
                hora_partida.setDisable(false);
                minutos_partida.setDisable(false);

                data_retorno.setDisable(false);
                hora_retorno.setDisable(false);
                minutos_retorno.setDisable(false);

                cpf_viajante_final.setDisable(false);
                nome_viajante_final.setDisable(false);
                nascimento_viajante_final.setDisable(false);
                sexo_viajante_final.setDisable(false);
        
                placa_veiculo_final.setDisable(false);
                modelo_veiculo_final.setDisable(false);
                tipo_veiculo_final.setDisable(false);
                ano_veiculo_final.setDisable(false);
                
                cep_destino_final.setDisable(false);
                endereco_destino_final.setDisable(false);
                
            }catch(Exception e){
                alterar_viagem.setDisable(true);
                telaAviso.titulo = "Erro";
                telaAviso.texto = e.toString();
                telaAviso telaAviso = new telaAviso();
                telaAviso.aviso();
                alterar_viagem.setDisable(false);
            }
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
                rs.close();
                stmt.close();
                conexao.close();
    
            }catch(Exception e){
                alterar_viagem.setDisable(true);
                telaAviso.titulo = "Erro";
                telaAviso.texto = e.toString();
                telaAviso telaAviso = new telaAviso();
                telaAviso.aviso();
                alterar_viagem.setDisable(false);
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

            rs.close();
            stmt.close();
            conexao.close();

            }catch(Exception e){
                alterar_viagem.setDisable(true);
                telaAviso.titulo = "Erro";
                telaAviso.texto = e.toString();
                telaAviso telaAviso = new telaAviso();
                telaAviso.aviso();
                alterar_viagem.setDisable(false);
            }
        }
    }

    @FXML
    void selectec_cep_destino_final(ActionEvent event){
        if (cep_destino_final.getValue() != null){
            try(Connection conexao = DriverManager.getConnection(Main.caminho, Main.usuario, Main.senha);
                PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM destino WHERE cep = ?")){
                stmt.setInt(1, cep_destino_final.getValue());
                ResultSet rs = stmt.executeQuery();
    
                if (rs.next()) {
                    endereco_destino_final.setText(rs.getString("endereco"));
                }

                rs.close();
                stmt.close();
                conexao.close();
    
            }catch(Exception e){
                alterar_viagem.setDisable(true);
                telaAviso.titulo = "Erro";
                telaAviso.texto = e.toString();
                telaAviso telaAviso = new telaAviso();
                telaAviso.aviso();
                alterar_viagem.setDisable(false);
            }
        }
    }

    @FXML
    void alterar(ActionEvent event){
        LocalDate partida = data_partida.getValue();
        LocalDate retorno = data_retorno.getValue();

        try{
            if (id_viagem.getValue() == null){
                alterar_viagem.setDisable(true);
                telaAviso.titulo = "Alerta";
                telaAviso.texto = "Selecine um ID da viagem!";
                telaAviso telaAviso = new telaAviso();
                telaAviso.aviso();
                alterar_viagem.setDisable(false);
                

            } else if (data_partida.getValue().equals(data_retorno.getValue()) || !retorno.isAfter(partida.plusDays(0))) {
            alterar_viagem.setDisable(true);
            telaAviso.titulo = "Alerta";
            telaAviso.texto = "A data de retorno deve ser no mínimo um dia após a data de partida!";
            telaAviso telaAviso = new telaAviso();
            telaAviso.aviso();
            alterar_viagem.setDisable(false);

            } else {
                Connection conexao = DriverManager.getConnection(Main.caminho, Main.usuario, Main.senha);
                PreparedStatement stmt = conexao.prepareStatement("UPDATE viagem SET" +
                " cpf_viajante = ?, placa_veiculo = ?, cep_destino = ?, data_hora_partida = ?, data_hora_retorno = ? WHERE id = ?");
                stmt.setString(1, cpf_viajante_final.getValue());
                stmt.setString(2, placa_veiculo_final.getValue());
                stmt.setInt(3, Integer.valueOf(cep_destino_final.getValue()));
                stmt.setTimestamp(4, java.sql.Timestamp.valueOf(data_partida.getValue() + " " + hora_partida.getValue().toString() + ":" + minutos_partida.getValue().toString() + ":00"));
                stmt.setTimestamp(5, java.sql.Timestamp.valueOf(data_retorno.getValue() + " " + hora_retorno.getValue().toString() + ":" + minutos_retorno.getValue().toString() + ":00"));
                stmt.setInt(6, id_viagem.getValue());
                stmt.executeUpdate();

                alterar_viagem.setDisable(true);
                telaAviso.titulo = "Certo";
                telaAviso.texto = "Registro alterado com sucesso!";
                telaAviso telaAviso = new telaAviso();
                telaAviso.aviso();
            alterar_viagem.setDisable(false);

                stmt.close();
                conexao.close();

                atualizar();
            }

        } catch(Exception e) {
            alterar_viagem.setDisable(true);
            telaAviso.titulo = "Erro";
            telaAviso.texto = e.toString();
            telaAviso telaAviso = new telaAviso();
            telaAviso.aviso();
            alterar_viagem.setDisable(false);

        }
    }

    @FXML
    void deletar(ActionEvent event) {
        try(Connection conexao = DriverManager.getConnection(Main.caminho, Main.usuario, Main.senha);
            PreparedStatement stmt = conexao.prepareStatement("DELETE FROM viagem WHERE id = ?")){
            stmt.setInt(1, id_viagem.getValue());
            stmt.executeUpdate();

            alterar_viagem.setDisable(true);
            telaAviso.titulo = "Certo";
            telaAviso.texto = "Registro deletado com sucesso!";
            telaAviso telaAviso = new telaAviso();
            telaAviso.aviso();
            alterar_viagem.setDisable(false);

            stmt.close();
            conexao.close();

            atualizar();

        }catch(Exception e){
            alterar_viagem.setDisable(true);
            telaAviso.titulo = "Erro";
            telaAviso.texto = e.toString();
            telaAviso telaAviso = new telaAviso();
            telaAviso.aviso();
            alterar_viagem.setDisable(false);
        }
    }

    @FXML
    void voltar(ActionEvent event) {
        Main.changeScreen("main");
    }

    public void alterar_viagem(Stage stage){
        Main main = new Main();
        main.tela("/launchs/alterar_cadastro_viagem.fxml", "Cadastro de viagem", stage);
    }
}
