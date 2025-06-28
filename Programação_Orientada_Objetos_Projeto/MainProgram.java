/**
 * @author Bruno Silva 2021232021
 * @author Diogo Honorio 2021232043
 */


import our_package.*;


import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MainProgram {

    public static void main(String Args[]) throws IOException, ClassNotFoundException {

        StarThrive starThrive = new StarThrive();

        //  Leitura do ficheiro starthrive.dat
        int aux = 0;

        try {
            ArrayList<Empresas> empresas = ReadWriteFile.readFileObject();
            for (int i = 0; i < empresas.size(); i++) {
                starThrive.getLista_de_Empresas().add(empresas.get(i));
            }

        } catch (FileNotFoundException e) {
            System.out.println("ERRO:\tNao foi possivel abrir ficheiro:\tstarthrive.dat");
            aux = 1;
        } catch (IOException e) {
            System.out.println("ERRO:\tNao foi possivel ler ficheiro:\tstarthrive.dat");
            aux = 1;
        } catch (ClassNotFoundException e) {
            System.out.println("ERRO:\tNao foi possivel encontrar a classe:\tEmpresas");
            aux = 1;
        }



        if (aux != 0) {
            System.out.printf("\nTentanto ler o ficheiro starthrive.txt ...\n");
            ArrayList<Empresas> empresasTxt;
            empresasTxt = ReadWriteFile.readFileTxt();

            if (empresasTxt != null) {
                for (int i = 0; i < empresasTxt.size(); i++) {
                    starThrive.getLista_de_Empresas().add(empresasTxt.get(i));
                }
                System.out.printf("\nFicheiro starthrive.txt lido com sucesso!\n");
            }
        } else
            System.out.printf("\nFicheiro starthrive.dat lido com sucesso!\n");

        // Main:

        starThrive.toString_listaEmpresas();
        ReadWriteFile.writeFileObj(starThrive.getLista_de_Empresas());
        Gui gui = new Gui(starThrive);
    }

}



