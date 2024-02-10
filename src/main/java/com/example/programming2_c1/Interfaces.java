package com.example.programming2_c1;

import com.example.programming2_c1.JetClasses.JetData;
import com.example.programming2_c1.JetClasses.PrivateJet;

import java.util.ArrayList;

public class Interfaces {

    public interface ClientInterface{
        public abstract void redirectLoginPage();
        public abstract void displayCartTable();
        public abstract void displayDashboard();
        public abstract void displayTransactionTable();
        public abstract void displayInventory(ArrayList<PrivateJet> privateJets);
    }

    public interface AdminInterface{

        public abstract void redirectLoginPage();
        public abstract void displayAddDataTable();
        public abstract void displayDashboard();
        public abstract void displayTransactionTables(String caller);
    }


}