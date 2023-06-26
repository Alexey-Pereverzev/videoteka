package ru.gb.cabinetorderservice.configs;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gb.common.constants.Constant;

@Configuration
public class MailConfigForRabbitMQ implements Constant {

//    @Autowired(required=true)
//    private Environment environment;

    // Создать очередь
    @Bean
    public Queue mailQueue(){
        return new Queue(MAIL_QUEUE_NAME);
    }

    // Создать переключатель
    @Bean
    public Exchange mailExchange(){
        // долговечно: будь то постоянный (после закрытия rabritmq, в следующий раз, когда выключатель существует)
        return new DirectExchange(MAIL_EXCHANGE_NAME, true, false);
    }

    // Свяжите переключатели и очереди
    @Bean
    public Binding mailBinding(Queue mailQueue, Exchange mailExchange){
        return BindingBuilder
                .bind(mailQueue)
                .to(mailExchange)
                .with(MAIL_ROUTE_KEY)
                .noargs();
    }
}

