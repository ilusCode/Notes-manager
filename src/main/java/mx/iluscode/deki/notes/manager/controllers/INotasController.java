package mx.iluscode.deki.notes.manager.controllers;

import mx.iluscode.deki.notes.manager.model.CreateNoteResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

/***************************************
 *  @description
 *
 *  @project deki
 *  @author Team GFT Dev BaaS
 *  @created 10/12/21 - 1:07
 *
 ***************************************/
public interface INotasController {
    @PostMapping("/create")
    Mono<ResponseEntity<CreateNoteResponse>> createNotes(@RequestParam(value = "notesForSheet", required = true) int notesForSheet,
                                                         @RequestParam(value = "totalNotes", required = true) int totalNotes,
                                                         @RequestPart(value = "image", required = true) MultipartFile image,
                                                         @RequestParam(value = "noteInit", required = true) int noteInit,
                                                         @RequestPart(value = "template", required = true) MultipartFile template);
}
