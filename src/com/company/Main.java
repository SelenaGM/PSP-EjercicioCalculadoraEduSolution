package com.company;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
       int num1=0, num2=0;
       String signo = "ERROR";
       Scanner sc = new Scanner(System.in);
       ProcessBuilder proceso= null;


        System.out.println("Escribe la operación: ");
        String operacion = sc.nextLine();

        try{
            if (operacion.contains("+")) {
                signo = "+";
                num1 = Integer.parseInt(operacion.split("\\+")[0]);
                num2 = Integer.parseInt(operacion.split("\\+")[1]);
                proceso = new ProcessBuilder("java", "-jar", "suma.jar");

            } else if (operacion.contains("-")) {
                signo = "-";
                num1 = Integer.parseInt(operacion.split("-")[0]);
                num2 = Integer.parseInt(operacion.split("-")[1]);
                proceso = new ProcessBuilder("java", "-jar", "resta.jar");


            } else if (operacion.contains("*")) {
                signo = "*";
                num1 = Integer.parseInt(operacion.split("\\*")[0]);
                num2 = Integer.parseInt(operacion.split("\\*")[1]);
                proceso = new ProcessBuilder("java", "-jar", "multiplica.jar");

            } else if (operacion.contains("/")) {
                signo = "/";
                num1 = Integer.parseInt(operacion.split("/")[0]);
                num2 = Integer.parseInt(operacion.split("/")[1]);
                proceso = new ProcessBuilder("java", "-jar", "division.jar");

            } else {
                System.out.println("La operación no es realizable");
            }


            if(!signo.equals("ERROR")) {
                float resultado = creaHijoCalculadora(proceso,num1,num2);
                System.out.println(operacion+"="+resultado);
            }

        }catch (NumberFormatException exception){
            System.out.println("ERES BOBO QUE NO SABES ESCRIBIR UNA OPERACION???");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private static float creaHijoCalculadora(ProcessBuilder proceso, int num1, int num2) throws IOException {

        proceso.redirectErrorStream(true);
        Process hijo = proceso.start();

        OutputStream ops = hijo.getOutputStream();
        PrintStream ps = new PrintStream(ops);
        //Para leer lo que nos envía el hijo, SIEMPRE ANTES DE UTILIZARLAS PARA QUE NO SE NOS PIERDA EL PROCESO
        InputStream is = hijo.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);


        //Para enviarle la información al hijo:
        ps.println(String.valueOf(num1));
        ps.flush();
        ps.println(String.valueOf(num2));
        ps.flush();




        return Float.parseFloat(br.readLine());
    }
}