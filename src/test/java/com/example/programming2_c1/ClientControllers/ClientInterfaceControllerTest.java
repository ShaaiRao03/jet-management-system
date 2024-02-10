package com.example.programming2_c1.ClientControllers;

import com.example.programming2_c1.JetClasses.*;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ClientInterfaceControllerTest {

    @Test
    void redisplayBasedOnFilter() throws InterruptedException {

        /*
        NOTE : in this tester , make sure that the "prefSearch" is in lowercase. This is because in the real program , the "prefSearch" will be converted to lowercase by
        another method called "initiateSearch()" as there will be some filtration before passing the variables into this method whereas ,in this tester, we are directly acessing the method without
        any filtration. Hence, "prefSearch" will not be automatically converted to lowercase
         */

        //first priority is given to  number of passengers and hours since new
        //second priority is given to manufacturer and model
        //third priority is given to search term ( search terms can be used to search by using jet name , year or ID only[without S/N] )


        // create test data
        //the values in these id(s) MUST NOT BE CHANGED TO ENSURE TEST PASS
        PrivateJet jet1 = JetData.getPrivateJetGivenID("20370"); // Manufacturer  : Bombardier  , Model : Challenger 300 , Year : 2012 , Passenger : 4 , Hours Since New : 2651
        PrivateJet jet2 = JetData.getPrivateJetGivenID("525B-0146"); // Manufacturer  : Cessna , Model : Citation CJ3 , Year : 2007 , Passenger : 14, Hours Since New : 3218
        PrivateJet jet3 = JetData.getPrivateJetGivenID("TH-2177"); // Manufacturer  : Beechcraft , Model : G58 Baron , Year : 2007 , Passenger : 6, Hours Since New : 2283

        ArrayList<PrivateJet> jetList = new ArrayList<>();
        jetList.add(jet1);
        jetList.add(jet2);
        jetList.add(jet3);


        // create test object
        ClientInterfaceController clientInterfaceController = new ClientInterfaceController();

        // test scenario 1: no filter [return all]
        ArrayList<PrivateJet> result1 = clientInterfaceController.redisplayBasedOnFilter(jetList,"", "", "", 4, 0);
        assertEquals(jetList, result1);


        // test scenario 2: manufacturer filter
        ArrayList<PrivateJet> result2 = clientInterfaceController.redisplayBasedOnFilter(jetList,"", "Cessna", "", 4, 0);
        ArrayList<PrivateJet> expected2 = new ArrayList<>();
        expected2.add(jet2);
        assertEquals(expected2, result2);


        // test scenario 3: manufacturer and model filter [no possibility for mismatch of manufacturer and model / model exist without manufacturer]
        ArrayList<PrivateJet> result3 = clientInterfaceController.redisplayBasedOnFilter(jetList,"", "Beechcraft", "G58 Baron", 4, 0);
        ArrayList<PrivateJet> expected3 = new ArrayList<>();
        expected3.add(jet3);
        assertEquals(expected3, result3);



        // test scenario 4: search term filter [by year]
        ArrayList<PrivateJet> result4 = clientInterfaceController.redisplayBasedOnFilter(jetList,"2007", "", "", 4, 0);
        ArrayList<PrivateJet> expected4 = new ArrayList<>();
        expected4.add(jet2);
        expected4.add(jet3);
        assertEquals(expected4, result4);


        // test scenario 5: search term filter [by id]
        ArrayList<PrivateJet> result5 = clientInterfaceController.redisplayBasedOnFilter(jetList,"20370", "", "", 4, 0);
        ArrayList<PrivateJet> expected5 = new ArrayList<>();
        expected5.add(jet1);
        assertEquals(expected5, result5);


        //test scenario 6: search term filter [by name]
        ArrayList<PrivateJet> result6 = clientInterfaceController.redisplayBasedOnFilter(jetList,"cessna", "", "", 4, 0);
        ArrayList<PrivateJet> expected6 = new ArrayList<>();
        expected6.add(jet2);
        assertEquals(expected6, result6);


        //test scenario 7: search term filter [by a common character]
        ArrayList<PrivateJet> result7 = clientInterfaceController.redisplayBasedOnFilter(jetList,"e", "", "", 4, 0);
        ArrayList<PrivateJet> expected7 = new ArrayList<>();
        expected7.add(jet1);
        expected7.add(jet2);
        expected7.add(jet3);
        assertEquals(expected7, result7);


        // test scenario 8: prefPassenger
        ArrayList<PrivateJet> result8 = clientInterfaceController.redisplayBasedOnFilter(jetList,"", "", "", 8, 0);
        ArrayList<PrivateJet> expected8 = new ArrayList<>();
        expected8.add(jet2);
        assertEquals(expected8, result8);

        // test scenario 9: prefHours
        ArrayList<PrivateJet> result9 = clientInterfaceController.redisplayBasedOnFilter(jetList,"", "", "", 4, 3);
        ArrayList<PrivateJet> expected9 = new ArrayList<>();
        expected9.add(jet2);
        assertEquals(expected9, result9);


        // test scenario 10: combination of filters [manufacturer , model , passenger , hours]
        ArrayList<PrivateJet> result10 = clientInterfaceController.redisplayBasedOnFilter(jetList,"", "Beechcraft", "G58 Baron", 6, 0);
        ArrayList<PrivateJet> expected10 = new ArrayList<>();
        expected10.add(jet3);
        assertEquals(expected10, result10);

//         test scenario 11: combination of filters [search term ,manufacturer , model , passenger , hours { found }
        ArrayList<PrivateJet> result11 = clientInterfaceController.redisplayBasedOnFilter(jetList,"c", "Bombardier", "Challenger 300", 4, 0);
        ArrayList<PrivateJet> expected11 = new ArrayList<>();
        expected11.add(jet1);
        assertEquals(expected11, result11); //Manufacturer  : Bombardier  , Model : Challenger 300 , Year : 2012 , Passenger : 4 , Hours Since New : 2651


        // test scenario 12: combination of filters [search term ,manufacturer , model , passenger , hours] { not found due to either one doesnt match}
        ArrayList<PrivateJet> result12 = clientInterfaceController.redisplayBasedOnFilter(jetList,"dkms", "Bombardier", "Challenger 300", 4, 0);
        ArrayList<PrivateJet> expected12 = new ArrayList<>();
        assertEquals(expected12, result12);

        // test scenario 13: pref hours and passengers
        ArrayList<PrivateJet> result13 = clientInterfaceController.redisplayBasedOnFilter(jetList,"", "", "", 4, 3);
        ArrayList<PrivateJet> expected13 = new ArrayList<>();
        expected13.add(jet2);
        assertEquals(expected13, result13);
    }
}