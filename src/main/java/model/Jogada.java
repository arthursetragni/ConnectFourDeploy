package model;

public class Jogada {
	private int id;
	private int idJogador;
	private int coluna;
	
	public Jogada(int id, int coluna) {
		this.id = id;
		this.idJogador = id % 2;
		this.coluna = coluna;
	}
	public int getColuna() {
		return this.coluna;
	}
	
	public int getidJogador() {
		return this.idJogador;
	}
	
	public int getId() {
		return id;
	}
}
