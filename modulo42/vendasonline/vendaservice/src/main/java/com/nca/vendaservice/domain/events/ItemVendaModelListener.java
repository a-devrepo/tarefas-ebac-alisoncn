package com.nca.vendaservice.domain.events;

import com.nca.vendaservice.domain.model.ItemVenda;
import com.nca.vendaservice.domain.services.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class ItemVendaModelListener extends AbstractMongoEventListener<ItemVenda> {

    private SequenceGeneratorService sequenceGenerator;

    @Autowired
    public ItemVendaModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<ItemVenda> event) {
        if (Integer.parseInt(event.getSource().getId()) < Integer.parseInt("1")) {
            event.getSource().setId(String.valueOf(sequenceGenerator.generateSequence(ItemVenda.SEQUENCE_NAME)));
        }
    }
}
