<%-- 
    Document   : lesson-content
    Created on : May 20, 2026, 1:57:12 PM
    Author     : Akari
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="com.mathify.model.Lesson"%>

<!DOCTYPE html>

// Ni Cuma sementara Ya pis, Ntar klo dah fix modelnya baru dibaikin lagi

<html>

    <head>

        <title>Lesson Content</title>

        <style>

            body{

                font-family: Arial;

                margin: 0;
                padding: 0;

                background: #f5f5f5;
            }

            .container{

                width: 80%;

                margin: auto;

                margin-top: 30px;
            }

            .lesson-card{

                background: white;

                padding: 30px;

                border-radius: 15px;

                box-shadow:
                    0 2px 10px rgba(0,0,0,0.1);
            }

            .lesson-number{

                color: #4F46E5;

                font-weight: bold;
            }

            .content{

                margin-top: 20px;

                line-height: 1.8;
            }

            .navigation{

                margin-top: 30px;

                display: flex;

                justify-content: space-between;
            }

            .btn{

                background: #4F46E5;

                color: white;

                padding: 12px 18px;

                border-radius: 10px;

                text-decoration: none;
            }

            .btn:hover{

                opacity: 0.8;
            }

            .progress{

                margin-top: 20px;

                background: #ddd;

                height: 10px;

                border-radius: 10px;
            }

            .progress-bar{

                background: #4F46E5;

                height: 10px;

                border-radius: 10px;
            }

        </style>

    </head>

    <body>

        <%

            Lesson lesson
                    = (Lesson) request.getAttribute(
                            "lesson"
                    );

            int currentLesson
                    = (Integer) request.getAttribute(
                            "currentLesson"
                    );

            int totalLessons
                    = (Integer) request.getAttribute(
                            "totalLessons"
                    );

            double percentage
                    = ((double) currentLesson
                    / totalLessons) * 100;

        %>

        <div class="container">

            <div class="lesson-card">

                <p class="lesson-number">

                    Lesson
                    <%= lesson.getLessonNumber()%>

                </p>

                <h1>
                    <%= lesson.getTitle()%>
                </h1>

                <p>
                    <%= lesson.getDescription()%>
                </p>

                <div class="content">

                    <%= lesson.getContent()%>

                </div>

                <!-- PROGRESS -->

                <div class="progress">

                    <div class="progress-bar"

                         style="
                         width:
                         <%= percentage%>%">

                    </div>

                </div>

                <p>

                    Progress:
                    <%= currentLesson%> /
                    <%= totalLessons%>

                </p>

                <!-- NAVIGATION -->

                <div class="navigation">

                    <!-- PREVIOUS -->

                    <div>

                        <%

                            if (currentLesson > 1) {

                        %>

                        <a href="lesson-content?id=
                           <%= currentLesson - 1%>"

                           class="btn">

                            Previous

                        </a>

                        <%

                            }

                        %>

                    </div>

                    <!-- NEXT -->

                    <div>

                        <%                    if (currentLesson
                                    < totalLessons) {

                        %>

                        <a href="lesson-content?id=
                           <%= currentLesson + 1%>"

                           class="btn">

                            Next

                        </a>

                        <%

                            }

                        %>

                    </div>

                </div>

            </div>

        </div>

    </body>
</html>