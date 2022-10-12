package Projekt;

public class Train extends Thread{
    protected  int[] route;
    protected  Shared shared;
    protected int tail;
    protected  int head;
    protected  int temp;
    protected  int direction;
    protected  Controller controller;
    protected  int numberOfCarts;

    public Train(Shared shared, int numberOfCarts, Controller controller, int[] route)
    {
        this.shared = shared;
        this.numberOfCarts = numberOfCarts;
        this.controller = controller;
        this.route=route;
    }
}
