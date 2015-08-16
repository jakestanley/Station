package guicomponents;

import main.Display;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;

/**
 * Created by stanners on 26/05/2015.
 */
public class MessageBox extends GuiStatic { // TODO consider that update may be redundant

    public static final int MAX_MESSAGES = 5;
    public static final int MAX_MESSAGE_AGE_SECONDS = 5;
    public static int MAX_MESSAGE_AGE = Display.FRAME_RATE * MAX_MESSAGE_AGE_SECONDS;

    private ArrayList<MessagePanel> messages;

    public MessageBox(){
        super(Display.LEFT_COLUMN_WIDTH, Display.getMessageBoxY(), Display.RIGHT_COLUMN_WIDTH, Display.MESSAGE_BOX_HEIGHT);
        messages = new ArrayList<MessagePanel>();
    }

    @Override
    public void init() {

    }

    public void addMessage(String message){ // TODO animations for new messages and pushing messages down?
        MessagePanel messagePanel = new MessagePanel(message); // TODO CONSIDER renaming to message?
        messages.add(0, messagePanel);
        if(messages.size() > MAX_MESSAGES){
            messages.remove(MAX_MESSAGES);
        }
    }

    @Override
    public void update() {
        for (int i = 0; i < messages.size(); i++) {
            MessagePanel next =  messages.get(i);
            next.increaseAge();
            if(next.getAge() > MAX_MESSAGE_AGE){
                messages.remove(i);
            }
        }
    }

    public void renderBody(Graphics screen) {

        for (int i = 0; i < messages.size(); i++) {
            String message = messages.get(i).getMessage();
            screen.drawString(message, x + Display.MARGIN, y + Display.MARGIN + (i * Display.TEXT_PANEL_HEIGHT)); // TODO check hint is not null
        }
    }

    @Override
    public void renderContent(Graphics screen) {

    }

    @Override
    public void widgetClicked(int index) {

    }
}
