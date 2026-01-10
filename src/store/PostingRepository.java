package store;

import domain.Posting;

import java.util.List;
import java.util.Optional;

public interface PostingRepository {
    void save(Posting posting);

    Optional<Posting> findById(long id);

    List<Posting> findAll();

    boolean deleteById(long id);
}
