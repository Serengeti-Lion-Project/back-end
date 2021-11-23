package SerengetiLionProject.demo.service.listener;

import SerengetiLionProject.demo.domain.FinalSchedule;
import SerengetiLionProject.demo.service.generator.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component // uid - autoincrement 를 위해 필요한 클래스
public class FinalScheduleModelListener extends AbstractMongoEventListener<FinalSchedule> {

    private final SequenceGeneratorService generatorService;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<FinalSchedule> event) {
        event.getSource().setDate_id(generatorService.generateSequence(FinalSchedule.SEQUENCE_NAME));
    }
}