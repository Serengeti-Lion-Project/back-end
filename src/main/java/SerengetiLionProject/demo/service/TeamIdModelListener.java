package SerengetiLionProject.demo.service;

import SerengetiLionProject.demo.domain.FinalSchedule;
import SerengetiLionProject.demo.domain.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component // uid - autoincrement 를 위해 필요한 클래스
public class TeamIdModelListener extends AbstractMongoEventListener<Team> {

    private final NewSequenceGeneratorService generatorService;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Team> event) {
        event.getSource().set_id(generatorService.generateSequence(Team.SEQUENCE_NAME));
    }
}