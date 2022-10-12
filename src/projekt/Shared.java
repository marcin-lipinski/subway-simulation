package projekt;

import java.util.concurrent.Semaphore;

public class Shared{
    public Semaphore S1 = new Semaphore(0);
    public Semaphore S2 = new Semaphore(1);
    public Semaphore S3 = new Semaphore(1);
    public Semaphore S4 = new Semaphore(1);
    public Semaphore S5 = new Semaphore(1);

    public Shared(Controller controller) throws InterruptedException {
        if(controller.numberOfCarts>4)
        {
            S3.acquire();
            S2.acquire();
            S4.acquire();
        }
    }
}
