// Sources:
// https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/AnchorPane.html
// https://www.tutorialspoint.com/javafx/2dshapes_rounded_rectangle.htm
// https://www.youtube.com/watch?v=7nlU3_kEjTE&list=PLZPZq0r_RZOM-8vJA3NQFZB7JroDcMwev&index=4

// Chatgpt promts-

//         "Give me a data structure to store coordinates of an item"

 //       "Drone animation to scan an anchor pane"

 //       "Method to obtain coordinates for an item"

package com.example.dashboardjavafx;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;


import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.control.ChoiceDialog;

import javafx.scene.control.TextArea;

//-------------------------ANIMATION code begin---------------------------------------------------
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Point2D;

//-------------------------ANIMATION code end---------------------------------------------------

public class DashboardController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TreeView<String> treeView;
    @FXML
    private ComboBox<String> actionComboBox;
    @FXML
    private AnchorPane visualPane;
    @FXML
    private TextArea priceArea;

    //-------------------------ANIMATION code begin---------------------------------------------------

    @FXML
    private Button startAnimationButton;
    @FXML
    private Button scanFarmButton;
    @FXML
    private Button droneToBase;

    @FXML
    private ImageView droneImageView;
    @FXML
    private TranslateTransition droneAnimation;
    private Map<TreeItem<String>, Point2D> itemCoordinates = new HashMap<>(); //data structure to store the coordinates fo an item

    private static final double TEXT_OFFSET = 10; // Adjust this value as needed



    //-------------------------ANIMATION code end---------------------------------------------------


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        // Tree structure

        TreeItem<String> rootItem = new TreeItem<>("Root");
        TreeItem<String> branchItem1 = new TreeItem<>("Barn");
        TreeItem<String> branchItem2 = new TreeItem<>("Milk-storage");
        TreeItem<String> branchItem3 = new TreeItem<>("Command-center");
        TreeItem<String> branchItem4 = new TreeItem<>("Building");

        TreeItem<String> leafItem1 = new TreeItem<>("Cow");
        TreeItem<String> leafItem2 = new TreeItem<>("Drone");
        TreeItem<String> leafItem3 = new TreeItem<>("Crop");
        TreeItem<String> leafItem4 = new TreeItem<>("Live-stock-area");
        TreeItem<String> leafItem5 = new TreeItem<>("Room");
        TreeItem<String> leafItem6 = new TreeItem<>("Milk");
        TreeItem<String> leafItem7 = new TreeItem<>("Equipment");

        TreeItem<String> petalItem = new TreeItem<>("Cattle");

        //-------------------------ANIMATION code begin---------------------------------------------------

        // Initializing and adding the drone image to the visual pane
        Image droneImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("drone.png")));
        droneImageView = new ImageView(droneImage);
        visualPane.getChildren().add(droneImageView);


        // Creating a TranslateTransition for the drone animation
        droneAnimation = new TranslateTransition(Duration.seconds(2), droneImageView);

        // Setting the animation's target coordinates
        droneAnimation.setFromX(0); // Initial drone X-coordinate
        droneAnimation.setFromY(0); // Initial drone Y-coordinate
        droneAnimation.setToX(100); // Destination X-coordinate
        droneAnimation.setToY(100); // Destination Y-coordinate


        startAnimationButton.setOnAction(event -> goToSelectedItem());
        scanFarmButton.setOnAction(event -> scanFarmContinuously());
        droneToBase.setOnAction(event -> droneToBase());

        //-------------------------ANIMATION code end---------------------------------------------------


        branchItem1.getChildren().addAll(leafItem4, leafItem1);
        branchItem2.getChildren().addAll(leafItem6);
        branchItem3.getChildren().addAll(leafItem2, leafItem3);
        branchItem4.getChildren().addAll(leafItem5, leafItem7);


        rootItem.getChildren().addAll(branchItem1, branchItem2, branchItem3, branchItem4);

        leafItem5.getChildren().addAll(petalItem);


        treeView.setRoot(rootItem);

        //combo box with commands
        actionComboBox.getItems().addAll(
                "Delete Item",
                "Change Name",
                "Change Price",
                "Change Location",
                "Change Length",
                "Change Width",
                "Change Height",
                "Add Containers",
                "Add Item",
                "Delete Containers"
        );
    } //end of initialize

    //---------------------------------ANIMATION code begin-------------------------------------------
    int gridSize = 20;
    int farmSize = 500;
    // Defining the starting coordinates
    int startX = 0;
    int startY = 0;
    Duration animationDuration = Duration.seconds(0.1); // Adjusting the duration as needed

    //method to scan the entire farm
    public void scanFarmContinuously() { //method scans the entire farm
        moveDrone(startX, startY, startX + gridSize, startY + gridSize);
    }

    //method to move drone incrementally
    public void moveDrone(int x, int y, int x1, int y1) { //method to move drone incrementally
        droneAnimation.setFromX(x); // Initial X-coordinate
        droneAnimation.setFromY(y); // Initial Y-coordinate
        droneAnimation.setToX(x1); // Destination X-coordinate
        droneAnimation.setToY(y1); // Destination Y-coordinate

        droneAnimation.setDuration(animationDuration);

        // Setting an event handler to start the next animation when this one is finished
        droneAnimation.setOnFinished(event -> {
            // Calculate the next position
            int nextX = x1 + gridSize;
            int nextY = y1;
            if (nextX >= farmSize) {
                nextX = 0;
                nextY = y1 + gridSize;
            }

            // Checking if the scanning is completed
            if (nextY >= farmSize) {
                return;
            }

            // Starting the next animation
            moveDrone(x1, y1, nextX, nextY);
        });

        // Starting the animation
        droneAnimation.play();
    }

    public void droneToBase(){ //moving drone back to base position

        droneAnimation.stop();

        droneAnimation.setToX(0);
        droneAnimation.setToY(0);

        droneAnimation.setOnFinished(event -> {
            // Handle animation completion if needed
            System.out.println("Drone returned to base.");
        });

        droneAnimation.play();
    }

//method returns the coordinates of the tree item selected
    private Point2D calculateTargetCoordinates(TreeItem<String> selectedItem) {
        selectedItem = treeView.getSelectionModel().getSelectedItem();
        if (selectedItem != null && selectedItem.isLeaf() && itemCoordinates.containsKey(selectedItem)) {
            return itemCoordinates.get(selectedItem);
        }
        if (itemCoordinates.containsKey(selectedItem)) {
            return itemCoordinates.get(selectedItem);
        } else {
                return new Point2D(1.0, 1.0); // Default coordinates for other leaf items
            }
        }

        //method to animate drone to go to selected item if it is a leaf node
    public void goToSelectedItem() {
        TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if (selectedItem != null && selectedItem.isLeaf()) { // Checking if it's a leaf node
            Point2D targetCoordinates = calculateTargetCoordinates(selectedItem);

            System.out.println("Target Coordinates: " + targetCoordinates);

            // Setting the initial position of the drone to its current position
            droneAnimation.setFromX(droneImageView.getTranslateX());
            droneAnimation.setFromY(droneImageView.getTranslateY());

            droneAnimation.setToX(targetCoordinates.getX());
            droneAnimation.setToY(targetCoordinates.getY());

            System.out.println(targetCoordinates.getX());

            droneAnimation.setOnFinished(event -> {
                // Handle animation completion if needed
                System.out.println("Animation completed");
            });

            droneAnimation.play();
        } else {
            // in case of non-leaf node, item is not selectable
            System.out.println("Selected item is not a leaf node.");
        }
    }

    //-------------------------ANIMATION code end---------------------------------------------------


    public void selectItem() {
        TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();

        if (item != null) {
            System.out.println(item.getValue());
        }
    }

    private void drawSquare(TreeItem<String> selectedItem, double x, double y, double width, double height, String name) {
        String itemValue = selectedItem.getValue();

        if (itemValue != null) {
            // Create a rectangle (square) for the item/container
            Rectangle square = new Rectangle(x, y, width, height);
            square.setStyle("-fx-fill: transparent; -fx-stroke: black; -fx-stroke-width: 2;");

            // Create a text element for the item/container name
            Text nameText = new Text(x, y - 10, itemValue); // Adjust Y position of text

            Text itemNameText = new Text(x + 5, y + 15, name); // Adjust position as needed
            itemNameText.setStyle("-fx-font-size: 12;"); // Set font size as needed

            // Create a label for additional information (you can customize this part)
            Label nameLabel = new Label(name);

            // Create a pane to hold the square and text
            Pane squarePane = new Pane();
            squarePane.getChildren().addAll(square, itemNameText);

            itemNameText.layoutXProperty().bind(squarePane.layoutXProperty().add(5));
            itemNameText.layoutYProperty().bind(squarePane.layoutYProperty().add(15));

            nameLabel.setLayoutX(x);
            nameLabel.setLayoutY(y + height + 5);

            // Add the squarePane to the AnchorPane in your UI
            /* anchorPane.getChildren().add(squarePane); */
            visualPane.getChildren().add(squarePane);

            //-------------------------ANIMATION code begin---------------------------------------------------
            // Storing the coordinates in the map for drone ANIMATION
            itemCoordinates.put(selectedItem, new Point2D(x, y));
            //-------------------------ANIMATION code end---------------------------------------------------

            // Update the TreeView with the newly added item
            TreeItem<String> newItem = new TreeItem<>(name);
            selectedItem.getChildren().add(newItem);

            itemCoordinates.put(newItem, new Point2D(x, y));

        } else {
            // Handle the case where the item's value is missing or invalid
            System.out.println("Invalid item value: " + null);
        }
    }


    public void performAction() {
        // Implement the logic for performing the selected action
        String selectedAction = actionComboBox.getValue();
        TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();

        if (selectedAction != null && selectedItem != null) {
            switch (selectedAction) {
                // Other cases...
                case "Change Length" -> changeDimension(selectedItem, "Length");
                case "Change Width" -> changeDimension(selectedItem, "Width");
                case "Change Height" -> changeDimension(selectedItem, "Height");
                case "Change Location" -> changeLocation(selectedItem);
                case "Change Name" -> changeName(selectedItem);
                case "Add Containers" -> addContainers(selectedItem);
                case "Add Item" -> enterDimensionsAndPosition(selectedItem);
                case "Delete Containers" -> deleteContainers(selectedItem);
                case "Delete Item" -> deleteItem(selectedItem);
                case "Change Price" -> changePrice(selectedItem);
                //case "Enter Dimensions and Position" -> enterDimensionsAndPosition(selectedItem);
                default -> System.out.println("Unknown action");
            }
        }
    }


    private void enterDimensionsAndPosition(TreeItem<String> selectedItem) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Container");
        dialog.setHeaderText("Enter Name for your item: ");
        dialog.setContentText("Name: ");
        Optional<String> nameResult = dialog.showAndWait();

        TextInputDialog xDialog = new TextInputDialog();
        xDialog.setTitle("Enter Dimensions and Position - X");
        xDialog.setHeaderText("Enter the X coordinate:");
        xDialog.setContentText("X Coordinate:");

        TextInputDialog yDialog = new TextInputDialog();
        yDialog.setTitle("Enter Dimensions and Position - Y");
        yDialog.setHeaderText("Enter the Y coordinate:");
        yDialog.setContentText("Y Coordinate:");

        TextInputDialog widthDialog = new TextInputDialog();
        widthDialog.setTitle("Enter Dimensions and Position - Width");
        widthDialog.setHeaderText("Enter the Width:");
        widthDialog.setContentText("Width:");

        TextInputDialog heightDialog = new TextInputDialog();
        heightDialog.setTitle("Enter Dimensions and Position - Height");
        heightDialog.setHeaderText("Enter the Height:");
        heightDialog.setContentText("Height:");

        Optional<String> xResult = xDialog.showAndWait();
        Optional<String> yResult = yDialog.showAndWait();
        Optional<String> widthResult = widthDialog.showAndWait();
        Optional<String> heightResult = heightDialog.showAndWait();

        if (xResult.isPresent() && yResult.isPresent() && widthResult.isPresent() && heightResult.isPresent()) {
            try {
                double x = Double.parseDouble(xResult.get());
                double y = Double.parseDouble(yResult.get());
                double width = Double.parseDouble(widthResult.get());
                double height = Double.parseDouble(heightResult.get());
                String name = nameResult.get();

                // Call the method to draw the square with the specified dimensions and position
                drawSquare(selectedItem, x, y, width, height, name);
            } catch (NumberFormatException e) {
                // Handle invalid input (e.g., show an error message).
                System.out.println("Invalid input. Please enter valid numeric values.");
            }
        }
    }


    private void changeLocation(TreeItem<String> selectedItem) {
        TextInputDialog xDialog = new TextInputDialog();
        xDialog.setTitle("Change Location - X");
        xDialog.setHeaderText("Enter the new X coordinate:");
        xDialog.setContentText("X Coordinate:");

        Optional<String> xResult = xDialog.showAndWait();

        TextInputDialog yDialog = new TextInputDialog();
        yDialog.setTitle("Change Location - Y");
        yDialog.setHeaderText("Enter the new Y coordinate:");
        yDialog.setContentText("Y Coordinate:");

        Optional<String> yResult = yDialog.showAndWait();

        if (xResult.isPresent() && yResult.isPresent()) {
            try {
                double x = Double.parseDouble(xResult.get());
                double y = Double.parseDouble(yResult.get());

                // Extract the original item name
                String originalName = selectedItem.getValue().split(" - ")[0];

                // Update the square's location in the visual pane
                Point2D coordinates = itemCoordinates.get(selectedItem);
                for (Node node : visualPane.getChildren()) {
                    if (node instanceof Pane) {
                        Pane squarePane = (Pane) node;
                        if (squarePane.getChildren().size() >= 2) {
                            Node square = squarePane.getChildren().get(0);
                            if (square instanceof Rectangle) {
                                Rectangle rectangle = (Rectangle) square;
                                rectangle.setX(x);
                                rectangle.setY(y);

                                // Update the itemNameText location
                                squarePane.getChildren().stream()
                                        .filter(n -> n instanceof Text)
                                        .findFirst()
                                        .ifPresent(itemNameText -> {
                                            ((Text) itemNameText).setX(x + 5);
                                            ((Text) itemNameText).setY(y + 15);
                                        });

                                break;
                            }
                        }
                    }
                }

                // Update the selected item's value with the original name
                selectedItem.setValue(originalName);

            } catch (NumberFormatException e) {
                // Handle invalid input (e.g., show an error message).
                System.out.println("Invalid coordinates. Please enter valid numeric values.");
            }
        }
    }



    private void addContainers(TreeItem<String> selectedItem) {
        // Create a dialog for selecting the container name from a list of options
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Container");
        dialog.setHeaderText("Select a container name:");
        dialog.setContentText("Container Name:");

        Optional<String> nameResult = dialog.showAndWait();

        if (nameResult.isPresent()) {
            String containerName = nameResult.get();
            TreeItem<String> container = new TreeItem<>(containerName);
            selectedItem.getChildren().add(container);

            // You can add additional logic here if needed

            System.out.println("Container added: " + containerName);
        } else {
            System.out.println("Container addition canceled.");
        }
    }

    private void addItem(TreeItem<String> selectedItem) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Item");
        dialog.setHeaderText("Enter the name for the new item:");
        dialog.setContentText("Item Name:");
        Optional<String> nameResult = dialog.showAndWait();

        TextInputDialog xDialog = new TextInputDialog();
        xDialog.setTitle("Change Location - X");
        xDialog.setHeaderText("Enter the new X coordinate:");
        xDialog.setContentText("X Coordinate:");

        Optional<String> xResult = xDialog.showAndWait();

        TextInputDialog yDialog = new TextInputDialog();
        yDialog.setTitle("Change Location - Y");
        yDialog.setHeaderText("Enter the new Y coordinate:");
        yDialog.setContentText("Y Coordinate:");

        TextInputDialog widthDialog = new TextInputDialog();
        widthDialog.setTitle("Enter Dimensions and Position - Width");
        widthDialog.setHeaderText("Enter the Width:");
        widthDialog.setContentText("Width:");

        TextInputDialog heightDialog = new TextInputDialog();
        heightDialog.setTitle("Enter Dimensions and Position - Height");
        heightDialog.setHeaderText("Enter the Height:");
        heightDialog.setContentText("Height:");

        Optional<String> yResult = yDialog.showAndWait();

        Optional<String> widthResult = widthDialog.showAndWait();
        Optional<String> heightResult = heightDialog.showAndWait();

        if (xResult.isPresent() && yResult.isPresent()) {
            try {
                double x = Double.parseDouble(xResult.get());
                double y = Double.parseDouble(yResult.get());
                double width = Double.parseDouble(widthResult.get());
                double height = Double.parseDouble(heightResult.get());
                String name = nameResult.get();


                // Call the method to draw the square with the updated location
                drawSquare(selectedItem, x, y, width, height, name);

                Optional<String> result = dialog.showAndWait();
                result.ifPresent(itemName -> {
                    TreeItem<String> item = new TreeItem<>(itemName);
                    selectedItem.getChildren().add(item);
                    // Call the method to draw the square for the new item
                    drawSquare(selectedItem, x, y, width, height, name);
                });
            } catch (NumberFormatException e) {
                // Handle invalid input (e.g., show an error message).
                System.out.println("Invalid coordinates. Please enter valid numeric values.");
            }
        }

    }


    private void deleteItem(TreeItem<String> selectedItem) {
        TreeItem<String> parent = selectedItem.getParent();
        if (parent != null) {
            Point2D coordinates = itemCoordinates.remove(selectedItem); // Remove coordinates

            parent.getChildren().remove(selectedItem);

            // Remove the corresponding square from the visual pane
            for (Node node : visualPane.getChildren()) {
                if (node instanceof Pane) {
                    Pane squarePane = (Pane) node;
                    if (squarePane.getChildren().size() >= 2) {
                        Node square = squarePane.getChildren().get(0);
                        if (square instanceof Rectangle) {
                            Rectangle rectangle = (Rectangle) square;
                            if (rectangle.getX() == coordinates.getX() && rectangle.getY() == coordinates.getY()) {
                                visualPane.getChildren().remove(squarePane);
                                break;
                            }
                        }
                    }
                }
            }

            System.out.println("Item deleted successfully.");
        } else {
            System.out.println("Parent is null, unable to delete item.");
        }
    }

    private void changeName(TreeItem<String> selectedItem) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Change Name");
        dialog.setHeaderText("Enter the new name:");
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newName -> {
            selectedItem.setValue(newName);

            // Update the corresponding label in the visual pane
            Point2D coordinates = itemCoordinates.get(selectedItem);
            for (Node node : visualPane.getChildren()) {
                if (node instanceof Pane) {
                    Pane squarePane = (Pane) node;
                    if (squarePane.getChildren().size() >= 2) {
                        Node itemNameText = squarePane.getChildren().get(1);
                        if (itemNameText instanceof Text) {
                            ((Text) itemNameText).setText(newName);
                            break;
                        }
                    }
                }
            }
        });
    }


   /* private void changePrice(TreeItem<String> selectedItem) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Change Price");
        dialog.setHeaderText("Enter the new price:");
        dialog.setContentText("Price:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newPrice -> selectedItem.setValue(newPrice));
    }
*/
    
   /* private void changePrice(TreeItem<String> selectedItem) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Change Price");
        dialog.setHeaderText("Enter the new price:");
        dialog.setContentText("Price:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newPrice -> {
            try {
                double enteredPrice = Double.parseDouble(newPrice);

                // Perform addition to the entered price
                double additionValue = 0.00; // You can adjust this value as needed
                double calculatedPrice = enteredPrice + additionValue;

                // Display the calculated price in the priceArea TextArea
                priceArea.setText("Entered Price: " + enteredPrice + "\nAdded Value: " + additionValue + "\nCalculated Price: " + calculatedPrice);
            } catch (NumberFormatException e) {
                // Handle invalid input (e.g., show an error message).
                System.out.println("Invalid input. Please enter a valid numeric value.");
            }
        });
    }*/
    
    
  /*  private void changePrice(TreeItem<String> selectedItem) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Change Price");
        dialog.setHeaderText("Enter prices to add (separate with commas):");
        dialog.setContentText("Prices:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(prices -> {
            try {
                String[] priceValues = prices.split(",");
                double sum = 0.0;

                for (String priceValue : priceValues) {
                    double enteredPrice = Double.parseDouble(priceValue.trim());
                    sum += enteredPrice;
                }

                // Display the entered prices and their sum in the priceArea TextArea
                priceArea.setText("Entered Prices: " + prices + "\nSum of Prices: " + sum);
            } catch (NumberFormatException e) {
                // Handle invalid input (e.g., show an error message).
                System.out.println("Invalid input. Please enter valid numeric values separated by commas.");
            }
        });
    }*/
    
    
    private Map<String, Double> priceMap = new HashMap<>(); // Map to store item names and their prices

    private void changePrice(TreeItem<String> selectedItem) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Change Price");
        dialog.setHeaderText("Enter prices for selected items (separate with commas):");
        dialog.setContentText("Prices:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(prices -> {
            try {
                String[] priceValues = prices.split(",");
                for (String priceValue : priceValues) {
                    double enteredPrice = Double.parseDouble(priceValue.trim());
                    priceMap.put(selectedItem.getValue(), enteredPrice);
                }

                // Calculate the total sum of prices
                double totalSum = priceMap.values().stream().mapToDouble(Double::doubleValue).sum();

                // Display the names of selected items, their corresponding prices, and the total sum in the priceArea TextArea
                StringBuilder displayText = new StringBuilder();
                displayText.append("Item Names and Prices:\n");
                for (Map.Entry<String, Double> entry : priceMap.entrySet()) {
                    displayText.append(entry.getKey()).append(": $").append(entry.getValue()).append("\n");
                }
                displayText.append("\nTotal Sum of Prices: $").append(totalSum);
                priceArea.setText(displayText.toString());
            } catch (NumberFormatException e) {
                // Handle invalid input (e.g., show an error message).
                System.out.println("Invalid input. Please enter valid numeric values separated by commas.");
            }
        });
    }





    // Implement similar input logic for other functions


    private void changeDimension(TreeItem<String> selectedItem, String dimension) {
        // Implement dimension change logic
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Change " + dimension);
        dialog.setHeaderText("Enter new " + dimension + ":");
        dialog.setContentText("New " + dimension + ":");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(newValue -> {
            // Check if the input is valid (e.g., it's a number)
            try {
                double newDimension = Double.parseDouble(newValue);

                // Update the selected item's dimension (you should have a data structure to store these values)
                String currentText = selectedItem.getValue();
                String updatedText;

                if (currentText != null && !currentText.isEmpty()) {
                    // Extract existing dimensions
                    String[] parts = currentText.split(", ");
                    String updatedPart = dimension + ": " + newDimension;

                    // Update the dimension
                    for (int i = 0; i < parts.length; i++) {
                        if (parts[i].startsWith(dimension)) {
                            parts[i] = updatedPart;
                            break;
                        }
                    }

                    // Reconstruct the updated text
                    updatedText = String.join(", ", parts);
                } else {
                    updatedText = dimension + ": " + newDimension;
                }

                // Update the selected item's value
                selectedItem.setValue(updatedText);

                // Perform any other necessary actions, e.g., update the underlying data structure.
                updateSquareDimensions(selectedItem, newDimension, dimension);
            } catch (NumberFormatException e) {
                // Handle invalid input (e.g., show an error message).
                System.out.println("Invalid input. Please enter valid numeric values.");
            }
        });
    }

    private void updateSquareDimensions(TreeItem<String> selectedItem, double newDimension, String dimension) {
        Point2D coordinates = itemCoordinates.get(selectedItem);

        // Find the corresponding square in the visual pane and update its dimensions
        for (Node node : visualPane.getChildren()) {
            if (node instanceof Pane) {
                Pane squarePane = (Pane) node;
                if (squarePane.getChildren().size() >= 2) {
                    Node square = squarePane.getChildren().get(0);
                    if (square instanceof Rectangle) {
                        Rectangle rectangle = (Rectangle) square;
                        if (rectangle.getX() == coordinates.getX() && rectangle.getY() == coordinates.getY()) {
                            if (dimension.equals("Width")) {
                                rectangle.setWidth(newDimension);
                            } else if (dimension.equals("Height")) {
                                rectangle.setHeight(newDimension);
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    private void changeLength(TreeItem<String> selectedItem) {
        changeDimension(selectedItem, "Length");
    }

    private void changeWidth(TreeItem<String> selectedItem) {
        changeDimension(selectedItem, "Width");
    }

    private void changeHeight(TreeItem<String> selectedItem) {
        changeDimension(selectedItem, "Height");
    }


    private void deleteContainers(TreeItem<String> selectedItem) {
        // Remove corresponding squares from the visual pane
        for (TreeItem<String> child : selectedItem.getChildren()) {
            Point2D coordinates = itemCoordinates.remove(child);
            for (Node node : visualPane.getChildren()) {
                if (node instanceof Pane) {
                    Pane squarePane = (Pane) node;
                    if (squarePane.getChildren().size() >= 2) {
                        Node square = squarePane.getChildren().get(0);
                        if (square instanceof Rectangle) {
                            Rectangle rectangle = (Rectangle) square;
                            if (rectangle.getX() == coordinates.getX() && rectangle.getY() == coordinates.getY()) {
                                visualPane.getChildren().remove(squarePane);
                                break;
                            }
                        }
                    }
                }
            }
        }

        // Clear all children of the selected item
        selectedItem.getChildren().clear();

        // Remove the containers from the TreeView
        TreeItem<String> parent = selectedItem.getParent();
        if (parent != null) {
            parent.getChildren().remove(selectedItem);
            System.out.println("Containers deleted successfully.");
        } else {
            System.out.println("Parent is null, unable to delete containers.");
        }
    }




}//end of controller class



