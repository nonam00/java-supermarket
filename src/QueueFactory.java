import java.util.LinkedList;
import java.util.Queue;

public class QueueFactory {
    public Queue<Customer> createQueue() {
        return new LinkedList<>();
    }
}