package xshape.Command;

public class Originator {
    private Object state;
  
    public void setState(Object state) {
      this.state = state;
    }
  
    public Object getState() {
      return state;
    }
  
    public Memento createMemento() {
      return new Memento(state);
    }
  
    public void restoreFromMemento(Memento memento) {
      this.state = memento.getState();
    }
  }