package Empresa.Model;

import Empresa.Model.domain.Empresa;

public class Session {
    private static Empresa e = null;

    public static void set(Empresa empresa) {
        e = empresa;
    }
    public static Empresa get() {
        return e;
    }
    public static void nullify() {
        e = null;
    }
}
