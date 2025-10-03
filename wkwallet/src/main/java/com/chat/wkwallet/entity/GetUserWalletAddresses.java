package com.chat.wkwallet.entity;

import java.util.List;

public class GetUserWalletAddresses {

    private List<UserWalletAddress> list;
    /**
     * 总数量
     */
    private Long total;

    public List<UserWalletAddress> getList() { return list; }
    public void setList(List<UserWalletAddress> value) { this.list = value; }

    public Long getTotal() { return total; }
    public void setTotal(Long value) { this.total = value; }

}
