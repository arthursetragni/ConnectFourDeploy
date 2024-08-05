package app;


//import model.Denuncia;

import static spark.Spark.*;


import service.*;

//import service.DenunciaService;


public class aplicacao {
	
	private static JogoService jogoService = new JogoService();
	private static TabuleiroService tabuleiroService = new TabuleiroService();
    public static void main(String[] args) {

        port(6798);
        

        
        
        
        staticFiles.location("/Ligue4FrontEndo");

        
        get("/comecar", (request, response) -> jogoService.iniciar(request, response));
        get("/estado", (request, response) -> jogoService.getEstado(request, response));
        post("/cravaId", (request, response) -> jogoService.cravaIdJogo(request, response));	
        post("/jogada", (request, response) -> jogoService.jogada(request, response));
        get("/tabuleiro/:id", (request, response) -> tabuleiroService.getTabuleiro(request, response));
        
        post("/jogadaIa", (request, response) -> jogoService.jogadaIa(request, response));


 /*
         *
        
        get("/produto/update/:id", (request, response) -> tarefaService.update(request, response));
        get("/produto/delete/:id", (request, response) -> denunciaService.delete(request, response));
        get("/produto", (request, response) -> denunciaService.getAll(request, response));
        */             
    }
}