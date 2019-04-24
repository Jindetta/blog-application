package fi.tuni.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Main class of the program.
 *
 * @author Joonas Lauhala {@literal <joonas.lauhala@tuni.fi>}
 *         Tuukka Juusela {@literal <tuukka.juusela@tuni.fi}
 * @version 20192802
 * @since   1.8
 */
@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

    /**
     * Repository for blog posts.
     */
    @Autowired
    BlogRepository blogRepository;

    /**
     * Repository for users.
     */
    @Autowired
    UserRepository userRepository;

    /**
     * Repository for comments.
     */
    @Autowired
    CommentRepository commentRepository;

    /**
     * Repository for likes.
     */
    @Autowired
    LikeRepository likeRepository;

    /**
     * Starts the program.
     * @param args Arguments of program. Not in use.
     */
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

    /**
     * Creates dummy data to all databases when program is started.
     * @param args Arguments of method. Not in use.
     * @throws Exception Exception if something goes wrong in creation of test data.
     */
    @Override
    public void run(String... args) throws Exception {
	    ArrayList<User> users = new ArrayList<>();
	    userRepository.findAll().forEach(users::add);

	    List<Article> articles = Stream.of(
        new Article(LocalDate.now(), "Rocket League", "Rocket League is a vehicular soccer video game developed and published by Psyonix. The game was first released for Microsoft Windows and PlayStation 4 in July 2015, with ports for Xbox One, macOS, Linux, and Nintendo Switch being released later on. In June 2016, 505 Games began distributing a physical retail version for PlayStation 4 and Xbox One, with Warner Bros. Interactive Entertainment taking over those duties by the end of 2017.", users.get(3)),
        new Article(LocalDate.now(), "Rainbow Six: Siege", "Tom Clancy's Rainbow Six Siege (often shortened to Rainbow Six Siege) is a tactical shooter video game developed by Ubisoft Montreal and published by Ubisoft. It was released worldwide for Microsoft Windows, PlayStation 4, and Xbox One on December 1, 2015. The game puts heavy emphasis on environmental destruction and cooperation between players. Each player assumes control of an attacker or a defender in different gameplay modes such as rescuing a hostage, defusing a bomb, and taking control of a capture point. The title has no campaign but features a series of short missions that can be played solo. These missions have a loose narrative, focusing on recruits going through training to prepare them for future encounters with the White Masks, a terrorist group that threatens the safety of the world.", users.get(3)),
        new Article(LocalDate.of(2019,01,15), "Dab (dance)", "Dabbing, or the dab, is a simple dance move or gesture in which a person drops the head into the bent crook of a slanted, upwardly angled arm, while raising the opposite arm out straight in a parallel direction. Since 2015, dabbing has been used as a gesture of triumph or playfulness, becoming a youthful American fad and Internet meme.[1] The move looks similar to someone sneezing into the \"inside\" of their elbow.", users.get(2)),
        new Article(LocalDate.of(2019,03,20), "cURL", "cURL (pronounced 'curl'[4]) is a computer software project providing a library and command-line tool for transferring data using various protocols. The cURL project produces two products, libcurl and cURL. It was first released in 1997. The name stands for \"Client URL\".[5] The original author and lead developer is the Swedish developer Daniel Stenberg.", users.get(1)),
        new Article(LocalDate.of(2019,02,01), "Disc golf", "Disc golf (also called Frisbee golf or sometimes frolf) is a flying disc sport in which players throw a disc at a target; it is played using rules similar to golf. It is often played on a course of 9 or 18 holes. Players complete a hole by throwing a disc from a tee area toward a target, throwing again from the landing position of the disc until the target is reached. Usually, the number of throws a player uses to reach each target are tallied (often in relation to par), and players seek to complete each hole, and the course, in the lowest number of total throws.", users.get(1)),
        new Article(LocalDate.of(2019,03,20), "The Post Man", "The Post Man was an English newspaper published between 1695 and 1730.[1] It was edited by Jean de Fonvive, a Huguenot refugee.[2] It appeared three times a week and established such a reputation that the soldier, Duke of Marlborough, insisted that his military dispatches should only appear in its pages. It was published in London", users.get(1)),
        new Article(LocalDate.of(2019,02,01), "Ultimate", "Ultimate, originally known as Ultimate frisbee, is a non-contact team sport played with a flying disc (frisbee). Ultimate was developed in 1968 by a group of students at Columbia High School in Maplewood, New Jersey. Although Ultimate resembles many traditional sports in its athletic requirements, it is unlike most sports due to its focus on self-officiating, even at the highest levels of competition.", users.get(0)),
        new Article(LocalDate.of(2019,01,15), "World of Warcraft", "World of Warcraft (WoW) is a massively multiplayer online role-playing game (MMORPG) released in 2004 by Blizzard Entertainment. It is the fourth released game set in the Warcraft fantasy universe.[3] World of Warcraft takes place within the Warcraft world of Azeroth, approximately four years after the events at the conclusion of Blizzard's previous Warcraft release, Warcraft III: The Frozen Throne.[4] The game was announced in 2001, and was released for the 10th anniversary of the Warcraft franchise on November 23, 2004. Since launch, World of Warcraft has had seven major expansion packs released for it: The Burning Crusade, Wrath of the Lich King, Cataclysm, Mists of Pandaria, Warlords of Draenor, Legion, and Battle for Azeroth. ", users.get(2))
        ).collect(Collectors.toList());

	    blogRepository.saveAll(articles);

	    List<Comment> comments = Stream.of(
            new Comment(users.get(3), articles.get(1), "Tämä on kiva peli"),
            new Comment(users.get(1), articles.get(0), "Niin hyvää!"),
            new Comment(users.get(1), articles.get(0), "Erittäin hyvä artikkeli. Nautin jokaisesta hetkestä lukiessani tätä artikkelia. Kirjoittaja on varmasti erittäin hyvä pelaamaan tätä peliä!"),
            new Comment(users.get(1), articles.get(0), "Faktaa!"),
            new Comment(users.get(0), articles.get(3), "Heh")
        ).collect(Collectors.toList());

        commentRepository.saveAll(comments);

        System.out.println("-----------------------------------------------------");
        System.out.println("ARTICLES IN BACKEND: ");
        blogRepository.findAll().forEach(System.out::println);
        System.out.println("USERS IN BACKEND: ");
        userRepository.findAll().forEach(System.out::println);
        System.out.println("-----------------------------------------------------");
        System.out.println("COMMENTS IN BACKEND: ");
        commentRepository.findAll().forEach(System.out::println);
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

        List<LikeStatus> likeStatuses = Stream.of(
            new LikeStatus(users.get(0).getId(), comments.get(1).getId()),
            new LikeStatus(users.get(0).getId(), comments.get(1).getId()),
            new LikeStatus(users.get(0).getId(), comments.get(2).getId()),
            new LikeStatus(users.get(1).getId(), comments.get(2).getId()),
            new LikeStatus(users.get(2).getId(), comments.get(2).getId()),
            new LikeStatus(users.get(3).getId(), comments.get(2).getId()),
            new LikeStatus(users.get(0).getId(), comments.get(3).getId())
        ).collect(Collectors.toList());

        likeRepository.saveAll(likeStatuses);
	}
}
