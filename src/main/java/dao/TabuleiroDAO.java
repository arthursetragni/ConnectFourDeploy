package dao; //Define Pacote

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import java.math.BigInteger;
import java.security.MessageDigest;
import model.Jogo;
import model.Tabuleiro;


public class TabuleiroDAO {

	private Connection conexao;
	public TabuleiroDAO() { 
		conexao = null;
	}
	
	
	//Conectar ao Banco de Dados
	public boolean connect() {
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
	
	public boolean add(Tabuleiro tabuleiro) {
	    boolean status = false;
	    try {  
			tabuleiro.setId(lerUltimoID());
	        Statement st = conexao.createStatement();
	        // Asumindo que 'tabuleiro' é uma matriz de inteiros
	        StringBuilder query = new StringBuilder("INSERT INTO tabuleiro (id, tabuleiro) VALUES (")
	            .append(tabuleiro.getId()).append(", '").append(arrayToString(tabuleiro.getTabuleiro())).append("');");
	        st.executeUpdate(query.toString());
	        st.close();
	        status = true;
			
	    } catch (SQLException u) {  
	        throw new RuntimeException(u);
	    }
	    return status;
	}
	
	public boolean update(int id, Tabuleiro tabuleiro) {
	    boolean status = false;
	    try {  
	        Statement st = conexao.createStatement();

	        StringBuilder query = new StringBuilder("UPDATE tabuleiro SET tabuleiro = '")
	                .append(arrayToString(tabuleiro.getTabuleiro()))
	                .append("' WHERE id = ")
	                .append(id)
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
			ResultSet rs = st.executeQuery("SELECT MAX(id) AS ultimo_id FROM tabuleiro;");
			if (rs.next()) {
				ultimoID = rs.getInt("ultimo_id");
			}
			rs.close();
			st.close();
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return ultimoID +1 ;
	}
	

	// Método auxiliar para converter a matriz em uma string formatada
	private String arrayToString(int[][] array) {
	    StringBuilder result = new StringBuilder("{");
	    for (int i = 0; i < array.length; i++) {
	        result.append("{");
	        for (int j = 0; j < array[i].length; j++) {
	            result.append(array[i][j]);
	            if (j < array[i].length - 1) {
	                result.append(",");
	            }
	        }
	        result.append("}");
	        if (i < array.length - 1) {
	            result.append(",");
	        }
	    }
	    result.append("}");
	    return result.toString();
	}
	
	public Tabuleiro get(int id) {
        Tabuleiro tabuleiro = new Tabuleiro(id);

        try {
            String query = "SELECT tabuleiro FROM tabuleiro WHERE id = ?";
            PreparedStatement ps = conexao.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String tabuleiroString = rs.getString("tabuleiro");
                int[][] tabuleiroArray = stringToArray(tabuleiroString);
                tabuleiro.setTabuleiro(tabuleiroArray);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tabuleiro;
    }

	private int[][] stringToArray(String str) {
	    // Remover chaves externas
	    str = str.substring(2, str.length() - 2);
	    // Dividir em linhas
	    String[] rows = str.split("\\},\\{");
	    
	    int numRows = rows.length;
	    int numCols = rows[0].split(",").length;

	    int[][] array = new int[numRows][numCols];
	    
	    for (int i = 0; i < numRows; i++) {
	        // Dividir em colunas
	        String[] cols = rows[i].split(",");
	        
	        for (int j = 0; j < numCols; j++) {
	            array[i][j] = Integer.parseInt(cols[j]);
	        }
	    }
	    
	    return array;
	}


}