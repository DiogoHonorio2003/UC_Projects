package com.projetosd.meta2;
import java.rmi.Remote;

public interface RMIClient extends Remote{
    public void receiveMessage(String s) throws java.rmi.RemoteException;
}
