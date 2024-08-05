package model;

public class Jogo {
    private int id;
    private int estado;
    private int idJogador1;
    private int idJogador2;
    private int idTabuleiro;
    private int jogada;

    public Jogo(int id, int idJogador1, int idJogador2, int idTabuleiro) {
        this.id = id;
        this.estado = 0;
        this.idJogador1 = idJogador1;
        this.idJogador2 = idJogador2;
        this.idTabuleiro = idTabuleiro;
        this.jogada = 1;
    }
    
    public Jogo() {
    	this.id = 0;
        this.estado = 0;
        this.idJogador1 = 0;
        this.idJogador2 = 0;
        this.idTabuleiro = 0;
        this.jogada = 0;
    }

    // Métodos Get
    public int getId() {
        return id;
    }

    public int getEstado() {
        return estado;
    }

    public int getIdJogador1() {
        return idJogador1;
    }

    public int getIdJogador2() {
        return idJogador2;
    }

    public int getIdTabuleiro() {
        return idTabuleiro;
    }

    public int getJogada() {
        return jogada;
    }

    // Métodos Set
    public void setId(int id) {
        this.id = id;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void setIdJogador1(int idJogador1) {
        this.idJogador1 = idJogador1;
    }

    public void setIdJogador2(int idJogador2) {
        this.idJogador2 = idJogador2;
    }

    public void setIdTabuleiro(int idTabuleiro) {
        this.idTabuleiro = idTabuleiro;
    }

    public void setJogada(int jogada) {
        this.jogada = jogada;
    }
}
