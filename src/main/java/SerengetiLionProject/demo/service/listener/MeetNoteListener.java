package SerengetiLionProject.demo.service.listener;

import SerengetiLionProject.demo.domain.MeetNote;
import SerengetiLionProject.demo.service.generator.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MeetNoteListener extends AbstractMongoEventListener<MeetNote> {
    private final SequenceGeneratorService generatorService;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<MeetNote> event) {
        event.getSource().setNote_id(generatorService.generateSequence(MeetNote.SEQUENCE_NAME));
    }
}
