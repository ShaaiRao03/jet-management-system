package com.example.programming2_c1.AdminControllers;

import com.example.programming2_c1.ClientControllers.ClientInterfaceController;
import com.example.programming2_c1.JetClasses.JetData;
import com.example.programming2_c1.JetClasses.PrivateJet;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JetContentDataOperationTest {

    static JetContentDataOperation jetContentDataOperation = new JetContentDataOperation();
    @Test
    void redisplayBasedOnFilter() throws InterruptedException {
        PrivateJet jet1 = JetData.getPrivateJetGivenID("20370"); // Manufacturer  : Bombardier  , Model : Challenger 300
        PrivateJet jet2 = JetData.getPrivateJetGivenID("525B-0146"); // Manufacturer  : Cessna , Model : Citation CJ3
        PrivateJet jet3 = JetData.getPrivateJetGivenID("TH-2177"); // Manufacturer  : Beechcraft , Model : G58 Baron

        ArrayList<PrivateJet> jetList = new ArrayList<>();
        jetList.add(jet1);
        jetList.add(jet2);
        jetList.add(jet3);

        // test scenario 1: no filter [return all]
        ArrayList<PrivateJet> result1 = jetContentDataOperation.redisplayBasedOnFilter(jetList,"", null, null );
        assertEquals(jetList, result1);


        // test scenario 2: manufacturer filter
        ArrayList<PrivateJet> result2 = jetContentDataOperation.redisplayBasedOnFilter(jetList,"", "1 - Cessna", null);
        ArrayList<PrivateJet> expected2 = new ArrayList<>();
        expected2.add(jet2);
        assertEquals(expected2, result2);


        // test scenario 3: manufacturer and model filter [no possibility for mismatch of manufacturer and model / model exist without manufacturer]
        ArrayList<PrivateJet> result3 = jetContentDataOperation.redisplayBasedOnFilter(jetList,"", "1 - Beechcraft", "1 - G58 Baron");
        ArrayList<PrivateJet> expected3 = new ArrayList<>();
        expected3.add(jet3);
        assertEquals(expected3, result3);


        //test scenario 4: search term filter [by name]
        ArrayList<PrivateJet> result6 = jetContentDataOperation.redisplayBasedOnFilter(jetList,"cessna", null, null);
        ArrayList<PrivateJet> expected6 = new ArrayList<>();
        expected6.add(jet2);
        assertEquals(expected6, result6);


        //test scenario 5: search term filter [by a common character]
        ArrayList<PrivateJet> result7 = jetContentDataOperation.redisplayBasedOnFilter(jetList,"e", null, null);
        ArrayList<PrivateJet> expected7 = new ArrayList<>();
        expected7.add(jet1);
        expected7.add(jet2);
        expected7.add(jet3);
        assertEquals(expected7, result7);


        //test scenario 6: combination of filters [search term ,manufacturer , model] { found }
        ArrayList<PrivateJet> result11 = jetContentDataOperation.redisplayBasedOnFilter(jetList,"c", "1 - Bombardier", "1 - Challenger 300");
        ArrayList<PrivateJet> expected11 = new ArrayList<>();
        expected11.add(jet1);
        assertEquals(expected11, result11); //Manufacturer  : Bombardier  , Model : Challenger 300
    }


    @Test
    void getModel(){
        assertEquals("Challenger 300",jetContentDataOperation.getSelectedManufacturer("1 - Challenger 300"));
        assertEquals("Challenger",jetContentDataOperation.getSelectedManufacturer("1 - Challenger"));
    }

    @Test
    void getManufacturer(){
        assertEquals("Bombardier",jetContentDataOperation.getSelectedManufacturer("1 - Bombardier"));
        assertEquals("Dassault G4",jetContentDataOperation.getSelectedManufacturer("1 - Dassault G4"));
    }
}