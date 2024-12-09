package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;
import javafx.util.Callback;

public class Main extends Application implements EventHandler<ActionEvent> {

	private Stage primaryStage;
    private Scene scene_manager;
    private Scene scene_check;
	
    private TableView<RowData> marginTable = new TableView<>();
    private TableView<RowData> invTable = new TableView<>();
    private TableView<SubtotalData> totalsTable = new TableView<>();
    private TableView<InventoryData> ManagerTable = new TableView<>();
    private Button button_pay, button_remove, button_quantity, button_UPC, btn_sw_manager, 
    btn_sw_opencheck, btn_inv, btn_sales, btn_closedcheck, btn_history, btn_edit;

    public void start(Stage primaryStage) {
    	
    	// Sample entries - real UPCs temporarily given placeholder names
    	new Inventory.Entry(/*"711719541028"*/"upc1", "Sony PlayStation 5", 599.99, 6.8, 15.0, 10.0, 20.0, 5);
    	new Inventory.Entry(/*"889842640724"*/"upc2", "Microsoft Xbox Series X", 499.99, 6.2, 15.0, 10.0, 20.0, 5);
    	new Inventory.Entry(/*"815820025344"*/"upc3", "Meta Quest 3", 300.00, 2.5, 10, 10.0, 8);
    	
    	this.primaryStage = primaryStage;
        primaryStage.setTitle("Open-Check Window");
        double buttonWidth = 100;
        double buttonHeight = 50;
        double rowHeight = 25; 
        double marginHeight = (rowHeight * 2.5);
        double invHeight = (rowHeight * 17.5);
        double totalsHeight = (rowHeight * 4.5);
        double tableWidth = 500;
        double sceneWidth = tableWidth + buttonWidth + 20;
        double sceneHeight = marginHeight + invHeight + totalsHeight;

        // MARGIN TABLE
        TableColumn<RowData, String> sys = new TableColumn<>("System");
        TableColumn<RowData, String> user = new TableColumn<>("User");
        TableColumn<RowData, String> check = new TableColumn<>("Check");
        
        // INV TABLE
        TableColumn<RowData, String> item = new TableColumn<>("Item Name");
        TableColumn<RowData, String> quantity = new TableColumn<>("Quantity");
        TableColumn<RowData, Boolean> cost = new TableColumn<>("Price");
        TableColumn<RowData, String> rowNum = new TableColumn<>();
        
        // COST TABLE
        TableColumn<SubtotalData, String> labelColumn = new TableColumn<>();
        labelColumn.setCellValueFactory(new PropertyValueFactory<>("label"));      
        TableColumn<SubtotalData, String> valueColumn = new TableColumn<>();
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        
        //MANAGER PAGE TABLE
        //TableColumn<InventoryData, String> column1 = new TableColumn<>();
        //column1.setCellValueFactory(cellData -> cellData.getValue().column1Property());
        //TableColumn<InventoryData, String> column2 = new TableColumn<>();
        //column2.setCellValueFactory(cellData -> cellData.getValue().column2Property());
        //TableColumn<InventoryData, String> column3 = new TableColumn<>();
        //column3.setCellValueFactory(cellData -> cellData.getValue().column3Property());

        // HEADER/MARGIN
        sys.setCellValueFactory(new PropertyValueFactory<>("system"));
        user.setCellValueFactory(new PropertyValueFactory<>("user"));
        check.setCellValueFactory(new PropertyValueFactory<>("check"));
  
        // MIDDLE/INVENTORY
        rowNum.setCellValueFactory(new PropertyValueFactory<>("rowNumber"));
        
        // TOTALS
        


        // FOR INV
        double columnWidth = (tableWidth - rowNum.getWidth()) / 3;
        item.setPrefWidth(columnWidth);
        quantity.setPrefWidth(columnWidth);
        cost.setPrefWidth(columnWidth);

        sys.setPrefWidth(tableWidth / 3);
        user.setPrefWidth(tableWidth / 3);
        check.setPrefWidth(tableWidth / 3);

        marginTable.getColumns().addAll(sys, user, check);
        marginTable.setPrefWidth(tableWidth);
        RowData rowOne = new RowData(0, "System 1", "cbakke", "0001");
        marginTable.getItems().add(rowOne);
        marginTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        marginTable.setPlaceholder(new Label(""));
        marginTable.setPrefHeight(85);
        marginTable.setId("marginTable");

        invTable.getColumns().addAll(rowNum, item, quantity, cost);
        invTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        invTable.setPlaceholder(new Label(""));

        totalsTable.getColumns().addAll(labelColumn, valueColumn);
        totalsTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        totalsTable.setPlaceholder(new Label(""));
        totalsTable.setPrefWidth(tableWidth);
        valueColumn.setPrefWidth(tableWidth - labelColumn.getWidth());
        totalsTable.setPrefHeight(rowHeight * 3);
        ObservableList<SubtotalData> subtotalData = FXCollections.observableArrayList(
                new SubtotalData("Subtotal:", ""),
                new SubtotalData("Estimated Tax:", ""),
                new SubtotalData("Total:", "")
            );
        totalsTable.setItems(subtotalData);
        totalsTable.setFixedCellSize(rowHeight);
        totalsTable.setPrefHeight(totalsHeight);
        totalsTable.setPrefWidth(tableWidth);
        
        TableColumn<InventoryData, String> column1 = new TableColumn<>("");
        column1.setCellValueFactory(cellData -> cellData.getValue().column1Property());
        TableColumn<InventoryData, String> column2 = new TableColumn<>("");
        column2.setCellValueFactory(cellData -> cellData.getValue().column2Property());
        TableColumn<InventoryData, String> column3 = new TableColumn<>("");
        column3.setCellValueFactory(cellData -> cellData.getValue().column3Property());
        ManagerTable.getColumns().addAll(column1, column2, column3);
        ObservableList<InventoryData> InvData = FXCollections.observableArrayList(
                new InventoryData("UPC:", "", ""),
                new InventoryData("Item Name:", "", ""),
                new InventoryData("Price:", "", ""),
                new InventoryData("Weight (kg):", "", ""),
                new InventoryData("Length (cm):", "", ""),
                new InventoryData("Width (cm):", "", ""),
                new InventoryData("Height (cm):", "", ""),
                new InventoryData("In Stock:", "", ""),
                new InventoryData("Most Recent Sale:", "", "")
            );

        ObservableList<RowData> data = FXCollections.observableArrayList();
        for (int i = 1; i <= 16; i++) {
            data.add(new RowData(i, "System" + i, "User" + i, "0001"));
        }

        // FORMAT TABLES
        invTable.setItems(data);
        invTable.setFixedCellSize(rowHeight);
        invTable.setPrefHeight(invHeight);
        invTable.setPrefWidth(tableWidth);
        
        ManagerTable.setItems(InvData);
        ManagerTable.setFixedCellSize(rowHeight);
        ManagerTable.setPrefHeight(sceneHeight);
        ManagerTable.setPrefWidth(tableWidth);
        
        // DECLARE BUTTONS
        btn_sw_opencheck = new Button("Open Check");
        btn_inv = new Button("Inventory\nReport");
        btn_sales = new Button("Sales Report");
        btn_closedcheck = new Button("Closed Checks");
        btn_history = new Button("Inventory\nHistory");
        btn_edit = new Button("Edit Inventory");
        
        btn_sw_manager = new Button("Open Manager");
        button_pay = new Button("Pay");
        button_remove = new Button("Remove Item");
        button_quantity = new Button("Edit Item\nQuantity");
        button_UPC = new Button("Enter UPC");

        // FORMAT BUTTONS
        btn_sw_opencheck.setPrefSize(buttonWidth, buttonHeight);
        btn_inv.setPrefSize(buttonWidth, buttonHeight);
        btn_sales.setPrefSize(buttonWidth, buttonHeight);
        btn_closedcheck.setPrefSize(buttonWidth, buttonHeight);
        btn_history.setPrefSize(buttonWidth, buttonHeight);
        btn_edit.setPrefSize(buttonWidth, buttonHeight);

        btn_sw_manager.setPrefSize(buttonWidth, buttonHeight);
        button_pay.setPrefSize(buttonWidth, buttonHeight);
        button_remove.setPrefSize(buttonWidth, buttonHeight);
        button_quantity.setPrefSize(buttonWidth, buttonHeight);
        button_UPC.setPrefSize(buttonWidth, buttonHeight);

        // BUTTON HANDLERS
        btn_sw_opencheck.setOnAction(this);
        btn_inv.setOnAction(this);
        btn_sales.setOnAction(this);
        btn_closedcheck.setOnAction(this);
        btn_history.setOnAction(this);
        btn_edit.setOnAction(this);
        
        btn_sw_manager.setOnAction(this);
        button_pay.setOnAction(this);
        button_remove.setOnAction(this);
        button_quantity.setOnAction(this);
        button_UPC.setOnAction(this);
        
        //
        VBox btn_layout2 = new VBox(10);
        btn_layout2.getChildren().addAll(btn_sw_opencheck, btn_inv, btn_sales, btn_closedcheck, btn_history, btn_edit);
        btn_layout2.setPadding(new Insets(10,10,0,0));
        
        VBox table2_layout = new VBox();
        table2_layout.getChildren().addAll(ManagerTable);
        
        BorderPane layout2 = new BorderPane();
        layout2.setRight(btn_layout2);
        layout2.setLeft(table2_layout);
        
        // Create VBox for buttons with spacing and padding
        VBox btn_layout1 = new VBox(10); // Vertical layout for buttons with 10 units spacing
        btn_layout1.getChildren().addAll(btn_sw_manager, button_pay, button_remove, button_quantity, button_UPC);
        btn_layout1.setPadding(new Insets(10, 10, 0, 0)); // Top, Right, Bottom, Left padding

        VBox table_layout = new VBox();
        table_layout.getChildren().addAll(marginTable, invTable, totalsTable);
        // Set up the layout
        BorderPane layout1 = new BorderPane();
        layout1.setLeft(table_layout); // Add the table to the left side
        layout1.setRight(btn_layout1); // Add buttons to the right side

        // Set up the scene and show the stage
        scene_manager = new Scene(layout2, sceneWidth, sceneHeight);
        scene_check = new Scene(layout1, sceneWidth, sceneHeight); // Set the scene height to match the table height
        String css = 
    			".table-row-cell:empty { -fx-background-color: white; }\n" +
                ".table-row-cell:empty .table-cell { -fx-border-width: 0px; }" +
                "#marginTable .table-cell { -fx-alignment: CENTER; }\n";
        scene_manager.getStylesheets().add("data:," + css);
        scene_check.getStylesheets().add("data:," + css);  //Add the CSS to the scene
        primaryStage.setScene(scene_check);
        primaryStage.show();
        Pane header = (Pane) totalsTable.lookup("TableHeaderRow");
        if (header.isVisible()) {
            header.setMaxHeight(0);
            header.setMinHeight(0);
            header.setPrefHeight(0);
            header.setVisible(false);
        }
        Platform.runLater(() -> {
            // Disabling scrollbars for marginTable
            ScrollPane marginTableScroll = (ScrollPane) marginTable.lookup(".scroll-pane");
            if (marginTableScroll != null) {
                marginTableScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);  // Disable vertical scroll bar
                marginTableScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);  // Disable horizontal scroll bar
            }

            // Disabling scrollbars for invTable
            ScrollPane invTableScroll = (ScrollPane) invTable.lookup(".scroll-pane");
            if (invTableScroll != null) {
                invTableScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);  // Disable vertical scroll bar
                invTableScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);  // Disable horizontal scroll bar
            }

            // Disabling scrollbars for totalsTable
            ScrollPane totalsTableScroll = (ScrollPane) totalsTable.lookup(".scroll-pane");
            if (totalsTableScroll != null) {
                totalsTableScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);  // Disable vertical scroll bar
                totalsTableScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);  // Disable horizontal scroll bar
            }
        });
    }

    @Override
    public void handle(ActionEvent event) {
    	if (event.getSource() == btn_sw_manager) {
    	    primaryStage.setScene(scene_manager);  
    	    primaryStage.setTitle("Manager Window");
    	} else if (event.getSource() == btn_sw_opencheck) {
    	    primaryStage.setScene(scene_check); 
    	    primaryStage.setTitle("Open-Check Window");
    	}
    	if (event.getSource() == btn_inv) {
            System.out.println("Pressed Inv Report - Feature coming soon");
        }
        if (event.getSource() == btn_sales) {
            System.out.println("Pressed Sales History - Feature coming soon");
        }
        if (event.getSource() == btn_closedcheck) {
            System.out.println("Pressed Closed Checks - Feature coming soon");
        }
        if (event.getSource() == btn_history) {
            System.out.println("Pressed Inventory History - Feature coming soon");
        }
        if (event.getSource() == btn_edit) {
            System.out.println("Pressed Edit Inventory");
            
        }
        if (event.getSource() == button_remove) {
            System.out.println("Pressed Remove");
            //prompt user for which line to remove
            //get upc of that line
            //editQuantity(upc, 0);
        }
        if (event.getSource() == button_quantity) {
            System.out.println("Pressed Quantity");
            //prompt user for which line to edit, as well as quantity
            //get upc of that line
            //editQuantity(upc, quantity);
        }
        if (event.getSource() == button_UPC) {
            System.out.println("Pressed UPC");
            //prompt user to enter a upc
            //addItem(upc);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
