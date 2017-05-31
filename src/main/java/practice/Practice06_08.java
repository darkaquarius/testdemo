package practice;

/**
 * Created by huishen on 17/2/4.
 */
public class Practice06_08 {

    public static void main(String args[]){
        ClassD d1 = new ClassD();
        ClassD d2 = new ClassD(10);
    }
}

class ClassA{
    public ClassA(){
        System.out.println("ClassA()");
    }
}

class ClassB{
    public ClassB(){
        System.out.println("ClassB()");
    }
}

class ClassC extends ClassA{
    public ClassC(){
        System.out.println("CLassC()");
    }
}

class ClassD extends ClassB{
    private ClassA classA = new ClassA();
    private ClassC classC;

    public ClassD(){
        System.out.println("ClassD()");
    }

    public ClassD(int i){
        classC = new ClassC();
        System.out.println("ClassD(int)");
    }
}
