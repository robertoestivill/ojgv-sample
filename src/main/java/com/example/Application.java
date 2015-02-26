
package com.example;

import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.platform.Verticle;

import java.util.Map;

public class Application extends Verticle {

    @Override
    public void start() {

        int port = Integer.parseInt(System.getenv("OPENSHIFT_VERTX_PORT"));
        String ip = System.getenv("OPENSHIFT_VERTX_IP");

        vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                System.out.println("Got request: " + req.uri());
                System.out.println("Headers are: ");
                for (Map.Entry<String, String> entry : req.headers()) {
                    System.out.println(entry.getKey() + ":" + entry.getValue());
                }
                String gear = System.getenv("OPENSHIFT_GEAR_UUID");
                req.response().headers().set("Content-Type", "text/html; charset=UTF-8");
                req.response().end("<html><body><h1>Hello Vert.x from gear " + gear + ":)</h1></body></html>");
            }
        }).listen(port, ip);
    }
}
