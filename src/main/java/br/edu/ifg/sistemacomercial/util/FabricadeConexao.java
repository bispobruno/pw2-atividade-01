package br.edu.ifg.sistemacomercial.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FabricadeConexao {

    private static Connection conexao;
    private static final String URL = "jdbc:postgresql://localhost/atv1pw2";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "95134229";
    
    
    public static Connection getConexao() throws SQLException{
        if(conexao == null){
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FabricadeConexao.class.getName()).log(Level.SEVERE, null, ex);
            }
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
        }
        return conexao;
    }
    
    public static void fecharConexao() throws SQLException{
        if(conexao != null){
            conexao.close();
        } 
        conexao = null;
    }
    
    
}