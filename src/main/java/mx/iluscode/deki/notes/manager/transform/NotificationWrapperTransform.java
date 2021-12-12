package mx.iluscode.deki.notes.manager.transform;

import mx.iluscode.deki.notes.manager.model.NotificationWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/***************************************
 *  @description
 *
 *  @project deki
 *  @author Team GFT Dev BaaS
 *  @created 11/12/21 - 20:16
 *
 ***************************************/
@Component
public class NotificationWrapperTransform implements INotificationWrapperTransform {

    /**
     * Este metodo se encarga de generar un objeto {@link NotificationWrapper}
     *
     * @param code    Representa el código que identifica la respuesta
     * @param message Representa el mensaje de la respuesta
     * @param status  Representa el código de respuesta {@link HttpStatus}
     * @return un objeto {@link NotificationWrapper}
     */
    @Override
    public NotificationWrapper generateNotificationWrapper(String code, String message, HttpStatus status) {
        NotificationWrapper notificationWrapper = new NotificationWrapper();
        notificationWrapper.setCode(code);
        notificationWrapper.setTime(LocalDateTime.now());
        notificationWrapper.setMesagge(message);
        notificationWrapper.setHttpStatus(status);
        return notificationWrapper;
    }
}
