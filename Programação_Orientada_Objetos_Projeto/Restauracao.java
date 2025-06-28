package our_package;

/**
 * Classe empresas de restauração
 */
public class Restauracao extends Empresas{
    /**
     * Custo por empregado
     */
    private float custo_empregrado;
    /**
     * Numero de empregados
     */
    private int num_empregrados;
    /**
     * Salario medio anual
     */
    private float salario_medioAnual;
    /**
     * Media clientes diarios
     */
    private float media_ClientesDiarios;

    /**
     * Construtor da classe recebe dados para a inicialização dos atributos
     * @param nome Nome da empresa de restauração
     * @param distrito Distrito da empresa de restauração
     * @param localizacao Localização da empresa de restauração
     * @param tipo Tipo de empresa
     * @param custo_empregrado Custo de empregado
     * @param num_empregrados Numero de empregados
     * @param salario_medioAnual Salario medio anual
     * @param media_ClientesDiarios Media de clientes diarios
     */
    public Restauracao(String nome, String distrito, String localizacao, String tipo, float custo_empregrado, int num_empregrados, float salario_medioAnual, float media_ClientesDiarios) {
        super(nome, distrito, localizacao,tipo);
        this.custo_empregrado = custo_empregrado;
        this.num_empregrados = num_empregrados;
        this.salario_medioAnual = salario_medioAnual;
        this.media_ClientesDiarios = media_ClientesDiarios;
    }

    public float getCusto_empregrado() {
        return custo_empregrado;
    }

    public void setCusto_empregrado(float custo_empregrado) {
        this.custo_empregrado = custo_empregrado;
    }

    public int getNum_empregrados() {
        return num_empregrados;
    }

    public void setNum_empregrados(int num_empregrados) {
        this.num_empregrados = num_empregrados;
    }

    public float getSalario_medioAnual() {
        return salario_medioAnual;
    }

    public void setSalario_medioAnual(float salario_medioAnual) {
        this.salario_medioAnual = salario_medioAnual;
    }

    public float getMedia_ClientesDiarios() {
        return media_ClientesDiarios;
    }

    public void setMedia_ClientesDiarios(float media_ClientesDiarios) {
        this.media_ClientesDiarios = media_ClientesDiarios;
    }

    public float despesaAnual() {
        return 0;
    }

    public float receitaAnual() {
        return 0;
    }
    public String lucro() {
        return "";
    }
    public float media_ClientesDiarios() {
        return 0;
    }
    public String tipo(){return "";}
    public String[] details(){return null;}
}
