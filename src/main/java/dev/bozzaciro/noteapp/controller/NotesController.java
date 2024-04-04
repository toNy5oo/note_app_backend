package dev.bozzaciro.noteapp.controller;

import dev.bozzaciro.noteapp.constants.Request;
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
               return new ResponseEntity<List<NoteDTO>>(notes, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No Notes available", HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/notes/{id}")
    public ResponseEntity<?> getNote(@PathVariable("id") String id){
        Optional<NoteDTO> note = notesRepository.findById(id);
        if (note.isPresent()){
            return new ResponseEntity<>(note.get(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Note not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/note/create")
    public ResponseEntity<?> createNote(@RequestBody NoteDTO note){
        try{
            note.setCreatedAt(new Date());
            notesRepository.save(note);
            return new ResponseEntity<NoteDTO>(note, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/note/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable("id") String id){
        Optional<NoteDTO> note = notesRepository.findById(id);
        if (note.isPresent()) {
            try {

                notesRepository.deleteById(id);
                return new ResponseEntity<>(Request.DELETED, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else return new ResponseEntity<>(Request.NOT_FOUND, HttpStatus.NOT_FOUND);
    }


}
