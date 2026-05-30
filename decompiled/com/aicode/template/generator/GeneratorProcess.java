package com.aicode.template.generator;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/generator/GeneratorProcess.class */
public enum GeneratorProcess {
    INIT("初始化完成", 0.1d),
    ANALYSIS("解析完成", 0.2d),
    REQUEST_AI("请求AI模型", 0.3d),
    GENERATING("生成单测中...", 0.95d),
    GENERATED("生成完成", 0.98d),
    OVER("生成结束", 1.0d);

    private String message;
    private double process;

    GeneratorProcess(String message, double process) {
        this.message = message;
        this.process = process;
    }

    public String getMessage() {
        return this.message;
    }

    public double getProcess() {
        return this.process;
    }
}
