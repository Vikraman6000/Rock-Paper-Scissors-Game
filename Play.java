//Filename  - Play.java

import java.io.*;
import java.util.*;

public class Play {
    File store = new File("hs.txt");
    Scanner input = new Scanner(System.in);

    public void startPage(){
        String top = "None" ;
        try{
            if(!(store.exists())) store.createNewFile();
        }catch(IOException e){
            System.out.println("File Missing");
        }
        try{
            Scanner collect = new Scanner(store);
            if(collect.hasNextLine()){
                top = collect.nextLine();
            }
            collect.close();
        }catch(FileNotFoundException e){
            System.out.println("No file found");
        }
        System.out.println("*************************");
        System.out.println("High Score : " + top);
        System.out.println("**********************");
        System.out.println("Press Q to quit the game.\n"+
                "\nPress any key to Start playing");
        String waste ;
        waste = input.nextLine().toLowerCase().trim();
        if(waste.equals("q")){
            System.out.println("Thank You!!");
            System.exit(1);
        }
        System.out.println("Instructions:\n" +
                "Press 1 to represent Rock\n" +
                "Press 2 to represent Paper\n" +
                "Press 3 to represent Scissors\n\n" +
                "Important : Press 0 to stop playing\n");
    }
    public int gameplay(){
        System.out.println("Your Choice: ");
        int choice ;
        while (true) {
            try {
                choice = input.nextInt();
                break;
            } catch (InputMismatchException e) {
                input.next();
                System.out.println("Invalid input. Enter a number : ");
            }
        }
        if (choice==0) return -9;
        int points = 0;
        int comp = generate();
        if(choice==comp) {
            System.out.println("A draw no points");
            return 0;
        }
        switch(choice){
            case 1:
                if(comp==3) points =1;
                else points =-1;
                break;
            case 2 :
                if(comp==1) points =1;
                else points = -1;
                break;
            case 3:
                if(comp==2) points = 1;
                else points= -1;
                break;
            default:
                System.out.println("!!Enter a valid choice!!");
        }
        if(points==1)
            System.out.println("You won ");
        else if(points==-1) System.out.println("You lost ");
           return points;
    }
    public int generate(){
        int out = (int)(Math.random()*3);
        while(out==0){
            out = (int)(Math.random()*3);
        }
        return out;
    }
    public boolean end(int score){
        System.out.println("Enter your name:");
        input.nextLine();
        String name = input.nextLine().trim();
        while(name.length()<3){
            System.out.println("Enter a minimum 3 characters");
            name = input.nextLine().trim();
        }
        System.out.println(name+" you Scored : "+score);
        upload(name,score);
        try{
            Scanner collect = new Scanner(store);
            System.out.print("High Score: ");
            if(collect.hasNextLine()) {
                String line = collect.nextLine();
                System.out.println(line);
            }
            collect.close();
            System.out.println();
        }catch(FileNotFoundException e){
            System.out.println("No High scores found");
        }
        System.out.println("Press Y for yes / N for no");
        System.out.println("Do you want to play again:");
        String replay ;
        while (true) {
            replay = input.nextLine().toLowerCase().trim();
            if (replay.equals("y") || replay.equals("n")) break;
            else System.out.println("Invalid input. Press 'y' or 'n' : ");
        }
        return replay.equals("y");
    }
    public void upload(String name, int num){
        try {
            Scanner collect = new Scanner(store);
            String select ;
            String[] numb ;
            boolean flag = true;
            if(collect.hasNextLine()){
                select = collect.nextLine();
                numb = select.split(" ");
                if(Integer.parseInt(numb[1])>num) flag = false;
            }
            if(flag){
                FileWriter writer = new FileWriter(store);
                writer.write(name+" "+num);
                writer.close();
            }
            collect.close();
        }catch (IOException e){
            System.out.println("Error in Updating file");
        }
    }
    public int control(){
        startPage();
        int score = 0,temp;
        while(true){
            temp = gameplay();
            if(temp==-9) break;
            score += temp ;
            if(score<0) score = 0;
        }
        return score;
    }
    public static void main(String[] args) {
        Play game = new Play();
        while(game.end(game.control())){}
        System.out.println("Thank You!!");
    }
}

// input1: 1 2 2 3 3 2 2 2 2 2 3 1 2 2 1 2 2 1 2 2 2 1 3 1 2 3 2 1 0
// input2: 2 2 2 2 3 2 3 2 3 2 1 3 2 1 0
// input3: 1 2 3 2 2 3 1 3 2 0