package network;

import ch.qos.logback.classic.Logger;
import commands.Command;
import org.slf4j.LoggerFactory;
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
import java.util.concurrent.*;

public class TCPServer {
    private ServerSocketChannel serverSocketChannel;
    private int port = 4444;
    protected SocketChannel clientSocket;
    private static final Logger logger = (Logger) LoggerFactory.getLogger(TCPServer.class);
    BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();
    BlockingQueue<Request> requestQueue = new LinkedBlockingQueue<>();
    BlockingQueue<Throwable> errorQueue = new LinkedBlockingQueue<>();
    ExecutorService executorService = Executors.newCachedThreadPool();

        //Блокируюшая очередь с ошибками


    public void start(HashMap<String, Command> map, CollectionHandler collectionHandler) {
        openServerSocket();
        while (serverSocketChannel != null) {
            logger.info("Waiting for connection");
            try {
                this.clientSocket = serverSocketChannel.accept();
                logger.info("Successfully connected");
                executorService.submit(new RequestHandler(map, clientSocket, logger, requestQueue, errorQueue));
                new Thread(new Executor(map, clientSocket.socket(), requestQueue, messageQueue, errorQueue)).start();
                executorService.submit(new OutputSocketWriter(clientSocket.socket(), messageQueue, errorQueue));
            } catch (IOException ioe) {
                logger.error("Connection error: ", ioe.getMessage());
            }
        }
        closeServerSocket();
        executorService.shutdown();
    }

        private void openServerSocket() {
            try {
                serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.bind(new InetSocketAddress("localhost", port));
            } catch (IOException e) {
                logger.error("Error opening connection"+ e.getMessage());
            }
        }

        private void closeServerSocket() {
            try {
                serverSocketChannel.close();
            } catch (IOException e) {
                logger.error("Error closing connection"+ e.getMessage());
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
                    logger.error( "Error processing request: " + e.getMessage());
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