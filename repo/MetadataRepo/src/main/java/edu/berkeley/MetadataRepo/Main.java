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
        System.out.print("> ");
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext())
        {
            parseCommand(scan.nextLine());
            System.out.print("> ");
        }
    }

    private static void parseCommand(String line)
    {
        if (line.length() == 0)
            return;

        String[] cmds = line.split(" ");
        String act =  cmds[0];

        try
        {
            if (act.equals("commit"))
            {
                DatabaseController.commit(cmds[1], cmds[2]);
                System.out.println("Commit successful");
            }
            else if (act.equals("dump"))
            {
                DatabaseController.dump();
            }
            else if (act.equals("show"))
            {
                DatabaseController.show(cmds[1]);
            }
            else if (act.equals("clear"))
            {
                DatabaseController.clear();
                System.out.println("Repo has been cleared");
            }
            else
            {
                System.out.println("Error: Unrecognized command");
            }
        }
        catch (Exception e)
        {
            System.out.println("Error: Syntax error in command");
            System.out.println(e.toString());
        }
    }
}
