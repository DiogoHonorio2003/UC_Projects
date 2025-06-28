package our_package;

/**
 * Classe restaurantes
 */
public class Restaurante extends Restauracao{
    /**
     * Numero de dias de funcionamento durante o ano
     */
    private int num_DiasFuncionamentoAno;

    /**
     * Construtor da classe recebe dados para a inicialização dos atributos
     * @param nome Nome do restaurante
     * @param distrito Distrito do restaurnate
     * @param localizacao Coordenadas do restaurante
     * @param tipo Tipo de empresa
     * @param custo_empregrado Custo de empregado
     * @param num_empregrados Numero de empregados
     * @param salario_medioAnual Salario medio anual
     * @param media_ClientesDiarios media de clientes diarios
     * @param num_DiasFuncionamentoAno numero de dias de funcionamento durante o ano
     */
    public Restaurante(String nome, String distrito, String localizacao, String tipo, float custo_empregrado, int num_empregrados, float salario_medioAnual, float media_ClientesDiarios, int num_DiasFuncionamentoAno) {
        super(nome, distrito, localizacao,tipo, custo_empregrado, num_empregrados, salario_medioAnual,media_ClientesDiarios);
        this.num_DiasFuncionamentoAno = num_DiasFuncionamentoAno;
    }
    public int getNum_DiasFuncionamentoAno() {
        return num_DiasFuncionamentoAno;
    }

    public void setNum_DiasFuncionamentoAno(int num_DiasFuncionamentoAno) {
        this.num_DiasFuncionamentoAno = num_DiasFuncionamentoAno;
    }
}
