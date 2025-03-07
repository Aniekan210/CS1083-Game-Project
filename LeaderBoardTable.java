import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableCell;
import javafx.util.Callback;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;

public class LeaderBoardTable extends VBox {
    protected TableView<Player> table;
    protected TableColumn<Player, Integer> rankColumn;
    protected TableColumn<Player, String> nameColumn;
    protected TableColumn<Player, Integer> roundColumn;
    protected TableColumn<Player, Integer> rowColumn;
    protected TableColumn<Player, Integer> vBucksColumn;
    
    public LeaderBoardTable(double width, double height) {
        this.setMaxHeight(height);
        this.setMaxWidth(width);

        // Table view and columns
        table = new TableView<>();

        // Create columns
        rankColumn = new TableColumn<>("Rank");
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        rankColumn.setSortable(false);
        rankColumn.setPrefWidth(50);

        nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setSortable(false);
        nameColumn.setPrefWidth(140);

        roundColumn = new TableColumn<>("Round");
        roundColumn.setCellValueFactory(new PropertyValueFactory<>("roundNum"));
        roundColumn.setSortable(false);
        roundColumn.setPrefWidth(70);

        rowColumn = new TableColumn<>("Row");
        rowColumn.setCellValueFactory(new PropertyValueFactory<>("rowNum"));
        rowColumn.setSortable(false);
        rowColumn.setPrefWidth(50);

        vBucksColumn = new TableColumn<>("VBucks Won");
        vBucksColumn.setCellValueFactory(new PropertyValueFactory<>("vBucksWon"));
        vBucksColumn.setSortable(false);
        vBucksColumn.setPrefWidth(100);

        // Add columns to table
        table.getColumns().addAll(rankColumn, nameColumn, roundColumn, rowColumn, vBucksColumn);

        load();

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
    
    public void load()
    {
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
            row.setStyle("-fx-background-color: white;"); // Set row background to white
            return row;
        });

        // Header styling: set borders for the headers
        table.getColumns().forEach(column -> {
            column.setStyle(
                    "-fx-font-weight: bold; " +
                    "-fx-font-size: 14px; " +
                    "-fx-background-color: white; " +
                    "-fx-text-fill: black; " +
                    "-fx-alignment: CENTER; " +
                    "-fx-border-color: black; " +
                    "-fx-border-width: 1;"
            );
        });

        // Cell styling: add black border to each cell and set text color to black
        Callback<TableColumn<Player, Integer>, TableCell<Player, Integer>> integerCellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Player, Integer> call(TableColumn<Player, Integer> param) {
                        return new TableCell<>() {
                            @Override
                            protected void updateItem(Integer item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty || item == null) {
                                    setText(null);
                                    setStyle("");
                                } else {
                                    setText(item.toString());
                                    setStyle(
                                            "-fx-border-color: black; " +
                                            "-fx-border-width: 1; " +
                                            "-fx-alignment: CENTER; " +
                                            "-fx-text-fill: black; " +
                                            "-fx-background-color: white; " +
                                            "-fx-padding: 0;" // Remove padding
                                    );
                                }
                            }
                        };
                    }
                };

        Callback<TableColumn<Player, String>, TableCell<Player, String>> stringCellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Player, String> call(TableColumn<Player, String> param) {
                        return new TableCell<>() {
                            @Override
                            protected void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty || item == null) {
                                    setText(null);
                                    setStyle("");
                                } else {
                                    setText(item);
                                    setStyle(
                                            "-fx-border-color: black; " +
                                            "-fx-border-width: 1; " +
                                            "-fx-alignment: CENTER; " +
                                            "-fx-text-fill: black; " +
                                            "-fx-background-color: white; " +
                                            "-fx-padding: 0;" // Remove padding
                                    );
                                }
                            }
                        };
                    }
                };

        // Apply cell factories to columns
        rankColumn.setCellFactory(integerCellFactory);
        nameColumn.setCellFactory(stringCellFactory);
        roundColumn.setCellFactory(integerCellFactory);
        rowColumn.setCellFactory(integerCellFactory);
        vBucksColumn.setCellFactory(integerCellFactory);
        
        // Scrollbar styling
        table.setStyle(
                "-fx-background-color: white; " + // Set table background to white
                "-fx-border-color: black; " +
                "-fx-border-width: 3; " +
                "-fx-padding: 0; " + // Remove padding
                "-fx-cell-size: 25px; " + // Set fixed cell height
                "-fx-table-cell-border-color: black; " + // Add border to cells
                "-fx-table-header-border-color: black; " + // Add border to headers
                "-fx-scroll-bar-color: white; " + // Scrollbar track color
                "-fx-scroll-bar-thumb-color: black;" // Scrollbar thumb color
        );
        
        // Ensure the styles are applied after the table is rendered
        Platform.runLater(() -> {
            // Additional scrollbar styling
            table.lookupAll(".scroll-bar:vertical").forEach(node -> {
                node.setStyle(
                        "-fx-background-color: white; " + // Scrollbar track color
                        "-fx-scroll-bar-color: white; " + // Scrollbar track color
                        "-fx-scroll-bar-thumb-color: black; " + // Scrollbar thumb color
                        "-fx-scroll-bar-track-color: white; " + // Scrollbar track color
                        "-fx-pref-width: 4px; " + // Make the scrollbar very thin
                        "-fx-min-width: 4px; " + // Ensure the minimum width is respected
                        "-fx-max-width: 4px;" // Ensure the maximum width is respected
                );
            });
        
            // Style the scrollbar thumb (the draggable part)
            table.lookupAll(".scroll-bar:vertical .thumb").forEach(node -> {
                node.setStyle(
                        "-fx-background-color: #575a1b; " + // Scrollbar thumb color
                        "-fx-background-insets: 0, 1, 2; " +
                        "-fx-background-radius: 2em; " + // Rounded corners for the thumb
                        "-fx-pref-width: 4px; " + // Make the thumb very thin
                        "-fx-min-width: 4px; " + // Ensure the minimum width is respected
                        "-fx-max-width: 4px;" // Ensure the maximum width is respected
                );
            });
        });
    }
}