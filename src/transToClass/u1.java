package transToClass;

public class u1 {
    private byte u1;

    public u1() {

    }

    public u1(byte u1) {
        this.u1 = u1;
    }

    public void set(byte u1) {
        if(u1 < 0)
            throw new UnsignedError();
        this.u1 = u1;
    }

    public byte get() {
        return u1;
    }
}
