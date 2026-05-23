package com.example.pos_system.controller;

import com.example.pos_system.factory.AuthWindowFactory;
import com.example.pos_system.model.Order;
import com.example.pos_system.model.OrderItem;
import com.example.pos_system.model.Product;
import com.example.pos_system.model.UserAccount;
import com.example.pos_system.repository.OrderRepository;
import com.example.pos_system.repository.ProductRepository;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PosDashboardController {

    @FXML private Label lblUsername;
    @FXML private Label lblTotal;
    
    @FXML private TableView<Product> productsTable;
    @FXML private TableColumn<Product, String> colProductName;
    @FXML private TableColumn<Product, BigDecimal> colProductPrice;
    @FXML private TableColumn<Product, Integer> colProductStock;
    
    @FXML private TableView<OrderItem> cartTable;
    @FXML private TableColumn<OrderItem, String> colCartName;
    @FXML private TableColumn<OrderItem, Integer> colCartQty;
    @FXML private TableColumn<OrderItem, BigDecimal> colCartSubtotal;
    
    @FXML private Spinner<Integer> spinnerQuantity;

    private UserAccount currentUser;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private ObservableList<Product> productsList;
    private ObservableList<OrderItem> cartList;
    private BigDecimal currentTotal = BigDecimal.ZERO;

    public void initializeData(UserAccount user) {
        this.currentUser = user;
        lblUsername.setText("Welcome, " + user.getUsername());
        
        productRepository = new ProductRepository();
        orderRepository = new OrderRepository();
        
        setupTables();
        loadProducts();
        
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        spinnerQuantity.setValueFactory(valueFactory);
    }

    private void setupTables() {
        colProductName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        colProductPrice.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPrice()));
        colProductStock.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getStock()));

        colCartName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProductName()));
        colCartQty.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getQuantity()));
        colCartSubtotal.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getSubtotal()));

        cartList = FXCollections.observableArrayList();
        cartTable.setItems(cartList);
    }

    private void loadProducts() {
        productsList = FXCollections.observableArrayList(productRepository.getAllProducts());
        productsTable.setItems(productsList);
    }

    @FXML
    private void handleAddToCart(ActionEvent event) {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select a product first.");
            return;
        }

        int quantity = spinnerQuantity.getValue();
        if (quantity > selectedProduct.getStock()) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Insufficient stock available.");
            return;
        }

        BigDecimal subtotal = selectedProduct.getPrice().multiply(BigDecimal.valueOf(quantity));
        
        // Check if product already in cart
        boolean found = false;
        for (OrderItem item : cartList) {
            if (item.getProductId() == selectedProduct.getId()) {
                item.setQuantity(item.getQuantity() + quantity);
                item.setSubtotal(item.getSubtotal().add(subtotal));
                found = true;
                break;
            }
        }
        
        if (!found) {
            OrderItem newItem = new OrderItem(0, 0, selectedProduct.getId(), quantity, subtotal);
            newItem.setProductName(selectedProduct.getName());
            cartList.add(newItem);
        }
        
        cartTable.refresh();
        updateTotal();
    }

    @FXML
    private void handleRemoveFromCart(ActionEvent event) {
        OrderItem selected = cartTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            cartList.remove(selected);
            updateTotal();
        }
    }

    private void updateTotal() {
        currentTotal = BigDecimal.ZERO;
        for (OrderItem item : cartList) {
            currentTotal = currentTotal.add(item.getSubtotal());
        }
        lblTotal.setText("$" + currentTotal.toString());
    }

    @FXML
    private void handleCheckout(ActionEvent event) {
        if (cartList.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Cart is empty.");
            return;
        }

        Order order = new Order(0, currentUser.getId(), currentTotal);
        List<OrderItem> items = new ArrayList<>(cartList);

        boolean success = orderRepository.createOrder(order, items);
        
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Checkout completed successfully!");
            cartList.clear();
            updateTotal();
            loadProducts(); // Refresh stock
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Checkout failed. Please check stock levels.");
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(AuthWindowFactory.createLoginScene());
            stage.setTitle("Login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
