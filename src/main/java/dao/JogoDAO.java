package dao; //Define Pacote

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import java.math.BigInteger;
import java.security.MessageDigest;
import model.Jogo;


public class JogoDAO {

	private Connection conexao;
	public JogoDAO() { 
		conexao = null;
	}
	
	
	//Conectar ao Banco de Dados
	public boolean conectar() {
		String driverName = "org.postgresql.Driver";                    
		String serverName = "localhost";
		String mydatabase = "ligQuatro";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
		String username = "ti2cc";
		String password = "ti@cc";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexão efetuada com o postgres!");
		} catch (ClassNotFoundException e) { 
			System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
		}
		return status;
	}
	
		
	//Fecha Conexão com o Banco de dados
	public boolean close() {
		boolean status = false;
			
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
	
	public boolean add(Jogo jogo) {
		jogo.setId(lerUltimoID());
	    boolean status = false;
	    try {  
	        Statement st = conexao.createStatement();
	        st.executeUpdate("INSERT INTO jogo (id, estado, \"idJogador1\", \"idJogador2\", \"idTabuleiro\", jogada)"
	                        + "VALUES (" + jogo.getId() + ", " + jogo.getEstado() + ", "  
	                        + jogo.getIdJogador1() + ", " + jogo.getIdJogador2() + ", " + jogo.getIdTabuleiro() + ", "
	                        + jogo.getJogada() + ");");
	        st.close();

	        status = true;
	    } catch (SQLException u) {  
	        throw new RuntimeException(u);
	    }
	    return status;
	}
	
	public Jogo getJogo(int id) {
		try {
			Statement st = conexao.createStatement();
			String sql = "SELECT * FROM jogo WHERE id= '" + id + "'";
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			Jogo jogo = new Jogo();
			jogo.setId(rs.getInt("id"));
			jogo.setIdJogador1(rs.getInt("idJogador1"));
			jogo.setIdJogador2(rs.getInt("idJogador2"));
			jogo.setIdTabuleiro(rs.getInt("idTabuleiro"));
			jogo.setEstado(rs.getInt("estado"));
			jogo.setJogada(rs.getInt("jogada"));
			return jogo;
			
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
		
		return null;
	}


	public boolean update(Jogo jogo) {
		boolean status = false;
	    try {  
	        Statement st = conexao.createStatement();

	        StringBuilder query = new StringBuilder("UPDATE jogo SET jogada = '")
	                .append(jogo.getJogada())
	                .append("' WHERE id = ")
	                .append(jogo.getId())
	                .append(";");
	        st.executeUpdate(query.toString());

	        st.close();
	        status = true;
	    } catch (SQLException u) {  
	        throw new RuntimeException(u);
	    }
	    return status;
		
	}

	public int lerUltimoID() {
		int ultimoID = 1;
		try {
			Statement st = conexao.createStatement();
			ResultSet rs = st.executeQuery("SELECT MAX(id) AS ultimo_id FROM jogo;");
			if (rs.next()) {
				ultimoID = rs.getInt("ultimo_id");
			}
			rs.close();
			st.close();
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return ultimoID +1;
	}

}