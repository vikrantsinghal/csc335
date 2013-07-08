package Test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

import Characters.Warrior;
import Items.Inanimate;
import Model.GetRequest;
import Model.GiveRequest;
import View.Client;
import View.ClientThreadConnection;
import View.Server;

public class ClientTest {

	public static final String HOST_NAME = "localhost";
	public static final int PORT_NUMBER = 4009;
	private ObjectOutputStream outputToLiasonLoop;
	private ObjectInputStream inputFromLiasonLoop;
	private Server server;

	@Test
	public void test() {
		Server s = new Server(PORT_NUMBER);
		Client c = new Client();
		try {
			ClientThreadConnection ctc = new ClientThreadConnection(new Socket(
					HOST_NAME, PORT_NUMBER), s);
		} catch (UnknownHostException e) {
			// nope
		} catch (IOException e) {
			// nope
		}
		String result = s.getItem("Josh", "rock");
		assertEquals(result, "Josh added Rock");
		result = s.dropItem("Josh", "rock");
		assertEquals(result, "Josh dropped Rock");
		s.getItem("Josh", "rock");
		result = s.useItem("Josh", "rock");
		assertEquals(result, "Well that was useless...");
		result = s.updateUserStats("Josh");
		assertEquals(result,
				"Name: Josh\n\nLevel: 0\n\nExp: 0\n\nHealth: 100\n\nGold: 10");
		s.addAccount("a", "b");
		s.addAccount("c", "d");
		result = s.tell("a", "b", "hello");
		assertEquals(result, "\nCould not send message to b");
	}

	@Test
	public void getAndGiveRequestTest() {
		GetRequest gr = new GetRequest(new Warrior("Josh"), new Inanimate(
				"rock", 0), new Warrior("Kyle"));
		assertEquals("Josh", gr.getReceiver().getName());
		assertEquals("Kyle", gr.getSender().getName());
		assertEquals("rock", gr.getItem().getItemName());
		assertEquals(gr.getDescription(), "Josh wants rock from you.");
		GiveRequest GR = new GiveRequest(new Warrior("Josh"), new Inanimate(
				"rock", 0), new Warrior("Kyle"));
		assertEquals("Josh", GR.getSender().getName());
		assertEquals("Kyle", GR.getReceiver().getName());
		assertEquals("rock", GR.getItem().getItemName());
		assertEquals(GR.getDescription(), "Josh wants to give you rock");
	}

}
