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

        switch (cmd) {
            case "-S":
                readState(args, argsOffset, printHTML);
                break;
            case "-R":
                readRooms(args, argsOffset, printHTML);
                break;
            case "-T":
                readTime(args, printHTML);
                break;
            case "-I":

                break;
            case "-A":

                break;
            case "-B":

                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public static void readState(String[] args, int argsOffset, boolean printHTML){
        Preconditions.checkArgument(args.length == argsOffset + 4);
        HistoryPrinter printer = new HistoryPrinter(LogUtils.getLogState(args[1], args[args.length - 1]));
        if (printHTML) {
            printer.printStateHTML();
        }
        else {
            printer.printState();
        }
    }

    public static void readRooms(String[] args, int argsOffset, boolean printHTML){
        int argIndex = 3 + argsOffset;
        Preconditions.checkArgument(args[argIndex].equals("-E") || args[argIndex].equals("-G"));
        argIndex ++;
        Preconditions.checkArgument(args[argIndex].matches("[a-zA-Z]+"));
        Preconditions.checkArgument(args.length == argsOffset + 6);
        HistoryPrinter printer = new HistoryPrinter(LogUtils.getLogState(args[1], args[args.length - 1]));
        if (printHTML) {
            printer.printRoomsVisitedHTML(args[argIndex]);
        }
        else {
            printer.printRoomsVisited(args[argIndex]);
        }
    }

    public static void readTime(String[] args, boolean printHTML){
        Preconditions.checkArgument(!printHTML);
        Preconditions.checkArgument(args[3].equals("-E") || args[3].equals("-G"));
        Preconditions.checkArgument(args[4].matches("[a-zA-Z]+"));
        Preconditions.checkArgument(args.length == 6);
        HistoryPrinter printer = new HistoryPrinter(LogUtils.getLogState(args[1], args[args.length - 1]));
        printer.printTimeSpentInGallery(args[4]);
    }

    public static void main(String[] args){
        try {
            read(args);
            System.exit(0);
        } catch (FileNotFoundException e) {
            System.out.println("invalid");
            System.exit(-1);
        }

    }
}
