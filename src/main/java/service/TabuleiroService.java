package service;

import dao.*;
import model.*;
import spark.*;
import java.io.*;

public class TabuleiroService{
	private TabuleiroDAO tabuleiroDAO;
	
	public TabuleiroService() {
		tabuleiroDAO = new TabuleiroDAO();
	}
	
	public int create() {
		Tabuleiro tabuleiro = new Tabuleiro(0);
		
		//this.tabuleiroDAO = new TabuleiroDAO();
		
		tabuleiroDAO.connect();
		
		tabuleiroDAO.add(tabuleiro);
		
		tabuleiroDAO.close();
		
		return tabuleiro.getId();
	}
	
	/*public Tabuleiro get() {
		Tabuleiro tabuleiro = tabuleiroDAO.get();
		return tabuleiro;
	}*/
	
	
	//entrada do usuario ainda nao foi validada
	public boolean jogada(Jogo jogo,int coluna) {
		
		tabuleiroDAO.connect();
		
		int jogador = (jogo.getJogada() % 2) + 1;		
		
		Tabuleiro tabuleiro = tabuleiroDAO.get(jogo.getIdTabuleiro());
		
		//A propria função de inserir uma jogada verifica se tem ganhador e retorna o estado do jogo
		//0 = jogo ativo
		//1 = vitoria jogador 1
		//2 = vitoria jogador 2
		jogo.setEstado(tabuleiro.setValor(jogador, (coluna - 1), jogo.getJogada()));  
		
		tabuleiroDAO.update(jogo.getIdTabuleiro(), tabuleiro);
		
		tabuleiroDAO.close();
		
		return true;
	}

	public String getTabuleiro(Request request,Response response){

		response.header("Access-Control-Allow-Origin", "*");
        response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.header("Access-Control-Allow-Headers", "Content-Type");

        
		tabuleiroDAO.connect();

		Tabuleiro tabuleiro = tabuleiroDAO.get(Integer.parseInt(request.params("id")));

		tabuleiroDAO.close();

		return converterMatrizParaJSON(tabuleiro.getTabuleiro());
	}

	public static String converterMatrizParaJSON(int[][] matriz) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("["); // Início do array JSON externo

        for (int i = 0; i < matriz.length; i++) {
            jsonBuilder.append("["); // Início do array JSON interno

            for (int j = 0; j < matriz[i].length; j++) {
                jsonBuilder.append(matriz[i][j]);

                // Adiciona vírgula entre os elementos, exceto para o último elemento
                if (j < matriz[i].length - 1) {
                    jsonBuilder.append(",");
                }
            }

            jsonBuilder.append("]");

            // Adiciona vírgula entre os arrays internos, exceto para o último array
            if (i < matriz.length - 1) {
                jsonBuilder.append(",");
            }
        }

        jsonBuilder.append("]"); // Fim do array JSON externo

        return jsonBuilder.toString();
    }

    public boolean jogadaIa(Jogo jogo) {
		tabuleiroDAO.connect();
		System.out.println("TabuleiroService");
		int jogador = (jogo.getJogada() % 2) + 1;		
		
		Tabuleiro tabuleiro = tabuleiroDAO.get(jogo.getIdTabuleiro());
		
		//A propria função de inserir uma jogada verifica se tem ganhador e retorna o estado do jogo
		//0 = jogo ativo
		//1 = vitoria jogador 1
		//2 = vitoria jogador 2
		jogo.setEstado(tabuleiro.jogadaIa(jogador , jogo.getJogada()));  
		
		tabuleiroDAO.update(jogo.getIdTabuleiro(), tabuleiro);
		
		tabuleiroDAO.close();
		
		return true;
	}
	
}