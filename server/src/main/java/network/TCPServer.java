package network;

import commands.Command;
import utils.Executor;
import requests.Request;
import utils.CollectionHandler;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPServer {
    private ServerSocketChannel serverSocketChannel;
    private int port = 3333;
    protected SocketChannel clientSocket;
    private Logger logger = Logger.getLogger("logger");
    BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();
    BlockingQueue<Request> requestQueue = new LinkedBlockingQueue<>();
    BlockingQueue<Throwable> errorQueue = new LinkedBlockingQueue<>();

    public void start(HashMap<String, Command> map, CollectionHandler collectionHandler) {
        openServerSocket();
        ForkJoinPool pool = new ForkJoinPool();
        while (serverSocketChannel != null) {
            logger.log(Level.INFO, "Ожидание подключения...");
            try {
                this.clientSocket = serverSocketChannel.accept();
                logger.log(Level.FINER, "Подключение успешно");
                pool.execute(new RequestHandler(map, clientSocket, logger, requestQueue, errorQueue));
                pool.execute(new OutputSocketWriter(clientSocket.socket(), messageQueue, errorQueue));
                pool.execute(new Executor(map, clientSocket.socket(), requestQueue, messageQueue, errorQueue));
            } catch (IOException ioe) {
                logger.log(Level.SEVERE, "Не удалось подключиться к клиенту: ", ioe.getMessage());
            }
        }
        closeServerSocket();
        pool.shutdown();
    }

    private void openServerSocket() {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress("localhost", port));
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Ошибка при открытии соединения", e.getMessage());
        }
    }

    private void closeServerSocket() {
        try {
            serverSocketChannel.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Ошибка при закрытии соединения", e.getMessage());
        }
    }

    public Socket getClientSocket(){
        return clientSocket.socket();
    }

    private static class RequestHandler implements Runnable {
        private HashMap<String, Command> map;
        private SocketChannel clientSocket;
        private Logger logger;
        BlockingQueue<Request> requestQueue;
        BlockingQueue<Throwable> errorQueue;

        public RequestHandler(HashMap<String, Command> map, SocketChannel clientSocket, Logger logger, BlockingQueue<Request> requestQueue, BlockingQueue<Throwable> errorQueue) {
            this.map = map;
            this.clientSocket = clientSocket;
            this.logger = logger;
            this.requestQueue = requestQueue;
            this.errorQueue = errorQueue;
        }

        @Override
        public void run() {
            try {
                processRequest(map);
            } catch (Throwable e) {
                logger.log(Level.SEVERE, "Ошибка при обработке запроса: " + e.getMessage());
                try {
                    errorQueue.put(e);
                    requestQueue.put(new Request("err", "err", null, null));
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }

        private boolean processRequest(HashMap<String, Command> map) throws IOException, ClassNotFoundException {
            ObjectInput objectInput = new ObjectInputStream(clientSocket.socket().getInputStream());
            Request request = (Request) objectInput.readObject();
            // objectInput.close();
            try {
                requestQueue.put(request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
    }
}