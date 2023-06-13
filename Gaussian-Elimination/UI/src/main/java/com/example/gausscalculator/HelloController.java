package com.example.gausscalculator;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;


            public class HelloController {
                int xcount;

                @FXML
                private Pane Xpane;
                @FXML
                private Button Xbutton;
                @FXML
                private TextField Xcount;
                @FXML
                private Pane Pane2;
                @FXML
                private VBox Vbucks;
                @FXML
                private Button Butt;
                @FXML
                private Pane Pane3;
                @FXML
                private VBox Vbocks;
                @FXML
                private void XButtonPressed(){
                    xcount=Integer.parseInt(Xcount.getText());
                    Xpane.setVisible(false);
                    for (int i = 0; i < xcount; i++) {
                        HBox hub = new HBox();
                        for (int j = 1; j <= xcount; j++) {
                            TextField tx=new TextField();
                            tx.setStyle("-fx-background-color:  #3a6d7a;-fx-background-radius: 20");
                            hub.getChildren().add(new Text(String.format(" X%d = ",j)));
                            hub.getChildren().add(tx);
                        }
                        if(xcount<4){
                            Vbucks.setPrefWidth(750);
                            Vbucks.setPadding(new Insets(0,-60,0,275));
                        }else if(xcount<6){
                            Vbucks.setPrefWidth(900);
                            Vbucks.setPadding(new Insets(0,-60,0,150));
                        }
                        TextField tx=new TextField();
                        tx.setStyle("-fx-background-color:  #3a6d7a;-fx-background-radius: 20;");
                        tx.setPrefWidth(120);
                        hub.getChildren().add(new Text(" | "));
                        hub.getChildren().add(tx);
                        hub.setStyle("-fx-background-color:  #74d7f2; -fx-background-radius: 20px;-fx-alignment: CENTER;");
                        hub.setPadding(new Insets(10,10,10,10));
                        hub.setPrefHeight(35);
                        hub.setSpacing(5);
                        Vbucks.getChildren().add(hub);
                        Vbucks.setSpacing(10);
                    }

                    Pane2.setVisible(true);

                }
                @FXML
                private void solveButtonPressed(){
                    double[][] a = new double[xcount][xcount];
                    double[] b = new double[xcount];
                    for (int i = 0; i < xcount; i++) {
                        HBox h = (HBox) Vbucks.getChildren().get(i);
                        TextField tf;
                        for (int j = 0; j < xcount; j++) {
                            tf = (TextField) (h.getChildren().get(j + j + 1));
                            a[i][j] = Double.parseDouble(tf.getText());
                        }
                        tf=(TextField) (h.getChildren().get(xcount+xcount+1));
                        b[i] = Double.parseDouble(tf.getText());

                    }
                    solution(a,b);
                    Pane2.setVisible(false);
                    Pane3.setVisible(true);
                }

                public  void solution(double[][] matrix, double[] solution) {
                    for (int i = 0; i < solution.length; i++) {
                        int pivot = i;
                        for (int a = i + 1; a < solution.length; a++)
                            if (Math.abs(matrix[a][i]) > Math.abs(matrix[pivot][i])) pivot = a;

                        //Намираме Pivot row-а с който ще изпълняваме елиминацията и я местим
                        double[] temp = matrix[i];
                        matrix[i] = matrix[pivot];
                        matrix[pivot] = temp;
                        double s = solution[i];
                        solution[i] = solution[pivot];
                        solution[pivot] = s;

                        //Решаваме матрицата чрез pivoting между ред X и ред Y
                        for (int j = i + 1; j < solution.length; j++) {
                            double coefficient = matrix[j][i] / matrix[i][i];
                            solution[j] -= coefficient * solution[i];
                            for (int d = i; d < solution.length; d++) {
                                matrix[j][d] -= coefficient * matrix[i][d];
                            }

                        }
                    }
                    printMatrix(matrix, solution);
                    double[] solutions = new double[solution.length];
                    for (int i = solution.length - 1; i >= 0; i--) {
                        double sum = 0;
                        for (int j = i + 1; j < solution.length; j++)
                            sum += matrix[i][j] * solutions[j];
                        solutions[i] = (solution[i] - sum) / matrix[i][i];
                    }
                    printX(solutions);

                }

                private void printMatrix(double matrix[][],double arr[]){
                    String a="";
                    for (int i = 0; i < xcount; i++) {
                        HBox hb=new HBox();
                        for (int j = 0; j < xcount; j++) {
                            a+=String.format("| %.2f ",matrix[i][j]);
                        }
                        a+=String.format("| %.2f |",arr[i]);
                        Text t = new Text(a);
                       hb.getChildren().add(t);
                        hb.setStyle("-fx-background-color:  #74d7f2; -fx-background-radius: 20px;-fx-alignment: CENTER;");
                        hb.setPadding(new Insets(10,10,10,10));
                        hb.setPrefHeight(35);
                        hb.setSpacing(5);
                        Vbocks.setSpacing(10);
                        Vbocks.getChildren().add(hb);
                        a=" ";
                    }
                }
                private void printX(double arr[]) {
                    HBox hu = new HBox();
                    for (int i = 0; i < arr.length; i++) {
                        Text tot = new Text(String.format("X%d = | %.2f ", (i+1), arr[i]));
                        hu.getChildren().add(tot);
                    }
                    hu.setStyle("-fx-background-color:  #74d7f2; -fx-background-radius: 20px;-fx-alignment: CENTER;");
                    hu.setPadding(new Insets(10,10,10,10));
                    hu.setPrefHeight(35);
                    hu.setSpacing(5);
                    Vbocks.getChildren().add(hu);

                }

            }
