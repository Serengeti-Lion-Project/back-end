package SerengetiLionProject.demo.repository;

import SerengetiLionProject.demo.domain.MeetNote;

import java.util.List;

public interface MeetNoteRepository {
    MeetNote save(MeetNote meetNote);
    MeetNote findByTeam_idAndNote_id(Long team_id,Long note_id);
    List<MeetNote> findAllByTeam_id(Long team_id);

    //List<MeetNote> findAll(); 안쓸거 같아서 일단 주석 


}
