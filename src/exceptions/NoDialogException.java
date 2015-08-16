package exceptions;

import guicomponents.Dialog;
import main.ContextController;

/**
 * Created by stanners on 16/08/2015.
 */
public class NoDialogException extends Exception {

    public static int BAD_CONTEXT = 0;
    public static int NULL_DIALOG = 1;

    public NoDialogException(int context, Dialog dialog){
        super(getMessageNDE(context, dialog));
    }

    public static String getMessageNDE(int context, Dialog dialog){
        String message = "NoDialogException:";

        if(ContextController.DIALOG != context){
            message = message + " Context was not set to dialog.";
        }

        if(dialog == null){
            message = message + " Dialog was null.";
        }

        return message;

    }

}
