package utils;

import commands.Command;
import requests.Request;

import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
public class Executor implements Runnable{
    HashMap<String, Command> map;
    Socket socket;
    BlockingQueue<String> messageQueue;
    BlockingQueue<Request> requestQueue;
    BlockingQueue<Throwable> errorQueue;

    public Executor(HashMap<String, Command> map,  Socket clientSocket, BlockingQueue<Request> requestQueue,
            BlockingQueue<String> messageQueue, BlockingQueue<Throwable> errorQueue ){
        this.map = map;
        this.socket = clientSocket;
        this.requestQueue = requestQueue;
        this.messageQueue = messageQueue;
        this.errorQueue = errorQueue;
    }

    @Override
    public void run(){
        Request request;
        try {
            request = requestQueue.take();
            if(map.containsKey(request.getCommandName())&&errorQueue.peek()==null){
                String toOut = map.get(request.getCommandName()).execute(request);
                messageQueue.put(toOut);}
            else {messageQueue.put("Request processing error");
            }
        } catch (Throwable e){
            try {
                errorQueue.put(e);
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }
    }
}
