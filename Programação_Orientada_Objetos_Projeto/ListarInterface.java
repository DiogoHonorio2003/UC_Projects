package our_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Classe de interface que lista as empresas
 */
public class ListarInterface extends JFrame{
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
     * Butão que atualiza a lista de empresas
     */
    private JButton updateButton;
    /**
     * label de texto
     */
    private JLabel label;

    /**
     * Método que lista as empresas
     * @param database Instância da StarThrive
     * @param gui Instância de interface
     */
    public ListarInterface(StarThrive database, Gui gui) {
        super();

        this.gui = gui;
        this.database = database;

        this.setTitle("Lista de Empresas");
        this.setSize(300,600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        label = new JLabel("Lista de empresas:");
        mainPanel.add(label,BorderLayout.NORTH);

        DefaultListModel listValues = new DefaultListModel();

        for (int i = 0; i < database.getLista_de_Empresas().size(); i++) {
            listValues.addElement(database.getLista_de_Empresas().get(i).getNome());
        }

        lista = new JList(listValues);

        JScrollPane listaEmp = new JScrollPane(lista);
        listaEmp.setBounds(0, 0, 300, 600);
        listaEmp.setVisible(true);
        lista.setVisible(true);
        mainPanel.add(listaEmp,BorderLayout.CENTER);
        mainPanel.setVisible(true);


        updateButton = new JButton("Atualizar");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listValues.removeAllElements();
                for (int i = 0; i < database.getLista_de_Empresas().size(); i++) {
                    listValues.addElement(database.getLista_de_Empresas().get(i).getNome());
                }
            }
        });
        mainPanel.add(updateButton,BorderLayout.SOUTH);
        this.add(mainPanel);


        this.setVisible(true);
    }
}
