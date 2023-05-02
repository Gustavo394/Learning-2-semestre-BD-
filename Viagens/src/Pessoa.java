import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.sql.*;

public class Pessoa{

    public static Stage stage;
    /*
     * Aqui está sendo declarado as variáveis dos recursos usados na tela
     */
    @FXML private TextField nome_pessoa;
    @FXML private TextField cpf_pessoa;
    @FXML private TextField idade_pessoa;
    @FXML private RadioButton masculino;
    @FXML private RadioButton feminino;
    /*
     * Nesta parte está sendo feita a lógica de seleção do 'radio'
     * Quando o radio 'masculino' for selecionado o radio 'feminino' será alterado para 'falso'
     * Para que os dois não estejam selecionados ao mesmo tempo
     */
    @FXML
    void masculinoSelecionado(ActionEvent event){
        if(masculino.isSelected()){
            feminino.setSelected(false);
        }

    }    
    /*
     * Nesta parte está sendo feita a lógica de seleção do 'radio'
     * Quando o radio 'feminino' for selecionado o radio 'masculino' será alterado para 'falso'
     * Para que os dois não estejam selecionados ao mesmo tempo
     */
    @FXML
    void femininoSelecionado(ActionEvent event){
        if(feminino.isSelected()){
            masculino.setSelected(false);
        }

    }
    /*
     * Aqui está sendo criado a ação quando o botão 'salvar' receber algum evento
     */
    @FXML
    void salvar(ActionEvent event){

        try {
            /*
             * Caso algum campo esteja vazio será emitido uma janela onde será informado o erro
             */
            if (cpf_pessoa.getText().isEmpty() || nome_pessoa.getText().isEmpty() || idade_pessoa.getText().isEmpty() || (!masculino.isSelected() && !feminino.isSelected())){
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
                 */
                String sexo = null;
                /*
                 * Será feita a verificação do sexo informado
                 */
                if (masculino.isSelected()){
                    sexo = "Homem";
        
                }
                else if (feminino.isSelected()){
                    sexo = "Mulher";
        
                }                
                /*
                 * Será realizado a conexão com o banco de dados para salvar as novas informações
                 */    
                Connection conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/viagem", "postgres", "@1233");
                Statement stmt = conexao.createStatement();
                String sql = "INSERT INTO pessoa (cpf, nome, idade, sexo) VALUES ('" + String.valueOf(cpf_pessoa.getText())
                + "', '" + String.valueOf(nome_pessoa.getText()) + "', '" + String.valueOf(idade_pessoa.getText()) + "', '" + sexo + "')";
                stmt.executeUpdate(sql);
                stmt.close();
                conexao.close();
                /*
                 * Por fim os campos serão redefinidos
                 * Também será apresentado uma janela informando que a ação foi realizada
                 */
                cpf_pessoa.clear();
                nome_pessoa.clear();
                idade_pessoa.clear();
                masculino.setSelected(false);
                feminino.setSelected(false);

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
        Main.changeScreen("main");

    }
    /*
     * Quando a classe Main chamar este método
     * Será chamado o método 'changeScreen' da classe Main para criar a nota janela
     */
    public void cadastrar_pessoa(){
        Main.changeScreen("pessoa");

    }
}
