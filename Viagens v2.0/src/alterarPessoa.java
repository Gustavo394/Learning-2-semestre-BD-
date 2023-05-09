import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.collections.FXCollections;
import javafx.scene.control.DatePicker;
import javafx.collections.ObservableList;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import java.sql.*;

public class alterarPessoa{

    @FXML private AnchorPane alterar_pessoa;

    @FXML private ComboBox<String> cpf_pessoa;
    @FXML private TextField nome_pessoa;
    @FXML private DatePicker nascimento_pessoa;
    @FXML private RadioButton masculino;
    @FXML private RadioButton feminino;
    @FXML private RadioButton outro;

    String sexo;
    
    private ObservableList<String> cpfList = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        nome_pessoa.setTextFormatter(new TextFormatter<>(change -> {
            if (!change.getText().matches("[0-9]")) {
                return change;
            }
            return null;
        }));
        atualizar();
    }
    
    public void setStage(Stage stage){
        stage.setOnShowing(event -> atualizar());
    }

    public void atualizar(){
        cpf_pessoa.setItems(null);
        cpfList.clear();
        
        try{
            Connection conexao = DriverManager.getConnection(Main.caminho, Main.usuario, Main.senha);
            Statement stmt = conexao.createStatement();

            ResultSet cpfResultSet = stmt.executeQuery("SELECT cpf FROM pessoa");{
                while (cpfResultSet.next()){
                    String cpf = cpfResultSet.getString("cpf");
                    cpfList.add(cpf);
                }

                cpf_pessoa.setItems(cpfList);
                cpfResultSet.close();
            }

            if (cpfList.isEmpty()){
                alterar_pessoa.setDisable(true);
                telaAviso.titulo = "Alerta";
                telaAviso.texto = "Não existem pessoas cadastradas!";
                telaAviso telaAviso = new telaAviso();
                telaAviso.aviso();
                alterar_pessoa.setDisable(false);
            }

            nome_pessoa.clear();
            nascimento_pessoa.setValue(null);
            masculino.setSelected(false);
            feminino.setSelected(false);
            
        }catch(Exception e){
            alterar_pessoa.setDisable(true);
            telaAviso.titulo = "Erro";
            telaAviso.texto = e.toString();
            telaAviso telaAviso = new telaAviso();
            telaAviso.aviso();
            alterar_pessoa.setDisable(false);

        }
    }
    @FXML
    void cpfSelecionado(ActionEvent event){
        if (cpf_pessoa.getValue() != null){
            try(Connection conexao = DriverManager.getConnection(Main.caminho, Main.usuario, Main.senha);
                PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM pessoa WHERE cpf = ?")){
                stmt.setString(1, cpf_pessoa.getValue());
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    nome_pessoa.setText(rs.getString("nome"));
                    nascimento_pessoa.setValue(rs.getDate("nascimento").toLocalDate());
                    if (rs.getString("sexo").equals("Homem")){
                        masculino.setSelected(true);
                        feminino.setSelected(false);
                        outro.setSelected(false);

                    }if(rs.getString("sexo").equals("Mulher")){
                        masculino.setSelected(false);
                        feminino.setSelected(true);
                        outro.setSelected(false);

                    }else if(rs.getString("sexo").equals("Outro")){
                        masculino.setSelected(false);
                        feminino.setSelected(false);
                        outro.setSelected(true);

                    }
                }

            }catch(Exception e){
                alterar_pessoa.setDisable(true);
                telaAviso.titulo = "Erro";
                telaAviso.texto = e.toString();
                telaAviso telaAviso = new telaAviso();
                telaAviso.aviso();
                alterar_pessoa.setDisable(false);

            }
        }
    }

    @FXML
    void masculinoSelecionado(ActionEvent event){
        if(masculino.isSelected()){
            sexo = "Homem";
            feminino.setSelected(false);
            outro.setSelected(false);
        }

    }
    
    @FXML
    void femininoSelecionado(ActionEvent event){
        if(feminino.isSelected()){
            sexo = "Mulher";
            masculino.setSelected(false);
            outro.setSelected(false);
        }

    }
    
    @FXML
    void outroSelecionado(ActionEvent event){
        if(outro.isSelected()){
            sexo = "Outro";
            masculino.setSelected(false);
            feminino.setSelected(false);
        }

    }

    @FXML
    void alterar(ActionEvent event){
        
        if(masculino.isSelected()){
            sexo = "Homem";
        } else if (feminino.isSelected()){
            sexo = "Mulher";
        } else if (outro.isSelected()){
            sexo = "Outro";
        }

        try{
            if (cpf_pessoa.getValue() == null || nome_pessoa.getText().isEmpty() || nascimento_pessoa.getValue() == null ||
            (!masculino.isSelected() && !feminino.isSelected() && !outro.isSelected())){
                alterar_pessoa.setDisable(true);
                telaAviso.titulo = "Alerta";
                telaAviso.texto = "Preencha todos os campos corretamente!";
                telaAviso telaAviso = new telaAviso();
                telaAviso.aviso();
                alterar_pessoa.setDisable(false);

            }else{
                Connection conexao = DriverManager.getConnection(Main.caminho, Main.usuario, Main.senha);
                PreparedStatement cpf = conexao.prepareStatement("SELECT cpf_viajante FROM viagem WHERE cpf_viajante = ?");
                cpf.setString(1, cpf_pessoa.getValue());
                ResultSet cpfrs = cpf.executeQuery();
    
                if (cpfrs.next()) {
                    alterar_pessoa.setDisable(true);
                    telaAviso.titulo = "Alerta";
                    telaAviso.texto = "O CPF " + cpf_pessoa.getValue() + " está registrado em uma viagem e não pode ser alterado!";
                    telaAviso telaAviso = new telaAviso();
                    telaAviso.aviso();
                    alterar_pessoa.setDisable(false);
                } else {
                    PreparedStatement stmt = conexao.prepareStatement("UPDATE pessoa SET nome = ?, nascimento = ?, sexo = ? WHERE cpf = ?");
    
                    stmt.setString(1, nome_pessoa.getText().toString());
                    stmt.setDate(2, Date.valueOf(nascimento_pessoa.getValue()));
                    stmt.setString(3, sexo);
                    stmt.setString(4, cpf_pessoa.getValue());
                    stmt.executeUpdate();
    
                    alterar_pessoa.setDisable(true);
                    telaAviso.titulo = "Certo";
                    telaAviso.texto = "Registro alterado com sucesso!";
                    telaAviso telaAviso = new telaAviso();
                    telaAviso.aviso();
                    alterar_pessoa.setDisable(false);
                    atualizar();
                }
            }

        }catch(Exception e){
            alterar_pessoa.setDisable(true);
             telaAviso.titulo = "Erro";
             telaAviso.texto = e.toString();
             telaAviso telaAviso = new telaAviso();
             telaAviso.aviso();
             alterar_pessoa.setDisable(false);

        }
    }

    @FXML
    void deletar(ActionEvent event){
        try{
            if (cpf_pessoa.getValue() == null){
                alterar_pessoa.setDisable(true);
                telaAviso.titulo = "Alerta";
                telaAviso.texto = "Preencha todos os campos corretamente!";
                telaAviso telaAviso = new telaAviso();
                telaAviso.aviso();
                alterar_pessoa.setDisable(false);

            }else{
                Connection conexao = DriverManager.getConnection(Main.caminho, Main.usuario, Main.senha);
                PreparedStatement cpf = conexao.prepareStatement("SELECT cpf_viajante FROM viagem WHERE cpf_viajante = ?");
                cpf.setString(1, cpf_pessoa.getValue());
                ResultSet cpfrs = cpf.executeQuery();
    
                if (cpfrs.next()) {
                    alterar_pessoa.setDisable(true);
                    telaAviso.titulo = "Alerta";
                    telaAviso.texto = "O CPF " + cpf_pessoa.getValue() + " está registrado em uma viagem e não pode deletado!";
                    telaAviso telaAviso = new telaAviso();
                    telaAviso.aviso();
                    alterar_pessoa.setDisable(false);
                } else {
                    PreparedStatement stmt = conexao.prepareStatement("DELETE FROM pessoa WHERE cpf = ?");
                    stmt.setString(1, cpf_pessoa.getValue());
                    stmt.executeUpdate();
    
                    alterar_pessoa.setDisable(true);
                    telaAviso.titulo = "Certo";
                    telaAviso.texto = "Registro deletado com sucesso!";
                    telaAviso telaAviso = new telaAviso();
                    telaAviso.aviso(); 
                    atualizar();
                    alterar_pessoa.setDisable(false);
                }                
            }

        }catch(Exception e){
            alterar_pessoa.setDisable(true);
             telaAviso.titulo = "Erro";
             telaAviso.texto = e.toString();
             telaAviso telaAviso = new telaAviso();
             telaAviso.aviso();
             alterar_pessoa.setDisable(false);

        }
    }

    @FXML
    void voltar(ActionEvent event){
        Main.changeScreen("main");

    }

    public void alterar_Pessoa(){
        Main.changeScreen("alterarPessoa");

    }
}
