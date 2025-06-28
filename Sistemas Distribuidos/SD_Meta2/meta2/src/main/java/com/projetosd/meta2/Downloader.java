package com.projetosd.meta2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
//import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Downloader {

    // Variaveis estaticas

    public static int PORT;  // Porta do Multicast
    public static int PORTClient;  // Porta de conexão com o Gateaway 
    public static long SLEEP_TIME; // Tempo de espera entre Downloaders
    public static int NUM_DOWNLOADERS;  // Numero de Downloaders
  
    public static InetAddress enderecoGrupo;  // Endereço do grupo multicast
  
    public static LinkedBlockingDeque<String> urls;  // Fila de URLs
  
    public static Document doc;  

    
    // Construtor do Downloader - Inicia as variaveis
    public Downloader(int PORTClient_ini, int PORT_ini, int NUM_DOWNLOADERS_ini, long SLEEP_TIME_ini, String mc_address ) {
      PORTClient = PORTClient_ini;
      PORT = PORT_ini;
      try {
        enderecoGrupo = InetAddress.getByName(mc_address);
      } catch (UnknownHostException e) {
        e.printStackTrace();
      }
      NUM_DOWNLOADERS = NUM_DOWNLOADERS_ini;
      SLEEP_TIME = SLEEP_TIME_ini;
      urls = new LinkedBlockingDeque<String>();
    }

    // Iniciar o programa
    public static void main(String args[]) {
        Properties properties = new Properties();
        try{
            // Ler o ficheiro config.properties
            properties.load(new FileInputStream("C:\\Users\\bruno\\Desktop\\SD\\SD_Meta2\\meta2\\src\\main\\java\\com\\projetosd\\meta2\\config.properties"));
            int tcp_port = Integer.parseInt(properties.getProperty("down.PORTClient"));
            int port = Integer.parseInt(properties.getProperty("mc.port"));
            int num_down = Integer.parseInt(properties.getProperty("down.NUM_DOWNLOADER"));
            long sleep = Integer.parseInt(properties.getProperty("down.SLEEP_TIME"));
            String multicast_add = properties.getProperty("mc.address");

            Downloader downloader = new Downloader(tcp_port, port, num_down, sleep, multicast_add);
            downloader.start();

        } catch (IOException e){
            System.out.println("IO Exception in Gateaway: Nao foi possivel ler o ficheiro config.properties " + e);
        }
    }

    // Iniciar os Downloaders e o Socket do Client
    public void start(){
        ExecutorService downloaders_executed = Executors.newFixedThreadPool(NUM_DOWNLOADERS);  // Cria um pool de threads para os Downloaders
        try (ServerSocket listenSocket = new ServerSocket(PORTClient)) {  // Listen porta do Cliente
            System.out.println("A escuta no porto 6000");
            System.out.println("LISTEN SOCKET=" + listenSocket);
            while(true) {
                Socket clientSocket = listenSocket.accept();  // Aceita conexão do cliente
                System.out.println("CLIENT_SOCKET (created at accept())="+clientSocket);   
                new Client_TCP(clientSocket, urls).start(); 

                for(int i = 0; i <= NUM_DOWNLOADERS; i++){
                    downloaders_executed.execute(new WebSearch(urls));  // Inicia Downloaders
                    System.out.println("Downloader " + i + " initialized");
                    try{
                        Thread.sleep(SLEEP_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch(IOException e) {
            System.out.println("Listen:" + e.getMessage());
        } finally{ 
            downloaders_executed.shutdown();
            try {
                if (!downloaders_executed.awaitTermination(5, TimeUnit.SECONDS)) {
                    downloaders_executed.shutdownNow();
                }
            } catch (InterruptedException ie) {
                downloaders_executed.shutdownNow();
            }
        }
    }

    // Lidar com as conexões TCP dos Clients
    public class Client_TCP extends Thread{
        private DataInputStream in;   // Entrada de dados
        private LinkedBlockingDeque<String> urls;  //Fila para armazenar URLs
        private Socket socket;

        // Construtor
        public Client_TCP (Socket aClientSocket, LinkedBlockingDeque<String> urls) throws IOException{
            this.socket = aClientSocket;
            this.urls = urls;
            this.in = new DataInputStream(aClientSocket.getInputStream());
        }

        @Override
        public void run(){
            try{
                while (true) {
                    String data = "";
                    data = in.readUTF(); // Ler do DataInputStream

                    if (in.available() > 0) {
                        System.out.println("Recebeu " + data);
                    }
                    if(!data.isEmpty()){  // Se existir dados recebe e adiciona a fila
                        System.out.println("Recebeu: " + data);
                        urls.addFirst(data);

                        System.out.println("URL ADDED = " + data);
                    }
                }
            } catch(EOFException e) {
                System.err.println("EOF:" + e);
            } catch(IOException e) {
                System.out.println("IO:" + e);
            } finally {
                try{  // fecha fluxos de entrada e o socket
                    in.close();
                    socket.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // Classe para procurar e processar paginas web (WebCrawler)
    private static class WebSearch implements Runnable {

        private BlockingQueue<String> urls;  //Fila URLs

        // Construtor WebSearch
        public WebSearch(BlockingQueue<String> urls){
            this.urls = urls;
        }

        @Override
        public void run() {
            try{
                String url_text = urls.take();  // Espera a chegada de um URL
                WebSearcher(url_text); // Chama WebSearcher
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Processar a pagina Web
        public void WebSearcher(String url_text) throws IndexOutOfBoundsException, NullPointerException{
            try {

                URL url = new URL(url_text);

                //fazer o pedido ao URL
                Document doc = Jsoup.connect(url_text).get();

                //Separar palavras
                StringTokenizer tokens = new StringTokenizer(doc.text());

                int countTokens = 0;
                String quote = "";
                while (tokens.hasMoreElements()){  // Adicionar Words
                    String token = tokens.nextToken().toLowerCase();
                    url.addWords(token);    
                    if (countTokens < 10){   // Criar quote
                        quote += token + " ";
                        countTokens ++;
                    }
                }

                //extrair os links para outras paginas
                Elements links = doc.select("a[href]");
                ArrayList<String> uniqueLinks = new ArrayList<>();
                for (Element link : links){
                    String link_unique = link.attr("abs:href");
                    if (!link_unique.isEmpty() && !uniqueLinks.contains(link_unique)) {
                        uniqueLinks.add(link_unique);
                        urls.add(link_unique);
                    }
                }

                for(String link : uniqueLinks){
                    url.addLinks(link);
                }
                


                url.setUrl(url_text);  // Adicionar URL
                url.setTitle(doc.title());   // Adicionar title
                url.setQuote(quote); // Adicionar quote
                

                sendMulticast(url);  // Enviar ficheiro por Multicast

            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Enviar informação por Multicast
    public static void sendMulticast(URL url) {
        MulticastSocket socket = null;
        try{
            socket = new MulticastSocket(PORT);  // Criar Socket Multicast 

            HashMap<String, String> index = new HashMap<>();  // Criação HashMap

            // Colocar info no HashMap
            index.put("url", url.getUrl());
            index.put("title", url.getTitle());
            index.put("quote", url.getQuote());
            if (url.getWords() == null || url.getWords().isEmpty()) {
                index.put("words", " ");
            } else {
                index.put("words", url.getWords().toString().replaceAll("[\\[\\]\\s]", ""));
            }
            if (url.getLinks() == null || url.getLinks().isEmpty()) {
                index.put("links", " ");
            } else {
                index.put("links", url.getLinks().toString().replaceAll("[\\[\\]\\s]", ""));
            }

            // Construção da mensagem
            StringBuilder message = new StringBuilder();
            for (String key : index.keySet()) {
                message.append(key)
                       .append(" | ")
                       .append(index.get(key))
                       .append(" ; ");
            }
            message.append("\n");
            byte[] buffer = message.toString().getBytes();

            // Cria pacote de dados UDP
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, enderecoGrupo, PORT);
            // Envia o pacote atraves do Socket Multicast
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }
}