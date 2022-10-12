package Projekt;

public class BlueTrain extends Train{
    public BlueTrain(Shared shared, int numberOfCarts, Controller controller, int[] route)
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
                if(route[head]==19)try {shared.S4.acquire();} catch (InterruptedException e) {e.printStackTrace();}
                if(route[tail]==25)
                {
                    shared.S4.release();
                    shared.S1.release();
                }
                head++;
                tail++;
            }

            if(direction==1)
            {
                if(route[head]==26)
                {
                    try {shared.S1.acquire(); } catch (InterruptedException e) {e.printStackTrace();}
                    try {shared.S4.acquire();} catch (InterruptedException e) { e.printStackTrace();}
                }
                if(route[tail]==25)shared.S4.release();
                head--;
                tail--;
            }
            try { shared.S5.acquire();} catch (InterruptedException e) {e.printStackTrace();}
            controller.moveTrain(head, direction, 2);
            shared.S5.release();
            try{Thread.sleep(controller.speedOfBlueTrain);}catch (InterruptedException e){e.printStackTrace();}
            if ((direction == 1 && route[head] ==1) || (direction==0 && route[head]==44))
            {
                direction = (direction + 1) % 2;
                try{Thread.sleep(controller.timeOfBlueDropping);}catch (InterruptedException e){e.printStackTrace();}
                temp = head;
                head = tail;
                tail = temp;
            }
        }
    }
}
