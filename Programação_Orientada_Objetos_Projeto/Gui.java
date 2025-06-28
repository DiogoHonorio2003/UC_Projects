package our_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe da interface
 */
public class Gui {
    /**
     * Janela Principal
     */
    private JFrame janelaPrincipal;
    /**
     * Painel principal
     */
    private JPanel mainPanel;
    /**
     * Instância propria de interface
     */
    private Gui selfInstance;
    /**
     * Popup da janela de criar
     */
    private CriarInterface popup_cria;
    /**
     * Popup da janela de listar
     */
    private ListarInterface popup_listar;
    /**
     * Popup da janela de apagar
     */
    private ApagarInterface popup_apagar;
    /**
     * Popup da janela de editar
     */
    private EditarInterface popup_editar;
    /**
     * Popup da janela que apresenta as empresas com maior receita anual, menor despeja anual e maior lucro anual
     */
    private Funcao2Interface popup_funcao2;
    /**
     * Popup da janela que apresenta as duas maiores empresas com capacidade de clientes diarios
     */
    private MaiorCapacidadeInterface popup_capacidadeC;
    /**
     * Butão que leva à função de listar
     */
    private JButton listarButton;
    /**
     * Butão que leva à função de criar
     */
    private JButton criarButton;
    /**
     * Butão que leva à função de editar
     */
    private JButton editarButton;
    /**
     * Butão que leva à função de apagar
     */
    private JButton apagarButton;
    /**
     * Butão que leva à função que apresenta as empresas com maior receita anual, menor despeja anual e maior lucro anual
     */
    private JButton funcao2Button;
    /**
     * Butão que leva à função que apresenta as duas maiores empresas com capacidade de clientes diarios
     */
    private JButton capacidadeC;
    /**
     * Instância da StarThrive
     */
    private StarThrive st;
    /**
     * label de texto
     */
    private JLabel label;

    /**
     * Construtor da interface
     * @param database Instância da StarThrive
     */
    public Gui(StarThrive database){

        this.st = database;
        initGui();
        selfInstance = this;
    }

    /**
     * Método que inicializa a interface
     */
    public void initGui() {
        janelaPrincipal = new JFrame();
        janelaPrincipal.setTitle("StartThrive");
        janelaPrincipal.setSize(600, 650);
        janelaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        label = new JLabel("StarThrive");
        label.setBounds(180,100,300,50);
        label.setFont(new Font("Times New Roman", Font.PLAIN, 50));
        mainPanel.add(label);
        listarButton = new JButton("Listar");
        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popup_listar = new ListarInterface(st,selfInstance);
                popup_listar.setVisible(true);
            }
        });
        criarButton = new JButton("Criar");
        criarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popup_cria = new CriarInterface(st,selfInstance);
                popup_cria.setVisible(true);
            }
        });
        editarButton = new JButton("Editar");
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popup_editar = new EditarInterface(st,selfInstance);
                popup_editar.setVisible(true);
            }
        });
        apagarButton = new JButton("Apagar");
        apagarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popup_apagar = new ApagarInterface(st,selfInstance);
                popup_apagar.setVisible(true);
            }
        });
        funcao2Button = new JButton("Empresas de cada tipo com maior Receita, menor Despesa e maior Lucro");
        funcao2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popup_funcao2 = new Funcao2Interface(st,selfInstance);
                popup_funcao2.setVisible(true);
            }
        });
        capacidadeC = new JButton("Empresas com maior Capacidade de clientes diarios");
        capacidadeC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popup_capacidadeC = new MaiorCapacidadeInterface(st,selfInstance);
                popup_capacidadeC.setVisible(true);
            }
        });


        listarButton.setBounds(50,250,200,100);
        criarButton.setBounds(50,400,200,100);
        editarButton.setBounds(325,250,200,100);
        apagarButton.setBounds(325,400,200,100);
        funcao2Button.setBounds(50,190,475,50);
        capacidadeC.setBounds(50,510,475,50);

        mainPanel.add(listarButton);
        mainPanel.add(criarButton);
        mainPanel.add(editarButton);
        mainPanel.add(apagarButton);
        mainPanel.add(funcao2Button);
        mainPanel.add(capacidadeC);

        janelaPrincipal.add(mainPanel);
        janelaPrincipal.setVisible(true);

    }
}

