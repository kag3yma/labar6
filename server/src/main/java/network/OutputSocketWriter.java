package network;


import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

//Насколько я понял, здесь нужно применить ForkJoinPool, но я хуй знает, как им пользоваться, поэтому тут темка из лабы Сексова, нужно переделать

public class OutputSocketWriter implements Runnable {
    private final Socket socket;
    private String msg;
    BlockingQueue<String> messageQueue;
    BlockingQueue<Throwable> errorQueue;


    public OutputSocketWriter(Socket socket, BlockingQueue<String> messageQueue, BlockingQueue<Throwable> errorQueue) {
        this.socket = socket;
        this.messageQueue = messageQueue;
        this.errorQueue = errorQueue;
    }
    @Override
    public void run(){
        try {
            this.msg = messageQueue.take();
            if(!socket.isClosed()){
                PrintWriter outputStream = new PrintWriter(socket.getOutputStream(), true);
                outputStream.println(msg);
            }else{
                System.out.println("Socket is closed");
            }
        }catch (Throwable e){
            System.out.println(e.getMessage());
        }
    }
}
