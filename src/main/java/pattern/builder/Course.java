package pattern.builder;

/**
 * builder 建造者设计模式
 */
public class Course {

    private String courseName;
    private String coursePPT;

    public static CourseBuilder builder() {
        return new CourseBuilder();
    }

    public Course(CourseBuilder builder) {
        this.courseName = builder.courseName;
        this.coursePPT = builder.coursePPT;
    }

    public static class CourseBuilder {
        private String courseName;
        private String coursePPT;

        public CourseBuilder courseName(String courseName) {
            this.courseName = courseName;
            return this;
        }

        public CourseBuilder coursePPT(String coursePPT) {
            this.coursePPT = coursePPT;
            return this;
        }

        public Course build() {
            return new Course(this);
        }

    }

    public static void  main(String[] args) {
        Course course = Course.builder().courseName("courseName1").coursePPT("coursePPT1").build();
    }
}
