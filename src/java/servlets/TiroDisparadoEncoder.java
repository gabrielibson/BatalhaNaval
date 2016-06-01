/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.math.BigDecimal;
import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;


public class TiroDisparadoEncoder implements Encoder.Text<TiroDisparado>{
     @Override
    public void init(final EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public String encode(final TiroDisparado tiro) throws EncodeException {
        return Json.createObjectBuilder()
                .add("x", String.valueOf(tiro.getX()))
                .add("y", String.valueOf(tiro.getY()))
                .add("sender", String.valueOf(tiro.getSender())).build()
                .toString();
    }

}
