/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import servlets.TiroDisparado;
import servlets.TiroDisparadoDecoder;
import servlets.TiroDisparadoEncoder;
import util.Perfil;


@ServerEndpoint(value = "/batalhanavalendpoint/{mesa}/{perfil}/{nickname}", 
        encoders = TiroDisparadoEncoder.class, decoders = TiroDisparadoDecoder.class)
public class WebsocketBatalhaNaval {
    private static int i = 0;
    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
  
    @OnOpen
    public void onOpen(final Session peer, @PathParam("mesa") final String mesa, 
            @PathParam("perfil") final String perfil, @PathParam("nickname") String nickname) {
        peer.getUserProperties().put("mesa", mesa);
        if (perfil.equals(Perfil.JOGADOR)) {
            i++;
            peer.getUserProperties().put("perfil", Perfil.JOGADOR + i);
        } else {
            peer.getUserProperties().put("perfil", Perfil.VISUALIZADOR);
        }
        peers.add(peer);
        for (Session s : peers) {
            if (s.isOpen() && mesa.equals(s.getUserProperties().get("mesa"))
                    && !s.getId().equals(peer.getId()) && 
                        perfil.equals(Perfil.JOGADOR)) {
                try {
                    s.getBasicRemote().sendText("2_"+nickname);
                } catch (IOException ex) {
                    Logger.getLogger(WebsocketBatalhaNaval.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     *
     * @param session
     * @param tiro
     */
    @OnMessage
    public void onMessage(final Session session, final TiroDisparado tiro) {
        String mesa = (String) session.getUserProperties().get("mesa");
        String perfil = (String) session.getUserProperties().get("perfil");
        if (("").equals(tiro.getMsg()) || tiro.getMsg() == null) {
            for (Session s : peers) {
                if (s.isOpen() && mesa.equals(s.getUserProperties().get("mesa"))
                        && !perfil.equals(s.getUserProperties().get("perfil"))) {
                    try {
                        try {
                            s.getBasicRemote().sendObject(tiro);
                        } catch (EncodeException ex) {
                            Logger.getLogger(WebsocketBatalhaNaval.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } catch (IOException ex) {
                        Logger.getLogger(WebsocketBatalhaNaval.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }else{
            for (Session s : peers) {
                if (s.isOpen() && mesa.equals(s.getUserProperties().get("mesa"))
                        && !perfil.equals(s.getUserProperties().get("perfil"))) {
                    try {
                        s.getBasicRemote().sendText(tiro.getMsg());
                    } catch (IOException ex) {
                        Logger.getLogger(WebsocketBatalhaNaval.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
       
    
    @OnClose
    public void onClose(Session peer) {
        peers.remove(peer);
    }

    @OnError
    public void onError(Throwable t) {
        System.out.println(t.getMessage());
    }
    
}
