package demo;

/**
 * Created by huishen on 18/1/16.
 *
 * 改进EnumTest02
 * EnumTest02不能共享代码
 */

public enum EnumTest03 {

    MONDAY {
        @Override
        public String print() {
            return "workday";
        }
    },
    THUSDAY {
        @Override
        public String print() {
            return "workday";
        }
    },
    SATURDAY {
        @Override
        public String print() {
            return "weekend";
        }
    },
    SUNDAY {
        @Override
        public String print() {
            return "weekend";
        }
    }
    ;

    abstract public String print();

}
