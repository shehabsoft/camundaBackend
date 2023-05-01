package org.example.delegates;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SentMessageDelegate implements JavaDelegate {
    private static final Logger LOGGER = LoggerFactory.getLogger(SentMessageDelegate.class);
    private final ProcessEngine processEngine;

    public SentMessageDelegate(ProcessEngine processEngine) {
        this.processEngine = processEngine;
    }
    @Override
    public void execute(DelegateExecution execution) throws Exception {

        String businessKey = execution.getBusinessKey();

        var message_correlation = (String) execution.getVariable("message_correlation");

        Map<String,Object> varaibleMap=new HashMap<>();
        varaibleMap.put("APP_NUMBER",businessKey);
        processEngine.getRuntimeService()
                .correlateMessage(message_correlation, businessKey,varaibleMap);



        LOGGER.info("Forward Application  with Id  {} Step To {} ",businessKey,message_correlation);
    }
}
