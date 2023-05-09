import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Consulta{

    public static class ConsultaViagens {

        private Integer idViagem;
        private String horaPartida;
        private String horaRetorno;

        private String cpfViajante;
        private String nomeViajante;
        private Date nascimentoViajante;
        private String sexoViajante;

        private String placaVeiculo;
        private String modeloVeiculo;
        private String tipoVeiculo;
        private String anoVeiculo;

        private String cepDestino;
        private String enderecoDestino;

        public ConsultaViagens(Integer idViagem, String horaPartida, String horaRetorno,
            String cpfViajante, String nomeViajante, Date nascimentoViajante, String sexoViajante,
            String placaVeiculo, String modeloVeiculo, String tipoVeiculo,String anoVeiculo,
            String cepDestino, String enderecoDestino){

            this.idViagem = idViagem;
            this.horaPartida = horaPartida;
            this.horaRetorno = horaRetorno;

            this.cpfViajante = cpfViajante;
            this.nomeViajante = nomeViajante;
            this.nascimentoViajante = nascimentoViajante;
            this.sexoViajante = sexoViajante;

            this.placaVeiculo = placaVeiculo;
            this.modeloVeiculo = modeloVeiculo;
            this.tipoVeiculo = tipoVeiculo;
            this.anoVeiculo = anoVeiculo;
            
            this.cepDestino = cepDestino;
            this.enderecoDestino = enderecoDestino;

        }

        public Integer getIdViagem(){return idViagem;}
        public void setIdViagem(Integer idViagem){this.idViagem = idViagem;}

        public String getHoraPartida(){return horaPartida;}
        public void setHoraPartida(String horaPartida){this.horaPartida = horaPartida;}

        public String getHoraRetorno(){return horaRetorno;}
        public void setHoraRetorno(String horaRetorno){this.horaRetorno = horaRetorno;}

        public String getCpfViajante(){return cpfViajante;}
        public void setCpfViajante(String cpfViajante){this.cpfViajante = cpfViajante;}

        public String getNomeViajante(){return nomeViajante;}    
        public void setNomeViajante(String nomeViajante){this.nomeViajante = nomeViajante;}    

        public Date getNascimentoViajante(){return nascimentoViajante;}    
        public void setNascimentoViajante(Date nascimentoViajante){this.nascimentoViajante = nascimentoViajante;}
    
        public String getSexoViajante(){return sexoViajante;}    
        public void setSexoViajante(String sexoViajante){this.sexoViajante = sexoViajante;}

        public String getPlacaVeiculo(){return placaVeiculo;}    
        public void setPlacaVeiculo(String placaVeiculo){this.placaVeiculo = placaVeiculo;}

        public String getModeloVeiculo(){return modeloVeiculo;}    
        public void setModeloVeiculo(String modeloVeiculo){this.modeloVeiculo = modeloVeiculo;}

        public String getTipoVeiculo(){return tipoVeiculo;}    
        public void setTipoVeiculo(String tipoVeiculo){this.tipoVeiculo = tipoVeiculo;}

        public String getAnoVeiculo(){return anoVeiculo;}    
        public void setAnoVeiculo(String anoVeiculo){this.anoVeiculo = anoVeiculo;}

        public String getCepDestino(){return cepDestino;}    
        public void setCepDestino(String cepDestino){this.cepDestino = cepDestino;}

        public String getEnderecoDestino(){return enderecoDestino;}    
        public void setEnderecoDestino(String enderecoDestino){this.enderecoDestino = enderecoDestino;}

    }

    @FXML private TableColumn<Consulta.ConsultaViagens, Integer> colunaId;
    @FXML private TableColumn<Consulta.ConsultaViagens, String> colunaHoraRetorno;
    @FXML private TableColumn<Consulta.ConsultaViagens, String> colunaHoraPartida;

    @FXML private TableColumn<Consulta.ConsultaViagens, String> colunaCPF;
    @FXML private TableColumn<Consulta.ConsultaViagens, String> colunaNome;
    @FXML private TableColumn<Consulta.ConsultaViagens, Date> colunaNascimento;
    @FXML private TableColumn<Consulta.ConsultaViagens, String> colunaSexo;

    @FXML private TableColumn<Consulta.ConsultaViagens, String> colunaPlaca;
    @FXML private TableColumn<Consulta.ConsultaViagens, String> colunaModelo;
    @FXML private TableColumn<Consulta.ConsultaViagens, String> colunaTipo;
    @FXML private TableColumn<Consulta.ConsultaViagens, String> colunaAno;

    @FXML private TableColumn<Consulta.ConsultaViagens, String> colunaCEP;
    @FXML private TableColumn<Consulta.ConsultaViagens, String> colunaEndereco;

    @FXML private TableView<Consulta.ConsultaViagens> tabelaViagens;

    @FXML
    public void initialize(){
        preencherTabela();
    }

    public void setStage(Stage stage){
        stage.setOnShowing(event -> preencherTabela());
    }

    public ObservableList<Consulta.ConsultaViagens> atualizar(){
        ObservableList<Consulta.ConsultaViagens> viagens = FXCollections.observableArrayList();
    
        try (Connection conexao = DriverManager.getConnection(Main.caminho, Main.usuario, Main.senha);
        Statement stmt = conexao.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT " +
            "id, data_hora_partida, data_hora_retorno, pessoa.cpf AS cpf_viajante, pessoa.nome AS nome_viajante, pessoa.nascimento AS nascimento_viajante, pessoa.sexo AS sexo_viajante, " +
            "veiculo.placa AS placa_veiculo, veiculo.modelo AS modelo_veiculo, veiculo.tipo AS tipo_veiculo, veiculo.ano AS ano_veiculo, " +
            "destino.cep AS cep_destino, destino.endereco AS endereco_destino " +
            "FROM viagem " +
            "JOIN pessoa ON viagem.cpf_viajante = pessoa.cpf " +
            "JOIN veiculo ON viagem.placa_veiculo = veiculo.placa " +
            "JOIN destino ON viagem.cep_destino = destino.cep")){
        
            while (rs.next()){
                int idViagem = rs.getInt("id");

                LocalDateTime dataHoraPartida = rs.getTimestamp("data_hora_partida").toLocalDateTime();
                LocalDate data_partida = dataHoraPartida.toLocalDate();
                LocalTime hora_partida = dataHoraPartida.toLocalTime();

                DateTimeFormatter datePFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formattedDateP = data_partida.format(datePFormatter);

                DateTimeFormatter timePFormatter = DateTimeFormatter.ofPattern("HH:mm");
                String formattedTimeP = hora_partida.format(timePFormatter);

                String horaPartida = formattedTimeP + " " + formattedDateP;

                LocalDateTime dataHoraRetorno = rs.getTimestamp("data_hora_retorno").toLocalDateTime();
                LocalDate data_retorno = dataHoraRetorno.toLocalDate();
                LocalTime hora_retorno = dataHoraRetorno.toLocalTime();

                DateTimeFormatter dateRFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formattedDateR = data_retorno.format(dateRFormatter);

                DateTimeFormatter timeRFormatter = DateTimeFormatter.ofPattern("HH:mm");
                String formattedTimeR = hora_retorno.format(timeRFormatter);

                String horaRetorno = formattedTimeR + " " + formattedDateR;

                String cpfViajante = rs.getString("cpf_viajante");
                String nomeViajante = rs.getString("nome_viajante");
                Date nascimentoViajante = rs.getDate("nascimento_viajante");
                String sexoViajante = rs.getString("sexo_viajante");
                
                String placaVeiculo = rs.getString("placa_veiculo");
                String modeloVeiculo = rs.getString("modelo_veiculo");
                String tipoVeiculo = rs.getString("tipo_veiculo");
                String anoVeiculo = rs.getString("ano_veiculo");

                String cepDestino = rs.getString("cep_destino");
                String enderecoDestino = rs.getString("endereco_destino");

                Consulta.ConsultaViagens consultar = new ConsultaViagens(
                idViagem, horaPartida, horaRetorno,
                cpfViajante, nomeViajante, nascimentoViajante, sexoViajante,
                placaVeiculo, modeloVeiculo, tipoVeiculo, anoVeiculo,
                cepDestino, enderecoDestino);

                viagens.add(consultar);
                
            }        
            return viagens;
        } catch (SQLException e){
            telaAviso.titulo = "Erro";
            telaAviso.texto = e.toString();
            telaAviso telaAviso = new telaAviso();
            telaAviso.aviso();
            return null;
        }
    }

    public void preencherTabela(){
        try {
            ObservableList<Consulta.ConsultaViagens> viagens = atualizar();

            colunaId.setCellValueFactory(new PropertyValueFactory<>("idViagem"));
            colunaHoraPartida.setCellValueFactory(new PropertyValueFactory<>("horaPartida"));
            colunaHoraRetorno.setCellValueFactory(new PropertyValueFactory<>("horaRetorno"));

            colunaCPF.setCellValueFactory(new PropertyValueFactory<>("cpfViajante"));
            colunaNome.setCellValueFactory(new PropertyValueFactory<>("nomeViajante"));
            colunaNascimento.setCellValueFactory(new PropertyValueFactory<>("nascimentoViajante"));
            colunaSexo.setCellValueFactory(new PropertyValueFactory<>("sexoViajante"));

            colunaPlaca.setCellValueFactory(new PropertyValueFactory<>("placaVeiculo"));
            colunaModelo.setCellValueFactory(new PropertyValueFactory<>("modeloVeiculo"));
            colunaTipo.setCellValueFactory(new PropertyValueFactory<>("tipoVeiculo"));
            colunaAno.setCellValueFactory(new PropertyValueFactory<>("anoVeiculo"));

            colunaCEP.setCellValueFactory(new PropertyValueFactory<>("cepDestino"));
            colunaEndereco.setCellValueFactory(new PropertyValueFactory<>("enderecoDestino"));

            tabelaViagens.setItems(viagens);

        }catch(Exception e){
            telaAviso.titulo = "Erro";
            telaAviso.texto = e.toString();
            telaAviso telaAviso = new telaAviso();
            telaAviso.aviso();

        }
    }

    @FXML
    void voltar(ActionEvent event) {
        Main.changeScreen("main");

    }

    public void obter_viagens(Stage stage){
        Main main = new Main();
        main.tela("/launchs/consultar_viagem.fxml", "Cadastro de viagem", stage);
        
    }
}