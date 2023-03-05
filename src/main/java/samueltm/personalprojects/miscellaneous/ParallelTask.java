package samueltm.personalprojects.miscellaneous;

import java.util.List;
import java.util.concurrent.RecursiveAction;

public class ParallelTask extends RecursiveAction {

    private static final int THRESHOLD = 10; // threshold to determine if subtask should be split

    private final List<Runnable> tasks; // list of tasks to execute

    public ParallelTask(List<Runnable> tasks) {
        this.tasks = tasks;
    }

    @Override
    protected void compute() {
        if (tasks.size() <= THRESHOLD) {
            // execute each task sequentially if the list size is below the threshold
            for (Runnable task : tasks) {
                task.run();
            }
        } else {
            // split the task list into two sublists and create two new ParallelTask instances to execute them
            int mid = tasks.size() / 2;
            List<Runnable> leftTasks = tasks.subList(0, mid);
            List<Runnable> rightTasks = tasks.subList(mid, tasks.size());
            ParallelTask leftTask = new ParallelTask(leftTasks);
            ParallelTask rightTask = new ParallelTask(rightTasks);

            // invoke the two subtasks in parallel using the ForkJoinPool
            invokeAll(leftTask, rightTask);
        }
    }
}
