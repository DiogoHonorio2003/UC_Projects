package our_package;

/**
 * Classe mercearias
 */
public class Mercearia extends Empresas{
    /**
     * Custo por empregado
     */
    private float custo_empregrado;

    /**
     * Construtor da classe recebe dados para a inicialização dos atributos
     * @param nome Nome da Mercearia
     * @param distrito Distrito da Mercearia
     * @param localizacao Coordenadas da Mercearia
     * @param tipo Tipo de empresa
     */
    public Mercearia(String nome, String distrito, String localizacao,String tipo) {
        super(nome, distrito, localizacao,tipo);
        custo_empregrado=0;
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

