package com.chat.wkwallet.entity;

import java.util.List;

public class GetCoinTypesEntity {

    private List<CoinTypeInfo> coinTypes;

    public List<CoinTypeInfo> getCoinTypes() {
        return coinTypes;
    }

    public void setCoinTypes(List<CoinTypeInfo> coinTypes) {
        this.coinTypes = coinTypes;
    }
}
