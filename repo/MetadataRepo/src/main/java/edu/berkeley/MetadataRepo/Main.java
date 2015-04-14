package edu.berkeley.MetadataRepo;

import java.util.Scanner;

/**
 * Main program
 */
public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Welcome to the Metadata Repo shell!");
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext())
        {
            parseCommand(scan.nextLine());
        }
    }

    private static void parseCommand(String line)
    {
        String[] cmds = line.split(" ");
        String act =  cmds[0];

        try
        {
            if (act.equals("commit"))
            {
                DatabaseController.commit(cmds[1], cmds[2]);
            }
            else if (act.equals("dump"))
            {
                DatabaseController.dump();
            }
            else if (act.equals("show"))
            {
                DatabaseController.show(cmds[1]);
            }
            else
            {
                System.out.println("Error: Unrecognized command");
            }
        }
        catch (Exception e)
        {
            System.out.println("Error: Syntax error in command");
            e.printStackTrace();
        }
    }
}
