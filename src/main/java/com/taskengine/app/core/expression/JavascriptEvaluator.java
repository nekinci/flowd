package com.taskengine.app.core.expression;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;

public class JavascriptEvaluator implements Evaluator {

    @Override
    public Object evaluate(String expression, ExpressionContext context) {
        try (Context jsContext =
                     Context.newBuilder().allowHostAccess(HostAccess.EXPLICIT).build()) {
            jsContext.getBindings("js").putMember("context", context);
            return jsContext.eval("js", expression).as(Object.class);
        }
    }

}
