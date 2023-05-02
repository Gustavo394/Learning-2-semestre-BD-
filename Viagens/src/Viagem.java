import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class Viagem{
    /*
     * Aqui está sendo declarado as variáveis dos recursos usados na tela
     */
    @FXML private ComboBox<String> cpf_viajante_final;
    @FXML private TextField nome_viajante_final;
    @FXML private TextField idade_viajante_final;
    @FXML private TextField sexo_viajante_final;

    @FXML private ComboBox<String> placa_veiculo_final;
    @FXML private TextField modelo_veiculo_final;
    @FXML private TextField tipo_veiculo_final;
    @FXML private TextField ano_veiculo_final;

    @FXML private ComboBox<String> cep_destino_final;
    @FXML private TextField endereco_destino_final;

    @FXML private Button btndownload;
    /*
     * Aqui está sendo criado alguns Arrays que serão preenchidos com as informações do banco de dados
     */
    private ObservableList<String> cpfList = FXCollections.observableArrayList();
    private ObservableList<String> placaList = FXCollections.observableArrayList();
    private ObservableList<String> cepList = FXCollections.observableArrayList();
    /*
     * Este método é usado para manter atualizado a tela com as informações novas
     */
    @FXML
    public void initialize(){
        atualizar();
    }
    /*
     * Este método é usado para mostrar as novas informações
     */
    public void setStage(Stage stage){
        stage.setOnShowing(event -> atualizar());
    }
    
    public void atualizar(){
        cpfList.clear();
        placaList.clear();
        cepList.clear();

        try{
            /*
             * Aqui será realizado a conexão com o banco de dados para salvar as novas informações
             */
            Connection conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/viagem", "postgres", "@1233");
            Statement stmt = conexao.createStatement();
            /*
             * Aqui está sendo passado um comando para extrair informações do banco de dados
             * Estas informações serão armazenadas em um array
             */
            ResultSet cpfResultSet = stmt.executeQuery("SELECT cpf FROM pessoa");{
                while (cpfResultSet.next()){
                    String cpf = cpfResultSet.getString("cpf");
                    cpfList.add(cpf);
                    
                }    
                cpf_viajante_final.setItems(cpfList);
                cpfResultSet.close();

            }
            /*
             * Aqui está sendo passado um comando para extrair informações do banco de dados
             * Estas informações serão armazenadas em um array
             */
            ResultSet placaResultSet = stmt.executeQuery("SELECT placa FROM veiculo");{
                while (placaResultSet.next()){
                    String placa = placaResultSet.getString("placa");
                    placaList.add(placa);
                    
                }    
                placa_veiculo_final.setItems(placaList);
                placaResultSet.close();

            }
            /*
             * Aqui está sendo passado um comando para extrair informações do banco de dados
             * Estas informações serão armazenadas em um array
             */
            ResultSet cepResultSet = stmt.executeQuery("SELECT cep FROM destino");{
                while (cepResultSet.next()){
                    String cep = cepResultSet.getString("cep");
                    cepList.add(cep);
                    
                }    
                cep_destino_final.setItems(cepList);
                cepResultSet.close();

            }
            conexao.close();
            stmt.close();

            cpf_viajante_final.setValue(null);
            nome_viajante_final.clear();
            idade_viajante_final.clear();
            sexo_viajante_final.clear();

            placa_veiculo_final.setValue(null);
            modelo_veiculo_final.clear();
            tipo_veiculo_final.clear();
            ano_veiculo_final.clear();

            cep_destino_final.setValue(null);
            endereco_destino_final.clear();

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
     * Este método será utilizado quando for selecionado alguma informação no combobox
     * No combobox serão mostradas chaves primarias de tabelas do banco de dados
     * Todos os outros campos serão preenchidos com as informações correspondentes
     */
    @FXML
    void selectec_cpf_viajante_final(ActionEvent event){
        String cpfSelecionado = cpf_viajante_final.getValue();

        try(Connection conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/viagem", "postgres", "@1233");
            PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM pessoa WHERE cpf = ?")){
            stmt.setString(1, cpfSelecionado);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nome_viajante_final.setText(rs.getString("nome"));
                idade_viajante_final.setText(rs.getString("idade"));
                sexo_viajante_final.setText(rs.getString("sexo"));
            }

        }catch(Exception e){

        }

    }
    /*
     * Este método será utilizado quando for selecionado alguma informação no combobox
     * No combobox serão mostradas chaves primarias de tabelas do banco de dados
     * Todos os outros campos serão preenchidos com as informações correspondentes
     */
    @FXML
    void selectec_placa_carro_final(ActionEvent event){
        String placaSelecionada = placa_veiculo_final.getValue();

        try(Connection conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/viagem", "postgres", "@1233");
            PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM veiculo WHERE placa = ?")){
            stmt.setString(1, placaSelecionada);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                modelo_veiculo_final.setText(rs.getString("modelo"));
                tipo_veiculo_final.setText(rs.getString("tipo"));
                ano_veiculo_final.setText(rs.getString("ano"));
            }

        }catch(Exception e){

        }

    }
    /*
     * Este método será utilizado quando for selecionado alguma informação no combobox
     * No combobox serão mostradas chaves primarias de tabelas do banco de dados
     * Todos os outros campos serão preenchidos com as informações correspondentes
     */
    @FXML
    void selectec_cep_destino_final(ActionEvent event){
        String cepSelecionado = cep_destino_final.getValue();

        try(Connection conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/viagem", "postgres", "@1233");
            PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM destino WHERE cep = ?")){
            stmt.setString(1, cepSelecionado);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                endereco_destino_final.setText(rs.getString("endereco"));
            }

        }catch(Exception e){

        }

    }
    /*
     * Aqui está sendo criado a ação quando o botão 'salvar' receber algum evento
     */
    @FXML
    void salvar(ActionEvent event){
        try{
            /*
             * Caso algum campo esteja vazio será emitido uma janela onde será informado o erro
             */
            if(cpf_viajante_final.getValue() == null || placa_veiculo_final.getValue() == null || cep_destino_final.getValue() == null){
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Alerta");
                alerta.setHeaderText(null);
                alerta.setContentText("Preencha todos os campos corretamente!");

                Image icone = new Image(getClass().getResourceAsStream("/launchs/aviso.png"));

                ImageView imageView = new ImageView(icone);
                imageView.setFitHeight(32);
                imageView.setFitWidth(32);
                DialogPane dialogPane = alerta.getDialogPane();
                dialogPane.setGraphic(imageView);

                alerta.showAndWait();
            
            }else{
                /*
                 * Se estiver tudo certo na hora de registrar as informações
                 * Será realizado a conexão com o banco de dados para salvar as novas informações
                 */
                Connection conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/viagem", "postgres", "@1233");
                PreparedStatement stmt = conexao.prepareStatement("INSERT INTO viagem (cpf_viajante, placa_veiculo, cep_destino) VALUES ('"
                + cpf_viajante_final.getValue() + "', '" + placa_veiculo_final.getValue() + "', '" + cep_destino_final.getValue() + "')");
                stmt.executeUpdate();
                conexao.close();
                stmt.close();
                /*
                 * Por fim os campos serão redefinidos
                 * Também será apresentado uma janela informando que a ação foi realizada
                 */
                cpf_viajante_final.setValue(null);
                nome_viajante_final.clear();
                idade_viajante_final.clear();
                sexo_viajante_final.clear();

                placa_veiculo_final.setValue(null);
                modelo_veiculo_final.clear();
                tipo_veiculo_final.clear();
                ano_veiculo_final.clear();

                cep_destino_final.setValue(null);
                endereco_destino_final.clear();

                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Sucesso");
                alerta.setHeaderText(null);
                alerta.setContentText("Registro realizado com sucesso!");

                String caminhoIcone = "/launchs/sucesso.png";
                ImageView icon = new ImageView(new Image(caminhoIcone));
                icon.setFitHeight(32);
                icon.setFitWidth(32);
                alerta.setGraphic(icon);

                alerta.showAndWait();

            }
            
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
        cpf_viajante_final.setValue(null);
        nome_viajante_final.clear();
        idade_viajante_final.clear();
        sexo_viajante_final.clear();

        placa_veiculo_final.setValue(null);
        modelo_veiculo_final.clear();
        tipo_veiculo_final.clear();
        ano_veiculo_final.clear();

        cep_destino_final.setValue(null);
        endereco_destino_final.clear();
        Main.changeScreen("main");

    }
    /*
     * Quando a classe Main chamar este método
     * Será chamado o método 'changeScreen' da classe Main para criar a nota janela
     */
    public void cadastro_viagem(Stage stage){
        Main main = new Main();
        main.tela("/launchs/cadastro_viagem.fxml", "Cadastro de viagem", stage);
        
    }
}
