package com.atm.atm2;



import java.io.*;

public class Card {
    private Integer balance = null;
    private Integer pin_code = null;
    private Long number = null;


    public void readCard(File filename){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))){
            String p = bufferedReader.readLine();
            String n = bufferedReader.readLine();
            String b = bufferedReader.readLine();
            this.pin_code = Integer.parseInt(p.trim());
            this.balance = Integer.parseInt(b.trim());
            this.number = Long.valueOf(n.trim());

        }catch (IOException e){
            System.out.println(e);
        }
    }
    public void updateBalanceCard(File file, int addBalance){
        String p1 = null;
        String n1 = null;
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){
            p1 = bufferedReader.readLine();
            n1 = bufferedReader.readLine();


        }catch (IOException e){
            System.out.println(e);
        }
        File fold = new File(file.getPath());
        fold.delete();
        File fnew = new File("C:\\Users\\gafru\\IdeaProjects\\ATM2\\src\\card.txt");

        String source = String.valueOf(this.balance + addBalance);


        try {
            FileWriter f2 = new FileWriter(fnew, false);
            setBalance(this.balance + addBalance);
            f2.write(p1 + "\n" + n1 +"\n"+ source);
            f2.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }




    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getPin_code() {
        return pin_code;
    }

    public int getBalance() {
        return balance;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }
}
