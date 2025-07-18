import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class Supermarket {
    public static void main(String[] args) throws InterruptedException {
        int cashiersCount = 3;
        int minQueueLength = 3, maxQueueLength = 10;
        int minServiceTime = 2000, maxServiceTime = 5000;
        int minCustomerInterval = 1000, maxCustomerInterval = 3000;
        int warehouseCapacity = 1000;

        System.out.println("Параметры:");
        System.out.println("Касс: " + cashiersCount);
        System.out.println("Длина очереди: " + minQueueLength + "-" + maxQueueLength);
        System.out.println("Время обслуживания: " + minServiceTime + "-" + maxServiceTime + " мс");
        System.out.println("Интервал покупателей: " + minCustomerInterval + "-" + maxCustomerInterval + " мс");
        System.out.println("Емкость склада: " + warehouseCapacity);

        Warehouse warehouse = new Warehouse(warehouseCapacity);
        CustomerFactory customerFactory = new CustomerFactory();
        QueueFactory queueFactory = new QueueFactory();

        List<Queue<Customer>> queues = IntStream.range(0, cashiersCount)
                .mapToObj(_ -> queueFactory.createQueue())
                .toList();

        List<Cashier> cashiers = IntStream.range(0, cashiersCount)
                .mapToObj(i -> new Cashier(i + 1, queues.get(i), warehouse, minServiceTime, maxServiceTime))
                .toList();

        cashiers.forEach(Thread::start);

        Supplier supplier = new Supplier(warehouse, 50, 200, 5000);
        supplier.start();

        Random random = new Random();
        while (true) {
            Customer customer = customerFactory.createCustomer();
            // Выбираем самую короткую очередь
            Queue<Customer> queue = queues.stream().min(Comparator.comparingInt(Queue::size)).get();
            synchronized (queue) {
                if (queue.size() < maxQueueLength) {
                    queue.add(customer);
                    System.out.println("Покупатель " + customer.getId() + " добавлен в очередь (размер: " + queue.size() + ")");
                } else {
                    System.out.println("Покупатель " + customer.getId() + " ушел — все очереди заполнены");
                }
            }
            int interval = minCustomerInterval + random.nextInt(maxCustomerInterval - minCustomerInterval + 1);
            Thread.sleep(interval);
        }
    }
}