package transToClass;

public class u2 {

    short u2;

    public void set(short u2) {
        if(u2 < 0)
            throw new UnsignedError();
        this.u2 = u2;
    }

    public short get() {
        return u2;
    }
}
