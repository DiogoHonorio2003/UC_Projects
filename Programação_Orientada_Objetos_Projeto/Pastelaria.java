package our_package;

/**
 * Classe pastelaria
 */
public class Pastelaria extends Restauracao{
    /**
     * Media de bolos vendidos num dia
     */
    private float media_BolosDia;
    /**
     * media de faturação anual de bolos vendidos ao dia
     */
    private float media_FaturacaoAnualBolosDia;

    /**
     * Construtor da classe recebe dados para a inicialização dos atributos
     * @param nome Nome da pastelaria
     * @param distrito Distrito da pastelaria
     * @param localizacao Coordenadas da pastelaria
     * @param custo_empregrado Custo por empregado
     * @param num_empregrados Numero de empregados
     * @param salario_medioAnual Salario medio anual
     * @param media_ClientesDiarios Media de clientes diarios
     * @param media_BolosDia media de bolos vendidos num dia
     * @param media_FaturacaoAnualBolosDia media de faturação Anual de bolos vendidos ao dia
     */
    public Pastelaria(String nome, String distrito, String localizacao, float custo_empregrado, int num_empregrados, float salario_medioAnual, float media_ClientesDiarios, float media_BolosDia, float media_FaturacaoAnualBolosDia) {
        super(nome, distrito, localizacao,"Pastelaria", custo_empregrado, num_empregrados, salario_medioAnual,media_ClientesDiarios);
        this.media_BolosDia = media_BolosDia;
        this.media_FaturacaoAnualBolosDia = media_FaturacaoAnualBolosDia;
    }

    /**
     * Método para cálculo da despesa anual da pastelaria
     * @return despesa anual da pastelaria
     */
    @Override
    public float despesaAnual(){
        return this.getNum_empregrados()*this.getSalario_medioAnual();
    }

    /**
     * Método para cálculo da receita anual da pastelaria
     * @return receita anual da pastelaria
     */
    @Override
    public float receitaAnual(){
        return this.getMedia_BolosDia()*this.getMedia_FaturacaoAnualBolosDia();
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
     * Método para cálculo do lucro da pastelaria
     * @return se a pastelaria tem lucro ou não
     */
    @Override
    public String lucro(){
        if(receitaAnual()-despesaAnual()>0){return "Sim";}
        else{return "Nao";}
    }

    /**
     * Método de acesso externo à media de clientes diarios da pastelaria
     * @return Media de clientes diarios da pastelaria
     */
    @Override
    public float media_ClientesDiarios() {
        return this.getMedia_ClientesDiarios();
    }

    /**
     * Método de acesso externo a todos os dados da pastelaria
     * @return dados da pastelaria
     */
    @Override
    public String[] details() {
        String[] array = {String.valueOf(getCusto_empregrado()), String.valueOf(getNum_empregrados()), String.valueOf(getSalario_medioAnual()),String.valueOf(getMedia_ClientesDiarios()), String.valueOf(getMedia_BolosDia()),String.valueOf(getMedia_FaturacaoAnualBolosDia())};
        return array;
    }
    public float getMedia_BolosDia() {
        return media_BolosDia;
    }

    public void setMedia_BolosDia(float media_BolosDia) {
        this.media_BolosDia = media_BolosDia;
    }

    public float getMedia_FaturacaoAnualBolosDia() {
        return media_FaturacaoAnualBolosDia;
    }

    public void setMedia_FaturacaoAnualBolosDia(float media_FaturacaoAnualBolosDia) {
        this.media_FaturacaoAnualBolosDia = media_FaturacaoAnualBolosDia;
    }

    @Override
    public String toString() {
        return ("\n\tNome: "+getNome()+
                "\n\tTipo de Empresa: Pastelaria"+
                "\n\tDistrito: "+getDistrito()+
                "\n\tDespesa Anual: "+despesaAnual()+
                "\n\tReceita Anual: "+receitaAnual()+
                "\n\tLucro: "+lucro()+"\n");
    }

}
