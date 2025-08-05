package com.taskengine.app.core.expression;

public interface Evaluator {

    Object evaluate(String expression, ExpressionContext context);
}
