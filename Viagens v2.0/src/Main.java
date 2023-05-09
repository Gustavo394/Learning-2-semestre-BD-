import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;

public class Main extends Application{

    @FXML private AnchorPane tela_menu;

    static String caminho = "jdbc:postgresql://localhost:5432/viagem";
    static String usuario = "postgres";
    static String senha = "@1233";

    public static Stage stage;

    @Override
    public void start(Stage primaryStage){
        tela("/launchs/tela_menu.fxml", "Menu", primaryStage);
    }

    public void tela(String fxml, String titulo, Stage primaryStage){

        try{
            stage = primaryStage;
            primaryStage.setTitle(titulo);
            Parent loader = FXMLLoader.load(getClass().getResource(fxml));
            Scene scene = new Scene(loader);

            Image icon = new Image(getClass().getResourceAsStream("/launchs/icone.png"));
            primaryStage.getIcons().add(icon);

            primaryStage.sizeToScene();
            
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            
            double x = (screenBounds.getWidth() - loader.prefWidth(-1)) / 2;
            double y = (screenBounds.getHeight() - loader.prefHeight(-1)) / 2;
            primaryStage.setX(x);
            primaryStage.setY(y);
    
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();

        }catch(Exception e){
            tela_menu.setDisable(true);
            telaAviso.titulo = "Erro";
            telaAviso.texto = e.toString();
            telaAviso telaAviso = new telaAviso();
            telaAviso.aviso();
            tela_menu.setDisable(false);
        }
    }

    @FXML
    void cadastrar_pessoa(ActionEvent event){
        tela_menu.setDisable(true);
        Pessoa pessoa = new Pessoa();
        pessoa.cadastrar_pessoa();
    }

    @FXML
    void alterar_pessoa(ActionEvent event){
        tela_menu.setDisable(true);
        alterarPessoa pessoa = new alterarPessoa();
        pessoa.alterar_Pessoa();
    }

    @FXML
    void cadastrar_veiculo(ActionEvent event){
        tela_menu.setDisable(true);
        Veiculo veiculo = new Veiculo();
        veiculo.cadastro_veiculo();
    }

    @FXML
    void alterar_veiculo(ActionEvent event){
        tela_menu.setDisable(true);
        alterarVeiculo veiculo = new alterarVeiculo();
        veiculo.alterar_Veiculo();
    }

    @FXML
    void cadastrar_destino(ActionEvent event){
        tela_menu.setDisable(true);
        Destino destino = new Destino();
        destino.cadastro_destino();
    }
    @FXML
    void alterar_destino(ActionEvent event) {
        tela_menu.setDisable(true);
        alterarDestino destino = new alterarDestino();
        destino.alterar_Destino();
    }

    @FXML
    void cadastrar_viagem(ActionEvent event){
        tela_menu.setDisable(true);
        Viagem viagem = new Viagem();
        viagem.cadastro_viagem(stage);
    }

    @FXML
    void alterar_viagem(ActionEvent event){
        tela_menu.setDisable(true);
        alterarViagem viagem = new alterarViagem();
        viagem.alterar_viagem(stage);
    }

    @FXML
    void consultar_viagem(ActionEvent event){
        tela_menu.setDisable(true);
        Consulta consulta = new Consulta();
        consulta.obter_viagens(stage);
    }

    public static void changeScreen(String scr){
        Main main = new Main();
        switch (scr){
            case "main":
                main.tela("/launchs/tela_menu.fxml", "Menu", stage);
                break;
            case "cadastrarPessoa":
                main.tela("/launchs/cadastro_pessoa.fxml", "Cadastro de pessoa", stage);
                break;                
            case "alterarPessoa":
                main.tela("/launchs/alterar_cadastro_pessoa.fxml", "Alterar cadastro de pessoa", stage);
                break;
            case "cadastrarVeiculo":
                main.tela("/launchs/cadastro_veiculo.fxml", "Cadastro de veículo", stage);
                break;
            case "alterarVeiculo":
                main.tela("/launchs/alterar_cadastro_veiculo.fxml", "Alterar cadastro de veiculo", stage);
                break;
            case "cadastrarDestino":
                main.tela("/launchs/cadastro_destino.fxml", "Cadastro de destino", stage);
                break;                
            case "alterarDestino":
                main.tela("/launchs/alterar_cadastro_destino.fxml", "Alterar cadastro de destino", stage);
                break;
        }
    }

    public static void main (String[] args){
        launch();
    }
}
