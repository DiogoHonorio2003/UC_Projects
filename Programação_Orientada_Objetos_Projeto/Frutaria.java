package our_package;

/**
 * Classe da Frutaria
 */
public class Frutaria extends Mercearia {
    /**
     * Numero de produtos
     */
    private int num_Produtos;
    /**
     * Media de faturação anual por produto
     */
    private float media_FaturacaoAnualProduto;
    /**
     * Custo anual de limpeza
     */
    private float custo_AnualLimpeza;

    /**
     * Construtor da classe recebe dados para a inicialização dos atributos
     * @param nome Nome da frutaria
     * @param distrito Distrito da frutaria
     * @param localizacao Coordenadas da frutaria
     * @param num_Produtos Numero de produtos
     * @param media_FaturacaoAnualProduto Media de faturação anual por produto
     * @param custo_AnualLimpeza Custo anual de limpeza
     */
    public Frutaria(String nome, String distrito, String localizacao, int num_Produtos, float media_FaturacaoAnualProduto, float custo_AnualLimpeza) {
        super(nome, distrito, localizacao,"Frutaria");
        this.num_Produtos = num_Produtos;
        this.media_FaturacaoAnualProduto = media_FaturacaoAnualProduto;
        this.custo_AnualLimpeza = custo_AnualLimpeza;
    }

    /**
     * Método para cálculo da despesa anual da frutaria
     * @return despesa anual da frutaria
     */
    @Override
    public float despesaAnual(){
        return this.getCusto_AnualLimpeza();
    }

    /**
     * Método para cálculo da receita anual da frutaria
     * @return receita anual da frutaria
     */
    @Override
    public float receitaAnual(){
        return this.getNum_Produtos()* this.getMedia_FaturacaoAnualProduto();
    }

    /**
     * Método para cálculo do lucro da frutaria
     * @return se a pastelaria tem lucro ou não
     */
    @Override
    public String lucro(){
        if(receitaAnual()-despesaAnual()>0){return "Sim";}
        else{return "Nao";}
    }

    /**
     * Método de acesso externo ao tipo de empresa
     * @return tipo de empresa
     */
    @Override
    public String tipo(){return this.getTipo();}

    /**
     * Método de acesso externo a todos os dados da frutaria
     * @return dados da frutaria
     */
    @Override
    public String[] details() {
        String[] array = {String.valueOf(getNum_Produtos()),String.valueOf(getMedia_FaturacaoAnualProduto()),String.valueOf(getCusto_AnualLimpeza())};
        return array;
    }

    public int getNum_Produtos() {
        return num_Produtos;
    }

    public void setNum_Produtos(int num_Produtos) {
        this.num_Produtos = num_Produtos;
    }

    public float getMedia_FaturacaoAnualProduto() {
        return media_FaturacaoAnualProduto;
    }

    public void setMedia_FaturacaoAnualProduto(float media_FaturacaoAnualProduto) {
        this.media_FaturacaoAnualProduto = media_FaturacaoAnualProduto;
    }

    public float getCusto_AnualLimpeza() {
        return custo_AnualLimpeza;
    }

    public void setCusto_AnualLimpeza(float custo_AnualLimpeza) {
        this.custo_AnualLimpeza = custo_AnualLimpeza;
    }

    @Override
    public String toString() {
        return ("\n\tNome: "+getNome()+
                "\n\tTipo de Empresa: Frutaria"+
                "\n\tDistrito: "+getDistrito()+
                "\n\tDespesa Anual: "+despesaAnual()+
                "\n\tReceita Anual: "+receitaAnual()+
                "\n\tLucro: "+lucro()+"\n");
    }
}
