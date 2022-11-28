package controller;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class ServerGame {
Server server = new Server();

public ServerGame() {
    Kryo kryo = server.getKryo();
    kryo.register(SomeRequest.class);
    kryo.register(SomeResponse.class);
    server.start();
    try {
        server.bind(54555, 54777);
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    server.addListener(new Listener() {
           public void received (Connection connection, Object object) {
              if (object instanceof SomeRequest) {
                 SomeRequest request = (SomeRequest)object;
                 System.out.println(request.text);
                 SomeResponse response = new SomeResponse();
                 response.text = "Thanks!";
                 connection.sendTCP(response);
              }
           }
        });
}

}