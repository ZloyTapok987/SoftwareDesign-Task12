import io.reactivex.netty.protocol.http.server.HttpServer;

import rx.Observable;

import http.RxAccountServer;
import http.MarketHttpClient;

import dao.AccountInMemory;

public class Main {
    public static void main(String[] args) {
        RxAccountServer server = new RxAccountServer(new AccountInMemory(new MarketHttpClient()));

        HttpServer
                .newServer(8081)
                .start((req, resp) -> {
                    Observable<String> response = server.getResponse(req);
                    return resp.writeString(response.map(r -> r + System.lineSeparator()));
                })
                .awaitShutdown();
    }
}