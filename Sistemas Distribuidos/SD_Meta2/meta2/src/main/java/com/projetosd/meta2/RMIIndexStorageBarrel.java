package com.projetosd.meta2;
import java.rmi.Remote;

public interface RMIIndexStorageBarrel extends Remote{
    public String getBarrelReg() throws java.rmi.RemoteException;
    public String searchURL(String url) throws java.rmi.RemoteException;
    public String searchWord(String term) throws java.rmi.RemoteException;
    public String login(String username, String password) throws java.rmi.RemoteException;
    public String register(String username, String password) throws java.rmi.RemoteException;
    public void updateTextFiles(URL data) throws java.rmi.RemoteException;
}
