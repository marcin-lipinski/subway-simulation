package projekt;

public class YellowTrain extends Train{
    public YellowTrain(Shared shared, int numberOfCarts, Controller controller, int[] route)
    {
        super(shared, numberOfCarts, controller, route);
        this.tail = 1;
        this.direction = 0;
        this.head = tail + (numberOfCarts - 1);
        this.number = 0;
    }

    protected void moveForward() {
        if (route[head]==19){
            try {shared.S3.acquire();}catch (InterruptedException e) {e.printStackTrace();}
            try {shared.S2.acquire();}catch (InterruptedException e) {e.printStackTrace();}
        }
        if (route[tail]==25){
            shared.S1.release();
            if(numberOfCarts < 5)shared.S2.release();
        }
        if(route[tail]==49) shared.S3.release();
        head++;
        tail++;
    }

    protected void moveBack(){
        if(route[head]==50){
            try {shared.S2.acquire();}catch (InterruptedException e) {e.printStackTrace();}
            try {shared.S3.acquire();}catch (InterruptedException e) {e.printStackTrace();}
        }
        if (route[head] == 31)try {shared.S1.acquire();}catch (InterruptedException e) {e.printStackTrace();}
        if(route[tail]==25){
            shared.S2.release();
            shared.S3.release();
        }
        head--;
        tail--;
    }

    protected void updateVariable(){
        if ((direction == 1 && route[head] == 1) || (direction==0 && route[head]==53)){
            direction = (direction + 1) % 2;
            sleepTime(controller.timeOfYellowDropping);
            temp = head;
            head = tail;
            tail = temp;
        }
    }

    protected void ownAction(){
        sleepTime(controller.speedOfYellowTrain);
    }
}