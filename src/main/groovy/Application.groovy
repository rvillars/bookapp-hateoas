package ch.bfh.swos.bookapp

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.web.SpringBootServletInitializer
import org.springframework.context.annotation.Configuration
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.data.rest.core.config.RepositoryRestConfiguration
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration

import javax.persistence.*

@EnableAutoConfiguration
class Application extends RepositoryRestMvcConfiguration {

    @Override
    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.setBaseUri(new URI("/rest"))
        config.exposeIdsFor(Book.class)
        config.setReturnBodyOnCreate(true)
        config.setReturnBodyOnUpdate(true)
    }

    static void main(String[] args) {
        SpringApplication.run Application, args
    }
}

@Configuration
public class WebConfig extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        application.sources(Application.class)
        return super.configure(application)
    }
}

@RepositoryRestResource(path = "books")
interface BookRepository extends PagingAndSortingRepository<Book, Long> {}

@RepositoryRestResource(path = "authors")
interface AuthorRepository extends PagingAndSortingRepository<Author, Long> {}

@Entity
class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    String title
    @Temporal(TemporalType.DATE)
    Date releasedate
    @ManyToOne
    Author author
}

@Entity
class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    String firstname
    String lastname
    @OneToMany(mappedBy = "author", cascade = [CascadeType.PERSIST, CascadeType.REMOVE])
    Set<Book> books
}