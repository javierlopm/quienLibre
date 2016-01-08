package net.ddns.javierlopm.quienlibre;

/**
 * Created by javierlopm on 05/01/16.
 */
public enum Edificio {
    AUL("Aulas"),
    MYS("Matemáticas y Sistemas"),
    ELE("Eléctrica"),
    FE1("Física 1"),
    FE2("Física 2"),
    EGE("Estudios Generales"),
    ENE("Energética"),
    MEM("Mecánica de Materiales"),
    MEU("Mecánica y Estudios Urbanos");

    private final String fullname;

    Edificio(String fullname){
        this.fullname = fullname;
    }
}
