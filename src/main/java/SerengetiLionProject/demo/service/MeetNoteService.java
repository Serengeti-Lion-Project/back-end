package SerengetiLionProject.demo.service;

import SerengetiLionProject.demo.domain.MeetNote;

import SerengetiLionProject.demo.repository.MeetNoteRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class MeetNoteService {
    private final MeetNoteRepository noteRepository;

    public MeetNoteService(MeetNoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }


    public Long SaveNote(MeetNote meetNote){
        noteRepository.save(meetNote);
        return meetNote.getNote_id(); // 저장하면 note_id 리턴
    }

    public MeetNote findOne(Long team_id,Long note_id){
        return noteRepository.findByTeam_idAndNote_id(team_id,note_id);
    }

    public MeetNote findByNoteId(Long note_id){
        return noteRepository.findByNoteId(note_id);
    }

    public List<MeetNote> findAllByTeam_id(Long team_id) {
        return noteRepository.findAllByTeam_id(team_id);
    }
}
