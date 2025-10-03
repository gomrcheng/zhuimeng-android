package com.chat.wkwallet.entity;

public class CoinRate {
    private String coinName;
    private long id;
    private String lastUpdate;
    private double rate;
    private String source;
    private String symbol;

    public String getCoinName() { return coinName; }
    public void setCoinName(String value) { this.coinName = value; }

    public long getid() { return id; }
    public void setid(long value) { this.id = value; }

    public String getLastUpdate() { return lastUpdate; }
    public void setLastUpdate(String value) { this.lastUpdate = value; }

    public double getRate() { return rate; }
    public void setRate(double value) { this.rate = value; }

    public String getSource() { return source; }
    public void setSource(String value) { this.source = value; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String value) { this.symbol = value; }
}
