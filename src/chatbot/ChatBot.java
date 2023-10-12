package chatbot;

import java.util.Scanner;

public class ChatBot
{
    private static final Scanner scan = new Scanner(System.in);
    private final String botName;

    public static void main(String[] args) {
        ChatBot spectrum = new ChatBot("Spectrum021");
        spectrum.greet();
    }

    public ChatBot(String name) {
        this.botName = name;
    }

    public void greet(){
        int birth_year = 2021;

        System.out.printf("Hello, my name is %s. \nI was created in %d. \nPlease, remind me your name.\n", botName, birth_year);
        String clName = scan.nextLine();
        System.out.printf("What a great name you have, %s.\n", clName);
        this.guessAge();
    }

    private void guessAge(){
        System.out.print("\nLet me guess your age. \nEnter remainders of dividing your age by 3, 5 and 7.\n");

        int remainder3, remainder5, remainder7, age;

        remainder3 = scan.nextInt() % 3;
        remainder5 = scan.nextInt() % 5;
        remainder7 = scan.nextInt() % 7;

        age = ChatBot.getAge(remainder3, remainder5, remainder7);
        System.out.printf("Your age is %d; that's a good time to start programming!\n", age);
        this.countNumber();
    }

    private void countNumber() {
        System.out.println("Now i will prove to you than I can count to any number you want!");
        int clCount = scan.nextInt();

        for (int i = 0; i <= clCount; i++) {
            System.out.println(i + " !");
        }
        this.getTest();
    }

    private void getTest() {
        System.out.println("What was the name originally given to the new programming language, which later became known as 'Java'?\n1. Snake\n2. Tree\n3. Java\n4. Oak \nUse answer option number.");
        String answer;
        boolean ex = false;

        while(!ex) {
            answer = (scan.nextLine()).trim();

            if(answer.equals("4")) {
                System.out.println("It was Oak, right! \nGoodbye, have a nice day!");
                ex = true;
            }
            else if(!answer.isEmpty()) {
                System.out.println("Nope, please try again!");
            }
        }
    }

    private static int getAge(int r3, int r5, int r7) {
        return (r3 * 70 + r5 * 21 + r7 * 15) % 105;
    }
}