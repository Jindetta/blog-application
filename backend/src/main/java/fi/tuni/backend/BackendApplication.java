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

    @Autowired
    UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
	    User user1 = new User("Tuukka", "Juusela");
        User user2 = new User("Taneli", "Taikina");
        User user3 = new User("Maikki", "Manaaja");
	    userRepository.save(user1);
	    userRepository.save(user2);
        userRepository.save(user3);

        blogRepository.save(new Article(LocalDate.of(2019,01,15), "Dab (dance)", "Dabbing, or the dab, is a simple dance move or gesture in which a person drops the head into the bent crook of a slanted, upwardly angled arm, while raising the opposite arm out straight in a parallel direction. Since 2015, dabbing has been used as a gesture of triumph or playfulness, becoming a youthful American fad and Internet meme.[1] The move looks similar to someone sneezing into the \"inside\" of their elbow.", user3.getId()));
        blogRepository.save(new Article(LocalDate.of(2019,03,20), "cURL", "cURL (pronounced 'curl'[4]) is a computer software project providing a library and command-line tool for transferring data using various protocols. The cURL project produces two products, libcurl and cURL. It was first released in 1997. The name stands for \"Client URL\".[5] The original author and lead developer is the Swedish developer Daniel Stenberg.", user2.getId()));
        blogRepository.save(new Article(LocalDate.of(2019,02,01), "Disc golf", "Disc golf (also called Frisbee golf or sometimes frolf) is a flying disc sport in which players throw a disc at a target; it is played using rules similar to golf. It is often played on a course of 9 or 18 holes. Players complete a hole by throwing a disc from a tee area toward a target, throwing again from the landing position of the disc until the target is reached. Usually, the number of throws a player uses to reach each target are tallied (often in relation to par), and players seek to complete each hole, and the course, in the lowest number of total throws.", user1.getId()));
        blogRepository.save(new Article(LocalDate.of(2019,03,20), "The Post Man", "The Post Man was an English newspaper published between 1695 and 1730.[1] It was edited by Jean de Fonvive, a Huguenot refugee.[2] It appeared three times a week and established such a reputation that the soldier, Duke of Marlborough, insisted that his military dispatches should only appear in its pages. It was published in London", user2.getId()));
        blogRepository.save(new Article(LocalDate.of(2019,02,01), "Ultimate", "Ultimate, originally known as Ultimate frisbee, is a non-contact team sport played with a flying disc (frisbee). Ultimate was developed in 1968 by a group of students at Columbia High School in Maplewood, New Jersey. Although Ultimate resembles many traditional sports in its athletic requirements, it is unlike most sports due to its focus on self-officiating, even at the highest levels of competition.", user1.getId()));
        blogRepository.save(new Article(LocalDate.of(2019,01,15), "World of Warcraft", "World of Warcraft (WoW) is a massively multiplayer online role-playing game (MMORPG) released in 2004 by Blizzard Entertainment. It is the fourth released game set in the Warcraft fantasy universe.[3] World of Warcraft takes place within the Warcraft world of Azeroth, approximately four years after the events at the conclusion of Blizzard's previous Warcraft release, Warcraft III: The Frozen Throne.[4] The game was announced in 2001, and was released for the 10th anniversary of the Warcraft franchise on November 23, 2004. Since launch, World of Warcraft has had seven major expansion packs released for it: The Burning Crusade, Wrath of the Lich King, Cataclysm, Mists of Pandaria, Warlords of Draenor, Legion, and Battle for Azeroth. ", user3.getId()));

        Iterable<Article> articles = blogRepository.findAll();
        Iterable<User> users = userRepository.findAll();
        System.out.println("-----------------------------------------------------");
        System.out.println("ARTICLES IN BACKEND: ");
        articles.forEach(System.out::println);
        System.out.println("USERS IN BACKEND: ");
        users.forEach(System.out::println);
        System.out.println("-----------------------------------------------------");
        System.out.println("CURL COMMANDS - ARTICLES ");
        System.out.println("Get all articles: curl -X \"GET\" localhost:8080/blogs");
        System.out.println("Get article: curl -X \"GET\" localhost:8080/blogs/2");
        System.out.println("Delete article: curl -X \"DELETE\" localhost:8080/blogs/2");
        System.out.println("Post article: curl -d \"title=Postman is better&content=Curl sucks&authorId=1\" localhost:8080/blogs");
        System.out.println();
        System.out.println("CURL COMMANDS - USERS");
        System.out.println("Get all users: curl -X \"GET\" localhost:8080/users");
        System.out.println("Get user: curl -X \"GET\" localhost:8080/users/1");
        System.out.println("Delete user: curl -X \"DELETE\" localhost:8080/users/1");
        System.out.println("Get all users: curl -d \"firstName=Tuukka&lastName=Juusela\" localhost:8080/users");
        System.out.println("-----------------------------------------------------");

	}
}
