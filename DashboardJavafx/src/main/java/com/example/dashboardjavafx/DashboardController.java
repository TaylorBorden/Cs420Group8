// Sources:
// https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/AnchorPane.html
// https://www.tutorialspoint.com/javafx/2dshapes_rounded_rectangle.htm
// https://www.youtube.com/watch?v=7nlU3_kEjTE&list=PLZPZq0r_RZOM-8vJA3NQFZB7JroDcMwev&index=4

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
        droneAnimation.setToX(0);
        droneAnimation.setToY(0);
        droneAnimation.play();
    }

//method returns the coordinates of the tree item selected
    private Point2D calculateTargetCoordinates(TreeItem<String> selectedItem) {
        selectedItem = treeView.getSelectionModel().getSelectedItem();
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

            // Setting the initial position of the drone to its current position
            droneAnimation.setFromX(droneImageView.getTranslateX());
            droneAnimation.setFromY(droneImageView.getTranslateY());

            droneAnimation.setToX(targetCoordinates.getX());
            droneAnimation.setToY(targetCoordinates.getY());

            System.out.println(targetCoordinates.getX());

            droneAnimation.setOnFinished(event -> {
                // Handle animation completion if needed
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

            // Create a label for additional information (you can customize this part)
            Label nameLabel = new Label(name);

            // Create a pane to hold the square and text
            Pane squarePane = new Pane();
            squarePane.getChildren().addAll(square, nameText, nameLabel);

            nameLabel.setLayoutX(x);
            nameLabel.setLayoutY(y + height + 5);

            // Add the squarePane to the AnchorPane in your UI
            /* anchorPane.getChildren().add(squarePane); */

            visualPane.getChildren().add(square);

//-------------------------ANIMATION code begin---------------------------------------------------
// Storing the coordinates in the map for drone ANIMATION

            itemCoordinates.put(selectedItem, new Point2D(x, y));

//-------------------------ANIMATION code end---------------------------------------------------

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
                case "Add Containers" -> enterDimensionsAndPosition(selectedItem);
                case "Add Item" -> enterDimensionsAndPosition(selectedItem);
                case "Delete Containers" -> deleteContainers(selectedItem);
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

                String newLocation = "X: " + x + ", Y: " + y;
                selectedItem.setValue(newLocation);

                // Call the method to draw the square with the updated location
                drawSquare(selectedItem, x, y, width, height, "");
            } catch (NumberFormatException e) {
                // Handle invalid input (e.g., show an error message).
                System.out.println("Invalid coordinates. Please enter valid numeric values.");
            }
        }
    }


    private void addContainers(TreeItem<String> selectedItem) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Container");
        dialog.setHeaderText("Enter the name for the new container:");
        dialog.setContentText("Container Name:");
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


            } catch (NumberFormatException e) {
                // Handle invalid input (e.g., show an error message).
                System.out.println("Invalid coordinates. Please enter valid numeric values.");
            }
        }

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(containerName -> {
            TreeItem<String> container = new TreeItem<>(containerName);
            selectedItem.getChildren().add(container);

            // Call the method to draw the square for the new container
            drawSquare(selectedItem, 0, 0, 0, 0, "");
        });
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
        // Implement item deletion logic
        TreeItem<String> parent = selectedItem.getParent();
        if (parent != null) {
            parent.getChildren().remove(selectedItem);
        }
    }

    private void changeName(TreeItem<String> selectedItem) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Change Name");
        dialog.setHeaderText("Enter the new name:");
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newName -> selectedItem.setValue(newName));
    }

    private void changePrice(TreeItem<String> selectedItem) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Change Price");
        dialog.setHeaderText("Enter the new price:");
        dialog.setContentText("Price:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newPrice -> selectedItem.setValue(newPrice));
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
                selectedItem.setValue(dimension + ": " + newDimension);

                // Perform any other necessary actions, e.g., update the underlying data structure.
            } catch (NumberFormatException e) {
                // Handle invalid input (e.g., show an error message).
            }
        });
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
        // Implement logic to delete containers under the selected item
        selectedItem.getChildren().clear();
    }


}//end of controller class



