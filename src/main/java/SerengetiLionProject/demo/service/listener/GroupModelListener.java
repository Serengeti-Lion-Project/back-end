package SerengetiLionProject.demo.service.listener;

import SerengetiLionProject.demo.domain.MeetGroup;
import SerengetiLionProject.demo.domain.User;
import SerengetiLionProject.demo.service.generator.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component // uid - autoincrement 를 위해 필요한 클래스
public class GroupModelListener extends AbstractMongoEventListener<MeetGroup> {

    private final SequenceGeneratorService generatorService;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<MeetGroup> event) {
        event.getSource().setUrl_id(generatorService.generateSequence(User.SEQUENCE_NAME));
    }
}