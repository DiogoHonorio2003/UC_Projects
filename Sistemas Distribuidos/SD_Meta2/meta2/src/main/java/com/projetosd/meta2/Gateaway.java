package com.projetosd.meta2;
/*
 * Sistemas Distribuidos 2024
 * 
 * Bruno Jose Silverio Da Silva 202123021
 * Diogo Emanuel Matos Honorio 2021232043
 * 
 */


import java.rmi.*;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Component;

import java.util.Properties;

public class Gateaway extends UnicastRemoteObject implements RMIGateaway{
    public static int tcp_port;
    private ArrayList<RMIClient> clients;
    private ArrayList<RMIIndexStorageBarrel> barrels;
    private ArrayList<Long> times;
    private HashMap<String, Integer> top10;

    public Gateaway() throws RemoteException{
        clients = new ArrayList<>();
        barrels = new ArrayList<>();
        times = new ArrayList<>();
        top10 = new HashMap<>();
    }

    public static void main(String[] args){
        Properties properties = new Properties();
        try{
            // Ler o ficheiro config.properties
            properties.load(new FileInputStream("C:\\Users\\bruno\\Desktop\\SD\\SD_Meta2\\meta2\\src\\main\\java\\com\\projetosd\\meta2\\config.properties"));
            int port = Integer.parseInt(properties.getProperty("rmi.gateaway"));
            tcp_port = Integer.parseInt(properties.getProperty("down.PORTClient"));

            Gateaway gateaway = new Gateaway();
            LocateRegistry.createRegistry(port).rebind("Gateaway", gateaway);
        } catch(RemoteException re){
            System.out.println("Remote Exception in Gateaway: " + re);
        } catch (IOException e){
            System.out.println("IO Exception in Gateaway: Nao foi possivel ler o ficheiro config.properties -" + e);
        }
    }

    // Método que executa o login de um cliente
    public String check_login(String username, String password){
        try{
            // Escolher barrel para a execucao da tarefa
            RMIIndexStorageBarrel barrel = chooseBarrel();

            // Caso nao haja barrels ativos
            if (barrel == null){
                return "Erro: Nao existe nenhum Barrel ativo\n";
            }

            return barrel.login(username, password); // Uso da funcao do RMIIndexStorageBarrel
        } catch(RemoteException e){
            System.out.println(e.getMessage());
        }
        return "Erro: Nao foi possivel efetuar login";
    }

    // Método que executa o registo de um cliente
    public String check_register(String username, String password){
        try{
            // Escolher barrel para a execucao da tarefa
            RMIIndexStorageBarrel barrel = chooseBarrel();

            // Caso nao haja barrels ativos
            if (barrel == null){
                return "Erro: Nao existe nenhum Barrel ativo\n";
            }

            admin_send("Novo usuario registado: " + username);
            return barrel.register(username, password); // Uso da funcao do RMIIndexStorageBarrel
        } catch(RemoteException e){
            System.out.println(e.getMessage());
        }
        return "Erro: Nao foi possivel efetuar registo";
    }
    
    // Método que envia os urls inseridos pelo cliente para o downloader atraves de TCP
    public void send_url(String url){
        try{
            Socket socket = new Socket("localhost", tcp_port);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(url);
            out.flush();
            socket.close();

            // Send update to admin
            long start = System.currentTimeMillis(); // Inicializacao da contagem do tempo decorrido ao executar a tarefa
            long elapsedTimeMillis = System.currentTimeMillis()-start;
            times.add(elapsedTimeMillis);
            admin_send("Link enviado: " + url + " -> " + calcularMedia(times) + "ms");


        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return;
    }
    
    // Método que executa a pesquisa de termos
    public String search(String words){
        try{
            // Atualizar as pesquisas mais comuns
            updateTop10(words);
            // Escolher barrel para a execucao da tarefa
            RMIIndexStorageBarrel barrel = chooseBarrel();

            // Caso nao haja barrels ativos
            if (barrel == null){
                return "Erro: Nao existe nenhum Barrel ativo\n";
            }

            System.out.println("Barrel choosed: "+ barrel.getBarrelReg());
            if (barrel != null){
                long start = System.currentTimeMillis(); // Inicializacao da contagem do tempo decorrido ao executar a tarefa
                String text = barrel.searchWord(words); // Uso da funcao do RMIIndexStorageBarrel
                long elapsedTimeMillis = System.currentTimeMillis()-start;
                times.add(elapsedTimeMillis);
                admin_send("Pesquisa por termo(s): " + words + " -> " + calcularMedia(times) + "ms -> Barrel Usado: " + barrel.getBarrelReg());
                return text;
            }
        } catch (RemoteException e){
            System.out.println(e.getMessage());
        }
        return "Nenhum Barrel Disponivel";
    }

    // Método que executa a pesquisa do link
    public String searchLink(String link){
        try{
            // Atualizar as pesquisas mais comuns
            updateTop10(link);
            // Escolher barrel para a execucao da tarefa
            RMIIndexStorageBarrel barrel = chooseBarrel(); 

            // Caso nao haja barrels ativos
            if (barrel == null){
                return "Erro: Nao existe nenhum Barrel ativo\n";
            }

            System.out.println("Barrel choosed: "+ barrel.getBarrelReg());
            if (barrel != null){
                long start = System.currentTimeMillis(); // Inicializacao da contagem do tempo decorrido ao executar a tarefa
                String text = barrel.searchURL(link); // Uso da funcao do RMIIndexStorageBarrel
                long elapsedTimeMillis = System.currentTimeMillis()-start;
                times.add(elapsedTimeMillis);
                admin_send("Pesquisa por URLs associados: " + link + " -> Tempo medio: " + calcularMedia(times) + "ms -> Barrel Usado: " + barrel.getBarrelReg());
                return text;
            }
        } catch (RemoteException e){
            System.out.println(e.getMessage());
        }
        return "Nenhum Barrel Disponivel";
    }

    // Adicionar o cliente da lista de clientes na pagina de admin
    @Override
    public void admin_subscribe(RMIClient admin) throws RemoteException{
        clients.add(admin);
    }

    // Remover o cliente da lista de clientes na pagina de admin
    @Override
    public void admin_unsubscribe(RMIClient admin) throws RemoteException{
        clients.remove(admin);
    }

    // Enviar mensagem para os clientes que estao na pagina de admin
    @Override
    public void admin_send(String message){
        try{
            for(RMIClient client : clients){
                client.receiveMessage(message); // Usar funcao do RMI client
            }
        }catch (RemoteException e){
			System.out.println(e);
		}
    }

    // Adicionar o barrel ao ArrayList de barrels
    public void addBarrel(RMIIndexStorageBarrel barrel) throws RemoteException{
        System.out.println("Barrel " + barrel.getBarrelReg() + " adicionado"); // Mostrar o nome do barrel adicionado
        barrels.add(barrel);
    }

    public void removeBarrel(RMIIndexStorageBarrel barrel) throws RemoteException{
        System.out.println("Barrel " + barrel.getBarrelReg() + " removido"); // Mostrar o nome do barrel removido
        barrels.remove(barrel);
    }

    // Método que escolhe um barrel aleartoriamente
    private RMIIndexStorageBarrel chooseBarrel() {
        int barrelSize = barrels.size();
        if (barrelSize > 0) {
            Random rand = new Random();
            return barrels.get(rand.nextInt(barrelSize));
        } else {
            return null; // If no barrel is available, return null
        }
    }
    
    // Método que calcula media de valores de um ArrayList
    public static double calcularMedia(ArrayList<Long> numeros) {
        long soma = 0;
        for (Long numero : numeros) {
            soma += numero;
        }
        return (double) soma / numeros.size();
    }

    // Retornar os barrels connectados ao gateaway
    public String showCurrentBarrel(){
        try{
            String text = "Barrels ativos:\n";

            if (barrels.size() == 0){
                text += "Nenhum barrel ativo\n";
                return text;
            }

            for (RMIIndexStorageBarrel barrel : barrels){
                text = text + barrel.getBarrelReg() + "\n";
            }
            return text;
        }catch (RemoteException e){
            System.out.println(e);
        }
        return "Erro: Nenhum barrel encontrado\n";
    }

    // Atualizar hashmap do top10
    public void updateTop10(String text) {
        // Verifica se a chave já existe no mapa
        if (top10.containsKey(text)) {
            // Se existe, incrementa o valor associado a ela
            int count = top10.get(text);
            top10.put(text, count + 1);
        } else {
            // Se não existe, adiciona a chave com valor 1
            top10.put(text, 1);
        }
    }

    // Retornar as 10 pesquisas mais frequentes
    public String getTop10KeysAsString() {
        ArrayList<Map.Entry<String, Integer>> entries = new ArrayList<>(top10.entrySet()); // Cria uma lista de entradas do mapa
        Collections.sort(entries, (entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())); // Ordena a lista de acordo com os valores (número de pesquisas)

        entries = new ArrayList<>(entries.subList(0, Math.min(10, entries.size()))); // Pega apenas as 10 primeiras entradas (as 10 mais pesquisadas)
        StringBuilder result = new StringBuilder("Top 10 chaves mais pesquisadas:\n"); // Constrói a string com as chaves e seus valores correspondentes
        for (Map.Entry<String, Integer> entry : entries) {
            result.append(entry.getKey()).append(": ").append(entry.getValue()).append(" vezes\n");
        }
        return result.toString();
    }
}
