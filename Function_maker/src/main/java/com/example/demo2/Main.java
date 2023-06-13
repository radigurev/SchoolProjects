package com.example.demo2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;


/**
 * Това е програмата която чертае функции Целта на програмата е когато се run-не
 * се показва вече вписана графика като пример. Когато се отвори прозорчето най отдолу ще имате
 * TextBox(TextField) където ще можете да записвате функциите като израси
 * Във израсите можете да записвате знаци като +, -, *, /,^ които функционират
 * които са както в програмирането. Допълнително може да се ползват
 * sin, cos, tan, sec, csc, cot,
 * arcsin, arccos, arctan, exp, ln, log2, log10, abs, and sqrt,
 * where ln is the natural log, log2 is the log to the base 2,
 * log10 is the log to the base 10, abs is absolute value,
 * и sqrt
 * големината на екрана е от -5 => 5 на x и y абцисите
 *
 * lmao cc
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    //Това е така наречения CANVAS с който ще се чертае върху екрана(в този случай ще чертаем функцията)
    private Graph graph;

    //Това е TestField класа от Javafx където ще трябва да напиша функцията която искам да изобразя на екрана
    private TextField functionInput;

    //Това е за изписване на съобщение като съобщение за неправилно записана функция
    private Label message;


    public void start(Stage stage) {

        graph = new Graph(new Expr("x * (x + 3) - (x + sqrt(2)) * (x - sqrt(2))"));

        message = new Label("Напишете функция като примерната");

        functionInput = new TextField("x*(x+3)-(x +sqrt(2))*(x -sqrt(2))");

        Button ready = new Button("Enter");
        ready.setDefaultButton(true);


        /**
         Тук взимам израза който е написан в textfield-а и когато се
         създаде нов обект от и казваме на GraphCanvas-а да начерате
         функцията и ако израза на функцията е грешен влиза в catch
         и изписва че функцията е грешна
         */
        ready.setOnAction(evt -> {
            Expr function;
            try {
                String def = functionInput.getText();
                function = new Expr(def);
                graph.setFunction(function);
            } catch (IllegalArgumentException e) {
                graph.clearFunction();
                message.setText(e.getMessage());
            }
            functionInput.selectAll();
            functionInput.requestFocus();
        });

        /**
         Създаване на hbox ми позволява подребда на label-а functionTextField-а и бутона
         */
        HBox bottom = new HBox(8, new Label("f(x) = "), functionInput, ready);

        BorderPane root = new BorderPane();
        root.setCenter(graph);
        root.setTop(message);
        root.setBottom(bottom);


        /**
         Малко front-end с библиотеките на javafx
         */

        root.setStyle("-fx-border-color:gray; -fx-border-width:4px;-fx-text-alignment: CENTER");
        message.setTextFill(Color.BLACK);

        message.setStyle("-fx-background-color:white; -fx-padding:7px; "
                + "-fx-border-color:gray; -fx-border-width:0 0 4px 0; -fx-alignment: CENTER");
        message.setMaxWidth(10000);
        ready.setStyle("-fx-background-radius: 10px; -fx-background-color: #949398FF");
        bottom.setStyle("-fx-border-color:#949398FF; -fx-border-width:4px 0 0 0; -fx-padding:8px;-fx-alignment: CENTER");
        HBox.setHgrow(functionInput, Priority.ALWAYS);

        //Сетване на прозореца
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Function painter");
        stage.show();

    }

    private static class Graph extends Canvas {

        private static final int FUNCTION_X = 10;
        private static final int FUNCTION_Y = 20;
        private final double NO_FUNCTION_MESSAGE_X = getWidth() / 2 - 40;
        private final double NO_FUNCTION_MESSAGE_Y = getHeight() / 2 - 25;
        private static final int FUNCTION_DOTS = 300;
        private static final int ORDINATE_POSITION = 305;
        private static final int ORDINATE_POSITION_X = 285;
        private static final int AXIS_POSITION = 295;
        private static final int AXIS_POSITION_Y = 315;

        Expr func;


        Graph(Expr firstFunction) {
            super(600, 600);
            func = firstFunction;
            draw();
        }

        //Слагаме Canvas-a да изобразифункцията
        public void setFunction(Expr exp) {
            func = exp;
            draw();
        }

        //Чистим функция
        public void clearFunction() {
            func = null;
            draw();
        }

        /**
            Слагаме прозореца да е бял, после рисувам
            Абцисата и ординатата с мерните единици
            и най накрая се рисува функцията
            !АКО израза е грешен излиза съобщение
         */

        public void draw() {
            GraphicsContext g = getGraphicsContext2D();
            g.setFill(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());

            if (func == null) {
                g.setFill(Color.BLACK);
                g.fillText("Няма функция", NO_FUNCTION_MESSAGE_X, NO_FUNCTION_MESSAGE_Y);
            } else {
                g.setFill(Color.PURPLE);
                g.fillText("y = " + func, FUNCTION_X, FUNCTION_Y);
                drawAxes(g);
                drawFunction(g);
            }
        }

        /**
            Метода за рисуване на абцисата и ординатата с мерните единици от -5=>5

         */
        void drawAxes(GraphicsContext g) {
            double width = getWidth();
            double height = getHeight();
            g.setStroke(Color.BLACK);
            g.setLineWidth(2);
            g.strokeLine(5, height / 2, width - 5, height / 2);
            g.strokeLine(width / 2, 5, width / 2, height - 5);
            g.setStroke(Color.RED);
            /*g.fillText("0",width/2 - 8,height/2+5);
            g.fillText("2",285,185);
            g.fillText("3",285,125);
            g.fillText("1",285,245);
            g.fillText("0",285,305);
             */
            //295
            //g.fillText("1",295,315);
            for (int i = 1; i <= 5; i++) {
                g.fillText(Integer.toString(i), ORDINATE_POSITION_X, ORDINATE_POSITION - 60 * i);
                g.fillText(String.format("-%d", i), ORDINATE_POSITION_X + 20, ORDINATE_POSITION + 60 * i);
                g.fillText(Integer.toString(i), AXIS_POSITION + 60.5 * i, AXIS_POSITION_Y);
                g.fillText(String.format("-%d", i), AXIS_POSITION - 60.5 * i, AXIS_POSITION_Y);
            }

        }

        /**
         Рисуване графиката като слагаме 301 точки със линии които свързваме

         */
        void drawFunction(GraphicsContext g) {

            double x, y;    //y=f(x)
            double prevX, prevY;//миналите точки в графиката

            double dx;//разликата на последователните точки в графиката


            dx = 10.0 / 300;

            //слагане на цвят за функцията
            g.setStroke(Color.RED);
            g.setLineWidth(1);

            x = -5;
            y = func.value(x);

            /**
              в този цикъл се рисуват останалите части от линията която трябва да се нарисува
             и ако Y е not assigned защото така се връща от expr класа се рисува
             */
            for (int i = 1; i <= FUNCTION_DOTS; i++) {

                prevX = x;
                prevY = y;

                x += dx;//взима се кординатите на следващата точка

                y = func.value(x);

                if ((!Double.isNaN(y)) && (!Double.isNaN(prevY))) {
                    //чертаем линейния сигмент между двете точки
                    putLine(g, prevX, prevY, x, y);
                }

            }

        }

        //Рисуваме линия от точка x1 y1 до x2 y2
        void putLine(GraphicsContext g, double x1, double y1,
                     double x2, double y2) {


            double a1, b1;
            double a2, b2;

            double width = getWidth();
            double height = getHeight();
            //Формули за превръщане от кординати в пиксели
            a1 = (int) ((x1 + 5) / 10 * width);
            b1 = (int) ((5 - y1) / 10 * height);
            a2 = (int) ((x2 + 5) / 10 * width);
            b2 = (int) ((5 - y2) / 10 * height);
            //рисуване на функцията
            g.strokeLine(a1, b1, a2, b2);

        }

    }


}