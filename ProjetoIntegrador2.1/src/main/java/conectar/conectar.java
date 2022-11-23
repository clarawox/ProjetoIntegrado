package conectar;

import java.sql.*;

public class conectar {

    private static Connection conexao_MySql = null;
    private static String localBD = "localhost";
    private static String LINK = "jdbc:mysql://" + localBD + ":3306/ds";
    private static final String usuario = "root";
    private static final String senha = "Senai123";

    // Método para fazer a conexão com um banco de dados MySql
    public Connection connectionMySql() {

        try {
            conexao_MySql = DriverManager.getConnection(LINK, usuario, senha);
            System.out.println("conexão OK!");
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um problema na conexão com o BD", e);
        }
        return conexao_MySql;
    }

    public void InsereCliente(Connection con, String nome, String cpf, String cel, String end, String email, String senha) {
        try {
            String sql = "INSERT INTO clientes (nome, email, endereco, senha) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, nome);
            pstm.setString(2, cpf);
            pstm.setString(3, end);
            pstm.setString(4, cel);
            pstm.setString(5, email);
            pstm.setString(6, senha);
            pstm.execute();
        } catch (Exception e) {
            System.out.println("Erro ao inserir " + e);
        }
    }

    public void InsereClientes(Connection con, String nome, String email, String end, String senha) {
        try {
            String sql = "INSERT INTO clientes (nome, email, senha, endereco) VALUES (?, ?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, nome);
            pstm.setString(2, email);
            pstm.setString(3, senha);
            pstm.setString(4, end);
            pstm.execute();
        } catch (Exception e) {
            System.out.println("Erro ao inserir " + e);
        }
    }

    public boolean logar(Connection con, String email, String senha) {
        try {
            String sql = "select * from cliente where email=? and senha=?";
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, email);
            pstm.setString(2, senha);
            return true;
        } catch (Exception e) {
            System.out.println("O erro é: " + e);
            return false;
        }
    }

    public static String pegarNome(Connection con, String email) {
        String nome = "";
        String sql = "Select nome from clientes where email = ?";
        PreparedStatement preparedStmt;
        
        try {
           PreparedStatement pstm = con.prepareStatement(sql);
           pstm.setString(1, email);
           ResultSet result = pstm.executeQuery();
            
           while(result.next()){
               nome = result.getString("nome");
               System.out.println(nome);
           }
        } catch (Exception e) {
            System.out.println("O erro é: " + e);
        }
        return nome;
    }

    public void closeConnectionMySql(Connection con) {
        try {
            if (con != null) {
                con.close();
                System.out.println("Fechamento OK");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um problema para encerrar a conexão com o BD.", e);
        }
    }

}
