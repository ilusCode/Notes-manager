package mx.iluscode.deki.notes.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

/***************************************
 *  @description
 *
 *  @project deki
 *  @author Team GFT Dev BaaS
 *  @created 10/12/21 - 0:02
 *
 ***************************************/
@Service
@Slf4j
public class FilesService implements IFilesService {

    /**
     * Este metodó se encarga de preparar el ambiente de trabajo
     *
     * @param file   representa el archivo de trabajo
     * @param prefix representa el prefijo para asignar el nombre del espacio de trabajo
     * @return un {@link Path} con la ubicacion de donde se crean los archivos temporaless
     */
    @Override
    public Path loadFileTemp(MultipartFile file, String prefix) {
        Path tempDirectory = null;
        try {
            tempDirectory = Files.createTempDirectory("deki_".concat(prefix).concat("_"));
            log.info("Se crea el espacio -> {}", tempDirectory.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path pathComplete = null;

        /**
         * Se valida que el archivo no este vacío
         */
        if (nonNull(file) && !file.isEmpty()) {
            assert tempDirectory != null;
            String ruta = tempDirectory.toFile().getAbsolutePath();
            byte[] bytesImg;
            try {
                bytesImg = file.getBytes();
                pathComplete = Paths.get(ruta.concat("/").concat(prefix).concat("_").concat(requireNonNull(file.getOriginalFilename())));
                Files.write(pathComplete, bytesImg);
                log.info("Se crea el archivo -> {}", pathComplete.getParent());
            } catch (IOException e) {
                log.error("ERROR -> ", e);
            }
        }
        return pathComplete;
    }
}
