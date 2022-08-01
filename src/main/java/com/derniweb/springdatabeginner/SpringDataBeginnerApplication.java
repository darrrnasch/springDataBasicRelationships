package com.derniweb.springdatabeginner;

import com.derniweb.springdatabeginner.entity.Treadmill;
import com.derniweb.springdatabeginner.repository.TreadmillRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;
import java.util.Optional;

@SpringBootApplication
public class SpringDataBeginnerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataBeginnerApplication.class, args);
    }

    @Component
    public class Runner implements CommandLineRunner {
        private final TreadmillRepository repository;

        public Runner(TreadmillRepository repository) {
            this.repository = repository;
        }

        @Override
        public void run(String... args) {
            System.out.println("Before save:");
            doWeHaveSomethingInDb();

            System.out.println("Saving...");
            addDataToDb();

            System.out.println("After save:");
            doWeHaveSomethingInDb();

            System.out.println("Reading from db");
            readFromDb();

            System.out.println("Updating db");
            updateDb();

            System.out.println("Delete from db");
            deleteFromDb();
        }

        private void doWeHaveSomethingInDb() {
            long count = repository.count();
            if (count > 0) {
                System.out.printf("Db has %d treadmills\n", count);
            } else {
                System.out.println("Db is empty");
            }
        }

        private void addDataToDb() {
            repository.save(new Treadmill("aaa", "Yamaguchi runway"));
            repository.save(new Treadmill("bbb", "Yamaguchi runway pro-x"));
            repository.save(new Treadmill("ccc", "Yamaguchi max"));
        }

        private String createTreadmillView(Treadmill treadmill) {
            return String.format("Treadmill(code: %s, model: %s)", treadmill.getCode(), treadmill.getModel());
        }

        private void readFromDb() {
            System.out.println("Looking for the treadmill with code='bbb'... ");
            Optional<Treadmill> treadmill = repository.findById("bbb");
            /*
                First time seeing this kind of optional.
             */
            String result = treadmill.map(this::createTreadmillView).orElse("Not found");
            System.out.println(result);

            /*
                what if we know it doesn't exist
             */
            System.out.println("Looking for the treadmill with code='not-existed-code'... ");
            Optional<Treadmill> treadmill2 = repository.findById("not-existed-code");
            String result2 = treadmill2.map(this::createTreadmillView).orElse("Not found");
            System.out.println(result2);

            /*
                read all from db
                for millions of object this method could exhaust our memory. So be careful with it.
             */
            System.out.println("printing all treadmills from db");
            Iterable<Treadmill> treadmills = repository.findAll();
            for (Treadmill t : treadmills) {
                System.out.println(createTreadmillView(t));
            }
        }

        private void updateDb() {
            Optional<Treadmill> existedTreadmill = repository.findById("aaa");

            String existed = existedTreadmill
                    .map(this::createTreadmillView)
                    .orElse("Not found");

            System.out.println("Before update: " + existed);
            System.out.println("Updating...");


            /*
                !!!!!! if the database has an entity with the same id, save methods act as update methods.
             */
            existedTreadmill.ifPresent(treadmill -> {
                treadmill.setModel("Yamaguchi runway-x");
                repository.save(treadmill);
            });

            Optional<Treadmill> updatedTreadmill = repository.findById("aaa");
            String updated = updatedTreadmill
                    .map(this::createTreadmillView)
                    .orElse("Not found");

            System.out.println("After update: " + updated);
        }

        private void printAllTreadmills() {
            Iterable<Treadmill> treadmills = repository.findAll();
            for (Treadmill treadmill : treadmills) {
                System.out.println(createTreadmillView(treadmill));
            }
        }

        private void deleteFromDb() {
            System.out.println("Before delete: ");
            printAllTreadmills();

            System.out.println("Deleting...");
            repository.deleteById("ccc");

            System.out.println("After delete: ");
            printAllTreadmills();

            /*
                another logic of deleting follows
             */
            System.out.println("Before delete: ");
            printAllTreadmills();

            System.out.println("Deleting...");
            Optional<Treadmill> proXTreadmill = repository.findById("bbb");
            proXTreadmill.ifPresent(
                    treadmill -> {
                        repository.delete(treadmill);
                    }
            );

            System.out.println("After delete: ");
            printAllTreadmills();
        }
    }
}
