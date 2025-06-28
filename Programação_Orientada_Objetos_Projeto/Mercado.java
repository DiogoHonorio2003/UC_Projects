package our_package;

/**
 * Classe mercado
 */
public class Mercado extends Mercearia{
    /**
     * Area dos corredores
     */
    private float area_corredores;
    /**
     * Media de faturação anual por metro quadrado
     */
    private float media_FaturacaoAnualm2;
    /**
     * Custo anual de limpeza
     */
    private float custo_AnualLimpeza;
    /**
     * Tipo do mercado
     */
    private String tipoMercearia;

    /**
     * Construtor da classe recebe dados para a inicialização dos atributos
     * @param nome Nome do mercado
     * @param distrito Distrito do mercado
     * @param localizacao Coordenadas do mercado
     * @param area_corredores Area dos corredores
     * @param media_FaturacaoAnualm2 Media de faturação anual por metro quadrado
     * @param custo_AnualLimpeza Custo anual de limpeza
     * @param tipoMercearia Tipo do mercado
     */
    public Mercado(String nome, String distrito, String localizacao, float area_corredores, float media_FaturacaoAnualm2, float custo_AnualLimpeza, String tipoMercearia) {
        super(nome, distrito, localizacao,"Mercado");
        this.area_corredores = area_corredores;
        this.media_FaturacaoAnualm2 = media_FaturacaoAnualm2;
        this.custo_AnualLimpeza = custo_AnualLimpeza;
        this.tipoMercearia = tipoMercearia;
    }

    /**
     * Método para cálculo da despesa anual do mercado
     * @return despesa anual do mercado
     */
    @Override
    public float despesaAnual(){
        return this.getCusto_AnualLimpeza();
    }

    /**
     * Método para cálculo da receita anual do mercado
     * @return receita anual mercado
     */
    @Override
    public float receitaAnual(){
        return this.getArea_corredores()*getMedia_FaturacaoAnualm2();
    }

    /**
     * Método para cálculo do lucro do mercado
     * @return se o mercado tem lucro ou não
     */
    @Override
    public String lucro(){
        if(receitaAnual()-despesaAnual()>0){return "Sim";}
        else{return "Nao";}
    }

    /**
     * Método de acesso externo a todos os dados do mercado
     * @return dados do mercado
     */
    @Override
    public String[] details() {
        String[] array = {String.valueOf(getArea_corredores()),String.valueOf(getMedia_FaturacaoAnualm2()),String.valueOf(getCusto_AnualLimpeza()),String.valueOf(getTipoMercearia())};
        return array;
    }

    public float getArea_corredores() {
        return area_corredores;
    }

    public void setArea_corredores(float area_corredores) {
        this.area_corredores = area_corredores;
    }

    public float getMedia_FaturacaoAnualm2() {
        return media_FaturacaoAnualm2;
    }

    public void setMedia_FaturacaoAnualm2(float media_FaturacaoAnualm2) {
        this.media_FaturacaoAnualm2 = media_FaturacaoAnualm2;
    }

    public float getCusto_AnualLimpeza() {
        return custo_AnualLimpeza;
    }

    public void setCusto_AnualLimpeza(float custo_AnualLimpeza) {
        this.custo_AnualLimpeza = custo_AnualLimpeza;
    }

    public String getTipoMercearia() {
        return tipoMercearia;
    }

    public void setTipoMercearia(String tipoMercearia) {
        this.tipoMercearia = tipoMercearia;
    }
    @Override
    public String tipo(){return this.getTipo();}

    @Override
    public String toString() {
        return ("\n\tNome: "+getNome()+
                "\n\tTipo de Empresa: Mercado "+getTipoMercearia()+
                "\n\tDistrito: "+getDistrito()+
                "\n\tDespesa Anual: "+despesaAnual()+
                "\n\tReceita Anual: "+receitaAnual()+
                "\n\tLucro: "+lucro()+"\n");
    }
}
