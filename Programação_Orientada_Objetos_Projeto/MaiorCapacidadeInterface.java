package our_package;

import javax.swing.*;
import java.awt.*;

/**
 * Classe de interface que imprime as duas maiores empresas com capacidade de clientes diarios
 */
public class MaiorCapacidadeInterface extends JFrame {
    /**
     * Instância de interface
     */
    private Gui gui;
    /**
     * Painel principal
     */
    private JPanel mainPanel;
    /**
     * Painel auxiliar
     */
    private JPanel auxPanel;
    /**
     * Painel auxiliar
     */
    private JPanel auxTopPanel;
    /**
     * Painel auxiliar
     */
    private JPanel gridPanel1;
    /**
     * Painel auxiliar
     */
    private JPanel gridPanel2;
    /**
     * Instância da StarThrive
     */
    private StarThrive database;
    /**
     * Empresa com maior capacidade de clientes
     */
    private Empresas aux1 = null;
    /**
     * Empresa com a segunda maior capacidade de clientes
     */
    private Empresas aux2 = null;

    /**
     * Método para encontar as duas empresas de maior capacidade de clientes e inserir na interface
     * @param database Instancia da StarThrive
     * @param gui instância de interface
     */
    public MaiorCapacidadeInterface(StarThrive database, Gui gui) {
        super();

        this.gui = gui;
        this.database = database;

        this.setTitle("Empresas com Maior Capacidade de Clientes");
        this.setSize(1200, 500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        auxTopPanel = new JPanel();
        auxTopPanel.setLayout(new GridLayout(1,2));

        JLabel label1 = new JLabel("Empresa com maior num. de Clientes Diarios");
        JLabel label2 = new JLabel("Segunda empresa com maior num. de Clientes Diarios");
        auxTopPanel.add(label1);
        auxTopPanel.add(label2);
        mainPanel.add(auxTopPanel,BorderLayout.NORTH);

        auxPanel = new JPanel();
        auxPanel.setLayout(new GridLayout(1,2));


        for (int i = 0; i < database.getLista_de_Empresas().size(); i++) {
            if ((database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("Cafe")) | (database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("Pastelaria")) | (database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("FastFood")) | (database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("Locais"))) {
                if(aux1==null){
                    aux1=database.getLista_de_Empresas().get(i);
                }
                else if (aux1.media_ClientesDiarios() < database.getLista_de_Empresas().get(i).media_ClientesDiarios()) {
                    aux1 = database.getLista_de_Empresas().get(i);
                }
            }
        }
        for (int i = 0; i < database.getLista_de_Empresas().size(); i++) {
            if ((database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("Cafe")) | (database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("Pastelaria")) | (database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("FastFood")) | (database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("Locais"))) {
                if (database.getLista_de_Empresas().get(i)==aux1) continue;
                if(aux2==null){
                    aux2=database.getLista_de_Empresas().get(i);
                    continue;
                }
                if (aux2.media_ClientesDiarios() < database.getLista_de_Empresas().get(i).media_ClientesDiarios()) {
                    aux2 = database.getLista_de_Empresas().get(i);
                }
            }
        }

        gridPanel1 = new JPanel();
        gridPanel1.setLayout(new GridLayout(6,2));


        if(aux1.getTipo().equalsIgnoreCase("Cafe")){
            String[] array = aux1.details();
            System.out.printf(array[3]);

            JLabel nome = new JLabel("Nome");
            JTextField nomeI = new JTextField(aux1.getNome());
            JLabel distrto = new JLabel("Distrito");
            JTextField distritoI = new JTextField(aux1.getDistrito());
            JLabel localizacao = new JLabel("Localizacao");
            JTextField localizacaoI = new JTextField(aux1.getLocalizacao());
            JLabel custoEmpregado = new JLabel("Custo Empregado");
            JTextField custoEmpregadoI = new JTextField(array[0]);
            JLabel numEmpreagos = new JLabel("Num. Empregados");
            JTextField numEmpreagosI = new JTextField(array[1]);
            JLabel salarioAnual = new JLabel("Salario Medio Anual");
            JTextField salarioAnualI = new JTextField(array[2]);
            JLabel clientesDiarios = new JLabel("Media Clientes Diarios");
            JTextField clientesDiariosI = new JTextField(array[3]);

            JLabel cafesDiarios = new JLabel("Media Cafes Diarios");
            JTextField cafesDiariosI = new JTextField(array[4]);
            JLabel faturacaoAnualCafes = new JLabel("Media Faturacao A. Cafes");
            JTextField faturacaoAnualCafesI = new JTextField(array[5]);

            JLabel espaco = new JLabel("");

            gridPanel1.add(nome);
            gridPanel1.add(nomeI);
            gridPanel1.add(distrto);
            gridPanel1.add(distritoI);
            gridPanel1.add(localizacao);
            gridPanel1.add(localizacaoI);
            gridPanel1.add(custoEmpregado);
            gridPanel1.add(custoEmpregadoI);
            gridPanel1.add(numEmpreagos);
            gridPanel1.add(numEmpreagosI);
            gridPanel1.add(salarioAnual);
            gridPanel1.add(salarioAnualI);
            gridPanel1.add(clientesDiarios);
            gridPanel1.add(clientesDiariosI);
            gridPanel1.add(cafesDiarios);
            gridPanel1.add(cafesDiariosI);
            gridPanel1.add(faturacaoAnualCafes);
            gridPanel1.add(faturacaoAnualCafesI);
            gridPanel1.add(espaco);
            gridPanel1.add(espaco);
            gridPanel1.add(espaco);
        }
        if(aux1.getTipo().equalsIgnoreCase("Pastelaria")){
            String[] array = aux1.details();

            JLabel nome = new JLabel("Nome");
            JTextField nomeI = new JTextField(aux1.getNome());
            JLabel distrto = new JLabel("Distrito");
            JTextField distritoI = new JTextField(aux1.getDistrito());
            JLabel localizacao = new JLabel("Localizacao");
            JTextField localizacaoI = new JTextField(aux1.getLocalizacao());
            JLabel custoEmpregado = new JLabel("Custo Empregado");
            JTextField custoEmpregadoI = new JTextField(array[0]);
            JLabel numEmpreagos = new JLabel("Num. Empregados");
            JTextField numEmpreagosI = new JTextField(array[1]);
            JLabel salarioAnual = new JLabel("Salario Medio Anual");
            JTextField salarioAnualI = new JTextField(array[2]);
            JLabel clientesDiarios = new JLabel("Media Clientes Diarios");
            JTextField clientesDiariosI = new JTextField(array[3]);

            JLabel cafesDiarios = new JLabel("Media Bolos Diarios");
            JTextField cafesDiariosI = new JTextField(array[4]);
            JLabel faturacaoAnualCafes = new JLabel("Media Faturacao A. Bolos");
            JTextField faturacaoAnualCafesI = new JTextField(array[5]);

            JLabel espaco = new JLabel("");

            gridPanel1.add(nome);
            gridPanel1.add(nomeI);
            gridPanel1.add(distrto);
            gridPanel1.add(distritoI);
            gridPanel1.add(localizacao);
            gridPanel1.add(localizacaoI);
            gridPanel1.add(custoEmpregado);
            gridPanel1.add(custoEmpregadoI);
            gridPanel1.add(numEmpreagos);
            gridPanel1.add(numEmpreagosI);
            gridPanel1.add(salarioAnual);
            gridPanel1.add(salarioAnualI);
            gridPanel1.add(clientesDiarios);
            gridPanel1.add(clientesDiariosI);
            gridPanel1.add(cafesDiarios);
            gridPanel1.add(cafesDiariosI);
            gridPanel1.add(faturacaoAnualCafes);
            gridPanel1.add(faturacaoAnualCafesI);
            gridPanel1.add(espaco);
            gridPanel1.add(espaco);
            gridPanel1.add(espaco);
        }
        if(aux1.getTipo().equalsIgnoreCase("FastFood")){
            String[] array = aux1.details();

            JLabel nome = new JLabel("Nome");
            JTextField nomeI = new JTextField(aux1.getNome());
            JLabel distrto = new JLabel("Distrito");
            JTextField distritoI = new JTextField(aux1.getDistrito());
            JLabel localizacao = new JLabel("Localizacao");
            JTextField localizacaoI = new JTextField(aux1.getLocalizacao());
            JLabel custoEmpregado = new JLabel("Custo Empregado");
            JTextField custoEmpregadoI = new JTextField(array[0]);
            JLabel numEmpreagos = new JLabel("Num. Empregados");
            JTextField numEmpreagosI = new JTextField(array[1]);
            JLabel salarioAnual = new JLabel("Salario Medio Anual");
            JTextField salarioAnualI = new JTextField(array[2]);
            JLabel clientesDiarios = new JLabel("Media Clientes Diarios");
            JTextField clientesDiariosI = new JTextField(array[3]);

            JLabel numDiasFuncionamento = new JLabel("Num. Dias Funcionamento");
            JTextField numDiasFuncionamentoI = new JTextField(array[4]);
            JLabel numMesasInt = new JLabel("Num. Mesas Interiores");
            JTextField numMesasIntI = new JTextField(array[5]);
            JLabel faturacaoAMesa = new JLabel("Faturacao A. Mesas");
            JTextField faturacaoAMesaI = new JTextField(array[6]);
            JLabel clientesDrive = new JLabel("Media Clientes Drive");
            JTextField clientesDriveI = new JTextField(array[7]);
            JLabel faturacaoClientesD = new JLabel("Fatuaracao Clientes Drive");
            JTextField faturacaoClientesDI = new JTextField(array[8]);

            gridPanel1.add(nome);
            gridPanel1.add(nomeI);
            gridPanel1.add(distrto);
            gridPanel1.add(distritoI);
            gridPanel1.add(localizacao);
            gridPanel1.add(localizacaoI);
            gridPanel1.add(custoEmpregado);
            gridPanel1.add(custoEmpregadoI);
            gridPanel1.add(numEmpreagos);
            gridPanel1.add(numEmpreagosI);
            gridPanel1.add(salarioAnual);
            gridPanel1.add(salarioAnualI);
            gridPanel1.add(clientesDiarios);
            gridPanel1.add(clientesDiariosI);
            gridPanel1.add(numDiasFuncionamento);
            gridPanel1.add(numDiasFuncionamentoI);
            gridPanel1.add(numMesasInt);
            gridPanel1.add(numMesasIntI);
            gridPanel1.add(faturacaoAMesa);
            gridPanel1.add(faturacaoAMesaI);
            gridPanel1.add(clientesDrive);
            gridPanel1.add(clientesDriveI);
            gridPanel1.add(faturacaoClientesD);
            gridPanel1.add(faturacaoClientesDI);
        }
        if(aux1.getTipo().equalsIgnoreCase("Locais")){
            String[] array = aux1.details();

            JLabel nome = new JLabel("Nome");
            JTextField nomeI = new JTextField(aux1.getNome());
            JLabel distrto = new JLabel("Distrito");
            JTextField distritoI = new JTextField(aux1.getDistrito());
            JLabel localizacao = new JLabel("Localizacao");
            JTextField localizacaoI = new JTextField(aux1.getLocalizacao());
            JLabel custoEmpregado = new JLabel("Custo Empregado");
            JTextField custoEmpregadoI = new JTextField(array[0]);
            JLabel numEmpreagos = new JLabel("Num. Empregados");
            JTextField numEmpreagosI = new JTextField(array[1]);
            JLabel salarioAnual = new JLabel("Salario Medio Anual");
            JTextField salarioAnualI = new JTextField(array[2]);
            JLabel clientesDiarios = new JLabel("Media Clientes Diarios");
            JTextField clientesDiariosI = new JTextField(array[3]);

            JLabel numDiasFuncionamento = new JLabel("Num. Dias Funcionamento");
            JTextField numDiasFuncionamentoI = new JTextField(array[4]);
            JLabel numMesasInt = new JLabel("Num. Mesas Interiores");
            JTextField numMesasIntI = new JTextField(array[5]);
            JLabel numMesasExt = new JLabel("Num. Mesas Exteriores");
            JTextField numMesasExtI = new JTextField(array[6]);
            JLabel custoLAnual = new JLabel("Custo Licenca A.");
            JTextField custoLAnualI = new JTextField(array[7]);
            JLabel faturacaoAMesa = new JLabel("Faturacao A. Mesas");
            JTextField faturacaoAMesaI = new JTextField(array[8]);

            gridPanel1.add(nome);
            gridPanel1.add(nomeI);
            gridPanel1.add(distrto);
            gridPanel1.add(distritoI);
            gridPanel1.add(localizacao);
            gridPanel1.add(localizacaoI);
            gridPanel1.add(custoEmpregado);
            gridPanel1.add(custoEmpregadoI);
            gridPanel1.add(numEmpreagos);
            gridPanel1.add(numEmpreagosI);
            gridPanel1.add(salarioAnual);
            gridPanel1.add(salarioAnualI);
            gridPanel1.add(clientesDiarios);
            gridPanel1.add(clientesDiariosI);
            gridPanel1.add(numDiasFuncionamento);
            gridPanel1.add(numDiasFuncionamentoI);
            gridPanel1.add(numMesasInt);
            gridPanel1.add(numMesasIntI);
            gridPanel1.add(numMesasExt);
            gridPanel1.add(numMesasExtI);
            gridPanel1.add(custoLAnual);
            gridPanel1.add(custoLAnualI);
            gridPanel1.add(faturacaoAMesa);
            gridPanel1.add(faturacaoAMesaI);
        }

        gridPanel2 = new JPanel();
        gridPanel2.setLayout(new GridLayout(6,2));



        if(aux2.getTipo().equalsIgnoreCase("Cafe")){
            String[] array = aux2.details();

            JLabel nome = new JLabel("Nome");
            JTextField nomeI = new JTextField(aux2.getNome());
            JLabel distrto = new JLabel("Distrito");
            JTextField distritoI = new JTextField(aux2.getDistrito());
            JLabel localizacao = new JLabel("Localizacao");
            JTextField localizacaoI = new JTextField(aux2.getLocalizacao());
            JLabel custoEmpregado = new JLabel("Custo Empregado");
            JTextField custoEmpregadoI = new JTextField(array[0]);
            JLabel numEmpreagos = new JLabel("Num. Empregados");
            JTextField numEmpreagosI = new JTextField(array[1]);
            JLabel salarioAnual = new JLabel("Salario Medio Anual");
            JTextField salarioAnualI = new JTextField(array[2]);
            JLabel clientesDiarios = new JLabel("Media Clientes Diarios");
            JTextField clientesDiariosI = new JTextField(array[3]);

            JLabel cafesDiarios = new JLabel("Media Cafes Diarios");
            JTextField cafesDiariosI = new JTextField(array[4]);
            JLabel faturacaoAnualCafes = new JLabel("Media Faturacao A. Cafes");
            JTextField faturacaoAnualCafesI = new JTextField(array[5]);

            JLabel espaco = new JLabel("");

            gridPanel2.add(nome);
            gridPanel2.add(nomeI);
            gridPanel2.add(distrto);
            gridPanel2.add(distritoI);
            gridPanel2.add(localizacao);
            gridPanel2.add(localizacaoI);
            gridPanel2.add(custoEmpregado);
            gridPanel2.add(custoEmpregadoI);
            gridPanel2.add(numEmpreagos);
            gridPanel2.add(numEmpreagosI);
            gridPanel2.add(salarioAnual);
            gridPanel2.add(salarioAnualI);
            gridPanel2.add(clientesDiarios);
            gridPanel2.add(clientesDiariosI);
            gridPanel2.add(cafesDiarios);
            gridPanel2.add(cafesDiariosI);
            gridPanel2.add(faturacaoAnualCafes);
            gridPanel2.add(faturacaoAnualCafesI);
            gridPanel2.add(espaco);
            gridPanel2.add(espaco);
            gridPanel2.add(espaco);
        }
        if(aux2.getTipo().equalsIgnoreCase("Pastelaria")){
            String[] array = aux2.details();

            JLabel nome = new JLabel("Nome");
            JTextField nomeI = new JTextField(aux2.getNome());
            JLabel distrto = new JLabel("Distrito");
            JTextField distritoI = new JTextField(aux2.getDistrito());
            JLabel localizacao = new JLabel("Localizacao");
            JTextField localizacaoI = new JTextField(aux2.getLocalizacao());
            JLabel custoEmpregado = new JLabel("Custo Empregado");
            JTextField custoEmpregadoI = new JTextField(array[0]);
            JLabel numEmpreagos = new JLabel("Num. Empregados");
            JTextField numEmpreagosI = new JTextField(array[1]);
            JLabel salarioAnual = new JLabel("Salario Medio Anual");
            JTextField salarioAnualI = new JTextField(array[2]);
            JLabel clientesDiarios = new JLabel("Media Clientes Diarios");
            JTextField clientesDiariosI = new JTextField(array[3]);

            JLabel cafesDiarios = new JLabel("Media Bolos Diarios");
            JTextField cafesDiariosI = new JTextField(array[4]);
            JLabel faturacaoAnualCafes = new JLabel("Media Faturacao A. Bolos");
            JTextField faturacaoAnualCafesI = new JTextField(array[5]);

            JLabel espaco = new JLabel("");

            gridPanel2.add(nome);
            gridPanel2.add(nomeI);
            gridPanel2.add(distrto);
            gridPanel2.add(distritoI);
            gridPanel2.add(localizacao);
            gridPanel2.add(localizacaoI);
            gridPanel2.add(custoEmpregado);
            gridPanel2.add(custoEmpregadoI);
            gridPanel2.add(numEmpreagos);
            gridPanel2.add(numEmpreagosI);
            gridPanel2.add(salarioAnual);
            gridPanel2.add(salarioAnualI);
            gridPanel2.add(clientesDiarios);
            gridPanel2.add(clientesDiariosI);
            gridPanel2.add(cafesDiarios);
            gridPanel2.add(cafesDiariosI);
            gridPanel2.add(faturacaoAnualCafes);
            gridPanel2.add(faturacaoAnualCafesI);
            gridPanel2.add(espaco);
            gridPanel2.add(espaco);
            gridPanel2.add(espaco);
        }
        if(aux2.getTipo().equalsIgnoreCase("FastFood")){
            String[] array = aux2.details();

            JLabel nome = new JLabel("Nome");
            JTextField nomeI = new JTextField(aux2.getNome());
            JLabel distrto = new JLabel("Distrito");
            JTextField distritoI = new JTextField(aux2.getDistrito());
            JLabel localizacao = new JLabel("Localizacao");
            JTextField localizacaoI = new JTextField(aux2.getLocalizacao());
            JLabel custoEmpregado = new JLabel("Custo Empregado");
            JTextField custoEmpregadoI = new JTextField(array[0]);
            JLabel numEmpreagos = new JLabel("Num. Empregados");
            JTextField numEmpreagosI = new JTextField(array[1]);
            JLabel salarioAnual = new JLabel("Salario Medio Anual");
            JTextField salarioAnualI = new JTextField(array[2]);
            JLabel clientesDiarios = new JLabel("Media Clientes Diarios");
            JTextField clientesDiariosI = new JTextField(array[3]);

            JLabel numDiasFuncionamento = new JLabel("Num. Dias Funcionamento");
            JTextField numDiasFuncionamentoI = new JTextField(array[4]);
            JLabel numMesasInt = new JLabel("Num. Mesas Interiores");
            JTextField numMesasIntI = new JTextField(array[5]);
            JLabel faturacaoAMesa = new JLabel("Faturacao A. Mesas");
            JTextField faturacaoAMesaI = new JTextField(array[6]);
            JLabel clientesDrive = new JLabel("Media Clientes Drive");
            JTextField clientesDriveI = new JTextField(array[7]);
            JLabel faturacaoClientesD = new JLabel("Fatuaracao Clientes Drive");
            JTextField faturacaoClientesDI = new JTextField(array[8]);

            gridPanel2.add(nome);
            gridPanel2.add(nomeI);
            gridPanel2.add(distrto);
            gridPanel2.add(distritoI);
            gridPanel2.add(localizacao);
            gridPanel2.add(localizacaoI);
            gridPanel2.add(custoEmpregado);
            gridPanel2.add(custoEmpregadoI);
            gridPanel2.add(numEmpreagos);
            gridPanel2.add(numEmpreagosI);
            gridPanel2.add(salarioAnual);
            gridPanel2.add(salarioAnualI);
            gridPanel2.add(clientesDiarios);
            gridPanel2.add(clientesDiariosI);
            gridPanel2.add(numDiasFuncionamento);
            gridPanel2.add(numDiasFuncionamentoI);
            gridPanel2.add(numMesasInt);
            gridPanel2.add(numMesasIntI);
            gridPanel2.add(faturacaoAMesa);
            gridPanel2.add(faturacaoAMesaI);
            gridPanel2.add(clientesDrive);
            gridPanel2.add(clientesDriveI);
            gridPanel2.add(faturacaoClientesD);
            gridPanel2.add(faturacaoClientesDI);
        }
        if(aux2.getTipo().equalsIgnoreCase("Locais")){
            String[] array = aux2.details();

            JLabel nome = new JLabel("Nome");
            JTextField nomeI = new JTextField(aux2.getNome());
            JLabel distrto = new JLabel("Distrito");
            JTextField distritoI = new JTextField(aux2.getDistrito());
            JLabel localizacao = new JLabel("Localizacao");
            JTextField localizacaoI = new JTextField(aux2.getLocalizacao());
            JLabel custoEmpregado = new JLabel("Custo Empregado");
            JTextField custoEmpregadoI = new JTextField(array[0]);
            JLabel numEmpreagos = new JLabel("Num. Empregados");
            JTextField numEmpreagosI = new JTextField(array[1]);
            JLabel salarioAnual = new JLabel("Salario Medio Anual");
            JTextField salarioAnualI = new JTextField(array[2]);
            JLabel clientesDiarios = new JLabel("Media Clientes Diarios");
            JTextField clientesDiariosI = new JTextField(array[3]);

            JLabel numDiasFuncionamento = new JLabel("Num. Dias Funcionamento");
            JTextField numDiasFuncionamentoI = new JTextField(array[4]);
            JLabel numMesasInt = new JLabel("Num. Mesas Interiores");
            JTextField numMesasIntI = new JTextField(array[5]);
            JLabel numMesasExt = new JLabel("Num. Mesas Exteriores");
            JTextField numMesasExtI = new JTextField(array[6]);
            JLabel custoLAnual = new JLabel("Custo Licenca A.");
            JTextField custoLAnualI = new JTextField(array[7]);
            JLabel faturacaoAMesa = new JLabel("Faturacao A. Mesas");
            JTextField faturacaoAMesaI = new JTextField(array[8]);

            gridPanel2.add(nome);
            gridPanel2.add(nomeI);
            gridPanel2.add(distrto);
            gridPanel2.add(distritoI);
            gridPanel2.add(localizacao);
            gridPanel2.add(localizacaoI);
            gridPanel2.add(custoEmpregado);
            gridPanel2.add(custoEmpregadoI);
            gridPanel2.add(numEmpreagos);
            gridPanel2.add(numEmpreagosI);
            gridPanel2.add(salarioAnual);
            gridPanel2.add(salarioAnualI);
            gridPanel2.add(clientesDiarios);
            gridPanel2.add(clientesDiariosI);
            gridPanel2.add(numDiasFuncionamento);
            gridPanel2.add(numDiasFuncionamentoI);
            gridPanel2.add(numMesasInt);
            gridPanel2.add(numMesasIntI);
            gridPanel2.add(numMesasExt);
            gridPanel2.add(numMesasExtI);
            gridPanel2.add(custoLAnual);
            gridPanel2.add(custoLAnualI);
            gridPanel2.add(faturacaoAMesa);
            gridPanel2.add(faturacaoAMesaI);
        }

        auxPanel.add(gridPanel1);
        auxPanel.add(gridPanel2);
        mainPanel.add(auxPanel,BorderLayout.CENTER);
        this.add(mainPanel);
        this.setVisible(true);
    }
}
