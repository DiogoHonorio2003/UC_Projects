package our_package;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Classe responsavel por escrever e ler os ficheiros .dat e .txt
 */
public class ReadWriteFile extends StarThrive {

    public static String FichieroObj = "starthrive.dat";
    public static String FichieroTxt = "starthrive.txt";

    /**
     * Método que escreve os objetos no ficheiro .dat
     * @param lista lista de empresas
     */
    public static void writeFileObj(ArrayList<Empresas> lista){
        File f = new File(FichieroObj);

        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(lista);
            oos.close();
        } catch (FileNotFoundException e){
            System.out.printf("ERRO:\nNao foi possivel abrir ficheiro: " +getFichieroObj());
        } catch (IOException e) {
            System.out.printf("ERRO:\nNao foi possivel ler ficheiro: " + getFichieroObj());
        }

    }

    /**
     * Método que lê os objetos do ficheiro .dat
     * @return lista de empresas
     * @throws FileNotFoundException Não ser possivel abrir ficheiro
     * @throws IOException Não ser possivel efetuar a leitura da linha de dados do ficheiro de dados
     * @throws ClassNotFoundException Não foi possivel efetuar o parse de um dos valores
     */
    public static ArrayList<Empresas> readFileObject() throws FileNotFoundException,IOException,ClassNotFoundException {
        File f = new File(FichieroObj);
        ArrayList<Empresas> empresas;

        FileInputStream fis = new FileInputStream(f);
        ObjectInputStream ois = new ObjectInputStream(fis);
        empresas = (ArrayList<Empresas>)ois.readObject();
        ois.close();

        return empresas;
    }

    /**
     * Método que lê o ficheiro .txt
     * @return lista de empresas
     */
    public static ArrayList<Empresas> readFileTxt(){
        File f = new File(FichieroTxt);
        ArrayList<Empresas> empresas = new ArrayList<>();

        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null){
                String[] lineData = line.split(",");
                String tipo = lineData[0];
                String nome = lineData[1];
                String distrito = lineData[2];
                String localizacao = lineData[3];

                if((tipo.equalsIgnoreCase("Cafe"))|(tipo.equalsIgnoreCase("Pastelaria"))|(tipo.equalsIgnoreCase("FastFood"))|(tipo.equalsIgnoreCase("Locais"))) {
                    float custo_empregado = Float.parseFloat(lineData[4]);
                    int num_empregados = Integer.parseInt(lineData[5]);
                    float salario_medioAnual = Float.parseFloat(lineData[6]);
                    float media_ClientesDiarios = Float.parseFloat(lineData[7]);

                    if (tipo.equalsIgnoreCase("Cafe")) {
                        float media_CafesDia = Float.parseFloat(lineData[8]);
                        float media_FaturacaoAnualCafesDia = Float.parseFloat(lineData[9]);
                        Cafe c = new Cafe(nome, distrito, localizacao, custo_empregado, num_empregados, salario_medioAnual, media_ClientesDiarios, media_CafesDia, media_FaturacaoAnualCafesDia);
                        empresas.add(c);
                    }
                    if (tipo.equalsIgnoreCase("Pastelaria")) {
                        float media_BolosDia = Float.parseFloat(lineData[8]);
                        float media_FaturacaoAnualBolosDia = Float.parseFloat(lineData[9]);
                        Pastelaria p = new Pastelaria(nome, distrito, localizacao, custo_empregado, num_empregados, salario_medioAnual, media_ClientesDiarios, media_BolosDia, media_FaturacaoAnualBolosDia);
                        empresas.add(p);
                    }
                    if (tipo.equalsIgnoreCase("FastFood")) {
                        int num_DiasFuncionamentoAno = Integer.parseInt(lineData[8]);
                        int num_MesasInteriores = Integer.parseInt(lineData[9]);
                        float media_FaturacaoAnualMesaDia = Float.parseFloat(lineData[10]);
                        float mediaDiaria_ClientesDrive = Float.parseFloat(lineData[11]);
                        float mediaDiaria_FaturacaoClienteDrive = Float.parseFloat(lineData[12]);
                        Fast_Food ff = new Fast_Food(nome, distrito, localizacao, custo_empregado, num_empregados, salario_medioAnual, media_ClientesDiarios, num_DiasFuncionamentoAno, num_MesasInteriores, media_FaturacaoAnualMesaDia, mediaDiaria_ClientesDrive, mediaDiaria_FaturacaoClienteDrive);
                        empresas.add(ff);
                    }
                    if (tipo.equalsIgnoreCase("Locais")) {
                        int num_DiasFuncionamentoAno = Integer.parseInt(lineData[8]);
                        int num_MesasInteriores = Integer.parseInt(lineData[9]);
                        int num_MesasExteriores = Integer.parseInt(lineData[10]);
                        float custo_LicensaAnualMesaExterior = Float.parseFloat(lineData[11]);
                        float media_FaturacaoAnualMesaDia = Float.parseFloat(lineData[12]);
                        Locais l = new Locais(nome, distrito, localizacao, custo_empregado, num_empregados, salario_medioAnual, media_ClientesDiarios, num_DiasFuncionamentoAno, num_MesasInteriores, num_MesasExteriores, custo_LicensaAnualMesaExterior, media_FaturacaoAnualMesaDia);
                        empresas.add(l);
                    }
                }
                else if (tipo.equalsIgnoreCase("Frutaria")){
                    int num_Produtos = Integer.parseInt(lineData[4]);
                    float media_FaturacaoAnualProduto = Float.parseFloat(lineData[5]);
                    float custo_AnualLimpeza = Float.parseFloat(lineData[6]);
                    Frutaria fru = new Frutaria(nome,distrito,localizacao,num_Produtos,media_FaturacaoAnualProduto,custo_AnualLimpeza);
                    empresas.add(fru);
                }
                else if (tipo.equalsIgnoreCase("Mercado")){
                    float area_corredores = Float.parseFloat(lineData[4]);
                    float media_FaturacaoAnualm2 = Float.parseFloat(lineData[5]);
                    float custo_AnualLimpeza = Float.parseFloat(lineData[6]);
                    String tipoMercearia = lineData[7];
                    Mercado m = new Mercado(nome,distrito,localizacao,area_corredores,media_FaturacaoAnualm2,custo_AnualLimpeza,tipoMercearia);
                    empresas.add(m);
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("ERRO:\tNao foi possivel abrir ficheiro: " +getFichieroTxt());
            return null;
        } catch (IOException e) {
            System.out.println("ERRO:\tNao foi possivel efetuar a leitura da linha de dados do ficheiro de dados");
            return null;
        } catch (NumberFormatException e) {
            System.out.println("ERRO:\tNao foi possivel efetuar o parse de um dos valores");
            return null;
        }
        return empresas;
    }


    public static String getFichieroObj() {
        return FichieroObj;
    }

    public static void setFichieroObj(String fichieroObj) {
        FichieroObj = fichieroObj;
    }

    public static String getFichieroTxt() {
        return FichieroTxt;
    }

    public static void setFichieroTxt(String fichieroTxt) {
        FichieroTxt = fichieroTxt;
    }
}
