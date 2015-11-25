/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author pbeat_000
 */
public class TiroDisparadoDecoder implements Decoder.Text<TiroDisparado>{
     @Override
    public void init(final EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public TiroDisparado decode(final String textMessage) throws DecodeException {
        TiroDisparado tiro = new TiroDisparado();
        if ((textMessage).contains("x")) {
            JsonObject obj = Json.createReader(new StringReader(textMessage))
                    .readObject();
            tiro.setX(Integer.parseInt(obj.getString("x")));
            tiro.setY(Integer.parseInt(obj.getString("y")));
        }else{
            tiro.setMsg(textMessage);
        }
        return tiro;
    }

    @Override
    public boolean willDecode(final String s) {
        return true;
    }
}
