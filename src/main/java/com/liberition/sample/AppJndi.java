package com.liberition.sample;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class AppJndi {
    public static void main(String[] args) throws JMSException, NamingException {
        InitialContext ic = new InitialContext();

        ConnectionFactory cf = (ConnectionFactory) ic.lookup("ConnectionFactory");

        Queue orderQueue = (Queue) ic.lookup("queues/myQueue");

        Connection connection = cf.createConnection("user", "pass");

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        MessageProducer producer = session.createProducer(orderQueue);

        MessageConsumer consumer = session.createConsumer(orderQueue);

        connection.start();

        TextMessage message = session.createTextMessage("This is a test message");
        producer.send(message);

        TextMessage receivedMessage = (TextMessage) consumer.receive();
        System.out.println("Got message: " + receivedMessage.getText());
    }
}
