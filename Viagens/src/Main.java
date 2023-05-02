import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Main extends Application{

    /*
     * A classe Stage é usada para criar e manipular uma janela
     * Através desta classe você pode definir título, ícone, conteúdo, tamanho e posição da janela na tela
     */
    public static Stage stage;

    /*
     * O método Start é um ponto de partida
     * Onde podemos definir instruções que vão ser executadas logo ao iniciar o programa
     * Neste caso estamos enviando para o método 'tela' as instrução necessária para criar a tela 'menu'
     * Onde ela será iniciada com o arquivo 'tela_menu.fxml' e o título 'Menu'
     */
    @Override
    public void start(Stage primaryStage){
        tela("/launchs/tela_menu.fxml", "Menu", primaryStage);

    }

    /*
     * Este método 'tela' é usado para criar uma nova tela de acordo com as informações recebidas
     * A variável 'fxml' representa o arquivo para criar a tela
     * A variável 'título' representa o título que a tela vai receber
     * A raviável 'primaryStage' representa as informações que vão ser usadas na nova tela
     */
    public void tela(String fxml, String titulo, Stage primaryStage){

        try{
            stage = primaryStage;
            primaryStage.setTitle(titulo);
            Parent loader = FXMLLoader.load(getClass().getResource(fxml));
            Scene scene = new Scene(loader);

            Image icon = new Image(getClass().getResourceAsStream("/launchs/icone.png"));
            stage.getIcons().add(icon);
            
            /*
             * Aqui está sendo passado instruções para sempre manter a janela centralizada
             * De acordo com o tamanho da janela e da tela do usuário
             */
            primaryStage.sizeToScene();
            
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            
            double x = (screenBounds.getWidth() - loader.prefWidth(-1)) / 2;
            double y = (screenBounds.getHeight() - loader.prefHeight(-1)) / 2;
            stage.setX(x);
            stage.setY(y);
    
            primaryStage.setScene(scene);
            primaryStage.show();

        }catch(IOException e){
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
     * Aqui estamos chamando um método de outra classe
     */
    @FXML
    void cadastrar_pessoa(ActionEvent event){
        Pessoa pessoa = new Pessoa();
        pessoa.cadastrar_pessoa();

    }

    /*
     * Aqui estamos chamando um método de outra classe
     */
    @FXML
    void cadastrar_veiculo(ActionEvent event){
        Veiculo veiculo = new Veiculo();
        veiculo.cadastro_veiculo();

    }
    
    /*
     * Aqui estamos chamando um método de outra classe
     */
    @FXML
    void cadastrar_destino(ActionEvent event){
        Destino destino = new Destino();
        destino.cadastro_destino();

    }
    
    /*
     * Aqui estamos chamando um método de outra classe
     */
    @FXML
    void cadastrar_viagem(ActionEvent event){
        Viagem viagem = new Viagem();
        viagem.cadastro_viagem(stage);

    }
    
    /*
     * Aqui estamos chamando um método de outra classe
     */
    @FXML
    void consultar_viagem(ActionEvent event){
        Consulta consulta = new Consulta();
        consulta.obter_viagens(stage);

    }

    /*
     * Este método é usado para mudar a tela de acordo com as ações do usuário
     */
    public static void changeScreen(String scr){
        Main main = new Main();
        switch (scr){
            case "main":
                main.tela("/launchs/tela_menu.fxml", "Menu", stage);
                break;
            case "pessoa":
                main.tela("/launchs/cadastro_pessoa.fxml", "Cadastro de pessoa", stage);
                break;
            case "veiculo":
            main.tela("/launchs/cadastro_veiculo.fxml", "Cadastro de veículo", stage);
                break;
            case "destino":
            main.tela("/launchs/cadastro_destino.fxml", "Cadastro de destino", stage);
                break;

        }
    }

    public static void main (String[] args){
        launch();

    }
}
