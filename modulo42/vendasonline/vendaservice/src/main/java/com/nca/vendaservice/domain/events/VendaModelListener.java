package com.nca.vendaservice.domain.events;

import com.nca.vendaservice.domain.model.Venda;
import com.nca.vendaservice.domain.services.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class VendaModelListener extends AbstractMongoEventListener<Venda> {

    private SequenceGeneratorService sequenceGenerator;

    @Autowired
    public VendaModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Venda> event) {
        if (Integer.parseInt(event.getSource().getId()) < Integer.parseInt("1")) {
            event.getSource().setId(String.valueOf(sequenceGenerator.generateSequence(Venda.SEQUENCE_NAME)));
        }
    }
}
