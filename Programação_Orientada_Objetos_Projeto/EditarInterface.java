package our_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe de interface que edita as empresas
 */
public class EditarInterface extends JFrame{
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
     * Butão guarda a empresa editada
     */
    private JButton saveButton;
    /**
     * Lista de empresas da interface
     */
    private JList lista;
    /**
     * Butão escolhe a empresa que se quer editar
     */
    private JButton chooseButton;
    /**
     * Painel auxiliar
     */
    private JPanel auxPanel;
    /**
     * Indice de empresa
     */
    private int index;

    /**
     * Método que edita a empresa
     * @param database Instância da StarThrive
     * @param gui Instância de interface
     */
    public EditarInterface(StarThrive database, Gui gui) {
        super();

        this.gui = gui;
        this.database = database;

        this.setTitle("Editar Empresa");
        this.setSize(1000, 500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);




        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        DefaultListModel listValues = new DefaultListModel();

        for (int i = 0; i < database.getLista_de_Empresas().size(); i++) {
            listValues.addElement(database.getLista_de_Empresas().get(i).getNome());
        }

        lista = new JList(listValues);
        JScrollPane listaEmp = new JScrollPane(lista);

        auxPanel = new JPanel();
        auxPanel.setLayout(new BorderLayout());
        auxPanel.add(listaEmp, BorderLayout.CENTER);


        JLabel labelNome = new JLabel("Nome da Empresa:");
        JLabel labelDistrito = new JLabel("Distrito:");
        JLabel labelLocalizacao = new JLabel("Localizacao:");
        JLabel labelCustoE = new JLabel("Custo por Empregado:");
        JLabel labelNE = new JLabel("Numero de Empregados:");
        JLabel labelSalarioMA = new JLabel("Salario Medio Anual:");
        JLabel labelMClientesD = new JLabel("Media Clientes Diarios:");
        JLabel labelMCafesD = new JLabel("Media Cafes Diarios:");
        JLabel labelFACafesD = new JLabel("Media Faturacao Anual Cafes por Dia:");
        JLabel labelNDFuncionamento = new JLabel("Numero Dias de Funcionamento:");
        JLabel labelNMesasI = new JLabel("Numero de Mesas Interiores:");
        JLabel labelMFAMesasID = new JLabel("Media Faturacao Anual das Mesas por Dia:");
        JLabel labelMDClientesDrive = new JLabel("Media Diaria de Clientes Drive:");
        JLabel labelMDFClientesDrive = new JLabel("Media Diaira Faturacao Clientes Drive:");
        JLabel labelNMesasE = new JLabel("Numero de Mesas Exteriores:");
        JLabel labelCLAMesaE = new JLabel("Custo de Licensa Anual por Mesa Exterior:");
        JLabel labelMFAMesaD = new JLabel("Media de Faturacao Anual por Mesa ao Dia:");
        JLabel labelNProdutos = new JLabel("Numero de Produtos:");
        JLabel labelMFAProduto = new JLabel("Media de Faturacao Anual do Produto:");
        JLabel labelCustoALimpeza = new JLabel("Custo Anual de Limpeza:");
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
        custoEInput.setText("0");
        JTextField nEInput = new JTextField(40);
        nEInput.setText("0");
        JTextField salarioMAInput = new JTextField(40);
        salarioMAInput.setText("0");
        JTextField mClientesDInput = new JTextField(40);
        mClientesDInput.setText("0");
        JTextField mCafesDInput = new JTextField(40);
        mCafesDInput.setText("0");
        JTextField mFCafesDInput = new JTextField(40);
        mFCafesDInput.setText("0");
        JTextField nDFuncionamentoInput = new JTextField(40);
        nDFuncionamentoInput.setText("0");
        JTextField nMesasIInput = new JTextField(40);
        nMesasIInput.setText("0");
        JTextField nMesasEInput = new JTextField(40);
        nMesasEInput.setText("0");
        JTextField mFAMesasIDInput = new JTextField(40);
        mFAMesasIDInput.setText("0");
        JTextField mDClientesDriveInput = new JTextField(40);
        mDClientesDriveInput.setText("0");
        JTextField mDFClientesDriveInput = new JTextField(40);
        mDFClientesDriveInput.setText("0");
        JTextField cLAMesaEInput = new JTextField(40);
        cLAMesaEInput.setText("0");
        JTextField mFAMesaDInput = new JTextField(40);
        mFAMesaDInput.setText("0");
        JTextField nProdutosInput = new JTextField(40);
        nProdutosInput.setText("0");
        JTextField mFAProdutoInput = new JTextField(40);
        mFAProdutoInput.setText("0");
        JTextField custoALimpezaInput = new JTextField(40);
        custoALimpezaInput.setText("0");
        JTextField areaCInput = new JTextField(40);
        areaCInput.setText("0");
        JTextField mFAMetro2Input = new JTextField(40);
        mFAMetro2Input.setText("0");
        JTextField tipoMInput = new JTextField(40);
        tipoMInput.setText("");

        labelNome.setBounds(20, 0, 200, 35);
        labelDistrito.setBounds(220, 0, 200, 35);
        labelLocalizacao.setBounds(420, 0, 200, 35);
        nomeInput.setBounds(20, 35, 200, 35);
        distritoInput.setBounds(220, 35, 200, 35);
        localizaoInput.setBounds(420, 35, 200, 35);
        labelCustoE.setBounds(20, 80, 200, 35);
        labelNE.setBounds(220, 80, 200, 35);
        labelSalarioMA.setBounds(420, 80, 200, 35);
        labelMClientesD.setBounds(620, 80, 200, 35);
        custoEInput.setBounds(20, 115, 200, 35);
        nEInput.setBounds(220, 115, 200, 35);
        salarioMAInput.setBounds(420, 115, 200, 35);
        mClientesDInput.setBounds(620, 115, 200, 35);
        labelMCafesD.setBounds(20, 160, 200, 35);
        labelFACafesD.setBounds(220, 160, 200, 35);
        mCafesDInput.setBounds(20, 195, 200, 35);
        mFCafesDInput.setBounds(220, 195, 200, 35);
        labelNDFuncionamento.setBounds(20, 160, 200, 35);
        labelNMesasI.setBounds(220, 160, 200, 35);
        labelMFAMesasID.setBounds(420, 160, 200, 35);
        labelMDClientesDrive.setBounds(620, 160, 200, 35);
        nDFuncionamentoInput.setBounds(20, 195, 200, 35);
        nMesasIInput.setBounds(220, 195, 200, 35);
        mFAMesasIDInput.setBounds(420, 195, 200, 35);
        mDClientesDriveInput.setBounds(620, 195, 200, 35);
        labelMDFClientesDrive.setBounds(20, 240, 200, 35);
        mDFClientesDriveInput.setBounds(20, 275, 200, 35);
        labelNMesasE.setBounds(420, 160, 200, 35);
        labelCLAMesaE.setBounds(620, 160, 200, 35);
        nMesasEInput.setBounds(420, 195, 200, 35);
        cLAMesaEInput.setBounds(620, 195, 200, 35);
        labelMFAMesaD.setBounds(20, 240, 200, 35);
        mFAMesaDInput.setBounds(20, 275, 200, 35);
        labelNProdutos.setBounds(20, 80, 200, 35);
        labelMFAProduto.setBounds(220, 80, 200, 35);
        labelCustoALimpeza.setBounds(420, 80, 200, 35);
        nProdutosInput.setBounds(20, 115, 200, 35);
        mFAProdutoInput.setBounds(220, 115, 200, 35);
        custoALimpezaInput.setBounds(420, 115, 200, 35);
        labelAreaC.setBounds(20, 80, 200, 35);
        labelMFAMetro2.setBounds(220, 80, 200, 35);
        labelTipoM.setBounds(620, 80, 200, 35);
        areaCInput.setBounds(20, 115, 200, 35);
        mFAMetro2Input.setBounds(220, 115, 200, 35);
        tipoMInput.setBounds(620, 115, 200, 35);

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




        labelNome.setVisible(false);
        labelDistrito.setVisible(false);
        labelLocalizacao.setVisible(false);
        labelCustoE.setVisible(false);
        mFCafesDInput.setVisible(false);
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
        nomeInput.setVisible(false);
        distritoInput.setVisible(false);
        localizaoInput.setVisible(false);



        mainPanel.add(gridPanel,BorderLayout.CENTER);


        chooseButton = new JButton("Escolher");
        chooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                index = lista.getSelectedIndex();
                Empresas empresa = database.getLista_de_Empresas().get(index);

                if (empresa.getTipo().equals("Cafe")) {
                    nomeInput.setText(empresa.getNome());
                    distritoInput.setText(empresa.getDistrito());
                    localizaoInput.setText(empresa.getLocalizacao());

                    String[] array = empresa.details();

                    custoEInput.setText(array[0]);
                    nEInput.setText(array[1]);
                    salarioMAInput.setText(array[2]);
                    mCafesDInput.setText(array[3]);
                    mFCafesDInput.setText(array[4]);
                    mClientesDInput.setText(array[5]);

                    labelNome.setVisible(true);
                    labelDistrito.setVisible(true);
                    labelLocalizacao.setVisible(true);
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
                    nomeInput.setVisible(true);
                    distritoInput.setVisible(true);
                    localizaoInput.setVisible(true);
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
                if (empresa.getTipo().equals("Pastelaria")) {
                    nomeInput.setText(empresa.getNome());
                    distritoInput.setText(empresa.getDistrito());
                    localizaoInput.setText(empresa.getLocalizacao());

                    String[] array = empresa.details();

                    custoEInput.setText(array[0]);
                    nEInput.setText(array[1]);
                    salarioMAInput.setText(array[2]);
                    mCafesDInput.setText(array[3]);
                    mFCafesDInput.setText(array[4]);
                    mClientesDInput.setText(array[5]);

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
                    nomeInput.setVisible(true);
                    distritoInput.setVisible(true);
                    localizaoInput.setVisible(true);
                    labelNome.setVisible(true);
                    labelDistrito.setVisible(true);
                    labelLocalizacao.setVisible(true);
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
                if (empresa.getTipo().equals("FastFood")) {
                    nomeInput.setText(empresa.getNome());
                    distritoInput.setText(empresa.getDistrito());
                    localizaoInput.setText(empresa.getLocalizacao());

                    String[] array = empresa.details();

                    custoEInput.setText(array[0]);
                    nEInput.setText(array[1]);
                    salarioMAInput.setText(array[2]);
                    mClientesDInput.setText(array[3]);
                    nDFuncionamentoInput.setText(array[4]);
                    nMesasIInput.setText(array[5]);
                    mFAMesasIDInput.setText(array[6]);
                    mDClientesDriveInput.setText(array[7]);
                    mDFClientesDriveInput.setText(array[8]);

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
                    nomeInput.setVisible(true);
                    distritoInput.setVisible(true);
                    localizaoInput.setVisible(true);
                    labelNome.setVisible(true);
                    labelDistrito.setVisible(true);
                    labelLocalizacao.setVisible(true);
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
                if (empresa.getTipo().equals("Locais")) {
                    nomeInput.setText(empresa.getNome());
                    distritoInput.setText(empresa.getDistrito());
                    localizaoInput.setText(empresa.getLocalizacao());

                    String[] array = empresa.details();

                    custoEInput.setText(array[0]);
                    nEInput.setText(array[1]);
                    salarioMAInput.setText(array[2]);
                    mClientesDInput.setText(array[3]);
                    nDFuncionamentoInput.setText(array[4]);
                    nMesasIInput.setText(array[5]);
                    nMesasEInput.setText(array[6]);
                    cLAMesaEInput.setText(array[7]);
                    mFAMesaDInput.setText(array[8]);

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
                    nomeInput.setVisible(true);
                    distritoInput.setVisible(true);
                    localizaoInput.setVisible(true);
                    labelNome.setVisible(true);
                    labelDistrito.setVisible(true);
                    labelLocalizacao.setVisible(true);
                    //
                    mFAMesasIDInput.setVisible(false);
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
                if (empresa.getTipo().equals("Frutaria")) {
                    nomeInput.setText(empresa.getNome());
                    distritoInput.setText(empresa.getDistrito());
                    localizaoInput.setText(empresa.getLocalizacao());

                    String[] array = empresa.details();

                    nProdutosInput.setText(array[0]);
                    mFAProdutoInput.setText(array[1]);
                    custoALimpezaInput.setText(array[2]);

                    labelNProdutos.setVisible(true);
                    labelMFAProduto.setVisible(true);
                    labelCustoALimpeza.setVisible(true);
                    nProdutosInput.setVisible(true);
                    mFAProdutoInput.setVisible(true);
                    custoALimpezaInput.setVisible(true);
                    nomeInput.setVisible(true);
                    distritoInput.setVisible(true);
                    localizaoInput.setVisible(true);
                    labelNome.setVisible(true);
                    labelDistrito.setVisible(true);
                    labelLocalizacao.setVisible(true);

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
                if (empresa.getTipo().equals("Mercado")) {
                    nomeInput.setText(empresa.getNome());
                    distritoInput.setText(empresa.getDistrito());
                    localizaoInput.setText(empresa.getLocalizacao());

                    String[] array = empresa.details();

                    areaCInput.setText(array[0]);
                    mFAMetro2Input.setText(array[1]);
                    custoALimpezaInput.setText(array[2]);
                    tipoMInput.setText(array[3]);

                    custoALimpezaInput.setVisible(true);
                    areaCInput.setVisible(true);
                    mFAMetro2Input.setVisible(true);
                    tipoMInput.setVisible(true);
                    labelCustoALimpeza.setVisible(true);
                    labelAreaC.setVisible(true);
                    labelMFAMetro2.setVisible(true);
                    labelTipoM.setVisible(true);
                    nomeInput.setVisible(true);
                    distritoInput.setVisible(true);
                    localizaoInput.setVisible(true);
                    labelNome.setVisible(true);
                    labelDistrito.setVisible(true);
                    labelLocalizacao.setVisible(true);
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

        saveButton = new JButton("Editar");
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


                Empresas empresa = database.getLista_de_Empresas().get(index);

                if(empresa.getTipo().equals("Cafe")){
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
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Media Clientes Drive", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        mCafesD = Float.parseFloat(mCafesDInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Media de Cafes Diario", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        mFCafesD = Float.parseFloat(mFCafesDInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Media Faturacao de Cafes Diaria", "Erro", JOptionPane.PLAIN_MESSAGE);
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
                if(empresa.getTipo().equals("Pastelaria")){
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
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Media de Bolos Diarios", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        mFCafesD = Float.parseFloat(mFCafesDInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Media Faturacao Bolos por Dia", "Erro", JOptionPane.PLAIN_MESSAGE);
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
                if(empresa.getTipo().equals("FastFood")){
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
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Num. Empregados", "Erro", JOptionPane.PLAIN_MESSAGE);
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
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Media Faturacao Mesas Interiores", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        nDFuncionamento = Integer.parseInt(nDFuncionamentoInput.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Num. Dias de Funcionamento", "Erro", JOptionPane.PLAIN_MESSAGE);
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
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Media Diaria Clientes Drive", "Erro", JOptionPane.PLAIN_MESSAGE);
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
                if(empresa.getTipo().equals("Locais")){
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
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Num. Empregados", "Erro", JOptionPane.PLAIN_MESSAGE);
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
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Media Faturacao Mesas Diaria", "Erro", JOptionPane.PLAIN_MESSAGE);
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
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Num. Mesas Exteriores", "Erro", JOptionPane.PLAIN_MESSAGE);
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
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Custo Licenca Mesa Exterior", "Erro", JOptionPane.PLAIN_MESSAGE);
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
                if(empresa.getTipo().equals("Frutaria")){
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
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Custo de Limpeza", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    if(nomeInput.getText()==null) isValid = false;
                    if(distritoInput.getText()==null) isValid = false;
                    if(localizaoInput.getText()==null) isValid = false;
                    if(nProdutosInput.getText()==null) isValid = false;
                    if(mFAProdutoInput.getText()==null) isValid = false;
                    if(custoALimpezaInput.getText()==null) isValid = false;
                }
                if(empresa.getTipo().equals("Mercado")){
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
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Area de Corredores", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                    try{
                        mFAMetro2 = Float.parseFloat(mFAMetro2Input.getText());
                    } catch (NumberFormatException ex){
                        isValid = false;
                        JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel efetuar o parse em: Media Faturacao por m2", "Erro", JOptionPane.PLAIN_MESSAGE);
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
                if((isValid)&(empresa.getTipo()!="")){
                    if(empresa.getTipo().equals("Cafe")){
                        Cafe cafe = new Cafe(nome,distrito,localizacao,custoE,nE,salarioMA,mClientesD,mCafesD,mFCafesD);
                        if(!database.editarEmpresa(index,cafe)){
                            JOptionPane.showMessageDialog(null, "Erro: Nao foi criar a empresa: Cafe.", "Criar Cafe", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                    if(empresa.getTipo().equals("Pastelaria")){
                        Pastelaria pastelaria = new Pastelaria(nome,distrito,localizacao,custoE,nE,salarioMA,mClientesD,mCafesD,mFCafesD);
                        if(!database.editarEmpresa(index,pastelaria)){
                            JOptionPane.showMessageDialog(null, "Erro: Nao foi criar a empresa: Pastelaria.", "Criar Pastelaria", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                    if(empresa.getTipo().equals("FastFood")){
                        Fast_Food fast_food = new Fast_Food(nome,distrito,localizacao,custoE,nE,salarioMA,mClientesD,nDFuncionamento,nMesasI,mFAMesasID,mDClientesDrive,mDFClientesDrive);
                        if(!database.editarEmpresa(index,fast_food)){
                            JOptionPane.showMessageDialog(null, "Erro: Nao foi criar a empresa: FastFood.", "Criar FastFood", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                    if(empresa.getTipo().equals("Locais")){
                        Locais locais = new Locais(nome,distrito,localizacao,custoE,nE,salarioMA,mClientesD,nDFuncionamento,nMesasI,nMesasE,cLAMesaE,mFAMesaD);
                        if(!database.editarEmpresa(index,locais)){
                            JOptionPane.showMessageDialog(null, "Erro: Nao foi criar a empresa: Locais.", "Criar Locais", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                    if(empresa.getTipo().equals("Frutaria")){
                        Frutaria frutaria = new Frutaria(nome,distrito,localizacao,nProdutos,mFAProduto,custoALimpeza);
                        if(!database.editarEmpresa(index,frutaria)){
                            JOptionPane.showMessageDialog(null, "Erro: Nao foi criar a empresa: Frutaria.", "Criar Frutaria", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                    if(empresa.getTipo().equals("Mercado")){
                        Mercado mercado = new Mercado(nome,distrito,localizacao,areaC,mFAMetro2,custoALimpeza,tipoM);
                        if(!database.editarEmpresa(index,mercado)){
                            JOptionPane.showMessageDialog(null, "Erro: Nao foi criar a empresa: Mercado.", "Criar Mercado", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Empresa editada com sucesso!", "Criar Empresa", JOptionPane.PLAIN_MESSAGE);
                    ReadWriteFile.writeFileObj(database.getLista_de_Empresas());

                }else JOptionPane.showMessageDialog(null, "Erro: Campos Invalidos.", "Criar Fruta", JOptionPane.PLAIN_MESSAGE);

                listValues.removeAllElements();
                for (int i = 0; i < database.getLista_de_Empresas().size(); i++) {
                    listValues.addElement(database.getLista_de_Empresas().get(i).getNome());
                }
                labelNome.setVisible(false);
                labelDistrito.setVisible(false);
                labelLocalizacao.setVisible(false);
                labelCustoE.setVisible(false);
                mFCafesDInput.setVisible(false);
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
                nomeInput.setVisible(false);
                distritoInput.setVisible(false);
                localizaoInput.setVisible(false);

            }
        });
        auxPanel.add(chooseButton,BorderLayout.SOUTH);
        mainPanel.add(auxPanel,BorderLayout.WEST);
        mainPanel.add(saveButton,BorderLayout.SOUTH);
        this.add(mainPanel);
        this.setVisible(true);
    }
}