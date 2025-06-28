package com.projetosd.meta2;

//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.FileInputStream;
//import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

//import javax.servlet.http.HttpServletRequest;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import javax.servlet.http.HttpServletRequest;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//import java.util.Date;

import java.util.Arrays;


@Controller
public class ClientController {



    //@Autowired
    private RMIGateaway rmiGateaway;

    @GetMapping("/")
    public String showHomePage() {
        return "redirect:/register";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpServletResponse response) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("C:\\Users\\bruno\\Desktop\\SD\\SD_Meta2\\meta2\\src\\main\\java\\com\\projetosd\\meta2\\config.properties"));
            int port = Integer.parseInt(properties.getProperty("rmi.gateaway"));
            rmiGateaway = (RMIGateaway) LocateRegistry.getRegistry(port).lookup("Gateaway");
            String loginResult = rmiGateaway.check_login(username, password);
            model.addAttribute("loginResult", loginResult);
            if (loginResult.equals("Login efetuado com sucesso")) {
                return "menu";
            }
        } catch (Exception e) {
            model.addAttribute("loginResult", "Erro ao efetuar login.");
        }
        return "login";
    }

    @GetMapping("/menu")
    public String showMenuPage() {
        return "menu";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, Model model) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("C:\\Users\\bruno\\Desktop\\SD\\SD_Meta2\\meta2\\src\\main\\java\\com\\projetosd\\meta2\\config.properties"));
            int port = Integer.parseInt(properties.getProperty("rmi.gateaway"));
            rmiGateaway = (RMIGateaway) LocateRegistry.getRegistry(port).lookup("Gateaway");
            String registerResult = rmiGateaway.check_register(username, password);
            model.addAttribute("registerResult", registerResult);
            if (registerResult.equals("Utilizador registado com sucesso.")) {
                return "login";
            }
        } catch (Exception e) {
            model.addAttribute("registerResult", "Erro ao efetuar registo.");
        }
        return "register";
    }
    
    @GetMapping("/url")
    public String showUrlPage(){
        return "url";
    }

    @PostMapping("/url")
    public String url(@RequestParam String url, Model model){
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("C:\\Users\\bruno\\Desktop\\SD\\SD_Meta2\\meta2\\src\\main\\java\\com\\projetosd\\meta2\\config.properties"));
            int port = Integer.parseInt(properties.getProperty("rmi.gateaway"));
            rmiGateaway = (RMIGateaway) LocateRegistry.getRegistry(port).lookup("Gateaway");

            if (!url.startsWith("https://")){
                model.addAttribute("urlResult", "URL invalido");
                return "url";
            }

            rmiGateaway.send_url(url);
            String urlResult = "Enviado";
            model.addAttribute("urlResult", urlResult);

        } catch (Exception e) {
            model.addAttribute("urlResult", "Erro ao executar o pedido.");
        }
        return "url";
    }

    @GetMapping("/search")
    public String showSearchPage(){
        return "search";
    }

    @PostMapping("/search")
    public String search(@RequestParam String words, Model model){
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("C:\\Users\\bruno\\Desktop\\SD\\SD_Meta2\\meta2\\src\\main\\java\\com\\projetosd\\meta2\\config.properties"));
            int port = Integer.parseInt(properties.getProperty("rmi.gateaway"));
            rmiGateaway = (RMIGateaway) LocateRegistry.getRegistry(port).lookup("Gateaway");

            String searchResult = rmiGateaway.search(words);

            if (searchResult.equals("Erro: Nao existe nenhum Barrel ativo\n")){
                model.addAttribute("searchResult", searchResult);
                return "search";
            }

            if (searchResult.length() <= 1){
                searchResult = "Nao foi possivel encontrar os termos fornecidos na base de dados";
                model.addAttribute("searchResult", searchResult);
                return "search";
            }

            String[] resultsArray = searchResult.split(", ");
            List<String> resultsList = Arrays.asList(resultsArray);

            // Lógica para dividir os resultados em lotes de 10
            List<List<String>> batches = new ArrayList<>();
            int batchSize = 10;
            int totalResults = resultsList.size();
            for (int i = 0; i < totalResults; i += batchSize) {
                int endIndex = Math.min(i + batchSize, totalResults);
                List<String> batch = resultsList.subList(i, endIndex);
                batches.add(batch);
            }

            model.addAttribute("batches", batches);

        } catch (Exception e) {
            model.addAttribute("searchResult", "Erro ao executar o pedido.");
        }

        return "search";
    }

    @GetMapping("/links")
    public String showLinksPage(){
        return "links";
    }

    @PostMapping("/links")
    public String links(@RequestParam String links, Model model){
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("C:\\Users\\bruno\\Desktop\\SD\\SD_Meta2\\meta2\\src\\main\\java\\com\\projetosd\\meta2\\config.properties"));
            int port = Integer.parseInt(properties.getProperty("rmi.gateaway"));
            rmiGateaway = (RMIGateaway) LocateRegistry.getRegistry(port).lookup("Gateaway");

            String linkResult = rmiGateaway.searchLink(links);

            if (!links.startsWith("https://")){
                model.addAttribute("linkResult", "URL invalido");
                return "links";
            }

            if (linkResult.equals("Erro: Nao existe nenhum Barrel ativo\n")){
                model.addAttribute("linkResult", linkResult);
                return "links";
            }

            if (linkResult.length() <= 1){
                linkResult = "Nao foi possivel encontrar os termos fornecidos na base de dados";
                model.addAttribute("linkResult", linkResult);
                return "links";
            }

            String[] linksArray = linkResult.split(", ");
            List<String> linksList = Arrays.asList(linksArray);

            // Lógica para dividir os resultados em lotes de 10
            List<List<String>> batches = new ArrayList<>();
            int batchSize = 10;
            int totalResults = linksList.size();
            for (int i = 0; i < totalResults; i += batchSize) {
                int endIndex = Math.min(i + batchSize, totalResults);
                List<String> batch = linksList.subList(i, endIndex);
                batches.add(batch);
            }

            model.addAttribute("batches", batches);

        } catch (Exception e) {
            model.addAttribute("linkResult", "Erro ao executar o pedido.");
        }

        return "links";
    }  
}