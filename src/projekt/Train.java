package projekt;

public abstract class Train extends Thread{
    protected Controller controller;
    protected Shared shared;
    protected int[] route;
    protected int tail;
    protected int head;
    protected int temp;
    protected int direction;
    protected int numberOfCarts;
    protected int number;

    protected Train(Shared shared, int numberOfCarts, Controller controller, int[] route)
    {
        this.shared = shared;
        this.numberOfCarts = numberOfCarts;
        this.controller = controller;
        this.route=route;
    }

    @Override
    public void run(){
        while(true){
            switch (direction){
                case 0:
                    moveForward();
                    break;
                case 1:
                    moveBack();
                    break;
                default:
                    break;
            }
            mutualSemaphoreAndMove();
            ownAction();
            updateVariable();
        }
    }

    abstract void moveForward();
    abstract void moveBack();
    abstract void updateVariable();
    abstract void ownAction();
    protected void mutualSemaphoreAndMove(){
        try { shared.S5.acquire();}
        catch (InterruptedException e) {e.printStackTrace();}

        controller.moveTrain(head, direction, number);
        shared.S5.release();
    }
    protected void sleepTime(int time){
        try{Thread.sleep(time);}
        catch (InterruptedException e){e.printStackTrace();}
    }
}
