import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableRow;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.text.FontWeight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import javafx.util.Callback;

public class LeaderBoardTable extends VBox {
    public LeaderBoardTable(double width, double height) {
        this.setMaxHeight(height);
        this.setMaxWidth(width);

        // Table view and columns
        TableView<Player> table = new TableView<>();
        
        // Create columns
        TableColumn<Player, Integer> rankColumn = new TableColumn<>("Rank");
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        rankColumn.setSortable(false);
        rankColumn.setPrefWidth(50);
        rankColumn.setStyle("-fx-alignment: CENTER; -fx-text-fill: white;");

        TableColumn<Player, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setSortable(false);
        nameColumn.setPrefWidth(150);
        nameColumn.setStyle("-fx-alignment: CENTER; -fx-text-fill: white;");

        TableColumn<Player, Integer> roundColumn = new TableColumn<>("Round");
        roundColumn.setCellValueFactory(new PropertyValueFactory<>("roundNum"));
        roundColumn.setSortable(false);
        roundColumn.setPrefWidth(70);
        roundColumn.setStyle("-fx-alignment: CENTER; -fx-text-fill: white;");

        TableColumn<Player, Integer> rowColumn = new TableColumn<>("Row");
        rowColumn.setCellValueFactory(new PropertyValueFactory<>("rowNum"));
        rowColumn.setSortable(false);
        rowColumn.setPrefWidth(50);
        rowColumn.setStyle("-fx-alignment: CENTER; -fx-text-fill: white;");

        TableColumn<Player, Integer> vBucksColumn = new TableColumn<>("VBucks Won");
        vBucksColumn.setCellValueFactory(new PropertyValueFactory<>("vBucksWon"));
        vBucksColumn.setSortable(false);
        vBucksColumn.setPrefWidth(100);
        vBucksColumn.setStyle("-fx-alignment: CENTER; -fx-text-fill: white;");

        // Add columns to table
        table.getColumns().addAll(rankColumn, nameColumn, roundColumn, rowColumn, vBucksColumn);

        // Load data from text file
        ObservableList<Player> data = loadLeaderboardData();

        // Sort by VBucksWon (desc), RoundNum (desc), RowNum (desc)
        data.sort(Comparator
            .comparing(Player::getVBucksWon, Comparator.reverseOrder())
            .thenComparing(Player::getRoundNum, Comparator.reverseOrder())
            .thenComparing(Player::getRowNum, Comparator.reverseOrder())
        );

        // Assign serial numbers after sorting
        int serialNumberCounter = 1;
        for (Player player : data) {
            player.serialNumber.set(serialNumberCounter++);
        }

        table.setItems(data);

        // Set table properties
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Row styling: add border to each row
        table.setRowFactory(tv -> {
            TableRow<Player> row = new TableRow<>();
            row.setStyle("-fx-background-color: #00000000;");
            return row;
        });

        // Header styling: set borders for the headers
        table.getColumns().forEach(column -> {
            column.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-background-color: white; -fx-text-fill: black;");
        });

        // Scrollbar styling directly within Java
        table.setStyle(
            "-fx-background-color: white; -fx-border-color: black; -fx-border-width: 3;" +
            "-fx-scroll-bar: #000000; " +
            "-fx-scroll-bar .thumb: #ffffff; " +
            "-fx-scroll-bar .track: #ffffff; " +
            "-fx-scroll-bar .increment-button: #00000000;"
        );

        // Add table to the layout
        this.getChildren().add(table);
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
                    int rank = 0;
                    rank = rank + 1;
                    String name = parts[0];
                    System.out.println(name);
                    int roundNum = Integer.parseInt(parts[1]);
                    int rowNum = Integer.parseInt(parts[2]);
                    int vBucksWon = Integer.parseInt(parts[3]);
                    list.add(new Player(rank, name, roundNum, rowNum, vBucksWon));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return list;
    }
}
