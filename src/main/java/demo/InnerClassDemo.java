package demo;

/**
 * @author huishen
 * @date 2019-04-11 20:02
 */
public class InnerClassDemo {

    public int outerPrint() {
        System.out.println("this is outerPrint");
        return 1;
    }

    // public static class Inner {
    //     public void innerPrint() {
    //         InnerClassDemo.this.outerPrint();
    //     }
    // }

    public Action print() {
        return new Action() {
            @Override
            public int print() {
                return InnerClassDemo.this.outerPrint();
            }
        };
    }

    public Action print2() {
        return () -> InnerClassDemo.this.outerPrint();
    }

    public static void main(String[] args) {
        InnerClassDemo innerClassDemo = new InnerClassDemo();
        Action action = innerClassDemo.print();
        System.out.println(action.print());
        System.out.println("---------");
        Action a2 = innerClassDemo.print2();
        System.out.println(a2.print());

    }

}

interface Action {
    int print();
}
