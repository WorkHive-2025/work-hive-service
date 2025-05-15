package workhive.app.global.utils;

import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class UniqueKeyGenerator {

    private static final ThreadLocal<AtomicInteger> threadCounter =
            ThreadLocal.withInitial(() -> new AtomicInteger(0));

    public String generateUniqueKey() {
        long nanoTimeOfDay = LocalTime.now().toNanoOfDay();
        int nanoPart = (int) (nanoTimeOfDay % 1000000); // 마지막 6자리 유지

        int counter = threadCounter.get().getAndIncrement() % 10;

        return String.format("%05d", nanoPart / 10) + counter;
    }
}
