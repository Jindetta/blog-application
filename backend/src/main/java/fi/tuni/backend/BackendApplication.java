package fi.tuni.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

    @Autowired
    BlogRepository blogRepository;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}


    @Override
    public void run(String... args) throws Exception {
        blogRepository.save(new Article(LocalDate.of(2019,02,01), "Ultimate", "Ultimate, originally known as Ultimate frisbee, is a non-contact team sport played with a flying disc (frisbee). Ultimate was developed in 1968 by a group of students at Columbia High School in Maplewood, New Jersey. Although Ultimate resembles many traditional sports in its athletic requirements, it is unlike most sports due to its focus on self-officiating, even at the highest levels of competition.", 2));
        blogRepository.save(new Article(LocalDate.of(2019,02,01), "Disc golf", "Disc golf (also called Frisbee golf or sometimes frolf) is a flying disc sport in which players throw a disc at a target; it is played using rules similar to golf.[5] It is often played on a course of 9 or 18 holes. Players complete a hole by throwing a disc from a tee area toward a target, throwing again from the landing position of the disc until the target is reached. Usually, the number of throws a player uses to reach each target are tallied (often in relation to par), and players seek to complete each hole, and the course, in the lowest number of total throws.", 1));
    }
}
