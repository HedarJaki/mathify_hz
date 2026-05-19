/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mathify.model;

/**
 *
 * @author Akari
 */
public class LearningModule implements Playable {

    private String moduleId;
    private String moduleType;
    private int durationMinutes;
    private String content;

    public LearningModule() {
    }

    public LearningModule(String moduleId,
                          String moduleType,
                          int durationMinutes,
                          String content) {

        this.moduleId = moduleId;
        this.moduleType = moduleType;
        this.durationMinutes = durationMinutes;
        this.content = content;
    }

    @Override
    public void start() {

        System.out.println("Module dimulai: " + moduleId);
    }

    @Override
    public void pause() {

        System.out.println("Module dijeda");
    }

    @Override
    public void stop() {

        System.out.println("Module dihentikan");
    }

    public String getContent() {

        return content;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
