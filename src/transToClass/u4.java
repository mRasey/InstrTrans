package transToClass;

public class u4 {
    int u4;

    public u4() {

    }

    public u4(int u4) {
        if(u4 < 0)
            throw new UnsignedError();
        else
            this.u4 = u4;
    }

    public void set(int u4) {
        if(u4 < 0)
            throw new UnsignedError();
        else
            this.u4 = u4;
    }

    public int get() {
        return u4;
    }
}
