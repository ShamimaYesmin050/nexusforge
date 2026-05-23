package nexusforge;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

         // ======== ROOT ==========
        BorderPane root = new BorderPane();

           // ======= SIDEBAR =======
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(25));
        sidebar.setPrefWidth(220);

        sidebar.setStyle(
                "-fx-background-color: #0F172A;"
        );

        Label logo = new Label("NexusForge");
        logo.setTextFill(Color.WHITE);
        logo.setFont(Font.font("Arial", 26));

        Button dashboardBtn = createSidebarButton("Dashboard");
        Button employeeBtn = createSidebarButton("Employees");
        Button attendanceBtn = createSidebarButton("Attendance");
        Button reportsBtn = createSidebarButton("Reports");
        Button logoutBtn = createSidebarButton("Logout");

        sidebar.getChildren().addAll(
                logo,
                dashboardBtn,
                employeeBtn,
                attendanceBtn,
                reportsBtn,
                logoutBtn
        );

        // ======= TOP DASHBOARD ======
        HBox dashboardCards = new HBox(20);
        dashboardCards.setPadding(new Insets(20));

        VBox totalEmployeesCard = createCard("Total Employees", "0");
        VBox presentTodayCard = createCard("Present Today", "0");
        VBox absentTodayCard = createCard("Absent Today", "0");

        dashboardCards.getChildren().addAll(
                totalEmployeesCard,
                presentTodayCard,
                absentTodayCard
        );

            //======= CONTROL AREA ======
        HBox controlArea = new HBox(15);
        controlArea.setPadding(new Insets(10, 20, 10, 20));

        Button addBtn = createControlButton("Add Employee");
        Button updateBtn = createControlButton("Update");
        Button deleteBtn = createControlButton("Delete");
        Button searchBtn = createControlButton("Search");
        Button markBtn = createControlButton("Mark Attendance");

        controlArea.getChildren().addAll(
                addBtn,
                updateBtn,
                deleteBtn,
                searchBtn,
                markBtn
        );

            // ====== TABLE VIEW =======
        TableView tableView = new TableView();

        tableView.setPrefHeight(400);

        TableColumn idColumn = new TableColumn("ID");
        idColumn.setPrefWidth(150);

        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setPrefWidth(300);

        TableColumn departmentColumn = new TableColumn("Department");
        departmentColumn.setPrefWidth(250);

        tableView.getColumns().addAll(
                idColumn,
                nameColumn,
                departmentColumn
        );

        VBox centerLayout = new VBox(10);

        centerLayout.getChildren().addAll(
                dashboardCards,
                controlArea,
                tableView
        );

        VBox.setVgrow(tableView, Priority.ALWAYS);

        root.setLeft(sidebar);
        root.setCenter(centerLayout);

           // ====== SCENE =====
        Scene scene = new Scene(root, 1200, 700);

        stage.setTitle("Employee Management System");
        stage.setScene(scene);
        stage.show();
    }

         // ====== SIDEBAR BUTTON ======
    private Button createSidebarButton(String text) {

        Button btn = new Button(text);

        btn.setPrefWidth(170);
        btn.setPrefHeight(45);

        btn.setStyle(
                "-fx-background-color: #1E293B;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 15px;" +
                "-fx-background-radius: 10;"
        );

        return btn;
    }

           // ====== CONTROL BUTTON =======
    private Button createControlButton(String text) {

        Button btn = new Button(text);

        btn.setPrefHeight(40);

        btn.setStyle(
                "-fx-background-color: #2563EB;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-background-radius: 8;"
        );

        return btn;
    }

    // ================= DASHBOARD CARD =================
    private VBox createCard(String title, String value) {

        VBox card = new VBox(10);

        card.setAlignment(Pos.CENTER);
        card.setPrefSize(240, 120);

        card.setStyle(
                "-fx-background-color: #E2E8F0;" +
                "-fx-background-radius: 15;"
        );

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", 18));

        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("Arial", 32));

        card.getChildren().addAll(
                titleLabel,
                valueLabel
        );

        return card;
    }

    public static void main(String[] args) {
        launch(args);
    }
}