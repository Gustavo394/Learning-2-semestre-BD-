import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import java.sql.*;

public class alterarDestino {

    @FXML private AnchorPane alterar_destino;

    @FXML private ComboBox<Integer> cep_destino;
    @FXML private TextField endereco_destino;

    private ObservableList<Integer> cepList = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        atualizar();
    }
    
    public void setStage(Stage stage){
        stage.setOnShowing(event -> atualizar());
    }

    public void atualizar(){
        cep_destino.setItems(null);
        cepList.clear();
        
        try{
            Connection conexao = DriverManager.getConnection(Main.caminho, Main.usuario, Main.senha);
            Statement stmt = conexao.createStatement();

            ResultSet cpfResultSet = stmt.executeQuery("SELECT cep FROM destino");{
                while (cpfResultSet.next()){
                    Integer cpf = cpfResultSet.getInt("cep");
                    cepList.add(cpf);
                    
                }    
                cep_destino.setItems(cepList);
                cpfResultSet.close();

            }

            if (cepList.isEmpty()){
                alterar_destino.setDisable(true);
                telaAviso.titulo = "Alerta";
                telaAviso.texto = "Não existem destinos cadastrados!";
                telaAviso telaAviso = new telaAviso();
                telaAviso.aviso();
                alterar_destino.setDisable(false);
            }

            cep_destino.setValue(null);
            endereco_destino.clear();

        }catch(Exception e){
            alterar_destino.setDisable(true);
            telaAviso.titulo = "Erro";
            telaAviso.texto = e.toString();
            telaAviso telaAviso = new telaAviso();
            telaAviso.aviso();
            alterar_destino.setDisable(false);

        }
    }
    @FXML
    void selectec_cep(ActionEvent event){
        if (cep_destino.getValue() != null){
            try(Connection conexao = DriverManager.getConnection(Main.caminho, Main.usuario, Main.senha);
                PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM destino WHERE cep = ?")){
                stmt.setInt(1, cep_destino.getValue());
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    endereco_destino.setText(rs.getString("endereco"));
                }

            }catch(Exception e){
                alterar_destino.setDisable(true);
                telaAviso.titulo = "Erro";
                telaAviso.texto = e.toString();
                telaAviso telaAviso = new telaAviso();
                telaAviso.aviso();
                alterar_destino.setDisable(false);

            }
        }

    }

    @FXML
    void alterar(ActionEvent event){
        try{
            if (cep_destino.getValue() == null || endereco_destino.getText().isEmpty()){
                alterar_destino.setDisable(true);
                telaAviso.titulo = "Alerta";
                telaAviso.texto = "Preencha todos os campos corretamente!";
                telaAviso telaAviso = new telaAviso();
                telaAviso.aviso();
                alterar_destino.setDisable(false);
                
            }else{
                Connection conexao = DriverManager.getConnection(Main.caminho, Main.usuario, Main.senha);
                PreparedStatement cep = conexao.prepareStatement("SELECT cep_destino FROM viagem WHERE cep_destino = ?");
                cep.setInt(1, Integer.valueOf(cep_destino.getValue()));
                ResultSet ceprs = cep.executeQuery();
    
                if (ceprs.next()) {
                    alterar_destino.setDisable(true);
                    telaAviso.titulo = "Alerta";
                    telaAviso.texto = "O cep " + cep_destino.getValue() + " está registrado em uma viagem e não pode ser alterado!";
                    telaAviso telaAviso = new telaAviso();
                    telaAviso.aviso();
                    alterar_destino.setDisable(false);

                } else {
                    PreparedStatement stmt = conexao.prepareStatement("UPDATE destino SET endereco = ? WHERE cep = ?");

                    stmt.setString(1, endereco_destino.getText().toString());
                    stmt.setInt(2, Integer.valueOf(cep_destino.getValue()));
                    stmt.executeUpdate();

                    alterar_destino.setDisable(true);
                    telaAviso.titulo = "Certo";
                    telaAviso.texto = "Registro alterado com sucesso!";
                    telaAviso telaAviso = new telaAviso();
                    telaAviso.aviso();  
                    atualizar();
                alterar_destino.setDisable(false);
                }
            }
        }catch(Exception e){
            alterar_destino.setDisable(true);
             telaAviso.titulo = "Erro";
             telaAviso.texto = e.toString();
             telaAviso telaAviso = new telaAviso();
             telaAviso.aviso();
             alterar_destino.setDisable(false);

        }
    }

    @FXML
    void deletar(ActionEvent event){
        alterar_destino.setDisable(true);

        try{
            if (cep_destino.getValue() == null){
                alterar_destino.setDisable(true);
                telaAviso.titulo = "Alerta";
                telaAviso.texto = "Preencha todos os campos corretamente!";
                telaAviso telaAviso = new telaAviso();
                telaAviso.aviso();
                alterar_destino.setDisable(false);

            }else{
                Connection conexao = DriverManager.getConnection(Main.caminho, Main.usuario, Main.senha);
                PreparedStatement cep = conexao.prepareStatement("SELECT cep_destino FROM viagem WHERE cep_destino = ?");
                cep.setInt(1, Integer.valueOf(cep_destino.getValue()));
                ResultSet ceprs = cep.executeQuery();
    
                if (ceprs.next()) {
                    alterar_destino.setDisable(true);
                    telaAviso.titulo = "Alerta";
                    telaAviso.texto = "O cep " + cep_destino.getValue() + " está registrado em uma viagem e não pode ser deletado!";
                    telaAviso telaAviso = new telaAviso();
                    telaAviso.aviso();
                    alterar_destino.setDisable(false);

                } else {
                    PreparedStatement stmt = conexao.prepareStatement("DELETE FROM destino WHERE cep = ?");
                    stmt.setInt(1, Integer.valueOf(cep_destino.getValue()));
                    stmt.executeUpdate();
        
                    alterar_destino.setDisable(true);
                    telaAviso.titulo = "Certo";
                    telaAviso.texto = "Registro deletado com sucesso!";
                    telaAviso telaAviso = new telaAviso();
                    telaAviso.aviso(); 
                    atualizar();
                    alterar_destino.setDisable(false);

                }
            }
        }catch(Exception e){
            alterar_destino.setDisable(true);
             telaAviso.titulo = "Erro";
             telaAviso.texto = e.toString();
             telaAviso telaAviso = new telaAviso();
             telaAviso.aviso();
             alterar_destino.setDisable(false);

        }
    }

    @FXML
    void voltar(ActionEvent event) {
        Main.changeScreen("main");

    }

    public void alterar_Destino(){
        Main.changeScreen("alterarDestino");

    }
}
