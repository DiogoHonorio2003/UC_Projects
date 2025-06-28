package our_package;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe para gerir empresas
 */
public class StarThrive implements Serializable{
    /**
     * Lista de todas as empresas
     */
    private ArrayList<Empresas> lista_de_Empresas;

    /**
     * Construtor da StarThrive
     */
    public StarThrive() {

        lista_de_Empresas = new ArrayList<>();

    }

    /**
     * Método que imprime a lista de empresas
     */
    public void toString_listaEmpresas(){
        if (lista_de_Empresas.size()==0){System.out.printf("\nA lista de empresas esta vazia.");}
        else {
            System.out.printf("\nLista de Empresas:\n");
            for (int i = 0; i < lista_de_Empresas.size(); i++) {
                System.out.printf(lista_de_Empresas.get(i).toString());
            }
        }
    }

    public ArrayList<Empresas> getLista_de_Empresas() {
        return lista_de_Empresas;
    }

    public void setLista_de_Empresas(ArrayList<Empresas> lista_de_Empresas) {
        this.lista_de_Empresas = lista_de_Empresas;
    }

    /**
     * Método que cria uma nova empresa
     * @param empresa Nova empresa
     * @return true se foi adicionada, false no caso dessa empresa já se encontrar na lista
     */
    public boolean criarEmpresa(Empresas empresa){
        for(int i=0;i<lista_de_Empresas.size();i++){
            if(lista_de_Empresas.get(i).getNome().equalsIgnoreCase(empresa.getNome())){
                return false;
            }
        }
        lista_de_Empresas.add(empresa);
        return true;
    }

    /**
     * Método que edita uma empresa já existente
     * @param index Indice em que a empresa que irá ser editada se encontra na lista
     * @param empresaEditada Empresa já editada
     * @return true no caso de ser editada com sucesso
     */
    public boolean editarEmpresa(int index,Empresas empresaEditada){
        if(lista_de_Empresas.get(index).getTipo().equalsIgnoreCase("Cafe")&&empresaEditada.getTipo().equalsIgnoreCase("Cafe")){
            Cafe aux_empresa = (Cafe) lista_de_Empresas.get(index);
            aux_empresa.setNome(empresaEditada.getNome());
            aux_empresa.setDistrito(empresaEditada.getDistrito());
            aux_empresa.setLocalizacao(empresaEditada.getLocalizacao());
            aux_empresa.setCusto_empregrado(((Cafe) empresaEditada).getCusto_empregrado());
            aux_empresa.setNum_empregrados(((Cafe) empresaEditada).getNum_empregrados());
            aux_empresa.setSalario_medioAnual(((Cafe) empresaEditada).getSalario_medioAnual());
            aux_empresa.setMedia_ClientesDiarios(empresaEditada.media_ClientesDiarios());
            aux_empresa.setMedia_CafesDia(((Cafe) empresaEditada).getMedia_CafesDia());
            aux_empresa.setMedia_FaturacaoAnualCafesDia(((Cafe) empresaEditada).getMedia_FaturacaoAnualCafesDia());
            return true;
        }
        if(lista_de_Empresas.get(index).getTipo().equalsIgnoreCase("Pastelaria")&&empresaEditada.getTipo().equalsIgnoreCase("Pastelaria")){
            Pastelaria aux_empresa = (Pastelaria) lista_de_Empresas.get(index);
            aux_empresa.setNome(empresaEditada.getNome());
            aux_empresa.setDistrito(empresaEditada.getDistrito());
            aux_empresa.setLocalizacao(empresaEditada.getLocalizacao());
            aux_empresa.setCusto_empregrado(((Pastelaria) empresaEditada).getCusto_empregrado());
            aux_empresa.setNum_empregrados(((Pastelaria) empresaEditada).getNum_empregrados());
            aux_empresa.setSalario_medioAnual(((Pastelaria) empresaEditada).getSalario_medioAnual());
            aux_empresa.setMedia_ClientesDiarios(empresaEditada.media_ClientesDiarios());
            aux_empresa.setMedia_BolosDia(((Pastelaria) empresaEditada).getMedia_BolosDia());
            aux_empresa.setMedia_FaturacaoAnualBolosDia(((Pastelaria) empresaEditada).getMedia_FaturacaoAnualBolosDia());
            return true;
        }
        if(lista_de_Empresas.get(index).getTipo().equalsIgnoreCase("FastFood")&&empresaEditada.getTipo().equalsIgnoreCase("FastFood")){
            Fast_Food aux_empresa = (Fast_Food) lista_de_Empresas.get(index);
            aux_empresa.setNome(empresaEditada.getNome());
            aux_empresa.setDistrito(empresaEditada.getDistrito());
            aux_empresa.setLocalizacao(empresaEditada.getLocalizacao());

            aux_empresa.setCusto_empregrado(((Fast_Food) empresaEditada).getCusto_empregrado());
            aux_empresa.setNum_empregrados(((Fast_Food) empresaEditada).getNum_empregrados());
            aux_empresa.setSalario_medioAnual(((Fast_Food) empresaEditada).getSalario_medioAnual());
            aux_empresa.setMedia_ClientesDiarios(empresaEditada.media_ClientesDiarios());
            aux_empresa.setNum_DiasFuncionamentoAno(((Fast_Food) empresaEditada).getNum_DiasFuncionamentoAno());
            aux_empresa.setNum_MesasInteriores(((Fast_Food) empresaEditada).getNum_MesasInteriores());
            aux_empresa.setMedia_FaturacaoAnualMesaDia(((Fast_Food) empresaEditada).getMedia_FaturacaoAnualMesaDia());
            aux_empresa.setMediaDiaria_ClientesDrive(((Fast_Food) empresaEditada).getMediaDiaria_ClientesDrive());
            aux_empresa.setMediaDiaria_FaturacaoClienteDrive(((Fast_Food) empresaEditada).getMediaDiaria_FaturacaoClienteDrive());
            return true;
        }
        if(lista_de_Empresas.get(index).getTipo().equalsIgnoreCase("Locais")&&empresaEditada.getTipo().equalsIgnoreCase("Locais")){
            Locais aux_empresa = (Locais) lista_de_Empresas.get(index);
            aux_empresa.setNome(empresaEditada.getNome());
            aux_empresa.setDistrito(empresaEditada.getDistrito());
            aux_empresa.setLocalizacao(empresaEditada.getLocalizacao());

            aux_empresa.setCusto_empregrado(((Locais) empresaEditada).getCusto_empregrado());
            aux_empresa.setNum_empregrados(((Locais) empresaEditada).getNum_empregrados());
            aux_empresa.setSalario_medioAnual(((Locais) empresaEditada).getSalario_medioAnual());
            aux_empresa.setMedia_ClientesDiarios(empresaEditada.media_ClientesDiarios());
            aux_empresa.setNum_DiasFuncionamentoAno(((Locais) empresaEditada).getNum_DiasFuncionamentoAno());
            aux_empresa.setNum_MesasInteriores(((Locais) empresaEditada).getNum_MesasInteriores());
            aux_empresa.setNum_MesasExteriores(((Locais) empresaEditada).getNum_MesasExteriores());
            aux_empresa.setCusto_LicensaAnualMesaExterior(((Locais) empresaEditada).getCusto_LicensaAnualMesaExterior());
            aux_empresa.setMedia_FaturacaoAnualMesaDia(((Locais) empresaEditada).getMedia_FaturacaoAnualMesaDia());
            return true;

        }
        if(lista_de_Empresas.get(index).getTipo().equalsIgnoreCase("Frutaria")&&empresaEditada.getTipo().equalsIgnoreCase("Frutaria")){
            Frutaria aux_empresa = (Frutaria) lista_de_Empresas.get(index);
            aux_empresa.setNome(empresaEditada.getNome());
            aux_empresa.setDistrito(empresaEditada.getDistrito());
            aux_empresa.setLocalizacao(empresaEditada.getLocalizacao());

            aux_empresa.setNum_Produtos(((Frutaria) empresaEditada).getNum_Produtos());
            aux_empresa.setMedia_FaturacaoAnualProduto(((Frutaria) empresaEditada).getMedia_FaturacaoAnualProduto());
            aux_empresa.setCusto_AnualLimpeza(((Frutaria) empresaEditada).getCusto_AnualLimpeza());
            return true;
        }
        if(lista_de_Empresas.get(index).getTipo().equalsIgnoreCase("Mercado")&&empresaEditada.getTipo().equalsIgnoreCase("Mercado")){
            Mercado aux_empresa = (Mercado) lista_de_Empresas.get(index);
            aux_empresa.setNome(empresaEditada.getNome());
            aux_empresa.setDistrito(empresaEditada.getDistrito());
            aux_empresa.setLocalizacao(empresaEditada.getLocalizacao());

            aux_empresa.setArea_corredores(((Mercado) empresaEditada).getArea_corredores());
            aux_empresa.setMedia_FaturacaoAnualm2(((Mercado) empresaEditada).getMedia_FaturacaoAnualm2());
            aux_empresa.setCusto_AnualLimpeza(((Mercado) empresaEditada).getCusto_AnualLimpeza());
            aux_empresa.setTipoMercearia(((Mercado) empresaEditada).getTipoMercearia());
            return true;
        }
        return false;
    }


    /**
     * Método que elimina uma empresa da lista de empresas
     * @param index indice da lista que o utilizador deseja eliminar
     * @return true no fim de eliminar a empresa com sucesso
     */
    public boolean apagarEmpresa(int index){
            lista_de_Empresas.remove(index);
        return true;
    }
}


