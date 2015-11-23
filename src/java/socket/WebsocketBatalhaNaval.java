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
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import servlets.TiroDisparado;
import servlets.TiroDisparadoDecoder;
import servlets.TiroDisparadoEncoder;

/**
 *
 * @author gabriel.ibson
 */
@ServerEndpoint(value = "/batalhanavalendpoint/{mesa}/{jogador}", encoders = TiroDisparadoEncoder.class, decoders = TiroDisparadoDecoder.class)
public class WebsocketBatalhaNaval {
    private static int i = 1;
    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
  
    @OnOpen
    public void onOpen (final Session peer, @PathParam("mesa") final String mesa, @PathParam("jogador") final String jogador) {
        peer.getUserProperties().put("mesa", mesa);
        i++;
        peer.getUserProperties().put("jogador", jogador+i);
        peers.add(peer);
    }

    /**
     *
     * @param session
     * @param tiro
     */
    @OnMessage
    public void onMessage(final Session session, final TiroDisparado tiro){
        String mesa = (String) session.getUserProperties().get("mesa");
        String jogador = (String) session.getUserProperties().get("jogador");
         for (Session s : peers) {
            if (s.isOpen() && mesa.equals(s.getUserProperties().get("mesa")) 
                    && !jogador.equals(s.getUserProperties().get("jogador"))) {
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
        
    }
    
    @OnClose
    public void onClose (Session peer) {
        peers.remove(peer);
    }
    
}
