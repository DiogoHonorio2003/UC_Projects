package com.projetosd.meta2;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
//import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;


public class IndexStorageBarrel implements RMIIndexStorageBarrel, Serializable {

    // Variaveis estaticas

    // Definição de HashMaps de urls / words / logins(username and password)
    public static HashMap<String, HashSet<String>> urls;
    public static HashMap<String, HashSet<String>> words;
    public static HashMap<String, String> logins;
    
    public static String MULTICAST_ADDRESS;  // Endereço Multicast
    public static int PORT;  // Porta de Multicast
    public static int BUF_SIZE;   // Tamanho do Buffer
    public static int nBarrels;  // Numero de Barrels criados ao rodar o programa
    public String barrelReg;

    public static String urlFile;
    public static String wordsFile;
    public static String loginsFile;

    private static RMIIndexStorageBarrel rmiBarrel;
    public static RMIGateaway RMIGateaway;

    // Construtor do Barrel - Inicia as variaveis
    public IndexStorageBarrel() {
        try{
            rmiBarrel = (RMIIndexStorageBarrel) UnicastRemoteObject.exportObject(this, 0);
            Random rand = new Random();
            this.barrelReg = "barrel" + rand.nextInt(100);
            System.out.println("---> Barrel " + this.barrelReg + " Started");
            urls = new HashMap<>();
            words = new HashMap<>();
            logins = new HashMap<>();
            addToListBarrels();
        } catch (RemoteException e) {
            System.out.println("Erro ao inicializar barrel");
        }
    }
    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("C:\\Users\\bruno\\Desktop\\SD\\SD_Meta2\\meta2\\src\\main\\java\\com\\projetosd\\meta2\\config.properties"));
            int port = Integer.parseInt(properties.getProperty("rmi.gateaway"));
            MULTICAST_ADDRESS = properties.getProperty("mc.address");
            PORT = Integer.parseInt(properties.getProperty("mc.port"));
            nBarrels = Integer.parseInt(properties.getProperty("isb.nBarrels"));
            BUF_SIZE = Integer.parseInt(properties.getProperty("isb.buff_size"));
            urlFile = properties.getProperty("isb.url");
            wordsFile = properties.getProperty("isb.words");
            loginsFile = properties.getProperty("isb.login");
            RMIGateaway = (RMIGateaway) LocateRegistry.getRegistry(port).lookup("Gateaway");

            
            //System.out.println("Barrel Started");

            // Criação e Registo do Barrel
            //new IndexStorageBarrel();
            //LocateRegistry.createRegistry(4300).rebind("IndexStorageBarrel", barrel);
    
            // Cria Ficheiro de URLs caso não exista
            File file_urls = new File(urlFile);
            if (!file_urls.exists()) {
                try {
                    if (file_urls.createNewFile()) {
                        System.out.println("Arquivo "+ urlFile +" criado com sucesso.");
                    } else {
                        System.out.println("Não foi possível criar o arquivo "+ urlFile +".");
                    }
                } catch (IOException e) {
                    System.out.println("Ocorreu um erro ao criar o arquivo "+ urlFile +".");
                    e.printStackTrace();
                }
            }

            // Cria Ficheiro de Words caso não exista
            File file_words = new File(wordsFile);
            if (!file_words.exists()) {
                try {
                    if (file_words.createNewFile()) {
                        System.out.println("Arquivo "+ wordsFile +" criado com sucesso.");
                    } else {
                        System.out.println("Não foi possível criar o arquivo "+ wordsFile +".");
                    }
                } catch (IOException e) {
                    System.out.println("Ocorreu um erro ao criar o arquivo "+ wordsFile +".");
                    e.printStackTrace();
                }
            }

            // Cria Ficheiro de Logins caso não exista
            File file_login = new File(loginsFile);
            if (!file_login.exists()) {
                try {
                    // Se não existir, cria o arquivo
                    if (file_login.createNewFile()) {
                        System.out.println("Arquivo "+ loginsFile +" criado com sucesso.");
                    } else {
                        System.out.println("Não foi possível criar o arquivo "+ loginsFile +".");
                    }
                } catch (IOException e) {
                    System.out.println("Ocorreu um erro ao criar o arquivo "+ loginsFile +".");
                    e.printStackTrace();
                }
            }

            System.out.println("Files loaded");

            // Correr n Barrels
            ExecutorService executorBarrels = Executors.newFixedThreadPool(nBarrels);
            executorBarrels.execute(new BarrelThread());
            executorBarrels.shutdown();
        
        } catch (RemoteException e) {
            System.out.println("Erro: Nao foi possivel conectar ao Gateaway");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    // Procura determinda Word no File de Words
    public String searchWord(String word) {
        String result = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(wordsFile))) {  // Ler Ficheiro words.txt
            String line;
            while ((line = reader.readLine()) != null) {  // Lê cada linha até não existir mais
                String[] parts = line.split(",");   // Divide word do URL associado, quote e titulo
                if (parts.length >= 2 && parts[0].equalsIgnoreCase(word)) {
                    if (result.length() > 0) {
                        result += (", ");
                    }
                    result += (parts[1]); // acrescenta URL associado
                }
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Palavra não encontrada.";
    }

    // Procura determinado URL no File de URLs
    // Procura determinado URL no File de URLs
    public String searchURL(String url) {
        Map<String, Integer> urlCounts = new HashMap<>();
        Map<String, List<String>> urlAssociations = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(urlFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String associatedURL = parts[0];
                    String currentURL = parts[1];

                    if (currentURL.equals(url)) {
                        urlCounts.put(associatedURL, urlCounts.getOrDefault(associatedURL, 0) + 1);
                        urlAssociations.computeIfAbsent(currentURL, k -> new ArrayList<>()).add(associatedURL);
                    }
                }
            }
        
            // Contar quantas vezes cada URL associado aparece como currentURL
            try (BufferedReader reader2 = new BufferedReader(new FileReader(urlFile))) {
                while ((line = reader2.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        String currentURL = parts[1].trim();
                        if (urlCounts.containsKey(currentURL)) {
                            urlCounts.put(currentURL, urlCounts.getOrDefault(currentURL, 0) + 1);
                        }
                    }
                }
            }

            // Ordenar os URLs associados por contagem de menções em ordem decrescente
            List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(urlCounts.entrySet());
            sortedEntries.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

            // Construir o resultado
            StringBuilder result = new StringBuilder();
            for (Map.Entry<String, Integer> entry : sortedEntries) {
                if (result.length() > 0) {
                    result.append(", ");
                }
                result.append(entry.getKey());
            }

            return result.length() > 0 ? result.toString() : "URL não encontrada.";

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "URL não encontrada.";
    }



    public synchronized String register(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(loginsFile))) {  // Ler Ficheiro login.txt
            HashMap<String, String> logins = new HashMap<>();

            // Lê o conteúdo existente do arquivo, se houver
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                logins.put(parts[0], parts[1]);
            }

            // Verifica se o nome de usuário já existe no HashMap.
            if (logins.containsKey(username)) {
                return "Este utilizador já existe";
            }

            // Acrescenta o novo login ao HashMap
            logins.put(username, password);

            // Guarda o HashMap atualizado no File
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(loginsFile))) {
                for (String key : logins.keySet()) {
                    writer.write(key + ":" + logins.get(key));
                    writer.newLine();
                }
                return "Utilizador registado com sucesso.";
            } catch (IOException e) {
                e.printStackTrace();
                return "Erro ao salvar os dados de login.";
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "Erro ao ler os dados de login.";
        }
    }

    public String login(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(loginsFile))) {  // Ler Ficheiro login.txt
            HashMap<String, String> logins = new HashMap<>();

            // Lê o conteúdo existente do arquivo, se houver
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                logins.put(parts[0], parts[1]);
            }

            // Verifica se o nome de usuário existe no HashMap.
            if (logins.containsKey(username)) {
                String user_password = logins.get(username);
                if (user_password.equals(password)) {  // Verifica se a senha fornecida corresponde à senha armazenada no HashMap.
                    return "Login efetuado com sucesso";
                } else {
                    return "Password errada";
                }
            } else {
                return "Utilizador não encontrado";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Ocorreu um erro no login";
        }
    }

    public String getBarrelReg(){
        return this.barrelReg;
    }


    public static void addToListBarrels(){
        try{
            // Obtém a referência para o registro RMI na máquina local e procura pelo objeto registrado com o nome "Gateaway".
            //RMIGateaway rmiGateaway = (RMIGateaway) LocateRegistry.getRegistry("localhost", 5000).lookup("Gateaway");
            // Exporta o objeto atual e adiciona ao registro RMI no RMIGateaway.
            //RMIIndexStorageBarrel rmiBarrel = (RMIIndexStorageBarrel) UnicastRemoteObject.exportObject(this, 0);
            RMIGateaway.addBarrel(rmiBarrel);        
        } catch (Exception e) {
            System.out.println("Erro: Nao foi possivel connectar ao Gateaway");
            //System.exit(0);
            /*
            System.err.println(e.getMessage());
            e.printStackTrace();
            */
        }
    }

    public static void removeBarrelFromList() {
        try{
            // Obtém a referência para o registro RMI na máquina local e procura pelo objeto registrado com o nome "Gateaway".
            //RMIGateaway rmiGateaway = (RMIGateaway) LocateRegistry.getRegistry("localhost", 5000).lookup("Gateaway");
            // Exporta o objeto atual e adiciona ao registro RMI no RMIGateaway.
            //RMIIndexStorageBarrel rmiBarrel = (RMIIndexStorageBarrel) UnicastRemoteObject.exportObject(this, 0);
            RMIGateaway.removeBarrel(rmiBarrel);        
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    // Atualizar os Files
    public void updateTextFiles(URL data) {
        // Escrever palavras no File das words
        List<String> words = data.getWords();
        Set<String> writtenLinks = new HashSet<>();

        for (String word : words) {
            if (!word.isEmpty()) {
                String link = word + "," + data.url;
                if (!writtenLinks.contains(link)) {
                    writtenLinks.add(link);
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(wordsFile, true))) {
                        writer.write(word + "," + data.url + "|" + data.title + "|" + data.quote);
                        writer.newLine();
                    } catch (IOException e) {
                        System.out.println("Erro ao escrever no arquivo words.txt.");
                        e.printStackTrace();
                    }
                }
            }
        }

        // Escrever links no File dos links
        List<String> links = data.getLinks();
        for (String link : links) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(urlFile, true))) {
                writer.write(link + "," + data.getUrl());
                writer.newLine();
            } catch (IOException e) {
                System.out.println("Erro ao escrever no arquivo urls.txt.");
                e.printStackTrace();
            }
        }
    }


    public static class BarrelThread implements Runnable {

        @SuppressWarnings("deprecation")
        @Override
        public void run() {
            IndexStorageBarrel storageBarrel = new IndexStorageBarrel();  // Cria IndexStorageBarrel para lidar com o armazenamento dos dados.
            //new IndexStorageBarrel();  // Cria IndexStorageBarrel para lidar com o armazenamento dos dados.
            while(true){
                try {
                    // Cria a Socket de multicast
                    InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
                    MulticastSocket socket = new MulticastSocket(PORT);
                    System.out.println("Connected to MultiCast address: " + MULTICAST_ADDRESS + " Port: " + PORT);
                    socket.joinGroup(group);
                    
                    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                        System.out.println("Received Ctrl+C, closing...");
                        removeBarrelFromList();
                    }));

                    // Receber os dados
                    byte[] buf = new byte[BUF_SIZE];
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);
    
                    // Processa os dados recebidos
                    String received = new String(packet.getData(), 0, packet.getLength());
                    System.out.println("Received: " + received); // TEST
                    String[] data = received.split(" ; ");
                
                    URL url = new URL(""); // Cria URL para armazenar dados
    
                    for (String key_value : data) {
                        if (key_value.length() > 1){
                            String[] key = key_value.split(" | ");
    
                            switch (key[0]) {  // Verifica tipo de dados
                                case "url":
                                    System.out.println(key[2]);
                                    url.setUrl(key[2]);
                                    break;
                                
                                case "title":
                                    url.setTitle(key[2]);
                                    break;
    
                                case "quote":
                                    StringBuilder quoteBuilder = new StringBuilder();
                                    for (int i = 2; i < key.length; i++) {
                                        quoteBuilder.append(key[i]);
                                        if (i < key.length - 1) {
                                            quoteBuilder.append(" ");
                                        }
                                    }
                                    String quote = quoteBuilder.toString();
                                    url.setQuote(quote);
                                    break;
                                case "words":
                                    key = key_value.split(",");
                                    for (int i = 1; i < key.length; i++){
                                        url.addWords(key[i]);
                                    }
                                    break;
                                
                                case "links":
                                    key = key_value.split(",");
                                    for (int i = 1; i < key.length; i++) {
                                        String item = key[i];
                                        if (!(url.getLinks().contains(item)))
                                            url.addLinks(item);
                                    }
                                    break;
    
                                default:
                                    System.out.println(key[2]);
                                    break;
                            }
                        }
                    }

                    storageBarrel.updateTextFiles(url);  // Atualiza os Files
                    
                    // Sai do grupo Multicast e fecha o socket
                    socket.leaveGroup(group);
                    socket.close();
    
                } catch (IOException e) {
                    e.printStackTrace();
                }  
            }
        }
    }
}