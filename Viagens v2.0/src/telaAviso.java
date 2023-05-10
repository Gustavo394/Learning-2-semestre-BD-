import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class telaAviso {

    @FXML private ImageView imagemAviso;
    @FXML private Text textoAviso;

    static String titulo = null;
    static String texto = null;
    
    private Stage tela_aviso = new Stage();


    public void setStage(Stage stage) {
        this.tela_aviso = stage;
    }

    @FXML
    public void initialize(){
        textoAviso.setText(texto);
        Image simbolo = new Image(getClass().getResourceAsStream("/launchs/" + telaAviso.titulo + ".png"));
        imagemAviso.setImage(simbolo);
    }

    public void aviso(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/launchs/tela_aviso.fxml"));
            Parent root = loader.load();
            telaAviso controlador = loader.getController();
            controlador.setStage(tela_aviso);
            Scene scene = new Scene(root);
            
            Image icone = new Image(getClass().getResourceAsStream("/launchs/icone.png"));
            tela_aviso.getIcons().add(icone);

            tela_aviso.sizeToScene();
            tela_aviso.setTitle(titulo);
            
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            
            double x = (screenBounds.getWidth() - root.prefWidth(-1)) / 2;
            double y = (screenBounds.getHeight() - root.prefHeight(-1)) / 2;
            tela_aviso.setX(x);
            tela_aviso.setY(y);
    
            tela_aviso.setResizable(false);
            tela_aviso.setScene(scene);
            tela_aviso.showAndWait(); // Esperar o usuário fechar a janela para retornar ao código

        }catch(IOException e){
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

    @FXML
    private void ok(ActionEvent event){
        tela_aviso.close();

    }
}