package threadpool;

public interface Task {
  default String getName() {
    return null;
  };
  void performWork();
  //todo: what? - CountDownLatch;
}
