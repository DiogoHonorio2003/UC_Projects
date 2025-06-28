package our_package;

/**
 * Classe restaurantes locais
 */
public class Locais extends Restaurante{
    /**
     * Numero de mesas interiores
     */
    private int num_MesasInteriores;
    /**
     * Numero de mesas de esplanada
     */
    private int num_MesasExteriores;
    /**
     * Custo de licensa anual por cada mesa de esplanada
     */
    private float custo_LicensaAnualMesaExterior;
    /**
     * Media de faturação anual por mesa ao dia
     */
    private float media_FaturacaoAnualMesaDia;

    /**
     * Construtor da classe recebe dados para a inicialização dos atributos
     * @param nome Nome do restaurante local
     * @param distrito Distrito do restaurante local
     * @param localizacao Coordenadas do restaurante local
     * @param custo_empregrado Custo por empregado
     * @param num_empregrados Numero de empregados
     * @param salario_medioAnual Salario medio anual
     * @param media_ClientesDiarios Media de clientes diarios
     * @param num_DiasFuncionamentoAno Numero de dias de funcionamento
     * @param num_MesasInteriores Numero de mesas interiores
     * @param num_MesasExteriores Numero de mesas de esplanada
     * @param custo_LicensaAnualMesaExterior Custo de licensa anual por cada mesa de esplanada
     * @param media_FaturacaoAnualMesaDia Media de faturação anual por mesa ao dia
     */
    public Locais(String nome, String distrito, String localizacao, float custo_empregrado, int num_empregrados, float salario_medioAnual, float media_ClientesDiarios, int num_DiasFuncionamentoAno, int num_MesasInteriores, int num_MesasExteriores, float custo_LicensaAnualMesaExterior, float media_FaturacaoAnualMesaDia) {
        super(nome, distrito, localizacao,"Locais", custo_empregrado, num_empregrados, salario_medioAnual, media_ClientesDiarios, num_DiasFuncionamentoAno);
        this.num_MesasInteriores = num_MesasInteriores;
        this.num_MesasExteriores = num_MesasExteriores;
        this.custo_LicensaAnualMesaExterior = custo_LicensaAnualMesaExterior;
        this.media_FaturacaoAnualMesaDia = media_FaturacaoAnualMesaDia;
    }

    /**
     * Método para cálculo da despesa anual do restaurante local
     * @return despesa anual do restaurante local
     */
    @Override
    public float despesaAnual(){
        return (this.getNum_empregrados()*this.getSalario_medioAnual()+this.getNum_MesasExteriores()*this.getCusto_LicensaAnualMesaExterior());
    }

    /**
     * Método para cálculo da receita anual do restaurante local
     * @return receita anual do restaurante local
     */
    @Override
    public float receitaAnual(){
        return ((this.getNum_MesasInteriores()+this.getNum_MesasExteriores())*this.getMedia_FaturacaoAnualMesaDia()*this.getNum_DiasFuncionamentoAno());
    }

    /**
     * Método para cálculo do lucro do restaurante local
     * @return se o restaurante local tem lucro ou não
     */
    @Override
    public String lucro(){
        if(receitaAnual()-despesaAnual()>0){return "Sim";}
        else{return "Nao";}
    }

    /**
     * Método de acesso externo à media de clientes diarios do restaurante local
     * @return Media de clientes diarios do restaurante local
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
    public String tipo(){return this.getTipo();}

    /**
     * Método de acesso externo a todos os dados do restaurante local
     * @return dados do restaurante local
     */
    @Override
    public String[] details() {
        String[] array = {String.valueOf(getCusto_empregrado()), String.valueOf(getNum_empregrados()), String.valueOf(getSalario_medioAnual()),String.valueOf(getMedia_ClientesDiarios()),String.valueOf(getNum_DiasFuncionamentoAno()),String.valueOf(getNum_MesasInteriores()),String.valueOf(getNum_MesasExteriores()),String.valueOf(getCusto_LicensaAnualMesaExterior()),String.valueOf(getMedia_FaturacaoAnualMesaDia())};
        return array;
    }

    public int getNum_MesasInteriores() {
        return num_MesasInteriores;
    }

    public void setNum_MesasInteriores(int num_MesasInteriores) {
        this.num_MesasInteriores = num_MesasInteriores;
    }

    public int getNum_MesasExteriores() {
        return num_MesasExteriores;
    }

    public void setNum_MesasExteriores(int num_MesasExteriores) {
        this.num_MesasExteriores = num_MesasExteriores;
    }

    public float getCusto_LicensaAnualMesaExterior() {
        return custo_LicensaAnualMesaExterior;
    }

    public void setCusto_LicensaAnualMesaExterior(float custo_LicensaAnualMesaExterior) {
        this.custo_LicensaAnualMesaExterior = custo_LicensaAnualMesaExterior;
    }

    public float getMedia_FaturacaoAnualMesaDia() {
        return media_FaturacaoAnualMesaDia;
    }

    public void setMedia_FaturacaoAnualMesaDia(float media_FaturacaoAnualMesaDia) {
        this.media_FaturacaoAnualMesaDia = media_FaturacaoAnualMesaDia;
    }

    @Override
    public String toString() {
        return ("\n\tNome: "+getNome()+
                "\n\tTipo de Empresa: Restaurante Local"+
                "\n\tDistrito: "+getDistrito()+
                "\n\tDespesa Anual: "+despesaAnual()+
                "\n\tReceita Anual: "+receitaAnual()+
                "\n\tLucro: "+lucro()+"\n");
    }
}
