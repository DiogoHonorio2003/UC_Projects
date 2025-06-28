package our_package;

import java.io.Serializable;

/**
 * Classe empresas
 */
public abstract class Empresas implements Serializable{
    /**
     * Nome da empresa
     */
    private String nome;
    /**
     * Distrito da empresa
     */
    private String distrito;
    /**
     * Coordenadas da empresa
     */
    private String localizacao;
    /**
     * Tipo de empresa
     */
    private String tipo;

    /**
     * Construtor da classe recebe dados para a inicialização dos atributos
     * @param nome Nome da Empresa
     * @param distrito Distrito da empresa
     * @param localizacao Coordenadas da empresa
     * @param tipo Tipo de empresa
     */
    public Empresas(String nome, String distrito, String localizacao, String tipo) {
        this.nome = nome;
        this.distrito = distrito;
        this.localizacao = localizacao;
        this.tipo=tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Método abstrato de cálculo de despesa anual
     * @return despesa anual da empresa
     */
    public abstract float despesaAnual();

    /**
     * Método abstrato de cálculo da receita anual
     * @return receita anual da empresa
     */
    public abstract float receitaAnual();

    /**
     * Método abstrato de cálculo de lucro da empresa
     * @return se a empresa tem lucro ou não
     */
    public abstract String lucro();

    /**
     * Método abstrato de media de clientes diarios da empresa
     * @return Media de clientes diarios de empresa
     */
    public abstract float media_ClientesDiarios();

    /**
     * Método abstrato de tipo de empresa
     * @return tipo de empresa
     */
    public abstract String tipo();

    /**
     * Método abstrato de todos os dados da empresa
     * @return dados da empresa
     */
    public abstract String[] details();
}
