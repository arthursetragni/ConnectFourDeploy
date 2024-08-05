package service;



import dao.*;
import model.*;
import spark.Request;
import spark.Response;
import com.google.gson.Gson;

public class JogoService{
	private JogoDAO jogoDAO;
	
	public JogoService() {
		jogoDAO = new JogoDAO();
	}


	
	public Object iniciar(Request request, Response response) {
		response.header("Access-Control-Allow-Origin", "*");
        response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.header("Access-Control-Allow-Headers", "*");


		TabuleiroService tabuleiro = new TabuleiroService();
		jogoDAO.conectar();
		int id = 0;
		int idJogador1 = 1;
		int idJogador2 = 2;
		int idTabuleiro = tabuleiro.create();
		
		
		Jogo jogo = new Jogo(id, idJogador1, idJogador2, idTabuleiro);
		jogoDAO.add(jogo);
		request.session(true); 
		request.session().attribute("jogo",jogo.getId());
		System.out.println(jogo.getId());
		
		
		jogoDAO.close();

		//response.redirect("tabuleiro.html?id=" + jogo.getId());

		//response.redirect("./index.html");
		Gson gson = new Gson();
		String json = gson.toJson(jogo);

		return json;
	}

	public Object jogada(Request request, Response response) {
		response.header("Access-Control-Allow-Origin", "*");
        response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.header("Access-Control-Allow-Headers", "Content-Type");

		int coluna = Integer.parseInt(request.queryParams("coluna"));

		System.out.println(coluna);
		
		TabuleiroService tabuleiro = new TabuleiroService();
		jogoDAO.conectar();
		Jogo jogo;
		//pega qual a jogada atual pra saber qual o jogador
		request.session(true); 
		//jogo = jogoDAO.getJogo((int)request.session().attribute("jogo"));
		jogo  = jogoDAO.getJogo(Integer.parseInt(request.queryParams("idJogo")));
		
		
		while(!tabuleiro.jogada(jogo, coluna));
		
		jogo.setJogada(jogo.getJogada() + 1);
		
		
		//faz a jogada passando a coluna e a jogada
		//
		/*
		 * Tem que adicionar mais um às jogadas
		 * 
		 * */
		jogoDAO.update(jogo);
		
		jogoDAO.close();
		
		if(jogo.getEstado() != 0) {
			System.out.println("O jogador " + jogo.getEstado() + " é o campeão");
			response.redirect("./fim.html");
			return null;
		}
		
		response.redirect("./tabuleiro.html");
		
		return null;
	}

	public Object jogadaIa(Request request, Response response) {
		response.header("Access-Control-Allow-Origin", "*");
        response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.header("Access-Control-Allow-Headers", "Content-Type");

		System.out.println("JogoService");

		
		TabuleiroService tabuleiro = new TabuleiroService();
		jogoDAO.conectar();
		Jogo jogo;
		//pega qual a jogada atual pra saber qual o jogador
		request.session(true); 
		//jogo = jogoDAO.getJogo((int)request.session().attribute("jogo"));
		jogo  = jogoDAO.getJogo(Integer.parseInt(request.queryParams("idJogo")));
		
		jogoDAO.close();
		
		while(!tabuleiro.jogadaIa(jogo));
		
		jogo.setJogada(jogo.getJogada() + 1);
		
		
		//faz a jogada passando a coluna e a jogada
		//
		/*
		 * Tem que adicionar mais um às jogadas
		 * 
		 * */
		jogoDAO.conectar();
		jogoDAO.update(jogo);

		
		
		jogoDAO.close();
		
		if(jogo.getEstado() != 0) {
			System.out.println("O jogador " + jogo.getEstado() + " é o campeão");
			response.redirect("./fim.html");
			return null;
		}
		
		//response.redirect("./tabuleiro.html");
		
		return null;
	}

	public Object cravaIdJogo(Request request, Response response) {
		try {
			System.out.println(request.queryParams("id"));
			String idParam = request.queryParams("id"); // Use 'queryParams' para obter parâmetros de solicitação POST
			if (idParam != null) {
				int id = Integer.parseInt(idParam);
				System.out.println(id);

				request.session(true);
				request.session().attribute("jogo", id);
				return "ID do jogo salvo com sucesso."; // ou qualquer resposta que deseje enviar ao frontend
			} else {
				response.status(400); // Bad Request se 'id' não estiver presente na solicitação
				return "ID do jogo não encontrado na solicitação.";
			}
		} catch (NumberFormatException e) {
			response.status(400); // Bad Request se 'id' não for um número válido
			return "ID do jogo não é um número válido.";
		}
	}
	
	public Object getEstado(Request request, Response response){
		jogoDAO.conectar();
		request.session(true); 
	    Jogo jogo = jogoDAO.getJogo((int)request.session().attribute("jogo"));
		
		Gson gson = new Gson();
		String json = gson.toJson(jogo.getEstado());
		jogoDAO.close();

		return json;
	}
	
}

