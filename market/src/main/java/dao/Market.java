package dao;

import com.mongodb.rx.client.Success;

import rx.Observable;

import model.Stocks;

public interface Market {
    Observable<Success> addCompany(String name, int stocksCount, int stocksPrice);

    Observable<Stocks> getCompanies();

    Observable<Success> addStocks(String companyName, int stocksCount);

    Observable<Stocks> getStocksInfo(String companyName);

    Observable<Success> buyStocks(String companyName, int count);

    Observable<Success> changeStocksPrice(String companyName, int newStocksPrice);
}