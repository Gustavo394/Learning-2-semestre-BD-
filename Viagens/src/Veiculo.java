import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import java.sql.*;

public class Veiculo{
    /*
     * Aqui está sendo declarado as variáveis dos recursos usados na tela
     */    
    @FXML private TextField placa_veiculo;
    @FXML private TextField modelo_veiculo;
    @FXML private TextField tipo_veiculo;
    @FXML private TextField ano_veiculo;
    /*
     * Aqui está sendo criado a ação quando o botão 'salvar' receber algum evento
     */
    @FXML
    void salvar(ActionEvent event){
        try{
            /*
             * Caso algum campo esteja vazio será emitido uma janela onde será informado o erro
             */
            if (placa_veiculo.getText().isEmpty() || modelo_veiculo.getText().isEmpty() || tipo_veiculo.getText().isEmpty() || ano_veiculo.getText().isEmpty()){
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
                Statement stmt = conexao.createStatement();
                String sql = "INSERT INTO veiculo (placa, modelo, tipo, ano) VALUES ('" + String.valueOf(placa_veiculo.getText())
                + "', '" + String.valueOf(modelo_veiculo.getText()) + "', '" + String.valueOf(tipo_veiculo.getText())
                + "', '" + String.valueOf(ano_veiculo.getText()) + "')";
                stmt.executeUpdate(sql);
                stmt.close();
                conexao.close();
                /*
                 * Por fim os campos serão redefinidos
                 * Também será apresentado uma janela informando que a ação foi realizada
                 */
                placa_veiculo.clear();
                modelo_veiculo.clear();
                tipo_veiculo.clear();
                ano_veiculo.clear();

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
    void voltar(ActionEvent event){
        placa_veiculo.clear();
        modelo_veiculo.clear();
        tipo_veiculo.clear();
        ano_veiculo.clear();
        Main.changeScreen("main");

    }
    /*
     * Quando a classe Main chamar este método
     * Será chamado o método 'changeScreen' da classe Main para criar a nota janela
     */
    public void cadastro_veiculo(){
        Main.changeScreen("veiculo");
        
    }
}
