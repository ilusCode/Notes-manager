package mx.iluscode.deki.notes.manager.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.iluscode.deki.notes.manager.model.CreateNoteResponse;
import mx.iluscode.deki.notes.manager.model.NotificationWrapper;
import mx.iluscode.deki.notes.manager.services.IFilesService;
import mx.iluscode.deki.notes.manager.services.INotesService;
import mx.iluscode.deki.notes.manager.transform.INotificationWrapperTransform;
import mx.iluscode.deki.notes.manager.utils.DekiConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static java.nio.file.Files.deleteIfExists;
import static mx.iluscode.deki.notes.manager.utils.ErrorCodes.DEKI_ERROR_002;

/***************************************
 *  @description
 *
 *  @project deki
 *  @author Team GFT Dev BaaS
 *  @created 09/12/21 - 23:39
 *
 ***************************************/
@RestController
@RequestMapping("/notes")
@Slf4j
@RequiredArgsConstructor
public class NotasController implements INotasController {

    /**
     * Se instancia la interfaz {@link INotesService}
     */
    private final INotesService notesService;

    /**
     * Se instancia la interfaz {@link IFilesService}
     */
    private final IFilesService filesService;

    /**
     * Se instancia la interfaz {@link INotificationWrapperTransform}
     */
    private final INotificationWrapperTransform wrapperTransform;

    @Override
    @PostMapping("/create")
    public Mono<ResponseEntity<CreateNoteResponse>> createNotes(@RequestParam(value = "notesForSheet", required = true) int notesForSheet, @RequestParam(value = "totalNotes", required = true) int totalNotes, @RequestPart(value = "image", required = true) MultipartFile image, @RequestParam(value = "noteInit", required = true) int noteInit, @RequestPart(value = "template", required = true) MultipartFile template) {
        log.info(DekiConstants.SEPARADOR);
        log.info("SE INICIA PROCESO DE CREACION DE NOTAS");
        log.info(DekiConstants.SEPARADOR);
        Mono<ResponseEntity<CreateNoteResponse>> responseEntityMono;
        if (notesForSheet > 0 && notesForSheet <= 4) {
            File imageFile = filesService.loadFileTemp(image, "image").toFile();
            File templateFile = filesService.loadFileTemp(template, "template").toFile();
            return notesService.createNotes(imageFile, templateFile, notesForSheet, totalNotes, noteInit)
                    .map(body -> new ResponseEntity<>(body, body.getNotificationWrapper().getHttpStatus()))
                    .publishOn(Schedulers.boundedElastic())
                    .map(response -> {
                        try {
                            String img = imageFile.getParent();
                            String temp = templateFile.getParent();
                            deleteIfExists(imageFile.toPath());
                            deleteIfExists(templateFile.toPath());
                            deleteIfExists(Paths.get(img));
                            deleteIfExists(Paths.get(temp));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return response;
                    });
        } else {
            CreateNoteResponse data = new CreateNoteResponse();
            NotificationWrapper nw = wrapperTransform.generateNotificationWrapper(DEKI_ERROR_002.getCode(), DEKI_ERROR_002.getMessage(), DEKI_ERROR_002.getHttpStatus());
            data.setNotificationWrapper(nw);
            responseEntityMono = Mono.just(new ResponseEntity<>(data, data.getNotificationWrapper().getHttpStatus()));
        }
        return responseEntityMono.publishOn(Schedulers.boundedElastic()).doFinally(signalType -> {
            log.info(DekiConstants.SEPARADOR);
            log.info("SE FINALIZA PROCESO DE CREACION DE NOTAS");
            log.info(DekiConstants.SEPARADOR);
        });
    }
}
