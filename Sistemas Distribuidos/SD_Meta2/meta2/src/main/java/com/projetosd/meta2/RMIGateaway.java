package com.projetosd.meta2;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIGateaway extends Remote{
    public String check_login(String username, String password) throws java.rmi.RemoteException;
    public String check_register(String username, String password) throws java.rmi.RemoteException;
    public void send_url(String url) throws java.rmi.RemoteException;
    public String search(String words) throws java.rmi.RemoteException;
    public void admin_subscribe(RMIClient admin) throws java.rmi.RemoteException;
    public void admin_send(String message) throws java.rmi.RemoteException;
    public void addBarrel(RMIIndexStorageBarrel barrel) throws java.rmi.RemoteException;
    public void removeBarrel(RMIIndexStorageBarrel barrel) throws RemoteException;
    public String searchLink(String link) throws java.rmi.RemoteException;
    public void admin_unsubscribe(RMIClient admin) throws java.rmi.RemoteException;
    public String showCurrentBarrel() throws java.rmi.RemoteException;
    String getTop10KeysAsString() throws java.rmi.RemoteException;
}
