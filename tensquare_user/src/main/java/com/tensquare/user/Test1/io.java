package com.tensquare.user.Test1;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author
 * @date 2020/4/26 16:01
 */
public class io {

    public static void fileIO() throws Exception {
        //Reader r = new FileReader(new File("d:\\1.txt"));
        InputStream is = new FileInputStream(new File("d:\\1.txt"));
        byte b[] = new byte[Integer.parseInt(new File("d:\\1.txt").length()
                + "")];
        is.read(b);
        System.out.write(b);
       // System.out.println();
        is.close();
    }

    public static void a(){
        try {
            FileReader fr= new FileReader("d:\\1.txt");
            int ch=fr.read();
            while(ch!=-1){
                System.out.println((char) ch);
                ch=fr.read();
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void b(){
        try {

            FileReader fr = new FileReader("d:\\1.txt");
            FileWriter fw = new FileWriter("d:\\2.txt");
            System.out.println(fw);
            char[] chs = new char[1024];
            int len=0;
            while ((len=fr.read(chs))!=-1){
                fw.write(chs,0,len);
                System.out.println(chs);
            }

            fw.close();
            fr.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void c(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("d:\\1.txt"));
            BufferedWriter bw = new BufferedWriter(new FileWriter("d:\\3.txt"));
            char[] chs = new char[1024];
            int len=0;
            while ((len=br.read(chs))!=-1){
                bw.write(chs,0,len);
            }
            bw.close();
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void d(){

        try {
            FileInputStream is = new FileInputStream("d:\\1.txt");
            FileOutputStream os = new FileOutputStream("d:\\4.txt");
            byte[] bytes = new byte[1024];
            int len=0;
            while ((len=is.read(bytes))!=-1){
                os.write(bytes,0,len);
            }
            is.close();
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void e(){
        String s ="";
        Random rand = new Random();
        for (int i=0;i<10;i++){
            s=s+rand.nextInt(1000)+"";
        }

        System.out.println(s);
    }
    public static void f(){
        String s ="";
        Random rand = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0;i<10;i++){

            stringBuilder.append(rand.nextInt(1000));
            stringBuilder.append("");
        }

        System.out.println(stringBuilder.toString());
    }

    public static void h(){
        String s="";
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        ArrayList<Object> list = new ArrayList<>();
        ConcurrentHashMap<Object, Object> concurrentHashMap = new ConcurrentHashMap<>();
        System.out.println(1<<4);

    }

    public static void main(String[] args) throws Exception {
        //fileIO();
        //a();
        //b();
        //c();
        //e();
        //f();

        System.out.println(1<<30);
        System.out.println(2^30);
    }

}
