import java.util.ArrayList;

public class renamedProc {
    String oldname;
    String newname;
    Integer scopeL;
    ArrayList<renamedProc> siblingsparentsandparentsiblings;

    public renamedProc(String oldname, String newname, Integer scopeL) {
        this.oldname = oldname;
        this.newname = newname;
        this.scopeL = scopeL;
        this.siblingsparentsandparentsiblings = new ArrayList<>();
    }
}
