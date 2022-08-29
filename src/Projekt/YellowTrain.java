package Projekt;

public class YellowTrain extends Thread
{
    public int[] route;
    public Shared shared;
    int tail=1, head, temp, numberOfCarts;
    int direction=0;
    Controller controller;

    public YellowTrain(Shared shared, int numberOfCarts, Controller controller, int[] route)
    {
        this.shared = shared;
        this.numberOfCarts=numberOfCarts;
        this.head=tail+numberOfCarts-1;
        this.controller = controller;
        this.route=route;
    }

    public void run()
    {
        while(true)
        {
            if(direction==0)
            {
                if (route[head]==19)
                {
                    try {shared.S3.acquire();}catch (InterruptedException e) {e.printStackTrace();}
                    try {shared.S2.acquire();}catch (InterruptedException e) {e.printStackTrace();}
                }
                if (route[tail]==25)
                {
                    shared.S1.release();
                    if(numberOfCarts < 5)shared.S2.release();
                }
                if(route[tail]==49) shared.S3.release();
                head++;
                tail++;
            }
            if(direction==1)
            {
                if(route[head]==50)
                {
                    try {shared.S2.acquire();}catch (InterruptedException e) {e.printStackTrace();}
                    try {shared.S3.acquire();}catch (InterruptedException e) {e.printStackTrace();}
                }
                if (route[head] == 31)try {shared.S1.acquire();}catch (InterruptedException e) {e.printStackTrace();}
                if(route[tail]==25)
                {
                    shared.S2.release();
                    shared.S3.release();
                }
                head--;
                tail--;
            }
            try { shared.S5.acquire();} catch (InterruptedException e) {e.printStackTrace();}
            controller.moveTrain(head, direction, 0);
            shared.S5.release();

            try{Thread.sleep(controller.speedOfYellowTrain);}catch (InterruptedException e){e.printStackTrace();}
            if ((direction == 1 && route[head] == 1) || (direction==0 && route[head]==53))
            {
                direction = (direction + 1) % 2;
                try{Thread.sleep(controller.timeOfYellowDropping);}catch (InterruptedException e){e.printStackTrace();}
                temp = head;
                head = tail;
                tail = temp;
            }
        }
    }
}
