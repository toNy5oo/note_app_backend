package dev.bozzaciro.noteapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Notes")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {

    @Id
    private String id;

    private String title;

    private String description;

    private Date createdAt;
}
