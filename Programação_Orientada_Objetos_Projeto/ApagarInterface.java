package our_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe de interface que apaga uma empresa da lista
 */
public class ApagarInterface extends JFrame {
    /**
     * Instância de interface
     */
    private Gui gui;
    /**
     * Painel principal
     */
    private JPanel mainPanel;
    /**
     * Instância da StarThrive
     */
    private StarThrive database;
    /**
     * Lista de empresas da interface
     */
    private JList lista;
    /**
     * label de texto
     */
    private JLabel label;
    /**
     * Butão de apagar
     */
    private JButton apagarButton;

    /**
     * Método que apaga empresa da lista de empresas
     * @param database Instância da StarThrive
     * @param gui Instância de interface
     */
    public ApagarInterface(StarThrive database,Gui gui){
        super();

        this.gui = gui;
        this.database = database;

        this.setTitle("Lista de Empresas");
        this.setSize(300,600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        DefaultListModel listValues = new DefaultListModel();

        for (int i = 0; i < database.getLista_de_Empresas().size(); i++) {
            listValues.addElement(database.getLista_de_Empresas().get(i).getNome());
        }

        lista = new JList(listValues);

        JScrollPane listaEmp = new JScrollPane(lista);
        listaEmp.setBounds(0, 0, 300, 600);
        /*
        listaEmp.setVisible(true);
        lista.setVisible(true);
        */
        label = new JLabel("Selecione a empresa que deseja eliminar");
        mainPanel.add(label,BorderLayout.NORTH);
        mainPanel.add(listaEmp,BorderLayout.CENTER);
        mainPanel.setVisible(true);


        apagarButton = new JButton("Apagar");
        apagarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int empresa =lista.getSelectedIndex();

                if(!database.apagarEmpresa(empresa)) {
                    JOptionPane.showMessageDialog(null, "Erro: Nao foi possivel apagar a empresa", "Apagar Empresa", JOptionPane.PLAIN_MESSAGE);
                }

                listValues.removeAllElements();
                for (int i = 0; i < database.getLista_de_Empresas().size(); i++) {
                    listValues.addElement(database.getLista_de_Empresas().get(i).getNome());
                }

            }
        });
        mainPanel.add(apagarButton,BorderLayout.SOUTH);
        this.add(mainPanel);
        this.setVisible(true);
    }
}

