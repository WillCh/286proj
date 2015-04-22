package edu.berkeley.MetadataRepo;

import java.util.Scanner;

/**
 * Main program
 */
public class Main
{
    public static void main(String[] args)
    {
        MetadataRepo repo = new MetadataRepo("54.69.1.154");
        System.out.println("Welcome to the Metadata Repo shell!");
        System.out.print("> ");
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext())
        {
            repo.execute(scan.nextLine());
            System.out.print("> ");
        }
    }
}
