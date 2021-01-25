package Lesson_2.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private ConsoleServer server;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private String nick;

    public String getNick() {
        return nick;
    }

    public ClientHandler(ConsoleServer server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String str = in.readUTF();
                            if (str.startsWith("/auth")) {
                                String[] tokens = str.split(" ");
                                String networkNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                                if (networkNick != null) {
                                    sendMsg("/authok");
                                    nick = networkNick;
                                    server.subscribe(ClientHandler.this);
                                    String nickList = "/clientlist" + server.nickList();
                                    server.broadcastMsg (nickList);
                                    break;
                                } else {
                                    sendMsg("Неверный логин/пароль");
                                }
                            }
                            if (str.startsWith("/reg")) {
                                String[] tokens = str.split(" ");
                                boolean regOk = AuthService.addUser(tokens[1], tokens[2],tokens[3]);

                                if (regOk) {
                                    sendMsg("/regOK");
                                    server.subscribe(ClientHandler.this);
                                    nick = tokens[3];
                                   String nickList = "/clientlist" + server.nickList();
                                    server.broadcastMsg (nickList);
                                    System.out.println(nick + tokens[3] );
                                    break;
                                } else {
                                    sendMsg("Ошибка регистрации, попробуйте другое имя");
                                }
                            }
                        }

                        while (true) {
                            String str = in.readUTF();
                            if (str.equals("/end")) {
                                out.writeUTF("/serverClosed");
                                server.broadcastMsg( nick + " покинул чат");
                                break;
                            }
                            if (str.startsWith("/rename")) {
                                System.out.println(str);
                                String[] token = str.split(" ");
                               String changeNick =  AuthService.changeNick(nick, token[1]);
                               if (changeNick == null) {
                                   sendMsg("Ник " + token[1] + " занят");
                                   continue;
                               }

                                   server.broadcastMsg(nick + " изменил ник на " + token[1]);
                                   nick = changeNick;
                                   String nickList = "/clientlist" + server.nickList();
                                   server.broadcastMsg(nickList);
                                   continue;

                            }
                            System.out.println("Client " + str);
                            server.broadcastMsg(nick + ": " + str);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        server.unsubscribe(ClientHandler.this);
                        String nickList = "/clientlist" + server.nickList();
                        server.broadcastMsg (nickList);
                    }
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
