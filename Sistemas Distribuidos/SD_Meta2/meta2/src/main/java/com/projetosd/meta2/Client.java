package com.projetosd.meta2;

import java.io.FileInputStream;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.Scanner;

public class Client implements Serializable, RMIClient{
    public static RMIGateaway RMIGateaway;
    static Scanner scanner;
    private static RMIClient rmiClient;

    public Client() {
        try {
            // Exporta o objeto do cliente para que ele possa ser acessível remotamente
            rmiClient = (RMIClient) UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException e) {
            System.out.println("Erro ao inicializar client");
        }
    }

    // Implementação do método remoto para receber mensagens do servidor
    @Override
    public void receiveMessage(String message) throws RemoteException {
        String timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        System.out.println("Server ( "+ timeStamp +" ): " + message);
    }

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        try{
            scanner = new Scanner(System.in);

            // Ler o ficheiro config.properties
            properties.load(new FileInputStream("C:\\Users\\bruno\\Desktop\\SD\\SD_Meta2\\meta2\\src\\main\\java\\com\\projetosd\\meta2\\config.properties"));
            int port = Integer.parseInt(properties.getProperty("rmi.gateaway"));

            RMIGateaway = (RMIGateaway) LocateRegistry.getRegistry(port).lookup("Gateaway"); // Obter a referência remota para o servidor utilizando RMI
            new Client();
            boolean isConnected = login(); // Realização do login
            // Apenas sair do loop ate o cliente se registar ou dar login
            while(isConnected == false){
                isConnected = login();
            }

            while(isConnected) {
                // Exibição do menu para o cliente
                System.out.println("\n\n\tMENU GOOGOL");
                System.out.println("1: Introduzir um URL");
                System.out.println("2: Pesquisar paginas atraves de um conjunto de termos");
                System.out.println("3: Consultar lista de paginas associadas a uma determinada ligacao");
                System.out.println("4: Request admin");
                System.out.println("5: Dismiss admin");
                System.out.println("6: Admin: status");
                System.out.println("7: Exit");
                String response = scanner.nextLine();
            
                // Execução da acaoo correspondente a opcaoo escolhida pelo cliente
                switch(response){
                    case "1":
                        System.out.println("Insira o URL: ");
                        String url = scanner.nextLine();
                        if(!url.startsWith("https://")){
                            System.out.println("URL valido.");
                            break;
                        }
                        RMIGateaway.send_url(url);
                        System.out.println("Enviado");
                        break;

                    case "2":
                        int count = 0;
                        System.out.print("Pesquisa por termos: ");
                        String search = scanner.nextLine();
                        String server_result = RMIGateaway.search(search);
                        //System.out.print(server_result);
                        if (server_result.equals("Erro: Nao existe nenhum Barrel ativo\n")){System.out.println(server_result);break;}
                        if (server_result.length() <= 1){System.out.println("Nao foi possivel encontrar os termos fornecidos na base de dados");break;}
                        for (String result : server_result.split(", ")){  
                            if (count == 10){
                                System.out.println("\nPrecione ENTER para continuar (ou qualquer outra tecla para sair)");
                                if (scanner.nextLine().equals("")) {count = 0; continue;}
                                break;
                            }
                            System.out.println("-> " + result + "\n"); count++;
                        }
                        break;
                        
                    case "3":
                        count = 0;
                        System.out.println("Insira o URL: ");
                        search = scanner.nextLine();
                        server_result = RMIGateaway.searchLink(search);
                        //System.out.println(server_result);
                        if (server_result.equals("Erro: Nao existe nenhum Barrel ativo\n")){System.out.println(server_result);break;}
                        if (server_result.length() <= 1){System.out.println("Nao foi possivel encontrar links associados");break;}
                        for (String result : server_result.split(", ")){
                            if (count == 10){
                                System.out.println("\nPrecione ENTER para continuar (ou qualquer outra tecla para sair)");
                                if (scanner.nextLine().equals("")) {count = 0; continue;}
                                break;
                            }
                            System.out.println("-> " + result + "\n"); count++;
                        }
                        break;

                    case "4":
                        /*/
                        requestAdmin(rmiClient);
                        System.out.println("Recebendo mensagens do servido em tempo real: (Pressione q ou quit para sair)\ns: Show status");
                        Boolean isOn = true;
                        while(isOn){
                            String q = scanner.nextLine();
                            if (q.equals("q") || q.equals("quit")){
                                removeAdmin(rmiClient);
                                isOn = false;
                            }
                            if(q.equals("s")){
                                System.out.println(RMIGateaway.showCurrentBarrel() + "\n" + RMIGateaway.getTop10KeysAsString());
                            }
                        }
                        */
                        requestAdmin(rmiClient);
                        System.out.println("Recebendo updates do servidor em tempo real...\n");
                        break;

                    
                    case "5":
                        removeAdmin(rmiClient);
                        System.out.println("Ira deixar de receber admin updates...\n");
                        break;

                    case "6":
                        System.out.println(RMIGateaway.showCurrentBarrel() + "\n" + RMIGateaway.getTop10KeysAsString());
                        break;

                    case "7":
                        System.out.println("Programa terminado");
                        isConnected = false;
                        break;

                    default:
                        System.out.println("Nao foi possivel executar o pedido.");
                        break;
                }
            }
            // Encerramento do programa
            scanner.close();
            System.exit(0);
        } catch (NotBoundException | RemoteException e) {
            System.out.println("Erro ao iniciar pagina do Cliente.");
        }
    }

    // Método para solicitar tornar-se um cliente admin
    public static void requestAdmin(RMIClient rmiClient){
        try{
            //RMIGateaway rmiGateaway = (RMIGateaway) LocateRegistry.getRegistry(5000).lookup("Gateaway");
            RMIGateaway.admin_subscribe(rmiClient);
        } catch (Exception e) {
            System.out.println("Erro ao conectar com o servidor Gateaway");
        }        
    }

    // Método para remover status de admin do cliente
    public static void removeAdmin(RMIClient rmiClient){
        try{
            //RMIGateaway rmiGateaway = (RMIGateaway) LocateRegistry.getRegistry(5000).lookup("Gateaway");
            RMIGateaway.admin_unsubscribe(rmiClient);
        } catch (Exception e) {
            System.out.println("Erro ao conectar com o servidor Gateaway");
        }        
    }

    // Método para realizar login ou registro
    public static boolean login() {
        System.out.println("\n\tGOOGOL LOGIN / REGISTER PAGE");
        System.out.println("1: Login");
        System.out.println("2: Register");
        String res = scanner.nextLine();
        
        switch(res){
            case "1":
                System.out.println("Insira o seu Username: ");
                String username = scanner.nextLine();
                System.out.print("Insira a sua password: ");
                String password = scanner.nextLine();

                    String server_result;
                    try{
                        server_result = RMIGateaway.check_login(username, password);
                        System.out.println("-> " + server_result);
                        if(server_result.equals("Password errada") || server_result.equals("Utilizador não encontrado") || server_result.equals("Ocorreu um erro no login")){
                            return false;
                        }else if(server_result.equals("Login efetuado com sucesso")){ 
                            return true;
                        
                        }/*else{
                            System.out.println(server_result);
                        }
                        */
                        break;
                    } catch (RemoteException e) {
                        scanner.close();
                        System.out.println("Erro ao efutuar login.");
                    }
            case "2":
                System.out.println("Insira o seu Username: ");
                username = scanner.nextLine();
                System.out.print("Insira a sua password: ");
                password = scanner.nextLine();

                    try{
                        server_result = RMIGateaway.check_register(username, password);
                        System.out.println("-> " + server_result);
                        if(server_result.equals("Este utilizador já existe") || server_result.equals("Erro ao salvar os dados de login.")){
                            return false;
                        }else if(server_result.equals("Utilizador registado com sucesso.")){ 
                            return true;
                        
                        }/*else{
                            System.out.println(server_result);
                        }
                        */
                        break;
                    } catch (RemoteException e) {
                        scanner.close();
                        System.out.println("Erro ao efutuar registro.");
                    }
            default:
                break;
        }
        System.out.println("Nao foi possivel executar o pedido.");
        return false;
    }
}

