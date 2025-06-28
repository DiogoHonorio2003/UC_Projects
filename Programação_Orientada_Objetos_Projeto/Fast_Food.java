package our_package;

/**
 * Classe restaurantes de fast-food
 */
public class Fast_Food extends Restaurante{
    /**
     * Numero de mesas interiores
     */
    private int num_MesasInteriores;
    /**
     * media de faturacao anual por mesa num dia
     */
    private float media_FaturacaoAnualMesaDia;
    /**
     * media diaria de clientes drive-thru
     */
    private float mediaDiaria_ClientesDrive;
    /**
     * media diaria de faturação de clientes drive-thru
     */
    private float mediaDiaria_FaturacaoClienteDrive;

    /**
     * Construtor da classe recebe dados para a inicialização dos atributos
     * @param nome Nome do restaurante fast-food
     * @param distrito Distrito do restaurante fast-food
     * @param localizacao Coordenadas do restaurante fast-food
     * @param custo_empregrado Custo por empregado
     * @param num_empregrados Numero de empregados
     * @param salario_medioAnual Salaria medio anual
     * @param media_ClientesDiarios Media de clientes diarios
     * @param num_DiasFuncionamentoAno Numero de dias de funcionamento
     * @param num_MesasInteriores Numero de mesas interiores
     * @param media_FaturacaoAnualMesaDia Media de faturacao anual por mesa num dia
     * @param mediaDiaria_ClientesDrive Media diaria de clientes drive-thru
     * @param mediaDiaria_FaturacaoClienteDrive Media diaria de faturação de clientes drive-thru
     */
    public Fast_Food(String nome, String distrito, String localizacao, float custo_empregrado, int num_empregrados, float salario_medioAnual, float media_ClientesDiarios, int num_DiasFuncionamentoAno, int num_MesasInteriores, float media_FaturacaoAnualMesaDia, float mediaDiaria_ClientesDrive, float mediaDiaria_FaturacaoClienteDrive) {
        super(nome, distrito, localizacao,"FastFood", custo_empregrado, num_empregrados, salario_medioAnual, media_ClientesDiarios, num_DiasFuncionamentoAno);
        this.num_MesasInteriores = num_MesasInteriores;
        this.media_FaturacaoAnualMesaDia = media_FaturacaoAnualMesaDia;
        this.mediaDiaria_ClientesDrive = mediaDiaria_ClientesDrive;
        this.mediaDiaria_FaturacaoClienteDrive = mediaDiaria_FaturacaoClienteDrive;
    }

    /**
     * Método para cálculo da despesa anual do restaurante fast-food
     * @return despesa anual do restaurante fast-food
     */
    @Override
    public float despesaAnual(){
        return this.getNum_empregrados()*this.getSalario_medioAnual();
    }

    /**
     * Método para cálculo da receita anual do restaurante fast-food
     * @return receita anual do restaurante fast-food
     */
    @Override
    public float receitaAnual(){
        return ((((this.getNum_MesasInteriores()*this.getMedia_FaturacaoAnualMesaDia())+this.getMediaDiaria_ClientesDrive())*this.getMediaDiaria_FaturacaoClienteDrive())*this.getNum_DiasFuncionamentoAno());
    }

    /**
     * Método para cálculo do lucro do restaurante fast-food
     * @return se o restaurante fast-food tem lucro ou não
     */
    @Override
    public String lucro(){
        if(receitaAnual()-despesaAnual()>0){return "Sim";}
        else{return "Nao/";}
    }

    /**
     * Método de acesso externo à media de clientes diarios do cafe
     * @return Media de clientes diarios do restaurante fast-food
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
     * Método de acesso externo a todos os dados do restaurante fast-food
     * @return dados do restaurante fast-food
     */
    @Override
    public String[] details() {
        String[] array = {String.valueOf(getCusto_empregrado()), String.valueOf(getNum_empregrados()), String.valueOf(getSalario_medioAnual()),String.valueOf(getMedia_ClientesDiarios()),String.valueOf(getNum_DiasFuncionamentoAno()),String.valueOf(getNum_MesasInteriores()),String.valueOf(getMedia_FaturacaoAnualMesaDia()),String.valueOf(getMediaDiaria_ClientesDrive()),String.valueOf(getMediaDiaria_FaturacaoClienteDrive())};
        return array;
    }

    public int getNum_MesasInteriores() {
        return num_MesasInteriores;
    }
    public void setNum_MesasInteriores(int num_MesasInteriores) {
        this.num_MesasInteriores = num_MesasInteriores;
    }

    public float getMedia_FaturacaoAnualMesaDia() {
        return media_FaturacaoAnualMesaDia;
    }

    public void setMedia_FaturacaoAnualMesaDia(float media_FaturacaoAnualMesaDia) {
        this.media_FaturacaoAnualMesaDia = media_FaturacaoAnualMesaDia;
    }

    public float getMediaDiaria_ClientesDrive() {
        return mediaDiaria_ClientesDrive;
    }

    public void setMediaDiaria_ClientesDrive(float mediaDiaria_ClientesDrive) {
        this.mediaDiaria_ClientesDrive = mediaDiaria_ClientesDrive;
    }

    public float getMediaDiaria_FaturacaoClienteDrive() {
        return mediaDiaria_FaturacaoClienteDrive;
    }

    public void setMediaDiaria_FaturacaoClienteDrive(float mediaDiaria_FaturacaoClienteDrive) {
        this.mediaDiaria_FaturacaoClienteDrive = mediaDiaria_FaturacaoClienteDrive;
    }

    @Override
    public String toString() {
        return ("\n\tNome: "+getNome()+
                "\n\tTipo de Empresa: Restaurante Fast-Food"+
                "\n\tDistrito: "+getDistrito()+
                "\n\tDespesa Anual: "+despesaAnual()+
                "\n\tReceita Anual: "+receitaAnual()+
                "\n\tLucro: "+lucro()+"\n");
    }
}
