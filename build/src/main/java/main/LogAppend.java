package main;

import com.google.common.base.Preconditions;
import history.GalleryState;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by kevin on 9/7/14.
 */
public final class LogAppend {

    public static void append(String[] args) throws IOException {
        if (args[0].equals("-B")){
            Preconditions.checkArgument(args.length == 2);
            batchAppend(args[1]);
        }
        else{
            singleAppend(args);
        }
    }

    //TODO reads in command args line by line from file
    public static void batchAppend(String file) throws IOException {
        HashMap<String, GalleryState> states = new HashMap<String, GalleryState>();

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            String[] args = line.split(" ");
            //baby version of the full try catch
            try {
                checkAppendArgs(args);
                String path;
                int room = -1;
                if (args.length == 8){
                    path = args[7];
                }
                else{
                    room = Integer.parseInt(args[8]);
                    path = args[9];
                }

                LogUtils.validateToken(args[3], path);
                if(!states.containsKey(path)) {
                    states.put(path, LogUtils.getLogState(args[3], path));
                }

                int time = Integer.parseInt(args[1]);
                boolean isEmployee = args[4].equals("-E");
                String name = args[5];
                boolean arrival = args[6].equals("-A");

                appendEvent(time, isEmployee, name, arrival, room, states.get(path));
            }
            catch (Exception e){
                System.out.println("invalid");
            }
        }
        br.close();
        //TODO do whatever to write out the state to files
    }

    public static void singleAppend(String[] args){
        checkAppendArgs(args);
        GalleryState state;
        if (args.length == 8){
            LogUtils.validateToken(args[3], args[7]);
            state = LogUtils.getLogState(args[3], args[7]);
        }
        else {
            LogUtils.validateToken(args[3],args[9]);
            state = LogUtils.getLogState(args[3], args[9]);
        }

        int time = Integer.parseInt(args[1]);
        boolean isEmployee = args[4].equals("-E");
        String name = args[5];
        boolean arrival = args[6].equals("-A");
        int room = -1;
        if (args.length == 10){
            room = Integer.parseInt(args[8]);
        }
        appendEvent(time, isEmployee, name, arrival, room, state);
        //TODO do whatever to write out the state to file
    }

    // update state from a single event
    public static void appendEvent(int time, boolean isEmployee, String name,
                                   boolean arrival, int room, GalleryState state){
        if (room >= 0) {
            if (arrival) {
                state.personRoomEnter(time, name, room);
            }
            else {
                state.personRoomLeave(time, name, room);
            }
        }
        else {
            if (isEmployee) {
                if (arrival) {
                    state.employeeGalleryArrival(time, name);
                }
                else {
                    state.employeeGalleryDeparture(time, name);
                }
            }
            else {
                if (arrival) {
                    state.guestGalleryArrival(time, name);
                }
                else {
                    state.guestGalleryDeparture(time, name);
                }
            }
        }
    }

    // validate args when args[0] != -B
    private static void checkAppendArgs(String[] args) {
        Preconditions.checkArgument(args[0].equals("-T"));
        Preconditions.checkArgument(Integer.parseInt(args[1]) >= 0);
        Preconditions.checkArgument(args[2].equals("-K"));
        Preconditions.checkArgument(args[3].matches("[a-zA-Z0-9]+"));
        Preconditions.checkArgument(args[4].equals("-E") || args[4].equals("-G"));
        Preconditions.checkArgument(args[5].matches("[a-zA-Z]+"));
        Preconditions.checkArgument(args[6].equals("-A") || args[6].equals("-L"));

        if (args[7].equals("-R")) {
            Preconditions.checkArgument(Integer.parseInt(args[8]) >= 0);
            Preconditions.checkArgument(args.length == 10);
            Preconditions.checkArgument(args[9].matches("[a-zA-Z0-9_]+"));
        }
        else{
            Preconditions.checkArgument(args.length == 8);
            Preconditions.checkArgument(args[7].matches("[a-zA-Z0-9_]+"));
        }
    }

    public static void main(String[] args){
        try {
            append(args);
            System.exit(0);
        }
        catch(SecurityException e){
            System.err.println("security error");
            System.exit(-1);
        }
        catch (Exception e){
            System.out.println("invalid");
            System.exit(-1);
        }
    }
}
