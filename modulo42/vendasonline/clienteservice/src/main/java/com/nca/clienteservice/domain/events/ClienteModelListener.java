package com.nca.clienteservice.domain.events;

import com.nca.produtoservice.domain.model.Produto;
import com.nca.produtoservice.domain.services.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class ClienteModelListener extends AbstractMongoEventListener<Produto> {

    private SequenceGeneratorService sequenceGenerator;

    @Autowired
    public ClienteModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Produto> event) {
        if (Integer.parseInt(event.getSource().getId()) < Integer.parseInt("1")) {
            event.getSource().setId(String.valueOf(sequenceGenerator.generateSequence(Produto.SEQUENCE_NAME)));
        }
    }
}
