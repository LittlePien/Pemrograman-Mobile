package com.example.roomassignment.data.repository

import com.example.roomassignment.data.db.BookDao
import com.example.roomassignment.data.db.GenreDao
import com.example.roomassignment.data.entity.BookEntity
import com.example.roomassignment.data.entity.GenreEntity
import com.example.roomassignment.data.entity.GenreWithBooks
import kotlinx.coroutines.flow.Flow

class FantasyRepository(
    private val genreDao: GenreDao,
    private val bookDao: BookDao
) {
    val genresWithBooks: Flow<List<GenreWithBooks>> = genreDao.getGenresWithBooks()

    suspend fun seedIfEmpty() {
        if (genreDao.count() > 0) return

        val genres = listOf(
            GenreEntity(
                name = "High Fantasy",
                description = "Sprawling secondary worlds with their own histories, magic systems and mythologies."
            ),
            GenreEntity(
                name = "Grimdark",
                description = "Morally grey characters surviving brutal, unforgiving worlds."
            ),
            GenreEntity(
                name = "Fairy Tale Retelling",
                description = "Familiar folk tales reimagined with new twists and points of view."
            ),
            GenreEntity(
                name = "Portal Fantasy",
                description = "Ordinary people pulled into extraordinary, magical worlds."
            )
        )

        val genreIds = genreDao.insertAll(genres).map { it.toInt() }
        val (highFantasyId, grimdarkId, retellingId, portalId) = genreIds

        val books = listOf(
            BookEntity(
                genreId = highFantasyId,
                title = "The Fellowship of the Ring",
                author = "J.R.R. Tolkien",
                year = 1954,
                rating = 4.9f,
                synopsis = "A hobbit and his companions set out across Middle-earth to destroy a ring of immense and corrupting power.",
                link = "https://openlibrary.org/search?q=fellowship+of+the+ring+tolkien"
            ),
            BookEntity(
                genreId = highFantasyId,
                title = "The Name of the Wind",
                author = "Patrick Rothfuss",
                year = 2007,
                rating = 4.7f,
                synopsis = "An innkeeper recounts the story of his youth: a gifted but reckless student of magic chasing the truth behind his parents' deaths.",
                link = "https://openlibrary.org/search?q=name+of+the+wind+rothfuss"
            ),
            BookEntity(
                genreId = grimdarkId,
                title = "The Blade Itself",
                author = "Joe Abercrombie",
                year = 2006,
                rating = 4.5f,
                synopsis = "A crippled torturer, a vain swordsman and a savage barbarian are drawn into a war none of them fully understand.",
                link = "https://openlibrary.org/search?q=the+blade+itself+abercrombie"
            ),
            BookEntity(
                genreId = grimdarkId,
                title = "Prince of Thorns",
                author = "Mark Lawrence",
                year = 2011,
                rating = 4.3f,
                synopsis = "A dispossessed young prince leads a band of outlaws across a shattered, post-apocalyptic kingdom on his way to a throne.",
                link = "https://openlibrary.org/search?q=prince+of+thorns+mark+lawrence"
            ),
            BookEntity(
                genreId = retellingId,
                title = "Uprooted",
                author = "Naomi Novik",
                year = 2015,
                rating = 4.6f,
                synopsis = "A wizard known as the Dragon takes a village girl as his apprentice, and she discovers a darker power growing in the forest nearby.",
                link = "https://openlibrary.org/search?q=uprooted+naomi+novik"
            ),
            BookEntity(
                genreId = retellingId,
                title = "Spinning Silver",
                author = "Naomi Novik",
                year = 2018,
                rating = 4.6f,
                synopsis = "A moneylender's daughter strikes a deal with a cold, glittering ruler of winter and ice that spirals beyond her control.",
                link = "https://openlibrary.org/search?q=spinning+silver+naomi+novik"
            ),
            BookEntity(
                genreId = portalId,
                title = "The Lion, the Witch and the Wardrobe",
                author = "C.S. Lewis",
                year = 1950,
                rating = 4.7f,
                synopsis = "Four siblings step through an old wardrobe into the snowbound land of Narnia and join the fight against an endless winter.",
                link = "https://openlibrary.org/search?q=lion+witch+wardrobe+lewis"
            ),
            BookEntity(
                genreId = portalId,
                title = "Piranesi",
                author = "Susanna Clarke",
                year = 2020,
                rating = 4.4f,
                synopsis = "A man living alone in an endless, labyrinthine House slowly uncovers the truth of how he came to be there.",
                link = "https://openlibrary.org/search?q=piranesi+susanna+clarke"
            )
        )
        bookDao.insertAll(books)
    }
}