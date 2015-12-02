/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jogo.BatalhaNaval;
import jogo.Embarcacao;
import jogo.Jogador;
import jogo.Mesa;
import jogo.Tabuleiro;
import org.json.JSONArray;
import org.json.JSONObject;
import util.Perfil;

/**
 *
 * @author pbeat_000
 */
public class BatalhaNavalServlet extends HttpServlet {

    private static int codigoJogador = 0;
    private BatalhaNaval batalhaNaval = null;
    JSONObject json = null;
    private static final List<Mesa> listaMesas = new ArrayList<>();
    
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
        String acao = request.getParameter("acao");
        if (acao.equals("iniciarBatalha")) {
            String perfil = request.getParameter("perfil");
            if (perfil.equals(Perfil.JOGADOR)) {
                getContextoJogador(request, response);
            } else {
                getContextoVisualizador(request, response);
            }
        }
        //registrarTiroDisparado
        else if(acao.equals("registerTiro")){
            registrarTiroDisparado(request);
        }
        //listarMesas
        else{
            String saida = "<select id='mesa'>";
            saida+= "<option value='0'>Selecione</option>";
            if (listaMesas.size() > 0) {
                List<Mesa> listaAux = new ArrayList<>();
                listaAux.addAll(listaMesas);
                Collections.sort(listaAux);
                for (int i = 1; i <= 12; i++) {
                    Mesa mesa = null;
                    if(!listaAux.isEmpty()){
                        mesa = listaAux.get(0);
                    }                    
                    if(mesa != null && mesa.getCodigo() == i){
                        String op1 = "<option value='"+i+"'>Mesa "+i+"&nbsp&nbsp&nbsp&nbsp 1 Jogador</option>";
                        String op2 = "<option value='"+i+"'>Mesa "+i+"&nbsp&nbsp&nbsp&nbsp 2 Jogadores</option>";
                        saida += mesa.getBatalhaNaval().isTemSegundoJogador()?op2:op1;
                        listaAux.remove(0);
                    }else{
                        saida+= "<option value='"+i+"'>Mesa "+i+"&nbsp&nbsp&nbsp&nbsp 0 Jogadores</option>";
                    }
                }
            }else{
                for(int i = 1; i <= 12; i++){
                    saida+= "<option value='"+i+"'>Mesa "+i+"&nbsp&nbsp&nbsp&nbsp 0 Jogadores</option>";
                }
            }
            saida+= "</select>";
            
            response.getWriter().write(saida);
        }
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
        String codMesa = request.getParameter("mesa");
        int codigo = Integer.parseInt(codMesa);
        
        for (Mesa mesa : listaMesas) {           
            if(mesa != null){
                if(mesa.getCodigo() == codigo){
                    listaMesas.remove(mesa);
                    break;
                }
            }
        }
    }
    
    private void getContextoJogador(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
        String codMesa = request.getParameter("mesa");
        String nomeMesa = "mesa"+codMesa;
        String nomeJogador2 = request.getParameter("nickname");
        int codigo = Integer.parseInt(codMesa);
        Mesa mesa = null;
        
        if(batalhaNaval != null){
            for(Mesa m : listaMesas){
                if(m.getCodigo() == codigo){
                    mesa = m;
                    break;
                }
            }
            if(mesa != null){
                if(!mesa.getBatalhaNaval().isTemSegundoJogador()){
                    mesa.getBatalhaNaval().setTemSegundoJogador(true);
                    mesa.getBatalhaNaval().getJogador2().setNome(nomeJogador2);
                    json = new JSONObject(mesa.getBatalhaNaval());
                    response.getWriter().write(json.toString());
                }else{
                    response.getWriter().write("");
                }
            }else{
                this.batalhaNaval = this.inicializarJogo(request);  
                this.batalhaNaval.setTemSegundoJogador(false);
                mesa = new Mesa(nomeMesa, codigo, batalhaNaval);
                listaMesas.add(mesa);
                json = new JSONObject(batalhaNaval);
                response.getWriter().write(json.toString());
            }
        }else{
            this.batalhaNaval = this.inicializarJogo(request);  
            this.batalhaNaval.setTemSegundoJogador(false);
            mesa = new Mesa(nomeMesa, codigo, batalhaNaval);
            listaMesas.add(mesa);
            json = new JSONObject(batalhaNaval);
            response.getWriter().write(json.toString());
        }
    }
    
    private void registrarTiroDisparado(HttpServletRequest request) {

        int codigoMesa = Integer.parseInt(request.getParameter("mesa"));
        BatalhaNaval batalha = null;
        for (Mesa mesa : listaMesas) {
            if (mesa.getCodigo() == codigoMesa) {
                batalha = mesa.getBatalhaNaval();
                break;
            }
        }
        int x = Integer.parseInt(request.getParameter("x"));
        int y = Integer.parseInt(request.getParameter("y"));
        if (request.getParameter("nomeJogador").
                equals(batalha.getJogador1().getNome())) {
            TiroDisparado tiro = new TiroDisparado(x, y);
            batalha.getJogador1().getListTirosDisparados().add(tiro);
        } else {
            TiroDisparado tiro = new TiroDisparado(x, y);
            batalha.getJogador2().getListTirosDisparados().add(tiro);
        }
    }
    
    private void getContextoVisualizador(HttpServletRequest request, HttpServletResponse response)
        throws IOException{
        String codMesa = request.getParameter("mesa");
        int codigo = Integer.parseInt(codMesa);
        Mesa mesa = null;
        for(Mesa m : listaMesas){
            if(m.getCodigo() == codigo){
                mesa = m;
            }
        }
        if(mesa != null){
            json = new JSONObject(mesa.getBatalhaNaval());
            response.getWriter().write(json.toString());
        }else{
            response.getWriter().write("");
        }
    }
    
    public BatalhaNaval inicializarJogo(HttpServletRequest request){
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
        jogador1.setNome(request.getParameter("nickname"));
        
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
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
