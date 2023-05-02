import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import java.sql.*;

public class Consulta{
    /*
     * Aqui está sendo criado uma classe nova para gerenciar as informações extraídas do banco de dados
     */
    public static class ConsultaViagens {
        /*
         * Estas variáveis serão usadas para armazenar as informações extraídas do banco de dados
         */
        private String cpfViajante;
        private String nomeViajante;
        private String idadeViajante;
        private String sexoViajante;

        private String placaVeiculo;
        private String modeloVeiculo;
        private String tipoVeiculo;
        private String anoVeiculo;

        private String cepDestino;
        private String enderecoDestino;
        /*
         * Aqui está sendo realizado o armazenamento das informações dentro das variáveis privadas desta classe
         */
        public ConsultaViagens(
            String cpfViajante, String nomeViajante, String idadeViajante, String sexoViajante,
            String placaVeiculo, String modeloVeiculo, String tipoVeiculo, String anoVeiculo,
            String cepDestino, String enderecoDestino){
            this.cpfViajante = cpfViajante;
            this.nomeViajante = nomeViajante;
            this.idadeViajante = idadeViajante;
            this.sexoViajante = sexoViajante;

            this.placaVeiculo = placaVeiculo;
            this.modeloVeiculo = modeloVeiculo;
            this.tipoVeiculo = tipoVeiculo;
            this.anoVeiculo = anoVeiculo;
            
            this.cepDestino = cepDestino;
            this.enderecoDestino = enderecoDestino;

        }
        /*
         * Aqui está sendo realizado o procedimento de chamar as variáveis privadas para poder armazenar as informações dentro delas
         */
        public String getCpfViajante(){return cpfViajante;}
        public void setCpfViajante(String cpfViajante){this.cpfViajante = cpfViajante;}

        public String getNomeViajante(){return nomeViajante;}    
        public void setNomeViajante(String nomeViajante){this.nomeViajante = nomeViajante;}    

        public String getIdadeViajante(){return idadeViajante;}    
        public void setIdadeViajante(String idadeViajante){this.idadeViajante = idadeViajante;}
    
        public String getSexoViajante(){return sexoViajante;}    
        public void setSexoViajante(String sexoViajante){this.sexoViajante = sexoViajante;}
        /*
         * Aqui está sendo realizado o procedimento de chamar as variáveis privadas para poder armazenar as informações dentro delas
         */
        public String getPlacaVeiculo(){return placaVeiculo;}    
        public void setPlacaVeiculo(String placaVeiculo){this.placaVeiculo = placaVeiculo;}

        public String getModeloVeiculo(){return modeloVeiculo;}    
        public void setModeloVeiculo(String modeloVeiculo){this.modeloVeiculo = modeloVeiculo;}

        public String getTipoVeiculo(){return tipoVeiculo;}    
        public void setTipoVeiculo(String tipoVeiculo){this.tipoVeiculo = tipoVeiculo;}

        public String getAnoVeiculo(){return anoVeiculo;}    
        public void setAnoVeiculo(String anoVeiculo){this.anoVeiculo = anoVeiculo;}
        /*
         * Aqui está sendo realizado o procedimento de chamar as variáveis privadas para poder armazenar as informações dentro delas
         */
        public String getCepDestino(){return cepDestino;}    
        public void setCepDestino(String cepDestino){this.cepDestino = cepDestino;}

        public String getEnderecoDestino(){return enderecoDestino;}    
        public void setEnderecoDestino(String enderecoDestino){this.enderecoDestino = enderecoDestino;}

    }
    /*
     * Aqui está sendo criado os arraylists que representam as colunas da tabela
     * Estes serão preenchidos com as informações extraídas do banco de dados
     */
    @FXML private TableColumn<Consulta.ConsultaViagens, String> colunaCPF;
    @FXML private TableColumn<Consulta.ConsultaViagens, String> colunaNome;
    @FXML private TableColumn<Consulta.ConsultaViagens, String> colunaIdade;
    @FXML private TableColumn<Consulta.ConsultaViagens, String> colunaSexo;

    @FXML private TableColumn<Consulta.ConsultaViagens, String> colunaPlaca;
    @FXML private TableColumn<Consulta.ConsultaViagens, String> colunaModelo;
    @FXML private TableColumn<Consulta.ConsultaViagens, String> colunaTipo;
    @FXML private TableColumn<Consulta.ConsultaViagens, String> colunaAno;

    @FXML private TableColumn<Consulta.ConsultaViagens, String> colunaCEP;
    @FXML private TableColumn<Consulta.ConsultaViagens, String> colunaEndereco;

    @FXML private TableView<Consulta.ConsultaViagens> tabelaViagens;
    /*
     * Este método é auto iniciado quando a tela de 'consulta' é gerada
     * Aqui é chamado o método 'preencherTabela'
     */
    @FXML
    public void initialize(){
        preencherTabela();
    }
    /*
     * Este método é usado para iniciar o método preencherTabela
     */
    public void setStage(Stage stage){
        stage.setOnShowing(event -> preencherTabela());
    }
    /*
     * Este método está realizando a conexão com o banco de dados
     * Através dele será extraído as informações do banco de dados
     */
    public ObservableList<Consulta.ConsultaViagens> atualizar(){
        /*
         * Aqui está sendo declarado um Array 'viagens'
         */
        ObservableList<Consulta.ConsultaViagens> viagens = FXCollections.observableArrayList();
    
        try (Connection conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/viagem", "postgres", "@1233");
        Statement stmt = conexao.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT " +
            "pessoa.cpf AS cpf_viajante, pessoa.nome AS nome_viajante, pessoa.idade AS idade_viajante, pessoa.sexo AS sexo_viajante, " +
            "veiculo.placa AS placa_veiculo, veiculo.modelo AS modelo_veiculo, veiculo.tipo AS tipo_veiculo, veiculo.ano AS ano_veiculo, " +
            "destino.cep AS cep_destino, destino.endereco AS endereco_destino " +
            "FROM viagem " +
            "JOIN pessoa ON viagem.cpf_viajante = pessoa.cpf " +
            "JOIN veiculo ON viagem.placa_veiculo = veiculo.placa " +
            "JOIN destino ON viagem.cep_destino = destino.cep")) {
        
        while (rs.next()) {
            String cpfViajante = rs.getString("cpf_viajante");
            String nomeViajante = rs.getString("nome_viajante");
            String idadeViajante = rs.getString("idade_viajante");
            String sexoViajante = rs.getString("sexo_viajante");
            
            String placaVeiculo = rs.getString("placa_veiculo");
            String modeloVeiculo = rs.getString("modelo_veiculo");
            String tipoVeiculo = rs.getString("tipo_veiculo");
            String anoVeiculo = rs.getString("ano_veiculo");

            String cepDestino = rs.getString("cep_destino");
            String enderecoDestino = rs.getString("endereco_destino");
            /*
             * Aqui está encaminhando as informações obtidas do banco de dadps
             * Para o método 'ConsultaViagens' da classe interna 'ConsultaViagens'
             */
            Consulta.ConsultaViagens consultar = new ConsultaViagens(
            cpfViajante, nomeViajante, idadeViajante, sexoViajante,
            placaVeiculo, modeloVeiculo, tipoVeiculo, anoVeiculo,
            cepDestino, enderecoDestino);
            /*
             * Aqui está sendo armazenado tudo dentro do Array 'viagens'
             */
            viagens.add(consultar);
        }
        
        return viagens;
    } catch (SQLException e) {
        /*
         * Caso ocorra algum erro
         * Será gerado uma janela informado o erro encontrado
         */
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Erro");
        alerta.setHeaderText(null);
        alerta.setContentText("Erro " + e);

        Image icone = new Image(getClass().getResourceAsStream("/launchs/erro.png"));

        ImageView imageView = new ImageView(icone);
        imageView.setFitHeight(32);
        imageView.setFitWidth(32);
        DialogPane dialogPane = alerta.getDialogPane();
        dialogPane.setGraphic(imageView);

        alerta.showAndWait();
        return null;
    }
    }

    /*
     * Este método está sendo usado para inserir as informações extraídas do banco de dados na tabela presente na janela
     */
    public void preencherTabela(){
        try {
            ObservableList<Consulta.ConsultaViagens> viagens = atualizar();

            colunaCPF.setCellValueFactory(new PropertyValueFactory<>("cpfViajante"));
            colunaNome.setCellValueFactory(new PropertyValueFactory<>("nomeViajante"));
            colunaIdade.setCellValueFactory(new PropertyValueFactory<>("idadeViajante"));
            colunaSexo.setCellValueFactory(new PropertyValueFactory<>("sexoViajante"));

            colunaPlaca.setCellValueFactory(new PropertyValueFactory<>("placaVeiculo"));
            colunaModelo.setCellValueFactory(new PropertyValueFactory<>("modeloVeiculo"));
            colunaTipo.setCellValueFactory(new PropertyValueFactory<>("tipoVeiculo"));
            colunaAno.setCellValueFactory(new PropertyValueFactory<>("anoVeiculo"));

            colunaCEP.setCellValueFactory(new PropertyValueFactory<>("cepDestino"));
            colunaEndereco.setCellValueFactory(new PropertyValueFactory<>("enderecoDestino"));

            tabelaViagens.setItems(viagens);

        }catch(Exception e){
            /*
             * Caso ocorra algum erro
             * Será gerado uma janela informado o erro encontrado
             */
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Erro");
            alerta.setHeaderText(null);
            alerta.setContentText("Erro " + e);

            Image icone = new Image(getClass().getResourceAsStream("/launchs/erro.png"));

            ImageView imageView = new ImageView(icone);
            imageView.setFitHeight(32);
            imageView.setFitWidth(32);
            DialogPane dialogPane = alerta.getDialogPane();
            dialogPane.setGraphic(imageView);

            alerta.showAndWait();

        }
    }
    /*
     * Caso o usuário clique no botão 'voltar'
     * A janela atual irá retornar para o 'menu'
     */
    @FXML
    void voltar(ActionEvent event) {
        Main.changeScreen("main");

    }
    /*
     * Quando a classe Main chamar este método
     * Será chamado o método 'changeScreen' da classe Main para criar a nota janela
     */
    public void obter_viagens(Stage stage){
        Main main = new Main();
        main.tela("/launchs/consultar_viagem.fxml", "Cadastro de viagem", stage);
        
    }
}