package com.liberition.sample;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;

public class App {
    public static void main(String[] args) throws JMSException {

        ConnectionFactory cf = new ActiveMQConnectionFactory("(tcp://10.172.96.97:61616,tcp://10.172.96.98:61616)");

        Connection connection = cf.createConnection("user", "pass");

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue orderQueue = session.createQueue("SAP.PI.TEST.QUEUE");

        MessageProducer producer = session.createProducer(orderQueue);

        MessageConsumer consumer = session.createConsumer(orderQueue);

        connection.start();

        TextMessage message = session.createTextMessage("This is a test message");
        producer.send(message);

        TextMessage receivedMessage = (TextMessage) consumer.receive();
        System.out.println("Got message: " + receivedMessage.getText());
    }
}
