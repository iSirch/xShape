package xshape.Command;

public class Memento {
    private final Object state;
  
    public Memento(Object state) {
      this.state = state;
    }
  
    public Object getState() {
      return state;
    }
}
