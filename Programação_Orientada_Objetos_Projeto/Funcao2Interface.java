package our_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe de interface que imprime as empresas com maior receita anual, menor despeja anual e maior lucro anual
 */
public class Funcao2Interface extends JFrame {
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
     * Combobox dos tipos de empresas
     */
    private JComboBox<String> tipoCombobox;
    /**
     * Instância da StarThrive
     */
    private StarThrive database;
    /**
     * Label do valor da empresa com menor despesa anual
     */
    private JLabel labelDespesa;
    /**
     * Label do valor da empresa com maior receita anual
     */
    private JLabel labelReceita;
    /**
     * Label do valor da empresa com maior lucro anual
     */
    private JLabel labelLucro;
    /**
     * Label da empresa com maior receita anual
     */
    private JLabel labelNomeR;
    /**
     * Label da empresa com menor despesa anual
     */
    private JLabel labelNomeD;
    /**
     * Label da empresa com maior lucro anual
     */
    private JLabel labelNomeL;
    /**
     * TextField do Input do nome da empresa com maior receita anual
     */
    private JTextField nomeReceitaInput;
    /**
     * TextField do Input do nome da empresa com menor despesa anual
     */
    private JTextField nomeDespesaInput;
    /**
     * TextField do Input do nome da empresa com maior lucro anual
     */
    private JTextField nomeLucroInput;
    /**
     * TextField do Input do valor da empresa com maior receita anual
     */
    private JTextField valorReceitaAnualInput;
    /**
     * TextField do Input do valor da empresa com menor despesa anual
     */
    private JTextField valorDespesaAnualInput;
    /**
     * TextField do Input do valor da empresa com maior lucro anual
     */
    private JTextField valorLucroInput;


    /**
     * Método que apresenta as empresas com maior receita anual, menor despeja anual e maior lucro anual
     * @param database Instância da StarThrive
     * @param gui Instância de interface
     */
    public Funcao2Interface(StarThrive database, Gui gui){
        super();

        this.gui = gui;
        this.database = database;

        this.setTitle("Empresas de cada tipo com maior Receita, menor Despesa e maior Lucro");
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        auxPanel = new JPanel();
        auxPanel.setLayout(null);


        String[] tipos = {"Cafe","Pastelaria","FastFood","Locais","Frutaria","Mercado"};
        tipoCombobox = new JComboBox(tipos);
        mainPanel.add(tipoCombobox,BorderLayout.NORTH);

        labelReceita = new JLabel("Empresa com maior Receita Anual:");
        labelDespesa = new JLabel("Empresa com menor Despesa Anual:");
        labelLucro = new JLabel("Empresa com maior Lucro Anual:");

        labelNomeR = new JLabel("Nome da Empresa:");
        labelNomeD = new JLabel("Nome da Empresa:");
        labelNomeL = new JLabel("Nome da Empresa:");

        nomeReceitaInput = new JTextField();
        nomeDespesaInput = new JTextField();
        nomeLucroInput = new JTextField();
        valorReceitaAnualInput = new JTextField();
        valorDespesaAnualInput = new JTextField();
        valorLucroInput = new JTextField();

        labelReceita.setBounds(10,40,200,50);
        labelNomeR.setBounds(10,60,200,50);
        nomeReceitaInput.setBounds(10,100,200,50);
        valorReceitaAnualInput.setBounds(220,100,200,50);

        labelDespesa.setBounds(10,160,300,50);
        labelNomeD.setBounds(10,180,200,50);
        nomeDespesaInput.setBounds(10,220,200,50);
        valorDespesaAnualInput.setBounds(220,220,200,50);

        labelLucro.setBounds(10,270,200,50);
        labelNomeL.setBounds(10,290,200,50);
        nomeLucroInput.setBounds(10,330,200,50);
        valorLucroInput.setBounds(220,330,200,50);

        auxPanel.add(labelReceita);
        auxPanel.add(labelDespesa);
        auxPanel.add(labelLucro);
        auxPanel.add(labelNomeR);
        auxPanel.add(labelNomeD);
        auxPanel.add(labelNomeL);
        auxPanel.add(nomeReceitaInput);
        auxPanel.add(nomeDespesaInput);
        auxPanel.add(nomeLucroInput);
        auxPanel.add(valorReceitaAnualInput);
        auxPanel.add(valorDespesaAnualInput);
        auxPanel.add(valorLucroInput);

        tipoCombobox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String opcao = (String) tipoCombobox.getSelectedItem();

                if (opcao.equalsIgnoreCase("Cafe")) {
                    Empresas empresaR = null;
                    Empresas empresaD = null;
                    Empresas empresaL = null;
                    for(int i = 0;i<database.getLista_de_Empresas().size();i++){
                        if(empresaR==null) {
                            if (database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("Cafe")) {
                                empresaR = database.getLista_de_Empresas().get(i);
                                empresaD = database.getLista_de_Empresas().get(i);
                                empresaL = database.getLista_de_Empresas().get(i);
                            }
                        }
                    }
                    for (int i = 0; i < database.getLista_de_Empresas().size(); i++) {
                        if (database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("Cafe")) {
                            if (database.getLista_de_Empresas().get(i).receitaAnual() > empresaR.receitaAnual()) {
                                empresaR = database.getLista_de_Empresas().get(i);
                            }
                        }
                        if (database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("Cafe")) {
                            if (database.getLista_de_Empresas().get(i).despesaAnual() < empresaD.despesaAnual()) {
                                empresaD = database.getLista_de_Empresas().get(i);
                            }
                        }
                        if (database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("Cafe")) {
                            if ((database.getLista_de_Empresas().get(i).receitaAnual() - database.getLista_de_Empresas().get(i).despesaAnual()) > empresaL.receitaAnual()-empresaL.despesaAnual()) {
                                empresaL = database.getLista_de_Empresas().get(i);
                            }
                        }
                        nomeReceitaInput.setText(empresaR.getNome());
                        nomeDespesaInput.setText(empresaD.getNome());
                        nomeLucroInput.setText(empresaL.getNome());
                        valorReceitaAnualInput.setText(String.valueOf(empresaR.receitaAnual()));
                        valorDespesaAnualInput.setText(String.valueOf(empresaD.despesaAnual()));
                        valorLucroInput.setText(String.valueOf(empresaL.receitaAnual()-empresaL.despesaAnual()));
                    }
                }
                if (opcao.equalsIgnoreCase("Pastelaria")) {
                    Empresas empresaR = null;
                    Empresas empresaD = null;
                    Empresas empresaL = null;
                    for(int i = 0;i<database.getLista_de_Empresas().size();i++){
                        if(empresaR==null) {
                            if (database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("Pastelaria")) {
                                empresaR = database.getLista_de_Empresas().get(i);
                                empresaD = database.getLista_de_Empresas().get(i);
                                empresaL = database.getLista_de_Empresas().get(i);
                            }
                        }
                    }
                    for (int i = 0; i < database.getLista_de_Empresas().size(); i++) {
                        if (database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("Pastelaria")) {
                            if (database.getLista_de_Empresas().get(i).receitaAnual() > empresaR.receitaAnual()) {
                                empresaR = database.getLista_de_Empresas().get(i);
                            }
                        }
                        if (database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("Pastelaria")) {
                            if (database.getLista_de_Empresas().get(i).despesaAnual() < empresaD.despesaAnual()) {
                                empresaD = database.getLista_de_Empresas().get(i);
                            }
                        }
                        if (database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("Pastelaria")) {
                            if ((database.getLista_de_Empresas().get(i).receitaAnual() - database.getLista_de_Empresas().get(i).despesaAnual()) > empresaL.receitaAnual()-empresaL.despesaAnual()) {
                                empresaL = database.getLista_de_Empresas().get(i);
                            }
                        }
                        nomeReceitaInput.setText(empresaR.getNome());
                        nomeDespesaInput.setText(empresaD.getNome());
                        nomeLucroInput.setText(empresaL.getNome());
                        valorReceitaAnualInput.setText(String.valueOf(empresaR.receitaAnual()));
                        valorDespesaAnualInput.setText(String.valueOf(empresaD.despesaAnual()));
                        valorLucroInput.setText(String.valueOf(empresaL.receitaAnual()-empresaL.despesaAnual()));
                    }
                }
                if (opcao.equalsIgnoreCase("FastFood")) {
                    Empresas empresaR = null;
                    Empresas empresaD = null;
                    Empresas empresaL = null;
                    for(int i = 0;i<database.getLista_de_Empresas().size();i++){
                        if(empresaR==null) {
                            if (database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("FastFood")) {
                                empresaR = database.getLista_de_Empresas().get(i);
                                empresaD = database.getLista_de_Empresas().get(i);
                                empresaL = database.getLista_de_Empresas().get(i);
                            }
                        }
                    }
                    for (int i = 0; i < database.getLista_de_Empresas().size(); i++) {
                        if (database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("FastFood")) {
                            if (database.getLista_de_Empresas().get(i).receitaAnual() > empresaR.receitaAnual()) {
                                empresaR = database.getLista_de_Empresas().get(i);
                            }
                        }
                        if (database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("FastFood")) {
                            if (database.getLista_de_Empresas().get(i).despesaAnual() < empresaD.despesaAnual()) {
                                empresaD = database.getLista_de_Empresas().get(i);
                            }
                        }
                        if (database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("FastFood")) {
                            if ((database.getLista_de_Empresas().get(i).receitaAnual() - database.getLista_de_Empresas().get(i).despesaAnual()) > empresaL.receitaAnual()-empresaL.despesaAnual()) {
                                empresaL = database.getLista_de_Empresas().get(i);
                            }
                        }
                        nomeReceitaInput.setText(empresaR.getNome());
                        nomeDespesaInput.setText(empresaD.getNome());
                        nomeLucroInput.setText(empresaL.getNome());
                        valorReceitaAnualInput.setText(String.valueOf(empresaR.receitaAnual()));
                        valorDespesaAnualInput.setText(String.valueOf(empresaD.despesaAnual()));
                        valorLucroInput.setText(String.valueOf(empresaL.receitaAnual()-empresaL.despesaAnual()));
                    }
                }
                if (opcao.equalsIgnoreCase("Frutaria")) {
                    Empresas empresaR = null;
                    Empresas empresaD = null;
                    Empresas empresaL = null;
                    for(int i = 0;i<database.getLista_de_Empresas().size();i++){
                        if(empresaR==null) {
                            if (database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("Frutaria")) {
                                empresaR = database.getLista_de_Empresas().get(i);
                                empresaD = database.getLista_de_Empresas().get(i);
                                empresaL = database.getLista_de_Empresas().get(i);
                            }
                        }
                    }
                    for (int i = 0; i < database.getLista_de_Empresas().size(); i++) {
                        if (database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("Frutaria")) {
                            if (database.getLista_de_Empresas().get(i).receitaAnual() > empresaR.receitaAnual()) {
                                empresaR = database.getLista_de_Empresas().get(i);
                            }
                        }
                        if (database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("Frutaria")) {
                            if (database.getLista_de_Empresas().get(i).despesaAnual() < empresaD.despesaAnual()) {
                                empresaD = database.getLista_de_Empresas().get(i);
                            }
                        }
                        if (database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("Frutaria")) {
                            if ((database.getLista_de_Empresas().get(i).receitaAnual() - database.getLista_de_Empresas().get(i).despesaAnual()) > empresaL.receitaAnual()-empresaL.despesaAnual()) {
                                empresaL = database.getLista_de_Empresas().get(i);
                            }
                        }
                        nomeReceitaInput.setText(empresaR.getNome());
                        nomeDespesaInput.setText(empresaD.getNome());
                        nomeLucroInput.setText(empresaL.getNome());
                        valorReceitaAnualInput.setText(String.valueOf(empresaR.receitaAnual()));
                        valorDespesaAnualInput.setText(String.valueOf(empresaD.despesaAnual()));
                        valorLucroInput.setText(String.valueOf(empresaL.receitaAnual()-empresaL.despesaAnual()));
                    }
                }
                if (opcao.equalsIgnoreCase("Locais")) {
                    Empresas empresaR = null;
                    Empresas empresaD = null;
                    Empresas empresaL = null;
                    for(int i = 0;i<database.getLista_de_Empresas().size();i++){
                        if(empresaR==null) {
                            if (database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("Locais")) {
                                empresaR = database.getLista_de_Empresas().get(i);
                                empresaD = database.getLista_de_Empresas().get(i);
                                empresaL = database.getLista_de_Empresas().get(i);
                            }
                        }
                    }
                    for (int i = 0; i < database.getLista_de_Empresas().size(); i++) {
                        if (database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("Locais")) {
                            if (database.getLista_de_Empresas().get(i).receitaAnual() > empresaR.receitaAnual()) {
                                empresaR = database.getLista_de_Empresas().get(i);
                            }
                        }
                        if (database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("Locais")) {
                            if (database.getLista_de_Empresas().get(i).despesaAnual() < empresaD.despesaAnual()) {
                                empresaD = database.getLista_de_Empresas().get(i);
                            }
                        }
                        if (database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("Locais")) {
                            if ((database.getLista_de_Empresas().get(i).receitaAnual() - database.getLista_de_Empresas().get(i).despesaAnual()) > empresaL.receitaAnual()-empresaL.despesaAnual()) {
                                empresaL = database.getLista_de_Empresas().get(i);
                            }
                        }
                        nomeReceitaInput.setText(empresaR.getNome());
                        nomeDespesaInput.setText(empresaD.getNome());
                        nomeLucroInput.setText(empresaL.getNome());
                        valorReceitaAnualInput.setText(String.valueOf(empresaR.receitaAnual()));
                        valorDespesaAnualInput.setText(String.valueOf(empresaD.despesaAnual()));
                        valorLucroInput.setText(String.valueOf(empresaL.receitaAnual()-empresaL.despesaAnual()));
                    }
                }
                if (opcao.equalsIgnoreCase("Mercado")) {
                    Empresas empresaR = null;
                    Empresas empresaD = null;
                    Empresas empresaL = null;
                    for(int i = 0;i<database.getLista_de_Empresas().size();i++){
                        if(empresaR==null) {
                            if (database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("Mercado")) {
                                empresaR = database.getLista_de_Empresas().get(i);
                                empresaD = database.getLista_de_Empresas().get(i);
                                empresaL = database.getLista_de_Empresas().get(i);
                            }
                        }
                    }
                    for (int i = 0; i < database.getLista_de_Empresas().size(); i++) {
                        if (database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("Mercado")) {
                            if (database.getLista_de_Empresas().get(i).receitaAnual() > empresaR.receitaAnual()) {
                                empresaR = database.getLista_de_Empresas().get(i);
                            }
                        }
                        if (database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("Mercado")) {
                            if (database.getLista_de_Empresas().get(i).despesaAnual() < empresaD.despesaAnual()) {
                                empresaD = database.getLista_de_Empresas().get(i);
                            }
                        }
                        if (database.getLista_de_Empresas().get(i).getTipo().equalsIgnoreCase("Mercado")) {
                            if ((database.getLista_de_Empresas().get(i).receitaAnual() - database.getLista_de_Empresas().get(i).despesaAnual()) > empresaL.receitaAnual()-empresaL.despesaAnual()) {
                                empresaL = database.getLista_de_Empresas().get(i);
                            }
                        }
                        nomeReceitaInput.setText(empresaR.getNome());
                        nomeDespesaInput.setText(empresaD.getNome());
                        nomeLucroInput.setText(empresaL.getNome());
                        valorReceitaAnualInput.setText(String.valueOf(empresaR.receitaAnual()));
                        valorDespesaAnualInput.setText(String.valueOf(empresaD.despesaAnual()));
                        valorLucroInput.setText(String.valueOf(empresaL.receitaAnual()-empresaL.despesaAnual()));
                    }
                }
            }
        });
        mainPanel.add(auxPanel,BorderLayout.CENTER);
        this.add(mainPanel);
        this.setVisible(true);
    }

}
