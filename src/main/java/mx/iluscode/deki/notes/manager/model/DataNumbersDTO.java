package mx.iluscode.deki.notes.manager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/***************************************
 *  @description
 *
 *  @project deki
 *  @author Team GFT Dev BaaS
 *  @created 10/12/21 - 17:55
 *
 ***************************************/
@Setter
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class DataNumbersDTO implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4595524876938579478L;

    /**
     * Segmento p1
     */
    private String p1;

    /**
     * Segmento p2
     */
    private String p2;

    /**
     * Segmento p3
     */
    private String p3;

    /**
     * Segmento p4
     */
    private String p4;

    /**
     * Segmento p5
     */
    private String p5;

    /**
     * Segmento p6
     */
    private String p6;

    /**
     * constructo de un parámetro
     *
     * @param p1 segmento p1
     */
    public DataNumbersDTO(String p1) {
        this.p1 = p1;
    }

    /**
     * constructo de dos parámetros
     *
     * @param p1 segmento p1
     * @param p2 segmento p2
     */
    public DataNumbersDTO(String p1, String p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    /**
     * constructo de tres parámetros
     *
     * @param p1 segmento p1
     * @param p2 segmento p2
     * @param p3 segmento p3
     */
    public DataNumbersDTO(String p1, String p2, String p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    /**
     * constructo de cuatro parámetros
     *
     * @param p1 segmento p1
     * @param p2 segmento p2
     * @param p3 segmento p3
     * @param p4 segmento p4
     */
    public DataNumbersDTO(String p1, String p2, String p3, String p4) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
    }

    /**
     * constructo de cinco parámetros
     *
     * @param p1 segmento p1
     * @param p2 segmento p2
     * @param p3 segmento p3
     * @param p4 segmento p4
     * @param p5 segmento p5
     */
    public DataNumbersDTO(String p1, String p2, String p3, String p4, String p5) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.p5 = p5;
    }

}
