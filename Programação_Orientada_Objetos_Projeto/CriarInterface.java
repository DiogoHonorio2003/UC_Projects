package our_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Queue;

/**
 * Classe de interface que cria uma empresa
 */
public class CriarInterface extends JFrame {
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
    private JPanel gridPanel;
    /**
     * Instância da StarThrive
     */
    private StarThrive database;
    /**
     * Combobox do tipo de empresa
     */
    private JComboBox<String> tipoE;
    /**
     * Butão de guardar
     */
    private JButton saveButton;

    /**
     * Método que cria uma nova empresa
     * @param database Instância da StarThrive
     * @param gui Instância de interface
     */
    public CriarInterface(StarThrive database, Gui gui){
        super();

        this.gui = gui;
        this.database = database;

        this.setTitle("Criar Empresa");
        this.setSize(1000,500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        String[] tipos = {"Cafe","Pastelaria","Fast-Food","Locais","Frutaria","Mercado"};
        tipoE = new JComboBox(tipos);

        mainPanel.add(tipoE,BorderLayout.NORTH);

        //
        JLabel labelNome = new JLabel("Nome da Empresa:");
        JLabel labelDistrito = new JLabel("Distrito:");
        JLabel labelLocalizacao = new JLabel("Localizacao:");


        // cafe, pastelaria,locais,fast food
        JLabel labelCustoE = new JLabel("Custo por Empregado:");
        JLabel labelNE = new JLabel("Numero de Empregados:");
        JLabel labelSalarioMA = new JLabel("Salario Medio Anual:");
        JLabel labelMClientesD = new JLabel("Media Clientes Diarios:");
        // cafe e Pastelaria
        JLabel labelMCafesD = new JLabel("Media Cafes Diarios:");
        JLabel labelFACafesD = new JLabel("Media Faturacao Anual Cafes por Dia:");
        // pastelaria

        //FastFood
        JLabel labelNDFuncionamento = new JLabel("Numero Dias de Funcionamento:");
        JLabel labelNMesasI = new JLabel("Numero de Mesas Interiores:");
        JLabel labelMFAMesasID = new JLabel("Media Faturacao Anual das Mesas por Dia:");
        JLabel labelMDClientesDrive = new JLabel("Media Diaria de Clientes Drive:");
        JLabel labelMDFClientesDrive= new JLabel("Media Diaira Faturacao Clientes Drive:");
        // locais
        JLabel labelNMesasE = new JLabel("Numero de Mesas Exteriores:");
        JLabel labelCLAMesaE = new JLabel("Custo de Licensa Anual por Mesa Exterior:");
        JLabel labelMFAMesaD = new JLabel("Media de Faturacao Anual por Mesa ao Dia:");

        // frutaria
        JLabel labelNProdutos = new JLabel("Numero de Produtos:");
        JLabel labelMFAProduto = new JLabel("Media de Faturacao Anual do Produto:");
        JLabel labelCustoALimpeza = new JLabel("Custo Anual de Limpeza:");

        // Mercado
        JLabel labelAreaC = new JLabel("Area dos Corredores:");
        JLabel labelMFAMetro2 = new JLabel("Media de Faturacao por Metro Quadradro");
        JLabel labelTipoM = new JLabel("Tipo de Mercado:");

        JTextField nomeInput = new JTextField(40);
        nomeInput.setText("");
        JTextField distritoInput = new JTextField(40);
        distritoInput.setText("");
        JTextField localizaoInput = new JTextField(40);
        localizaoInput.setText("");
        JTextField custoEInput = new JTextField(40);
        custoEInput.setText("");
        JTextField nEInput = new JTextField(40);
        nEInput.setText("");
        JTextField salarioMAInput = new JTextField(40);
        salarioMAInput.setText("");
        JTextField mClientesDInput = new JTextField(40);
        mClientesDInput.setText("");
        JTextField mCafesDInput = new JTextField(40);
        mCafesDInput.setText("");
        JTextField mFCafesDInput = new JTextField(40);
        mFCafesDInput.setText("");
        JTextField nDFuncionamentoInput = new JTextField(40);
        nDFuncionamentoInput.setText("");
        JTextField nMesasIInput = new JTextField(40);
        nMesasIInput.setText("");
        JTextField nMesasEInput = new JTextField(40);
        nMesasEInput.setText("");
        JTextField mFAMesasIDInput = new JTextField(40);
        mFAMesasIDInput.setText("");
        JTextField mDClientesDriveInput = new JTextField(40);
        mDClientesDriveInput.setText("");
        JTextField mDFClientesDriveInput = new JTextField(40);
        mDFClientesDriveInput.setText("");
        JTextField cLAMesaEInput = new JTextField(40);
        cLAMesaEInput.setText("");
        JTextField mFAMesaDInput = new JTextField(40);
        mFAMesaDInput.setText("");
        JTextField nProdutosInput = new JTextField(40);
        nProdutosInput.setText("");
        JTextField mFAProdutoInput = new JTextField(40);
        mFAProdutoInput.setText("");
        JTextField custoALimpezaInput = new JTextField(40);
        custoALimpezaInput.setText("");
        JTextField areaCInput = new JTextField(40);
        areaCInput.setText("");
        JTextField mFAMetro2Input = new JTextField(40);
        mFAMetro2Input.setText("");
        JTextField tipoMInput = new JTextField(40);
        tipoMInput.setText("");

        // Empresas
        labelNome.setBounds(20,0,200,35);
        labelDistrito.setBounds(220,0,200,35);
        labelLocalizacao.setBounds(420,0,200,35);
        nomeInput.setBounds(20,35,200,35);
        distritoInput.setBounds(220,35,200,35);
        localizaoInput.setBounds(420,35,200,35);
        // Cafe Pastelaria Fast Food Locais
        labelCustoE.setBounds(20,80,200,35);
        labelNE.setBounds(220,80,200,35);
        labelSalarioMA.setBounds(420,80,200,35);
        labelMClientesD.setBounds(620,80,200,35);
        custoEInput.setBounds(20,115,200,35);
        nEInput.setBounds(220,115,200,35);
        salarioMAInput.setBounds(420,115,200,35);
        mClientesDInput.setBounds(620,115,200,35);
        // Cafe Pastelaria
        labelMCafesD.setBounds(20,160,200,35);
        labelFACafesD.setBounds(220,160,200,35);
        mCafesDInput.setBounds(20,195,200,35);
        mFCafesDInput.setBounds(220,195,200,35);
        // Fast Food
        labelNDFuncionamento.setBounds(20,160,200,35);
        labelNMesasI.setBounds(220,160,200,35);
        labelMFAMesasID.setBounds(420,160,200,35);
        labelMDClientesDrive.setBounds(620,160,200,35);
        nDFuncionamentoInput.setBounds(20,195,200,35);
        nMesasIInput.setBounds(220,195,200,35);
        mFAMesasIDInput.setBounds(420,195,200,35);
        mDClientesDriveInput.setBounds(620,195,200,35);
        labelMDFClientesDrive.setBounds(20,240,200,35);
        mDFClientesDriveInput.setBounds(20,275,200,35);
        // Locais
        labelNMesasE.setBounds(420,160,200,35);
        labelCLAMesaE.setBounds(620,160,200,35);
        nMesasEInput.setBounds(420,195,200,35);
        cLAMesaEInput.setBounds(620,195,200,35);
        labelMFAMesaD.setBounds(20,240,200,35);
        mFAMesaDInput.setBounds(20,275,200,35);
        //Frutaria
        labelNProdutos.setBounds(20,80,200,35);
        labelMFAProduto.setBounds(220,80,200,35);
        labelCustoALimpeza.setBounds(420,80,200,35);
        nProdutosInput.setBounds(20,115,200,35);
        mFAProdutoInput.setBounds(220,115,200,35);
        custoALimpezaInput.setBounds(420,115,200,35);
        //Mercado
        labelAreaC.setBounds(20,80,200,35);
        labelMFAMetro2.setBounds(220,80,200,35);
        labelTipoM.setBounds(620,80,200,35);
        areaCInput.setBounds(20,115,200,35);
        mFAMetro2Input.setBounds(220,115,200,35);
        tipoMInput.setBounds(620,115,200,35);


        // adicionar ao panel
        gridPanel = new JPanel();
        gridPanel.setLayout(null);

        gridPanel.add(labelNome);
        gridPanel.add(labelDistrito);
        gridPanel.add(labelLocalizacao);
        gridPanel.add(labelCustoE);
        gridPanel.add(nomeInput);
        gridPanel.add(distritoInput);
        gridPanel.add(localizaoInput);
        gridPanel.add(custoEInput);
        gridPanel.add(labelNE);
        gridPanel.add(labelSalarioMA);
        gridPanel.add(labelMClientesD);
        gridPanel.add(labelMCafesD);
        gridPanel.add(nEInput);
        gridPanel.add(salarioMAInput);
        gridPanel.add(mClientesDInput);
        gridPanel.add(mCafesDInput);
        gridPanel.add(labelFACafesD);
        gridPanel.add(labelNDFuncionamento);
        gridPanel.add(labelNMesasI);
        gridPanel.add(labelNMesasE);
        gridPanel.add(mFCafesDInput);
        gridPanel.add(nDFuncionamentoInput);
        gridPanel.add(nMesasIInput);
        gridPanel.add(nMesasEInput);
        gridPanel.add(labelMFAMesasID);
        gridPanel.add(labelMDClientesDrive);
        gridPanel.add(labelMDFClientesDrive);
        gridPanel.add(labelCLAMesaE);
        gridPanel.add(mFAMesasIDInput);
        gridPanel.add(mFAMesaDInput);
        gridPanel.add(mDClientesDriveInput);
        gridPanel.add(mDFClientesDriveInput);
        gridPanel.add(cLAMesaEInput);
        gridPanel.add(labelMFAMesaD);
        gridPanel.add(labelNProdutos);
        gridPanel.add(labelMFAProduto);
        gridPanel.add(labelCustoALimpeza);
        gridPanel.add(mFAMesaDInput);
        gridPanel.add(nProdutosInput);
        gridPanel.add(mFAProdutoInput);
        gridPanel.add(custoALimpezaInput);
        gridPanel.add(labelAreaC);
        gridPanel.add(labelMFAMetro2);
        gridPanel.add(labelTipoM);
        gridPanel.add(areaCInput);
        gridPanel.add(mFAMetro2Input);
        gridPanel.add(tipoMInput);

        labelMCafesD.setText("Media de Cafes por Dia");
        labelFACafesD.setText("Faturacao de Cafes por Dia");
        labelCustoE.setVisible(true);
        mFCafesDInput.setVisible(true);
        labelNE.setVisible(true);
        labelSalarioMA.setVisible(true);
        labelMClientesD.setVisible(true);
        labelMCafesD.setVisible(true);
        labelFACafesD.setVisible(true);
        custoEInput.setVisible(true);
        nEInput.setVisible(true);
        salarioMAInput.setVisible(true);
        mClientesDInput.setVisible(true);
        mCafesDInput.setVisible(true);
        labelNDFuncionamento.setVisible(false);
        labelNMesasI.setVisible(false);
        labelMFAMesasID.setVisible(false);
        labelMDClientesDrive.setVisible(false);
        labelMDFClientesDrive.setVisible(false);
        labelNMesasE.setVisible(false);
        labelCLAMesaE.setVisible(false);
        labelMFAMesaD.setVisible(false);
        labelNProdutos.setVisible(false);
        labelMFAProduto.setVisible(false);
        labelCustoALimpeza.setVisible(false);
        labelAreaC.setVisible(false);
        labelMFAMetro2.setVisible(false);
        labelTipoM.setVisible(false);
        nDFuncionamentoInput.setVisible(false);
        nMesasIInput.setVisible(false);
        mFAMesaDInput.setVisible(false);
        mDClientesDriveInput.setVisible(false);
        mDFClientesDriveInput.setVisible(false);
        nMesasEInput.setVisible(false);
        cLAMesaEInput.setVisible(false);
        mFAMesaDInput.setVisible(false);
        nProdutosInput.setVisible(false);
        mFAProdutoInput.setVisible(false);
        custoALimpezaInput.setVisible(false);
        areaCInput.setVisible(false);
        mFAMetro2Input.setVisible(false);
        tipoMInput.setVisible(false);
        mFAMesaDInput.setVisible(false);
        mFAMesasIDInput.setVisible(false);

        mainPanel.add(gridPanel,BorderLayout.CENTER);
        mainPanel.setVisible(true);
        this.add(mainPanel);
        tipoE.setSelectedIndex(0);



        tipoE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String opcao = (String) tipoE.getSelectedItem();

                if(opcao=="Cafe"){
                    labelMCafesD.setText("Media de Cafes por Dia");
                    labelFACafesD.setText("Faturacao de Cafes por Dia");
                    labelCustoE.setVisible(true);
                    labelNE.setVisible(true);
                    labelSalarioMA.setVisible(true);
                    labelMClientesD.setVisible(true);
                    labelMCafesD.setVisible(true);
                    labelFACafesD.setVisible(true);
                    custoEInput.setVisible(true);
                    nEInput.setVisible(true);
                    salarioMAInput.setVisible(true);
                    mClientesDInput.setVisible(true);
                    mCafesDInput.setVisible(true);
                    mFCafesDInput.setVisible(true);
                    //
                    mFAMesasIDInput.setVisible(false);
                    labelNDFuncionamento.setVisible(false);
                    labelNMesasI.setVisible(false);
                    labelMFAMesasID.setVisible(false);
                    labelMDClientesDrive.setVisible(false);
                    labelMDFClientesDrive.setVisible(false);
                    labelNMesasE.setVisible(false);
                    labelCLAMesaE.setVisible(false);
                    labelMFAMesaD.setVisible(false);
                    labelNProdutos.setVisible(false);
                    labelMFAProduto.setVisible(false);
                    labelCustoALimpeza.setVisible(false);
                    labelAreaC.setVisible(false);
                    labelMFAMetro2.setVisible(false);
                    labelTipoM.setVisible(false);
                    nDFuncionamentoInput.setVisible(false);
                    nMesasIInput.setVisible(false);
                    mFAMesaDInput.setVisible(false);
                    mDClientesDriveInput.setVisible(false);
                    mDFClientesDriveInput.setVisible(false);
                    nMesasEInput.setVisible(false);
                    cLAMesaEInput.setVisible(false);
                    mFAMesaDInput.setVisible(false);
                    nProdutosInput.setVisible(false);
                    mFAProdutoInput.setVisible(false);
                    custoALimpezaInput.setVisible(false);
                    areaCInput.setVisible(false);
                    mFAMetro2Input.setVisible(false);
                    tipoMInput.setVisible(false);
                }
                if(opcao=="Pastelaria"){
                    labelMCafesD.setText("Media de Bolos por Dia");
                    labelFACafesD.setText("Faturacao de Bolos por Dia");
                    labelCustoE.setVisible(true);
                    labelNE.setVisible(true);
                    labelSalarioMA.setVisible(true);
                    labelMClientesD.setVisible(true);
                    labelMCafesD.setVisible(true);
                    labelFACafesD.setVisible(true);
                    custoEInput.setVisible(true);
                    nEInput.setVisible(true);
                    salarioMAInput.setVisible(true);
                    mClientesDInput.setVisible(true);
                    mCafesDInput.setVisible(true);
                    mFCafesDInput.setVisible(true);
                    //
                    labelNDFuncionamento.setVisible(false);
                    labelNMesasI.setVisible(false);
                    labelMFAMesasID.setVisible(false);
                    labelMDClientesDrive.setVisible(false);
                    labelMDFClientesDrive.setVisible(false);
                    labelNMesasE.setVisible(false);
                    labelCLAMesaE.setVisible(false);
                    labelMFAMesaD.setVisible(false);
                    labelNProdutos.setVisible(false);
                    labelMFAProduto.setVisible(false);
                    labelCustoALimpeza.setVisible(false);
                    labelAreaC.setVisible(false);
                    labelMFAMetro2.setVisible(false);
                    labelTipoM.setVisible(false);
                    nDFuncionamentoInput.setVisible(false);
                    nMesasIInput.setVisible(false);
                    mFAMesaDInput.setVisible(false);
                    mDClientesDriveInput.setVisible(false);
                    mDFClientesDriveInput.setVisible(false);
                    nMesasEInput.setVisible(false);
                    cLAMesaEInput.setVisible(false);
                    mFAMesaDInput.setVisible(false);
                    nProdutosInput.setVisible(false);
                    mFAProdutoInput.setVisible(false);
                    custoALimpezaInput.setVisible(false);
                    areaCInput.setVisible(false);
                    mFAMetro2Input.setVisible(false);
                    tipoMInput.setVisible(false);
                    mFAMesasIDInput.setVisible(false);
                }
                if(opcao=="Fast-Food"){
                    labelCustoE.setVisible(true);
                    labelNE.setVisible(true);
                    labelSalarioMA.setVisible(true);
                    labelMClientesD.setVisible(true);
                    labelNDFuncionamento.setVisible(true);
                    labelNMesasI.setVisible(true);
                    labelMFAMesasID.setVisible(true);
                    labelMDClientesDrive.setVisible(true);
                    labelMDFClientesDrive.setVisible(true);
                    custoEInput.setVisible(true);
                    nEInput.setVisible(true);
                    salarioMAInput.setVisible(true);
                    mFAMesasIDInput.setVisible(true);
                    nDFuncionamentoInput.setVisible(true);
                    nMesasIInput.setVisible(true);
                    mDClientesDriveInput.setVisible(true);
                    mDFClientesDriveInput.setVisible(true);
                    mClientesDInput.setVisible(true);
                    //
                    mFCafesDInput.setVisible(false);
                    labelFACafesD.setVisible(false);
                    labelMCafesD.setVisible(false);
                    mCafesDInput.setVisible(false);
                    labelNMesasE.setVisible(false);
                    labelCLAMesaE.setVisible(false);
                    labelMFAMesaD.setVisible(false);
                    labelNProdutos.setVisible(false);
                    labelMFAProduto.setVisible(false);
                    mFAMesaDInput.setVisible(false);
                    labelCustoALimpeza.setVisible(false);
                    labelAreaC.setVisible(false);
                    labelMFAMetro2.setVisible(false);
                    labelTipoM.setVisible(false);
                    nMesasEInput.setVisible(false);
                    cLAMesaEInput.setVisible(false);
                    nProdutosInput.setVisible(false);
                    mFAProdutoInput.setVisible(false);
                    custoALimpezaInput.setVisible(false);
                    areaCInput.setVisible(false);
                    mFAMetro2Input.setVisible(false);
                    tipoMInput.setVisible(false);
                }
                if(opcao=="Locais"){
                    labelCustoE.setVisible(true);
                    labelNE.setVisible(true);
                    labelSalarioMA.setVisible(true);
                    labelMClientesD.setVisible(true);
                    custoEInput.setVisible(true);
                    nEInput.setVisible(true);
                    salarioMAInput.setVisible(true);
                    labelNDFuncionamento.setVisible(true);
                    labelNMesasI.setVisible(true);
                    labelMFAMesasID.setVisible(true);

                    nDFuncionamentoInput.setVisible(true);
                    nMesasIInput.setVisible(true);
                    mClientesDInput.setVisible(true);
                    labelNMesasE.setVisible(true);
                    nMesasEInput.setVisible(true);
                    cLAMesaEInput.setVisible(true);
                    labelCLAMesaE.setVisible(true);
                    labelMFAMesaD.setVisible(true);
                    mFAMesaDInput.setVisible(true);
                    mFAMesasIDInput.setVisible(true);
                    //
                    mFCafesDInput.setVisible(false);
                    labelMDClientesDrive.setVisible(false);
                    labelMDFClientesDrive.setVisible(false);
                    mDClientesDriveInput.setVisible(false);
                    mDFClientesDriveInput.setVisible(false);
                    labelFACafesD.setVisible(false);
                    labelMCafesD.setVisible(false);
                    mCafesDInput.setVisible(false);
                    labelNProdutos.setVisible(false);
                    labelMFAProduto.setVisible(false);
                    labelCustoALimpeza.setVisible(false);
                    labelAreaC.setVisible(false);
                    labelMFAMetro2.setVisible(false);
                    labelTipoM.setVisible(false);
                    nProdutosInput.setVisible(false);
                    mFAProdutoInput.setVisible(false);
                    custoALimpezaInput.setVisible(false);
                    areaCInput.setVisible(false);
                    mFAMetro2Input.setVisible(false);
                    tipoMInput.setVisible(false);
                    labelMFAMesasID.setVisible(false);

                }
                if(opcao=="Frutaria"){
                    labelNProdutos.setVisible(true);
                    labelMFAProduto.setVisible(true);
                    labelCustoALimpeza.setVisible(true);
                    nProdutosInput.setVisible(true);
                    mFAProdutoInput.setVisible(true);
                    custoALimpezaInput.setVisible(true);
                    //
                    labelCustoE.setVisible(false);
                    labelNE.setVisible(false);
                    labelSalarioMA.setVisible(false);
                    labelMClientesD.setVisible(false);
                    labelMCafesD.setVisible(false);
                    labelFACafesD.setVisible(false);
                    custoEInput.setVisible(false);
                    nEInput.setVisible(false);
                    salarioMAInput.setVisible(false);
                    mClientesDInput.setVisible(false);
                    mCafesDInput.setVisible(false);
                    labelNDFuncionamento.setVisible(false);
                    labelNMesasI.setVisible(false);
                    labelMFAMesasID.setVisible(false);
                    labelMDClientesDrive.setVisible(false);
                    labelMDFClientesDrive.setVisible(false);
                    labelNMesasE.setVisible(false);
                    labelCLAMesaE.setVisible(false);
                    labelMFAMesaD.setVisible(false);
                    labelAreaC.setVisible(false);
                    labelMFAMetro2.setVisible(false);
                    labelTipoM.setVisible(false);
                    nDFuncionamentoInput.setVisible(false);
                    nMesasIInput.setVisible(false);
                    mFAMesaDInput.setVisible(false);
                    mDClientesDriveInput.setVisible(false);
                    mDFClientesDriveInput.setVisible(false);
                    nMesasEInput.setVisible(false);
                    cLAMesaEInput.setVisible(false);
                    mFAMesaDInput.setVisible(false);
                    areaCInput.setVisible(false);
                    mFAMetro2Input.setVisible(false);
                    tipoMInput.setVisible(false);
                    mFAMesasIDInput.setVisible(false);
                    mFCafesDInput.setVisible(false);

                }
                if(opcao=="Mercado"){
                    custoALimpezaInput.setVisible(true);
                    areaCInput.setVisible(true);
                    mFAMetro2Input.setVisible(true);
                    tipoMInput.setVisible(true);
                    labelCustoALimpeza.setVisible(true);
                    labelAreaC.setVisible(true);
                    labelMFAMetro2.setVisible(true);
                    labelTipoM.setVisible(true);
                    //
                    labelCustoE.setVisible(false);
                    labelNE.setVisible(false);
                    labelSalarioMA.setVisible(false);
                    labelMClientesD.setVisible(false);
                    labelMCafesD.setVisible(false);
                    labelFACafesD.setVisible(false);
                    custoEInput.setVisible(false);
                    nEInput.setVisible(false);
                    salarioMAInput.setVisible(false );
                    mClientesDInput.setVisible(false);
                    mCafesDInput.setVisible(false);
                    labelNDFuncionamento.setVisible(false);
                    labelNMesasI.setVisible(false);
                    labelMFAMesasID.setVisible(false);
                    labelMDClientesDrive.setVisible(false);
                    labelMDFClientesDrive.setVisible(false);
                    labelNMesasE.setVisible(false);
                    labelCLAMesaE.setVisible(false);
                    labelMFAMesaD.setVisible(false);
                    labelNProdutos.setVisible(false);
                    labelMFAProduto.setVisible(false);
                    nDFuncionamentoInput.setVisible(false);
                    nMesasIInput.setVisible(false);
                    mFAMesaDInput.setVisible(false);
                    mDClientesDriveInput.setVisible(false);
                    mDFClientesDriveInput.setVisible(false);
                    nMesasEInput.setVisible(false);
                    cLAMesaEInput.setVisible(false);
                    mFAMesaDInput.setVisible(false);
                    nProdutosInput.setVisible(false);
                    mFAProdutoInput.setVisible(false);
                    mFAMesasIDInput.setVisible(false);
                    mFCafesDInput.setVisible(false);

                }
            }
        });
        saveButton = new JButton("Guardar");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isValid = true;
                String nome = "";
                String distrito= "";
                String localizacao= "";
                float custoE = 0;
                int nE= 0;
                float salarioMA= 0;
                float mClientesD= 0;
                float mCafesD= 0;
                float mFCafesD= 0;
                float mFAMesasID= 0;
                int nDFuncionamento= 0;
                int nMesasI= 0;
                float mDClientesDrive= 0;
                float mDFClientesDrive= 0;
                float mFAMesaD= 0;
                int nMesasE= 0;
                float cLAMesaE= 0;
                int nProdutos= 0;
                float mFAProduto= 0;
                float custoALimpeza= 0;
                float areaC= 0;
                float mFAMetro2= 0;
                String tipoM= "";

                String opcao = (String) tipoE.getSelectedItem();
                if(opcao.equals("Cafe")){
                    nome = nomeInput.getText();
                    distrito = distritoInput.getText();
                    localizacao = localizaoInput.getText();
                    try{
                        custoE = Float.parseFloat(custoEInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Custo por Empregado", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        nE = Integer.parseInt(nEInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Num. de Empregados", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        salarioMA = Float.parseFloat(salarioMAInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Salario Medio Anual", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        mClientesD = Float.parseFloat(mClientesDInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Media Clientes Diarios", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        mCafesD = Float.parseFloat(mCafesDInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Media de Cafes Diarios", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        mFCafesD = Float.parseFloat(mFCafesDInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Media Faturacao de Cafes Diarios", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    if(nomeInput.getText()==null) isValid = false;
                    if(distritoInput.getText()==null) isValid = false;
                    if(localizaoInput.getText()==null) isValid = false;
                    if(custoEInput.getText()==null) isValid = false;
                    if(nEInput.getText()==null) isValid = false;
                    if(salarioMAInput.getText()==null) isValid = false;
                    if(mClientesDInput.getText()==null) isValid = false;
                    if(mCafesDInput.getText()==null) isValid = false;
                    if(mFCafesDInput.getText()==null) isValid = false;
                }
                if(opcao.equals("Pastelaria")){
                    nome = nomeInput.getText();
                    distrito = distritoInput.getText();
                    localizacao = localizaoInput.getText();
                    try{
                        custoE = Float.parseFloat(custoEInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Custo por Empregado", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        nE = Integer.parseInt(nEInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Num. de Empregados", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        salarioMA = Float.parseFloat(salarioMAInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Salario Medio Anual", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        mClientesD = Float.parseFloat(mClientesDInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Media Clientes Diarios", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        mCafesD = Float.parseFloat(mCafesDInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Medio de Bolos Diarios", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        mFCafesD = Float.parseFloat(mFCafesDInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Media Faturacao Bolos Diarios", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    if(nomeInput.getText()==null) isValid = false;
                    if(distritoInput.getText()==null) isValid = false;
                    if(localizaoInput.getText()==null) isValid = false;
                    if(custoEInput.getText()==null) isValid = false;
                    if(nEInput.getText()==null) isValid = false;
                    if(salarioMAInput.getText()==null) isValid = false;
                    if(mClientesDInput.getText()==null) isValid = false;
                    if(mCafesDInput.getText()==null) isValid = false;
                    if(mFCafesDInput.getText()==null) isValid = false;
                }
                if(opcao.equals("Fast-Food")){
                    nome = nomeInput.getText();
                    distrito = distritoInput.getText();
                    localizacao = localizaoInput.getText();
                    try{
                        custoE = Float.parseFloat(custoEInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Custo por Empregado", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        nE = Integer.parseInt(nEInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Num. de Empreagos", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        salarioMA = Float.parseFloat(salarioMAInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Salario Medio Anual", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        mFAMesasID = Float.parseFloat(mFAMesasIDInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Media Faturacao A. Mesas Interiores", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        nDFuncionamento = Integer.parseInt(nDFuncionamentoInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Num. de Dias de Funcionamento", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        nMesasI = Integer.parseInt(nMesasIInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Num. Mesas Interiores", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        mDClientesDrive = Float.parseFloat(mDClientesDriveInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Media Diaria CLientes Drive", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        mDFClientesDrive = Float.parseFloat( mDFClientesDriveInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Media Diaria Faturacao Clientes Drive", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        mClientesD = Float.parseFloat(mClientesDInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Media Clientes Diarios", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    if(nomeInput.getText()==null) isValid = false;
                    if(distritoInput.getText()==null) isValid = false;
                    if(localizaoInput.getText()==null) isValid = false;
                    if(custoEInput.getText()==null) isValid = false;
                    if(nEInput.getText()==null) isValid = false;
                    if(salarioMAInput.getText()==null) isValid = false;
                    if(mFAMesasIDInput.getText()==null) isValid = false;
                    if(nDFuncionamentoInput.getText()==null) isValid = false;
                    if(nMesasIInput.getText()==null) isValid = false;
                    if(mDClientesDriveInput.getText()==null) isValid = false;
                    if(mDFClientesDriveInput.getText()==null) isValid = false;
                    if(mClientesDInput.getText()==null) isValid = false;
                }
                if(opcao.equals("Locais")){
                    nome = nomeInput.getText();
                    distrito = distritoInput.getText();
                    localizacao = localizaoInput.getText();
                    try{
                        custoE = Float.parseFloat(custoEInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Custo por Empregado", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        nE = Integer.parseInt(nEInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Num. de Empregados", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        salarioMA = Float.parseFloat(salarioMAInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Salario Medio Anual", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        mFAMesaD = Float.parseFloat(mFAMesaDInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Media Faturacao A. Mesas Diaria", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        nDFuncionamento = Integer.parseInt(nDFuncionamentoInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Num. Dias de Funcionamento", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        nMesasE = Integer.parseInt(nMesasEInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Num. de Mesas Exteriores", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        nMesasI = Integer.parseInt(nMesasIInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Num. Mesas Interiores", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        cLAMesaE = Float.parseFloat( cLAMesaEInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Custo Lincensa A. Mesas Exteriores", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        mClientesD = Float.parseFloat(mClientesDInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Media Clientes Diarios", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    if(nomeInput.getText()==null) isValid = false;
                    if(distritoInput.getText()==null) isValid = false;
                    if(localizaoInput.getText()==null) isValid = false;
                    if(custoEInput.getText()==null) isValid = false;
                    if(nEInput.getText()==null) isValid = false;
                    if(salarioMAInput.getText()==null) isValid = false;
                    if(mFAMesaDInput.getText()==null) isValid = false;
                    if(nDFuncionamentoInput.getText()==null) isValid = false;
                    if(nMesasIInput.getText()==null) isValid = false;
                    if(cLAMesaEInput.getText()==null) isValid = false;
                    if(mClientesDInput.getText()==null) isValid = false;
                    if(nMesasEInput.getText()==null) isValid = false;

                }
                if(opcao.equals("Frutaria")){
                    nome = nomeInput.getText();
                    distrito = distritoInput.getText();
                    localizacao = localizaoInput.getText();
                    try{
                        nProdutos = Integer.parseInt(nProdutosInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Num. de Produtos", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        mFAProduto = Float.parseFloat(mFAProdutoInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Media Faturacao A. Produto", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        custoALimpeza = Float.parseFloat(custoALimpezaInput.getText());
                    } catch (NumberFormatException ex) {
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Custo A. Limpeza", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    if(nomeInput.getText()==null) isValid = false;
                    if(distritoInput.getText()==null) isValid = false;
                    if(localizaoInput.getText()==null) isValid = false;
                    if(nProdutosInput.getText()==null) isValid = false;
                    if(mFAProdutoInput.getText()==null) isValid = false;
                    if(custoALimpezaInput.getText()==null) isValid = false;
                }
                if(opcao.equals("Mercado")){
                    nome = nomeInput.getText();
                    distrito = distritoInput.getText();
                    localizacao = localizaoInput.getText();
                    tipoM = tipoMInput.getText();
                    if(!((tipoM.equalsIgnoreCase("Hiper")|tipoM.equalsIgnoreCase("Super")|tipoM.equalsIgnoreCase("Mini")))){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel identificar o tipo de mercado (Hiper,Super,Mini)", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        areaC = Float.parseFloat(areaCInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Area Corredores", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        mFAMetro2 = Float.parseFloat(mFAMetro2Input.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Media Faturacao A. Metro Quadrado", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        custoALimpeza = Float.parseFloat(custoALimpezaInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Custo A. Limpeza", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    if(nomeInput.getText()==null) isValid = false;
                    if(distritoInput.getText()==null) isValid = false;
                    if(localizaoInput.getText()==null) isValid = false;
                    if(areaCInput.getText()==null) isValid = false;
                    if(tipoMInput.getText()==null) isValid = false;
                    if(mFAMetro2Input.getText()==null) isValid = false;
                    if(custoALimpezaInput.getText()==null) isValid = false;
                }
                if(isValid){
                    if(opcao.equals("Cafe")){
                        Cafe cafe = new Cafe(nome,distrito,localizacao,custoE,nE,salarioMA,mClientesD,mCafesD,mFCafesD);
                        if(!database.criarEmpresa(cafe)){
                            JOptionPane.showMessageDialog(null, "Erro: Nao foi criar a empresa: Cafe.", "Criar Cafe", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                    if(opcao.equals("Pastelaria")){
                        Pastelaria pastelaria = new Pastelaria(nome,distrito,localizacao,custoE,nE,salarioMA,mClientesD,mCafesD,mFCafesD);
                        if(!database.criarEmpresa(pastelaria)){
                            JOptionPane.showMessageDialog(null, "Erro: Nao foi criar a empresa: Pastelaria.", "Criar Pastelaria", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                    if(opcao.equals("Fast-Food")){
                        Fast_Food fast_food = new Fast_Food(nome,distrito,localizacao,custoE,nE,salarioMA,mClientesD,nDFuncionamento,nMesasI,mFAMesasID,mDClientesDrive,mDFClientesDrive);
                        if(!database.criarEmpresa(fast_food)){
                            JOptionPane.showMessageDialog(null, "Erro: Nao foi criar a empresa: FastFood.", "Criar FastFood", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                    if(opcao.equals("Locais")){
                        Locais locais = new Locais(nome,distrito,localizacao,custoE,nE,salarioMA,mClientesD,nDFuncionamento,nMesasI,nMesasE,cLAMesaE,mFAMesaD);
                        if(!database.criarEmpresa(locais)){
                            JOptionPane.showMessageDialog(null, "Erro: Nao foi criar a empresa: Locais.", "Criar Locais", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                    if(opcao.equals("Frutaria")){
                        Frutaria frutaria = new Frutaria(nome,distrito,localizacao,nProdutos,mFAProduto,custoALimpeza);
                        if(!database.criarEmpresa(frutaria)){
                            JOptionPane.showMessageDialog(null, "Erro: Nao foi criar a empresa: Frutaria.", "Criar Frutaria", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                    if(opcao.equals("Mercado")){
                        Mercado mercado = new Mercado(nome,distrito,localizacao,areaC,mFAMetro2,custoALimpeza,tipoM);
                        if(!database.criarEmpresa(mercado)){
                            JOptionPane.showMessageDialog(null, "Erro: Nao foi criar a empresa: Mercado.", "Criar Mercado", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Empresa criada com sucesso!", "Criar Empresa", JOptionPane.PLAIN_MESSAGE);
                    ReadWriteFile.writeFileObj(database.getLista_de_Empresas());
                } else JOptionPane.showMessageDialog(null, "Erro: Campos Invalidos.", "Criar Fruta", JOptionPane.PLAIN_MESSAGE);


            }
        });
        mainPanel.add(saveButton,BorderLayout.SOUTH);
        this.setVisible(true);
    }
}
