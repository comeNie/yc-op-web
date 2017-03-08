package com.ai.yc.op.web.model.bill;

import com.ai.slp.balance.api.translatorbill.param.FunAccountDetailResponse;

/**
 * Created by liquid on 17/3/7.
 */
public class BillDetailResponse extends FunAccountDetailResponse{
    private static final long serialVersionUID = 1L;

    Integer id;

    String lspName;

    public String getLspName() {
        return lspName;
    }

    public void setLspName(String lspName) {
        this.lspName = lspName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
