package org.example.delegates;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NotifyMessageEvent implements JavaDelegate {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyMessageEvent.class);
    private final ProcessEngine processEngine;

    public NotifyMessageEvent(ProcessEngine processEngine) {
        this.processEngine = processEngine;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        LOGGER.debug("Notify process once task received.");
        String businessKey = execution.getBusinessKey();
        var correlationProcess = (String) execution.getVariable("process_correlation");
        var messageCorrelation = (String) execution.getVariable("message_correlation");

        // Get the intermediate event Gateway execution instance
        var exec = processEngine.getRuntimeService()
                .createExecutionQuery()
                .processDefinitionKey(correlationProcess)
                .messageEventSubscriptionName(messageCorrelation)
                .processInstanceBusinessKey(businessKey)
                .singleResult();

        processEngine.getRuntimeService()
                .createMessageCorrelation(messageCorrelation)
                .processInstanceId(exec.getProcessInstanceId())
                .processInstanceBusinessKey(businessKey)
                .correlate();

        LOGGER.info("Notify Step for Application Id {} To Process {} for Correlation {} ", businessKey, correlationProcess, messageCorrelation);
    }
}
