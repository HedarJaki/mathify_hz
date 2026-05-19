package com.mathify.model;

public class LearningModule implements Playable {
    private String moduleId;
    private enum Type{VIDEO, TEXT, INTERACTIVE, EXECISE};
    private int durationMinutes;
    private int orderIndex;

    @Override
    public void start(){}

    public LearningModule(String id, int duration, int orderIndex){
        moduleId = id;
        durationMinutes = duration;
        this.orderIndex = orderIndex;
    }
}
