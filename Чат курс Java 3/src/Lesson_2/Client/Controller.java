package Lesson_2.Client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Controller {

    @FXML
    TextArea chatArea;

    @FXML
    TextField msgField;

    @FXML
    HBox bottomPanel;

    @FXML
    HBox upperPanel;

    @FXML
    HBox upperPanel1;

    @FXML
    TextField loginField;

    @FXML
    PasswordField passwordField;

    @FXML
    TextField loginFieldReg;

    @FXML
    PasswordField passwordFieldReg;

    @FXML
    VBox clientVbox;

    @FXML
    ListView<String> clientList;

    @FXML
    TextField nickChange;

    @FXML
    TextField nickname;

    Socket socket;

    DataInputStream in;
    DataOutputStream out;

    final String IP_ADDRESS = "localhost";
    final int PORT = 8189;

    private boolean isAuthorized;

    public void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
        if (!isAuthorized) {
            upperPanel.setVisible(true);
            upperPanel.setManaged(true);
            upperPanel1.setManaged(true);
            bottomPanel.setVisible(false);
            bottomPanel.setManaged(true);
            clientVbox.setVisible(false);
            clientVbox.setManaged(false);
        } else {
            upperPanel.setVisible(false);
            upperPanel.setManaged(false);
            upperPanel1.setManaged(false);
            bottomPanel.setVisible(true);
            bottomPanel.setManaged(true);
            clientVbox.setVisible(true);
            clientVbox.setManaged(true);
        }
    }

    public void connect() {
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String str = in.readUTF();
                            if (str.startsWith("/authok") || str.startsWith("/regOK")) {
                                setAuthorized(true);
                                break;
                            } else {
                                chatArea.appendText(str + "\n");
                            }
                        }

                        while (true) {
                            String str = in.readUTF();
                            if (str.equals("serverclosed")) break;

                            if (str.startsWith("/clientlist")) {
                                String[] tokens = str.split(" ");

                                Platform.runLater(() -> {
                                clientList.getItems().clear();
                                for (int i = 1; i < tokens.length; i++) {
                                    clientList.getItems().add(tokens[i]);
                                }
                                });
                                continue;
                            }



                            chatArea.appendText(str + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        setAuthorized(false);
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg() {
        try {
            out.writeUTF(msgField.getText());
            msgField.clear();
            msgField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tryToAuth(ActionEvent actionEvent) {
        if (socket == null || socket.isClosed()) {
            connect();
        }
        try {
            out.writeUTF("/auth " + loginField.getText() + " " + passwordField.getText());
            loginField.clear();
            passwordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toReg() {
        if (socket == null || socket.isClosed()) {
            connect();
        }
        try {
            out.writeUTF("/reg " + loginFieldReg.getText().trim() + " " + passwordFieldReg.getText().trim() + " " + nickname.getText().trim());
            loginField.clear();
            passwordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeNick () {
        if (socket == null || socket.isClosed()) {
            connect();
        }
        try {
            out.writeUTF("/rename " + nickChange.getText().trim());
            nickChange.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exit(ActionEvent actionEvent) {
        try {
            out.writeUTF("/end");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
