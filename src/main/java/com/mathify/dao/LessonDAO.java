/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mathify.dao;

import com.mathify.model.Lesson;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Akari
 */
public class LessonDAO {
    private static List<Lesson> lessonList =
            new ArrayList<>();

    public void addLesson(Lesson lesson) {

        lessonList.add(lesson);

        System.out.println("Lesson berhasil ditambahkan!");
    }

    public List<Lesson> getAllLessons() {

        return lessonList;
    }

    public Lesson getLessonById(String lessonId) {

        for (Lesson lesson : lessonList) {

            if (lesson.getLessonId().equals(lessonId)) {

                return lesson;
            }
        }

        return null;
    }

    public void updateLesson(String lessonId,
                             Lesson updatedLesson) {

        Lesson lesson = getLessonById(lessonId);

        if (lesson != null) {

            lesson.setTitle(updatedLesson.getTitle());
            lesson.setDescription(updatedLesson.getDescription());
            lesson.setXpReward(updatedLesson.getXpReward());

            System.out.println("Lesson berhasil diupdate!");

        } else {

            System.out.println("Lesson tidak ditemukan!");
        }
    }

    public void deleteLesson(String lessonId) {

        lessonList.removeIf(
                lesson ->
                        lesson.getLessonId().equals(lessonId)
        );

        System.out.println("Lesson berhasil dihapus!");
    }

    public List<Lesson> searchLesson(String keyword) {

        List<Lesson> result = new ArrayList<>();

        for (Lesson lesson : lessonList) {

            if (lesson.getTitle()
                    .toLowerCase()
                    .contains(keyword.toLowerCase())) {

                result.add(lesson);
            }
        }

        return result;
    }

    public int getTotalLesson() {

        return lessonList.size();
    }
}
