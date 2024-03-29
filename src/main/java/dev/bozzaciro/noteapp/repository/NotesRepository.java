package dev.bozzaciro.noteapp.repository;

import dev.bozzaciro.noteapp.model.NoteDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotesRepository extends MongoRepository<NoteDTO, String> {


}
