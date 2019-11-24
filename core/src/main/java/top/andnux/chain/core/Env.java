package top.andnux.chain.core;

public enum Env {

    TEST, MAIN;

    public static Env getCurrent() {
        return Env.MAIN;
    }
}
