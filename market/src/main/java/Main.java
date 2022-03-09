import io.reactivex.netty.protocol.http.server.HttpServer;

import rx.Observable;

import http.RxMarketServer;

import static dao.Utils.createDao;

public class Main {
    public static void main(String[] args) {
        RxMarketServer server = new RxMarketServer(createDao());

        HttpServer
                .newServer(8080)
                .start((req, resp) -> {
                    Observable<String> response = server.getResponse(req);
                    return resp.writeString(response.map(r -> r + System.lineSeparator()));
                })
                .awaitShutdown();
    }
}