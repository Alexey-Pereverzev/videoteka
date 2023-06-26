package ru.gb.cabinetorderservice.configs;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
//    @Bean
//    Queue queueA() {
//        return new Queue("queue.A", false);
//    }
//
//    @Bean
//    Queue queueB() {
//        return new Queue("queue.B", false);
//    }
//
//    @Bean
//    DirectExchange exchange() {
//        return new DirectExchange("exchange.direct");
//    }
//
//    @Bean
//    Binding bindingA(Queue queueA, DirectExchange exchange) {
//        return BindingBuilder.bind(queueA).to(exchange).with("event_A");
//    }
//
//    @Bean
//    Binding bindingB(Queue queueB, DirectExchange exchange) {
//        return BindingBuilder.bind(queueB).to(exchange).with("event_B");
//    }
//
//    @Bean
//    ApplicationRunner runner(ConnectionFactory cf) {
//        return args -> cf.createConnection().close();
//    }
//
//    @Bean
//    MessageConverter messageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }
//
//    @Bean
//    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(messageConverter());
//        return rabbitTemplate;
//    }

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Bean
    public RabbitTemplate rabbitTemplate(){
        // После того, как сообщение отправляется на коммутатор, это называется методом обратного вызова?
        connectionFactory.setPublisherConfirms(true);
        // сообщение Отправить с переключателя в очередь, называется ли метод обратного вызова
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());

        // Независимо от того, отправляется ли сообщение переключатель
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (ack) {
                    System.out.println("Сообщение Отправить успех");
                   // log.info(«Сообщение Отправить успех: CorrelationData ({}), ACK ({}), причина ({})», correlationData, ack, cause);
                }else{
                    System.out.println("Сообщение Отправить не успех");
                  //  log.info(«Ошибка сообщения: CorrelationData ({}), ACK ({}), причина ({})", correlationData, ack, cause);
                }
            }
        });
        // Если вы вызываете метод setreturncallback, то обязательные должны быть правдой, в противном случае Exchange не находит очередь, потеряет сообщение без запуска обратного вызова.
        rabbitTemplate.setMandatory(true);
        // Независимо от того, отправляется ли сообщение с переключателя в очередь, если отправка не удалась, он позвонит методу ReturnMessage.
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                //log.info(«Потеря сообщения: Exchange ({}), маршрут ({}), RepeCode ({}), ReplyText ({}), сообщение: {}",exchange,routingKey,replyCode,replyText,message);
            }
        });
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }
}

