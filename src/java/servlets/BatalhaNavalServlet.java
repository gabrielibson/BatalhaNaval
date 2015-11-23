/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jogo.BatalhaNaval;
import jogo.Embarcacao;
import jogo.Jogador;
import jogo.Tabuleiro;
import org.json.JSONObject;

/**
 *
 * @author pbeat_000
 */
public class BatalhaNavalServlet extends HttpServlet {

    private static int codigoJogador = 0;
    private BatalhaNaval batalhaNaval = null;
    JSONObject json = null;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet BatalhaNavalServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BatalhaNavalServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if(batalhaNaval != null){
            if(batalhaNaval.isTemSegundoJogador()){
                this.batalhaNaval = this.inicializarJogo();
                this.batalhaNaval.setTemSegundoJogador(false);
                json = new JSONObject(batalhaNaval);
                response.getWriter().write(json.toString());
            }else{
                batalhaNaval.setTemSegundoJogador(true);
                json = new JSONObject(batalhaNaval);
                response.getWriter().write(json.toString());
            }
        }else{
            this.batalhaNaval = this.inicializarJogo();  
            this.batalhaNaval.setTemSegundoJogador(false);
            json = new JSONObject(batalhaNaval);
            response.getWriter().write(json.toString());
        }
    }
    
    public BatalhaNaval inicializarJogo(){
        Tabuleiro tabuleiro1 = new Tabuleiro(10,10);
        Tabuleiro tabuleiro2 = new Tabuleiro(10,10);
        Embarcacao portaAviao = new Embarcacao(4);
        Embarcacao encouracado1 = new Embarcacao(3);
        Embarcacao encouracado2 = new Embarcacao(3);
        Embarcacao cruzador1 = new Embarcacao(2);
        Embarcacao cruzador2 = new Embarcacao(2);
        Embarcacao cruzador3 = new Embarcacao(2);
        Embarcacao submarino1 = new Embarcacao(1);
        Embarcacao submarino2 = new Embarcacao(1);
        Embarcacao submarino3 = new Embarcacao(1);
        Embarcacao submarino4 = new Embarcacao(1);
        
        Jogador jogador1 = new Jogador(tabuleiro1);
        jogador1.setCodigo(++codigoJogador);
        jogador1.setNome("jogador"+codigoJogador);
        
        Jogador jogador2 = new Jogador(tabuleiro2);
        jogador2.setCodigo(++codigoJogador);
        jogador2.setNome("jogador"+codigoJogador);
                
        batalhaNaval = new BatalhaNaval(jogador1, jogador2);
        batalhaNaval.getJogador1().getTabuleiro().adicionarEmbarcacaoAleatoria(portaAviao);
        batalhaNaval.getJogador1().getTabuleiro().adicionarEmbarcacaoAleatoria(encouracado1);
        batalhaNaval.getJogador1().getTabuleiro().adicionarEmbarcacaoAleatoria(encouracado2);
        batalhaNaval.getJogador1().getTabuleiro().adicionarEmbarcacaoAleatoria(cruzador1);
        batalhaNaval.getJogador1().getTabuleiro().adicionarEmbarcacaoAleatoria(cruzador2);
        batalhaNaval.getJogador1().getTabuleiro().adicionarEmbarcacaoAleatoria(cruzador3);
        batalhaNaval.getJogador1().getTabuleiro().adicionarEmbarcacaoAleatoria(submarino1);
        batalhaNaval.getJogador1().getTabuleiro().adicionarEmbarcacaoAleatoria(submarino2);
        batalhaNaval.getJogador1().getTabuleiro().adicionarEmbarcacaoAleatoria(submarino3);
        batalhaNaval.getJogador1().getTabuleiro().adicionarEmbarcacaoAleatoria(submarino4);
        
        portaAviao = new Embarcacao(4);
        encouracado1 = new Embarcacao(3);
        encouracado2 = new Embarcacao(3);
        cruzador1 = new Embarcacao(2);
        cruzador2 = new Embarcacao(2);
        cruzador3 = new Embarcacao(2);
        submarino1 = new Embarcacao(1);
        submarino2 = new Embarcacao(1);
        submarino3 = new Embarcacao(1);
        submarino4 = new Embarcacao(1);
        
        batalhaNaval.getJogador2().getTabuleiro().adicionarEmbarcacaoAleatoria(portaAviao);
        batalhaNaval.getJogador2().getTabuleiro().adicionarEmbarcacaoAleatoria(encouracado1);
        batalhaNaval.getJogador2().getTabuleiro().adicionarEmbarcacaoAleatoria(encouracado2);
        batalhaNaval.getJogador2().getTabuleiro().adicionarEmbarcacaoAleatoria(cruzador1);
        batalhaNaval.getJogador2().getTabuleiro().adicionarEmbarcacaoAleatoria(cruzador2);
        batalhaNaval.getJogador2().getTabuleiro().adicionarEmbarcacaoAleatoria(cruzador3);
        batalhaNaval.getJogador2().getTabuleiro().adicionarEmbarcacaoAleatoria(submarino1);
        batalhaNaval.getJogador2().getTabuleiro().adicionarEmbarcacaoAleatoria(submarino2);
        batalhaNaval.getJogador2().getTabuleiro().adicionarEmbarcacaoAleatoria(submarino3);
        batalhaNaval.getJogador2().getTabuleiro().adicionarEmbarcacaoAleatoria(submarino4);
        
        batalhaNaval.getJogador1().setTabuleiroContra(batalhaNaval.getJogador2().getTabuleiro());
        batalhaNaval.getJogador2().setTabuleiroContra(batalhaNaval.getJogador1().getTabuleiro());
        
        return batalhaNaval;
    }
    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
