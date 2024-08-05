package model;

class Estado{
	int valor;
	public Estado(int valor) {
		this.valor = valor;
	}
}

public class Tabuleiro {
	int id;
	private int[][] tabuleiro;
	public Tabuleiro(int id) {
		this.id = id;
		this.tabuleiro = new int[6][7];
		for(int i = 0; i< 6; i++) {
			for(int j = 0; j < 7; j++) {
				tabuleiro[i][j] = 0;
			}
		}
	}
	public int[][] getTabuleiro(){
		return tabuleiro;
	}
	public int getId() {
		return this.id;
	}

	public void setId(int id){
		this.id = id;
	}
	
	public void setTabuleiro(int[][] novo) {
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				tabuleiro[i][j] = novo[i][j];
			}
		}
	}
	
	public int setValor(int valor, int coluna, int jogada) {
		int status = 0;
		int i =5;
		if(coluna <= 6 && coluna >=0) {
	        while(tabuleiro[i][coluna] != 0){
	            i--;
	        }
	        tabuleiro[i][coluna] = valor;
		}
		
		if(jogada > 6) status = temGanhador(i, coluna, tabuleiro[i][coluna]);
		
		return status;
	}
	
	
	 private int temGanhador(int fileira, int coluna, int conteudo){
		 	Estado estado = new Estado(0);
	        if(fileira < 3) vitoriaVertical(fileira, coluna, conteudo, estado);
	        vitoriaHorizontal(fileira, coluna, conteudo, estado);
	        
	        vitoriaDiagonal(fileira, coluna, conteudo, estado);
	        return estado.valor;
	 }
	    
	    private void vitoriaVertical(int fileira, int coluna, int conteudo, Estado estado){
	        int verificador = 0;
	        int aux = fileira;
	        while(aux<=5 && tabuleiro[aux][coluna] == conteudo){
	            verificador++;
	            aux++;
	        } 
	        if(verificador >= 4) estado.valor = conteudo;
	    }
	    
	    private void vitoriaHorizontal(int fileira, int coluna, int conteudo, Estado estado){
	        int aux = coluna;
	        while(aux >= 1 && tabuleiro[fileira][aux - 1] == conteudo){
	            aux--;
	        }

	        int verificador = 0;

	        while (aux <=6 && tabuleiro[fileira][aux] == conteudo) {
	            aux++;
	            verificador++;
	        }
	        if(verificador >= 4){
	            estado.valor = conteudo;
	        }
	    }

	    private void vitoriaDiagonalDescendente(int fileira, int coluna, int conteudo, Estado estado) {
	        while (fileira >= 1 && coluna <= 4 && tabuleiro[fileira - 1][coluna + 1] == conteudo) {
	            fileira--;
	            coluna++;
	        }
	        int verificador = 0;
	        while (fileira <= 5 && coluna >= 0 && tabuleiro[fileira][coluna] == conteudo) {
	            fileira++;
	            coluna--;
	            verificador++;
	        }
	        if (verificador >= 4) estado.valor = conteudo;
	    }
	    
	    private void vitoriaDiagonalAscendente(int fileira, int coluna, int conteudo, Estado estado) {
	        while (fileira >= 1 && coluna >= 1 && tabuleiro[fileira - 1][coluna - 1] == conteudo) {
	            fileira--;
	            coluna--;
	        }
	        int verificador = 0;
	        while (fileira <= 5 && coluna <= 4 && tabuleiro[fileira][coluna] == conteudo) {
	            fileira++;
	            coluna++;
	            verificador++;
	        }
	        if (verificador >= 4) estado.valor = conteudo;
	    }
	        
	    private void vitoriaDiagonal(int fileira, int coluna, int conteudo, Estado estado) {
	        vitoriaDiagonalDescendente(fileira, coluna, conteudo, estado);
	        vitoriaDiagonalAscendente(fileira, coluna, conteudo, estado);
	    }


		public int jogadaIa(int jogador, int jogada) {
			int status = 0;
			int i =5;
			int coluna = Minimax.findBestMove(tabuleiro, 7);

			System.out.println(coluna + "Escolhido pela IA");

			System.out.println("Esteve aqui tambem");

			if(coluna <= 6 && coluna >=0) {
				while(tabuleiro[i][coluna] != 0){
					i--;
				}
				tabuleiro[i][coluna] = jogador;
			}
		
			if(jogada > 6) status = temGanhador(i, coluna, tabuleiro[i][coluna]);
			
			return status;
		}
}
