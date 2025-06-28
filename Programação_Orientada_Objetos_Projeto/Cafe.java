package our_package;

/**
 * Classe cafe
 */
public class Cafe extends Restauracao {
    /**
     * Media de cafes vendidos num dia
     */
    private float media_CafesDia;
    /**
     * Media de faturação anual de cafes vendidos num dia
     */
    private float media_FaturacaoAnualCafesDia;

    /**
     * Construtor da classe recebe dados para a inicialização dos atributos
     * @param nome Nome do cafe
     * @param distrito Distrito do cafe
     * @param localizacao Coordenadas do cafe
     * @param custo_empregrado Custo por empregado
     * @param num_empregrados Numero de empregados
     * @param salario_medioAnual Salario medio anual
     * @param media_ClientesDiarios Media de clientes diarios
     * @param media_CafesDia Media de cafes vendidos num dia
     * @param media_FaturacaoAnualCafesDia Media de faturação de cafes vendidos num dia
     */
    public Cafe(String nome, String distrito, String localizacao, float custo_empregrado, int num_empregrados, float salario_medioAnual, float media_ClientesDiarios, float media_CafesDia, float media_FaturacaoAnualCafesDia) {
        super(nome, distrito, localizacao, "Cafe", custo_empregrado, num_empregrados, salario_medioAnual, media_ClientesDiarios);
        this.media_CafesDia = media_CafesDia;
        this.media_FaturacaoAnualCafesDia = media_FaturacaoAnualCafesDia;
    }

    /**
     * Método para cálculo da despesa anual do cafe
     * @return despesa anual do cafe
     */
    @Override
    public float despesaAnual() {
        return this.getNum_empregrados() * this.getSalario_medioAnual();
    }

    /**
     * Método para cálculo da receita anual do cafe
     * @return receita anual do cafe
     */
    @Override
    public float receitaAnual() {
        return this.getMedia_CafesDia() * this.getMedia_FaturacaoAnualCafesDia();
    }

    /**
     * Método para cálculo do lucro do cafe
     * @return se o cafe tem lucro ou não
     */
    @Override
    public String lucro() {
        if (receitaAnual() - despesaAnual() > 0) {
            return "Sim";
        } else {
            return "Nao";
        }
    }

    /**
     * Método de acesso externo à media de clientes diarios do cafe
     * @return Media de clientes diarios do cafe
     */
    @Override
    public float media_ClientesDiarios() {
        return this.getMedia_ClientesDiarios();
    }

    /**
     * Método de acesso externo ao tipo de empresa
     * @return tipo de empresa
     */
    @Override
    public String tipo() {
        return this.getTipo();
    }

    /**
     * Método de acesso externo a todos os dados do cafe
     * @return dados do cafe
     */
    @Override
    public String[] details() {
        String[] array = {String.valueOf(getCusto_empregrado()), String.valueOf(getNum_empregrados()), String.valueOf(getSalario_medioAnual()), String.valueOf(getMedia_CafesDia()), String.valueOf(getMedia_FaturacaoAnualCafesDia()),String.valueOf(getMedia_ClientesDiarios())};
        return array;
    }


    public float getMedia_CafesDia() {
        return media_CafesDia;
    }

    public void setMedia_CafesDia(float media_CafesDia) {
        this.media_CafesDia = media_CafesDia;
    }

    public float getMedia_FaturacaoAnualCafesDia() {
        return media_FaturacaoAnualCafesDia;
    }

    public void setMedia_FaturacaoAnualCafesDia(float media_FaturacaoAnualCafesDia) {
        this.media_FaturacaoAnualCafesDia = media_FaturacaoAnualCafesDia;
    }

    @Override
    public String toString() {
        return ("\n\tNome: "+getNome()+
                "\n\tTipo de Empresa: Cafe"+
                "\n\tDistrito: "+getDistrito()+
                "\n\tDespesa Anual: "+despesaAnual()+
                "\n\tReceita Anual: "+receitaAnual()+
                "\n\tLucro: "+lucro()+"\n");
    }
}
