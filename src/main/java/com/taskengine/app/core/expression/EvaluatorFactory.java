package com.taskengine.app.core.expression;

public class EvaluatorFactory {


    public Evaluator createEvaluator(String language) {
        if ("javascript".equalsIgnoreCase(language)) {
            return new JavascriptEvaluator();
        }
        throw new IllegalArgumentException("Unsupported expression language: " + language);
    }

}
