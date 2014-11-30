import java.util.Properties;

import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.sun.messaging.Topic;
import com.sun.messaging.jmq.jmsclient.TextMessageImpl;
import com.sun.messaging.jms.Session;
import com.sun.messaging.jms.TopicConnection;

public class main {

	/**
	 * This class starts the producer and the consumer
	 * 
	 * @param args No argument is necessary
	 * @throws Exception if it fails to establish a connection with the file system jndi server or the provider
	 */
	public static void main(String[] args) throws Exception {
		startProducer();
		startConsumer();
	}
	
	/**
	 * Sends a jms message to the provider on the topic "uav topic"
	 * 
	 * @throws Exception if it fails to establish a connection with the file system jndi server or the provider
	 */
	private static void startProducer() throws Exception {
		
		Properties jndiProps = new Properties();
		jndiProps.put("java.naming.factory.initial", "com.sun.jndi.fscontext.RefFSContextFactory");
		jndiProps.put("java.naming.provider.url", "file:///C:/Temp");
		
		InitialContext ctx = null;
	
		ctx = new InitialContext(jndiProps);
		com.sun.messaging.ConnectionFactory tcf = (com.sun.messaging.ConnectionFactory) ctx.lookup("Factory");
	    Topic topic = (com.sun.messaging.Topic) ctx.lookup("uav topic");
	    TopicConnection conn = (TopicConnection) tcf.createTopicConnection();
	    Session session = (Session) conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
	    
	    MessageProducer producer = session.createProducer(topic);
	    conn.start();
	    TextMessage myTextMsg = session.createTextMessage();
	    myTextMsg.setText("Hello JMS Subscriber!");
        System.out.println("\n===Sending Message:===" + myTextMsg.toString());
        producer.send(myTextMsg);
        
        //Close the session and connection resources.
        session.close();
        conn.close();
		
	}
	
	/**
	 * Does a read till' it received a message from the provider on the topic "uav topic"
	 * 
	 * @throws Exception if it fails to establish a connection with the file system jndi server or the provider
	 */
	private static void startConsumer() throws Exception {
		Properties jndiProps = new Properties();
		jndiProps.put("java.naming.factory.initial", "com.sun.jndi.fscontext.RefFSContextFactory");
		jndiProps.put("java.naming.provider.url", "file:///C:/Temp");
		
		InitialContext ctx = null;
	
		ctx = new InitialContext(jndiProps);
		com.sun.messaging.ConnectionFactory tcf = (com.sun.messaging.ConnectionFactory) ctx.lookup("Factory");
	    Topic topic = (com.sun.messaging.Topic) ctx.lookup("uav topic");
	    TopicConnection conn = (TopicConnection) tcf.createTopicConnection();
	    Session session = (Session) conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
	    
	    MessageConsumer consumer = session.createConsumer(topic);
	    conn.start();

        Message msg = consumer.receive();

        if (msg instanceof TextMessageImpl) {
            TextMessage txtMsg = (TextMessage) msg;
            System.out.println("\n===Read Message:===" + txtMsg.toString());
        }
        
        //Close the session and connection resources.
        session.close();
        conn.close();
	    
	}

}
