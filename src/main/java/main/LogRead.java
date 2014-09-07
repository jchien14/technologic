package main;

import com.google.common.base.Preconditions;
import history.GalleryState;
import history.HistoryPrinter;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by kevin on 9/7/14.
 */
public final class LogRead {

    public static void read(String[] args) throws FileNotFoundException{
        // Validate token and file
        Preconditions.checkArgument(args[0].equals("-K"));
        Preconditions.checkArgument(args[1].matches("[a-zA-Z0-9]+"));
        Preconditions.checkArgument(args[args.length - 1].matches("[a-zA-Z0-9_]+"));

        String path = args[args.length - 1];
        // check file existence
        if (!Files.exists(Paths.get(path))){
            throw new FileNotFoundException();
        }
        LogUtils.validateToken(args[1], path);

        boolean printHTML = false;
        String cmd = args[2];
        int argsOffset = 0;
        if (cmd.equals("-H")){
            printHTML = true;
            cmd = args[3];
            argsOffset = 1;
        }

        if (cmd.equals("-S")){
            readState(args, argsOffset, printHTML);
        } else if (cmd.equals("-R")) {

        }



    }

    public static void readState(String[] args, int argsOffset, boolean printHTML){
        Preconditions.checkArgument(args.length == argsOffset + 4);
        HistoryPrinter printer = new HistoryPrinter(LogUtils.getLogState(args[1], args[args.length - 1]));
        printer.printState();
    }

    public static void main(String[] args){

    }
}
