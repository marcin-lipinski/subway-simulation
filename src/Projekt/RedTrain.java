package Projekt;

public class RedTrain extends Train{
    public RedTrain(Shared shared, int numberOfCarts, Controller controller, int[] route)
    {
        super(shared, numberOfCarts, controller, route);
        this.tail = 25;
        this.direction = 1;
        this.head = tail - (numberOfCarts - 1);
    }
    public void run()
    {
        while(true)
        {
            if(direction==0)
            {
                if(route[head]==24)
                {
                    try {shared.S3.acquire();}catch (InterruptedException e) {e.printStackTrace();}
                    try {shared.S2.acquire();} catch (InterruptedException e) {e.printStackTrace();}
                    try {shared.S4.acquire();} catch (InterruptedException e) {e.printStackTrace();}
                }
                if(route[tail]==25 && numberOfCarts < 5)
                {
                    shared.S2.release();
                    shared.S4.release();
                }
                if(route[tail]==49) shared.S3.release();
                head++;
                tail++;
            }
            if(direction==1)
            {
                if(route[head]==48)
                {
                    try {shared.S2.acquire();} catch (InterruptedException e) {e.printStackTrace();}
                    try {shared.S3.acquire();} catch (InterruptedException e) {e.printStackTrace();}
                    if(numberOfCarts>4)try {shared.S4.acquire();} catch (InterruptedException e) {e.printStackTrace();}
                }
                if(route[head]==31 && numberOfCarts<5) try {shared.S4.acquire();} catch (InterruptedException e) {e.printStackTrace();}
                if(route[tail]==25)
                {
                    shared.S2.release();
                    shared.S3.release();
                    shared.S4.release();
                }
                head--;
                tail--;
            }
            try { shared.S5.acquire();} catch (InterruptedException e) {e.printStackTrace();}
            controller.moveTrain(head, direction, 1);
            shared.S5.release();
            try{Thread.sleep(controller.speedOfRedTrain);}catch (InterruptedException e){e.printStackTrace();}
            if ((direction == 1 && route[head] ==6) || (direction==0 && route[head]==45))
            {
                direction = (direction + 1) % 2;
                try{Thread.sleep(controller.timeOfRedDropping);}catch (InterruptedException e){e.printStackTrace();}
                temp = head;
                head = tail;
                tail = temp;
            }
        }
    }
}
