package com.taskengine.app.customization;

import com.taskengine.app.core.invoker.Action;
import com.taskengine.app.core.invoker.ActionExecution;

public class SalesRegionalGroupSetAction implements Action {
    @Override
    public void execute(ActionExecution actionExecution) {
        // "europe"'un talep sahibinin bölgesi olduğunu varsayalım
        actionExecution.setVariable("SALES_REGIONAL_GROUP", "/europe/sales_management");
    }
}
