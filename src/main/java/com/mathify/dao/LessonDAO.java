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

    static {

        lessonList.add(
                new Lesson(
                        "1",
                        "Pengenalan Algebra",
                        "Belajar dasar algebra",
                        "Algebra adalah cabang matematika yang mempelajari simbol dan operasi matematika.",
                        100,
                        1
                )
        );

        lessonList.add(
                new Lesson(
                        "2",
                        "Variabel dan Konstanta",
                        "Belajar variabel dan konstanta",
                        "Variabel adalah simbol yang mewakili nilai tertentu dalam matematika.",
                        120,
                        2
                )
        );

        lessonList.add(
                new Lesson(
                        "3",
                        "Persamaan Linear",
                        "Belajar persamaan linear",
                        "Persamaan linear adalah persamaan matematika dengan pangkat tertinggi satu.",
                        150,
                        3
                )
        );
    }

    public void addLesson(Lesson lesson) {

        lessonList.add(lesson);

        System.out.println(
                "Lesson berhasil ditambahkan!"
        );
    }

    public List<Lesson> getAllLessons() {

        return lessonList;
    }

    public Lesson getLessonById(
            String lessonId) {

        for (Lesson lesson : lessonList) {

            if (lesson.getLessonId()
                    .equals(lessonId)) {

                return lesson;
            }
        }

        return null;
    }

    public Lesson getLessonByNumber(
            int lessonNumber) {

        for (Lesson lesson : lessonList) {

            if (lesson.getLessonNumber()
                    == lessonNumber) {

                return lesson;
            }
        }

        return null;
    }

    public void updateLesson(
            String lessonId,
            Lesson updatedLesson) {

        Lesson lesson =
                getLessonById(lessonId);

        if (lesson != null) {

            lesson.setTitle(
                    updatedLesson.getTitle()
            );

            lesson.setDescription(
                    updatedLesson.getDescription()
            );

            lesson.setXpReward(
                    updatedLesson.getXpReward()
            );

            lesson.setContent(
                    updatedLesson.getContent()
            );

            lesson.setLessonNumber(
                    updatedLesson.getLessonNumber()
            );

            System.out.println(
                    "Lesson berhasil diupdate!"
            );

        } else {

            System.out.println(
                    "Lesson tidak ditemukan!"
            );
        }
    }

    public void deleteLesson(
            String lessonId) {

        lessonList.removeIf(
                lesson ->
                        lesson.getLessonId()
                                .equals(lessonId)
        );

        System.out.println(
                "Lesson berhasil dihapus!"
        );
    }

    public List<Lesson> searchLesson(
            String keyword) {

        List<Lesson> result =
                new ArrayList<>();

        for (Lesson lesson : lessonList) {

            if (lesson.getTitle()
                    .toLowerCase()
                    .contains(
                            keyword.toLowerCase()
                    )) {

                result.add(lesson);
            }
        }

        return result;
    }

    public int getTotalLesson() {

        return lessonList.size();
    }

    public int getTotalLessons() {

        return lessonList.size();
    }
}