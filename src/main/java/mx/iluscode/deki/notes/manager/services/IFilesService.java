package mx.iluscode.deki.notes.manager.services;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

/***************************************
 *  @description
 *
 *  @project deki
 *  @author Team GFT Dev BaaS
 *  @created 10/12/21 - 0:02
 *
 ***************************************/
public interface IFilesService {

    /**
     * Este metodó se encarga de preparar el ambiente de trabajo
     *
     * @param file   representa el archivo de trabajo
     * @param prefix representa el prefijo para asignar el nombre del espacio de trabajo
     * @return un {@link Path} con la ubicacion de donde se crean los archivos temporaless
     */
    Path loadFileTemp(MultipartFile file, String prefix);
}
