package com.derniweb.springdatabeginner;

import com.derniweb.springdatabeginner.entity.Treadmill;
import com.derniweb.springdatabeginner.onetomany.bidirectional.PostBidirRepository;
import com.derniweb.springdatabeginner.onetomany.bidirectional.PostCommentbidir;
import com.derniweb.springdatabeginner.onetomany.bidirectional.Postbidir;
import com.derniweb.springdatabeginner.onetomany.unidirectional.Post;
import com.derniweb.springdatabeginner.onetomany.unidirectional.PostComment;
import com.derniweb.springdatabeginner.onetomany.unidirectional.PostRepository;
import com.derniweb.springdatabeginner.onetomany.unidirwithjoincol.PostCommentwjc;
import com.derniweb.springdatabeginner.onetomany.unidirwithjoincol.Postwjc;
import com.derniweb.springdatabeginner.onetomany.unidirwithjoincol.PostwjcRepository;
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
        private final PostRepository postRepository;
        private final PostwjcRepository postwjcRepository;

        private final PostBidirRepository postBidirRepository;

        public Runner(TreadmillRepository repository, PostRepository postRepository, PostwjcRepository postwjcRepository, PostBidirRepository postBidirRepository) {
            this.repository = repository;
            this.postRepository = postRepository;
            this.postwjcRepository = postwjcRepository;
            this.postBidirRepository = postBidirRepository;
        }

        @Override
        public void run(String... args) {
            /*
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
            */
            System.out.println("Starting oneToManyUniDir");
            oneToManyUniDir();

            System.out.println("Starting with join column");
            oneToManyUniDirWithJoinCol();

            System.out.println("Starting one to many bidirectional");
            oneToManyBiDirectional();
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
                    repository::delete
            );

            System.out.println("After delete: ");
            printAllTreadmills();
        }

        private void oneToManyUniDir() {
            /*
                This onetomany unidir will create 3 tables. 2 based on entities
                And one common table with both primary keys, so i guess it looks like
                a many to many structurally
             */
            Post post = new Post("First post");

            post.getComments().add(
                    new PostComment("My first review")
            );
            post.getComments().add(
                    new PostComment("My second review")
            );
            post.getComments().add(
                    new PostComment("My third review")
            );
            postRepository.save(post);
        }

        private void oneToManyUniDirWithJoinCol() {
            /*
                Added a join column to the previous example
                With this joincolumn solution we have only 2 tables.
                A foreign key is created with the joincolumn at the postcomment table
                These are the columns from the postcommentwjc table:
                    ID  	REVIEW  	POSTWJC_ID
             */
            Postwjc post = new Postwjc("First post");

            post.getComments().add(
                    new PostCommentwjc("My first review")
            );
            post.getComments().add(
                    new PostCommentwjc("My second review")
            );
            post.getComments().add(
                    new PostCommentwjc("My third review")
            );
            postwjcRepository.save(post);
            /*
                the results in the terminal
                Starting with join column
                Hibernate: call next value for hibernate_sequence
                Hibernate: call next value for hibernate_sequence
                Hibernate: call next value for hibernate_sequence
                Hibernate: call next value for hibernate_sequence
                Hibernate: insert into postwjc (title, id) values (?, ?)
                Hibernate: insert into post_commentwjc (review, id) values (?, ?)
                Hibernate: insert into post_commentwjc (review, id) values (?, ?)
                Hibernate: insert into post_commentwjc (review, id) values (?, ?)
                Hibernate: update post_commentwjc set postwjc_id=? where id=?
                Hibernate: update post_commentwjc set postwjc_id=? where id=?
                Hibernate: update post_commentwjc set postwjc_id=? where id=?
             */
        }

        private void oneToManyBiDirectional() {
            /*
                WE ARE USING A BIDIRECTIONAL ONETOMANY RELATIONSHIP
                THE TABLE STRUCTURE STAYS THE SAME AS WITH UNIDIRECTIONAL WITH JOINCOLUMN
                BUT WE HAVE FEWER HIBERNATE SQL STATEMENTS WHEN RUNNING.
                SO PERFORMANCE IS OPTIMIZED.
                Until now this is the best way to map a one-to-many relations
             */
            Postbidir post = new Postbidir("First post");

            post.addComment(
                    new PostCommentbidir("My first review")
            );
            post.addComment(
                    new PostCommentbidir("My second review")
            );
            post.addComment(
                    new PostCommentbidir("My third review")
            );
            postBidirRepository.save(post);
            /*
                the results in terminal
                Starting with join column
                Hibernate: call next value for hibernate_sequence
                Hibernate: call next value for hibernate_sequence
                Hibernate: call next value for hibernate_sequence
                Hibernate: call next value for hibernate_sequence
                Hibernate: insert into postwjc (title, id) values (?, ?)
                Hibernate: insert into post_commentwjc (review, id) values (?, ?)
                Hibernate: insert into post_commentwjc (review, id) values (?, ?)
                Hibernate: insert into post_commentwjc (review, id) values (?, ?)
                Hibernate: update post_commentwjc set postwjc_id=? where id=?
                Hibernate: update post_commentwjc set postwjc_id=? where id=?
                Hibernate: update post_commentwjc set postwjc_id=? where id=?
             */
        }
    }
}
