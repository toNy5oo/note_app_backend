package dev.bozzaciro.noteapp.controller;
import dev.bozzaciro.noteapp.model.NoteDTO;
import dev.bozzaciro.noteapp.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@CrossOrigin
@RestController
public class NotesController {

    @Autowired
    NotesRepository notesRepository;

    @GetMapping("/notes")
    public ResponseEntity<?> getNotes(){
        List<NoteDTO> notes = notesRepository.findAll();

        if (!notes.isEmpty()){
               return ResponseEntity.ok(notes);
        }
        else return new ResponseEntity<>("No Notes available", HttpStatus.NOT_FOUND);

    }

    @GetMapping("/notes/{id}")
    public ResponseEntity<?> getNote(@PathVariable("id") String id){
        Optional<NoteDTO> note = notesRepository.findById(id);
        if (note.isPresent()){
            return ResponseEntity.ok(note.get());
        }
        else return ResponseEntity.badRequest().body("Note not found");

    }

    @PostMapping("/note/create")
    public ResponseEntity<?> createNote(@RequestBody NoteDTO note){
        try{
            note.setCreatedAt(new Date());
            note.setUpdatedAt(new Date());
            notesRepository.save(note);
            return ResponseEntity.ok(note);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/note/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable("id") String id){
        Optional<NoteDTO> noteOptional = notesRepository.findById(id);
        if (noteOptional.isPresent()) {
            NoteDTO note = noteOptional.get(); // Extract the note from Optional
            try {
                notesRepository.deleteById(id);
                return ResponseEntity.ok(note);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note not found");

    }

    @PutMapping("/note/update")
    public ResponseEntity<?> updateNote(@RequestBody NoteDTO note){
        try{
            note.setUpdatedAt(new Date());
            notesRepository.save(note);
            return ResponseEntity.ok(note);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/note/togglePin/{id}")
    public ResponseEntity<?> togglePin(@PathVariable("id") String id){
        Optional<NoteDTO> optionalNote = notesRepository.findById(id);
        if (optionalNote.isPresent()) {
            NoteDTO note = optionalNote.get();
            try {
                note.setPinned(!note.isPinned());
                NoteDTO toggledNote = notesRepository.save(note);
                return ResponseEntity.ok(toggledNote);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note not found");
    }

}
