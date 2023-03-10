import org.zeromq.ZMsg;
import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;



public abstract class Client {
    private static final int REPLYTIMEOUT = 5000;

    protected ZMQ.Socket socketZMQ;
    protected String id;
    protected String socketEndpoint;
    protected ZContext zContext;

    public Client(ZContext zContext, String id, String socketEndpoint) {
        this.id = id;
        this.zContext = zContext;
        this.socketEndpoint = socketEndpoint;
        this.setup();

        int connectionTries=0;
        while(!this.connect()&&connectionTries<4){
            try {
                Thread.sleep(1000);
                connectionTries++;
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


    }

    protected void setup() {
        this.socketZMQ = zContext.createSocket(SocketType.REQ);
        this.socketZMQ.setIdentity(id.getBytes(ZMQ.CHARSET));
        this.socketZMQ.setReceiveTimeOut(REPLYTIMEOUT);
        this.socketZMQ.setSendTimeOut(REPLYTIMEOUT);
        this.socketZMQ.setReqRelaxed(true);
        this.socketZMQ.setLinger(0);
    }

    public ZMsg sendReceive(Message messageToSend){

        ZMsg message = messageToSend.createMessage();
        
        int tries = 0;
        ZMsg reply;
        reply = null;


        while (reply == null && tries<4) {
            System.out.println(this.id+": " + "Sending a " + messageToSend.getMessageType().toString() + " to the server");
            while (!message.send(this.socketZMQ) && tries<4){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println(this.id+": "+"Couldn't send message, trying again in 1 sec");
                tries++;
            };
            reply = this.receiveMessage();
            if(reply==null) System.out.println(this.id+": "+"Timeout, sending message again");
            tries++;
        }
        return reply;
    }

    public boolean connect() {
        return this.socketZMQ.connect("tcp://" +this.socketEndpoint);
    }

    public ZMsg receiveMessage(){
        
        ZMsg replyZMsg = null;
        try {
            replyZMsg=ZMsg.recvMsg(this.socketZMQ);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return replyZMsg;
    }

    public void closeSocket(){
        socketZMQ.disconnect("tcp://" +this.socketEndpoint);
        socketZMQ.close();
    }
}