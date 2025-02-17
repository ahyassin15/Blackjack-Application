package main.java.com.blackjack.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import loginassignment.FileOperations.ReadFile;

public class LeaderboardController {

  Scene gameScene;

  @FXML
  private TableView<LeaderboardEntry> table;

  @FXML
  private TableColumn<LeaderboardEntry, Integer> rankColumn;

  @FXML
  private TableColumn<LeaderboardEntry, String> usernameColumn;

  @FXML
  private TableColumn<LeaderboardEntry, Integer> balanceColumn;

  public void initialize() {
    // Sort the data using bubble sort
    HashMap<String, Integer> sortedScores = bubbleSort(ReadFile.getPlayerScores());

    // Set the cell value factories for the columns
    rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
    usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
    balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

    // Create an ObservableList of LeaderboardEntry objects
    ObservableList<LeaderboardEntry> leaderboardData = FXCollections.observableArrayList();

    // Populate the ObservableList from the sorted scores HashMap
    int rank = 1;
    for (Map.Entry<String, Integer> entry : sortedScores.entrySet()) {
      // Get the Key, Value per entry and add it into leaderboardData
      String username = entry.getKey();
      int balance = entry.getValue();
      leaderboardData.add(new LeaderboardEntry(rank, username, balance));
      rank++;
    }

    // Set the ObservableList as the data source for the TableView
    table.setItems(leaderboardData);
  }
  
  public static HashMap<String, Integer> bubbleSort(HashMap<String, Integer> scores) {
    // Convert the input HashMap to a List of Map entries
    List<Map.Entry<String, Integer>> entryList = new ArrayList<>(scores.entrySet());

    int n = entryList.size();
    boolean swapped;

    // Perform bubble sort algorithm that we did in class
    for (int i = 0; i < n - 1; i++) {
      swapped = false;
      for (int j = 0; j < n - i - 1; j++) {
        // Compare values of adjacent entries in the list
        if (entryList.get(j).getValue() < entryList.get(j + 1).getValue()) {
          // Swap entries j and j + 1
          Collections.swap(entryList, j, j + 1);
          swapped = true;
        }
      }
      // If no entries were swapped in the inner loop, the list is already sorted
      if (!swapped) {
        break;
      }
    }

    // Create a new sorted HashMap based on the sorted entry list
    HashMap<String, Integer> sortedScores = new LinkedHashMap<>();
    for (Map.Entry<String, Integer> entry : entryList) {
      sortedScores.put(entry.getKey(), entry.getValue());
    }
    
    return sortedScores;
  }

  public void getScene(Stage stage) {
    gameScene = stage.getScene();
  }

}
