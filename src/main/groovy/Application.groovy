package bookapp

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.*
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.config.RepositoryRestConfiguration
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.context.annotation.Import
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.data.repository.CrudRepository
import org.springframework.http.MediaType

import javax.persistence.*

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories
class Application extends RepositoryRestMvcConfiguration {
    static void main(String[] args) {
        SpringApplication.run Application, args
    }

    @Override
    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.setBaseUri(new URI("/rest"))
    }
}

@RepositoryRestResource(collectionResourceRel = "books", path = "books")
interface BookRepository extends PagingAndSortingRepository<Book, Long> {}

@RepositoryRestResource(collectionResourceRel = "authors", path = "authors" )
interface AuthorRepository extends PagingAndSortingRepository<Author, Long> {}

@Entity
class Book {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String title;
    @Temporal(TemporalType.DATE)
    Date releasedate;
    @ManyToOne
    Author author;
}

@Entity
class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id
	String firstname
	String lastname
	@OneToMany(mappedBy = "author", cascade = [CascadeType.PERSIST, CascadeType.REMOVE])
	Set<Book> books;
}