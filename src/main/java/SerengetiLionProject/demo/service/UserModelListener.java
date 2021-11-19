package SerengetiLionProject.demo.service;

import SerengetiLionProject.demo.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component // uid - autoincrement 를 위해 필요한 클래스
public class UserModelListener extends AbstractMongoEventListener<User> {

    private final SequenceGeneratorService generatorService;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<User> event) {
        event.getSource().set_id(generatorService.generateSequence(User.SEQUENCE_NAME));
    }
}