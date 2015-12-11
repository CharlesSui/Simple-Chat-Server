package test;


import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import static org.junit.Assert.*;
import org.junit.Test;

import g53sqm.chat.client.Client;

public class ClientTest {

	String expect, actual;
	static Client client1, client2, client3, client4, client5, client6, client7, client8, client9, client10, client11, client12;


	@Test
	public void testBasic(){

		String[] client1Commands = {"IDEN james", "HAIL Hello from client 1", "QUIT"}; // add more commands in here
		client1 = new Client(9000, "localhost", client1Commands, "client1");
		client1.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    client1.frame.setVisible(true);

	    ExecutorService executor = Executors.newCachedThreadPool();
	    executor.execute(client1);

	    try {
		      executor.awaitTermination(3, TimeUnit.SECONDS);
		    } catch(InterruptedException e){
		      e.printStackTrace();
		    }

		expect = "james: Hello from client 1";
	    actual = client1.getMessage();
	    System.out.println("actual: "+actual);
	    assertEquals(expect, actual);
	}

	@Test
	public void testFurther(){

		String[] client2Commands = {"IDEN tom", "LIST", "HAIL Hello from client 2", "QUIT"}; // add more commands in here
		client2 = new Client(9000, "localhost", client2Commands, "client2");
		client2.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    client2.frame.setVisible(true);

	    ExecutorService executor = Executors.newCachedThreadPool();
	    executor.execute(client2);
	    try {
		      executor.awaitTermination(3, TimeUnit.SECONDS);
		    } catch(InterruptedException e){
		      e.printStackTrace();
		    }

		expect = "tom: Hello from client 2";
	    actual = client2.getMessage();

	    System.out.println("actual: "+actual);
	    assertEquals(expect, actual);
	}

	@Test
	public void testStat(){

		String[] testStat = {"IDEN charles", "STAT", "HAIL Hello from client 3", "QUIT"}; //test STAT
		client3 = new Client(9000, "localhost", testStat, "client3");
		client3.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    client3.frame.setVisible(true);

	    ExecutorService executor = Executors.newCachedThreadPool();
	    executor.execute(client3);
	    try {
		      executor.awaitTermination(3, TimeUnit.SECONDS);
		    } catch(InterruptedException e){
		      e.printStackTrace();
		    }

		expect = "charles: Hello from client 3";
	    actual = client3.getMessage();

	    System.out.println("actual: "+actual);
	    assertEquals(expect, actual);
	}

	@Test
	public void testMesg(){


		String[] client1Commands = {"IDEN james", "STAT"}; // add more commands in here
		client1 = new Client(9000, "localhost", client1Commands, "client1");
		client1.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    client1.frame.setVisible(true);

	    ExecutorService executor = Executors.newCachedThreadPool();
	    executor.execute(client1);

	    try {
		      executor.awaitTermination(3, TimeUnit.SECONDS);
		    } catch(InterruptedException e){
		      e.printStackTrace();
		    }

		String[] testMesg = {"IDEN porden", "MESG james SQM is good", "QUIT"}; //test MESG
		client4 = new Client(9000, "localhost", testMesg, "client4");
		client4.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    client4.frame.setVisible(true);

	    executor.execute(client4);
	    try {
		      executor.awaitTermination(3, TimeUnit.SECONDS);
		    } catch(InterruptedException e){
		      e.printStackTrace();
		    }

		expect = "porden: james SQM is good";
	    actual = client4.getMessage();

	    System.out.println("actual: "+actual);
	    assertEquals(expect, actual);
	}

	@Test
	public void testNoNameCom(){

	    String[] testNoNameCom = {"IDENF sam", "HAIL Hello from client 6", "QUIT"}; // IDENE -- test invalid command cause no name
		client6 = new Client(9000, "localhost", testNoNameCom, "client6");
		client6.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    client6.frame.setVisible(true);

	    ExecutorService executor = Executors.newCachedThreadPool();
	    executor.execute(client6);
	    try {
		      executor.awaitTermination(3, TimeUnit.SECONDS);
		    } catch(InterruptedException e){
		      e.printStackTrace();
		    }

		expect = ": Hello from client 6";
	    actual = client6.getMessage();

	    System.out.println("actual: "+actual);
	    assertEquals(expect, actual);
	}

	@Test
	public void testNotLogInCom(){

	    String[] testNotLogInCom = {"HAIL Hello from client 7", "QUIT"}; // IDENE -- test invalid command cause no name
		client7 = new Client(9000, "localhost", testNotLogInCom, "client7");
		client7.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    client7.frame.setVisible(true);

	    ExecutorService executor = Executors.newCachedThreadPool();
	    executor.execute(client7);
	    try {
		      executor.awaitTermination(3, TimeUnit.SECONDS);
		    } catch(InterruptedException e){
		      e.printStackTrace();
		    }

		expect = "BAD You have not logged in yet";
	    actual = client7.getMessage();

	    System.out.println("actual: "+actual);
	    assertEquals(expect, actual);
	}

	@Test
	public void testNoHailCom(){

	    String[] testNoHailCom = {"IDEN bob", "HL hello from client 8", "QUIT"}; // HL -- test invalid command
		client8 = new Client(9000, "localhost", testNoHailCom, "client8");
		client8.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    client8.frame.setVisible(true);

	    ExecutorService executor = Executors.newCachedThreadPool();
	    executor.execute(client8);
	    try {
		      executor.awaitTermination(3, TimeUnit.SECONDS);
		    } catch(InterruptedException e){
		      e.printStackTrace();
		    }

		expect = "BAD command not recognised";
	    actual = client8.getMessage();

	    System.out.println("actual: "+actual);
	    assertEquals(expect, actual);
	}

	@Test
	public void testInvalidCom1(){

	    String[] testInvalidCom1 = {"IDE ming", "QUIT" };// IDE -- test invalid command
	    String[] testNotRecognisedCom1 = {"ABCD sam", "QUIT"}; // ABCD -- test command not recognized

		client9 = new Client(9000, "localhost", testInvalidCom1, "client9");
		client9.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    client9.frame.setVisible(true);

	    ExecutorService executor = Executors.newCachedThreadPool();
	    executor.execute(client9);
	    try {
		      executor.awaitTermination(3, TimeUnit.SECONDS);
		    } catch(InterruptedException e){
		      e.printStackTrace();
		    }


		client10 = new Client(9000, "localhost", testNotRecognisedCom1, "client10");
		client10.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    client10.frame.setVisible(true);

	    executor.execute(client10);
	    try {
		      executor.awaitTermination(3, TimeUnit.SECONDS);
		    } catch(InterruptedException e){
		      e.printStackTrace();
		    }

		expect = "BAD command not recognised";
	    actual = client9.getMessage();

	    System.out.println("actual: "+actual);
	    assertEquals(expect, actual);

	    actual = client10.getMessage();
	    System.out.println("actual: "+actual);
	    assertEquals(expect, actual);
	}

	@Test
	public void testInvalidCom2(){

	    String[] testInvalidCom2 = {"HL hello from client 11", "QUIT"}; //HL -- test invalid command
		client11 = new Client(9000, "localhost", testInvalidCom2, "client11");
		client11.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    client11.frame.setVisible(true);

	    ExecutorService executor = Executors.newCachedThreadPool();
	    executor.execute(client11);
	    try {
		      executor.awaitTermination(3, TimeUnit.SECONDS);
		    } catch(InterruptedException e){
		      e.printStackTrace();
		    }

		expect = "BAD command not recognised";
	    actual = client11.getMessage();
	    System.out.println("actual: "+actual);
	    assertEquals(expect, actual);
	}


}

