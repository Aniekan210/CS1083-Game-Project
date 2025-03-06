import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;

public class LeadeBoard extends VBox {

    // Player class with necessary properties and getters
    public static class Player {
        private final SimpleStringProperty name;
        private final SimpleIntegerProperty roundNum;
        private final SimpleIntegerProperty rowNum;
        private final SimpleIntegerProperty vBucksWon;

        public Player(String name, int roundNum, int rowNum, int vBucksWon) {
            this.name = new SimpleStringProperty(name);
            this.roundNum = new SimpleIntegerProperty(roundNum);
            this.rowNum = new SimpleIntegerProperty(rowNum);
            this.vBucksWon = new SimpleIntegerProperty(vBucksWon);
        }

        public String getName() {
            return name.get();
        }

        public int getRoundNum() {
            return roundNum.get();
        }

        public int getRowNum() {
            return rowNum.get();
        }

        public int getVBucksWon() {
            return vBucksWon.get();
        }
    }

    //@Override
    public void start(Stage primaryStage) {
        TableView<Player> table = new TableView<>();

        // Create columns
        TableColumn<Player, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Player, Integer> roundColumn = new TableColumn<>("Round");
        roundColumn.setCellValueFactory(new PropertyValueFactory<>("roundNum"));

        TableColumn<Player, Integer> rowColumn = new TableColumn<>("Row");
        rowColumn.setCellValueFactory(new PropertyValueFactory<>("rowNum"));

        TableColumn<Player, Integer> vBucksColumn = new TableColumn<>("VBucks Won");
        vBucksColumn.setCellValueFactory(new PropertyValueFactory<>("vBucksWon"));

        // Add columns to table
        table.getColumns().addAll(nameColumn, roundColumn, rowColumn, vBucksColumn);

        // Load data from text file
        ObservableList<Player> data = loadLeaderboardData();

        // Sort by VBucksWon (desc), RoundNum (desc), RowNum (desc)
        data.sort(Comparator
            .comparing(Player::getVBucksWon, Comparator.reverseOrder())
            .thenComparing(Player::getRoundNum, Comparator.reverseOrder())
            .thenComparing(Player::getRowNum, Comparator.reverseOrder())
        );

        table.setItems(data);

        VBox vbox = new VBox(table);
        Scene scene = new Scene(vbox, 400, 300);

        primaryStage.setTitle("Leaderboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to read leaderboard data from a text file
    private ObservableList<Player> loadLeaderboardData() {
        String filename = "leaderboard.txt";
        ObservableList<Player> list = FXCollections.observableArrayList();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+"); // Split by spaces
                if (parts.length == 4) {
                    String name = parts[0];
                    int roundNum = Integer.parseInt(parts[1]);
                    int rowNum = Integer.parseInt(parts[2]);
                    int vBucksWon = Integer.parseInt(parts[3]);
                    list.add(new Player(name, roundNum, rowNum, vBucksWon));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return list;
    }
}
