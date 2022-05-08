package it.polimi.ingsw.Server;
import java.util.Observable;
import java.util.Observer;

/* MessageReceiver class observes ClientConnection so that
* each time the clientConnection receives a message from the server,
* observers can be updated and handle the message via the methods in this class. */


/* quando mi arriva un messaggio, lo spacchetto e poi lo mando alla virtualView;
* in questo modo la virtualView con il messaggio pronto può notificare il controller
* e il controller modificherà il model; poi il model dopo essersi modificato notificherà la view.*/
public class MessageReceiver implements Observer {

    private VirtualView view;

    public MessageReceiver(VirtualView virtualView){
        this.view = virtualView;
    }


    /* this class asks the VirtualView to notify the controller */
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Message received from client");
        try{
            if(! (arg instanceof  UserMessage)){
                //send error message to virtualView
            }

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}