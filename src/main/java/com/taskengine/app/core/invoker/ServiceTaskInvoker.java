package com.taskengine.app.core.invoker;

import com.taskengine.app.core.data.om.InvokerType;

public interface ServiceTaskInvoker {
    Object invoke();
    InvokerType type();
}
