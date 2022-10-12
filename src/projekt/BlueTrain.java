package projekt;

public class BlueTrain extends Train{
    public BlueTrain(Shared shared, int numberOfCarts, Controller controller, int[] route)
    {
        super(shared, numberOfCarts, controller, route);
        this.tail = 25;
        this.direction = 1;
        this.head = tail - (numberOfCarts - 1);
        this.number = 2;
    }

    protected void moveForward() {
        if(route[head]==19)try {shared.S4.acquire();} catch (InterruptedException e) {e.printStackTrace();}
        if(route[tail]==25){
            shared.S4.release();
            shared.S1.release();
        }
        head++;
        tail++;
    }

    protected void moveBack(){
        if(route[head]==26){
            try {shared.S1.acquire(); } catch (InterruptedException e) {e.printStackTrace();}
            try {shared.S4.acquire();} catch (InterruptedException e) { e.printStackTrace();}
        }
        if(route[tail]==25)shared.S4.release();
        head--;
        tail--;
    }

    protected void updateVariable(){
        if ((direction == 1 && route[head] ==1) || (direction==0 && route[head]==44)){
            direction = (direction + 1) % 2;
            sleepTime(controller.timeOfBlueDropping);
            temp = head;
            head = tail;
            tail = temp;
        }
    }

    protected void ownAction(){
        sleepTime(controller.speedOfBlueTrain);
    }
}