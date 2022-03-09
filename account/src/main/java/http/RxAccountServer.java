package http;

import io.reactivex.netty.protocol.http.server.HttpServerRequest;

import lombok.AllArgsConstructor;

import rx.Observable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import static utils.HttpRequest.*;

import dao.Account;

@AllArgsConstructor
public class RxAccountServer {
    private final Account accounts;

    public <T> Observable<String> getResponse(HttpServerRequest<T> request) {
        String path = request.getDecodedPath().substring(1);
        if (path.equals("add_user")) {
            return addUser(request);
        }
        if (path.equals("add_money")) {
            return addMoney(request);
        }
        if (path.equals("get_user_stocks_info")) {
            return getUserStocksInfo(request);
        }
        if (path.equals("get_all_money")) {
            return getAllMoney(request);
        }
        if (path.equals("buy_stocks")) {
            return buyStocks(request);
        }
        if (path.equals("sell_stocks")) {
            return sellStocks(request);
        }
        return Observable.just("Unsupported request : " + path);
    }

    private <T> Observable<String> addUser(HttpServerRequest<T> request) {
        Optional<String> error = checkRequestParameters(request, Collections.singletonList("id"));
        if (error.isPresent()) {
            return Observable.just(error.get());
        }

        long id = getLongParam(request, "id");
        return accounts.addUser(id).map(Objects::toString).onErrorReturn(Throwable::getMessage);
    }

    private <T> Observable<String> addMoney(HttpServerRequest<T> request) {
        Optional<String> error = checkRequestParameters(request, Arrays.asList("id", "count"));
        if (error.isPresent()) {
            return Observable.just(error.get());
        }

        long id = getLongParam(request, "id");
        int count = getIntParam(request, "count");
        return accounts.addMoney(id, count).map(Objects::toString).onErrorReturn(Throwable::getMessage);
    }

    private <T> Observable<String> getUserStocksInfo(HttpServerRequest<T> request) {
        Optional<String> error = checkRequestParameters(request, Collections.singletonList("id"));
        if (error.isPresent()) {
            return Observable.just(error.get());
        }

        long id = getLongParam(request, "id");
        return accounts.getUserStocksInfo(id).map(Objects::toString).reduce("", (s1, s2) -> s1 + ",\n" + s2);
    }

    private <T> Observable<String> getAllMoney(HttpServerRequest<T> request) {
        Optional<String> error = checkRequestParameters(request, Collections.singletonList("id"));
        if (error.isPresent()) {
            return Observable.just(error.get());
        }

        long id = getLongParam(request, "id");
        return accounts.getAllMoney(id).map(Objects::toString).onErrorReturn(Throwable::getMessage);
    }

    private <T> Observable<String> buyStocks(HttpServerRequest<T> request) {
        Optional<String> error = checkRequestParameters(request, Arrays.asList("id", "company_name", "count"));
        if (error.isPresent()) {
            return Observable.just(error.get());
        }

        long id = getLongParam(request, "id");
        String companyName = getQueryParam(request, "company_name");
        int count = getIntParam(request, "count");
        return accounts.buyStocks(id, companyName, count).map(Objects::toString).onErrorReturn(Throwable::getMessage);
    }

    private <T> Observable<String> sellStocks(HttpServerRequest<T> request) {
        Optional<String> error = checkRequestParameters(request, Arrays.asList("id", "company_name", "count"));
        if (error.isPresent()) {
            return Observable.just(error.get());
        }

        long id = getLongParam(request, "id");
        String companyName = getQueryParam(request, "company_name");
        int count = getIntParam(request, "count");
        return accounts.sellStocks(id, companyName, count).map(Objects::toString).onErrorReturn(Throwable::getMessage);
    }
}