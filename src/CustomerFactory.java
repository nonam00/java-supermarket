import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomerFactory {
    private final AtomicInteger idGenerator = new AtomicInteger(1);
    private final Random random = new Random();

    public Customer createCustomer() {
        int items = 1 + random.nextInt(20); // 1-20 товаров
        return new Customer(idGenerator.getAndIncrement(), items);
    }
}